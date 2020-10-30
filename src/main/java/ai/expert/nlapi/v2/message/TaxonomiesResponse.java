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

package ai.expert.nlapi.v2.message;

import ai.expert.nlapi.exceptions.NLApiErrorCode;
import ai.expert.nlapi.exceptions.NLApiException;
import ai.expert.nlapi.v2.model.TaxonomyInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxonomiesResponse {

    private List<TaxonomyInfo> taxonomies;

    public TaxonomyInfo getTaxonomyByName(String name) throws NLApiException {
        return taxonomies.stream()
                         .filter(taxonomy -> taxonomy.getName().equals(name))
                         .findAny()
                         .orElseThrow(() -> new NLApiException(NLApiErrorCode.REQUEST_UNKNOWN_TAXONOMY_ERROR));
    }

    @SneakyThrows
    public String toJSON() {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(this);
    }

    @SneakyThrows
    public void prettyPrint() {
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this));
    }
}
