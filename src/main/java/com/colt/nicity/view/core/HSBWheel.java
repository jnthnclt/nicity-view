/*
 * HSBWheel.java.java
 *
 * Created on 03-12-2010 06:32:55 PM
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
import com.colt.nicity.view.image.IImage;
import com.colt.nicity.view.list.AItem;
import com.colt.nicity.view.paint.UPaint;
import com.colt.nicity.core.lang.UMath;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.core.observer.AObserver;
import com.colt.nicity.core.observer.Change;
import com.colt.nicity.core.observer.IObservable;
import com.colt.nicity.core.observer.IObserver;
import com.colt.nicity.core.value.IValue;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IMouseEvents;
import com.colt.nicity.view.interfaces.IMouseMotionEvents;



/**
 *
 * @author Administrator
 */
public class HSBWheel extends AItem implements IMouseEvents, IMouseMotionEvents {

    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        UV.exitFrame(new HSBWheel(new Value(new AColor(53, 53, 70)), 256), "");
    }

    // Overridable
    /**
     *
     * @param _color
     * @return
     */
    public AColor isValidColor(AColor _color) {
        return _color;
    }
    float lastB = Float.MAX_VALUE;
    float lastA = Float.MAX_VALUE;

    /**
     *
     * @return
     */
    public boolean refreshWheel() {
        float b = ((AColor) value.getValue()).getBrightness();
        float a = ((AColor) value.getValue()).getAlpha();
        if (b != lastB || a != lastA) {
            lastB = b;
            lastA = a;
            return true;
        }
        return false;
    }
    private final double tpi = Math.PI * 2;
    /**
     *
     */
    protected IImage buffer;
    IValue value;
    IObserver colorObserver;

    /**
     *
     * @param _value
     * @param _diam
     */
    public HSBWheel(IValue _value, int _diam) {
        value = _value;
        w = _diam;
        h = _diam;
        if (value instanceof IObservable) {
            colorObserver = new AObserver() {
                @Override
                public void change(Change _change) {
                    paint();
                }

                public void bound(IObservable _observable) {
                }

                public void released(IObservable _observable) {
                }
            };
            ((IObservable) value).bind(colorObserver);
        }
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void paintBackground(ICanvas g, int _x, int _y, int _w, int _h) {
        if (refreshWheel()) {
            _refreshWheel(g.who());
        }
        if (buffer != null) {
            UPaint.drawCheckerBoard(g, _x, _y, _w, _h, 16, AColor.darkGray, AColor.lightGray);
            g.drawImage(buffer, 0, 0, null);
            AColor color = (AColor) value.getValue();
            XY_I xy = xy(color);
            g.setColor(AColor.black);
            g.oval(false, _x + xy.x - 4, _y + xy.y - 4, 8, 8);
        }
    }

    private void _refreshWheel(long _who) {
        if (w == 0 || h == 0) {
            return;
        }
        AColor vc = ((AColor) value.getValue());
        float b = vc.getBrightness();
        float a = vc.getAlpha();

        IImage _buffer = VS.systemImage((int) w, (int) h, VS.c32BitARGB);
        buffer = _buffer;
        ICanvas bg = _buffer.canvas(_who);
        XY_I last = null;
        for (float s = 0.0f, r = 1; s < 1.0f; s += 1.0f / (w / 2), r += 1) {
            double circum = (r * 2) * Math.PI;
            for (float h = 0.0f; h < 1.0f; h += 1.0f / (circum * 3)) {
                XY_I xy = xy(h, s);
                if (xy.equals(last)) {
                    continue;
                }
                last = xy;
                AColor c = new AColor(AColor.HSBtoRGB(h, s, b));
                c.setA(a);
                c = isValidColor(c);
                if (c == null) {
                    continue;
                }
                bg.setColor(c);
                bg.line(xy.x, xy.y, xy.x, xy.y);
            }
        }
        bg.dispose();
        paint();
    }

    /**
     *
     * @param _hue
     * @param _sat
     * @return
     */
    public XY_I xy(float _hue, float _sat) {
        int cx = (int) (w / 2);
        int cy = (int) (h / 2);
        double[] xy = UMath.vector(_hue * tpi, _sat * (w / 2));
        int x = cx + (int) (xy[0]);
        int y = cy + (int) (xy[1]);
        return new XY_I(x, y);
    }

    /**
     *
     * @param _color
     * @return
     */
    public XY_I xy(AColor _color) {
        return xy(_color.getHue(), _color.getSaturation());
    }

    /**
     *
     * @param _xy
     * @return
     */
    public AColor xy(XY_I _xy) {
        float b = ((AColor) value.getValue()).getBrightness();
        float a = ((AColor) value.getValue()).getAlpha();

        int cx = (int) (w / 2);
        int cy = (int) (h / 2);
        double angle = UMath.angle(cx, cy, _xy.x, _xy.y);
        double distance = UMath.distance(cx, cy, _xy.x, _xy.y);
        double h = angle / tpi;
        double s = distance / (double) (w / 2);
        if (s > 1) {
            s = 1;
        }
        AColor c = new AColor(AColor.HSBtoRGB((float) h, (float) s, b));
        c.setAlpha(a);
        return c;
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
        AColor color = xy(p);
        value.setValue(color);
        paint();
    }

    // IMouseMotionEvents
    @Override
    public void mouseMoved(MouseMoved e) {
    }

    @Override
    public void mouseDragged(MouseDragged e) {
        XY_I p = e.getPoint();
        AColor color = xy(p);
        value.setValue(color);
        paint();
    }
}
