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


public class API {

    public static final String AUTHORITY = "https://nlapi.expert.ai";
    public static final String EDGE_AUTHORITY = "https://edgeapi.expert.ai";
    public static final String DEFAULT_EDGE_HOST = "http://127.0.0.1:6699";

    public enum Versions {

        V1("v1"),
        V2("v2");

        private final String value;

        Versions(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

        @Override
        public String toString() {
            return value();
        }
    }

    public enum Contexts {

        STANDARD("standard");

        private final String value;

        Contexts(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

        @Override
        public String toString() {
            return value();
        }
    }

    public enum Taxonomies {

        IPTC("iptc"),
        GEOTAX("geotax");

        private final String value;

        Taxonomies(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

        @Override
        public String toString() {
            return value();
        }
    }

    public enum Languages {

        notKnown("unknown", "Unknown"),
        de("de", "German"),
        en("en", "English"),
        es("es", "Spanish"),
        fr("fr", "French"),
        it("it", "Italian");

        private final String code;
        private final String name;

        Languages(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String code() {
            return code;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return getName();
        }
    }
}
