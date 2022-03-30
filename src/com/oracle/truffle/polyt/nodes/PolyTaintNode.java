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

package com.oracle.truffle.polyt.nodes;

import java.util.ArrayList;
import java.util.Iterator;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.instrumentation.Instrumenter;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.polyt.PolyTaint;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.interop.InteropLibrary;

/**
 * Node that "wraps" AST nodes of interest (nodes that correspond to secureL
 * statements/expressions). This is defined by the filter given to the
 * {@link Instrumenter} in
 * {@link PolyTaint#onCreate(com.oracle.truffle.api.instrumentation.TruffleInstrument.Env) }
 * ), and informs the {@link PolyTaint} that we
 * {@link PolyTaint#addTainted(SourceSection) tainted/marked} it's
 * {@link #instrumentedSourceSection source section}.
 */
public class PolyTaintNode extends ExecutionEventNode {

    protected PolyTaint instrument;
    // flag to determine already seen nodes
    @CompilerDirectives.CompilationFinal
    protected boolean seen;

    /**
     * Each node knows which {@link SourceSection} it instruments.
     */
    protected SourceSection instrumentedSourceSection;

    /**
     * Node being instrumented/wrapped by the instrumentation node
     */
    protected Node instrumentedNode;

    /**
     * Event context: gives us instrumented node, source section etc
     */
    protected EventContext context;

    /**
     * Corresponding root function for the instrumented node.
     */
    protected final String funcName;

    public PolyTaintNode(PolyTaint instrument, EventContext context) {
        this.instrument = instrument;
        this.instrumentedSourceSection = context.getInstrumentedSourceSection();
        this.instrumentedNode = context.getInstrumentedNode();
        this.funcName = context.getInstrumentedNode().getRootNode().getName();
    }

    @Override
    public void onReturnValue(VirtualFrame vFrame, Object result) {

        if (!seen) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            seen = true;
            instrument.addTainted(instrumentedSourceSection);
            String sectionName = instrumentedSourceSection.toString();
            String sourceName = instrumentedSourceSection.getSource().toString();
            String root1 = this.extractRootName(instrumentedNode);

            String root2 = instrumentedNode.getRootNode().getRootNode().getName();

            String name = "";

            System.out.println(">>> Event listener: name:" + name + " functionName: " + getFuncName());
        }
    }

    public EventContext getContext() {
        return context;
    }

    public String getFuncName() {
        return funcName;
    }

    public String extractRootName(Node instrumentedNode) {
        RootNode rootNode = instrumentedNode.getRootNode();
        if (rootNode != null) {
            if (rootNode.getName() == null) {
                return rootNode.toString();
            } else {
                return rootNode.getName();
            }
        } else {
            return "<unknown>";
        }
    }

    public boolean isBuiltin(Node instrumenteNode) {
        // return instrumenteNode instanceof JSBuiltinNode;
        return false;
    }

}
