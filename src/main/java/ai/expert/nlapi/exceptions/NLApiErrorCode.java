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

package ai.expert.nlapi.exceptions;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum NLApiErrorCode {

    DATA_PROCESSING_ERROR(100, "Data Processing Error"),
    DATA_EMPTY_ERROR(100, "Data empty Error"),
    AUTHENTICATION_ERROR(300, "Authentication Error"),
    CONNECTION_ERROR(400, "Connection Error"),
    EXECUTION_REQUEST_ERROR(401, "Excecution Request Error"),
    AUTHORIZATION_ERROR(401, "Unauthorized Error, please check credetial or authorizion token."),
    REQUEST_UNKNOWN_LANGUAGE_ERROR(501, "Unknown Language"),
    REQUEST_UNKNOWN_CONTEXT_ERROR(502, "Unknown Context"),
    REQUEST_UNKNOWN_TAXONOMY_ERROR(502, "Unknown Taxonomy"),
    PARSING_ERROR(600, "Parsing Error");

    public static final String BUNDLE_NAME = NLApiErrorCode.class.getName();

    private final int errorCode;
    private final String errorMessage;

    NLApiErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = (errorMessage != null) ? errorMessage : "";
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getBusinessMessage(ResourceBundle bundle) {
        try {
            return bundle.getString(errorCode + ": " + errorMessage);
        }
        catch(MissingResourceException e) {
            return "NLapi Error (" + errorCode + ": " + errorMessage + ")";
        }
    }
}