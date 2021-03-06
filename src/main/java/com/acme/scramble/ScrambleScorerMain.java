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
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.List;

/**
 * A main class that takes sample input file path as an argument or loads one from the classpath.
 *
 * @author saden
 */
public class ScrambleScorerMain implements LineProcessor<List<String>> {

    ScrambleScorer scorer = new ScrambleScorer();
    ImmutableList.Builder<String> resultBuilder;

    public ScrambleScorerMain(ScrambleScorer scorer, ImmutableList.Builder<String> resultBuilder) {
        this.scorer = scorer;
        this.resultBuilder = resultBuilder;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        ScrambleScorerMain scorerMain = new ScrambleScorerMain(new ScrambleScorer(), new ImmutableList.Builder<>());

        if (args == null || args.length == 1) {
            File file = new File(args[0]);
            if (file.exists()) {
                Files.readLines(file, UTF_8, scorerMain);
                return;
            }
            
            System.err.println("Input file '" + file + "' not found.");
        } else {
            URL resource = Resources.getResource("sample_input.txt");
            CharSource charSource = Resources.asCharSource(resource, UTF_8);
            charSource.readLines(scorerMain);
        }

    }

    @Override
    public boolean processLine(String line) throws IOException {
        String[] tokens = line.split(" ");
        String scrambled = tokens[0];
        String word = tokens[1];
        Score score = scorer.score(scrambled, word);
        score.println(scrambled, word);
        resultBuilder.add(score.getMessage(scrambled, word));

        return true;
    }

    @Override
    public List<String> getResult() {
        return resultBuilder.build();
    }

}
