/*
 * SoftFlavor.java.java
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
package com.colt.nicity.view.flavor;

import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class SoftFlavor extends AFlavor {

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
        if (r < 3) {
            r = 3;
        }
        paint(
                g, _x, _y, _w, _h, r,
                color);

    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param r
     * @param color
     */
    public static void paint(
            ICanvas _g,
            int _x, int _y, int _w, int _h, int r,
            AColor color) {

        _g.setColor(color);
        for (int i = r; i > -1; i -= 3) {
            float p = (float) (r - i) / (float) r;
            p = (float) Math.pow(p, 4f);
            p *= 0.75f;
            _g.setAlpha(p, 0);
            _g.roundRect(true, _x - i, _y - i, _w + (i * 2), _h + (i * 2), r + i, r + i);
        }
        _g.setAlpha(1f, 0);

    }
}
