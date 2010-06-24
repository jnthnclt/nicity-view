/*
 * VMeter.java.java
 *
 * Created on 03-12-2010 06:39:24 PM
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

import colt.nicity.core.value.Value;
import colt.nicity.view.border.SolidBorder;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.Place;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class VMeter extends Viewer {

    /**
     *
     */
    public Value<Double> meter = null;
    private Place place = UV.cNS;
    private double levels = 10;

    /**
     *
     * @param _meter
     * @param _w
     * @param _h
     * @param _levels
     * @param _place
     */
    public VMeter(Value<Double> _meter, int _w, int _h, double _levels, Place _place) {
        meter = _meter;
        place = _place;
        levels = _levels;
        setPlacer(new Placer(new RigidBox(_w, _h)));
        setBorder(new SolidBorder(AColor.white));
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

        double max = meter.doubleValue();

        if (place == UV.cNS) {

            int h = (int) (_h / levels);
            int y = h;
            double i = 0;
            done:
            {
                if (max < 0.3d) {
                    _g.setColor(AColor.green);
                }
                else if (max < 0.6d) {
                    _g.setColor(AColor.orange);
                }
                else {
                    _g.setColor(AColor.red);
                }
                for (; i < 1.0d; i += 1 / levels) {
                    if (i >= max) {
                        break done;
                    }
                    _g.roundRect(true, _x + 1, _y + _h - y, _w - 3, h - 1, 2, 2);
                    y += h;

                }
            }
        }
        else {
            int w = (int) (_w / levels);
            int x = 0;
            double i = 0;
            done:
            {
                _g.setColor(AColor.green);
                for (; i < 0.6d; i += 1 / levels) {
                    if (i >= max) {
                        break done;
                    }
                    _g.roundRect(true, _x + x, _y + 1, w - 1, _h - 3, 2, 2);
                    x += w;
                }
                _g.setColor(AColor.orange);
                for (; i < 0.8d; i += 1 / levels) {
                    if (i >= max) {
                        break done;
                    }
                    _g.roundRect(true, _x + x, _y + 1, w - 1, _h - 3, 2, 2);
                    x += w;
                }
                _g.setColor(AColor.red);
                for (; i < 1; i += 1 / levels) {
                    if (i >= max) {
                        break done;
                    }
                    _g.roundRect(true, _x + x, _y + 1, w - 1, _h - 3, 2, 2);
                    x += w;
                }
            }
        }
    }
}
