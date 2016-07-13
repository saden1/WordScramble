## Problem Statement

A popular item in the local paper is the word scramble. In this puzzle, a reader is presented with a series of letters that are for a scrambled word, e.g. "rwod" can be unscrambled to "word". Your job is to write a program that will score the difficulty of any particular scrambling of a word. The scores can be not, poor, fair or hard, depending on whether or not the scramble is not scrambled, easy to solve, a reasonable difficulty to solve or hard to solve, respectively.

Word scrambles can be judged by a set of heuristics including if the word looks real or if the scramble has letters in the correct place. A scramble looks like a real word if the letters alternate between vowels and consonants (with ‘Y’ being a vowel for this purpose). However, certain combinations of vowels and consonants are allowed:

|    |     |     |    |     |    |    |    |    |    |
|:--:|:---:|:---:|:--:|:---:|:--:|:--:|:--:|:--:|:--:|
| AI |  AY |  EA | EE |  EO | IO | OA | OO | OY | YA |
| YO |  YU |  BL | BR |  CH | CK | CL | CR | DR | FL |
| FR |  GH |  HL | GR |  KL | KR | KW | PF | PL | PR |
| SC | SCH | SCR | SH | SHR | SK | SL | SM | SN | SP |
| SQ |  ST |  SW | TH | THR | TR | TW | WH | WR |    |

Also, all double consonants are allowed, and, no other combinations are allowed. For instance, SWR doesn’t look real even though both SW and WR are independently looking real.


## Sample Input Score

| Scramble | Word  | Sound | Ordered | Real  | Score | Commment                |
|----------|-------|-------|---------|-------|-------|-------------------------|
| MAPS     | SPAM  | cvcc  | false   | false | fair  | not ordered & not real  |
| RIONY    | IRONY | cvvcv | true    | true  | fair  | ordered & real          |
| ONYRI    | IRONY | vcvcv | false   | true  | hard  | not ordered & real      |
| IRONY    | IRONY | vcvcv | -       | -     | not   | not scrambled           |
| INOYR    | IRONY | vcvvc | true    | true  | fair  | ordered && real         |
| IOYRN    | IRONY | vvvcc | true    | false | poor  | ordered & not real      |

## Truth Tables

### Score Truth Table

Return "not" if the following conditions are met:
1. the scramble and word are exactly the same
1. the scramble and the word are not the same size
1. the scramble and the word do not have the same occurrences of characters (i.e. "MAPSA SPAMS").

Otherwise use the below truth table to determine the score:

|                  | Ordered (true) | Not ordered (false) |
|------------------|----------------|---------------------|
| Real (true)      | fair           | hard                |
| Not Real (false) | poor           | fair                |


### Real Truth Table
The bellow table describes word sequence that aid in determining if a word is real.

|                  | Alternating | Combination | Double Consonant |
|------------------|-------------|-------------|------------------|
| Alternating      | true        | true        | false            |
| Combination      | true        | false       | false            |
| Double Consonant | false       | false       | false            |


### Input Permutations

SWR: false
SW - combination
WR - combination

MAPS SPAM: false
MA - alternating
AP - alternating
PS - double consonant

RIONY IRONY: true
RI - alternating
IO - combination
ON - alternating
NY - alternating

ONYRI IRONY: true
ON - alternating
NY - alternating
YR - alternating
RI - alternating

INOYR IRONY: true
IN - alternating
NO - alternating
OY - combination
YR - alternating

IOYRN IRONY: false
IO - combination
OY - combination
YR - alternating
RN - double consonant


alternating && alternating = true
alternative && double constant = false
alternative && combination = true
combination && alternative = true
combination && combination = true
double constant && double constant = false

