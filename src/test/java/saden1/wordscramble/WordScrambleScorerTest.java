/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saden1.wordscramble;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static saden1.wordscramble.Score.not;
import static saden1.wordscramble.Score.poor;
import static saden1.wordscramble.Score.hard;
import static saden1.wordscramble.Score.fair;

/**
 *
 * @author saden
 */
@RunWith(DataProviderRunner.class)
public class WordScrambleScorerTest {

    WordScrambleScorer cut;

    @Before
    public void init() {
        cut = new WordScrambleScorer();
    }

    //@Test(expected = NullPointerException.class)
    public void givenNullScoreShouldThrowNullPointerException() {
        cut.score(null);
    }

    // @Test(expected = IndexOutOfBoundsException.class)
    public void givenEmptyStringScoreShouldThrowIndexOutOfBoundsException() {
        cut.score("");
    }

    @DataProvider
    public static Object[][] sampleInput() {
        return new Object[][]{
            {"MAPS SPAM", fair},
            {"RIONY IRONY", fair},
            {"ONYRI IRONY", hard},
            {"IRONY IRONY", not},
            {"INOYR IRONY", fair},
            {"IOYRN IRONY", poor}
        };
    }

    @Test
    @UseDataProvider("sampleInput")
    public void givenSampleInputScoreShouldReturnExpectedAnswer(String input, Score score) {
        Score result = cut.score(input);

        assertThat(result).isEqualTo(score);
    }

    @DataProvider
    public static Object[][] nonRealSampleInput() {
        return new Object[][]{
            {"SWR RWS", fair},
            {"SWR RWS", fair},
            {"SWR SRW", poor},
            {"SWR SWR", not}
        };
    }

    @Test
    @UseDataProvider("nonRealSampleInput")
    public void givenNonRealSampleInputScoreShouldReturnExpectedAnswer(String input, Score score) {
        Score result = cut.score(input);

        assertThat(result).isEqualTo(score);
    }

    @DataProvider
    public static Object[][] extendedSampleInput() {
        return new Object[][]{
            {"A A", not},
            {"AA AA", not},
            {"AND ADN", poor},
            {"OG GO", hard},
            {"SAGE CAGE", not},
            {"AGE CAGE", not},
            {"CAGE CAGE", not},
            {"AGEC CAGE", hard},
            {"CAEG CAGE", poor},
            {"ECIDMATE DECIMATE", fair}
        };
    }

    @Test
    @UseDataProvider("extendedSampleInput")
    public void givenExtendedSampleInputScoreShouldReturnExpectedAnswer(String input, Score score) {
        Score result = cut.score(input);

        assertThat(result).isEqualTo(score);
    }
}
