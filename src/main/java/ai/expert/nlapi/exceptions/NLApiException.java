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

import java.util.UUID;

public class NLApiException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String uuid = UUID.randomUUID().toString();

    private final NLApiErrorCode errorCode;

    public NLApiException(NLApiErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public NLApiException(NLApiErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public NLApiException(NLApiErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public NLApiException(NLApiErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("error code " + errorCode.getErrorCode() + ": ");
        errorMessage.append(errorCode.getErrorMessage());
        if(super.getMessage() != null) {
            errorMessage.append(" - " + super.getMessage());
        }

        return errorMessage.toString();
    }

    public String getExceptionIdentifier() {
        return uuid;
    }

    public NLApiErrorCode getErrorCode() {
        return errorCode;
    }
}

