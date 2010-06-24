/*
 * VLog.java.java
 *
 * Created on 01-03-2010 01:34:45 PM
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
package colt.nicity.view.monitor;

import colt.nicity.view.list.VItem;
import colt.nicity.view.value.VToggle;
import colt.nicity.core.collection.CArray;
import colt.nicity.core.collection.CSet;
import colt.nicity.core.collection.keyed.KeyedValue;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.lang.UArray;
import colt.nicity.core.lang.UString;
import colt.nicity.core.lang.UText;
import colt.nicity.core.lang.UTrace;
import colt.nicity.core.time.UTime;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VPan;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IEmailer;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.ISizeable;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class VLog extends Viewer implements IOut, ISizeable {

    /**
     *
     */
    public static IEmailer logMailer;

    // Test
    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        // Make Gargage as fast as you can
        VChain c = new VChain(UV.cSN);
        c.add(new VLog("", 100, null));
        UV.exitFrame(c, "Test");
        //while (true) {
        //    VLog.out.out(URandom.rand(1000000));
        //}
        
    }
    /**
     *
     */
    public static VLog out;
    /**
     *
     */
    public static VLog err;
    Object key;
    Object[] lines;
    long[] threadIds;
    AColor[] lineColors;
    int insertAt = 0;
    File logFile;
    boolean enabled = true;

    /**
     *
     * @param _key
     * @param _capacity
     * @param _logFile
     */
    public VLog(Object _key, int _capacity, File _logFile) {
        this(_key, _capacity, 800, 600, _logFile);
    }

    /**
     *
     * @param _key
     * @param _capacity
     * @param _w
     * @param _h
     * @param _logFile
     */
    public VLog(Object _key, int _capacity, int _w, int _h, File _logFile) {

        key = _key;
        logFile = _logFile;
        setCapacity(_capacity, _w, _h);

        if (out == null) {
            out = this;
        }
        if (err == null) {
            err = this;
        }
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public void setSize(int _w, int _h) {
        //!! to do
    }

    /**
     *
     * @param _capacity
     * @param _w
     * @param _h
     */
    public void setCapacity(int _capacity, int _w, int _h) {
        lines = new Object[_capacity];
        threadIds = new long[_capacity];
        lineColors = new AColor[_capacity];
        VChain m = new VChain(UV.cEW);
        VToggle disable = new VToggle(" Logging ", enabled) {

            @Override
            public void picked(IEvent _e) {
                super.picked(_e);
                enabled = isTrue();
            }
        };
        disable.setBorder(null);
        m.add(disable);
        m.add(new VString(key));
        VItem clear = new VItem(" Clear ") {

            @Override
            public void picked(IEvent _e) {
                clear();
            }
        };
        m.add(clear);

        if (logMailer != null) {
            m.add(new VItem(" Report ") {

                @Override
                public void picked(IEvent _e) {
                    new Thread() {

                        @Override
                        public void run() {
                            Object[] copy = UArray.copy(lines);
                            String message = UString.toString(copy, "\n");
                            String[] tos = new String[]{logMailer.emailersAddress()};
                            logMailer.sendMail(null, "adk.VLog", tos, null, null, key + " Log", message);
                        }
                    }.start();
                }
            });
        }


        VChain c = new VChain(UV.cSN);
        c.add(m);
        RigidBox r = new RigidBox(1600, _capacity * 14) {

            @Override
            public void paintBorder(ICanvas _g, int _x, int _y, int _w, int _h) {
                _g.setColor(ViewColor.cTheme);
                _g.rect(true, _x, _y, _w, _h);
                _g.setFont(UV.fonts[UV.cText]);
                _g.setColor(ViewColor.cThemeFont);
                int y = 14;
                long[] _threadIds = threadIds;
                Object[] _lines = lines;
                AColor[] _colors = lineColors;
                int _insertAt = insertAt;
                for (int i = 0; i < _lines.length; i++) {
                    int getAt = _insertAt - i;
                    if (getAt < 0) {
                        getAt += lines.length;
                    }
                    if (getAt >= lines.length) {
                        continue;
                    }
                    if (_lines[getAt] != null) {
                        if (_colors[getAt] != null) {
                            _g.setColor(_colors[getAt]);
                        } else {
                            _g.setColor(ViewColor.cThemeFont);
                        }
                        _g.drawString(_lines[getAt].toString(), _x + 100, _y + y);
                        Double p = (Double) KeyedValue.get(threadProgress,_threadIds[getAt]);
                        if (p != null) {
                            _g.rect(true, _x, _y + y - 14, (int) (100 * p), 14);
                        }
                    }
                    y += 14;
                }
            }
        };
        c.add(UV.zone(new VPan(r, _w, _h)));
        setContent(c);
        layoutInterior();
        paint();
    }

    /**
     *
     * @return
     */
    public String[] toLines() {
        Object[] _lines = lines;
        int _insertAt = insertAt;
        CArray array = new CArray(String.class);
        for (int i = 0; i < _lines.length; i++) {
            int getAt = _insertAt - i;
            if (getAt < 0) {
                getAt += _lines.length;
            }
            if (getAt >= _lines.length) {
                continue;
            }
            if (_lines[getAt] != null) {
                array.insertLast(_lines[getAt].toString());
            }
            y += 14;
        }
        return (String[]) array.getAll();
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

    /**
     *
     */
    public void clear() {
        insertAt = 0;
        lines = new Object[lines.length];
        threadIds = new long[lines.length];
        lineColors = new AColor[lines.length];
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        return key;
    }
    CSet threadProgress = new CSet();

    /**
     *
     * @param _message
     */
    public void out(Object... _message) {
        if (!enabled || _message == null) {
            return;
        }
        for (Object m : _message) {
            if (m instanceof Throwable) {
                String[] trace = new UTrace((Throwable) m).getCalls();
                for (String t : trace) {
                    out(t);
                }
            } else {
                if (logFile != null) {
                    String string = UTime.basicTime(System.currentTimeMillis()) + ":" + m;
                    try {
                        UText.appendTextFile(string, logFile);
                    } catch (Exception x) {
                        x.printStackTrace();
                    }
                }
                int _insertAt = insertAt;
                if (_insertAt >= lines.length) {
                    insertAt = 0;
                    _insertAt = 0;
                }
                Thread ct = Thread.currentThread();
                KeyedValue.remove(threadProgress,threadIds[_insertAt]);//??
                threadIds[_insertAt] = ct.getId();
                lines[_insertAt] = m;
                lineColors[_insertAt] = AColor.getHashSolid(ct);
                insertAt += 1;
            }
        }
        paint();
    }

    /**
     *
     * @param _at
     * @param _outof
     */
    public void out(double _at, double _outof) {
        Thread ct = Thread.currentThread();
        KeyedValue.remove(threadProgress,ct.getId());
        KeyedValue.add(threadProgress,ct.getId(),(_at / _outof));
    }

    /**
     *
     * @return
     */
    public boolean canceled() {
        return false;
    }
}
