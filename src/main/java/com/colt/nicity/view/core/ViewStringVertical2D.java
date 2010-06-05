/*
 * ViewStringVertical2D.java.java
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
package com.colt.nicity.view.core;

import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class ViewStringVertical2D extends ViewString {

    /**
     *
     * @param text
     */
    public ViewStringVertical2D(Object text) {
        this(text, null, ViewColor.cThemeFont);
    }

    /**
     *
     * @param text
     * @param color
     */
    public ViewStringVertical2D(Object text, AColor color) {
        this(text, null, color);
    }

    /**
     *
     * @param text
     * @param font
     */
    public ViewStringVertical2D(Object text, AFont font) {
        this(text, font, ViewColor.cThemeFont);
    }

    /**
     *
     * @param text
     * @param font
     * @param fontColor
     */
    public ViewStringVertical2D(Object text, AFont font, AColor fontColor) {
        if (font == null) {
            setFont(UV.fonts[UV.cText]);
        } else {
            setFont(font);
        }
        setText(text);
        setColor(fontColor);
        font = getFont();
        w = font.getH(text.toString());//font.getFont().getSize();
        h = font.getW(text.toString());//fm.stringWidth(text.toString());
    }

    @Override
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        g.translate(0, (int) h);//??
        g.rotate(Math.toRadians(-90));
        g.setColor(fontColor);
        g.setFont(font);
        g.drawString(getText(), 0, font.getSize());
        g.rotate(Math.toRadians(90));
        g.translate(0, -(int) h);//??
    }
}
