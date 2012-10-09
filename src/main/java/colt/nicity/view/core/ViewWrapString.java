/*
 * ViewWrapString.java.java
 *
 * Created on 03-12-2010 06:34:55 PM
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

import colt.nicity.core.lang.UString;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.value.IValue;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class ViewWrapString extends AViewableWH implements IValue {
    /**
     *
     */
    protected Object text = "";
    /**
     *
     */
    protected AFont font;
    /**
     *
     */
    protected AColor fontColor;
    /**
     *
     */
    protected int wrapAfter = 32;
    /**
     *
     * @param _wrapAfter
     */
    public ViewWrapString(int _wrapAfter) {
        this(null, null, null, _wrapAfter);
    }
    /**
     *
     * @param _text
     * @param _wrapAfter
     */
    public ViewWrapString(Object _text, int _wrapAfter) {
        this(_text, null, null, _wrapAfter);
    }
    /**
     *
     * @param _text
     * @param _color
     * @param _wrapAfter
     */
    public ViewWrapString(Object _text, AColor _color, int _wrapAfter) {
        this(_text, null, _color, _wrapAfter);
    }
    /**
     *
     * @param _text
     * @param _font
     * @param _wrapAfter
     */
    public ViewWrapString(Object _text, AFont _font, int _wrapAfter) {
        this(_text, _font, null, _wrapAfter);
    }
    /**
     *
     * @param _text
     * @param _font
     * @param _color
     * @param _wrapAfter
     */
    public ViewWrapString(Object _text, AFont _font, AColor _color, int _wrapAfter) {
        super();
        wrapAfter = _wrapAfter;
        setColor(_color);
        if (_text == null) {
            _text = "";
        }
        text = _text;
        if (_font == null) {
            _font = UV.fonts[UV.cText];
        }
        font = _font;
        calcSize();
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
        calcSize();
    }
    
    /**
     *
     * @return
     */
    public String[] calcSize() {
        String s = text.toString();
        String[] wraps = UString.wrap(s, wrapAfter);
        w = 0;
        for (int i = 0; i < wraps.length; i++) {
            w = (float) Math.max(font.getW(wraps[i]), w);
        }
        h = (float) (font.getAscent() + font.getDescent()) * wraps.length;
        return wraps;
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
        calcSize();
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
        g.setColor(fontColor);
        g.setFont(font);
        String[] wraps = calcSize();
        int y = font.getSize();
        for (int i = 0; i < wraps.length; i++) {
            g.drawString(wraps[i], 0, y);
            y += font.getSize();
        }
    }
    
    @Override
    public String toString() {
        return text.toString();
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
