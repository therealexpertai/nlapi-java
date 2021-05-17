/*
 * Copyright (c) 2021 original authors
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

package ai.expert.nlapi.v2.message;

import ai.expert.nlapi.v2.model.ResourceInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourcesInfoResponse {

    @JsonProperty("result")
    private String result;

    @JsonProperty("server")
    private String server;

    @JsonProperty("time")
    private double time;

    @JsonProperty("itime")
    private double itime;

    @JsonProperty("error")
    private InstanceError error;

    @JsonProperty("data")
    private List<ResourceInfo> data;

    @SneakyThrows
    public String toJSON() {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(this);
    }

    @SneakyThrows
    public void prettyPrint() {
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this));
    }

    public boolean isSuccess() {
        return result.equals("success");
    }
}
