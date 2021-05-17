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

package ai.expert.nlapi.v2.edge;

import ai.expert.nlapi.exceptions.NLApiErrorCode;
import ai.expert.nlapi.exceptions.NLApiException;
import ai.expert.nlapi.utils.APIUtils;
import ai.expert.nlapi.utils.ObjectMapperAdapter;
import ai.expert.nlapi.v2.message.*;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Info {

    private static final Logger logger = LogManager.getLogger();

    private final String RESOURCES_URL;
    private final String STATISTICS_URL;
    private final String HEALTH_URL;

    public Info(InfoConfig config) {

        String host = config.getHost();

        RESOURCES_URL = String.format("%s/essex/packages", host);
        STATISTICS_URL = String.format("%s/essex/statistics", host);
        HEALTH_URL = String.format("%s/health", host);

        Unirest.config()
               .addDefaultHeader("Content-Type", "application/json")
               .addDefaultHeader("Accept", "application/json")
               .setObjectMapper(new ObjectMapperAdapter());
    }

    public ResourcesInfoResponse resources() throws NLApiException {
        return getResourcesInfoResponse();
    }

    public StatisticsInfoResponse statistics() throws NLApiException {
        return getStatisticsInfoResponse();
    }

    public void health() throws NLApiException {
        HttpResponse<String> response = Unirest.get(HEALTH_URL).asString();

        if (response.getStatus() != 200) {
            String msg = String.format("GET call to API %s return error status %d", HEALTH_URL, response.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.CONNECTION_ERROR, msg);
        }
    }

    private ResourcesInfoResponse getResourcesInfoResponse() throws NLApiException {

        // get json reply from expert.ai API
        String json = getInfoResponseString(RESOURCES_URL);

        // parsing and checking response
        ResourcesInfoResponse response = APIUtils.fromJSON(json, ResourcesInfoResponse.class);
        if (response.isSuccess()) {
            return response;
        }

        String msg = String.format("Edge Resources Info call return an error json: %s", json);
        logger.error(msg);
        throw new NLApiException(NLApiErrorCode.EXECUTION_REQUEST_ERROR, msg);
    }

    private StatisticsInfoResponse getStatisticsInfoResponse() throws NLApiException {

        // get json reply from expert.ai API
        String json = getInfoResponseString(STATISTICS_URL);

        // parsing and checking response
        StatisticsInfoResponse response = APIUtils.fromJSON(json, StatisticsInfoResponse.class);
        if (response.isSuccess()) {
            return response;
        }

        String msg = String.format("Edge Statistics Info call return an error json: %s", json);
        logger.error(msg);
        throw new NLApiException(NLApiErrorCode.EXECUTION_REQUEST_ERROR, msg);
    }

    private String getInfoResponseString(String url) throws NLApiException {
        logger.debug("Sending request to Edge Info API: " + url);
        HttpResponse<String> response = Unirest.post(url)
                                               .body("")
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

        if (response.getStatus() != 200) {
            String msg = String.format("Edge Info call to API %s return error status %d", url, response.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.CONNECTION_ERROR, msg);
        }

        logger.info("Edge Info call successful");
        return response.getBody();
    }
}
