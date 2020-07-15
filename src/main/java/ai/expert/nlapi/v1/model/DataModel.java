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

package ai.expert.nlapi.v1.model;

import ai.expert.nlapi.v1.API;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataModel {

    private String content;
    private API.Languages language;
    private String version;

    private List<Knowledge> knowledge;

    private List<Token> tokens;
    private List<Phrase> phrases;
    private List<Sentence> sentences;
    private List<Paragraph> paragraphs;

    private List<Topic> topics;
    private List<MainSentence> mainSentences;
    private List<MainPhrase> mainPhrases;
    private List<MainLemma> mainLemmas;
    private List<MainSyncon> mainSyncons;

    private List<Entity> entities;
    private List<Category> categories;
}
