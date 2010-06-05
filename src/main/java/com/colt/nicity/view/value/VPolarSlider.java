/*
 * VPolarSlider.java.java
 *
 * Created on 03-12-2010 06:39:02 PM
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

import com.colt.nicity.view.event.MouseEntered;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MousePressed;
import com.colt.nicity.view.event.MouseReleased;
import com.colt.nicity.view.list.AItem;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.core.value.IValue;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.Placer;
import com.colt.nicity.view.core.RigidBox;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VString;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class VPolarSlider extends AItem implements Comparable {

    IValue current;
    IValue value;
    Object title;
    float jw;
    float jh;
    boolean horizontal = false;

    /**
     *
     * @param _value
     * @param _title
     * @param _w
     * @param _h
     * @param _horizontal
     * @param _init
     */
    public VPolarSlider(IValue _value, Object _title, float _w, float _h, boolean _horizontal, boolean _init) {
        value = _value;
        title = _title;
        jw = _w;
        jh = _h;
        horizontal = _horizontal;
        if (_init) {
            init();
        }
    }

    /**
     *
     * @param _current
     */
    public void setCurrent(IValue _current) {
        current = _current;
    }

    /**
     *
     * @param _value
     */
    public void setValue(IValue _value) {
        value = _value;
    }

    public String toString() {
        if (title == null) {
            return "";
        }
        return title.toString();
    }

    /**
     *
     */
    public void init() {
        Viewer draw = null;
        if (title != null) {
            VString title = new VString(this);
            title.layoutInterior();
            if (horizontal) {
                draw = new Viewer(new RigidBox(jw - (int) title.getW(), jh));
                draw.place(title, UV.cEW);
            } else {
                draw = new Viewer(new RigidBox(jw, jh - (int) title.getH()));
                draw.place(title, UV.cSN);
            }
        } else {
            draw = new Viewer(new RigidBox(jw, jh));
        }
        setPlacer(new Placer(draw));
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public void paintBackground(ICanvas _g, int _x, int _y, int _w, int _h) {
        super.paintBackground(_g, _x, _y, _w, _h);

        if (horizontal) {
            double _value = ((Double) value.getValue()).doubleValue();
            int hjw = (int) (jw / 2);
            int w = Math.abs((int) (_value * (hjw)));
            if (_value >= 0) {
                _g.setColor(ViewColor.cThemeActive);
                _g.rect(true, _x + hjw, _y + (_h / 4), w, _h / 2);
                _g.setColor(ViewColor.cThemeShadow);
                _g.rect(false, _x + hjw, _y + (_h / 4), w, _h / 2);

            } else {
                _g.setColor(ViewColor.cThemeActive);
                _g.rect(true, _x + hjw - w, _y + (_h / 4), w, _h / 2);
                _g.setColor(ViewColor.cThemeShadow);
                _g.rect(false, _x + hjw - w, _y + (_h / 4), w, _h / 2);

            }

            _g.setColor(ViewColor.cThemeShadow);
            _g.line(_x + hjw, _y + 0, _x + hjw, _y + _h);

            //_g.line((int)jw-6,_h/2,(int)jw-2,_h/2);
            //_g.line((int)jw-4,(_h/2)-2,(int)jw-4,(_h/2)+2);

            //_g.line(2,_h/2,6,_h/2);


            if (current != null) {
                _value = ((Double) current.getValue()).doubleValue();
                _g.setColor(AColor.red);
                w = Math.abs((int) (_value * (hjw)));
                if (_value >= 0) {
                    _g.line(_x + hjw + w - 4, _y + _h, _x + hjw + w, _y + (int) jh / 2);
                    _g.line(_x + hjw + w + 4, _y + _h, _x + hjw + w, _y + (int) jh / 2);
                } else {
                    _g.line(_x + hjw - w - 4, _y + _h, _x + hjw - w, _y + (int) jh / 2);
                    _g.line(_x + hjw - w + 4, _y + _h, _x + hjw - w, _y + (int) jh / 2);
                }

            }

        } else {
            double _value = -((Double) value.getValue()).doubleValue();
            int hjh = (int) (jh / 2);
            int h = Math.abs((int) (_value * (hjh)));
            if (_value >= 0) {
                _g.setColor(ViewColor.cThemeActive);
                _g.rect(true, _x + (_w / 4), _y + hjh, _w / 2, h);
                _g.setColor(ViewColor.cThemeShadow);
                _g.rect(false, _x + (_w / 4), _y + hjh, _w / 2, h);
            } else {
                _g.setColor(ViewColor.cThemeActive);
                _g.rect(true, _x + (_w / 4), _y + hjh - h, _w / 2, h);
                _g.setColor(ViewColor.cThemeShadow);
                _g.rect(false, _x + (_w / 4), _y + hjh - h, _w / 2, h);

            }

            _g.setColor(ViewColor.cThemeShadow);
            _g.line(_x + 0, _y + hjh, _x + _w, _y + hjh);


            //_g.line(2,_w/2,_w-2,(_w/2));
            //_g.line(_w/2,2,_w/2,_w-2);
            //_g.line(2,(int)(jh-(_w/2)),_w-2,(int)(jh-(_w/2)));
        }

    }

    /**
     *
     * @return
     */
    public Object getValue() {
        return this;
    }

    // IMouseEvents
    /**
     * 
     * @param _e
     */
    public void mouseEntered(MouseEntered _e) {
    }

    /**
     *
     * @param _e
     */
    public void mouseExited(MouseExited _e) {
    }

    /**
     *
     * @param _e
     */
    public void mousePressed(MousePressed _e) {
    }

    /**
     *
     * @param _e
     */
    public void mouseReleased(MouseReleased _e) {
        if (_e.getClickCount() > 0) {
            XY_I p = _e.getPoint();
            if (horizontal) {
                if (p.x > jw || p.x < 0) {
                    return;
                }
                double _value = ((double) p.x / (double) (jw / 2)) - 1;
                value.setValue(new Double(_value));
            } else {
                if (p.y > jh || p.y < 0) {
                    return;
                }
                double _value = -(((double) p.y / (double) (jh / 2)) - 1);
                value.setValue(new Double(_value));
            }
            init();
            layoutInterior();
            flush();
        }
    }

    // Comparable
    public int compareTo(Object o) {
        if (o instanceof VPolarSlider) {
            return ((Double) value.getValue()).compareTo((Double) ((VPolarSlider) o).value.getValue());
        }
        return -1;
    }

    public int hashCode() {
        int code = value.getValue().hashCode();
        if (title != null) {
            code += title.hashCode();
        }
        return code;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof VPolarSlider) {
            VPolarSlider pv = (VPolarSlider) obj;
            return title == pv.title && value.getValue().equals(pv.getValue());
        }
        return false;
    }
}
