/*
 * GlassBorder.java.java
 *
 * Created on 01-03-2010 01:31:33 PM
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
public class GlassBorder extends AFlaggedBorder {

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
        AColor color = AColor.lightGray;
        if (is(cActive)) {
            if (is(cSelected)) {
                color = AColor.cyan;
            } else {
                color = AColor.orange;
            }
        } else if (is(cSelected)) {
            color = AColor.cyan;
        }

        w -= 1;
        h -= 1;

        g.setAlpha(0.1f, 0);
        g.setColor(color);
        g.roundRect(true, x, y, w, h, 5, 5);
        g.setAlpha(1f, 0);

        g.setColor(ViewColor.cButtonFont);
        g.roundRect(false, x, y, w, h, 5, 5);

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
