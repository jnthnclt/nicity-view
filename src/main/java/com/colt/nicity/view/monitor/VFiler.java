/*
 * VFiler.java.java
 *
 * Created on 03-12-2010 06:42:56 PM
 *
 * Copyright 2010 Jonathan Colt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.colt.nicity.view.monitor;

import com.colt.nicity.view.border.ViewBorder;
import com.colt.nicity.core.time.RateMonitor;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.AWindow;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.VIndent;
import com.colt.nicity.view.core.ViewString;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VFiler extends Viewer {

    /**
     *
     */
    public static VFiler _;

    static {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        if (_ != null) {
                            _.pulse();
                        }
                    } catch (Exception x) {
                    }
                }
            }
        };
        t.setPriority(2);
        t.start();
    }

    /**
     *
     * @param _diskSeeks
     * @param _diskReads
     * @param _diskWrites
     * @param _socketRequests
     * @param _socketResponses
     * @param _socketReads
     * @param _socketWrites
     * @param _supplement
     */
    public static void monitor(
            Value _diskSeeks,Value _diskReads,Value _diskWrites, Value _socketRequests,Value _socketResponses,Value _socketReads,Value _socketWrites,
            IView _supplement) {
        if (_ != null) {
            _.close();
        }
        _ = new VFiler(_diskSeeks, _diskReads, _diskWrites, _socketRequests, _socketResponses, _socketReads, _socketWrites, _supplement);
        _.toFront();
    }
    
    RateMonitor socketReadRate = new RateMonitor("r", false);
    RateMonitor socketWriteRate = new RateMonitor("w", false);
    RateMonitor diskReadRate = new RateMonitor("r", false);
    RateMonitor diskWriteRate = new RateMonitor("w", false);
    RateMonitor seekRate = new RateMonitor("seeks", false);
    RateMonitor clientConversationRate = new RateMonitor("r Conversations", false);
    RateMonitor serverConversationRate = new RateMonitor("w Conversations", false);

    Value diskSeeks;
    Value diskReads;
    Value diskWrites;
    Value socketRequests;
    Value socketResponses;
    Value socketReads;
    Value socketWrites;

    /**
     *
     * @param _diskSeeks
     * @param _diskReads
     * @param _diskWrites
     * @param _socketRequests
     * @param _socketResponses
     * @param _socketReads
     * @param _socketWrites
     * @param _supplement
     */
    public VFiler(Value _diskSeeks,Value _diskReads,Value _diskWrites, Value _socketRequests,Value _socketResponses,Value _socketReads,Value _socketWrites, IView _supplement) {
        _ = this;

        diskSeeks = _diskSeeks;
        diskReads = _diskReads;
        diskWrites = _diskWrites;

        socketRequests = _socketRequests;
        socketResponses = _socketResponses;
        socketReads = _socketReads;
        socketReads = _socketWrites;
       
        socketReadRate.setUnits("MB", 1024 * 1024);
        socketWriteRate.setUnits("MB", 1024 * 1024);


        diskReadRate.setUnits("MB", 1024 * 1024);
        diskWriteRate.setUnits("MB", 1024 * 1024);
        seekRate.setUnits("Kilo", 1000);

        clientConversationRate.setUnits("", 1);
        serverConversationRate.setUnits("", 1);


        VChain rates = new VChain(UV.cSWNW);

        VChain m = new VChain(UV.cSWNW);
       
        VChain c = new VChain(UV.cSWNW);
        c.add(new ViewString(seekRate, UV.fonts[UV.cSmall]));
        c.add(new ViewString(diskReadRate, UV.fonts[UV.cSmall]));
        c.add(new ViewString(diskWriteRate, UV.fonts[UV.cSmall]));
        VChain vc = new VChain(UV.cSWNW, new ViewString("Disk IO", UV.fonts[UV.cSmall]), new VIndent(5, c));
        vc.spans(UV.cXEW);
        m.add(vc);
    
        c = new VChain(UV.cSWNW);
        c.add(new ViewString(clientConversationRate, UV.fonts[UV.cSmall]));
        c.add(new ViewString(serverConversationRate, UV.fonts[UV.cSmall]));
        c.add(new ViewString(socketReadRate, UV.fonts[UV.cSmall]));
        c.add(new ViewString(socketWriteRate, UV.fonts[UV.cSmall]));

        vc = new VChain(UV.cSWNW, new ViewString("Network IO", UV.fonts[UV.cSmall]), new VIndent(5, c));
        vc.spans(UV.cXEW);
        m.add(vc);
        m.spans(UV.cXE);
        rates.add(m);

        rates.setBorder(new ViewBorder());
        rates.spans(UV.cXEW);

        VChain main = new VChain(UV.cSN);
        main.add(rates, UV.cFII);
        main.spans(UV.cXEW);
        if (_supplement != null) {
            main.add(_supplement);
        }
        setContent(main);
    }

    /**
     *
     */
    public void pulse() {

        if (diskSeeks != null) {
            seekRate.count(diskSeeks.longValue());
            seekRate.calculate();
        }

        if (diskWrites != null) {
            diskWriteRate.count(diskWrites.longValue());
            diskWriteRate.calculate();
        }

        if (diskReads != null) {

            diskReadRate.count(diskReads.longValue());
            diskReadRate.calculate();
        }
        if (socketWrites != null) {

            socketWriteRate.count(socketWrites.longValue());
            socketWriteRate.calculate();
        }
        if (socketReads != null) {

            socketReadRate.count(socketReads.longValue());
            socketReadRate.calculate();
        }

        if (socketResponses != null) {

            clientConversationRate.count(socketResponses.longValue());
            clientConversationRate.calculate();
        }
        if (socketRequests != null) {

            serverConversationRate.count(socketRequests.longValue());
            serverConversationRate.calculate();
        }

        repair();
        flush();
    }
    AWindow window;

    /**
     *
     */
    public void toFront() {
        AWindow _window = window;
        if (_window == null || _window.closed()) {
            window = UV.frame(this, "IO Monitor", true, false);
        } else {
            _window.toFront();
        }
    }

    /**
     *
     */
    public void close() {
        if (window != null) {
            window.dispose();
            window = null;
        }
    }
}
