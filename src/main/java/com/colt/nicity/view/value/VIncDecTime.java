/*
 * VIncDecTime.java.java
 *
 * Created on 03-12-2010 06:39:30 PM
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
package com.colt.nicity.view.value;

import com.colt.nicity.view.border.ViewBorder;
import com.colt.nicity.view.list.AItem;
import com.colt.nicity.core.time.UTime;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VButton;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.VString;
import com.colt.nicity.view.interfaces.IEvent;

/**
 *
 * @author Jonathan Colt
 * Mar 11, 2008
 */
public class VIncDecTime extends AItem {

    Value<Long> time;

    /**
     *
     * @param _time
     * @param _y
     * @param _m
     * @param _d
     * @param _h
     * @param _min
     * @param _s
     * @param _f
     */
    public VIncDecTime(Value<Long> _time, boolean _y, boolean _m, boolean _d, boolean _h, boolean _min, boolean _s, boolean _f) {
        time = _time;
        VChain c = new VChain(UV.cEW);
        if (_m) {
            Object s = new Object() {

                @Override
                public String toString() {
                    return UTime.month(time.longValue());
                }
            };
            VChain v = new VChain(UV.cSN);
            v.add(new VButton("-") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.monthLong(time.longValue(), -1));
                }
            });
            v.add(new VString(s));
            v.add(new VButton("+") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.monthLong(time.longValue(), 1));
                }
            });
            c.add(v);
            if (_d) {
                c.add(new VString("/"));
            }
        }
        if (_d) {
            Object s = new Object() {

                @Override
                public String toString() {
                    return UTime.day(time.longValue());
                }
            };
            VChain v = new VChain(UV.cSN);
            v.add(new VButton("-") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.dayLong(time.longValue(), -1));
                }
            });
            v.add(new VString(s));
            v.add(new VButton("+") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.dayLong(time.longValue(), 1));
                }
            });
            c.add(v);
            if (_y) {
                c.add(new VString("/"));
            }
        }
        if (_y) {
            Object s = new Object() {

                @Override
                public String toString() {
                    return UTime.year(time.longValue());
                }
            };
            VChain v = new VChain(UV.cSN);
            v.add(new VButton("-") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.yearLong(time.longValue(), -1));
                }
            });
            v.add(new VString(s));
            v.add(new VButton("+") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.yearLong(time.longValue(), 1));
                }
            });
            c.add(v);

        }

        c.add(new VString(" "));

        if (_h) {
            Object s = new Object() {

                @Override
                public String toString() {
                    return UTime.hour(time.longValue());
                }
            };
            VChain v = new VChain(UV.cSN);
            v.add(new VButton("-") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.hourLong(time.longValue(), -1));
                }
            });
            v.add(new VString(s));
            v.add(new VButton("+") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.hourLong(time.longValue(), 1));
                }
            });
            c.add(v);
            if (_min) {
                c.add(new VString(":"));
            }
        }
        if (_min) {
            Object s = new Object() {

                @Override
                public String toString() {
                    return UTime.minute(time.longValue());
                }
            };
            VChain v = new VChain(UV.cSN);
            v.add(new VButton("-") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.minuteLong(time.longValue(), -1));
                }
            });
            v.add(new VString(s));
            v.add(new VButton("+") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.minuteLong(time.longValue(), 1));
                }
            });
            c.add(v);
            if (_s) {
                c.add(new VString(":"));
            }
        }
        if (_s) {
            Object s = new Object() {

                @Override
                public String toString() {
                    return UTime.second(time.longValue());
                }
            };
            VChain v = new VChain(UV.cSN);
            v.add(new VButton("-") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.secondLong(time.longValue(), -1));
                }
            });
            v.add(new VString(s));
            v.add(new VButton("+") {

                @Override
                public void picked(IEvent _e) {
                    time.setValue(UTime.secondLong(time.longValue(), 1));
                }
            });
            c.add(v);
        }
        setContent(c);
        setBorder(new ViewBorder());
    }

    /**
     *
     */
    @Override
    public void mend() {
        enableFlag(UV.cRepair);//??
        super.mend();
    }

    /**
     *
     * @param _e
     */
    @Override
    public void picked(IEvent _e) {
        UV.popup(this, UV.cCC, new VDate(time), false);
    }
}
