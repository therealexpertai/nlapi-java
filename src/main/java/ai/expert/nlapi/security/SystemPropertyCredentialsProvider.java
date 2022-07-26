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

import ai.expert.nlapi.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemPropertyCredentialsProvider extends CredentialsProvider {

    private static final Logger logger = LoggerFactory.getLogger(SystemPropertyCredentialsProvider.class);


    public SystemPropertyCredentialsProvider() {
    }

    public Credential getCredentials() {

        logger.debug("Getting Credentials from System Property...");
        String username = StringUtils.trim(System.getProperty(SecurityUtils.USER_ACCESS_KEY_PROP));
        String password = StringUtils.trim(System.getProperty(SecurityUtils.PASSWORD_ACCESS_KEY_PROP));
        String token = StringUtils.trim(System.getProperty(SecurityUtils.TOKEN_ACCESS_KEY_PROP));

        if (token==null || token.isEmpty()) {
            // check the variables, if not valid return null
            if(username == null || username.isEmpty()) {
                return null;
            }

            if(password == null || password.isEmpty()) {
                return null;
            }
            logger.info("Found Credentials from System Property.");
        } else {
            logger.info("Found Token from System Property.");
        }
        return new Credential(username, password, token);
    }
}
