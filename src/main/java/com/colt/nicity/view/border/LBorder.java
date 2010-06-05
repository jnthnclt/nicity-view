/*
 * LBorder.java.java
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
public class LBorder extends AFlaggedBorder {

    private float x = 0;
    private float y = 0;
    private float w = 0;
    private float h = 0;
    private AColor buttonColor = ViewColor.cTheme;
    private int size = 3;

    /**
     *
     * @param _size
     */
    public LBorder(int _size) {
        this(ViewColor.cTheme, _size);
    }

    /**
     *
     * @param _color
     * @param _size
     */
    public LBorder(AColor _color, int _size) {
        buttonColor = _color;
        size = _size;
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
     * @param w
     * @param h
     */
    @Override
    public void paintBorder(ICanvas g, int x, int y, int w, int h) {
        AColor color = buttonColor;
        g.setColor(color.darken(0.4f));
        g.line(x + 0, y + 0, x + 0, y + h);
        g.line(x + 0, y + 0, x + size, y + 0);
        g.line(x + 0, y + h - 1, x + size, y + h - 1);
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
    public void paintBackground(ICanvas g, int x, int y, int w, int h) {
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
