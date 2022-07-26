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
public class EnvironmentVariablesCredentialsProvider extends CredentialsProvider {

    private static final Logger  logger = LoggerFactory.getLogger(EnvironmentVariablesCredentialsProvider.class);


    public EnvironmentVariablesCredentialsProvider() {
    }

    public Credential getCredentials() {

        logger.debug("Getting Credentials from Environment Variables...");
        String username = StringUtils.trim(System.getenv(SecurityUtils.USER_ACCESS_KEY_ENV));
        String password = StringUtils.trim(System.getenv(SecurityUtils.PASSWORD_ACCESS_KEY_ENV));
        String token = StringUtils.trim(System.getenv(SecurityUtils.TOKEN_ACCESS_KEY_ENV));

        if (token==null || token.isEmpty()) {
            // check the variables, if not valid return null
            if(username == null || username.isEmpty()) {
                return null;
            }

            if(password == null || password.isEmpty()) {
                return null;
            }
            logger.info("Found Credentials from Environment Variables.");
        } else {
            logger.info("Found Token from Environment Variables.");
        }

        return new Credential(username, password, token);
    }
}
