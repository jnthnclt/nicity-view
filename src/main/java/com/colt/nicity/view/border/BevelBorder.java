/*
 * BevelBorder.java.java
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
public class BevelBorder extends AFlaggedBorder {

    private static float x = 2;
    private static float y = 2;
    private static float w = 2;
    private static float h = 2;

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

        AColor outerHighlight = ViewColor.cThemeShadow;
        AColor innerHighlight = ViewColor.cThemeShadow;
        AColor outerShadow = ViewColor.cTheme;
        AColor innerShadow = ViewColor.cTheme;
        if (is(cActive)) {
            outerHighlight = ViewColor.cThemeActive;
            innerHighlight = ViewColor.cThemeActive;
            outerShadow = ViewColor.cThemeActive;
            innerShadow = ViewColor.cThemeActive;
        }
        if (is(cSelected)) {
            outerHighlight = ViewColor.cThemeSelected;
            innerHighlight = ViewColor.cThemeSelected;
            outerShadow = ViewColor.cThemeSelected;
            innerShadow = ViewColor.cThemeSelected;
        }

        g.setColor(outerHighlight);// Highlight Outer
        g.line(x + 0, y + 0, x + w - 2, y + 0);
        g.line(x + 0, y + 0, x + 0, y + h - 2);
        g.line(x + 1, y + 1, x + 1, y + 1);

        g.setColor(innerHighlight);// Highlight Inner
        g.line(x + 2, y + 1, x + w - 2, y + 1);
        g.line(x + 1, y + 2, x + 1, y + h - 2);
        g.line(x + 2, y + 2, x + 2, y + 2);
        g.line(x + 0, y + h - 1, x + 0, y + h - 2);
        g.line(x + w - 1, y + 0, x + w - 1, y + 0);

        g.setColor(outerShadow);//Shadow Outer
        g.line(x + 2, y + h - 1, x + w - 1, y + h - 1);
        g.line(x + w - 1, y + 2, x + w - 1, y + h - 1);

        g.setColor(innerShadow);//Shadow Inner
        g.line(x + w - 2, y + h - 2, x + w - 2, y + h - 2);
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
        g.setColor(ViewColor.cTheme);
        g.rect(true, x, y, w, h);
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
