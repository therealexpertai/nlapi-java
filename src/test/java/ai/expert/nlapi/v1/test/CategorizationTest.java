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

package ai.expert.nlapi.v1.test;

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v1.API;
import ai.expert.nlapi.v1.Categorizer;
import ai.expert.nlapi.v1.CategorizerConfig;
import ai.expert.nlapi.v1.message.ResponseDocument;

public class CategorizationTest {

    static StringBuilder sb = new StringBuilder();

    static {
        sb.append("Michael Jordan was one of the best basketball players of all time.");
        sb.append("Scoring was Jordan's stand-out skill, but he still holds a defensive NBA record, with eight steals in a half.");
    }

    public static String getSampleText() {
        return sb.toString();
    }

    public static Authentication createAuthentication() throws Exception {
        Authenticator authenticator = new BasicAuthenticator(new Credential("USERNAME", "PASSWORD"));
        return new Authentication(authenticator);
    }

    public static Categorizer createCategorizer() throws Exception {
        return new Categorizer(CategorizerConfig.builder()
                                                .withVersion(API.Versions.V1)
                                                .withTaxonomy(API.Taxonomies.IPTC)
                                                .withLanguage(API.Languages.en)
                                                .withAuthentication(createAuthentication())
                                                .build());
    }

    public static void main(String[] args) {
        try {
            Categorizer categorizer = createCategorizer();
            ResponseDocument categorization = categorizer.categorize(getSampleText());
            categorization.prettyPrint();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
