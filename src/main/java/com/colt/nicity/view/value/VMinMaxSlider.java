/*
 * VMinMaxSlider.java.java
 *
 * Created on 03-12-2010 06:39:20 PM
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

import com.colt.nicity.view.event.MousePressed;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class VMinMaxSlider extends VExpoSlider {//dg
    //!! inherits VSlider's IValue, but does not use it for anything

    /**
     *
     */
    protected double min = 0d;
    /**
     *
     */
    protected double max = 1d;

    /**
     *
     * @return
     */
    public double getMin() {
        return min;
    }

    /**
     *
     * @return
     */
    public double getMax() {
        return max;
    }

    /**
     *
     */
    public VMinMaxSlider() {
        this(new Value<Double>(0d), "", 100f, 18f, true);
    }

    /**
     *
     * @param _value
     * @param _title
     * @param _w
     * @param _h
     * @param _horizontal
     */
    public VMinMaxSlider(Value<Double> _value, Object _title, float _w, float _h, boolean _horizontal) {
        super(_value, _title, _w, _h, _horizontal);
    }

    /**
     *
     * @param _g
     * @param _tic
     * @param _w
     * @param _h
     */
    public void paintTic(ICanvas _g, int _tic, int _w, int _h) {
    } // override super

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    @Override
    public void paintBackground(ICanvas _g, int _x, int _y, int _w, int _h) {
        super.paintBackground(_g, _x, _y, _w, _h);

        if (horizontal) {
            int x1 = (int) (min * (jw));
            int x2 = (int) (max * (jw)) - 1; // -1 to show max=1.0
            int mid = (x1 + x2) / 2;
            if (bar) {
                _g.setColor(ViewColor.cThemeShadow);
                _g.rect(true, _x + x1, _y + (_h / 4), x2 - x1, (int) (h / 2));
                _g.setColor(ViewColor.cTheme);
                _g.rect(false, _x + x1, _y + (_h / 4), x2 - x1, (int) (h / 2));
                if (midline) {
                    _g.rect(true, _x + mid - 1, _y + (_h / 4), 2, (int) (h / 2));
                }
            } else {// tic
                _g.setColor(ticColor);
                _g.line(_x + x1, _y + (_h / 4), _x + x1, _y + (int) (h / 2));
                _g.line(_x + x2, _y + (_h / 4), _x + x2, _y + (int) (h / 2));
                if (midline) {
                    _g.setColor(ViewColor.cTheme);
                    _g.rect(true, _x + mid - 1, _y + (_h / 4), 2, (int) (h / 2));
                }
            }
        } else {// vertical
            int y1 = (int) (jh - min * (jh)) - 1; // -1 to show min=0.0
            int y2 = (int) (jh - max * (jh));
            int mid = (y1 + y2) / 2;
            if (bar) {//
                _g.setColor(ViewColor.cThemeShadow);
                _g.rect(true, _x + (_w / 4), _y + y2, (int) (w / 2), (y1 - y2));
                _g.setColor(ViewColor.cTheme);
                _g.rect(false, _x + (_w / 4), _y + y2, (int) (w / 2), (y1 - y2));
                if (midline) {
                    _g.rect(true, _x + (_w / 4), _y + mid - 1, (int) (w / 2), 2);
                }
            } else {// tic
                _g.setColor(ticColor);
                _g.line(_x + (_w / 4), _y + y1, _x + (int) (w / 2), _y + y1);
                _g.line(_x + (_w / 4), _y + y2, _x + (int) (w / 2), _y + y2);
                if (midline) {
                    _g.setColor(ViewColor.cTheme);
                    _g.rect(true, _x + (_w / 4), _y + mid - 1, (int) (w / 2), 2);
                }
            }
        }
    }
    static final int cAdjustMin = 1;
    static final int cAdjustMax = 2;
    static final int cAdjustMinMax = 3;
    // mousePressed latching vars
    /**
     *
     */
    protected int adjust = 0;
    /**
     *
     */
    protected int xymin = 0;
    /**
     *
     */
    protected int xymax = Integer.MAX_VALUE;

    /**
     *
     * @param _e
     */
    @Override
    public void mousePressed(MousePressed _e) {
        adjust = 0;
        XY_I p = _e.getPoint();
        int xy;
        if (horizontal) {
            xy = p.x;
            xymin = (int) (min * (jw));
            xymax = (int) (max * (jw));
        } else {
            xy = (int) (jh - p.y);
            xymin = (int) (min * (jh));
            xymax = (int) (max * (jh));
        }

        int mid = xymin + ((xymax - xymin) / 2);
        if (xy < mid + 4 && xy > mid - 4) {
            adjust = cAdjustMinMax; // 8-pixel-wide GUI target
        } else if (xy < mid) {
            adjust = cAdjustMin;
        } else {
            adjust = cAdjustMax;
        }

        select(_e.getPoint());
        paint();
    }

    /**
     *
     * @param p
     */
    @Override
    public void select(XY_I p) {
        double xy; // f(p.x or p.y)
        double wh; // f(w or h)

        if (horizontal) {
            xy = p.x;
            wh = jw;
        } else {// vertical
            xy = (int) (jh - p.y); // flip
            wh = jh;
        }

        if (xy >= wh || xy < 0) {
            return;
        }

        if (adjust == cAdjustMinMax) {
            // insure (max-min)>0
            if (xy >= wh - 1 || xy < 1) {
                return;
            }
            min = (xy - 1) / wh;
            max = (xy + 1) / wh;
        } else if (adjust == cAdjustMin) {
            if (xy > xymax) {
                adjust = cAdjustMinMax;
                return;
            }
            min = xy / wh;
        } else if (adjust == cAdjustMax) {
            if (xy < xymin) {
                adjust = cAdjustMinMax;
                return;
            }
            max = xy / wh;
        }
        paint();
    }
}
