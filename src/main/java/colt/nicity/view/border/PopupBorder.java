/*
 * PopupBorder.java.java
 *
 * Created on 03-12-2010 06:31:57 PM
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
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class PopupBorder extends AFlaggedBorder {

    private Object color = ViewColor.cTheme;
    private int size = 2;

    /**
     *
     */
    public PopupBorder() {
        this(ViewColor.cTheme);
    }

    /**
     *
     * @param _color
     */
    public PopupBorder(Object _color) {
        this(_color, 2);
    }

    /**
     *
     * @param _size
     */
    public PopupBorder(int _size) {
        this(ViewColor.cTheme, _size);
    }

    /**
     *
     * @param _color
     * @param _size
     */
    public PopupBorder(Object _color, int _size) {
        color = _color;
        size = _size;
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
        if (color == null) {
            return;
        }
        AColor use = null;
        if (color instanceof AColor) {
            use = (AColor) color;
        } else if (color instanceof IValue) {
            use = (AColor) ((IValue) color).getValue();
        } else {
            use = ViewColor.cTheme;
        }

        RoundButtonBorder.paint(
                g, x, y, _w, _h, 5, 5,
                use);

    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return size;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return size;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return size;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return size;
    }
}
