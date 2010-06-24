/*
 * SliderBarFlavor.java.java
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
package colt.nicity.view.flavor;

import colt.nicity.view.core.AColor;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;
import java.awt.Polygon;

/**
 *
 * @author Administrator
 */
public class SliderBarFlavor extends AFlavor {

    /**
     *
     */
    public static SliderBarFlavor pointingUp = new SliderBarFlavor() {

        @Override
        public void paintFlavor(ICanvas _g, int _x, int _y, int _w, int _h, AColor _color) {

            int hw = _w / 2;
            int hh = _h / 2;
            int x = _x + hw;
            int y = _y;
            Polygon p = new Polygon(
                    new int[]{x, x + hw, x + hw, x - hw, x - hw, x},
                    new int[]{y, y + hh, y + _h, y + _h, y + hh, y},
                    6);
            _g.setColor(_color);
            _g.polygon(true, p.xpoints, p.ypoints, p.npoints);
            _g.setColor(ViewColor.cThemeHighlight);
            _g.polygon(false, p.xpoints, p.ypoints, p.npoints);
        }
    };
    /**
     *
     */
    public static SliderBarFlavor pointingDown = new SliderBarFlavor() {

        @Override
        public void paintFlavor(ICanvas _g, int _x, int _y, int _w, int _h, AColor _color) {

            int hw = _w / 2;
            int hh = _h / 2;
            int x = _x + hw;
            int y = _y;
            Polygon p = new Polygon(
                    new int[]{x, x + hw, x + hw, x - hw, x - hw, x},
                    new int[]{y + _h, y + hh, y, y, y + hh, y + _h},
                    6);
            _g.setColor(_color);
            _g.polygon(true, p.xpoints, p.ypoints, p.npoints);
            _g.setColor(ViewColor.cThemeHighlight);
            _g.polygon(false, p.xpoints, p.ypoints, p.npoints);
        }
    };
    /**
     *
     */
    public static SliderBarFlavor pointingRight = new SliderBarFlavor() {

        @Override
        public void paintFlavor(ICanvas _g, int _x, int _y, int _w, int _h, AColor _color) {

            int hw = _w / 2;
            int hh = _h / 2;
            int x = _x + hw;
            int y = _y;
            Polygon p = new Polygon(
                    new int[]{x + _w, x + hw, x, x, x + hw, x + _w},
                    new int[]{y + hh, y + _h, y + _h, y, y, y + hh},
                    6);
            _g.setColor(_color);
            _g.polygon(true, p.xpoints, p.ypoints, p.npoints);
            _g.setColor(ViewColor.cThemeHighlight);
            _g.polygon(false, p.xpoints, p.ypoints, p.npoints);
        }
    };
    /**
     *
     */
    public static SliderBarFlavor pointingLeft = new SliderBarFlavor() {

        @Override
        public void paintFlavor(ICanvas _g, int _x, int _y, int _w, int _h, AColor _color) {

            int hw = _w / 2;
            int hh = _h / 2;
            int x = _x + hw;
            int y = _y;
            Polygon p = new Polygon(
                    new int[]{x, x + hw, x + _w, x + _w, x + hw, x},
                    new int[]{y + hh, y + _h, y + _h, y, y, y + hh},
                    6);
            _g.setColor(_color);
            _g.polygon(true, p.xpoints, p.ypoints, p.npoints);
            _g.setColor(ViewColor.cThemeHighlight);
            _g.polygon(false, p.xpoints, p.ypoints, p.npoints);
        }
    };

    /**
     *
     */
    public SliderBarFlavor() {
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _color
     */
    public void paintFlavor(ICanvas _g, int _x, int _y, int _w, int _h, AColor _color) {
        pointingUp.paintFlavor(_g, _x, _y, _w, _h, _color);
    }
}
