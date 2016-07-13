/*
 * Copyright 2015, 2016 saden.
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
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Score unit test class.
 *
 * @author saden
 */
@RunWith(DataProviderRunner.class)
public class ScoreTest {

    @DataProvider
    public static Object[][] enumInput() {
        return new Object[][]{
            {Score.not, "IRONY", "IRONY", "IRONY is not a scramble of IRONY"},
            {Score.poor, "IOYRN", "IRONY", "IOYRN is a poor scramble of IRONY"},
            {Score.fair, "INOYR", "IRONY", "INOYR is a fair scramble of IRONY"},
            {Score.hard, "ONYRI", "IRONY", "ONYRI is a hard scramble of IRONY"}
        };
    }

    @UseDataProvider("enumInput")
    @Test
    public void givenScrambleAndWordGetMessageShouldReturnExpectedMessage(Score score, String scramble, String word, String expectedMessage) {
        String result = score.getMessage(scramble, word);
        assertThat(result).isEqualTo(expectedMessage);
    }

    @UseDataProvider("enumInput")
    @Test
    public void givenScrambleAndWordPrintLineShouldPrintMessage(Score score, String scramble, String word, String expectedMessage) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        score.println(scramble, word);
        assertThat(outputStream.toString()).isEqualTo(expectedMessage + "\n");
    }

}
