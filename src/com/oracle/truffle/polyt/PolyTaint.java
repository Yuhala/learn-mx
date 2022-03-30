/*
 * Created on Wed Mar 09 2022
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
package com.oracle.truffle.polyt;

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.xml.transform.SourceLocator;

import org.graalvm.options.OptionCategory;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionKey;
import org.graalvm.options.OptionStability;
import org.graalvm.options.OptionValues;

import com.oracle.truffle.api.Option;
import com.oracle.truffle.api.instrumentation.Instrumenter;
import com.oracle.truffle.api.instrumentation.SourceFilter;
import com.oracle.truffle.api.instrumentation.SourceSectionFilter;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.StandardTags.ExpressionTag;
import com.oracle.truffle.api.instrumentation.StandardTags.StatementTag;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import com.oracle.truffle.api.instrumentation.TruffleInstrument.Registration;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.polyt.factory.PolyTaintEventFactory;
import com.oracle.truffle.api.instrumentation.StandardTags.WriteVariableTag;

/**
 * pyuhala
 * PolyTaint project is adapted from Truffle simple coverage tool project. Many
 * parts of the code base still contains
 * simple tool code but other methods and classes specific to polytaint are
 * being added. Simple tool code will stay in the
 * repo as long as its useful to me.
 */
@Registration(id = PolyTaint.ID, name = "Polyglot Taint", version = "0.1", services = PolyTaint.class)
public final class PolyTaint extends TruffleInstrument {

    public static final int JAVA_ID = 1;
    public static final int JS_ID = 2;

    @Option(name = "", help = "Enable Polytaint tool (default: true).", category = OptionCategory.USER, stability = OptionStability.STABLE)
    static final OptionKey<Boolean> ENABLED = new OptionKey<>(true);

    @Option(name = "PrintCoverage", help = "Print coverage to stdout on process exit (default: true).", category = OptionCategory.USER, stability = OptionStability.STABLE)
    static final OptionKey<Boolean> PRINT_COVERAGE = new OptionKey<>(true);
    // @formatter:on

    public static final String ID = "polytaint";
    public boolean taintTest = false;

    /**
     * Stores all application-specific functions/methods that have been tainted
     */
    final Set<String> taintedMethods = new HashSet<String>();

    /**
     * Global tracker object.
     */
    public TaintTracker tracker = new TaintTracker();

    private Instrumenter instrumenter;
    private Env instrumentEnv;

    public PolyTaint() {
        super();
    }

    public Env getEnv() {
        return instrumentEnv;
    }

    public Instrumenter getInstrumenter() {
        return instrumenter;
    }

    @Override
    protected void onCreate(final Env env) {
        this.instrumentEnv = env;
        this.instrumenter = env.getInstrumenter();

        final OptionValues options = env.getOptions();
        if (ENABLED.getValue(options)) {
            enable(env);
            env.registerService(this);
        }
    }

    /**
     * Ensures that the coverage info gathered by the instrument is printed at the
     * end of execution.
     *
     * @param env
     */
    @Override
    protected void onDispose(Env env) {
        // pyuhala: write results to JSON file

    }

    @Override
    protected OptionDescriptors getOptionDescriptors() {
        return new PolyTaintOptionDescriptors();
    }

    private void enable(final Env env) {

        /**
         * pyuhala:
         * We create a filter to monitor the execution of all secureL statements in the
         * application. This informs the
         * GraalVM runtime about the code sections to be tracked.
         */

        SourceSectionFilter secureLFilter = SourceSectionFilter.newBuilder()
                .tagIs(StatementTag.class, ExpressionTag.class)
                .sourceIs((s) -> s.getLanguage().equals("secureL")).build();

        SourceSectionFilter writeFilter = SourceSectionFilter.newBuilder().tagIs(WriteVariableTag.class).build();

        instrumenter.attachLoadSourceSectionListener(secureLFilter, new GatherSourceSectionsListener(this), true);

        // this.instrumenter.attachExecutionEventFactory(writeFilter, new
        // PolyTaintEventFactory(this));
        instrumenter.attachExecutionEventFactory(secureLFilter, new PolyTaintEventFactory(this, JS_ID));

    }

    /**
     * pyuhala:
     * Write the taint analysis results into a JSON file
     * 
     */
    private synchronized void writeTaintResults(final Env env) {
        // TODO
    }

    private void writeTaintResult() {
        // TODO
    }

    /**
     * pyuhala:
     * Called after a {@link SourceSection} is executed; this marks a tainted code
     * section. We update
     * the necessary data structures.
     * 
     * @param sourceSection the executed {@link SourceSection}
     */

    public synchronized void addTainted(SourceSection sourceSection) {
        // final TaintTracker tracker = taintMap.get(sourceSection.getSource());
        // tracker.addCovered(sourceSection);
        // TODO pyuhala: add tainted section to map/set...
        taintTest = true;
    }

    /**
     * Called when a new {@link SourceSection} is loaded. We can update our
     * {@link #coverageMap}.
     * 
     * @param sourceSection the newly loaded {@link SourceSection}
     */
    synchronized void addLoaded(SourceSection sourceSection) {
        /*
         * final Coverage coverage =
         * coverageMap.computeIfAbsent(sourceSection.getSource(), new Function<Source,
         * Coverage>() {
         * 
         * @Override
         * public Coverage apply(Source s) {
         * return new Coverage();
         * }
         * });
         * coverage.addLoaded(sourceSection);
         */
    }
}
