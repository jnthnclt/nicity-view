/*
 * RoundBorder.java.java
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
package com.colt.nicity.view.border;

import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class RoundBorder extends AFlaggedBorder {

    private int pad = 2;
    private AColor buttonColor = ViewColor.cButtonTheme;

    /**
     *
     */
    public RoundBorder() {
        this(ViewColor.cButtonTheme);
    }

    /**
     *
     * @param _color
     */
    public RoundBorder(AColor _color) {
        this(_color, 2);
    }

    /**
     *
     * @param _color
     * @param _pad
     */
    public RoundBorder(AColor _color, int _pad) {
        buttonColor = _color;
        pad = _pad;
    }

    /**
     *
     * @param _color
     */
    public void setColor(AColor _color) {
        buttonColor = _color;
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
    public void paintBorder(ICanvas g, int x, int y, int _w, int _h) {
        AColor color = buttonColor;
        if (is(cActive)) {
            color = ViewColor.cThemeActive;
        }
        if (is(cSelected)) {
            color = ViewColor.cThemeSelected;
        }
        int r = Math.min(_w, _h);

        paint(
                g, x + 1, y + 1, _w - 2, _h - 2, r - 1, r - 1,
                color.desaturate(0.2f).darken(0.25f), color.desaturate(0.05f).darken(0.05f),
                color.lighten(0.2f),
                color.desaturate(0.1f).lighten(0.1f), color.desaturate(0.15f).lighten(0.15f));
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
    }

    /**
     *
     * @param g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param r
     * @param r2
     * @param _darkest
     * @param _darker
     * @param color
     * @param _lighter
     * @param _lightest
     */
    public static void paint(
            ICanvas g,
            int _x, int _y, int _w, int _h, int r, int r2,
            AColor _darkest, AColor _darker,
            AColor color,
            AColor _lighter, AColor _lightest) {
        g.setColor(_darkest);
        g.roundRect(false, _x + 0, _y + 0, _w - 0, _h - 0, r, r);

        g.setColor(_darker);
        g.roundRect(false, _x + 1, _y + 1, _w - 2, _h - 2, r, r);

        g.setColor(color);
        g.roundRect(false, _x + 1, _y + 1, _w - 3, _h - 3, r - 1, r - 1);


    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return pad;
    }
}
