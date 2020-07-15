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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import kong.unirest.ObjectMapper;

import java.io.IOException;

public class ObjectMapperAdapter implements ObjectMapper {

    private final com.fasterxml.jackson.databind.ObjectMapper om;

    public ObjectMapperAdapter() {
        om = new com.fasterxml.jackson.databind.ObjectMapper();
        om.enable(SerializationFeature.WRAP_ROOT_VALUE);
    }

    @Override
    public <T> T readValue(String value, Class<T> valueType) {
        try {
            return om.readValue(value, valueType);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String writeValue(Object value) {
        try {
            return om.writeValueAsString(value);
        }
        catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
