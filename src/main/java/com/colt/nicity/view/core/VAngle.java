/*
 * VAngle.java.java
 *
 * Created on 01-03-2010 01:31:37 PM
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

import com.colt.nicity.core.lang.UMath;
import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class VAngle extends AViewableWH {

    /**
     *
     */
    protected AColor color = AColor.white;
    /**
     *
     */
    protected double radians;

    /**
     *
     * @param _size
     * @param _radians
     * @param _color
     */
    public VAngle(float _size, double _radians, AColor _color) {
        this(_size, _size, _radians, _color);
    }

    /**
     *
     * @param _w
     * @param _h
     * @param _radians
     * @param _color
     */
    public VAngle(float _w, float _h, double _radians, AColor _color) {
        w = _w;
        h = _h;
        if (_color != null) {
            color = _color;
        }
        radians = _radians;
    }

    /**
     *
     * @return
     */
    public AColor getColor() {
        return color;
    }

    /**
     *
     * @param _color
     */
    public void setColor(AColor _color) {
        if (_color != null) {
            color = _color;
        }
    }

    @Override
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        super.paintBody(g, _layer, mode, _painted);
        g.setColor(color);
        g.oval(true, 0, 0, (int) w, (int) h);

        g.setColor(color.invert());
        double[] vec = UMath.vector(radians, (Math.max(w, h) / 2));
        int ax = (int) vec[0];
        int ay = (int) vec[1];
        g.line((int) ((w / 2) - ax), (int) ((h / 2) - ay), (int) ((w / 2) + ax), (int) ((h / 2) + ay));
    }
}
