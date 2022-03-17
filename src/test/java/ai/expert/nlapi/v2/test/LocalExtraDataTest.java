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
import ai.expert.nlapi.v2.edge.Analyzer;
import ai.expert.nlapi.v2.edge.AnalyzerConfig;
import ai.expert.nlapi.v2.message.AnalyzeResponse;
import ai.expert.nlapi.v2.model.AnalyzeDocument;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class LocalExtraDataTest {

    static StringBuilder sb = new StringBuilder();

    public static String getSampleText() {
        return sb.toString();
    }

    public static Analyzer createAnalyzer(Authentication authentication) throws Exception {
        return new Analyzer(AnalyzerConfig.builder()
                                          .withVersion(API.Versions.V2)
                                          .withHost(API.DEFAULT_EDGE_HOST)
                                          .withAuthentication(authentication)
                                          .build());
    }

    @Before
    public void setup() {
        try {
            // load myProperties.txt file
            TestUtils.setPropertyFile("src/test/resources/myProperties.txt");

            // check number of call to avoid limit call rate error
            TestUtils.callRateCheck();

            // set text to be analyzed
            sb.append("Mario Rossi, \\/ Ph.D., D.Sc., F.A.H.A.\n" +
                      "CURRICULUM VITAE\n" +
                      "May 2017\n" +
                      "PERSONAL\n" +
                      "Office: University of Kentucky Telephone: (859) 323-3512\n" +
                      "Saha Cardiovascular Research Center Fax: (859) 257-3235\n" +
                      "BBSRB, room B-243 Cell: (859) 967-9563\n" +
                      "Lexington KY 40536-0509 E-Mail: Alan.Daugherty@uky.edu\n" +
                      "Web site: http://www.mc.uky.edu/cvrc/faculty/daugherty-alan/\n" +
                      "Date of Birth: May 2, 1957 Place of Birth: Liverpool, UK\n" +
                      "Nationality: Naturalized US Citizen\n");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExtraDataAnalysis() {
        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create analyzer
            Analyzer analyzer = createAnalyzer(authentication);

            // Custom analysis
            ArrayList<String> analysis = new ArrayList<>();
            analysis.add("disambiguation");
            analysis.add("extractions");
            analysis.add("entities");
            ArrayList<String> features = new ArrayList<>();
            features.add("syncpos");
            features.add("knowledge");
            features.add("extradata");
            Map<String,Object> extra = new HashMap<>();
            extra.put("key1","value1");
            AnalyzeResponse analysisResponse = analyzer.analyze(getSampleText(),analysis,features);
            analysisResponse.prettyPrint();

            // assert there is the data passed as input
            AnalyzeDocument data = analysisResponse.getData();
            assertNotNull(data);
            assertNotNull(data.getExtraData());
            assertSame(data.getLanguage(), API.Languages.en);
            assertNotNull(data.getParagraphs());
            assertNotNull(data.getPhrases());
            assertNotNull(data.getSentences());
            assertNotNull(data.getTokens());

        }
        catch(Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
