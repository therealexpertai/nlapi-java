/*
 * Copyright (c) 2020 original authors
 *
 * Licensed under the Apache License, Versions 2.0 (the "License");
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

package ai.expert.nlapi.v2;

import ai.expert.nlapi.exceptions.NLApiErrorCode;
import ai.expert.nlapi.exceptions.NLApiException;
import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.utils.APIUtils;
import ai.expert.nlapi.utils.ObjectMapperAdapter;
import ai.expert.nlapi.v2.message.AnalysisRequest;
import ai.expert.nlapi.v2.message.CategorizeResponse;
import ai.expert.nlapi.v2.model.Document;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Categorizer {

    private static final Logger logger = LogManager.getLogger();

    private final Authentication authentication;
    private final String URL;

    public Categorizer(CategorizerConfig config) {

        authentication = config.getAuthentication();

        URL = String.format("%s/%s/categorize/%s/%s",
                            API.AUTHORITY, config.getVersion(), config.getTaxonomy().toLowerCase(), config.getLanguage().code());

        Unirest.config()
               .addDefaultHeader("Content-Type", "application/json")
               .addDefaultHeader("Accept", "application/json")
               .setObjectMapper(new ObjectMapperAdapter());
    }

    public CategorizeResponse categorize(String text) throws NLApiException {

        // get json reply from expert.ai API
        String json = categorizeAsString(text);

        // parsing and checking response
        CategorizeResponse response = APIUtils.fromJSON(json, CategorizeResponse.class);
        if(response.isSuccess()) {
            return response;
        }

        String msg = String.format("Categorize call to API %s return an error json: %s", URL, json);
        logger.error(msg);
        throw new NLApiException(NLApiErrorCode.EXECUTION_REQUEST_ERROR, msg);

    }

    public String categorizeAsString(String text) throws NLApiException {

        logger.debug("Sending text to categorize API: " + URL);

        HttpResponse<String> response = Unirest.post(URL)
                                               .header("Authorization", APIUtils.getBearerToken(authentication))
                                               .body(new AnalysisRequest(Document.of(text)).toJSON())
                                               .asString();

        if(response.getStatus() != 200) {
            String msg = String.format("Categorize call to API %s return error status %d", URL, response.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.CONNECTION_ERROR, msg);
        }

        logger.info("Categorize call successful");
        return response.getBody();
    }
}
