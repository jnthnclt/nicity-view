/*
 * ZoneBorder.java.java
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
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Jonathan Colt
 * Apr 1, 2008
 */
public class ZoneBorder extends AFlaggedBorder {

    private AColor buttonColor = ViewColor.cTheme;
    private Object name;
    int pad = 2;

    /**
     *
     * @param _name
     * @param _color
     * @param _pad
     */
    public ZoneBorder(Object _name, AColor _color, int _pad) {
        name = _name;
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

        String s = "";
        if (name != null) {
            s = name.toString();
        }
        int sw = 0;
        if (s.length() > 0) {
            g.setFont(UV.fonts[UV.cSmall]);
            g.setColor(ViewColor.cThemeFont);
            g.drawString(s, pad + 2, (pad * 2) - 4);
            sw = (int) UV.fonts[UV.cSmall].getW(s);
            sw += 4;
        }
        if (sw > w) {
            sw = w;
        }


        g.setColor(color.darken(0.15f));
        g.line(x + pad + 0, y + pad + pad + 0, x + pad + 0, y + h - (pad + 1));// Vertical Left
        g.line(x + sw + pad + 1, y + pad + 0, x + w - (pad + 1), y + pad + 0);// Horzontal Top
        g.line(x + pad + 2, y + pad + pad + 0, x + sw + pad + 2, y + pad + pad + 0);// Horizontal Top

        g.setColor(color.lighten(0.1f));
        g.line(x + pad + 1, y + pad + pad + 1, x + pad + 1, y + h - (pad + 2));// Vertical Left
        g.line(x + sw + pad + 2, y + pad + 1, x + w - (pad + 2), y + pad + 1);// Horizontal Top
        g.line(x + pad + 2, y + pad + pad + 1, x + sw + pad + 2, y + pad + pad + 1);// Horizontal Top


        g.setColor(color.darken(0.15f));
        g.line(x + pad + 1, y + h - (pad + 1), x + w - (pad + 1), y + h - (pad + 1));// bottom
        g.line(x + w - (pad + 1), y + pad + 1, x + w - (pad + 1), y + h - (pad + 2));// right


        g.setColor(color.lighten(0.1f));
        g.line(x + pad + 2, y + h - (pad + 2), x + w - (pad + 2), y + h - (pad + 2)); // bottom
        g.line(x + w - (pad + 2), y + pad + 2, x + w - (pad + 2), y + h - (pad + 3)); // right


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
        String s = "";
        if (name != null) {
            s = name.toString();
        }
        int sw = (int) UV.fonts[UV.cSmall].getW(s);
        sw += 4;
        if (sw > w) {
            sw = w;
        }

        AColor color = buttonColor;
        g.setColor(color);
        g.rect(true, x + pad, y + pad + pad, w - (pad * 2), h - (pad * 3));
        g.rect(true, x + pad + sw, y + pad, w - (sw + (pad * 2)), pad + pad);

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
