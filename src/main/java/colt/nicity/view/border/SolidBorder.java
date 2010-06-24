/*
 * SolidBorder.java.java
 *
 * Created on 03-12-2010 06:32:19 PM
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

import colt.nicity.core.value.IValue;
import colt.nicity.view.core.AColor;
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
public class SolidBorder implements IBorder, IActiveBorder, ISelectedBorder, IActiveSelectedBorder {

    private int size = 0;
    /**
     *
     */
    public Object backgroundColor = ViewColor.cTheme;

    /**
     *
     */
    public SolidBorder() {
    }

    /**
     *
     * @param _backgroundColor
     */
    public SolidBorder(Object _backgroundColor) {
        backgroundColor = _backgroundColor;
    }

    /**
     *
     * @param _backgroundColor
     * @param _pad
     */
    public SolidBorder(Object _backgroundColor, int _pad) {
        backgroundColor = _backgroundColor;
        size = _pad;
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
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
    public void paintBackground(ICanvas g, int x, int y, int w, int h) {
        if (backgroundColor instanceof AColor) {
            g.setColor(((AColor) backgroundColor));
            g.rect(true, x, y, w, h);
        } else if (backgroundColor instanceof IValue) {
            AColor color = (AColor) ((IValue) backgroundColor).getValue();
            g.setColor(color);
            g.rect(true, x, y, w, h);
        }

    }

    /**
     *
     * @return
     */
    public boolean isActive() {
        return false;
    }

    /**
     *
     * @return
     */
    public boolean isSelected() {
        return false;
    }

    /**
     *
     * @return
     */
    public float getX() {
        return size;
    }

    /**
     *
     * @return
     */
    public float getY() {
        return size;
    }

    /**
     *
     * @return
     */
    public float getW() {
        return size;
    }

    /**
     *
     * @return
     */
    public float getH() {
        return size;
    }

    /**
     *
     * @return
     */
    public IBorder getDefaultBorder() {
        return this;
    }

    /**
     *
     * @return
     */
    public IActiveBorder getActiveBorder() {
        return this;
    }

    /**
     *
     * @return
     */
    public ISelectedBorder getSelectedBorder() {
        return this;
    }

    /**
     *
     * @return
     */
    public IActiveSelectedBorder getActiveSelectedBorder() {
        return this;
    }
}
