package ai.expert.nlapi.v2.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
                CategorizationTest.class,
                FullAnalysisTest.class,
                DisambiguationTest.class,
                RelevantsTest.class,
                EntitiesTest.class,
                EntitiesTest.class,
                SentimentTest.class,
                RelationsTest.class,
                FullAnalysisParsingTest.class
              })
public class ReleaseTest {
}
