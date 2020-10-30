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

package ai.expert.nlapi.security;

abstract class CredentialsProvider {

    protected CredentialsProvider nextCredentialsProvider;

    // set next in chain of responsibility
    protected CredentialsProvider setNextCredentialsProvider(CredentialsProvider nextCredentialsProvider) {
        this.nextCredentialsProvider = nextCredentialsProvider;
        return this.nextCredentialsProvider;
    }

    // solve credential in chain of responsibility
    public Credential solveCredentials() {
        Credential credential = getCredentials();
        if(credential != null) {
            // credetials solved
            return credential;
        }
        else if(nextCredentialsProvider != null) {
            // ask next CredentialsProvide to solve credetials
            return nextCredentialsProvider.solveCredentials();
        }
        // no possibility to solve credetials
        return null;
    }

    // CredentialsProvider checks if credential are set
    // null if not set
    abstract public Credential getCredentials();

}
