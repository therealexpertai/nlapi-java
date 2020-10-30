/*
 * Copyright (c) 2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.expert.nlapi.security;

import ai.expert.nlapi.exceptions.NLApiErrorCode;
import ai.expert.nlapi.exceptions.NLApiException;
import ai.expert.nlapi.utils.ObjectMapperAdapter;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
@EqualsAndHashCode
@ToString
public class BasicAuthenticator implements Authenticator {
    private static final Logger logger = LogManager.getLogger();

    private final Credential credential;

    public BasicAuthenticator(Credential credential) {
        this.credential = credential;
        init();
    }

    public BasicAuthenticator(CredentialsProvider credentialProvider) {
        this.credential = credentialProvider.getCredentials();
        init();
    }

    private void init() {
        Unirest.config().setObjectMapper(new ObjectMapperAdapter());
    }

    @Override
    public String authenticate() throws NLApiException {
        // chek credential
        if(credential == null) {
            String msg = "Please check credential settings.";
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.AUTHENTICATION_ERROR, msg);
        }
        if(credential.getUsername() == null || credential.getUsername().isEmpty()) {
            String msg = "Please check settings credential username.";
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.AUTHENTICATION_ERROR, msg);
        }
        if(credential.getPassword() == null || credential.getPassword().isEmpty()) {
            String msg = "Please check settings credential password.";
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.AUTHENTICATION_ERROR, msg);
        }

        // make Authentication call to expert.ai
        HttpResponse<String> httpResponse = Unirest.post(BASE_URL + "/oauth2/token")
                                                   .header("Content-Type", "application/json")
                                                   .header("Accept", "*/*")
                                                   .body(getCredential().toJSON()).asString();

        // check response status
        if(httpResponse.getStatus() == 500) {
            String msg =
              String.format("Authentication call to API %s return error status %d with message %s",
                            BASE_URL + "/oauth2/token", httpResponse.getStatus(), httpResponse.getBody());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.AUTHORIZATION_ERROR, msg);
        }
        else if(httpResponse.getStatus() != 200) {
            String msg =
              String.format("Authentication call to API %s return error status %d",
                            BASE_URL + "/oauth2/token", httpResponse.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.AUTHORIZATION_ERROR, msg);
        }

        return httpResponse.getBody();
    }
}
