/*
 * VColorWheel.java.java
 *
 * Created on 03-12-2010 06:41:18 PM
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

import com.colt.nicity.core.observer.AObserver;
import com.colt.nicity.core.observer.Change;
import com.colt.nicity.core.observer.IObservable;
import com.colt.nicity.core.observer.IObserver;
import com.colt.nicity.core.value.IValue;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.HSBWheel;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.IEvent;

/**
 *
 * @author Administrator
 */
public class VColorWheel extends Viewer {

    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        UV.exitFrame(new VColorWheel(new Value(AColor.randomPastel(127, 127)), 256), "");
    }
    // Overriable
    // return null if invalid color

    /**
     *
     * @param _color
     * @return
     */
    public AColor isValidColor(AColor _color) {
        return _color;
    }

    /**
     *
     * @return
     */
    public boolean refreshWheel() {
        return false;
    }

    /**
     *
     * @param _color
     */
    public void selected(AColor _color) {
    }
    HSBWheel wheel;
    IValue value;
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public IObserver hsbsliderChanged, rgbSliderChanged, irgbaChanged, colorChanged;

    /**
     *
     * @param _value
     * @param _diam
     */
    public VColorWheel(IValue _value, float _diam) {
        value = _value;
        wheel = new HSBWheel(value, (int) _diam) {

            @Override
            public void selected(IEvent _e) {
                VColorWheel.this.selected((AColor) value.getValue());
            }

            @Override
            public AColor isValidColor(AColor _color) {
                return VColorWheel.this.isValidColor(_color);
            }

            @Override
            public boolean refreshWheel() {
                boolean refesh = super.refreshWheel();
                if (refesh) {
                    return refesh;
                }
                return VColorWheel.this.refreshWheel();
            }
        };
        AColor c = (AColor) value.getValue();
        final Value hue = new Value(new Double(c.getHue()));
        final Value sat = new Value(new Double(c.getSaturation()));
        final Value bri = new Value(new Double(c.getBrightness()));
        final Value alp = new Value(new Double(c.getAlpha()));

        final Value red = new Value(new Double(c.getR() / 255f));
        final Value green = new Value(new Double(c.getG() / 255f));
        final Value blue = new Value(new Double(c.getB() / 255f));
        final Value rgbalp = new Value(new Double(c.getAlpha()));

        final Value ired = new Value(new Integer(c.getR()));
        final Value igreen = new Value(new Integer(c.getG()));
        final Value iblue = new Value(new Integer(c.getB()));
        final Value ialp = new Value(new Integer(c.getA()));

        colorChanged = new AObserver() {

            @Override
            public void change(Change _change) {
                AColor c = (AColor) _change.value();
                hue.value(new Double(c.getHue()));
                sat.value(new Double(c.getSaturation()));
                bri.value(new Double(c.getBrightness()));
                alp.value(new Double(c.getAlpha()));

                red.value(new Double(c.getR() / 255f));
                green.value(new Double(c.getG() / 255f));
                blue.value(new Double(c.getB() / 255f));
                rgbalp.value(new Double(c.getAlpha()));

                ired.value(new Integer(c.getR()));
                igreen.value(new Integer(c.getG()));
                iblue.value(new Integer(c.getB()));
                ialp.value(new Integer(c.getA()));

            }

            public void bound(IObservable _observable) {
            }

            public void released(IObservable _observable) {
            }
        };
        ((IObservable) value).bind(colorChanged);

        hsbsliderChanged = new AObserver() {

            @Override
            public void change(Change _change) {
                AColor c = new AColor(AColor.HSBtoRGB((float) hue.doubleValue(), (float) sat.doubleValue(), (float) bri.doubleValue()));
                c.setA((float) alp.doubleValue());
                value.setValue(c);
                VColorWheel.this.paint();
            }
            public void bound(IObservable _observable) {
            }

            public void released(IObservable _observable) {
            }
        };
        hue.bind(hsbsliderChanged);
        sat.bind(hsbsliderChanged);
        bri.bind(hsbsliderChanged);
        alp.bind(hsbsliderChanged);


        rgbSliderChanged = new AObserver() {

            @Override
            public void change(Change _change) {
                AColor c = new AColor((int) (red.doubleValue() * 255), (int) (green.doubleValue() * 255), (int) (blue.doubleValue() * 255), (int) (rgbalp.doubleValue() * 255));
                value.setValue(c);
                VColorWheel.this.paint();
            }
            public void bound(IObservable _observable) {
            }

            public void released(IObservable _observable) {
            }
        };
        red.bind(rgbSliderChanged);
        green.bind(rgbSliderChanged);
        blue.bind(rgbSliderChanged);
        rgbalp.bind(rgbSliderChanged);

        irgbaChanged = new AObserver() {

            @Override
            public void change(Change _change) {
                AColor c = new AColor(ired.intValue(), igreen.intValue(), iblue.intValue(), ialp.intValue());
                value.setValue(c);
                VColorWheel.this.paint();
            }
            public void bound(IObservable _observable) {
            }

            public void released(IObservable _observable) {
            }
        };
        ired.bind(irgbaChanged);
        igreen.bind(irgbaChanged);
        iblue.bind(irgbaChanged);
        ialp.bind(irgbaChanged);


        VChain chain = new VChain(UV.cSN);
        chain.add(UV.zone("Color Wheel", wheel));

        VChain edit = new VChain(UV.cEW);
        edit.add(new VEditValue("R:", ired, "", false, 40));
        edit.add(new VEditValue("G:", igreen, "", false, 40));
        edit.add(new VEditValue("B:", iblue, "", false, 40));
        edit.add(new VEditValue("A:", ialp, "", false, 40));
        VChain hsbc = new VChain(UV.cSN);


        hsbc.add(new VSlider(hue, "H", _diam, 16, true, true));
        hsbc.add(new VSlider(sat, "S", _diam, 16, true, true));
        hsbc.add(new VSlider(bri, "B", _diam, 16, true, true));
        hsbc.add(new VSlider(alp, "A", _diam, 16, true, true));

        chain.add(UV.zone("HSB", hsbc));

        VChain rgbc = new VChain(UV.cSN);
        rgbc.add(new VSlider(red, "R", _diam, 16, true, true));
        rgbc.add(new VSlider(green, "G", _diam, 16, true, true));
        rgbc.add(new VSlider(blue, "B", _diam, 16, true, true));
        rgbc.add(new VSlider(rgbalp, "A", _diam, 16, true, true));



        rgbc.add(edit);
        chain.add(UV.zone("RGB", rgbc));

        setContent(chain);
    }
}
