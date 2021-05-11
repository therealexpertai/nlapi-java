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
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntitiesTest {

    static StringBuilder sb = new StringBuilder();

    public static String getSampleText() {
        return sb.toString();
    }

    public static Analyzer createAnalyzer(Authentication authentication) throws Exception {
        return new Analyzer(AnalyzerConfig.builder()
                                          .withVersion(API.Versions.V2)
                                          .withContext(API.Contexts.STANDARD)
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
    public void testEntitiesAnalysis() {
        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create analyzer
            Analyzer analyzer = createAnalyzer(authentication);

            // Entities Analysis
            AnalyzeResponse analysis = analyzer.entities(getSampleText());
            analysis.prettyPrint();

            // assert there is the data passed as input
            assertNotNull(analysis.getData());
            assertNotNull(analysis.getData().getContent());
            assertSame(analysis.getData().getLanguage(), API.Languages.en);

            // assert there are all nl expert ai information
            assertNotNull(analysis.getData().getKnowledge());
            assertNotNull(analysis.getData().getEntities());

            assertNull(analysis.getData().getTopics());

            assertNull(analysis.getData().getMainLemmas());
            assertNull(analysis.getData().getMainSyncons());
            assertNull(analysis.getData().getMainPhrases());
            assertNull(analysis.getData().getMainSentences());

            assertNull(analysis.getData().getParagraphs());
            assertNull(analysis.getData().getPhrases());
            assertNull(analysis.getData().getSentences());
            assertNull(analysis.getData().getTokens());
        }
        catch(Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    public void testEntitiesAttributes() {
        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create analyzer
            Analyzer analyzer = createAnalyzer(authentication);

            // Entities Analysis
            AnalyzeResponse analysis = analyzer.entities("George Washington was the first President of the United States.");
            analysis.prettyPrint();

            // assert there is the data passed as input
            assertNotNull(analysis.getData());
            assertNotNull(analysis.getData().getContent());
            assertSame(analysis.getData().getLanguage(), API.Languages.en);

            // assert there are all nl expert ai information
            assertNotNull(analysis.getData().getKnowledge());
            assertNotNull(analysis.getData().getEntities());

            assertNull(analysis.getData().getTopics());

            assertNull(analysis.getData().getMainLemmas());
            assertNull(analysis.getData().getMainSyncons());
            assertNull(analysis.getData().getMainPhrases());
            assertNull(analysis.getData().getMainSentences());

            assertNull(analysis.getData().getParagraphs());
            assertNull(analysis.getData().getPhrases());
            assertNull(analysis.getData().getSentences());
            assertNull(analysis.getData().getTokens());
        }
        catch(Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
