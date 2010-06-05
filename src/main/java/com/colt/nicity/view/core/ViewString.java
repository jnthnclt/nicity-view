/*
 * ViewString.java.java
 *
 * Created on 03-12-2010 06:34:51 PM
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
import com.colt.nicity.core.value.IValue;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IOrientable;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class ViewString extends AViewableWH implements IOrientable, IValue {

    /**
     *
     */
    protected Object text = "";
    /**
     *
     */
    protected AFont font;// = UV.fonts[UV.cText];
    /**
     *
     */
    protected AColor fontColor;// = ViewColor.cThemeFont;

    /**
     *
     */
    public ViewString() {
        this(null, null, null);
    }

    /**
     *
     * @param _text
     */
    public ViewString(Object _text) {
        this(_text, null, null);
    }

    /**
     *
     * @param _text
     * @param _color
     */
    public ViewString(Object _text, AColor _color) {
        this(_text, null, _color);
    }

    /**
     *
     * @param _text
     * @param _font
     */
    public ViewString(Object _text, AFont _font) {
        this(_text, _font, null);
    }

    /**
     *
     * @param _text
     * @param _font
     * @param _color
     */
    public ViewString(Object _text, AFont _font, AColor _color) {
        super();
        setColor(_color);
        setFont(_font);

        w = (float) UVA.stringWidth(font, text.toString());
        h = (float) (UVA.fontAscent(font) + UVA.fontDescent(font));
        setText(_text);

    }

    /**
     *
     * @return
     */
    public String getText() {
        return text.toString();
    }

    /**
     *
     * @param _text
     */
    public void setText(Object _text) {
        if (_text == null) {
            _text = "";
        }
        text = _text;
        w = (float) UVA.stringWidth(font, text.toString());
        if (text.toString().length() > 0) {
            h = (float) (UVA.fontAscent(font) + UVA.fontDescent(font));
        } else {
            h = 1;
        }
    }

    /**
     *
     * @return
     */
    public AFont getFont() {
        return font;
    }

    /**
     *
     * @param _font
     */
    public void setFont(AFont _font) {
        if (_font == null) {
            _font = UV.fonts[UV.cText];
        }
        font = _font;
        w = (float) UVA.stringWidth(font, text.toString());
        h = (float) (UVA.fontAscent(font) + UVA.fontDescent(font));
    }

    /**
     *
     * @return
     */
    public AColor getColor() {
        return fontColor;
    }

    /**
     *
     * @param fontColor
     */
    public void setColor(AColor fontColor) {
        if (fontColor == null) {
            fontColor = ViewColor.cThemeFont;
        }
        this.fontColor = fontColor;
    }

    // AViwable
    @Override
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        super.paintBody(g, _layer, mode, _painted);
        g.setFont(font);
        String t = text.toString();

        w = (float) UVA.stringWidth(font, t);
        if (t.length() > 0) {
            h = (float) (UVA.fontAscent(font) + UVA.fontDescent(font));
        } else {
            h = 1;
        }
        g.setColor(fontColor);
        g.drawString(t, 0, font.getSize());
    }

    @Override
    public String toString() {
        return text.toString();
    }

    // IOrientable
    public IView getVertical(IView _caller, float _align) {
        return new ViewStringVertical(getText(), getFont(), getColor());
    }

    public IView getHorizontal(IView _caller, float _align) {
        return this;
    }

    // IValue
    /**
     *
     * @return
     */
    public Object getValue() {
        return text;
    }

    /**
     *
     * @param _value
     */
    public void setValue(Object _value) {
        setText((_value == null) ? "" : _value.toString());
    }
}
