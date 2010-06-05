/*
 * UPaintTime.java.java
 *
 * Created on 01-03-2010 01:33:52 PM
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
package com.colt.nicity.view.paint;

import com.colt.nicity.core.time.UTime;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.AFont;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.interfaces.ICanvas;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Administrator
 */
public class UPaintTime {

    /**
     *
     */
    public static int cL2R = 0;
    /**
     *
     */
    public static int cT2B = 1;
    static GregorianCalendar calendar = new GregorianCalendar();
    static final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    static final String[] days = new String[]{"zero", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    /**
     *
     * @param yearColor
     * @param monthColor
     * @param dayColor
     * @param textColor
     * @param _font
     * @param _g
     * @param _sTime
     * @param _time
     * @param _eTime
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _axis
     */
    public static void paint(
            final AColor yearColor, final AColor monthColor, final AColor dayColor,
            final AColor textColor, AFont _font,
            final ICanvas _g,
            final long _sTime, final long _time, final long _eTime,
            final int _x, final int _y, final int _w, final int _h,
            final int _axis) {
        AColor hourColor = AColor.orange;

        AFont font = UV.fonts[UV.cText];
        _g.setFont(font);

        long duration = _eTime - _sTime;
        int timeW = _h;

        double msecsPerPixel = (double) duration / (double) timeW;
        double pixelsPerMsec = 1.0d / msecsPerPixel;
        if (Double.isInfinite(msecsPerPixel)) {
            return;
        }
        if (Double.isInfinite(pixelsPerMsec)) {
            return;
        }

        int barHeight = _w / 4;

        // draw hours
        Hours:
        {
            _g.setColor(hourColor);
            boolean solid = false;
            int[] rect = new int[4];
            long offset = offsetToStartOfHour(_sTime);
            int w = (int) (((double) UTime.millisInAHour / (double) duration) * timeW);
            if (w > 1) {
                for (long t = -offset; t < duration; t += UTime.millisInAHour) {
                    solid = !solid;
                    int x = (int) (((double) t / (double) duration) * timeW);

                    String s = getHour(_sTime + t);
                    int sw = (int) font.getW(s);

                    rect(_g, solid,
                            0, x, barHeight, w,
                            //x,0,w,barHeight,
                            _x, _y, _w, _h, rect);
                    if (sw < rect[2] - rect[0]) {
                        _g.setColor(textColor);
                        _g.drawString(s, rect[0] + 2, rect[3] - 2);
                        _g.setColor(hourColor);
                    }
                }
            }
        }

        // draw days
        Days:
        {
            _g.setColor(dayColor);
            boolean solid = false;
            int[] rect = new int[4];
            long offset = offsetToStartOfDay(_sTime);
            int w = (int) (((double) UTime.millisInADay / (double) duration) * timeW);
            if (w > 1) {
                for (long t = -offset; t < duration; t += UTime.millisInADay) {
                    solid = !solid;
                    int x = (int) (((double) t / (double) duration) * timeW);

                    String s = getDay(_sTime + t);
                    int sw = (int) font.getW(s);

                    rect(_g, solid,
                            barHeight, x, barHeight, w,
                            //x,barHeight,w,barHeight,
                            _x, _y, _w, _h, rect);
                    if (sw < rect[2] - rect[0]) {
                        _g.setColor(textColor);
                        _g.drawString(s, rect[0] + 2, rect[3] - 2);
                        _g.setColor(dayColor);
                    }
                }
            }
        }

        // draw months
        Months:
        {
            _g.setColor(monthColor);
            boolean solid = false;
            int[] rect = new int[4];
            long offset = offsetToStartOfMonth(_sTime);
            int w = (int) (((double) UTime.millisInAMonth / (double) duration) * timeW);
            if (w > 1) {
                for (long t = -offset; t < duration; t += UTime.millisInAMonth) {
                    solid = !solid;
                    int x = (int) (((double) t / (double) duration) * timeW);

                    String s = getMonth(_sTime + t);
                    int sw = (int) font.getW(s);

                    rect(_g, solid,
                            barHeight * 2, x, barHeight, w,
                            //x,barHeight*2,w,barHeight,
                            _x, _y, _w, _h, rect);
                    if (sw < rect[2] - rect[0]) {
                        _g.setColor(textColor);
                        _g.drawString(s, rect[0] + 2, rect[3] - 2);
                        _g.setColor(monthColor);
                    }
                }
            }
        }

        // draw years
        Years:
        {
            _g.setColor(yearColor);
            boolean solid = false;
            int[] rect = new int[4];
            long offset = offsetToStartOfYear(_sTime);
            int w = (int) (((double) UTime.millisInAYear / (double) duration) * timeW);
            if (w > 1) {
                for (long t = -offset; t < duration; t += UTime.millisInAYear) {
                    solid = !solid;
                    int x = (int) (((double) t / (double) duration) * timeW);

                    String s = getYear(_sTime + t);
                    int sw = (int) font.getW(s);

                    rect(_g, solid,
                            barHeight * 3, x, barHeight, w,
                            //x,barHeight*3,w,barHeight,
                            _x, _y, _w, _h, rect);
                    if (sw < rect[2] - rect[0]) {
                        _g.setColor(textColor);
                        _g.drawString(s, rect[0] + 2, rect[3] - 2);
                        _g.setColor(yearColor);
                    }
                }
            }
        }
    }

    /**
     *
     * @param _g
     * @param _fill
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _cx
     * @param _cy
     * @param _cw
     * @param _ch
     * @param _rect
     */
    public static void rect(
            ICanvas _g, boolean _fill, int _x, int _y, int _w, int _h,
            int _cx, int _cy, int _cw, int _ch, int[] _rect) {
        int xpw = _x + _w;
        int yph = _y + _h;

        //if (_x < 0) _x = 0;
        //if (_y < 0) _y = 0;
        //if (xpw > _cw) xpw = _cw;
        //if (yph > _ch) yph = _ch;

        //if (xpw < 0) return;
        //if (yph < 0) return;


        double xp = (double) _x / (double) _cw;
        double yp = (double) _y / (double) _ch;

        double xpwp = (double) xpw / (double) _cw;
        double yphp = (double) yph / (double) _ch;

        int sx = (int) (xp * _cw);
        int sy = (int) (yp * _ch);
        int ex = (int) (xpwp * _cw);
        int ey = (int) (yphp * _ch);

        //_g.roundRect(false,_cx+sx,_cy+sy,ex-sx,ey-sy,10,10);
        //_g.arc(false,_cx+sx,_cy+sy,ex-sx,ey-sy,90,180);

        _g.rect(false, _cx + sx, _cy + sy, ex - sx, ey - sy);


        if (_rect != null) {
            _rect[0] = _cx + sx;
            _rect[1] = _cy + sy;
            _rect[2] = _cx + ex;
            _rect[3] = _cy + ey;
        }
    }

    /**
     *
     * @param _time
     * @return
     */
    public static String getMinute(long _time) {
        calendar.setTimeInMillis(_time);
        int v = calendar.get(Calendar.MINUTE);
        return "" + v;
    }

    /**
     *
     * @param _time
     * @return
     */
    public static String getHour(long _time) {
        calendar.setTimeInMillis(_time);
        int v = calendar.get(Calendar.HOUR);
        int ampm = calendar.get(Calendar.AM_PM);
        if (ampm == Calendar.AM) {
            return (v + 1) + "am";
        } else {
            return (v + 1) + "pm";
        }
    }

    /**
     *
     * @param _time
     * @return
     */
    public static String getDay(long _time) {
        calendar.setTimeInMillis(_time);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        return days[weekday] + " " + day;
    }

    /**
     *
     * @param _time
     * @return
     */
    public static String getMonth(long _time) {
        calendar.setTimeInMillis(_time);
        int month = calendar.get(Calendar.MONTH);
        return months[month];
    }

    /**
     *
     * @param _time
     * @return
     */
    public static String getYear(long _time) {
        calendar.setTimeInMillis(_time);
        int year = calendar.get(Calendar.YEAR);
        return "" + year;
    }

    /**
     *
     * @param _time
     * @return
     */
    public static long offsetToStartOfHour(long _time) {
        calendar.setTimeInMillis(_time);
        int h = calendar.get(Calendar.HOUR);
        if (h % 2 == 1) {
            h -= 1; // avoids constant rect color at left border
        }
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar = new GregorianCalendar(year, month, day, h, 0);
        long start = calendar.getTimeInMillis();
        return _time - start;
    }

    /**
     *
     * @param _time
     * @return
     */
    public static long offsetToStartOfDay(long _time) {
        calendar.setTimeInMillis(_time);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day % 2 == 1) {
            day -= 1; // avoids constant rect color at left border
        }
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar = new GregorianCalendar(year, month, day, 12, 0);
        long start = calendar.getTimeInMillis();
        return _time - start;
    }

    /**
     *
     * @param _time
     * @return
     */
    public static long offsetToStartOfMonth(long _time) {
        calendar.setTimeInMillis(_time);
        int month = calendar.get(Calendar.MONTH);
        if (month % 2 == 1) {
            month -= 1; // avoids constant rect color at left border
        }
        int year = calendar.get(Calendar.YEAR);
        calendar = new GregorianCalendar(year, month, 1, 12, 0);
        long start = calendar.getTimeInMillis();
        return _time - start;
    }

    /**
     *
     * @param _time
     * @return
     */
    public static long offsetToStartOfYear(long _time) {
        calendar.setTimeInMillis(_time);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        if (year % 2 == 1) {
            year -= 1; // avoids constant rect color at left border
        }
        calendar = new GregorianCalendar(year, 1, 1, 12, 0);
        long start = calendar.getTimeInMillis();
        return _time - start;
    }
}
