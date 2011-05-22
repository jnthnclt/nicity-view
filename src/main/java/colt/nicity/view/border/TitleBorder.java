/*
 * TitleBorder.java.java
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

import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.IActiveBorder;
import colt.nicity.view.interfaces.IActiveSelectedBorder;
import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.ISelectedBorder;

/**
 *
 * @author Administrator
 */
public class TitleBorder implements IBorder, IActiveBorder, ISelectedBorder, IActiveSelectedBorder {

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

        g.setColor(ViewColor.cTheme);
        g.rect(true, x + 0, y + 0, _w, _h);

        g.setColor(ViewColor.cTheme);
        g.line(x + 0, y + _h - 3, x + _w, y + _h - 3);

        g.setColor(ViewColor.cTheme);
        g.line(x + 0, y + _h - 2, x + _w, y + _h - 2);

        g.setColor(ViewColor.cTheme);
        g.line(x + 0, y + _h - 1, x + _w, y + _h - 1);

    }

    /**
     *
     * @return
     */
    @Override
    public boolean isActive() {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSelected() {
        return false;
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

    /**
     *
     * @return
     */
    @Override
    public IBorder getDefaultBorder() {
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public IActiveBorder getActiveBorder() {
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public ISelectedBorder getSelectedBorder() {
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public IActiveSelectedBorder getActiveSelectedBorder() {
        return this;
    }
}
