/*
 * BlankButtonBorder.java.java
 *
 * Created on 03-12-2010 06:31:33 PM
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
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class BlankButtonBorder extends AFlaggedBorder {

    private int pad = 2;
    private Object buttonColor = ViewColor.cTheme;

    /**
     *
     */
    public BlankButtonBorder() {
        this(ViewColor.cTheme, 2);
    }

    /**
     *
     * @param _pad
     */
    public BlankButtonBorder(int _pad) {
        this(ViewColor.cTheme, _pad);
    }

    /**
     *
     * @param _color
     */
    public BlankButtonBorder(Object _color) {
        this(_color, 2);
    }

    /**
     *
     * @param _color
     * @param _pad
     */
    public BlankButtonBorder(Object _color, int _pad) {
        buttonColor = _color;
        pad = _pad;
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
        AColor color = ViewColor.cTheme;
        if (buttonColor instanceof AColor) {
            color = (AColor) buttonColor;
        } else if (buttonColor instanceof IValue) {
            color = (AColor) ((IValue) buttonColor).getValue();
        }


        int r = 5;

        RoundButtonBorder.paint(
                g, x, y, _w, _h, r, Math.min(_w, _h),
                color);

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
