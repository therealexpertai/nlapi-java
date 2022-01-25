package ai.expert.nlapi.v2.test;

import ai.expert.nlapi.exceptions.NLApiException;
import ai.expert.nlapi.security.Authentication;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

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
            System.out.println("OK! Authentication error: " + nle.getMessage());
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
                System.out.println("OK! Authentication error after " + i + " access token requests: " + nle.getMessage());
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

    @Test
    public void testIsJWTExpired() {

        System.out.println("Is JWT expired test...");

        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                          .withExpiresAt(new Date())
                          .withIssuer("auth0")
                          .sign(algorithm);

        boolean isExpired = false;

        try {
            Authentication authentication = TestUtils.createExpiredJWTAuthentication(token);
            authentication.refresh();
            isExpired = authentication.isExpired();
            System.out.println("is JWT expired: " + isExpired);

        }
        catch(NLApiException nle) {
            assertTrue(true);
        }
        catch(Exception e) {
            fail();
        }

        assertTrue(isExpired);
    }

    @Test
    public void testIsJWTNotExpired() {

        System.out.println("Is JWT not expired test...");

        boolean isExpired = false;

        try {
            Authentication authentication = TestUtils.createAuthentication();
            authentication.refresh();
            isExpired = authentication.isExpired();
            System.out.println("is JWT expired: " + isExpired);
        }
        catch(NLApiException nle) {
            assertTrue(true);
        }
        catch(Exception e) {
            fail();
        }
        assertFalse(isExpired);
    }

    @Test
    public void testIsValidWithExpiredJWT() {

        System.out.println("Is valid with JWT expired test...");

        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                          .withExpiresAt(new Date())
                          .withIssuer("auth0")
                          .sign(algorithm);

        boolean isValid = true;

        try {
            Authentication authentication = TestUtils.createExpiredJWTAuthentication(token);
            authentication.refresh();
            isValid = authentication.isValid();
            System.out.println("is JWT valid: " + isValid);

        }
        catch(NLApiException nle) {
            assertTrue(true);
        }
        catch(Exception e) {
            fail();
        }

        assertFalse(isValid);
    }
}
