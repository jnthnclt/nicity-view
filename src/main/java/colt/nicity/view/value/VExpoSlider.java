/*
 * VExpoSlider.java.java
 *
 * Created on 03-12-2010 06:40:01 PM
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

import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.core.value.Value;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class VExpoSlider extends VSlider {//dg

    /**
     *
     */
    public double pow = 1d;

    /**
     *
     * @param _value
     * @param _title
     * @param _w
     * @param _h
     * @param _horizontal
     */
    public VExpoSlider(Value<Double> _value, Object _title, float _w, float _h, boolean _horizontal) {
        super(_value, _title, _w, _h, _horizontal);
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
    }

    /**
     *
     * @param p
     */
    @Override
    public void select(XY_I p) {
        if (horizontal) {
            if (p.x > jw || p.x < 0) {
                return;
            }
            double _value = Math.pow(((double) p.x / (double) (jw)), pow);
            if (sign) {
                value.setValue(new Double(_value));
            } else {
                value.setValue(new Double(-_value));
            }
        } else {
            if (p.y > jh || p.y < 0) {
                return;
            }
            double _value = Math.pow(1 - ((double) p.y / (double) (jh)), pow);
            if (sign) {
                value.setValue(new Double(_value));
            } else {
                value.setValue(new Double(-_value));
            }
        }
        paint();
    }
}
