/*
 * LogBorder.java.java
 *
 * Created on 01-03-2010 01:31:34 PM
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
package colt.nicity.view.border;

import colt.nicity.core.lang.UDouble;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class LogBorder extends AFlaggedBorder {

    private int pad = 2;
    private double rate = 0.2;
    private AColor color = ViewColor.cThemeShadow;

    /**
     *
     * @param _color
     */
    public LogBorder(AColor _color) {
        this(_color, 2);
    }

    /**
     *
     * @param _pad
     */
    public LogBorder(int _pad) {
        this(ViewColor.cThemeShadow, _pad);
    }

    /**
     *
     * @param _color
     * @param _pad
     */
    public LogBorder(AColor _color, int _pad) {
        this(_color, _pad, 0.2);
    }

    /**
     *
     * @param _color
     * @param _pad
     * @param _rate
     */
    public LogBorder(AColor _color, int _pad, double _rate) {
        color = _color;
        pad = _pad;
        rate = _rate;
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    @Override
    public void paintBorder(ICanvas g, int x, int y, int w, int h) {
    }

    /**
     *
     * @param _g
     * @param x
     * @param y
     * @param _w
     * @param _h
     */
    @Override
    public void paintBackground(ICanvas _g, int x, int y, int _w, int _h) {

        

        rate = 0.95d;
        double step = 0.01d;
        for (double dx = x; dx < 1 - step; dx += step) {
            double xRamp = UDouble.exponential(dx, rate, 3);
            double xRamp2 = UDouble.exponential(dx + step, rate, 3);
            double xDelta = xRamp2 - xRamp;
            for (double dy = y; dy < 1 - step; dy += step) {
                double yRamp = UDouble.exponential(dy, rate, 3);
                double yRamp2 = UDouble.exponential(dy + step, rate, 3);
                double yDelta = yRamp2 - yRamp;

                double ramp = (xRamp + yRamp) / 2;
                _g.setColor(
                        new AColor(1f - (float) (ramp)));
                _g.rect(true,
                        (int) (_w * xRamp), (int) (_h * yRamp),
                        (int) (_w * xDelta), (int) (_h * yDelta));
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return pad;
    }
}
