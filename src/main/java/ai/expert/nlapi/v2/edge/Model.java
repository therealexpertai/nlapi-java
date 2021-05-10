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

public class Model {

    private static final Logger logger = LogManager.getLogger();

    private final String resource;
    private final String EDGE_URL;

    public Model(ModelConfig config) {

        resource = config.getResource();

        EDGE_URL = String.format("%s/api/model", config.getHost());

        Unirest.config()
               .addDefaultHeader("Content-Type", "application/json")
               .addDefaultHeader("Accept", "application/json")
               .setObjectMapper(new ObjectMapperAdapter());
    }

    public TaxonomyModelResponse taxonomy() throws NLApiException {
        return getTaxonomyModelResponse();
    }

    public TemplatesModelResponse templates() throws NLApiException {
        return getTemplatesModelResponse();
    }

    private TaxonomyModelResponse getTaxonomyModelResponse() throws NLApiException {

        // get json reply from expert.ai API
        String json = getModelResponseString("taxonomy");

        // parsing and checking response
        TaxonomyModelResponse response = APIUtils.fromJSON(json, TaxonomyModelResponse.class);
        if(response.isSuccess()) {
            return response;
        }

        String msg = String.format("Edge taxonomy model call return an error json: %s", json);
        logger.error(msg);
        throw new NLApiException(NLApiErrorCode.EXECUTION_REQUEST_ERROR, msg);
    }

    private TemplatesModelResponse getTemplatesModelResponse() throws NLApiException {

        // get json reply from expert.ai API
        String json = getModelResponseString("templates");

        // parsing and checking response
        TemplatesModelResponse response = APIUtils.fromJSON(json, TemplatesModelResponse.class);
        if(response.isSuccess()) {
            return response;
        }

        String msg = String.format("Edge templates model call return an error json: %s", json);
        logger.error(msg);
        throw new NLApiException(NLApiErrorCode.EXECUTION_REQUEST_ERROR, msg);
    }

    private String getModelResponseString(String model) throws NLApiException {
        String URLpath = EDGE_URL;
        ModelRequest mr = new ModelRequest();
        mr.setInfo(model);
        mr.setResource(resource);
        logger.debug("Sending request to Edge Model API: " + URLpath);
        HttpResponse<String> response = Unirest.post(URLpath)
                                               .body(mr.toJSON())
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
            String msg = String.format("Edge Model call to API %s return error status %d", URLpath, response.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.CONNECTION_ERROR, msg);
        }

        logger.info("Edge Model call successful");
        return response.getBody();
    }
}
