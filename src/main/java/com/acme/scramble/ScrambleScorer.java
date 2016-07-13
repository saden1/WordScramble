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

import com.google.common.collect.ImmutableSet;
import java.util.Arrays;
import java.util.Set;

/**
 * A class for analyzing scramble word and scores its relative difficulty to solve.
 *
 * @author saden
 */
public class ScrambleScorer {

    private final static Set<Character> VOWELS;
    private final static Set<Character> CONSONANTS;
    private final static Set<String> COMBINATION;

    static {
        VOWELS = ImmutableSet.of('A', 'E', 'I', 'O', 'U', 'Y');

        CONSONANTS = ImmutableSet.of(
                'B', 'C', 'D', 'F', 'G',
                'H', 'J', 'K', 'L', 'M',
                'N', 'P', 'Q', 'R', 'S',
                'T', 'V', 'W', 'X', 'Z'
        );

        COMBINATION = ImmutableSet.of(
                "AI", "AY", "EA", "EE", "EO", "IO", "OA", "OO", "OY", "YA",
                "YO", "YU", "BL", "BR", "CH", "CK", "CL", "CR", "DR", "FL",
                "FR", "GH", "GL", "GR", "KL", "KR", "KW", "PF", "PL", "PR",
                "SC", "SCH", "SCR", "SH", "SHR", "SK", "SL", "SM", "SN", "SP",
                "SQ", "ST", "SW", "TH", "THR", "TR", "TW", "WH", "WR"
        );
    }

    /**
     * Analyze and score the given scramble and word.
     *
     * @param scramble the scrambled word
     * @param word the actual world
     * @return the score given to the scrambled word
     */
    public Score score(String scramble, String word) {
        if (!isScrambled(scramble, word)) {
            return Score.not;
        }

        boolean ordered = scramble.charAt(0) == word.charAt(0);
        boolean real = true;

        switch (scramble.length()) {
            case 2:
                real = isAlternating(scramble);

                break;
            case 3:
                real = isCombination(scramble);

                if (!ordered) {
                    ordered = scramble.charAt(1) == word.charAt(2) && scramble.charAt(2) == word.charAt(2);
                }
                break;
            default:
                for (int i = 0, j = 1, k = 2; j < scramble.length() - 1; i++, j++, k++) {
                    String pre = scramble.substring(i, k);
                    String post = scramble.substring(j, k + 1);

                    if (!ordered) {
                        ordered = scramble.charAt(j) == word.charAt(j) && scramble.charAt(k) == word.charAt(k);
                    }

                    if (!isReal(pre, post)) {
                        real = false;
                        break;
                    }
                }
        }

        if (ordered && !real) {
            return Score.poor;
        } else if (!ordered && real) {
            return Score.hard;
        }

        return Score.fair;
    }

    /**
     * Determine if the give scramble string is scrambled variant of the given word. Returns false if the following
     * conditions are not met:
     * <ul>
     * <li>the scramble and word are exactly the same</li>
     * <li>the scramble and the word are not the same size</li>
     * <li>the scramble and the word do not have the same occurrences of characters (i.e. "MAPSA SPAMS").</li>
     * </ul>
     *
     * @param scramble the scramble string
     * @param word the word string
     * @return
     */
    boolean isScrambled(String scramble, String word) {
        if (scramble.equals(word) || scramble.length() != word.length()) {
            return false;
        }

        char[] scrambles = scramble.toCharArray();
        char[] words = word.toCharArray();

        Arrays.sort(scrambles);
        Arrays.sort(words);

        return Arrays.equals(scrambles, words);
    }

    /**
     * Determine if any given pair of characters look real.
     *
     * @param pre the previous pair of characters
     * @param post the next pair of characters
     * @return true if the pairs look real
     */
    boolean isReal(String pre, String post) {
        return (isAlternating(pre) && isAlternating(post))
                || (isAlternating(pre) && isCombination(post))
                || (isCombination(pre) && isAlternating(post))
                || (isCombination(pre) && isCombination(post));
    }

    /**
     * Determine if a pair of characters alternate between a vowel and consonant and vice versa.
     *
     * @param pair the pair of characters
     * @return true if the characters alternate
     */
    boolean isAlternating(String pair) {
        char first = pair.charAt(0);
        char second = pair.charAt(1);

        return (VOWELS.contains(first) && CONSONANTS.contains(second))
                || (CONSONANTS.contains(first) && VOWELS.contains(second));
    }

    /**
     * Determine if a pair of characters are an allowed combination.
     *
     * @param pair the pair of characters
     * @return true if the characters are an allowed combination
     */
    boolean isCombination(String pair) {

        return COMBINATION.contains(pair);
    }

    /**
     * Determine if a pair of characters are both consonants
     *
     * @param pair the pair of characters.
     * @return true if the characters are both consonants
     */
    boolean isDoubleConsonants(String pair) {
        char first = pair.charAt(0);
        char second = pair.charAt(1);

        return CONSONANTS.contains(first) && CONSONANTS.contains(second);
    }

}
