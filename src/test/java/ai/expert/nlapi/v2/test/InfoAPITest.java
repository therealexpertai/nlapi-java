package ai.expert.nlapi.v2.test;

import ai.expert.nlapi.exceptions.NLApiException;
import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.cloud.InfoAPI;
import ai.expert.nlapi.v2.cloud.InfoAPIConfig;
import ai.expert.nlapi.v2.message.ContextsResponse;
import ai.expert.nlapi.v2.message.TaxonomiesResponse;
import ai.expert.nlapi.v2.message.TaxonomyResponse;
import ai.expert.nlapi.v2.model.ContextInfo;
import ai.expert.nlapi.v2.model.TaxonomyInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;


public class InfoAPITest {
    private ObjectMapper mapper;

    public static InfoAPI createInfoAPI(Authentication authentication) throws Exception {
        return new InfoAPI(InfoAPIConfig.builder()
                                        .withAuthentication(authentication)
                                        .withVersion(API.Versions.V2)
                                        .build());
    }

    @Before
    public void setup() throws Exception {
        mapper = new ObjectMapper();
        // Don't include properties with null value in JSON output
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Use default pretty printer
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Test
    public void testGetContexts() {

        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create InfoAPI
            InfoAPI infoAPI = createInfoAPI(authentication);

            ContextsResponse contexts = infoAPI.getContexts();
            contexts.prettyPrint();
            assertThat(contexts, instanceOf(ContextsResponse.class));

            // check existing contexts
            ContextInfo standard = contexts.getContextByName("standard");
            System.out.println("Found ContextInfo:\n" + mapper.writeValueAsString(standard));

            try {
                // check not existing contexts should throw an exception
                ContextInfo none = contexts.getContextByName("none");
                System.out.println("Found ContextInfo:\n" + mapper.writeValueAsString(none));
                fail();
            }
            catch(NLApiException e) {
                assertTrue(true);
            }

            assertTrue(true);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetTaxonomies() {

        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create InfoAPI
            InfoAPI infoAPI = createInfoAPI(authentication);

            TaxonomiesResponse taxonomies = infoAPI.getTaxonomies();
            taxonomies.prettyPrint();
            assertThat(taxonomies, instanceOf(TaxonomiesResponse.class));

            // check existing taxonomies
            TaxonomyInfo iptc = taxonomies.getTaxonomyByName("iptc");
            System.out.println("Found taxonomy:\n" + mapper.writeValueAsString(iptc));
            TaxonomyInfo geotax = taxonomies.getTaxonomyByName("geotax");
            System.out.println("Found taxonomy:\n" + mapper.writeValueAsString(geotax));

            try {
                // check not existing taxonomies should throw an exception
                TaxonomyInfo none = taxonomies.getTaxonomyByName("none");
                System.out.println("Found taxonomy:\n" + mapper.writeValueAsString(none));
                fail();
            }
            catch(NLApiException e) {
                assertTrue(true);
            }

            assertTrue(true);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetTaxonomy() {

        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create InfoAPI
            InfoAPI infoAPI = createInfoAPI(authentication);

            TaxonomyResponse taxonomy = infoAPI.getTaxonomy("geotax", API.Languages.en);
            taxonomy.prettyPrint();
            assertThat(taxonomy, instanceOf(TaxonomyResponse.class));

            assertTrue(true);
        }
        catch(Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
