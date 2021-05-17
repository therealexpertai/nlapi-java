/*
 * Copyright (c) 2021 original authors
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

import java.util.Locale;

public class StandardAnalyzer {
    private static final Logger logger = LogManager.getLogger();

    private final String context = API.Contexts.STANDARD.value().toLowerCase(Locale.ROOT);
    private final Authentication authentication;
    private final AnalyzerConfig configuration;

    public StandardAnalyzer(AnalyzerConfig config) {
            configuration = config;
            authentication = config.getAuthentication();

            Unirest.config()
                   .addDefaultHeader("Content-Type", "application/json")
                   .addDefaultHeader("Accept", "application/json")
                   .setObjectMapper(new ObjectMapperAdapter());
    }

    public AnalyzeResponse analyze(String text, String analysisType) throws NLApiException {
        return getResponseDocument(text, analysisType, defaultLang());
    }

    public AnalyzeResponse analyze(String text, String analysisType, API.Languages language) throws NLApiException {
        return getResponseDocument(text, analysisType, language);
    }

    public AnalyzeResponse full(String text) throws NLApiException {
        return getResponseDocument(text, "full", defaultLang());
    }

    public AnalyzeResponse full(String text, API.Languages language) throws NLApiException {
        return getResponseDocument(text, "full", language);
    }

    public AnalyzeResponse disambiguation(String text) throws NLApiException {
        return getResponseDocument(text, "disambiguation", defaultLang());
    }

    public AnalyzeResponse disambiguation(String text, API.Languages language) throws NLApiException {
        return getResponseDocument(text, "disambiguation", language);
    }

    public AnalyzeResponse relevants(String text) throws NLApiException {
        return getResponseDocument(text, "relevants", defaultLang());
    }

    public AnalyzeResponse relevants(String text, API.Languages language) throws NLApiException {
        return getResponseDocument(text, "relevants", language);
    }

    public AnalyzeResponse entities(String text) throws NLApiException {
        return getResponseDocument(text, "entities", defaultLang());
    }

    public AnalyzeResponse entities(String text, API.Languages language) throws NLApiException {
        return getResponseDocument(text, "entities", language);
    }

    public AnalyzeResponse relations(String text) throws NLApiException {
        return getResponseDocument(text, "relations", defaultLang());
    }

    public AnalyzeResponse relations(String text, API.Languages language) throws NLApiException {
        return getResponseDocument(text, "relations", language);
    }

    private AnalyzeResponse getResponseDocument(String text, String analysisType, API.Languages langauge) throws NLApiException {
        String URL = String.format("%s/%s/analyze/%s/%s",
                                   API.AUTHORITY,
                                   configuration.getVersion(),
                                   context,
                                   langauge.code());

        // if analysisType is defined and different from "full" enable specific analysis type, e.g. "entities"
        if(analysisType != null && !analysisType.isEmpty() && !analysisType.equalsIgnoreCase("full")) {
            URL = URL + "/" + analysisType.toLowerCase();
        }

        // get json reply from expert.ai API
        String json = getResponseDocumentString(text, URL);

        // parsing and checking response
        AnalyzeResponse response = APIUtils.fromJSON(json, AnalyzeResponse.class);
        if(response.isSuccess()) {
            return response;
        }

        String msg = String.format("Analyze call to API %s return an error json: %s", URL, json);
        logger.error(msg);
        throw new NLApiException(NLApiErrorCode.EXECUTION_REQUEST_ERROR, msg);
    }

    public String getResponseDocumentString(String text, String url) throws NLApiException {
        logger.debug("Sending text to analyze API: " + url);
        HttpResponse<String> response = Unirest.post(url)
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
            String msg = String.format("Analyze call to API %s return error status %d", url, response.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.CONNECTION_ERROR, msg);
        }

        return response.getBody();
    }

    private API.Languages defaultLang() {
        return configuration.getLanguage() != null ? configuration.getLanguage() : API.Languages.en;
    }
}
