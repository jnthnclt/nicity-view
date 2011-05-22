/*
 * VRange.java.java
 *
 * Created on 03-12-2010 06:38:26 PM
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

import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.flavor.RoundFlavor;
import colt.nicity.view.flavor.ScrollFlavor;
import colt.nicity.view.flavor.SliderBarFlavor;
import colt.nicity.view.list.AItem;
import colt.nicity.core.lang.UDouble;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.core.value.IValue;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VRange extends AItem implements IMouseMotionEvents {

    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        VChain c = new VChain(UV.cSN);
        c.add(new VRange("At Dynamic", null, null, new Value(0.5d), new RigidBox(1, 32), true, true));
        c.add(new RigidBox(1, 32));
        c.add(new VRange("Dynamic", new Value(0d), new Value(1d), null, new RigidBox(1, 32), true, true));
        c.add(new RigidBox(1, 32));
        c.add(new VRange("Dynamic", new Value(0d), new Value(1d), null, new RigidBox(1, 32), true, true));
        c.spans(UV.cXEW);

        VChain d = new VChain(UV.cSWNW);
        d.add(new VString("Test Test test test test test test test test test test test test test test test"));
        d.add(new RigidBox(600, 1));
        d.add(new Viewer(c));
        UV.exitFrame(new Viewer(d), "");
    }

    /**
     *
     */
    public void modified() {
    }
    Object title;
    /**
     *
     */
    public Value<Double> min;
    /**
     *
     */
    public Value<Double> at;
    /**
     *
     */
    public Value<Double> max;
    /**
     *
     */
    protected boolean horizontal = false;
    /**
     *
     */
    protected boolean solid = true;
    static ScrollFlavor flavor = new ScrollFlavor();
    private static final int cAtOnlyMode = 1;
    private static final int cRangeOnlyMode = 2;
    private static final int cAllThree = 3;
    private int mode = 0;
    int pw = 1;
    int ph = 1;

    /**
     *
     * @param _title
     * @param _min
     * @param _max
     * @param _at
     * @param _over
     * @param _horizontal
     * @param _solid
     */
    public VRange(Object _title, Value<Double> _min, Value<Double> _max, Value<Double> _at, IView _over, boolean _horizontal, boolean _solid) {

        title = _title;
        min = _min;
        max = _max;
        at = _at;

        if (min != null && max != null && at != null) {
            mode = cAllThree;
        }
        else if (min != null && max != null && at == null) {
            mode = cRangeOnlyMode;
        }
        else if (min == null && max == null && at != null) {
            mode = cAtOnlyMode;
        }
        else {
            throw new RuntimeException("Invalid input combination");
        }


        horizontal = _horizontal;
        solid = _solid;
        setContent(_over);
        if (solid) {
            setBorder(new ViewBorder());
        }
        else {
            setBorder(null);
        }
    }

    /**
     *
     * @param _v
     */
    public void at(double _v) {
        if (at != null) {
            at.setValue(_v);
            paint();
        }
    }

    /**
     *
     * @param _v
     */
    public void min(double _v) {
        if (min != null) {
            min.setValue(_v);
            paint();
        }
    }

    /**
     *
     * @param _v
     */
    public void max(double _v) {
        if (max != null) {
            max.setValue(_v);
            paint();
        }
    }

    /**
     *
     * @return
     */
    public double min() {
        if (min == null) {
            return at();
        }
        return min.doubleValue();
    }

    /**
     *
     * @return
     */
    public double max() {
        if (max == null) {
            return at();
        }
        return max.doubleValue();
    }

    /**
     *
     * @return
     */
    public double at() {
        if (at == null) {
            return min.doubleValue() + (max.doubleValue() - min.doubleValue());
        }
        return at.doubleValue();
    }

    /**
     *
     * @param _value
     * @return
     */
    public double percent(double _value) {
        double _min = 0;
        if (min != null) {
            _min = min.doubleValue();
        }
        double _max = 1;
        if (max != null) {
            _max = max.doubleValue();
        }
        if (_value < _min) {
            return 0 - Double.MIN_VALUE;
        }
        if (_value > _max) {
            return 1 + Double.MIN_VALUE;
        }
        return (_value - _min) / (_max - _min);
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
    public void paintBorder(ICanvas _g, int _x, int _y, int _w, int _h) {
        super.paintBorder(_g, _x, _y, _w, _h);
        pw = _w;
        ph = _h;
        double _min = 0;
        if (min != null) {
            _min = min.doubleValue();
        }
        double _max = 1;
        if (max != null) {
            _max = max.doubleValue();
        }
        paint(_g, _x, _y, _w, _h, _min, _max, horizontal);
        paintOver(_g, _x, _y, _w, _h, _min, _max, horizontal);
        if (title != null) {
            _g.setColor(ViewColor.cThemeFont);
            _g.setFont(UV.fonts[UV.cText]);
            String s = title.toString();
            XY_I p = UV.fonts[UV.cText].place(s, _w / 2, _h / 2, UV.cCC);
            _g.drawString(s, _x + p.x, _y + p.y);
        }
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _min
     * @param _max
     * @param _horizontal
     */
    public void paintOver(ICanvas _g, int _x, int _y, int _w, int _h, double _min, double _max, boolean _horizontal) {
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _min
     * @param _max
     * @param _horizontal
     */
    public void paint(ICanvas _g, int _x, int _y, int _w, int _h, double _min, double _max, boolean _horizontal) {
        _min = Double.MAX_VALUE;
        double _at = Double.MAX_VALUE;
        _max = Double.MAX_VALUE;

        if (min != null) {
            _min = min.doubleValue();
        }
        if (at != null) {
            _at = at.doubleValue();
        }
        if (max != null) {
            _max = max.doubleValue();
        }
        if (horizontal) {
            paintHorizontal(_g, _x, _y, _w, _h, _min, _at, _max, solid);
        }
        else {
            paintVertical(_g, _x, _y, _w, _h, _min, _at, _max, solid);
        }
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _min
     * @param _at
     * @param _max
     * @param _solid
     */
    public static void paintHorizontal(ICanvas _g, int _x, int _y, int _w, int _h, double _min, double _at, double _max, boolean _solid) {

        int fx = (int) (_min * (_w));
        int tx = (int) (_max * (_w));

        _g.setAlpha(0.5f, 0);
        if (_at == Double.MAX_VALUE) {
            int th = _h;
            _g.setColor(ViewColor.cThemeSelected);
            if (_solid) {
                _g.paintFlavor(flavor, _x, _y, _w, th, ViewColor.cTheme);
            }
            _g.paintFlavor(flavor, _x + fx, _y + (th / 4), (tx - fx), th / 2, ViewColor.cThemeSelected);
            _g.paintFlavor(RoundFlavor.flavor, _x + fx - (th / 4), _y + (th / 4), th / 2, th / 2, AColor.green);
            _g.paintFlavor(RoundFlavor.flavor, _x + tx - (th / 4), _y + (th / 4), th / 2, th / 2, AColor.red);

        }
        else {
            if (_min == Double.MAX_VALUE && _max == Double.MAX_VALUE) {
                int th = (_h);
                int ax = (int) (_at * (_w));
                _g.paintFlavor(SliderBarFlavor.pointingUp, _x + ax - 4, _y, 11, th, AColor.red);
            }
            else {
                int th = (_h / 2);
                _g.setColor(ViewColor.cThemeSelected);
                if (_solid) {
                    _g.paintFlavor(flavor, _x, _y, _w, th, ViewColor.cTheme);
                }
                _g.paintFlavor(flavor, _x + fx, _y + (th / 4), (tx - fx), th / 2, ViewColor.cThemeSelected);
                _g.paintFlavor(RoundFlavor.flavor, _x + fx - (th / 4), _y + (th / 2) - (th / 4), th / 2, th / 2, AColor.green);
                _g.paintFlavor(RoundFlavor.flavor, _x + tx - (th / 4), _y + (th / 2) - (th / 4), th / 2, th / 2, AColor.red);

                int ax = (int) (_at * (_w));
                _g.paintFlavor(SliderBarFlavor.pointingUp, _x + ax - 4, _y + th, 11, th, AColor.red);
            }
        }
        _g.setAlpha(1f, 0);
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _min
     * @param _at
     * @param _max
     * @param _solid
     */
    public static void paintVertical(ICanvas _g, int _x, int _y, int _w, int _h, double _min, double _at, double _max, boolean _solid) {

        int fy = (int) (_min * (_h));
        int ty = (int) (_max * (_h));


        if (_at == Double.MAX_VALUE) {
            int tw = (_w / 2);
            _g.setColor(ViewColor.cThemeSelected);
            int my = fy + ((ty - fy) / 2);
            _g.paintFlavor(SliderBarFlavor.pointingRight, _x + 4, _y + my - 4, tw - 9, 11, ViewColor.cThemeSelected);

            _g.line(_x + (tw / 2), _y + fy, _x + tw, _y + fy);
            _g.line(_x + (tw / 2), _y + ty, _x + tw, _y + ty);
            _g.line(_x + (tw / 2), _y + fy, _x + (tw / 2), _y + ty);
            _g.paintFlavor(SliderBarFlavor.pointingRight, _x + tw + 4, _y + fy - 4, tw - 9, 11, ViewColor.cThemeSelected);
            _g.paintFlavor(SliderBarFlavor.pointingRight, _x + tw + 4, _y + ty - 4, tw - 9, 11, ViewColor.cThemeSelected);
        }
        /*
        _g.setAlpha(0.5f,0);
        if (_at == Double.MAX_VALUE) {
        int tw = _w ;
        _g.setColor(ViewColor.cThemeSelected);
        if (_solid) _g.paintFlavor(flavor, _x, _y+4,tw-8,_h-8, ViewColor.cTheme);
        _g.paintFlavor(flavor, _x+fy, _y+(tw/4),(ty-fy),tw/2, ViewColor.cThemeSelected);
        _g.paintFlavor(RoundFlavor.flavor, _x + tw + 4, _y + fy - 4, tw - 9, 11, ViewColor.cThemeSelected);
        _g.paintFlavor(RoundFlavor.flavor, _x + tw + 4, _y + ty - 4, tw - 9, 11, ViewColor.cThemeSelected);
        } else {
        if (_min == Double.MAX_VALUE && _max == Double.MAX_VALUE) {
        int tw = _w ;
        int ay = (int) (_at * (_h));
        _g.paintFlavor(SliderBarFlavor.pointingUp, _x + ay - 4, _y + 4, 11, tw - 9, AColor.red);
        } else {
        int tw = _w/2;
        _g.setColor(ViewColor.cThemeSelected);
        if (_solid) _g.paintFlavor(flavor, _x, _y+4,_w,tw-8, ViewColor.cTheme);
        _g.paintFlavor(flavor, _x+fy, _y+(tw/4),(ty-fy),tw/2, ViewColor.cThemeSelected);
        _g.paintFlavor(RoundFlavor.flavor, _x + tw + 4, _y + fy - 4, tw - 9, 11, ViewColor.cThemeSelected);
        _g.paintFlavor(RoundFlavor.flavor, _x + tw + 4, _y + ty - 4, tw - 9, 11, ViewColor.cThemeSelected);

        int ax = (int) (_at * (_w));
        _g.paintFlavor(SliderBarFlavor.pointingUp, _x + ax - 4, _y + tw + 4, 11, tw - 9, AColor.red);
        }
        }
        _g.setAlpha(1f,0);*/
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
    boolean over = false;
    int snap = -1;

    /**
     *
     * @param _e
     */
    @Override
    public void mouseEntered(MouseEntered _e) {
        over = true;
        paint();
    }

    /**
     *
     * @param _e
     */
    @Override
    public void mouseExited(MouseExited _e) {
        over = false;
    }
    XY_I pressed;
    double[] pvs = new double[0];
    IValue[] vs = new IValue[0];

    /**
     *
     * @param _e
     */
    @Override
    public void mousePressed(MousePressed _e) {
        snap = -1;
        pvs = new double[0];
        vs = new IValue[0];
        if (_e.isLeftClick()) {
            pressed = _e.getPoint();
            if (mode == cAtOnlyMode) {
                pressedAtOnlyMode(pressed);
            }
            else if (mode == cRangeOnlyMode) {
                pressedRangeOnlyMode(pressed);
            }
            else if (mode == cAllThree) {
                if (horizontal) {
                    if (pressed.y > ph / 2) {
                        pressedAtOnlyMode(pressed);
                    }
                    else {
                        pressedRangeOnlyMode(pressed);
                    }
                }
                else {
                    if (pressed.x > pw / 2) {
                        pressedAtOnlyMode(pressed);
                    }
                    else {
                        pressedRangeOnlyMode(pressed);
                    }
                }
            }
            //set(_e.getPoint());
        }
        else {
            super.mousePressed(_e);
        }
    }

    private void pressedAtOnlyMode(XY_I pressed) {
        double _at = at.doubleValue();
        if (!pickable(pressed, _at)) {
            return;
        }
        pvs = new double[]{_at};
        vs = new IValue[]{at};
    }

    private void pressedRangeOnlyMode(XY_I pressed) {
        double _min = min.doubleValue();
        double _max = max.doubleValue();
        if (pickable(pressed, _min)) {
            pvs = new double[]{_min};
            vs = new IValue[]{min};
        }
        else if (pickable(pressed, _max)) {
            pvs = new double[]{_max};
            vs = new IValue[]{max};
        }
        else {
            double pp = 0;
            if (horizontal) {
                pp = (double) pressed.x / pw;
            }
            else {
                pp = (double) pressed.y / ph;
            }
            if (pp >= _min && pp <= _max) {
                pvs = new double[]{_min, _max};
                vs = new IValue[]{min, max};
            }
        }
    }

    private boolean pickable(XY_I _mp, double _v) {
        if (horizontal) {
            if (Math.abs(_mp.x - (_v * pw)) > 8) {
                return false;
            }
        }
        else {
            if (Math.abs(_mp.y - (_v * ph)) > 8) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param _e
     */
    @Override
    public void mouseReleased(MouseReleased _e) {
        snap = -1;
        if (_e.isLeftClick());
        else {
            super.mouseReleased(_e);
        }
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
        XY_I mp = _e.getPoint();
        if (pvs.length == 0) {
            return;
        }
        double shift = 0;
        if (horizontal) {
            shift = (double) (mp.x - pressed.x) / pw;
        }
        else {
            shift = (double) (mp.y - pressed.y) / ph;
        }

        for (int i = 0; i < pvs.length; i++) {
            double nv = UDouble.clamp(pvs[i] + shift, 0, 1);
            vs[i].setValue(nv);
        }
        modified();
        paint();

        //set(_e.getPoint());
    }

    /**
     *
     * @param p
     */
    public void set(XY_I p) {
        double _min = Double.MAX_VALUE;
        if (min != null) {
            _min = min.doubleValue();
        }
        double _max = Double.MAX_VALUE;
        if (max != null) {
            _max = max.doubleValue();
        }
        double _at = Double.MAX_VALUE;
        if (at != null) {
            _at = at.doubleValue();
        }
        double[] smam = set(p, (int) getW(), (int) ph, snap, _min, _at, _max, horizontal);
        if (smam != null) {
            snap = (int) smam[0];
            if (min != null) {
                min.setValue(new Double(smam[1]));
            }
            if (at != null) {
                at.setValue(new Double(smam[2]));
            }
            if (max != null) {
                max.setValue(new Double(smam[3]));
            }
            modified();
            paint();
        }
    }

    /**
     *
     * @param p
     * @param _w
     * @param _h
     * @param _snap
     * @param _min
     * @param _at
     * @param _max
     * @param _horizontal
     * @return
     */
    static public double[] set(XY_I p, int _w, int _h, double _snap, double _min, double _at, double _max, boolean _horizontal) {
        double _x = 0;
        double _y = 0;
        if (_horizontal) {
            if (p.x > _w || p.x < 0) {
                return null;
            }
            _x = (double) p.x / (double) (_w);
            _y = (double) p.y / (double) (_h);

        }
        else {
            if (p.y > _h || p.y < 0) {
                return null;
            }
            _x = ((double) p.y / (double) (_h));
            _y = ((double) p.x / (double) (_w));
        }


        if (_at != Double.MAX_VALUE) {
            if (_at < _min) {
                _at = _min;
            }
            if (_at > _max) {
                _at = _max;
            }
            if (_min != Double.MAX_VALUE && _max != Double.MAX_VALUE) {
                if (_snap == 1 || (_y < 0.33 && _snap == -1)) {
                    // Shift
                    _snap = 1;
                    double mshift = _at - _min;
                    double pshift = _max - _at;

                    _at = _x;
                    _min = _at - mshift;
                    _max = _at + pshift;
                }
                else if (_snap == 2 || (_y < 0.66 && _snap == -1)) {
                    // Resize
                    _snap = 2;
                    double pre = (_at - _min) / (_max - _min);

                    if (_x <= _min) {
                        _min = _x;
                    }
                    else if (_x >= _max) {
                        _max = _x;
                    }
                    else {
                        if (_x < _at) {
                            _min = _x;
                        }
                        else {
                            _max = _x;
                        }
                    }
                    if (_min > _max) {
                        _max = _min;
                    }
                    if (_max < _min) {
                        _min = _max;
                    }
                    _at = _min + ((_max - _min) * pre);
                }
                else {
                    // move at
                    _snap = 3;
                    _at = _x;
                    if (_at < _min) {
                        double d = _max - _min;
                        _min = _at;
                        _max = _min + d;
                    }
                    else if (_at > _max) {
                        double d = _max - _min;
                        _max = _at;
                        _min = _max - d;
                    }
                }
            }
            else {
                // move at
                _snap = 3;
                _at = _x;
                if (_at < _min) {
                    double d = _max - _min;
                    _min = _at;
                    _max = _min + d;
                }
                else if (_at > _max) {
                    double d = _max - _min;
                    _max = _at;
                    _min = _max - d;
                }
            }
        }
        else if (_min != Double.MAX_VALUE && _max != Double.MAX_VALUE) {
            _at = _min + ((_max - _min) / 2);
            if (_snap == 2 || (_y < 0.5 && _snap == -1)) {
                // Shift
                _snap = 2;
                double mshift = _at - _min;
                double pshift = _max - _at;
                _at = _x;
                _min = _at - mshift;
                _max = _at + pshift;
            }
            else {
                // Resize
                _snap = 1;
                double pre = (_at - _min) / (_max - _min);

                if (_x <= _min) {
                    _min = _x;
                }
                else if (_x >= _max) {
                    _max = _x;
                }
                else {
                    if (_x < _at) {
                        _min = _x;
                    }
                    else {
                        _max = _x;
                    }
                }
                if (_min > _max) {
                    _max = _min;
                }
                if (_max < _min) {
                    _min = _max;
                }
                _at = _min + ((_max - _min) * pre);
            }
        }
        return new double[]{_snap, _min, _at, _max};
    }
}



