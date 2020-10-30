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

package ai.expert.nlapi.v2.model;

import ai.expert.nlapi.exceptions.NLApiErrorCode;
import ai.expert.nlapi.exceptions.NLApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxonomyInfo {

    private String name;
    private String contract;
    private String description;
    private List<LanguageInfo> languages;

    public LanguageInfo getLanguagesByName(String name) throws NLApiException {
        return languages.stream()
                        .filter(language -> language.getName().equals(name))
                        .findAny()
                        .orElseThrow(() -> new NLApiException(NLApiErrorCode.REQUEST_UNKNOWN_LANGUAGE_ERROR));
    }
}
