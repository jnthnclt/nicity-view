/*
 * ScrollFlavor.java.java
 *
 * Created on 01-03-2010 01:31:33 PM
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
public class ScrollFlavor extends AFlavor {

    /**
     *
     */
    public static ScrollFlavor flavor = new ScrollFlavor();

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
        
        int range = 15;
        AColor color = _color;
        AColor darker = new AColor(color.getR()-range,color.getG()-range,color.getB()-range);
        AColor brighter = new AColor(color.getR()+range,color.getG()+range,color.getB()+range);
        int r = 5;
        if (_w >= _h) {
            g.setColor(darker);
            g.roundRect(true, _x + 0, _y + 0, _w - 0, _h - 0, r, r);

            g.setColor(brighter);
            g.roundRect(false, _x + 1, _y + 1, _w - 2, _h - 2, r - 1, r - 1);

            AColor fc = brighter;
            AColor tc = darker;

            int cl = _x + (_w / 2);
            g.setGradient(fc, cl, _y, tc, cl, _y + _h);
            g.roundRect(true, _x + 2, _y + 2, _w - 4, _h - 4, r - 2, r - 2);

        } else {
            g.setColor(darker);
            g.roundRect(true, _x + 0, _y + 0, _w - 0, _h - 0, r, r);

            g.setColor(brighter);
            g.roundRect(false, _x + 1, _y + 1, _w - 2, _h - 2, r - 1, r - 1);

            AColor fc = brighter;
            AColor tc = darker;

            int cl = _y + (_h / 2);
            g.setGradient(fc, _x, cl, tc, _x + _w, cl);
            g.roundRect(true, _x + 2, _y + 2, _w - 4, _h - 4, r - 2, r - 2);

        }
    }
}
