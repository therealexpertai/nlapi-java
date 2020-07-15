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

public enum EntityType {

    @JsonProperty("ADR")
    ADR("Street address"),

    @JsonProperty("ANM")
    ANM("Animals"),

    @JsonProperty("BLD")
    BLD("Building"),

    @JsonProperty("COM")
    COM("Businesses / companies"),

    @JsonProperty("DAT")
    DAT("Date"),

    @JsonProperty("DEV")
    DEV("Device"),

    @JsonProperty("DOC")
    DOC("RequestDocument"),

    @JsonProperty("EVN")
    EVN("Event"),

    @JsonProperty("FDD")
    FDD("Food/beverage"),

    @JsonProperty("GEA")
    GEA("Physical geographic features"),

    @JsonProperty("GEO")
    GEO("Administrative geographic areas"),

    @JsonProperty("GEX")
    GEX("Extended geography"),

    @JsonProperty("HOU")
    HOU("Hours"),

    @JsonProperty("LEN")
    LEN("Legal entities"),

    @JsonProperty("MAI")
    MAI("Email address"),

    @JsonProperty("MEA")
    MEA("Measure"),

    @JsonProperty("MMD")
    MMD("Mass media"),

    @JsonProperty("MON")
    MON("Money"),

    @JsonProperty("NPH")
    NPH("Humans"),

    @JsonProperty("ORG")
    ORG("Organizations / societies / institutions"),

    @JsonProperty("PCT")
    PCT("Percentage"),

    @JsonProperty("PHO")
    PHO("Phone number"),

    @JsonProperty("PPH")
    PPH("Physical phenomena"),

    @JsonProperty("PRD")
    PRD("Product"),

    @JsonProperty("VCL")
    VCL("Vehicle"),

    @JsonProperty("WEB")
    WEB("Web address"),

    @JsonProperty("WRK")
    WRK("Work of human intelligence"),

    @JsonProperty("NPR")
    NPR("Proper noun");

    private final String description;

    EntityType(String description) {
        this.description = description;
    }

    public static EntityType fromDescription(String description) {
        for(EntityType b : EntityType.values()) {
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
