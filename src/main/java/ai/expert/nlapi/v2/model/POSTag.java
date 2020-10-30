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

import com.fasterxml.jackson.annotation.JsonProperty;

public enum POSTag {

    @JsonProperty("ADJ")
    ADJ("adjective"),

    @JsonProperty("ADP")
    ADP("adposition"),

    @JsonProperty("ADV")
    ADV("adverb"),

    @JsonProperty("AUX")
    AUX("auxiliary"),

    @JsonProperty("CCONJ")
    CCONJ("coordinating conjunction"),

    @JsonProperty("DET")
    DET("determiner"),

    @JsonProperty("INTJ")
    INTJ("interjection"),

    @JsonProperty("NOUN")
    NOUN("noun"),

    @JsonProperty("NUM")
    NUM("numeral"),

    @JsonProperty("PART")
    PART("particle"),

    @JsonProperty("PRON")
    PRON("pronoun"),

    @JsonProperty("PROPN")
    PROPN("proper noun"),

    @JsonProperty("PUNCT")
    PUNCT("punctuation"),

    @JsonProperty("SCONJ")
    SCONJ("subordinating conjunction"),

    @JsonProperty("SYM")
    SYM("symbol"),

    @JsonProperty("VERB")
    VERB("verb"),

    @JsonProperty("X")
    X("other");

    private final String description;

    POSTag(String description) {
        this.description = description;
    }

    public static POSTag fromDescription(String description) {
        for(POSTag b : POSTag.values()) {
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
