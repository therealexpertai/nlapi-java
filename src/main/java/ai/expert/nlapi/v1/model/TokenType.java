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

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TokenType {

    @JsonProperty("ADJ")
    ADJ("Adjective"),

    @JsonProperty("ADV")
    ADV("Adverb"),

    @JsonProperty("ART")
    ART("Article"),

    @JsonProperty("AUX")
    AUX("Auxiliary verb"),

    @JsonProperty("CON")
    CON("Conjunction"),

    @JsonProperty("NOU")
    NOU("Noun"),

    @JsonProperty("NOU.ADR")
    NOU_ADR("Street address"),

    @JsonProperty("NOU.DAT")
    NOU_DAT("Date"),

    @JsonProperty("NOU.HOU")
    NOU_HOU("Hour"),

    @JsonProperty("NOU.MAI")
    NOU_MAI("Email address"),

    @JsonProperty("NOU.MEA")
    NOU_MEA("Measure"),

    @JsonProperty("NOU.MON")
    NOU_MON("Money"),

    @JsonProperty("NOU.PCT")
    NOU_PCT("Percentage"),

    @JsonProperty("NOU.PHO")
    NOU_PHO("Phone number"),

    @JsonProperty("NOU.WEB")
    NOU_WEB("Web address"),

    @JsonProperty("NPR")
    NPR("Proper noun"),

    @JsonProperty("NPR.ANM")
    NPR_ANM("Proper noun of an animal"),

    @JsonProperty("NPR.BLD")
    NPR_BLD("Proper noun of a building"),

    @JsonProperty("NPR.COM")
    NPR_COM("Proper noun of a business/company"),

    @JsonProperty("NPR.DEV")
    NPR_DEV("Proper noun of a device"),

    @JsonProperty("NPR.DOC")
    NPR_DOC("Proper noun of a document"),

    @JsonProperty("NPR.EVN")
    NPR_EVN("Proper noun of an event"),

    @JsonProperty("NPR.FDD")
    NPR_FDD("Proper noun of a food/beverage"),

    @JsonProperty("NPR.GEA")
    NPR_GEA("Proper noun of a physical geographic feature"),

    @JsonProperty("NPR.GEO")
    NPR_GEO("Proper noun of an administrative geographic area"),

    @JsonProperty("NPR.GEX")
    NPR_GEX("Proper noun of an extra-terrestrial or imaginary place"),

    @JsonProperty("NPR.LEN")
    NPR_LEN("Proper noun of a legal/fiscal entity"),

    @JsonProperty("NPR.MMD")
    NPR_MMD("Proper noun of a mass media"),

    @JsonProperty("NPR.NPH")
    NPR_NPH("Proper noun of a human being"),

    @JsonProperty("NPR.ORG")
    NPR_ORG("Proper noun of an organization/society/institution"),

    @JsonProperty("NPR.PPH")
    NPR_PPH("Proper noun of a physical phenomena"),

    @JsonProperty("NPR.PRD")
    NPR_PRD("Proper noun of a product"),

    @JsonProperty("NPR.VCL")
    NPR_VCL("Proper noun of a vehicle"),

    @JsonProperty("NPR.WRK")
    NPR_WRK("Proper noun of a work of human intelligence"),

    @JsonProperty("PNT")
    PNT("Punctuation mark"),

    @JsonProperty("PRE")
    PRE("Preposition"),

    @JsonProperty("PRO")
    PRO("Pronoun"),

    @JsonProperty("PRT")
    PRT("Particle"),

    @JsonProperty("VER")
    VER("Verb"),

    @JsonProperty("...")
    ANY("Any entity type");

    private final String description;

    TokenType(String description) {
        this.description = description;
    }

    public static TokenType fromDescription(String description) {
        for(TokenType b : TokenType.values()) {
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
