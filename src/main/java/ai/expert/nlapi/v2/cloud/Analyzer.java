/*
 * Copyright (c) 2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.expert.nlapi.v2.cloud;

import ai.expert.nlapi.exceptions.NLApiErrorCode;
import ai.expert.nlapi.exceptions.NLApiException;
import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.utils.APIUtils;
import ai.expert.nlapi.utils.ObjectMapperAdapter;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.message.AnalysisRequest;
import ai.expert.nlapi.v2.message.AnalyzeResponse;
import ai.expert.nlapi.v2.model.Document;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Analyzer {

    private static final Logger logger = LogManager.getLogger();

    private boolean isLanguageSet = false;
    private final Authentication authentication;
    private final AnalyzerConfig configuration;
    private String URL;

    public Analyzer(AnalyzerConfig config) {

        authentication = config.getAuthentication();
        configuration = config;
        if (config.getLanguage() != null) {
            URL = String.format("%s/%s/analyze/%s/%s",
                                API.AUTHORITY, config.getVersion(), config.getContext().value().toLowerCase(), config.getLanguage().code());
            this.isLanguageSet = true;
        } else {
            URL = String.format("%s/%s/analyze/%s/",
                                API.AUTHORITY, config.getVersion(), config.getContext().value().toLowerCase());
        }

        Unirest.config()
               .addDefaultHeader("Content-Type", "application/json")
               .addDefaultHeader("Accept", "application/json")
               .setObjectMapper(new ObjectMapperAdapter());
    }

    public AnalyzeResponse analyze(String text, String analysisType) throws NLApiException {
        return getResponseDocument(text, analysisType, API.Languages.notKnown);
    }

    public AnalyzeResponse analyze(String text, String analysisType, API.Languages lang) throws NLApiException {
        return getResponseDocument(text, analysisType, lang);
    }


    public AnalyzeResponse analyze(String text) throws NLApiException {
        return getResponseDocument(text, null, API.Languages.notKnown);
    }

    public AnalyzeResponse full(String text) throws NLApiException {
        return getResponseDocument(text, null, API.Languages.notKnown);
    }

    public AnalyzeResponse full(String text, API.Languages lang) throws NLApiException {
        return getResponseDocument(text, null, lang);
    }

    public AnalyzeResponse disambiguation(String text) throws NLApiException {
        return getResponseDocument(text, "disambiguation", API.Languages.notKnown);
    }

    public AnalyzeResponse disambiguation(String text, API.Languages lang) throws NLApiException {
        return getResponseDocument(text, "disambiguation", lang);
    }

    public AnalyzeResponse relevants(String text) throws NLApiException {
        return getResponseDocument(text, "relevants", API.Languages.notKnown);
    }

    public AnalyzeResponse relevants(String text, API.Languages lang) throws NLApiException {
        return getResponseDocument(text, "relevants", lang);
    }

    public AnalyzeResponse entities(String text) throws NLApiException {
        return getResponseDocument(text, "entities", API.Languages.notKnown);
    }

    public AnalyzeResponse entities(String text, API.Languages lang) throws NLApiException {
        return getResponseDocument(text, "entities", lang);
    }

    public AnalyzeResponse relations(String text) throws NLApiException {
        return getResponseDocument(text, "relations", API.Languages.notKnown);
    }

    public AnalyzeResponse relations(String text, API.Languages lang) throws NLApiException {
        return getResponseDocument(text, "relations", lang);
    }

    public AnalyzeResponse sentiment(String text) throws NLApiException {
        return getResponseDocument(text, "sentiment", API.Languages.notKnown);
    }

    public AnalyzeResponse sentiment(String text, API.Languages lang) throws NLApiException {
        return getResponseDocument(text, "sentiment", lang);
    }

    private AnalyzeResponse getResponseDocument(String text, String analysisType, API.Languages lang) throws NLApiException {

        // get json reply from expert.ai API
        String json = getResponseDocumentString(text, analysisType, lang);

        // parsing and checking response
        AnalyzeResponse response = APIUtils.fromJSON(json, AnalyzeResponse.class);
        if(response.isSuccess()) {
            return response;
        }

        String msg = String.format("Analyze call to API %s return an error json: %s", URL, json);
        logger.error(msg);
        throw new NLApiException(NLApiErrorCode.EXECUTION_REQUEST_ERROR, msg);
    }


    public String getResponseDocumentString(String text, String analysisType, API.Languages lang) throws NLApiException {
        String URLpath;
        if (lang != API.Languages.notKnown) {
            URLpath = String.format("%s/%s/analyze/%s/%s",
                          API.AUTHORITY, configuration.getVersion(),
                          configuration.getContext().value().toLowerCase(), lang.code());
        } else if (configuration.getLanguage() != null) {
            URLpath = String.format("%s/%s/analyze/%s/%s",
                          API.AUTHORITY, configuration.getVersion(),
                          configuration.getContext().value().toLowerCase(), configuration.getLanguage().code());
        } else {
            logger.info("No language detected, sending request with english language");
            URLpath = String.format("%s/%s/analyze/%s/%s",
                                    API.AUTHORITY, configuration.getVersion(),
                                    configuration.getContext().value().toLowerCase(), API.Languages.en.code());
        }

        // if analysisType is defined and different from "full" enable specific analysis type, e.g. "entities"
        if(analysisType != null && !analysisType.isEmpty() && !analysisType.equalsIgnoreCase("full")) {
            URLpath = URL + "/" + analysisType.toLowerCase();
        }
        logger.debug("Sending text to analyze API: " + URLpath);
        HttpResponse<String> response = Unirest.post(URLpath)
                                               .header("Authorization", APIUtils.getBearerToken(authentication))
                                               .body(new AnalysisRequest(Document.of(text)).toJSON())
                                               .asString();
        
        /*
         '401':
          description: Unauthorized
        '403':
          description: Forbidden
        '404':
          description: Not Found
        '413':
          description: Request Entity Too Large
        '500':
          description: Internal Server Error
        */

        if(response.getStatus() != 200) {
            String msg = String.format("Analyze call to API %s return error status %d", URLpath, response.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.CONNECTION_ERROR, msg);
        }

        String type = (analysisType != null && !analysisType.isEmpty()) ? analysisType : "full";
        logger.info(String.format("Analyze %s call successful", type));

        return response.getBody();
    }
}
