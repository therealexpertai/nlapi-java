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

package ai.expert.nlapi.v2.model;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class Options {
    List<String> analysis;
    List<String> features;
    Map<String,Object> extra;
    public static Options of(List<String> analysis, List<String> features) {
        return new Options(analysis, features,null);
    }
    public static Options of(List<String> analysis, List<String> features, Map<String,Object> extra) {
        return new Options(analysis, features,extra);
    }
}
