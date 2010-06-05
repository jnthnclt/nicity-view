/*
 * UnderlineBorder.java.java
 *
 * Created on 01-03-2010 01:31:40 PM
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
public class UnderlineBorder extends AFlaggedBorder {

    private AColor fill = null;
    private AColor line = ViewColor.cThemeFont;
    private int space = 0;
    private int pad = 0;

    /**
     *
     */
    public UnderlineBorder() {
    }

    /**
     *
     * @param _line
     */
    public UnderlineBorder(AColor _line) {
        this(null, _line, 0, 0);
    }

    /**
     *
     * @param _fill
     * @param _line
     */
    public UnderlineBorder(AColor _fill, AColor _line) {
        this(_fill, _line, 0, 0);
    }

    /**
     *
     * @param _fill
     * @param _line
     * @param _pad
     */
    public UnderlineBorder(AColor _fill, AColor _line, int _pad) {
        this(_fill, _line, _pad, 0);
    }

    /**
     *
     * @param _fill
     * @param _line
     * @param _pad
     * @param _space
     */
    public UnderlineBorder(AColor _fill, AColor _line, int _pad, int _space) {
        fill = _fill;
        line = _line;
        pad = _pad;
        space = _space;
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
        if (line != null) {
            g.setColor(line);
            g.rect(true, x + space, y + _h - (space * 2) - 3, _w - (space * 2) - 1, _h - (space * 2) - 1);
        }
        if (is(cActive)) {
            g.setColor(ViewColor.cThemeActive);
            g.rect(true, x + space, y + _h - (space * 2) - 3, _w - (space * 2) - 1, _h - (space * 2) - 1);
        }
        if (is(cSelected)) {
            g.setColor(ViewColor.cThemeSelected);
            g.rect(true, x + space, y + _h - (space * 2) - 3, _w - (space * 2) - 1, _h - (space * 2) - 1);
        }
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
        if (fill != null) {
            g.setColor(fill);
            g.rect(true, x, y, _w, _h);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return pad + space;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return pad + space;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return pad + space;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return pad + space;
    }
}
