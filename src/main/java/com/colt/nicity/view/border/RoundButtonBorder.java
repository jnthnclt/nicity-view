/*
 * RoundButtonBorder.java.java
 *
 * Created on 03-12-2010 06:32:12 PM
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
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.flavor.ButtonFlavor;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class RoundButtonBorder extends AFlaggedBorder {

    static ButtonFlavor flavor = new ButtonFlavor();
    private int maxRound = 100;
    private int pad = 4;
    private AColor envColor = ViewColor.cButtonThemeHighlight;
    private Object buttonColor = ViewColor.cButtonTheme;

    /**
     *
     */
    public RoundButtonBorder() {
        this(ViewColor.cButtonTheme);
    }

    /**
     *
     * @param _color
     */
    public RoundButtonBorder(Object _color) {
        this(_color, 4);
    }

    /**
     *
     * @param _color
     * @param _pad
     */
    public RoundButtonBorder(Object _color, int _pad) {
        buttonColor = _color;
        pad = _pad;
    }

    /**
     *
     * @param _env
     * @param _color
     * @param _pad
     * @param _maxRound
     */
    public RoundButtonBorder(AColor _env, Object _color, int _pad, int _maxRound) {
        envColor = _env;
        buttonColor = _color;
        pad = _pad;
        maxRound = _maxRound;
    }

    /**
     *
     * @param _color
     */
    public void setColor(Object _color) {
        buttonColor = _color;
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
        if (buttonColor == null) {
            return;
        }
        AColor color = ViewColor.cButtonTheme;
        AColor _color = color;
        if (buttonColor instanceof AColor) {
            color = (AColor) buttonColor;
        } else if (buttonColor instanceof IValue) {
            color = (AColor) ((IValue) buttonColor).getValue();
        }
        if (color == null) {
            return;
        }
        if (is(cActive)) {
            color = ViewColor.cThemeActive;
        }
        if (is(cSelected)) {
            color = ViewColor.cThemeSelected;
        }

        int r = Math.min(_w, _h);
        if (r > maxRound) {
            r = maxRound;
        }

        paint(
                g, x, y, _w, _h, r - 1, r - 1,
                color);


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
     * @param color
     */
    public static void paint(
            ICanvas g,
            int _x, int _y, int _w, int _h, int r, int r2,
            AColor color) {

        AColor _darkest = color.desaturate(0.1f).darken(0.2f);
        AColor _darker = color.desaturate(0.05f).darken(0.125f);
        AColor _lighter = color.desaturate(0.05f).lighten(0.125f);
        AColor _lightest = color.desaturate(0.1f).lighten(0.2f);

        int add = 0;
        int subtract = 0;

        // button shadow
        g.setColor(_darkest);
        g.roundRect(true, _x + add, _y + add, _w - subtract, _h - subtract, r - add, r - add);
        add += 0;
        subtract += 1;

        g.setColor(_darker);
        g.roundRect(true, _x + add, _y + add, _w - subtract, _h - subtract, r - add, r - add);
        add += 1;
        subtract += 2;

        g.setColor(color);
        g.roundRect(true, _x + add, _y + add, _w - subtract, _h - subtract, r - add, r - add);
        subtract += 2;

        if (_lighter != null) {
            g.setColor(_lighter);
            g.roundRect(true, _x + add + (r / 3), _y + add, _w - (r / 2) - subtract, (r2 / 2) - subtract, r2, r2);
        }
        if (_lightest != null) {
            g.setColor(_lightest);
            g.roundRect(true, _x + add + (r / 2), _y + add, _w - (r) - subtract, (r2 / 3) - subtract, r2, r2);
        }
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
