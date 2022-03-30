/*
 * Created on Wed Mar 18 2022
 *
 * The MIT License (MIT)
 * Copyright (c) 2022 Peterson Yuhala, Institut d'Informatique Université de Neuchâtel (IIUN)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.oracle.truffle.polyt.utils;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

public class SourceMapping {

    private static final String EVAL_AT = "eval at ";

    /**
     * Helper function to determine what is considered internal by the source
     * filter.
     *
     * @param src the Source to test
     * @return true if src is considered internal
     */
    public static boolean isInternal(final Source src) {
        return src.isInternal() || (!isEval(src) && (src.getPath() == null || src.getPath().equals("")));
    }

    public static boolean isEval(final Source src) {
        return src.getName().startsWith(EVAL_AT);
    }

    public static String innerMostEvalSource(String srcString) {
        String res = null;
        Pattern p = Pattern.compile(EVAL_AT + "[^\\s]+ \\((.*)\\)");
        Matcher m = p.matcher(srcString);
        while (m.matches()) {
            res = m.group(1);
            m = p.matcher(res);
        }
        return res;
    }
}
