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

import static java.lang.String.format;

/**
 *
 * @author saden
 */
public enum Score {
    not("%s is not a scramble of %s"),
    poor("%s is a poor scramble of %s"),
    hard("%s is a hard scramble of %s"),
    fair("%s is a fair scramble of %s");

    private final String messageFormat;

    Score(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public String getMessage(String scramble, String word) {
        return format(getMessageFormat(), scramble, word);
    }

    public void println(String scramble, String word) {
        System.out.println(getMessage(scramble, word));
    }
}
