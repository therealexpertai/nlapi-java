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

package ai.expert.nlapi.v2;

import ai.expert.nlapi.exceptions.NLApiErrorCode;
import ai.expert.nlapi.exceptions.NLApiException;
import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.utils.APIUtils;
import ai.expert.nlapi.utils.ObjectMapperAdapter;
import ai.expert.nlapi.v2.message.ContextsResponse;
import ai.expert.nlapi.v2.message.TaxonomiesResponse;
import ai.expert.nlapi.v2.message.TaxonomyResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Gio
 */
public class InfoAPI {

    private static final Logger logger = LogManager.getLogger();

    private final Authentication authentication;
    private final String URL;

    public InfoAPI(InfoAPIConfig config) {

        authentication = config.getAuthentication();

        URL = String.format("%s/%s", API.AUTHORITY, config.getVersion());

        Unirest.config()
               .addDefaultHeader("Content-Type", "application/json")
               .addDefaultHeader("Accept", "application/json")
               .setObjectMapper(new ObjectMapperAdapter());
    }

    /**
     * Returns information about available contexts
     *
     * @return Contexts
     */
    public ContextsResponse getContexts() throws NLApiException {

        String URLGet = URL + "/contexts";
        logger.debug("Calling GET contexts: " + URLGet);
        HttpResponse<String> response = Unirest.get(URLGet)
                                               .header("Authorization", APIUtils.getBearerToken(authentication))
                                               .asString();

        if(response.getStatus() != 200) {
            String msg = String.format("GET contexts call to API %s return error status %d", URLGet, response.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.CONNECTION_ERROR, msg);
        }

        logger.info(String.format("GET contexts call successful"));
        return APIUtils.fromJSON(response.getBody(), ContextsResponse.class);
    }

    /**
     * Returns information about available taxonomies
     *
     * @return Taxonomies
     */
    public TaxonomiesResponse getTaxonomies() throws NLApiException {

        String URLGet = URL + "/taxonomies";
        logger.debug("Calling GET taxonomies: " + URLGet);
        HttpResponse<String> response = Unirest.get(URLGet)
                                               .header("Authorization", APIUtils.getBearerToken(authentication))
                                               .asString();

        if(response.getStatus() != 200) {
            String msg = String.format("GET taxonomies call to API %s return error status %d", URLGet, response.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.CONNECTION_ERROR, msg);
        }

        logger.info(String.format("GET taxonomies call successful"));
        return APIUtils.fromJSON(response.getBody(), TaxonomiesResponse.class);
    }

    /**
     * Returns information about available taxonomy for specific language
     *
     * @return Taxonomy
     */
    public TaxonomyResponse getTaxonomy(String taxonomy, API.Languages lang) throws NLApiException {

        String URLGet = String.format("%s//taxonomies/%s/%s", URL, taxonomy.toLowerCase(), lang.code());
        logger.debug("Calling GET taxonomy: " + URLGet);
        HttpResponse<String> response = Unirest.get(URLGet)
                                               .header("Authorization", APIUtils.getBearerToken(authentication))
                                               .asString();

        if(response.getStatus() != 200) {
            String msg = String.format("GET taxonomy call to API %s return error status %d", URLGet, response.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.CONNECTION_ERROR, msg);
        }

        logger.info(String.format("GET taxonomy call successful"));
        return APIUtils.fromJSON(response.getBody(), TaxonomyResponse.class);
    }
}
