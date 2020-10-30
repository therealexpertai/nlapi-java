package ai.expert.nlapi.v2.test;

import ai.expert.nlapi.v2.model.Sentiment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class ParsingTest {
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        // Don't include properties with null value in JSON output
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Use default pretty printer
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Test
    public void testSentiment() {
        try {
            String pathJsonFile = "src/test/resources/test_files/sentiment.json";
            String json = TestUtils.readFile(pathJsonFile, StandardCharsets.UTF_8);
            System.out.println("Sentiment JSON:\n" + json);


            Sentiment value = mapper.readValue(json, Sentiment.class);
            assertThat(value, instanceOf(Sentiment.class));
            assertEquals(-15.0, value.getOverall().doubleValue(), 0);

            System.out.println("Sentiment JSON items size first level: " + value.getItems().size());
            System.out.println("Parsed JSON:\n" + mapper.writeValueAsString(value));
        }
        catch(Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
