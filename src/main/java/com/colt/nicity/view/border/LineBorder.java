/*
 * LineBorder.java.java
 *
 * Created on 03-12-2010 06:31:53 PM
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
public class LineBorder extends AFlaggedBorder {

    private Object fill = ViewColor.cTheme;
    private AColor line = AColor.gray;
    private int space = 0;
    /**
     *
     */
    protected int padT = 0;
    /**
     *
     */
    protected int padB = 0;
    /**
     *
     */
    protected int padR = 0;
    /**
     *
     */
    protected int padL = 0;
    private int round = 0;

    /**
     *
     */
    public LineBorder() {
    }

    /**
     *
     * @param _line
     * @param _pad
     */
    public LineBorder(AColor _line, int _pad) {
        this(ViewColor.cTheme, _line, _pad, 0);
    }

    /**
     *
     * @param _line
     * @param _pad
     * @param _round
     */
    public LineBorder(AColor _line, int _pad, int _round) {
        this(ViewColor.cTheme, _line, _pad, _round);
    }

    /**
     *
     * @param _line
     */
    public LineBorder(AColor _line) {
        this(ViewColor.cTheme, _line, 0, 0);
    }

    /**
     *
     * @param _fill
     * @param _line
     */
    public LineBorder(Object _fill, AColor _line) {
        this(_fill, _line, 0, 0);
    }

    /**
     *
     * @param _fill
     * @param _line
     * @param _padT
     * @param _padB
     * @param _padR
     * @param _padL
     */
    public LineBorder(Object _fill, AColor _line, int _padT, int _padB, int _padR, int _padL) {
        fill = _fill;
        line = _line;
        padT = _padT;
        padB = _padB;
        padR = _padR;
        padL = _padL;
        round = 0;
        space = 0;
    }

    /**
     *
     * @param _fill
     * @param _line
     * @param _pad
     */
    public LineBorder(Object _fill, AColor _line, int _pad) {
        this(_fill, _line, _pad, 0);
    }

    /**
     *
     * @param _fill
     * @param _line
     * @param _pad
     * @param _round
     */
    public LineBorder(Object _fill, AColor _line, int _pad, int _round) {
        this(_fill, _line, _pad, _round, 0);
    }

    /**
     *
     * @param _line
     * @param _pad
     * @param _round
     * @param _space
     */
    public LineBorder(AColor _line, int _pad, int _round, int _space) {
        this(ViewColor.cTheme, _line, _pad, _round, _space);
    }

    /**
     *
     * @param _fill
     * @param _line
     * @param _pad
     * @param _round
     * @param _space
     */
    public LineBorder(Object _fill, AColor _line, int _pad, int _round, int _space) {
        fill = _fill;
        line = _line;
        padT = _pad;
        padB = _pad;
        padR = _pad;
        padL = _pad;
        round = _round;
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
            if (round > 0) {
                g.roundRect(false, x + space, y + space, _w - (space * 2) - 1, _h - (space * 2) - 1, round, round);
            } else {
                g.rect(false, x + space, y + space, _w - (space * 2) - 1, _h - (space * 2) - 1);
            }
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
        AColor color = null;
        if (fill instanceof AColor) {
            color = (AColor) fill;
        } else if (fill instanceof IValue) {
            color = (AColor) ((IValue) fill).getValue();
        }
        if (color != null) {
            g.setColor(color);
            if (round > 0) {
                g.roundRect(true, x + space, y + space, _w - (space * 2) - 1, _h - (space * 2) - 1, round, round);
            } else {
                g.rect(true, x + space, y + space, _w - (space * 2) - 1, _h - (space * 2) - 1);
            }
        }

    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return padL + space;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return padT + space;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return padR + space;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return padB + space;
    }
}
