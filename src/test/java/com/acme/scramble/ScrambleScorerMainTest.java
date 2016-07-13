/*
 * Copyright 2016 saden.
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

import com.google.common.collect.ImmutableList;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 * A unit test for the ScrambleScorer main class.
 *
 * @author saden
 */
public class ScrambleScorerMainTest {

    ScrambleScorerMain cut;
    ScrambleScorer scorer;
    ImmutableList.Builder<String> resultBuilder;

    @Before
    public void init() {
        scorer = new ScrambleScorer();
        resultBuilder = new ImmutableList.Builder<>();
        cut = new ScrambleScorerMain(scorer, resultBuilder);
    }

    @Test
    public void callToMainWithNonExistingFileShouldPrintError() throws IOException, URISyntaxException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));

        String[] args = new String[]{"target/classes/does_not_exist.txt"};
        cut.main(args);
        assertThat(outputStream.toString())
                .contains("Input file 'target/classes/does_not_exist.txt' not found.");
    }

    @Test
    public void callToMainWithoutFileShouldLoadSampleInputFileFromTheClassPath() throws IOException, URISyntaxException {
        String[] args = new String[]{};
        cut.main(args);
    }

    @Test
    public void callToMainWithExistingFileShouldLoadFile() throws IOException, URISyntaxException {
        String[] args = new String[]{"target/classes/sample_input.txt"};
        cut.main(args);
    }

}
