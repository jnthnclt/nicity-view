/*
 * VerticalBuldgeFlavor.java.java
 *
 * Created on 01-03-2010 01:31:36 PM
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
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class VerticalBuldgeFlavor extends AFlavor {

    /**
     *
     * @param g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _color
     */
    public void paintFlavor(ICanvas g, int _x, int _y, int _w, int _h, AColor _color) {

        AColor color = _color;


        int r = 3;


        g.setColor(color.desaturate(0.5f).darken(0.2f));
        g.roundRect(true, _x + 0, _y + 0, _w - 1, _h - 1, r, r);

        g.setColor(color.desaturate(0.5f).lighten(0.2f));
        g.roundRect(false, _x + 1, _y + 1, _w - 3, _h - 3, r - 1, r - 1);

        AColor fc = color.desaturate(0.5f).lighten(0.2f);
        AColor tc = color.desaturate(0.5f).darken(0.2f);

        int cl = _y + (_h / 2);
        g.setGradient(tc, _x, cl, fc, _x + (_w / 2), cl);
        g.roundRect(true, _x + 2, _y + 2, (_w / 2), _h - 5, r - 2, r - 2);

        g.setGradient(fc, _x + (_w / 2), cl, tc, _x + _w, cl);
        g.roundRect(true, _x + (_w / 2) + 2, _y + 2, (_w / 2) - 5, _h - 5, r - 2, r - 2);

    }
}
