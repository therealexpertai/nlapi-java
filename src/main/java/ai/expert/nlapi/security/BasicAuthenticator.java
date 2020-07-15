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

import ai.expert.nlapi.utils.ObjectMapperAdapter;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class BasicAuthenticator implements Authenticator {

    private Credential credential;

    public BasicAuthenticator(Credential credential) {
        this.credential = credential;
        init();
    }

    private void init() {
        Unirest.config().setObjectMapper(new ObjectMapperAdapter());
    }

    @Override
    public String authenticate() {
        HttpResponse<String> res = Unirest.post(BASE_URL + "/oauth2/token")
                                          .header("Content-Type", "application/json")
                                          .header("Accept", "*/*")
                                          .body(getCredential().toJSON()).asString();
        return res.getBody();
    }
}
