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

public class SecurityUtils {

    // keys used for SystemPropertyCredentialsProvider
    public static final String USER_ACCESS_KEY_PROP = "eai.username";
    public static final String PASSWORD_ACCESS_KEY_PROP = "eai.password";

    // keys used for EnvironmentVariablesCredentialsProvider
    public static final String USER_ACCESS_KEY_ENV = "EAI_USERNAME";
    public static final String PASSWORD_ACCESS_KEY_ENV = "EAI_PASSWORD";

    public static String bearerOf(String JWT) {
        return String.format("Bearer %s", JWT);
    }
}
