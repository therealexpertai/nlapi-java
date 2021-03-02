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
import ai.expert.nlapi.v2.edge.Model;
import ai.expert.nlapi.v2.edge.ModelConfig;
import ai.expert.nlapi.v2.message.AnalyzeResponse;
import ai.expert.nlapi.v2.message.TaxonomyModelResponse;
import ai.expert.nlapi.v2.message.TemplatesModelResponse;
import ai.expert.nlapi.v2.model.Category;
import ai.expert.nlapi.v2.model.Extraction;
import ai.expert.nlapi.v2.model.Taxonomy;
import ai.expert.nlapi.v2.model.TemplateNamespace;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EdgeAnalysisTest {

    static StringBuilder sb = new StringBuilder();

    public static String getSampleText() {
        return sb.toString();
    }

    public static Analyzer createAnalyzer(Authentication authentication, String resource) throws Exception {
        return new Analyzer(AnalyzerConfig.builder()
                                          .withVersion(API.Versions.V2)
                                          .withHost(API.DEFAULT_EDGE_HOST)
                                          .withAuthentication(authentication)
                                          .withResource(resource)
                                          .build());
    }

    public static Analyzer createAnalyzer(Authentication authentication) throws Exception {
        return createAnalyzer(authentication, null);
     }

    public static Model createModel() throws Exception {
        return new Model(ModelConfig.builder()
                         .withVersion(API.Versions.V2)
                         .withHost(API.DEFAULT_EDGE_HOST)
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

    @Ignore
    //@Test
    public void testEdgeAnalysis() {
        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create analyzer
            Analyzer analyzer = createAnalyzer(authentication);
            /*
            Analyzer analyzer = new Analyzer(AnalyzerConfig.builder()
                                          .withVersion(API.Versions.V2)
                                          .withHost("http://127.0.0.1:6090")
                                          //.withResource("standard_en_15.0")
                                          .build());
            */

            // Full Analysis
            AnalyzeResponse analysis = analyzer.analyze(getSampleText());
            analysis.prettyPrint();

            // assert there is the data passed as input
            assertNotNull(analysis.getData());
            assertNotNull(analysis.getData().getContent());
            assertSame(analysis.getData().getLanguage(), API.Languages.en);

            // assert there are all nl expert ai information 
            assertNotNull(analysis.getData().getEntities());
            assertNotNull(analysis.getData().getTopics());
            assertNotNull(analysis.getData().getKnowledge());

            assertNotNull(analysis.getData().getMainLemmas());
            assertNotNull(analysis.getData().getMainSyncons());
            assertNotNull(analysis.getData().getMainPhrases());
            assertNotNull(analysis.getData().getMainSentences());

            assertNotNull(analysis.getData().getParagraphs());
            assertNotNull(analysis.getData().getPhrases());
            assertNotNull(analysis.getData().getSentences());
            assertNotNull(analysis.getData().getTokens());


            // categories and extractions
            // Full Analysis
            analysis = analyzer.classification(getSampleText());
            List<Category> categories = analysis.getData().getCategories();
            analysis = analyzer.extraction(getSampleText());
            List<Extraction> extractions = analysis.getData().getExtractions();

            Model model = createModel();
            TemplatesModelResponse templModelResponse = model.templates();
            List<TemplateNamespace> templates = templModelResponse.getData();

            TaxonomyModelResponse taxModelResponse = model.taxonomy();
            List<Taxonomy> taxonomies = taxModelResponse.getData();

        }
        catch(Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
