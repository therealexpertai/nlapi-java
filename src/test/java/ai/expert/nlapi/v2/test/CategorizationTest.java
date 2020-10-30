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

package ai.expert.nlapi.v2.test;

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.Categorizer;
import ai.expert.nlapi.v2.CategorizerConfig;
import ai.expert.nlapi.v2.message.CategorizeResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CategorizationTest {

    static StringBuilder sbIPTC = new StringBuilder();
    static StringBuilder sbGEOTAX = new StringBuilder();

    public static String getSampleTextIPTC() {
        return sbIPTC.toString();
    }

    public static String getSampleTextGEOTAX() {
        return sbGEOTAX.toString();
    }

    public static Categorizer createCategorizer(Authentication authentication, String taxonomy) throws Exception {
        return new Categorizer(CategorizerConfig.builder()
                                                .withVersion(API.Versions.V2)
                                                .withTaxonomy(taxonomy)
                                                .withLanguage(API.Languages.en)
                                                .withAuthentication(authentication)
                                                .build());
    }

    @Before
    public void setup() {

        // load myProperties.txt file
        TestUtils.setPropertyFile("src/test/resources/myProperties.txt");

        // check number of call to avoid limit call rate error
        TestUtils.callRateCheck();

        // set text to be analyzed using IPTC taxonomy
        sbIPTC.append("Michael Jordan was one of the best basketball players of all time. ");
        sbIPTC.append("Scoring was Jordan's stand-out skill, but he still holds a defensive NBA record, with eight steals in a half.");

        // set text to be analyzed using GeoTAX taxonomy
        sbGEOTAX.append("Rome is the capital city and a special comune of Italy as well as the capital of the Lazio region. ");
        sbGEOTAX.append("The city has been a major human settlement for almost three millennia. ");
        sbGEOTAX.append("It is the third most populous city in the European Union by population within city limits.");
    }

    @Test
    public void testCategorizationIPTC() {
        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create categorization
            Categorizer categorizer = createCategorizer(authentication, "IPTC");

            // send categorization request and get response
            CategorizeResponse categorization = categorizer.categorize(getSampleTextIPTC());
            // print json response
            categorization.prettyPrint();

            // assert there is the data passed as input
            assertTrue(categorization.getData() != null);
            assertTrue(categorization.getData().getContent() != null);
            assertTrue(categorization.getData().getLanguage() == API.Languages.en);

            // assert there are categories
            assertTrue(categorization.getData().getCategories() != null);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void testCategorizationGEOTAX() {
        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create categorization
            Categorizer categorizer = createCategorizer(authentication, "GeoTAX");

            // send categorization request and get response
            CategorizeResponse categorization = categorizer.categorize(getSampleTextGEOTAX());

            // print json response
            categorization.prettyPrint();

            // assert there is the data passed as input
            assertTrue(categorization.getData() != null);
            assertTrue(categorization.getData().getContent() != null);
            assertTrue(categorization.getData().getLanguage() == API.Languages.en);

            // assert there are categories
            assertTrue(categorization.getData().getCategories() != null);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            assertTrue(false);
        }
    }
}
