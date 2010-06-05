/*
 * ClearItemBorder.java.java
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

import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class ClearItemBorder extends AFlaggedBorder {

    private static float x = 0;
    private static float y = 0;
    private static float w = 0;
    private static float h = 0;
    private int padW = 0;
    private int padH = 0;

    /**
     *
     */
    public ClearItemBorder() {
        this(0);
    }

    /**
     *
     * @param _pad
     */
    public ClearItemBorder(int _pad) {
        this(_pad, _pad);
    }

    /**
     *
     * @param _padW
     * @param _padH
     */
    public ClearItemBorder(int _padW, int _padH) {
        padH = _padW;
        padW = _padH;
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
        if (is(cActive)) {
            g.setColor(ViewColor.cThemeActive);
            g.rect(true, x, y, _w - 1, _h - 1);
        }
        if (is(cSelected)) {
            g.setColor(ViewColor.cThemeSelected);
            g.rect(true, x, y, _w - 1, _h - 1);
        }

    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return padW;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return padH;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return padW;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return padH;
    }
}
