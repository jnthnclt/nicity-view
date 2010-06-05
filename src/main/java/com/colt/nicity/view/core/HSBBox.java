/*
 * HSBBox.java.java
 *
 * Created on 03-12-2010 06:32:34 PM
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
package com.colt.nicity.view.core;

import com.colt.nicity.view.adaptor.VS;
import com.colt.nicity.view.event.MouseDragged;
import com.colt.nicity.view.event.MouseEntered;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MouseMoved;
import com.colt.nicity.view.event.MousePressed;
import com.colt.nicity.view.event.MouseReleased;
import com.colt.nicity.view.event.MouseWheel;
import com.colt.nicity.view.image.IImage;
import com.colt.nicity.view.list.AItem;
import com.colt.nicity.core.lang.UFloat;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.core.value.IValue;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IMouseEvents;
import com.colt.nicity.view.interfaces.IMouseMotionEvents;
import com.colt.nicity.view.interfaces.IMouseWheelEvents;

/**
 *
 * @author Administrator
 */
public class HSBBox extends AItem implements IMouseEvents, IMouseMotionEvents, IMouseWheelEvents {

    IValue value;
    Object compareValue;
    /**
     *
     */
    protected IImage buffer;

    /**
     *
     * @param _value
     * @param _w
     * @param _h
     */
    public HSBBox(IValue _value, int _w, int _h) {
        value = _value;
        w = _w;
        h = _h + 15;
    }

    @Override
    public Object getValue() {
        return value;
    }

    /**
     *
     * @return
     */
    public float h() {
        return ((AColor) value.getValue()).getHue();
    }

    /**
     *
     * @return
     */
    public float s() {
        return ((AColor) value.getValue()).getSaturation();
    }

    /**
     *
     * @return
     */
    public float b() {
        return ((AColor) value.getValue()).getBrightness();
    }

    /**
     *
     * @return
     */
    public float a() {
        return ((AColor) value.getValue()).getAlpha();
    }

    @Override
    public void paintBackground(ICanvas g, int _x, int _y, int _w, int _h) {
        float _hue = h();
        float _s = s();
        float _b = b();

        IImage _buffer = buffer;
        if (compareValue == null || compareValue != value.getValue()) {
            compareValue = value.getValue();
            _buffer = VS.systemImage((int) _w, (int) _h, VS.c32BitARGB);
            buffer = _buffer;
            ICanvas bg = _buffer.canvas(g.who());
            int x = _x + 0;
            int y = _y + 15;


            for (float s = 0.0f; s < 1.0f; s += 1.0f / w) {
                for (float b = 1.0f; b > 0.0f; b -= 1.0f / h) {
                    bg.setColor(new AColor(AColor.HSBtoRGB(_hue, s, b)));
                    bg.line(x, y, x, y);
                    y++;
                }
                y = 15;
                x++;
            }
            y = 1;
            x = 0;
            for (float hue = 0.0f; hue < 1.0f; hue += 1.0f / w) {
                bg.setColor(new AColor(AColor.HSBtoRGB(hue, 1.0f, 1.0f)));
                bg.rect(true, x, y, 10, 10);
                x++;
            }

            bg.dispose();


        }
        if (_buffer != null) {
            g.drawImage(_buffer, 0, 0, null);
            g.setColor(new AColor(AColor.HSBtoRGB(_hue, 1 - _s, 1.0f)));
            g.oval(false, (int) (w * _s) - 3, 15 + (int) ((h - 15) * (1 - _b)) - 3, 7, 7);
            g.line((int) (w * _hue), 0, (int) (w * _hue), 10);
        }
    }

    /**
     *
     * @param _h
     * @param _s
     * @param _b
     * @param _a
     */
    public void setHSB(Object _h, Object _s, Object _b, Object _a) {
        float h = 0, s = 0, b = 0, a = 0;
        if (_h instanceof IValue) {
            _h = ((IValue) _h).getValue();
        }
        if (_s instanceof IValue) {
            _s = ((IValue) _s).getValue();
        }
        if (_b instanceof IValue) {
            _b = ((IValue) _b).getValue();
        }
        if (_a instanceof IValue) {
            _a = ((IValue) _a).getValue();
        }

        if (_h instanceof Double) {
            h = ((Double) _h).floatValue();
        }
        if (_s instanceof Double) {
            s = ((Double) _s).floatValue();
        }
        if (_b instanceof Double) {
            b = ((Double) _b).floatValue();
        }
        if (_a instanceof Double) {
            a = ((Double) _a).floatValue();
        }
        setHSB(h, s, b, a);
    }

    /**
     *
     * @param _h
     * @param _s
     * @param _b
     * @param _a
     */
    public void setHSB(float _h, float _s, float _b, float _a) {
        _h = UFloat.range(_h, 0.0f, 1.0f);
        _s = UFloat.range(_s, 0.0f, 1.0f);
        _b = UFloat.range(_b, 0.0f, 1.0f);
        AColor color = new AColor(_h, _s, _b);
        color.setA(_a);
        value.setValue(color);
    }

    private void setSB(int x, int y) {
        if (y < 10) {
            clearBuffer();
            setHSB(x / w, s(), b(), a());
        } else if (y > 15) {
            setHSB(h(), ((x) / (w)), 1 - ((y - 15) / (h - 15)), a());
        }
        repair();
        flush();
    }

    /**
     *
     */
    public void clearBuffer() {
        buffer = null;
    }

    // IMouseEvents
    @Override
    public void mouseEntered(MouseEntered e) {
        grabFocus(e.who());
    }

    @Override
    public void mouseExited(MouseExited e) {
    }

    @Override
    public void mousePressed(MousePressed e) {
    }

    @Override
    public void mouseReleased(MouseReleased e) {
        XY_I p = e.getPoint();
        setSB(p.x, p.y);
    }

    // IMouseWheelEvents
    public void mouseWheel(MouseWheel _e) {
        int rotation = _e.getWheelRotation();
        clearBuffer();
        float rate = 0.01f;
        if (_e.isControlDown()) {
            rate += 0.05f;
        }
        if (_e.isShiftDown()) {
            rate += 0.1f;
        }


        if (rotation < 0) {
            float h = h();
            h -= rate;
            if (h < 0.0f) {
                h = 1f;
            }
            setHSB(h, s(), b(), a());
        } else {
            float h = h();
            h += rate;
            if (h > 1.0f) {
                h = 0f;
            }
            setHSB(h, s(), b(), a());
        }
    }

    // IMouseMotionEvents
    @Override
    public void mouseMoved(MouseMoved e) {
    }

    @Override
    public void mouseDragged(MouseDragged e) {
        XY_I p = e.getPoint();
        setSB(p.x, p.y);
    }
}
