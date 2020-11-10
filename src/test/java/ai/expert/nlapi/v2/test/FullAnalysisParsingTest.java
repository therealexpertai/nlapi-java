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
import ai.expert.nlapi.v2.cloud.Analyzer;
import ai.expert.nlapi.v2.cloud.AnalyzerConfig;
import ai.expert.nlapi.v2.message.AnalyzeResponse;
import ai.expert.nlapi.v2.model.POSTag;
import ai.expert.nlapi.v2.model.Token;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class FullAnalysisParsingTest {

    public static Analyzer createAnalyzer(Authentication authentication) throws Exception {
        return new Analyzer(AnalyzerConfig.builder()
                                          .withVersion(API.Versions.V2)
                                          .withContext("standard")
                                          .withLanguage(API.Languages.en)
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
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPoSXCheck() {
        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create analyzer
            Analyzer analyzer = createAnalyzer(authentication);

            // Full Analysis
            AnalyzeResponse analysis = analyzer.analyze(TestUtils.readFile("src/test/resources/test_files/PoS_X_check.txt",
                                                                           StandardCharsets.UTF_8));
            analysis.prettyPrint();

            // assert there is the data passed as input
            assertNotNull(analysis.getData());
            assertNotNull(analysis.getData().getContent());
            assertSame(analysis.getData().getLanguage(), API.Languages.en);

            // assert there are all nl expert ai information
            assertNotNull(analysis.getData().getTokens());

            /* check if token with POSTag.X exists
             * {
				"dependency": {
					"head": 19,
					"id": 21,
					"label": "conj"
				},
				"end": 130,
				"lemma": "Audible.com",
				"paragraph": 0,
				"phrase": 14,
				"pos": "X",
				"sentence": 1,
				"start": 119,
				"syncon": -1,
				"type": "NOU.WEB",
				"vsyn": {
					"id": -436167,
					"parent": 92539
				}
			*/

            boolean found_POSTag_X = false;
            for(Token t : analysis.getData().getTokens()) {
                if(t.getPos() != null && t.getPos() == POSTag.X) {
                    found_POSTag_X = true;
                    break;
                }
            }
            assertTrue(found_POSTag_X);

        }
        catch(Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
