/*
 * VElapse.java.java
 *
 * Created on 03-12-2010 06:40:06 PM
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
package colt.nicity.view.value;

import colt.nicity.view.border.ButtonBorder;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.event.WindowActivated;
import colt.nicity.view.event.WindowClosed;
import colt.nicity.view.event.WindowDeactivated;
import colt.nicity.view.event.WindowDeiconified;
import colt.nicity.view.event.WindowIconified;
import colt.nicity.view.event.WindowOpened;
import colt.nicity.view.list.AItem;
import colt.nicity.view.list.VItem;
import colt.nicity.view.list.VMenu;
import colt.nicity.core.collection.CArray;
import colt.nicity.core.time.UTime;
import colt.nicity.core.value.IValue;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.ViewString;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IWindowEvents;

/**
 *
 * @author Administrator
 */
public class VElapse extends AItem implements IWindowEvents {

    IValue value;
    long elapse;
    AFont font = UV.fonts[UV.cText];
    private VMenu years;
    private VMenu months;
    private VMenu days;
    private VMenu hours;
    private VMenu minutes;
    private VMenu seconds;

    /**
     *
     * @param _value
     */
    public VElapse(IValue _value) {
        this(_value, UV.fonts[UV.cText]);
    }

    /**
     *
     * @param _value
     * @param _font
     */
    public VElapse(IValue _value, AFont _font) {
        value = _value;
        font = _font;
        elapse = ((Long) _value.getValue()).longValue();
        setElapse(elapse);
    }

    /**
     *
     * @param _elapse
     */
    public void setElapse(long _elapse) {

        long e = _elapse;
        long YY = e / UTime.millisInAYear;
        e -= YY * UTime.millisInAYear;
        long DD = e / UTime.millisInADay;
        e -= DD * UTime.millisInADay;
        long hh = e / UTime.millisInAHour;
        e -= hh * UTime.millisInAHour;
        long mm = e / UTime.millisInAMinute;
        e -= mm * UTime.millisInAMinute;
        long ss = e / UTime.millisInASecond;
        e -= ss * UTime.millisInASecond;


        CArray _days = new CArray();
        for (int i = 0; i < 365; i++) {
            _days.insertLast(new VItem(new ViewString(i + " days"), new Integer(i)) {

                public void picked(IEvent _e) {
                    changeElapse(-1, ((Integer) getValue()).intValue(), -1, -1, -1);
                }
            });
        }
        days = new VMenu(this, "days", _days, new ViewString(String.valueOf(DD) + "d ", font));
        days.setBorder(new ButtonBorder());


        CArray _years = new CArray();
        for (int i = ((int) YY - 10); i < ((int) YY + 10); i++) {
            if (i >= 0) {
                _years.insertLast(new VItem(new ViewString(i + " years"), new Integer(i)) {

                    public void picked(IEvent _e) {
                        changeElapse(((Integer) getValue()).intValue(), -1, -1, -1, -1);
                    }
                });
            }
        }
        years = new VMenu(this, "years", _years, new ViewString(String.valueOf(YY) + "y ", font));
        years.setBorder(new ButtonBorder());


        CArray _hours = new CArray();
        for (int i = 0; i < 24; i++) {
            _hours.insertLast(new VItem(new ViewString(i + " hours"), new Integer(i)) {

                public void picked(IEvent _e) {
                    changeElapse(-1, -1, ((Integer) getValue()).intValue(), -1, -1);
                }
            });
        }
        hours = new VMenu(this, "hours", _hours, new ViewString(String.valueOf(hh) + ":", font));
        hours.setBorder(new ButtonBorder());

        CArray _minutes = new CArray();
        for (int i = 0; i < 60; i++) {
            _minutes.insertLast(new VItem(new ViewString(i + " minutes"), new Integer(i)) {

                public void picked(IEvent _e) {
                    changeElapse(-1, -1, -1, ((Integer) getValue()).intValue(), -1);
                }
            });
        }
        minutes = new VMenu(this, "minutes", _minutes, new ViewString(String.valueOf(mm) + ":", font));
        minutes.setBorder(new ButtonBorder());

        CArray _seconds = new CArray();
        for (int i = 0; i < 60; i++) {
            _seconds.insertLast(new VItem(new ViewString(i + " seconds"), new Integer(i)) {

                public void picked(IEvent _e) {
                    changeElapse(-1, -1, -1, -1, ((Integer) getValue()).intValue());
                }
            });
        }
        seconds = new VMenu(this, "seconds", _seconds, new ViewString(String.valueOf(ss) + "", font));
        seconds.setBorder(new ButtonBorder());


        VChain elapse = new VChain(UV.cEW);

        elapse.add(years);
        elapse.add(days);
        elapse.add(hours);
        elapse.add(minutes);
        elapse.add(seconds);


        setPlacer(new Placer(elapse));
        setBorder(new ViewBorder());
        paint();
    }

    /**
     *
     * @param _yy
     * @param _dd
     * @param _hh
     * @param _mm
     * @param _ss
     */
    public void changeElapse(int _yy, int _dd, int _hh, int _mm, int _ss) {
        long e = elapse;
        long YY = e / UTime.millisInAYear;
        e -= YY * UTime.millisInAYear;
        long DD = e / UTime.millisInADay;
        e -= DD * UTime.millisInADay;
        long hh = e / UTime.millisInAHour;
        e -= hh * UTime.millisInAHour;
        long mm = e / UTime.millisInAMinute;
        e -= mm * UTime.millisInAMinute;
        long ss = e / UTime.millisInASecond;
        e -= ss * UTime.millisInASecond;

        if (_yy > -1) {
            YY = _yy;
        }
        if (_dd > -1) {
            DD = _dd;
        }
        if (_hh > -1) {
            hh = _hh;
        }
        if (_mm > -1) {
            mm = _mm;
        }
        if (_ss > -1) {
            ss = _ss;
        }


        elapse = (YY * UTime.millisInAYear)
                + (DD * UTime.millisInADay)
                + (hh * UTime.millisInAHour)
                + (mm * UTime.millisInAMinute)
                + (ss * UTime.millisInASecond);

        if (elapse < 0) {
            elapse = 0;
        }
        value.setValue(new Long(elapse));
        setElapse(elapse);

    }

    @Override
    public String toString() {
        return UTime.elapse(elapse);//+" "+UTime.fixedWidthTime(elapse);
    }

    // IWindowEvents
    /**
     *
     * @param _e
     */
    public void windowOpened(WindowOpened _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowClosed(WindowClosed _e) {
        value.setValue(new Long(elapse));
    }

    /**
     *
     * @param _e
     */
    public void windowActivated(WindowActivated _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowDeactivated(WindowDeactivated _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowIconified(WindowIconified _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowDeiconified(WindowDeiconified _e) {
    }
}
