/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saden1.wordscramble;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author saden
 */
public class WordScrambleScorer {

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

    public static void main(String[] args) throws URISyntaxException, IOException {
        WordScrambleScorer scorer = new WordScrambleScorer();

        File file;

        if (args == null || args.length == 0) {
            file = new File(args[0]);
        } else {
            file = new File(Resources.getResource("sample.txt").toURI());
        }

        Resources.readLines(file.toURI().toURL(), UTF_8, new LineProcessor< List<String>>() {
            private final List<String> result = new LinkedList<>();

            @Override
            public boolean processLine(String line) throws IOException {
                Score score = scorer.score(line);

//                switch (score) {
//                    case not:
//                        System.out.println(String.format("%s is not a scramble of %s", scrambled, word));
//                        break;
//                    case poor:
//                        System.out.println(String.format("%s is a poor scramble of %s", scrambled, word));
//                        break;
//                    case hard:
//                        System.out.println(String.format("%s is a hard scramble of %s", scrambled, word));
//                        break;
//                    case fair:
//                        System.out.println(String.format("%s is a fair scramble of %s", scrambled, word));
//                        break;
//                    default:
//                        throw new AssertionError(score.name());
//                }
                
                return true;
            }

            @Override
            public List<String> getResult() {
                return result;
            }
        });

    }

    public Score score(String line) {
        String[] tokens = line.split(" ");
        String scrambled = tokens[0];
        String word = tokens[1];

        Score score = analyze(scrambled, word);

        switch (score) {
            case not:
                System.out.println(String.format("%s is not a scramble of %s", scrambled, word));
                break;
            case poor:
                System.out.println(String.format("%s is a poor scramble of %s", scrambled, word));
                break;
            case hard:
                System.out.println(String.format("%s is a hard scramble of %s", scrambled, word));
                break;
            case fair:
                System.out.println(String.format("%s is a fair scramble of %s", scrambled, word));
                break;
            default:
                throw new AssertionError(score.name());
        }

        return score;
    }

    Score analyze(String scrambled, String word) {
        if (!isScrambled(scrambled, word)) {
            return Score.not;
        }

        boolean ordered = scrambled.charAt(0) == word.charAt(0);
        boolean real = true;

        switch (scrambled.length()) {
            case 1:
                return Score.poor;
            case 2:
                real = isAlternating(scrambled) || isCombination(scrambled) || isDoubleConsonants(word);
                break;
            case 3:
                real = isCombination(scrambled);
                if (!ordered) {
                    ordered = scrambled.charAt(1) == word.charAt(2) && scrambled.charAt(2) == word.charAt(2);
                }
                break;
            default:
                for (int i = 0, j = 1, k = 2; j < scrambled.length() - 1; i++, j++, k++) {
                    String pre = scrambled.substring(i, k);
                    String post = scrambled.substring(j, k + 1);

                    if (!ordered) {
                        ordered = scrambled.charAt(j) == word.charAt(j) && scrambled.charAt(k) == word.charAt(k);
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

    boolean isReal(String pre, String post) {
        return (isAlternating(pre) && isAlternating(post))
                || (isAlternating(pre) && isCombination(post))
                || (isCombination(pre) && isAlternating(post))
                || (isCombination(pre) && isCombination(post));
    }

    boolean isAlternating(String pair) {
        char first = pair.charAt(0);
        char second = pair.charAt(1);

        return (VOWELS.contains(first) && CONSONANTS.contains(second))
                || (CONSONANTS.contains(first) && VOWELS.contains(second));
    }

    boolean isCombination(String pair) {
        return COMBINATION.contains(pair);
    }

    boolean isDoubleConsonants(String pair) {
        char first = pair.charAt(0);
        char second = pair.charAt(1);

        return CONSONANTS.contains(first) && CONSONANTS.contains(second);
    }

}
