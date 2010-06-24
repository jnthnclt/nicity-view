/*
 * DownTrayBorder.java.java
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
package colt.nicity.view.border;

import colt.nicity.view.core.AColor;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class DownTrayBorder extends AFlaggedBorder {

    private static float x = 1;
    private static float y = 1;
    private static float w = 1;
    private static float h = 1;

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
        AColor color = ViewColor.cThemeFont;
        if (is(cActive)) {
            color = ViewColor.cThemeActive;
        }
        if (is(cSelected)) {
            color = ViewColor.cThemeSelected;
        }


        int r = 3;

        g.setColor(ViewColor.cThemeShadow);
        g.rect(true, x, y - r, _w, _h - 1 + r);

        g.setColor(color);
        g.roundRect(true, x + 1, y + 1, _w - 2, _h - 2, r, r);


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
