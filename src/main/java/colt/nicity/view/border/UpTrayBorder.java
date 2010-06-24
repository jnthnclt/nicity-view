/*
 * UpTrayBorder.java.java
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
package colt.nicity.view.border;

import colt.nicity.view.core.AColor;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class UpTrayBorder extends AFlaggedBorder {

    private static float x = 1;
    private static float y = 1;
    private static float w = 1;
    private static float h = 1;
    AColor color = ViewColor.cTheme;

    /**
     *
     */
    public UpTrayBorder() {
    }

    /**
     *
     * @param _color
     */
    public UpTrayBorder(AColor _color) {
        color = _color;
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
        AColor _color = color;
        if (is(cActive)) {
            _color = ViewColor.cThemeActive;
        }
        if (is(cSelected)) {
            _color = ViewColor.cThemeSelected;
        }

        int r = 2;

        g.setColor(_color.darken(0.1f));
        g.rect(true, x + 0, y + 0, _w, _h + r);

        g.setColor(_color);
        g.roundRect(true, x + 1, y + 1, _w - 2, _h + r, r, r);


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
