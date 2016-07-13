/*
 * Copyright 2016 Sharmarke Aden.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acme.scramble;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static com.acme.scramble.Score.not;
import static com.acme.scramble.Score.poor;
import static com.acme.scramble.Score.hard;
import static com.acme.scramble.Score.fair;

/**
 * A unit test for the scorer class.
 *
 * @author saden
 */
@RunWith(DataProviderRunner.class)
public class ScrambleScorerTest {

    ScrambleScorer cut;

    @Before
    public void init() {
        cut = new ScrambleScorer();
    }

    @DataProvider
    public static Object[][] nullInput() {
        return new Object[][]{
            {null, "SPAM"},
            {"MAPS", null}

        };
    }

    @UseDataProvider("nullInput")
    @Test(expected = NullPointerException.class)
    public void givenNullInputScoreShouldThrowException(String scramble, String word) {
        cut.score(scramble, word);
    }

    @DataProvider
    public static Object[][] sampleInput() {
        return new Object[][]{
            {"MAPS", "SPAM", fair},
            {"RIONY", "IRONY", fair},
            {"ONYRI", "IRONY", hard},
            {"IRONY", "IRONY", not},
            {"INOYR", "IRONY", fair},
            {"IOYRN", "IRONY", poor}
        };
    }

    @UseDataProvider("sampleInput")
    @Test
    public void givenSampleInputScoreShouldReturnExpectedAnswer(String scramble, String word, Score score) {
        Score result = cut.score(scramble, word);

        assertThat(result).isEqualTo(score);
    }

    @DataProvider
    public static Object[][] nonRealSampleInput() {
        return new Object[][]{
            {"SWR", "RWS", fair},
            {"SWR", "RWS", fair},
            {"SWR", "SRW", poor},
            {"SWR", "SWR", not}
        };
    }

    @Test
    @UseDataProvider("nonRealSampleInput")
    public void givenNonRealSampleInputScoreShouldReturnExpectedAnswer(String scramble, String word, Score score) {
        Score result = cut.score(scramble, word);

        assertThat(result).isEqualTo(score);
    }

    @DataProvider
    public static Object[][] extendedSampleInput() {
        return new Object[][]{
            {"A", "A", not},
            {"AA", "AA", not},
            {"AND", "ADN", poor},
            {"OG", "GO", hard},
            {"SAGE", "CAGE", not},
            {"AGE", "CAGE", not},
            {"CAGE", "CAGE", not},
            {"AGEC", "CAGE", hard},
            {"CAEG", "CAGE", poor},
            {"ECIDMATE", "DECIMATE", fair}
        };
    }

    @Test
    @UseDataProvider("extendedSampleInput")
    public void givenExtendedSampleInputScoreShouldReturnExpectedAnswer(String scramble, String word, Score score) {
        Score result = cut.score(scramble, word);

        assertThat(result).isEqualTo(score);
    }
}
