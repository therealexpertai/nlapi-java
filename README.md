# expert.ai Natural Language API for Java

Java Client for the [expert.ai](https://developer.expert.ai/) Natural Language API. Leverage Natural Language technology from your Java apps.

Check out what expert.ai’s Natural Language API can do for your application by [our live demo](https://try.expert.ai/).


## Build From Source


```bash
git clone git@github.com:therealexpertai/nlapi-java.git
cd nlapi-java
./gradlew build    
```


## Usage examples


Here are some examples of how to use the library in order to leverage the Natural Language API:


### Document Analysis


You can get the result of the deep linguistic analysis applied to your text as follows

```java

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v1.API;
import ai.expert.nlapi.v1.Analyzer;
import ai.expert.nlapi.v1.AnalyzerConfig;

public class AnalysisTest {

    static StringBuilder sb = new StringBuilder();

    // Sample text to be analyzed
    static {
        sb.append("Michael Jordan was one of the best basketball players of all time.");
        sb.append("Scoring was Jordan's stand-out skill, but he still holds a defensive NBA record, with eight steals in a half.");  
    }

    public static String getSampleText() {
        return sb.toString();
    }
    
    //Method for setting the authentication credentials - set your credentials here.
    public static Authentication createAuthentication() throws Exception {
        Authenticator authenticator = new BasicAuthenticator(new Credential("PUT HERE YOUR USERNAME", " PUT HERE YOUR PASSWORD"));
        return new Authentication(authenticator);
    }

    //Method for selecting the resource to be call by the API; as today, the API provides the standard context only, and  
    //five languages such as English, French, Spanish, German and Italian
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
            ResponseDocument response = null;
            
            // Disambiguation Analysis
            response = analyzer.disambiguation(getSampleText());
            response.prettyPrint();

            // Relevants Analysis
            response = analyzer.relevants(getSampleText());
            response.prettyPrint();

            // Entities Analysis
            response = analyzer.entities(getSampleText());
            response.prettyPrint();
            
            // Full Analysis
            response = analyzer.analyze(getSampleText());
            response.prettyPrint();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

```


### Document Classification


or to run a document classification with respect to the [IPTC Media Topic taxonomy](https://iptc.org/standards/media-topics/)

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
    
    // Sample text to be analyzed
    static {
        sb.append("Michael Jordan was one of the best basketball players of all time.");
        sb.append("Scoring was Jordan's stand-out skill, but he still holds a defensive NBA record, with eight steals in a half.");  
    }

    public static String getSampleText() {
        return sb.toString();
    }

    //Method for setting the authentication credentials - set your credentials here.
    public static Authentication createAuthentication() throws Exception {
        Authenticator authenticator = new BasicAuthenticator(new Credential("PUT HERE YOUR USERNAME", " PUT HERE YOUR PASSWORD"));
        return new Authentication(authenticator);
    }
    
    //Method for selecting the resource to be call by the API; as today, the API provides the IPTC classifier only, and 
    //five languages such as English, French, Spanish, German and Italian
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
            
            //Perform the IPTC classification and store it into a Response Object
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

The ResponseDocument class provides an object-based representation of the API JSON response as described on the [documentation portal](https://docs.expert.ai/nlapi/v1/):
* [disambiguation Response](https://docs.expert.ai/nlapi/v1/reference/output/linguistic-analysis/) 
* [relevants Response](https://docs.expert.ai/nlapi/v1/reference/output/keyphrase-extraction/)
* [entities Response](https://docs.expert.ai/nlapi/v1/guide/entity-recognition/)
* [full analysis Response](https://docs.expert.ai/nlapi/v1/reference/output/full-analysis/)
* [classification Response](https://docs.expert.ai/nlapi/v1/reference/output/classification/)
