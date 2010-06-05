/*
 * MenuBorder.java.java
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
package com.colt.nicity.view.border;

import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class MenuBorder extends AFlaggedBorder {

    private static float x = 1;
    private static float y = 0;
    private static float w = 0;
    private static float h = 0;
    AColor color = ViewColor.cTheme;

    /**
     *
     */
    public MenuBorder() {
    }

    /**
     *
     * @param _color
     */
    public MenuBorder(AColor _color) {
        color = _color;
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

        AColor _color = color;
        if (is(cActive)) {
            _color = ViewColor.cThemeActive;//color.lighten(0.2f);
        }
        if (is(cSelected)) {
            _color = ViewColor.cThemeSelected;//color.darken(0.2f);
        }

        g.setColor(_color);
        g.rect(true, x, y, _w, _h);

        g.setColor(_color.darken(0.32f));
        g.line(x + 0, y + 1, x + 0, y + _h - 3);//vertleft
        g.line(x + 0, y + _h - 1, x + _w, y + _h - 1);//baseline

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
