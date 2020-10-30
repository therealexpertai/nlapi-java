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
import ai.expert.nlapi.v2.Analyzer;
import ai.expert.nlapi.v2.AnalyzerConfig;
import ai.expert.nlapi.v2.message.AnalyzeResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SentimentTest {

    static StringBuilder sb = new StringBuilder();

    public static String getSampleText() {
        return sb.toString();
    }

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

            // set text to be analyzed
            sb.append("Michael Jordan was one of the best basketball players of all time.");
            sb.append("Scoring was Jordan's stand-out skill, but he still holds a defensive NBA record, with eight steals in a half.");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSentimentAnalysis() {
        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create analyzer
            Analyzer analyzer = createAnalyzer(authentication);

            // Relevants Analysis
            AnalyzeResponse analysis = analyzer.sentiment(getSampleText());
            analysis.prettyPrint();

            // assert there is the data passed as input
            assertTrue(analysis.getData() != null);
            assertTrue(analysis.getData().getContent() != null);
            assertTrue(analysis.getData().getLanguage() == API.Languages.en);

            // assert there are all nl expert ai information
            assertTrue(analysis.getData().getKnowledge() != null);
            assertTrue(analysis.getData().getTopics() == null);

            assertTrue(analysis.getData().getMainLemmas() == null);
            assertTrue(analysis.getData().getMainSyncons() == null);
            assertTrue(analysis.getData().getMainPhrases() == null);
            assertTrue(analysis.getData().getMainSentences() == null);

            assertTrue(analysis.getData().getEntities() == null);

            assertTrue(analysis.getData().getParagraphs() == null);
            assertTrue(analysis.getData().getPhrases() == null);
            assertTrue(analysis.getData().getSentences() == null);
            assertTrue(analysis.getData().getTokens() == null);

            assertTrue(analysis.getData().getSentiment() != null);
            assertTrue(analysis.getData().getRelations() == null);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            assertTrue(false);
        }
    }
}
