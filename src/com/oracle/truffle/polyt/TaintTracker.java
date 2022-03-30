/*
 * Created on Wed Mar 09 2022
 *
 * The MIT License (MIT)
 * Copyright (c) 2022 Peterson Yuhala
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


package com.oracle.truffle.polyt;

import java.util.HashSet;
import java.util.Set;

import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;


public final class TaintTracker {
   

    private final Set<SourceSection> loaded = new HashSet<>();
    private final Set<SourceSection> tainted = new HashSet<>();

    void addTainted(SourceSection sourceSection) {
        tainted.add(sourceSection);
    }

    void addLoaded(SourceSection sourceSection) {
        loaded.add(sourceSection);
    }

    void printTaintedMethods(){
        
    }

    private Set<SourceSection> nonCoveredSections() {
        final HashSet<SourceSection> nonCovered = new HashSet<>();
        nonCovered.addAll(loaded);
        nonCovered.removeAll(tainted);
        return nonCovered;
    }

    Set<Integer> nonCoveredLineNumbers() {
        Set<Integer> linesNotCovered = new HashSet<>();
        for (SourceSection ss : nonCoveredSections()) {
            for (int i = ss.getStartLine(); i <= ss.getEndLine(); i++) {
                linesNotCovered.add(i);
            }
        }
        return linesNotCovered;
    }

    Set<Integer> loadedLineNumbers() {
        Set<Integer> loadedLines = new HashSet<>();
        for (SourceSection ss : loaded) {
            for (int i = ss.getStartLine(); i <= ss.getEndLine(); i++) {
                loadedLines.add(i);
            }
        }
        return loadedLines;
    }

   

}
