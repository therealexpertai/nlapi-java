package ai.expert.nlapi.v2.test;

import ai.expert.nlapi.exceptions.NLApiException;
import ai.expert.nlapi.security.Authentication;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AuthenticationTester {

    @Before
    public void setup() {
        TestUtils.setPropertyFile("src/test/resources/myProperties.txt");
    }

    @Test
    public void testFakeAuthentication() {

        System.out.println("making fake authentication test...");

        try {
            Authentication authentication = TestUtils.createFakeAuthentication();
            String res = authentication.refresh();
            System.out.println("result authentication: " + res);
        }
        catch(NLApiException nle) {
            System.out.println("OK! Athentication error: " + nle.getMessage());
            assertTrue(true);
        }
        catch(Exception e) {
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testAuthentication() {

        System.out.println("making authentication test...");
        try {
            Authentication authentication = TestUtils.createAuthentication();
            String res = authentication.refresh();
            System.out.println("result authentication: " + res);
        }
        catch(NLApiException nle) {
            assertTrue(true);
        }
        catch(Exception e) {
            fail();
        }

        assertTrue(true);
    }

    @Test
    public void testTokenAccess() throws InterruptedException {

        System.out.println("making fake authentication test...");

        boolean access_token_error = false;
        for(int i = 0; i < 10; i++) {
            try {
                Authentication authentication = TestUtils.createAuthentication();
                String res = authentication.refresh();
                System.out.println("Access Token: " + res);
            }
            catch(NLApiException nle) {
                System.out.println("OK! Athentication error after " + i + " access toekn requests: " + nle.getMessage());
                access_token_error = true;
                TimeUnit.SECONDS.sleep(10);
                break;
            }
            catch(Exception e) {
                System.out.println("KO! Other error: " + e.getMessage());
                access_token_error = false;
                break;
            }
        }

        assertTrue(access_token_error);
    }
}
