/*
 * VPowSlider.java.java
 *
 * Created on 03-12-2010 06:38:56 PM
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

import com.colt.nicity.view.border.LineBorder;
import com.colt.nicity.view.border.ViewBorder;
import com.colt.nicity.view.list.AItem;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.EditString;
import com.colt.nicity.view.core.Placer;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.VString;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.IView;

//	A convenient GUI gadget that returns double getDouble() from 0 to 1.  
//	Has a good default 100x50 layout constructed by new VPowSlider("short title");
//	The slider is co-dependent with a settable power and a settable numeric display field.
//
//	This normalized exponential slider has a settable power from .05 to 10 (red tic on left)
//	and is linear (pow=1) when set half way up where there is a gray line.
//
//	To get better resolution when controlling values close to 0, raise the exponent.
//	To get better resolution when controlling values close to 1, lower the exponent.
//
//	Useful trick to find best pow for maximum headroom around some specific number:
//	enter the number, then move pow until the horizontal value slider is half way.
/**
 *
 * @author Administrator
 */
public class VPowSlider extends AItem {
    //!! defaults are designed for minimum GUI space, but avoid long titles

    static final float cW = 100f;
    static final float cH = 50f;
    static final double cPow = 1d;
    static final double cVal = .5d;
    static final int cPowW = 10;
    static final int cSliderH = 18;
    /**
     *
     */
    protected Object title;
    /**
     *
     */
    protected double pow = cPow;
    /**
     *
     */
    protected double val = cVal;
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
     * @param _title
     */
    public VPowSlider(Object _title) {
        this(_title, cPow, cVal, cW, cH);
    }

    /**
     *
     * @param _title
     * @param _pow
     */
    public VPowSlider(Object _title, double _pow) {
        this(_title, _pow, Math.pow(cVal, _pow), cW, cH);
    }

    /**
     *
     * @param _title
     * @param _pow
     * @param _val
     */
    public VPowSlider(Object _title, double _pow, double _val) {
        this(_title, _pow, _val, cW, cH);
    }

    /**
     *
     * @param _title
     * @param _pow
     * @param _val
     * @param _w
     * @param _h
     */
    public VPowSlider(Object _title, double _pow, double _val, float _w, float _h) {
        title = _title;
        val = _val;
        pow = _pow;
        jw = _w;
        jh = _h;
        setBorder(new ViewBorder());
        init();
    }

    /**
     *
     * @param _val
     */
    public void setDouble(double _val) {
        val = _val;
        paint();
    }

    /**
     *
     * @return
     */
    public double getDouble() {
        return val;
    }

    public String toString() {
        return title.toString();
    }
    _VNumber vedit = null;
    VExpoSlider vslider;
    VSlider vpow;
    Value sliderValue;
    Value powValue;

    /**
     *
     * @return
     */
    public IView init() {

        // main slider
        sliderValue = new Value() {

            public Object getValue() {
                return (Double) Math.pow(val, 1d / pow);
            }

            public void setValue(Object _val) {
                val = ((Double) _val).doubleValue();
                vedit.valueChanged();
                paint();
            }
        };
        vslider = new VExpoSlider(sliderValue, "", jw, cSliderH, true);
        vslider.pow = pow;
        vedit = new _VNumber();
        vedit.valueChanged();
        vedit.setBorder(new LineBorder(ViewColor.cTheme, ViewColor.cThemeHighlight, 1, 4, 1));

        // pow slider
        powValue = new Value() {

            public Object getValue() {
                double v;
                if (pow < .05) {
                    v = pow - .05;
                } else if (pow < 1) {
                    v = pow / 2;
                } else {
                    v = (pow + 9) / 20;
                }
                return (Double) v;
            }

            public void setValue(Object _val) {
                double v = ((Double) _val).doubleValue();
                if (v < .05) {
                    pow = .05 + v;
                } else if (v < .5) {
                    pow = 2 * v;
                } else {
                    pow = 20 * v - 9;
                }
                vslider.pow = pow;
                paint();
            }
        };
        vpow = new VSlider(powValue, "", cPowW, jh, false);
        vpow.setBorder(new LineBorder(ViewColor.cTheme, ViewColor.cThemeHighlight, 1, 4, 1));
        vpow.bar = false;

        // chains
        VChain cv = new VChain(UV.cSWNW);
        VChain ch = new VChain(UV.cEW);

        cv.add(new VString(title));
        cv.add(vedit);
        cv.add(vslider);

        ch.add(vpow);
        ch.add(cv);

        ch.setBorder(new LineBorder(ViewColor.cTheme, ViewColor.cThemeHighlight, 1, 4, 1));

        Viewer draw = new Viewer(ch);
        setPlacer(new Placer(new Viewer(draw)));
        return this;
    }

    class _VNumber extends AItem {

        EditString number = null;

        _VNumber() {
            float w = (float) (jw - cPow);
            number = new EditString(this, w, w) {

                public void stringChanged(String _string) {
                    if (_string == null) {
                        return;
                    }
                    if (!_string.startsWith("0.")) {
                        return;
                    }
                    stringSet(_string);
                }

                public void stringSet(String _string) {
                    if (_string == null) {
                        return;
                    }
                    _string = _string.trim();
                    if (_string.length() == 0) {
                        return;
                    }

                    try {
                        double d = Double.parseDouble(_string);
                        if (d > 1) {
                            d = 1;
                        }
                        if (d < 0) {
                            d = 0;
                        }
                        sliderValue.setValue(d);
                        vslider.paint();
                    } catch (Exception x) {
                        number.text = "ERROR";
                        number.paint();
                    }
                }
            };
            setPlacer(new Placer(number));
            setBorder(null);
        }

        void valueChanged() {
            //!!number.setText(Double.toString(val));//!!recursion
            String s = unscientific(val); //Double.toString(val);
            number.text = s;
            number.paint();
        }

        public String toString() {
            if (number == null) {
                return "";
            }
            return number.text.toString();
        }
    }
    static final String zeros = "0000000000"; //!! max width desireable for a VPowSlider

    static String unscientific(double d) {//!! good for 0-1; generalize and move to util?
        int num = 0;
        if (d >= 1) {
            return "1.0"; //!! specific to the 0-1 range
        }
        if (d <= 0) {
            return "0.0"; //!! specific to the 0-1 range
        }
        while (d < 1) {
            d *= 10;
            num++;
            if (num > zeros.length()) {
                return "TOO SMALL";
            }
        }
        d *= 100; //!! 3 digits should suffice; could also trim rightmost zeros
        if (num == 0) {
            return "0." + (long) (d);
        } else {
            return "0." + zeros.substring(0, num - 1) + (long) (d); // avoids string garbage
        }
    }
}
