# nlapi-java

Java SDK for the [expert.ai](https://developer.expert.ai/) Natural Language API.

See for yourself what expert.ai’s Natural Language API can do for your application by [test-driving our demo](https://try.expert.ai/).

## Build From Source

1. `git clone git@github.com:therealexpertai/nlapi-java.git`
2. `cd nlapi-java`
3. `./gradlew build` (Windows: `gradlew.bat build`)

## Usage

### Analisys

```java

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v1.API;
import ai.expert.nlapi.v1.Analyzer;
import ai.expert.nlapi.v1.AnalyzerConfig;

public class AnalisysTest {

    static StringBuilder sb = new StringBuilder();

    static {
        sb.append("Michael Jordan was one of the best basketball players of all time.");
        sb.append("Scoring was Jordan's stand-out skill, but he still holds a defensive NBA record, with eight steals in a half.");
    }

    public static String getSampleText() {
        return sb.toString();
    }

    public static Authentication createAuthentication() throws Exception {
        Authenticator authenticator = new BasicAuthenticator(new Credential("USERNAME", "PASSWORD"));
        return new Authentication(authenticator);
    }

    public static Analyzer createAnalyzer() throws Exception {
        return new Analyzer(AnalyzerConfig.builder()
                                          .withVersion(API.Versions.V1)
                                          .withContext(API.Contexts.STANDARD)
                                          .withLanguage(API.Languages.en)
                                          .withAuthentication(createAuthentication())
                                          .build());
    }

    public static void main(String[] args) {
        try {
            Analyzer analyzer = createAnalyzer();

            // Full Analisys
            analyzer.analyze(getSampleText()).prettyPrint();

            // Disambiguation Analisys
            analyzer.disambiguation(getSampleText()).prettyPrint();

            // Relevants Analisys
            analyzer.relevants(getSampleText()).prettyPrint();

            // Entities Analisys
            analyzer.entities(getSampleText()).prettyPrint();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

```

### Categorization

```java

package ai.expert.nlapi.v1.test;

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v1.API;
import ai.expert.nlapi.v1.Categorizer;
import ai.expert.nlapi.v1.CategorizerConfig;
import ai.expert.nlapi.v1.message.ResponseDocument;

public class CategorizationTest {

    static StringBuilder sb = new StringBuilder();

    static {
        sb.append("Michael Jordan was one of the best basketball players of all time.");
        sb.append("Scoring was Jordan's stand-out skill, but he still holds a defensive NBA record, with eight steals in a half.");
    }

    public static String getSampleText() {
        return sb.toString();
    }

    public static Authentication createAuthentication() throws Exception {
        Authenticator authenticator = new BasicAuthenticator(new Credential("USERNAME", "PASSWORD"));
        return new Authentication(authenticator);
    }

    public static Categorizer createCategorizer() throws Exception {
        return new Categorizer(CategorizerConfig.builder()
                                                .withVersion(API.Versions.V1)
                                                .withTaxonomy(API.Taxonomies.IPTC)
                                                .withLanguage(API.Languages.en)
                                                .withAuthentication(createAuthentication())
                                                .build());
    }

    public static void main(String[] args) {
        try {
            Categorizer categorizer = createCategorizer();
            ResponseDocument categorization = categorizer.categorize(getSampleText());
            categorization.prettyPrint();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

```
## Documentation

Checkout our [Live Docs](https://docs.expert.ai/)!
