/*
 * RecessBorder.java.java
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
public class RecessBorder extends AFlaggedBorder {

    private AColor buttonColor = ViewColor.cTheme;
    int pad = 2;

    /**
     *
     */
    public RecessBorder() {
        this(ViewColor.cTheme);
    }

    /**
     *
     * @param _color
     */
    public RecessBorder(AColor _color) {
        buttonColor = _color;
    }

    /**
     *
     * @param _color
     * @param _pad
     */
    public RecessBorder(AColor _color, int _pad) {
        buttonColor = _color;
        pad = _pad;
    }

    /**
     *
     * @param _color
     */
    public void setColor(AColor _color) {
        buttonColor = _color;
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
        AColor color = buttonColor;
        w--;
        h--;

        g.setColor(color.darken(0.15f));
        g.line(x + 0, y + 0, x + 0, y + h - 1);
        g.line(x + 1, y + 0, x + w - 1, y + 0);

        g.setColor(color.darken(0.2f));
        g.line(x + 1, y + 1, x + 1, y + h - 2);
        g.line(x + 2, y + 1, x + w - 2, y + 1);

        g.setColor(color.darken(0.05f));
        g.line(x + 1, y + h - 1, x + w - 1, y + h - 1);
        g.line(x + w - 1, y + 1, x + w - 1, y + h - 2);

        g.setColor(color.darken(0.1f));
        g.line(x + 2, y + h - 2, x + w - 2, y + h - 2);
        g.line(x + w - 2, y + 2, x + w - 2, y + h - 3);

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
        AColor color = buttonColor;

        g.setColor(color);
        g.rect(true, x, y, w, h);

    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return pad;
    }
}
