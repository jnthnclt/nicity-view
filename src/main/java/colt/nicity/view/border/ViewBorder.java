/*
 * ViewBorder.java.java
 *
 * Created on 01-03-2010 01:31:39 PM
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
public class ViewBorder implements IBorder {

    private static float x = 0;
    private static float y = 0;
    private static float w = 0;
    private static float h = 0;

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
     * @param w
     * @param h
     */
    @Override
    public void paintBackground(ICanvas g, int x, int y, int w, int h) {
        g.setColor(ViewColor.cItemTheme);
        g.rect(true, x, y, w, h);
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
        return new ViewBorder();
    }

    /**
     *
     * @return
     */
    @Override
    public IActiveBorder getActiveBorder() {
        return new ActiveViewBorder();
    }

    /**
     *
     * @return
     */
    @Override
    public ISelectedBorder getSelectedBorder() {
        return new SelectedViewBorder();
    }

    /**
     *
     * @return
     */
    @Override
    public IActiveSelectedBorder getActiveSelectedBorder() {
        return new ActiveSelectedViewBorder();
    }
}
