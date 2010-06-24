/*
 * PadBorder.java.java
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
package colt.nicity.view.border;

import colt.nicity.view.core.AColor;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class PadBorder extends AFlaggedBorder {

    private int padW = 4, padH = 4;
    private AColor c = ViewColor.cTheme;

    /**
     *
     */
    public PadBorder() {
    }

    /**
     *
     * @param _c
     * @param _padW
     * @param _padH
     */
    public PadBorder(AColor _c, int _padW, int _padH) {
        c = _c;
        padW = _padW;
        padH = _padH;
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
        _w -= 1;
        _h -= 1;
        if (c != null) {
            AColor color = c;
            g.setColor(color);
            g.rect(true, x, y, _w, _h);
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
