package ai.expert.nlapi.v2.test;

import ai.expert.nlapi.security.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestUtils {

    public static final int WAIT_CALL_RATE = 3;
    public static final int WAIT_SECONDS = 3;
    public static Authentication authentication = null;
    public static int testcalls = 0;

    public static void callRateCheck() {
        if(testcalls++ % WAIT_CALL_RATE != 0) {return;}
        try {
            TimeUnit.SECONDS.sleep(WAIT_SECONDS);
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String path, Charset encoding)
      throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static boolean setPropertyFile(String pathMyProperties) {
        // set up new properties object from file pathMyProperties
        Properties p = null;
        try {
            File file = new File(pathMyProperties);
            String absolutePathMyProperties = file.getAbsolutePath();
            System.out.println("System property file: " + absolutePathMyProperties);
            FileInputStream propFile = new FileInputStream(absolutePathMyProperties);
            p = new Properties(System.getProperties());
            p.load(propFile);
        }
        catch(Exception e) {
            System.out.println("WARN: problems loading \"" + pathMyProperties + "\" property file. Not using System Properties.");
            return false;
        }

        System.setProperties(p);
        return true;
    }

    public static Authentication getAuthentication() throws Exception {
        if(authentication == null) {
            authentication = createAuthentication();
        }
        return authentication;
    }

    public static Authentication createAuthentication() throws Exception {
        DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
        Authenticator authenticator = new BasicAuthenticator(credentialsProvider);
        return new Authentication(authenticator);
    }

    public static Authentication createFakeAuthentication() throws Exception {
        Authenticator authenticator = new BasicAuthenticator(new Credential("FAKE", "FAKE"));
        return new Authentication(authenticator);
    }
}
