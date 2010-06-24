/*
 * RoundFlavor.java.java
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
public class RoundFlavor extends AFlavor {

    /**
     *
     */
    public static RoundFlavor flavor = new RoundFlavor();

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
        int r = Math.min(_w, _h);
        paint(
                g, _x, _y, _w, _h, r,
                color.desaturate(0.2f).darken(0.25f), color.desaturate(0.05f).darken(0.05f),
                color,
                color.desaturate(0.1f).lighten(0.1f), color.desaturate(0.15f).lighten(0.15f));

    }

    /**
     *
     * @param g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param r
     * @param _darkest
     * @param _darker
     * @param color
     * @param _lighter
     * @param _lightest
     */
    public static void paint(
            ICanvas g,
            int _x, int _y, int _w, int _h, int r,
            AColor _darkest, AColor _darker,
            AColor color,
            AColor _lighter, AColor _lightest) {
        g.setColor(_darkest);
        g.roundRect(true, _x + 0, _y + 0, _w, _h, r, r);

        g.setColor(_darker);
        g.roundRect(true, _x + 1, _y + 1, _w - 3, _h - 3, r, r);

        g.setColor(color);
        g.roundRect(true, _x + 1, _y + 1, _w - 4, _h - 4, r - 1, r - 1);

        g.setColor(_lighter);
        g.roundRect(true, _x + 2 + (r / 4), _y + 2, _w - (r / 2) - 5, (r / 2) - 4, r, r);

        g.setColor(_lightest);
        g.roundRect(true, _x + 2 + (r / 2), _y + 2, _w - (r) - 5, (r / 4) - 4, r, r);
    }
}
