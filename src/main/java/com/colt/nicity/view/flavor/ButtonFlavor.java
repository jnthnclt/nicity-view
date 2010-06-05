/*
 * ButtonFlavor.java.java
 *
 * Created on 01-03-2010 01:31:35 PM
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
package com.colt.nicity.view.flavor;

import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class ButtonFlavor extends AFlavor {

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


        int r = 8;

        int hh = _h / 2;
        int qh = _h / 4;

        g.setColor(color);
        g.roundRect(true, _x + 0, _y + 0, _w - 1, _h - 1, r, r);

        g.setColor(color.desaturate(0.5f).darken(0.2f));
        g.roundRect(false, _x + 0, _y + 0, _w - 1, _h - 1, r, r);


        int cl = _x + (_w / 2);

        AColor fc = color.desaturate(0.15f).lighten(0.025f);
        AColor tc = color.desaturate(0.15f).lighten(0.06f);

        g.setGradient(fc, cl, _y, tc, cl, _y + hh);
        g.roundRect(true, _x + 2, _y + 2, _w - 4, hh, hh / 2, hh / 2);

        fc = color.desaturate(0.07f).darken(0.025f);
        tc = color.desaturate(0.1f).darken(0.05f);

        g.setGradient(fc, cl, _y + hh, tc, cl, _y + hh + hh);
        g.roundRect(true, _x + 2, _y + 2 + hh, _w - 4, hh - 3, hh / 2, hh / 2);


        // accent
        g.setColor(color.desaturate(0.3f).lighten(0.15f));
        g.roundRect(true, _x + 4, _y + 4, 2, 2, hh / 2, hh / 2);

    }
}
