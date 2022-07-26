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
import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.utils.APIUtils;
import ai.expert.nlapi.utils.ObjectMapperAdapter;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.message.AnalysisRequestWithOptions;
import ai.expert.nlapi.v2.message.AnalyzeResponse;
import ai.expert.nlapi.v2.model.Document;
import ai.expert.nlapi.v2.model.Options;
import ai.expert.nlapi.v2.message.EdgeKeyResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.util.Map;

public class Analyzer {

    private static final Logger logger = LoggerFactory.getLogger(Analyzer.class);


    private final Authentication authentication;
    private final String resource;
    private final String EKEY_URL;
    private final String EDGE_URL;

    public Analyzer(AnalyzerConfig config) {

        authentication = config.getAuthentication();
        resource = config.getResource();

        EKEY_URL = String.format("%s/edge/key", API.EDGE_AUTHORITY);
        EDGE_URL = String.format("%s/api/analyze", config.getHost());

        Unirest.config()
               .addDefaultHeader("Content-Type", "application/json")
               .addDefaultHeader("Accept", "application/json")
               .setObjectMapper(new ObjectMapperAdapter());
    }

    public AnalyzeResponse analyze(String text, List<String> analysis, List<String> features) throws NLApiException {
        return getResponseDocument(text, analysis, features,null);
    }
    public AnalyzeResponse analyze(String text, List<String> analysis, List<String> features, Map<String,Object> extra) throws NLApiException {
        return getResponseDocument(text, analysis, features,extra);
    }

    public AnalyzeResponse analyze(String text) throws NLApiException {
        ArrayList<String> analysis = new ArrayList<>();
        analysis.add("disambiguation");
        analysis.add("relevants");
        analysis.add("entities");
        analysis.add("sentiment");
        analysis.add("relations");
        ArrayList<String> features = new ArrayList<>();
        features.add("syncpos");
        features.add("knowledge");
        features.add("dependency");
        return getResponseDocument(text, analysis, features,null);
    }

    public AnalyzeResponse disambiguation(String text) throws NLApiException {
        ArrayList<String> analysis = new ArrayList<>();
        analysis.add("disambiguation");
        ArrayList<String> features = new ArrayList<>();
        features.add("syncpos");
        features.add("knowledge");
        features.add("dependency");
        return getResponseDocument(text, analysis, features,null);
    }

    public AnalyzeResponse relevants(String text) throws NLApiException {
        ArrayList<String> analysis = new ArrayList<>();
        analysis.add("relevants");
        ArrayList<String> features = new ArrayList<>();
        features.add("syncpos");
        features.add("knowledge");
        return getResponseDocument(text, analysis, features,null);
    }

    public AnalyzeResponse entities(String text) throws NLApiException {
        ArrayList<String> analysis = new ArrayList<>();
        analysis.add("entities");
        ArrayList<String> features = new ArrayList<>();
        features.add("syncpos");
        features.add("knowledge");
        return getResponseDocument(text, analysis, features,null);
    }

    public AnalyzeResponse relations(String text) throws NLApiException {
        ArrayList<String> analysis = new ArrayList<>();
        analysis.add("relations");
        ArrayList<String> features = new ArrayList<>();
        features.add("syncpos");
        features.add("knowledge");
        features.add("dependency");
        return getResponseDocument(text, analysis, features,null);
    }

    public AnalyzeResponse sentiment(String text) throws NLApiException {
        ArrayList<String> analysis = new ArrayList<>();
        analysis.add("sentiment");
        ArrayList<String> features = new ArrayList<>();
        features.add("syncpos");
        features.add("knowledge");
        return getResponseDocument(text, analysis, features,null);
    }

    public AnalyzeResponse classification(String text) throws NLApiException {
        ArrayList<String> analysis = new ArrayList<>();
        analysis.add("categories");
        ArrayList<String> features = new ArrayList<>();
        features.add("syncpos");
        return getResponseDocument(text, analysis, features,null);
    }

    public AnalyzeResponse extraction(String text) throws NLApiException {
        ArrayList<String> analysis = new ArrayList<>();
        analysis.add("extractions");
        ArrayList<String> features = new ArrayList<>();
        features.add("syncpos");
        return getResponseDocument(text, analysis, features,null);
    }

    private AnalyzeResponse getResponseDocument(String text, List<String> analysis, List<String> features,
                                                Map<String,Object> extra) throws NLApiException {

        // get json reply from expert.ai API
        String json = getResponseDocumentString(text, analysis, features,extra);

        // parsing and checking response
        AnalyzeResponse response = APIUtils.fromJSON(json, AnalyzeResponse.class);
        if(response.isSuccess()) {
            return response;
        }

        String msg = String.format("Edge Analyze call return an error json: %s", json);
        logger.error(msg);
        throw new NLApiException(NLApiErrorCode.EXECUTION_REQUEST_ERROR, msg);
    }

    private String getMD5(String text) {
        String res = null;
        try {
            MessageDigest md = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            byte[] array = md.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            res = sb.toString();
        } catch(NoSuchAlgorithmException ex) {
            logger.error("getMD5 exception: " + ex.getMessage());
        }
        return res;
    }

    private String getExecutionKey(String md5) throws NLApiException {
        String URLpath = EKEY_URL + "/" + md5;
        logger.debug("Requesting execution-key: " + URLpath);
        HttpResponse<String> response = Unirest.get(URLpath).header("Authorization", APIUtils.getBearerToken(authentication)).asString();
        if (response.getStatus()!=200) {
            String msg = String.format("Edge Execution key call to API %s return error status %d", URLpath, response.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.CONNECTION_ERROR, msg);
        }
        EdgeKeyResponse keyResponse = APIUtils.fromJSON(response.getBody(), EdgeKeyResponse.class);
        return keyResponse.getKey();
    }

    private String getResponseDocumentString(String text,
                                             List<String> analysis,
                                             List<String> features,
                                             Map<String,Object> extra) throws NLApiException {
        String ekey = "";
        if (authentication!=null) {
            String md5 = getMD5(text);
            if(md5 == null) {
                String msg = "Error generating text md5 hash";
                throw new NLApiException(NLApiErrorCode.DATA_PROCESSING_ERROR, msg);
            }
            ekey = getExecutionKey(md5);
        }
        String URLpath = EDGE_URL;
        logger.debug("Sending text to edge analyze API: " + URLpath);
        HttpResponse<String> response = Unirest.post(URLpath)
                                               .header("execution-key", ekey)
                                               .body(new AnalysisRequestWithOptions(Document.of(text), Options.of(analysis, features,extra), resource).toJSON())
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
            String msg = String.format("Edge Analyze call to API %s return error status %d", URLpath, response.getStatus());
            logger.error(msg);
            throw new NLApiException(NLApiErrorCode.CONNECTION_ERROR, msg);
        }

        logger.info("Edge Analyze call successful");
        return response.getBody();
    }
}
