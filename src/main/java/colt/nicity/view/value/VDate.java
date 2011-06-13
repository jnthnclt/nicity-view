/*
 * VDate.java.java
 *
 * Created on 03-12-2010 06:41:29 PM
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

import colt.nicity.view.border.ItemBorder;
import colt.nicity.view.list.AItem;
import colt.nicity.view.list.VItem;
import colt.nicity.core.time.UTime;
import colt.nicity.core.value.IValue;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VFixed;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.ViewString;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.rpp.IRPPViewable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * @author Administrator
 */
public class VDate extends AItem implements IRPPViewable {
    
    public static IView viewable(String[] args) {
        ViewColor.onBlack();
        return new VDate(new Value(UTime.currentGMT()));
    }
    
    

    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        ViewColor.onBlack();
        UV.exitFrame(viewable(_args), "Date");
    }
    IValue value;
    Viewer c = new Viewer();

    /**
     *
     * @param _value
     */
    public VDate(IValue _value) {
        value = _value;
        setContent(c);
        refresh();
    }
    private long lastTime = 0;

    /**
     *
     * @param g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    @Override
    public void paintBorder(ICanvas g, int _x, int _y, int _w, int _h) {
        super.paintBorder(g, _x, _y, _w, _h);
        if (!value.getValue().equals(lastTime)) {
            refresh();
        }
    }

    /**
     *
     */
    public void refresh() {
        long time = (Long) value.getValue();
        lastTime = time;
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(time);

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        calendar.set(year, month, 1);

        int[][] dayTable = new int[7][6];
        long[][] timesTable = new long[7][6];
        for (int w = 0; w < 6; w++) {
            for (int d = 0; d < 7; d++) {
                dayTable[d][w] = -1;
            }
        }

        for (int d = 1; d < 32; d++) {

            calendar.set(year, month, d);
            long daysTime = calendar.getTimeInMillis();
            int _month = calendar.get(Calendar.MONTH);
            if (_month != month) {
                break;
            }

            int dom = calendar.get(Calendar.DAY_OF_MONTH);
            int dow = calendar.get(Calendar.DAY_OF_WEEK);
            int wom = calendar.get(Calendar.WEEK_OF_MONTH);

            dayTable[dow - 1][wom - 1] = dom;
            timesTable[dow - 1][wom - 1] = daysTime;
        }

        VChain chain = new VChain(UV.cSN);

        int _month = 0;
        VChain yearLabel = new VChain(UV.cEW);
        for (int y = -5; y < 5; y++) {
            calendar.set(year + y, _month, day);
            final long yearTime = calendar.getTimeInMillis();
            IView button = time(yearTime, year + y, 40);
            yearLabel.add(button);
            if (y == 0) {
                button.setBorder(new ItemBorder(ViewColor.cThemeSelected));
            }
        }

        chain.add(UV.zone("Years", yearLabel));


        String[] m1 = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        VChain monthLabels1 = new VChain(UV.cEW);
        for (int m = 0; m < 12; m++) {
            calendar.set(year, _month, day);
            final long monthsTime = calendar.getTimeInMillis();
            IView button = time(monthsTime, m1[m], 32);
            monthLabels1.add(button);
            if (_month == month) {
                button.setBorder(new ItemBorder(ViewColor.cThemeSelected));
            }
            _month++;

        }
        chain.add(UV.zone("Months", monthLabels1));


        VChain days = new VChain(UV.cSN);

        int weekGridW = 50;
        String[] dow = new String[]{"S", "M", "T", "W", "T", "F", "S"};
        VChain weekLabel = new VChain(UV.cEW);
        for (int d = 0; d < 7; d++) {
            weekLabel.add(wrap(dow[d], weekGridW));
        }
        days.add(weekLabel);
        for (int w = 0; w < 6; w++) {
            VChain week = new VChain(UV.cEW);
            for (int d = 0; d < 7; d++) {
                int v = dayTable[d][w];
                if (v == -1) {
                    week.add(time(0l, "", weekGridW));
                } else {
                    final long dayTime = timesTable[d][w];
                    IView dayItem = time(dayTime, dayTable[d][w], weekGridW);
                    week.add(dayItem);
                    if (day == v) {
                        dayItem.setBorder(new ItemBorder(ViewColor.cThemeSelected));
                    }
                }
            }
            days.add(week);
        }
        chain.add(UV.zone("Days", days));


        VChain hoursLabel = new VChain(UV.cEW);
        for (int h = hour - 5; h < hour + 5; h++) {
            if (h < 0 || h > 24) {
                continue;
            }
            String hn = "" + h;

            calendar.set(year, month, day, h, minute, second);
            long t = calendar.getTimeInMillis();
            IView vi = time(t, hn, 40);
            hoursLabel.add(vi);
            if (h == hour) {
                vi.setBorder(new ItemBorder(ViewColor.cThemeSelected));
            }
        }
        chain.add(UV.zone("Hours", hoursLabel));

        VChain minLabel = new VChain(UV.cEW);
        for (int m = minute - 10; m < minute + 10; m++) {
            if (m < 0 || m > 59) {
                continue;
            }
            calendar.set(year, month, day, hour, m, second);
            long t = calendar.getTimeInMillis();
            IView vi = time(t, m, 20);
            minLabel.add(vi);
            if (m == minute) {
                vi.setBorder(new ItemBorder(ViewColor.cThemeSelected));
            }
        }
        chain.add(UV.zone("Minutes", minLabel));

        VChain secLabel = new VChain(UV.cEW);
        for (int s = second - 10; s < second + 10; s++) {
            if (s < 0 || s > 59) {
                continue;
            }
            calendar.set(year, month, day, hour, minute, s);
            long t = calendar.getTimeInMillis();
            IView vi = time(t, s, 20);
            secLabel.add(vi);
            if (s == second) {
                vi.setBorder(new ItemBorder(ViewColor.cThemeSelected));
            }
        }
        chain.add(UV.zone("Seconds", secLabel));

        VChain tools = new VChain(UV.cEW);
        tools.add(UV.border(new VButton("Today") {

            @Override
            public void picked(IEvent _e) {
                value.setValue(UTime.currentGMT());
            }
        }, new ItemBorder()));

        chain.add(UV.zone("Tools", tools));

        c.setView(UV.zones(chain));
    }

    final private IView time(final long _time, Object _o, int _w) {
        return new VItem(wrap(_o, _w), _time) {

            @Override
            public void picked(IEvent _e) {
                setDate((Long) getValue());
            }
        };
    }

    final private IView wrap(Object _o, int _w) {
        return new VFixed(new ViewString(_o, UV.fonts[UV.cTitle]), _w, 20);
    }

    /**
     *
     * @param _date
     */
    public void setDate(long _date) {
        value.setValue(_date);
        refresh();
    }
}
