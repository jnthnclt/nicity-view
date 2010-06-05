/*
 * RampMenuBorder.java.java
 *
 * Created on 03-12-2010 06:32:08 PM
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
package com.colt.nicity.view.border;

import com.colt.nicity.core.value.IValue;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.Place;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class RampMenuBorder extends AFlaggedBorder {

    private static float x = 1;
    private static float y = 0;
    private static float w = 0;
    private static float h = 0;
    Object color = ViewColor.cTheme;
    Place place;

    /**
     *
     * @param _place
     */
    public RampMenuBorder(Place _place) {
        place = _place;
    }

    /**
     *
     * @param _color
     * @param _place
     */
    public RampMenuBorder(Object _color, Place _place) {
        color = _color;
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
     * @param g
     * @param x
     * @param y
     * @param _w
     * @param _h
     */
    @Override
    public void paintBackground(ICanvas g, int x, int y, int _w, int _h) {
        AColor _color = ViewColor.cTheme;
        if (color instanceof AColor) {
            _color = (AColor) color;
        } else if (color instanceof IValue) {
            _color = (AColor) ((IValue) color).getValue();
        }
        if (is(cActive)) {
            _color = _color.lighten(0.2f);
        }
        if (is(cSelected)) {
            _color = _color.darken(0.2f);
        }


        RampBorder.paint(_color, place, g, x, y, _w, _h);

        //g.setColor(_color.darken(0.32f));
        //g.line(0, 1, 0,_h-3);//vertleft
        //g.line(0, _h-1, _w,_h-1);//baseline

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
