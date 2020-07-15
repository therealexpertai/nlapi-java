/*
 * Copyright (c) 2020 original authors
 *
 * Licensed under the Apache License, Versions 2.0 (the "License");
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

package ai.expert.nlapi.v1.message;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.Value;

@Value
@JsonRootName(value = "document")
public class RequestDocument {

    String text;

    public static RequestDocument of(String text) {
        return new RequestDocument(text);
    }

    @SneakyThrows
    public String toJSON() {
        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.WRAP_ROOT_VALUE);
        return om.writeValueAsString(this);
    }
}
