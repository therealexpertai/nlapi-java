# expert.ai Natural Language API for Java v2

Java client for the expert.ai Natural Language APIs adds Natural Language understanding capabilities to your Java apps.
The client can use either the Cloud based [Natural Language API](https://docs.expert.ai/nlapi/latest/) or a local instance of [Edge NL API](https://docs.expert.ai/edgenlapi/latest/).

Check out what expert.ai Natural Language API can do for your application by [our live demo](https://try.expert.ai/).
Natural Language API provides a comprehensive set of natural language understanding capabilities based on expert.ai technology:

* Document analysis:
    * Deep linguistic analysis:
        * Text subdivision
        * Part-of-speech tagging
        * Syntactic analysis
        * Lemmatization
        * Keyphrase extraction
        * Semantic analysis
    * Named entity recognition
    * Relation extraction
    * Sentiment analysis
* Document classification

## What you'll need

* About 15 minutes
* A favorite text editor or IDE
* Java JDK version 8 or higher
* [Gradle](https://gradle.org/install/) installed

## Build from source


```bash
git clone git@github.com:therealexpertai/nlapi-java.git
cd nlapi-java
./gradlew build    
```

##  Generate a distribution from source


```bash
git clone git@github.com:therealexpertai/nlapi-java.git
cd nlapi-java
./gradlew distZip    
```


## Setting your credentials

This Java Client checks your expert.ai credentials using a chain of credential providers.

The default chain checks in order the following:
 
 * your Environment Variables set on the machine with keys **EAI_USERNAME** and **EAI_PASSWORD**
 * the [System Properties](https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html), 
 which can be set on a System Properties file (e.g. myProperties.txt) using keys **eai.username** and **eai.password**


## Usage examples


Here are some examples of how to use the library in order to leverage the Natural Language API:


### Document Analisys


You can get the result of the document analysis applied to your text as follows:

##### Natural Language API:

```java

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.cloud.Analyzer;
import ai.expert.nlapi.v2.cloud.AnalyzerConfig;
import ai.expert.nlapi.v2.message.AnalyzeResponse;

public class AnalisysTest {

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
    	DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
        Authenticator authenticator = new BasicAuthenticator(credentialsProvider);
        return new Authentication(authenticator);
    }

    //Method for selecting the resource to be call by the API; 
    //as today, the API provides the standard context only, and  
    //five languages such as English, French, Spanish, German and Italian
    public static Analyzer createAnalyzer() throws Exception {
        return new Analyzer(AnalyzerConfig.builder()
            .withVersion(API.Versions.V2)
            .withContext("standard")
            .withLanguage(API.Languages.en)
            .withAuthentication(createAuthentication())
            .build());
    }

    public static void main(String[] args) {
        try {
            Analyzer analyzer = createAnalyzer();
            AnalyzeResponse response = null;
            
            // Disambiguation Analisys
            response = analyzer.disambiguation(getSampleText());
            response.prettyPrint();

            // Relevants Analisys
            response = analyzer.relevants(getSampleText());
            response.prettyPrint();

            // Entities Analisys
            response = analyzer.entities(getSampleText());
            response.prettyPrint();

            // Relations Analisys
            response = analyzer.relations(getSampleText());
            response.prettyPrint();

            // Sentiment Analisys
            response = analyzer.sentiment(getSampleText());
            response.prettyPrint();
            
            // Full Analisys
            response = analyzer.analyze(getSampleText());
            response.prettyPrint();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

```

##### Edge NL API:

```java

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.edge.Analyzer;
import ai.expert.nlapi.v2.edge.AnalyzerConfig;
import ai.expert.nlapi.v2.message.AnalyzeResponse;

public class AnalisysTest {

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
    	DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
        Authenticator authenticator = new BasicAuthenticator(credentialsProvider);
        return new Authentication(authenticator);
    }

    //Method for selecting the resource to be call by the API; 
    //as today, the API provides the standard context only, and  
    //five languages such as English, French, Spanish, German and Italian
    public static Analyzer createAnalyzer() throws Exception {
        return new Analyzer(AnalyzerConfig.builder()
            .withVersion(API.Versions.V2)
            .withHost(API.DEFAULT_EDGE_HOST)
            .withAuthentication(createAuthentication())
            .build());
    }

    public static void main(String[] args) {
        try {
            Analyzer analyzer = createAnalyzer();
            AnalyzeResponse response = null;
            
            // Disambiguation Analisys
            response = analyzer.disambiguation(getSampleText());
            response.prettyPrint();

            // Relevants Analisys
            response = analyzer.relevants(getSampleText());
            response.prettyPrint();

            // Entities Analisys
            response = analyzer.entities(getSampleText());
            response.prettyPrint();

            // Relations Analisys
            response = analyzer.relations(getSampleText());
            response.prettyPrint();

            // Sentiment Analisys
            response = analyzer.sentiment(getSampleText());
            response.prettyPrint();
            
            // Full Analisys
            response = analyzer.analyze(getSampleText());
            response.prettyPrint();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

```


The API document analysis resources operate within a [context](https://docs.expert.ai/nlapi/v2/guide/contexts-and-kg/). For retrieving the list of all valid contexts use this code:

```java

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.cloud.Analyzer;
import ai.expert.nlapi.v2.cloud.AnalyzerConfig;

public class ContextsTest {


    //Method for setting the authentication credentials - set your credentials here.
    public static Authentication createAuthentication() throws Exception {
    	DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
        Authenticator authenticator = new BasicAuthenticator(credentialsProvider);
        return new Authentication(authenticator);
    }



    public static void main(String[] args) {
        try {
            // create InfoAPI
        	InfoAPI infoAPI = new InfoAPI(InfoAPIConfig.builder()
                .withAuthentication(createAuthentication())
                .withVersion(API.Versions.V2)
                .build());
    	
	    	
	        Contexts contexts = infoAPI.getContexts();
	        contexts.prettyPrint();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

```

### Document Classification


You can also run document classification with respect to the [IPTC Media Topic taxonomy](https://iptc.org/standards/media-topics/)

```java

package ai.expert.nlapi.v2.test;

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.cloud.Categorizer;
import ai.expert.nlapi.v2.cloud.CategorizerConfig;
import ai.expert.nlapi.v2.message.CategorizeResponse;

public class CategorizationIPTCTest {

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
    	DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
        Authenticator authenticator = new BasicAuthenticator(credentialsProvider);
        return new Authentication(authenticator);
    }
    
    //Method for selecting the resource to be call by the API; 
    //as today, the API provides the IPTC classifier only, and 
    //five languages such as English, French, Spanish, German and Italian
    public static Categorizer createCategorizer() throws Exception {
        return new Categorizer(CategorizerConfig.builder()
            .withVersion(API.Versions.V2)
            .withTaxonomy("iptc")
            .withLanguage(API.Languages.en)
            .withAuthentication(createAuthentication())
            .build());
    }

    public static void main(String[] args) {
        try {
            Categorizer categorizer = createCategorizer();
            
            //Perform the IPTC classification and store it into a Response Object
            CategorizeResponse categorization = categorizer.categorize(getSampleText());
            categorization.prettyPrint();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

```

or with respect to the [GeoTax taxonomy](https://docs.expert.ai/nlapi/v2/guide/taxonomies/#geotax-taxonomy):

```java

package ai.expert.nlapi.v2.test;

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.cloud.Categorizer;
import ai.expert.nlapi.v2.cloud.CategorizerConfig;
import ai.expert.nlapi.v2.message.CategorizeResponse;

public class CategorizationGeoTAXTest {

    static StringBuilder sb = new StringBuilder();
    
    // Sample text to be analyzed
    static {
    	// set text to be analyzed using GeoTAX taxonomy
        sb.append("Rome is the capital city and a special comune of Italy as well as the capital of the Lazio region. ");
        sb.append("The city has been a major human settlement for almost three millennia. ");
        sb.append("It is the third most populous city in the European Union by population within city limits.");
    }

    public static String getSampleText() {
        return sb.toString();
    }

    //Method for setting the authentication credentials - set your credentials here.
    public static Authentication createAuthentication() throws Exception {
    	DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
        Authenticator authenticator = new BasicAuthenticator(credentialsProvider);
        return new Authentication(authenticator);
    }
    
    //Method for selecting the resource to be call by the API; 
    //as today, the API provides the GeoTAX classifier only, and 
    //five languages such as English, French, Spanish, German and Italian
    public static Categorizer createCategorizer() throws Exception {
        return new Categorizer(CategorizerConfig.builder()
            .withVersion(API.Versions.V2)
            .withTaxonomy("geotax")
            .withLanguage(API.Languages.en)
            .withAuthentication(createAuthentication())
            .build());
    }

    public static void main(String[] args) {
        try {
            Categorizer categorizer = createCategorizer();
            
            //Perform the GeoTAX classification and store it into a Response Object
            CategorizeResponse categorization = categorizer.categorize(getSampleText());
            categorization.prettyPrint();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

```


For retrieving the list of all categories of a taxonomy for a specific language follow this example:

#### GeoTax categories for English

```java

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.cloud.Analyzer;
import ai.expert.nlapi.v2.cloud.AnalyzerConfig;

public class TaxonomiesTest {


    //Method for setting the authentication credentials - set your credentials here.
    public static Authentication createAuthentication() throws Exception {
    	DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
        Authenticator authenticator = new BasicAuthenticator(credentialsProvider);
        return new Authentication(authenticator);
    }



    public static void main(String[] args) {
        try {
            // create InfoAPI
        	InfoAPI infoAPI = new InfoAPI(InfoAPIConfig.builder()
                .withAuthentication(createAuthentication())
                .withVersion(API.Versions.V2)
                .build());
    	
	    	
	        TaxonomyResponse taxonomy = infoAPI.getTaxonomy("geotax", API.Languages.en);
        	taxonomy.prettyPrint();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

```


For retrieving the list of all valid taxonomies use this code:

```java

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.security.Authenticator;
import ai.expert.nlapi.security.BasicAuthenticator;
import ai.expert.nlapi.security.Credential;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.cloud.Analyzer;
import ai.expert.nlapi.v2.cloud.AnalyzerConfig;

public class TaxonomiesTest {


    //Method for setting the authentication credentials - set your credentials here.
    public static Authentication createAuthentication() throws Exception {
    	DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
        Authenticator authenticator = new BasicAuthenticator(credentialsProvider);
        return new Authentication(authenticator);
    }



    public static void main(String[] args) {
        try {
            // create InfoAPI
        	InfoAPI infoAPI = new InfoAPI(InfoAPIConfig.builder()
                .withAuthentication(createAuthentication())
                .withVersion(API.Versions.V2)
                .build());
    	
	    	
	        Taxonomies taxonomies = infoAPI.getTaxonomies();
        	taxonomies.prettyPrint();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}

```

## API capabilities

The API analysis and classification capabilities are listed below:

### Document Analysis


* [Deep linguistic analysis](https://docs.expert.ai/nlapi/v2/reference/output/linguistic-analysis/)	
* [Keyphrase extraction](https://docs.expert.ai/nlapi/v2/reference/output/keyphrase-extraction/)	
* [Named entities recognition](https://docs.expert.ai/nlapi/v2/reference/output/entity-recognition/)
* [Full document analysis](https://docs.expert.ai/nlapi/v2/reference/output/full-analysis/)


### Document Classification


* [IPTC Media Topics and GeoTax classification](https://docs.expert.ai/nlapi/v2/reference/output/classification/)

## Notes

The project makes use of [Lombok Project](https://projectlombok.org/). 

Project Lombok is a Java library that automatically plugs into your editor. 
In case you use Jetbrains IntelliJ IDEA editor see this link [https://projectlombok.org/setup/intellij](https://projectlombok.org/setup/intellij). 
For Eclipse editor check this link [https://projectlombok.org/setup/eclipse](https://projectlombok.org/setup/eclipse) for installing Lombok Project.
