/*
 * VSlider.java.java
 *
 * Created on 03-12-2010 06:38:20 PM
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
import com.colt.nicity.view.event.MouseDragged;
import com.colt.nicity.view.event.MouseEntered;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MouseMoved;
import com.colt.nicity.view.event.MousePressed;
import com.colt.nicity.view.event.MouseReleased;
import com.colt.nicity.view.flavor.SliderBarFlavor;
import com.colt.nicity.view.list.AItem;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.Placer;
import com.colt.nicity.view.core.RigidBox;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IMouseMotionEvents;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VSlider extends AItem implements Comparable, IMouseMotionEvents {

    /**
     *
     */
    public static boolean cPlus = true;
    /**
     *
     */
    public static boolean cMinus = false;
    /**
     *
     */
    protected Value<Double> value;
    /**
     *
     */
    protected Object title;
    /**
     *
     */
    protected float jw;
    /**
     *
     */
    protected float jh;
    /**
     *
     */
    protected boolean horizontal = false;
    /**
     *
     */
    protected boolean sign = cPlus;
    /**
     *
     */
    public boolean bar = true;
    /**
     *
     */
    public boolean midline = true;//dg
    /**
     *
     */
    public AColor ticColor = AColor.red;//dg

    /**
     *
     * @param _value
     * @param _title
     * @param _w
     * @param _h
     * @param _horizontal
     */
    public VSlider(Value<Double> _value, Object _title, float _w, float _h, boolean _horizontal) {
        this(_value, _title, _w, _h, _horizontal, true);
    }

    /**
     *
     * @param _value
     * @param _title
     * @param _w
     * @param _h
     * @param _horizontal
     * @param _bar
     */
    public VSlider(Value<Double> _value, Object _title, float _w, float _h, boolean _horizontal, boolean _bar) {
        value = _value;
        title = _title;
        if (value.doubleValue() < 0) {
            sign = cMinus;
        } else {
            sign = cPlus;
        }
        jw = _w;
        jh = _h;
        horizontal = _horizontal;
        setBorder(new ViewBorder());
        init();//dg one time init; removed from select()
    }

    /**
     *
     * @param _value
     */
    public void setValue(Value<Double> _value) {
        value = _value;
    }

    @Override
    public String toString() {
        return title.toString();
    }

    /**
     *
     * @return
     */
    public IView init() {
        Viewer draw = new Viewer(new RigidBox(jw, jh));
        setPlacer(new Placer(new Viewer(draw)));
        return this;
    }

    /**
     *
     * @param _g
     * @param _tic
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public void paintTic(ICanvas _g, int _tic, int _x, int _y, int _w, int _h) {//dg
        if (horizontal) {
            if (midline) {
                _g.setColor(ViewColor.cTheme);
                _g.line(_x + (_w / 2), _y + 0, _x + (_w / 2), _y + _h);
            }
            _g.setColor(ticColor);
            _g.line(_x + _tic, _y + 0, _x + _tic, _y + _h);
            if (title != null) {
                _g.setColor(ViewColor.cThemeFont);
                _g.drawString(title.toString(), _x + 2, _y + _h - 2);
            }
        } else {
            if (midline) {
                _g.setColor(ViewColor.cTheme);
                _g.line(_x + 0, _y + (_h / 2), _x + _w, _y + (_h / 2));
            }
            _g.setColor(ticColor);
            _g.line(0, _tic, _w, _tic);
        }
    }

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

        jw = _w;
        jh = _h;

        double _value = value.doubleValue();
        if (_value < 0) {
            sign = cMinus;
        } else {
            sign = cPlus;
        }


        if (horizontal) {
            if (!sign) {
                _value = -_value;
            }
            int w = (int) (_value * (jw));
            if (bar) {
                int hh = _h / 2;
                _g.setColor(ViewColor.cButtonThemeShadow);
                _g.roundRect(false, _x + 0, _y + hh - 2, _w - 1, 4, 2, 2);

                _g.setColor(ViewColor.cThemeSelected);
                _g.roundRect(true, _x + 0, _y + hh - 2, w - 1, 4, 3, 3);
                _g.setColor(ViewColor.cThemeHighlight);
                _g.roundRect(false, _x + 0, _y + hh - 2, w - 1, 4, 3, 3);


                SliderBarFlavor.pointingUp.paintFlavor(_g, _x + w - 6, _y + hh, 12, hh, ViewColor.cThemeSelected);

                if (title != null) {
                    _g.setFont(UV.fonts[UV.cSmall]);
                    _g.setColor(ViewColor.cThemeFont);
                    _g.drawString(title.toString(), _x + 2, _y + _h - 2);
                }

            } else { // if tic
                paintTic(_g, w, _x, _y, _w, _h);
            }

        } else {

            if (!sign) {
                _value = -_value;
            }
            int h = (int) (_value * jh);
            if (bar) {
                _g.setColor(ViewColor.cThemeSelected);
                _g.rect(true, _x + (_w / 4), _y + (int) (jh - h), (int) (w / 2), h);
                _g.setColor(ViewColor.cTheme);
                _g.rect(false, _x + (_w / 4), _y + (int) (jh - h), (int) (w / 2), h);

                if (title != null) {
                    _g.setFont(UV.fonts[UV.cSmall]);
                    _g.setColor(ViewColor.cThemeFont);
                    _g.drawString(title.toString(), _x + 2, _y + _h - 2);
                }

            } else { // if tic
                paintTic(_g, (int) (jh - h), _x, _y, _w, _h);//dg reverse y axis
            }

        }
    }

    /**
     *
     * @param p
     */
    public void select(XY_I p) {
        double _value = value.doubleValue();
        if (horizontal) {
            if (p.x > jw) {
                _value = 1;
                return;
            } else if (p.x < 0) {
                _value = 0;
                return;
            } else {
                _value = ((double) p.x / (double) (jw));
            }
        } else {
            if (p.y > jh) {
                _value = 1;
                return;
            } else if (p.y < 0) {
                _value = 0;
                return;
            } else {
                _value = (double) p.y / (double) (jh);
            }
            _value = 1d - _value;
        }
        if (sign) {
            value.setValue(new Double(_value));
        } else {
            value.setValue(new Double(-_value));
        }
        paint();//dg removed init because it creates many new objects for every drag event
    }

    /**
     *
     * @return
     */
    @Override
    public Object getValue() {
        return this;
    }

    // IMouseEvents
    /**
     *
     * @param _e
     */
    @Override
    public void mouseEntered(MouseEntered _e) {
        paint();
    }

    /**
     *
     * @param _e
     */
    @Override
    public void mouseExited(MouseExited _e) {
    }

    /**
     *
     * @param _e
     */
    @Override
    public void mousePressed(MousePressed _e) {
        select(_e.getPoint());
    }

    /**
     *
     * @param _e
     */
    @Override
    public void mouseReleased(MouseReleased _e) {
    }

    // IMouseMotionEvents
    /**
     *
     * @param _e
     */
    @Override
    public void mouseMoved(MouseMoved _e) {
    }

    /**
     *
     * @param _e
     */
    @Override
    public void mouseDragged(MouseDragged _e) {
        select(_e.getPoint());
    }

    // Comparable
    public int compareTo(Object o) {
        if (o instanceof VSlider) {
            return (value.getValue()).compareTo(((VSlider) o).value.getValue());
        }
        return -1;
    }

    @Override
    public int hashCode() {
        return value.getValue().hashCode() + title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof VSlider) {
            VSlider pv = (VSlider) obj;
            return title == pv.title && value.getValue().equals(pv.getValue());
        }
        return false;
    }
}
