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

package ai.expert.nlapi.utils;

import ai.expert.nlapi.exceptions.NLApiErrorCode;
import ai.expert.nlapi.exceptions.NLApiException;
import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.SecurityUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class APIUtils {

    private static final Logger logger = LogManager.getLogger();

    public static <T> T fromJSON(String json, Class<T> valueType) throws NLApiException {

        T response = null;

        try {
            ObjectMapper om = new ObjectMapper();
            // Trying to deserialize value into an enum, don't fail on unknown value, use null instead
            om.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
            // Just ignore unknown fields, don't stop parsing
            //om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            response = om.readValue(json, valueType);
        }
        catch(JsonMappingException e) {
            String msg = String.format("Please report this issue. Error while parsing json response into class [%s]: %s",
                                       valueType.toString(),
                                       json);
            logger.error(msg);
            logger.error(e);
            throw new NLApiException(NLApiErrorCode.PARSING_ERROR, msg);
        }
        catch(JsonProcessingException e) {
            String msg = String.format("Please report this issue. Error while processing json into class [%s]: %s",
                                       valueType.toString(),
                                       json);
            logger.error(msg);
            logger.error(e);
            throw new NLApiException(NLApiErrorCode.PARSING_ERROR, msg);
        }

        return response;
    }

    public static String getBearerToken(Authentication authentication) throws NLApiException {
        return SecurityUtils.bearerOf(authentication.isValid() ? authentication.getJWT() : authentication.refresh());
    }
}
