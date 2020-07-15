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

package ai.expert.nlapi.v1.model;

public enum PhraseType {

    AP("Adjective Phrase"),
    CP("Conjunction Phrase"),
    CR("Blank lines"),
    DP("Adverb Phrase"),
    NP("Noun Phrase"),
    PN("Nominal Predicate "),
    PP("Preposition Phrase"),
    RP("Relative Phrase"),
    VP("Verb Phrase"),
    NA("Not Applicable");

    private final String description;

    PhraseType(String description) {
        this.description = description;
    }

    public static PhraseType fromDescription(String description) {
        for(PhraseType b : PhraseType.values()) {
            if(String.valueOf(b.description).equals(description)) {
                return b;
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
    }
}
