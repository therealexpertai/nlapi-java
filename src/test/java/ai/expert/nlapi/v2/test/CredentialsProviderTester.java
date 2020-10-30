package ai.expert.nlapi.v2.test;

import ai.expert.nlapi.security.*;
import ai.expert.nlapi.utils.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CredentialsProviderTester {

    @Before
    public void setup() {
    }

    @Test
    public void testSystemPropertyCredentialsProvider() {

        System.out.println("making system property CredentialsProvider test...");
        if(!TestUtils.setPropertyFile("src/test/resources/myProperties.txt")) {
            System.out.println("System property file not loaded!");
            return;
        }

        SystemPropertyCredentialsProvider propCredProvider = new SystemPropertyCredentialsProvider();

        Credential credential = propCredProvider.getCredentials();

        if(credential == null) {
            System.out.println("KO. System property credentials are not set");
        }
        else if(!credential.isValid()) {
            if(StringUtils.isBlank(credential.getUsername())) {
                System.out.println("Not set System Property key: " + SecurityUtils.USER_ACCESS_KEY_PROP);
            }
            if(StringUtils.isBlank(credential.getPassword())) {
                System.out.println("Not set System Property key: " + SecurityUtils.USER_ACCESS_KEY_PROP);
            }
        }
        else {
            System.out.println("OK! System property credentials are set");
        }

        assertTrue(true);
    }

    @Test
    public void testEnvironmentVariablesCredentialsProvider() {

        System.out.println("making environment variables CredentialsProvider test...");

        EnvironmentVariablesCredentialsProvider propCredProvider = new EnvironmentVariablesCredentialsProvider();

        Credential credential = propCredProvider.getCredentials();

        if(credential == null) {
            System.out.println("KO. Environment variables credentials are not set");
        }
        else if(!credential.isValid()) {
            if(StringUtils.isBlank(credential.getUsername())) {
                System.out.println("Not set Environment Variable key: " + SecurityUtils.USER_ACCESS_KEY_ENV);
            }
            if(StringUtils.isBlank(credential.getPassword())) {
                System.out.println("Not set Environment Variable key: " + SecurityUtils.USER_ACCESS_KEY_ENV);
            }
        }
        else {
            System.out.println("OK! Environment variables credentials are set");
        }

        assertTrue(true);
    }

    @Test
    public void testDefaultCredentialsProvider() {

        System.out.println("making deafult CredentialsProvider with provider chain test...");

        TestUtils.setPropertyFile("src/test/resources/myProperties.txt");
        DefaultCredentialsProvider propCredProvider = new DefaultCredentialsProvider();
        Credential credential = propCredProvider.getCredentials();

        if(credential == null) {
            System.out.println("KO. No credentials are not set");
            assertTrue(false);
        }
        else {
            System.out.println("OK! Credentials are set");
        }

        assertTrue(true);
    }
}
