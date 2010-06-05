/*
 * HorizonBorder.java.java
 *
 * Created on 01-03-2010 01:31:35 PM
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
public class HorizonBorder extends AFlaggedBorder {

    private int padding = 2;
    AColor sky = new AColor(40, 137, 204);
    AColor ground = new AColor(144, 106, 0);

    /**
     *
     */
    public HorizonBorder() {
    }

    /**
     *
     * @param _sky
     * @param _ground
     * @param _padding
     */
    public HorizonBorder(AColor _sky, AColor _ground, int _padding) {
        sky = _sky;
        ground = _ground;
        padding = _padding;
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
     * @param _g
     * @param x
     * @param y
     * @param _w
     * @param _h
     */
    @Override
    public void paintBackground(ICanvas _g, int x, int y, int _w, int _h) {

        boolean light = false;
        if (ViewColor.cTheme.sum() > ViewColor.cThemeFont.sum()) {
            light = true;
        }

        int ySize = 2;

        for (int i = 0; i < (_h / ySize); i++) {
            double scale = (double) i / (double) (_h / ySize);
            scale = Math.pow(scale, 1.5d);
            scale *= 2;
            scale -= 1;
            if (scale < 0) {
                if (light) {
                    _g.setColor(sky.desaturate(1 - (float) Math.abs(scale)));
                } else {
                    _g.setColor(sky.darken((float) Math.abs(scale)));
                }
            } else {
                if (light) {
                    _g.setColor(ground.desaturate((float) Math.abs(scale)));
                } else {
                    _g.setColor(ground.darken((float) Math.abs(1 - scale)));
                }
            }
            _g.rect(true, x, y, _w, ySize);
            y += ySize;
        }

    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return padding;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return padding;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return padding;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return padding;
    }
}
