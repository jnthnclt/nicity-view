/*
 * SunkenBorder.java.java
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
public class SunkenBorder extends AFlaggedBorder {

    private int maxRound = 100;
    private int gen = 4;
    private int pad = 4;
    private int inset = 2;
    private AColor fillColor = ViewColor.cThemeFont;
    private AColor fromColor = ViewColor.cThemeFont;
    private AColor toColor = ViewColor.cTheme;
    private float pow = 1;

    /**
     *
     * @param _from
     * @param _to
     * @param _fill
     * @param _pad
     * @param _gen
     * @param _maxRound
     * @param _pow
     */
    public SunkenBorder(AColor _from, AColor _to, AColor _fill, int _pad, int _gen, int _maxRound, float _pow) {
        fromColor = _from;
        fillColor = _fill;
        toColor = _to;
        pad = _pad;
        gen = _gen;
        maxRound = _maxRound;
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
        AColor color = fromColor;
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

        g.setColor(fillColor);
        g.roundRect(true, x, y, _w, _h, r, r);

        for (int i = 0; i < gen; i++) {
            int xi = (int) (i / 2);
            int yi = i;
            float p = (float) (i + 1) / (float) gen;
            if (p < 0.5f) {
                p *= 2f;
                p = (float) Math.pow(p, pow);
                AColor pc = color.interpolateShortestDistanceTo(toColor, p);
                g.setColor(pc);
                g.roundRect(false, x + xi, y + yi, _w - (xi * 2), _h - (yi * 2), r - xi, r - yi);
            } else {
                p -= 0.5f;
                p *= 2f;
                p = (float) Math.pow(p, pow);
                AColor pc = toColor.interpolateShortestDistanceTo(fillColor, p);
                g.setColor(pc);
                g.roundRect(false, x + xi, y + yi, _w - (xi * 2), _h - (yi * 2), r - xi, r - yi);


            }
        }

        for (int i = 0; i < gen; i++) {
            int xi = (int) (i / 2);
            int yi = i;
            float p = (float) (i + 1) / (float) gen;
            if (p < 0.5f) {
                p *= 2f;
                p = (float) Math.pow(p, pow);
                AColor pc = color.interpolateShortestDistanceTo(toColor, p);
                g.setColor(pc);
                g.roundRect(false, x + xi, y + gen + 1, _w - (xi * 2), _h - (yi * 2) - gen - 1, r - xi, r - yi);
            } else {
                p -= 0.5f;
                p *= 2f;
                p = (float) Math.pow(p, pow);
                AColor pc = toColor.interpolateShortestDistanceTo(fillColor, p);
                g.setColor(pc);
                g.roundRect(false, x + xi, y + gen + 1, _w - (xi * 2), _h - (yi * 2) - gen - 1, r - xi, r - yi);


            }
        }

    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return pad + inset;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return pad + inset;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return pad + inset;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return pad + inset;
    }
}
