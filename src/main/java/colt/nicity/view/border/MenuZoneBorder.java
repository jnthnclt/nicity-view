/*
 * MenuZoneBorder.java.java
 *
 * Created on 01-03-2010 01:31:38 PM
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
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class MenuZoneBorder extends AFlaggedBorder {

    private AColor color = ViewColor.cTheme;
    private IView menu;
    int pad = 2;

    /**
     *
     * @param _menu
     * @param _color
     * @param _pad
     */
    public MenuZoneBorder(IView _menu, AColor _color, int _pad) {
        menu = _menu;
        color = _color;
        pad = _pad;
    }

    /**
     *
     * @param _color
     */
    public void setColor(AColor _color) {
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
        w--;
        h--;

        int mw = (int) menu.getW();
        if (mw > w) {
            mw = w;
        }


        g.setColor(color.darken(0.15f));
        g.line(x + pad + 0, y + pad + pad + 0, x + pad + 0, y + h - (pad + 1));// Vertical Left
        g.line(x + mw + pad + 1, y + pad + 0, x + w - (pad + 1), y + pad + 0);// Horzontal Top
        g.line(x + pad + 2, y + pad + pad + 0, x + mw + pad + 2, y + pad + pad + 0);// Horizontal Top

        g.setColor(color.darken(0.2f));
        g.line(x + pad + 1, y + pad + pad + 1, x + pad + 1, y + h - (pad + 2));// Vertical Left
        g.line(x + mw + pad + 2, y + pad + 1, x + w - (pad + 2), y + pad + 1);// Horizontal Top
        g.line(x + pad + 2, y + pad + pad + 1, x + mw + pad + 2, y + pad + pad + 1);// Horizontal Top

        g.setColor(color.darken(0.05f));
        g.line(x + pad + 1, y + h - (pad + 1), x + w - (pad + 1), y + h - (pad + 1));
        g.line(x + w - (pad + 1), y + pad + 1, x + w - (pad + 1), y + h - (pad + 2));

        g.setColor(color.darken(0.1f));
        g.line(x + pad + 2, y + h - (pad + 2), x + w - (pad + 2), y + h - (pad + 2));
        g.line(x + w - (pad + 2), y + pad + 2, x + w - (pad + 2), y + h - (pad + 3));

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
        int mw = (int) menu.getW();
        int mh = (int) menu.getH();
        mw += 4;
        if (mw > w) {
            mw = w;
        }

        g.setColor(color);
        g.rect(true, x + pad, y + pad + pad, w - (pad * 2), h - (pad * 3));
        g.rect(true, x + pad + mw, y + pad, w - (mw + (pad * 2)), pad + pad);

    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return pad * 2;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return 4 + (pad * 2);
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return pad * 2;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return pad * 2;
    }
}
