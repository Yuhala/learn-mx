
/*
 * Created on Wed Mar 21 2022
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
 * Specialized instrumentation nodes for javascript ASTs. These nodes take into
 * consideration language(js)-specific semantics when instrumenting the AST
 * as opposed to the more generic PolyTaintNodes which instrument generic
 * Truffle AST nodes.
 */
public final class JSInstrumentNode extends PolyTaintNode {

    public JSInstrumentNode(PolyTaint instrument, EventContext context) {       
        super(instrument, context);        
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

            System.out.println(">>> Event listener: name:" + name + " root-name: " + root1);
        }
    }
}
