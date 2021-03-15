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

package ai.expert.nlapi.v2.test;

import ai.expert.nlapi.security.Authentication;
import ai.expert.nlapi.v2.API;
import ai.expert.nlapi.v2.cloud.Detector;
import ai.expert.nlapi.v2.cloud.DetectorConfig;
import ai.expert.nlapi.v2.message.DetectResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DetectionTest {

    static StringBuilder textEn = new StringBuilder();
    static StringBuilder textIt = new StringBuilder();


    public static String getSampleTextEn() {
        return textEn.toString();
    }
    public static String getSampleTextIt() {
        return textIt.toString();
    }
    public static Detector createDetector(Authentication authentication, String detector, API.Languages lang) throws Exception {
        return new Detector(DetectorConfig.builder()
                      .withVersion(API.Versions.V2)
                      .withDetector(detector)
                      .withLanguage(lang)
                      .withAuthentication(authentication)
                      .build());
    }

    @Before
    public void setup() {

        // load myProperties.txt file
        TestUtils.setPropertyFile("src/test/resources/myProperties.txt");

        // check number of call to avoid limit call rate error
        TestUtils.callRateCheck();

        // set text to be analyzed using IPTC taxonomy
        textEn.append("Michael Jordan was one of the best basketball players of all time. ");

        // set text to be analyzed using GeoTAX taxonomy
        textIt.append("MARIO ROSSI\n" +
                      "VIALE EUROPA 300\n" +
                      "00144 ROMA RM");
    }

    @Test
    public void testDetectorEn() {
        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create detector
            Detector detectorEn = createDetector(authentication, "pii", API.Languages.en);

            // send detector request and get response
            DetectResponse detect = detectorEn.detection(getSampleTextEn());
            // print json response
            detect.prettyPrint();

            // assert there is the data passed as input
            assertNotNull(detect.getData());
            assertNotNull(detect.getData().getContent());
            assertSame(detect.getData().getLanguage(), API.Languages.en);

            // assert there are extractions
            assertNotNull(detect.getData().getExtractions());
            // assert there are extra data
            assertNotNull(detect.getData().getExtraData());
        }
        catch(Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDetectorIt() {
        try {
            // get authentication, if not exist it creates one
            Authentication authentication = TestUtils.getAuthentication();

            // create categorization
            Detector detectorIt = createDetector(authentication, "pii", API.Languages.it);

            // send categorization request and get response
            DetectResponse detect = detectorIt.detection(getSampleTextIt());
            // print json response
            detect.prettyPrint();

            // assert there is the data passed as input
            assertNotNull(detect.getData());
            assertNotNull(detect.getData().getContent());
            assertSame(detect.getData().getLanguage(), API.Languages.it);

            // assert there are categories
            assertNotNull(detect.getData().getExtractions());
            assertNotNull(detect.getData().getExtraData());
        }
        catch(Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
}
