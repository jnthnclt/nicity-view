/*
 * WindowFlavor.java.java
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
package colt.nicity.view.flavor;

import colt.nicity.view.core.AColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class WindowFlavor extends AFlavor {

    /**
     *
     * @param g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _color
     */
    @Override
    public void paintFlavor(ICanvas g, int _x, int _y, int _w, int _h, AColor _color) {

        AColor color = _color;
        int r = 3;

        g.setColor(color.desaturate(0.5f).darken(0.2f));
        g.roundRect(true, _x + 0, _y + 0, _w , _h , r, r);

        g.setColor(color.desaturate(0.2f).darken(0.05f));
        g.roundRect(true, _x + 1, _y + 1, _w - 2, _h - 2, r, r);

        int h = _h - 5;
        int t = (int) (h * 0.2);
        int m = (int) (h * 0.65);
        int b = (int) (h * 0.15);
        if (t > 32) {
            m += t - 32;
            t = 32;
        }

        int cl = _x + _w / 2;


        AColor dark = color.desaturate(0.15f).darken(0.1f);
        AColor middle = color;
        AColor light = color.desaturate(0.15f).lighten(0.25f);

        g.setGradient(light, cl, _y, middle, cl, _y + t);
        g.roundRect(true, _x + 2, _y + 2, _w - 4, t, r - 1, r - 1);

        g.setGradient(middle, cl, _y + t, light, cl, _y + t + m);
        g.roundRect(true, _x + 2, _y + 2 + t, _w - 4, m, r - 1, r - 1);

        g.setGradient(light, cl, _y + t + m, dark, cl, _y + t + m + b);
        g.roundRect(true, _x + 2, _y + 2 + t + m, _w - 4, b, r - 1, r - 1);


    }
}
