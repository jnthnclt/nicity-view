/*
 * MenuItemBorder.java.java
 *
 * Created on 01-03-2010 01:31:39 PM
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
public class MenuItemBorder extends AFlaggedBorder {

    private static float x = 4;
    private static float y = 0;
    private static float w = 4;
    private static float h = 0;
    AColor color = ViewColor.cTheme;

    /**
     *
     */
    public MenuItemBorder() {
    }

    /**
     *
     * @param _color
     */
    public MenuItemBorder(AColor _color) {
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
            _color = ViewColor.cThemeActive;//_color.darken(0.2f);
        } else if (is(cSelected)) {
            _color = ViewColor.cThemeSelected;//_color.darken(0.4f);
        }

        g.setColor(_color);
        g.rect(true, x + 0, y + 0, _w, _h);

        g.setColor(_color.lighten(0.05f));
        g.roundRect(true, x + 1, y + 1, _w - 2, _h - 2, 4, 4);

        g.setColor(_color.lighten(0.1f));
        g.roundRect(true, x + 2, y + 2, _w - 4, _h - 6, 6, 6);

        g.setColor(_color.lighten(0.4f));
        g.roundRect(true, x + 3, y + 3, _w - 8, _h - 16, 16, 16);
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
