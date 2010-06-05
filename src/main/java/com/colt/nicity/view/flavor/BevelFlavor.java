/*
 * BevelFlavor.java.java
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
public class BevelFlavor extends AFlavor {

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
        g.setColor(_color);
        g.rect(true, _x, _y, _w, _h);

        g.setColor(AColor.white);
        g.line(_x + 0, _y + 0, _x + 0, _y + (_h - 1));
        g.line(_x + 1, _y + 0, _x + (_w - 1), _y + (0));

        g.setColor(AColor.lightGray);
        g.line(_x + 1, _y + 1, _x + (1), _y + (_h - 2));
        g.line(_x + 2, _y + 1, _x + (_w - 2), _y + (1));

        g.setColor(AColor.darkGray);
        g.line(_x + 1, _y + (_h - 1), _x + (_w - 2), _y + (_h - 1));
        g.line(_x + (_w - 1), _y + (1), _x + (_w - 1), _y + (_h - 2));

        g.setColor(AColor.gray);
        g.line(_x + 2, _y + (_h - 2), _x + (_w - 2), _y + (_h - 2));
        g.line(_x + (_w - 2), _y + (2), _x + (_w - 2), _y + (_h - 3));
    }
}
