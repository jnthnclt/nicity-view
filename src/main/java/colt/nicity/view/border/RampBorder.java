/*
 * RampBorder.java.java
 *
 * Created on 03-12-2010 06:32:01 PM
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

import colt.nicity.core.value.IValue;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.Place;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class RampBorder extends AFlaggedBorder {

    private static float x = 0;
    private static float y = 0;
    private static float w = 0;
    private static float h = 0;
    private Object rampColor = ViewColor.cTheme;
    Place place = UV.cEW;

    /**
     *
     * @param _color
     * @param _place
     */
    public RampBorder(Object _color, Place _place) {
        rampColor = _color;
        place = _place;
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

        AColor _color = ViewColor.cTheme;
        if (rampColor instanceof AColor) {
            _color = (AColor) rampColor;
        } else if (rampColor instanceof IValue) {
            _color = (AColor) ((IValue) rampColor).getValue();
        }
        paint(_color, place, _g, x, y, _w, _h);
    }

    /**
     *
     * @param _color
     * @param place
     * @param _g
     * @param x
     * @param y
     * @param _w
     * @param _h
     */
    public static void paint(AColor _color, Place place, ICanvas _g, int x, int y, int _w, int _h) {
        double step = (Math.PI / 2) / (_h / 2);
        float hue = _color.getHue();
        float sat = _color.getSaturation();
        float bri = _color.getBrightness();

        for (double i = 0; i < (Math.PI / 2); i += step) {
            double s = Math.sin(i);
            double e = Math.sin(i + step);

            _g.setColor(
                    new AColor(_color.getHue(), sat * (float) (s), bri + ((1 - bri) * (float) (s))));


            if (place == UV.cEW) {
                double _sx = (s) * _w;
                double _ex = (e) * _w;
                _g.rect(true, x + (int) (_sx), y + 0, (int) (_ex), _h);
            } else if (place == UV.cWE) {
                double _sx = (s) * _w;
                double _ex = (e) * _w;
                _g.rect(true, x + _w - (int) (_ex), y + 0, _w - (int) (_sx), _h);
            } else if (place == UV.cNS) {
                double _sy = (s) * _h;
                double _ey = (e) * _h;
                _g.rect(true, x + 0, y + (int) _sy, _w, (int) _ey);
            } else if (place == UV.cSN) {
                double _sy = (s) * _h;
                double _ey = (e) * _h;
                _g.rect(true, x + 0, y + _h - (int) (_sy), _w, _h - (int) (_ey));
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return w;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return h;
    }
}
