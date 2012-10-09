/*
 * ViewStringVertical.java.java
 *
 * Created on 01-03-2010 01:31:40 PM
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
package colt.nicity.view.core;

import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IOrientable;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class ViewStringVertical extends ViewString implements IOrientable {

    /**
     *
     * @param text
     */
    public ViewStringVertical(Object text) {
        this(text, null, ViewColor.cThemeFont);
    }

    /**
     *
     * @param text
     * @param color
     */
    public ViewStringVertical(Object text, AColor color) {
        this(text, null, color);
    }

    /**
     *
     * @param text
     * @param font
     */
    public ViewStringVertical(Object text, AFont font) {
        this(text, font, ViewColor.cThemeFont);
    }

    /**
     *
     * @param text
     * @param font
     * @param fontColor
     */
    public ViewStringVertical(Object text, AFont font, AColor fontColor) {
        if (font == null) {
            setFont(UV.fonts[UV.cText]);
        } else {
            setFont(font);
        }
        setText(text);
        setColor(fontColor);
        font = getFont();
        w = font.getH(text.toString());
        h = font.getSize() * (text.toString().length());
    }

    // ICell
    @Override
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        super.paintBody(g, _layer, mode, _painted);
        g.setColor(fontColor);
        g.setFont(font);
        char[] chars = text.toString().toCharArray();
        int yStep = font.getSize();
        int y = yStep;
        for (int i = 0; i < chars.length; i++) {
            float charW = font.getCharW(chars[i]);
            float x = (w / 2) - (charW / 2);
            g.drawString("" + chars[i], (int) x, y);
            y += yStep;
        }
    }

    // IOrientable
    @Override
    public IView getVertical(IView _caller, float _align) {
        return this;
    }

    @Override
    public IView getHorizontal(IView _caller, float _align) {
        return new ViewString(getText(), getFont(), getColor());
    }
}
