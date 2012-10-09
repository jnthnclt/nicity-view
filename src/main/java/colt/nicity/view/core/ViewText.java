/*
 * ViewText.java.java
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
package colt.nicity.view.core;

import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class ViewText extends AViewableWH {

    /**
     *
     */
    protected AFont font = UV.fonts[UV.cText];
    /**
     *
     */
    protected String[] text;
    /**
     *
     */
    protected AColor fontColor = ViewColor.cThemeFont;

    /**
     *
     */
    public ViewText() {
        super();
    }

    /**
     *
     * @param _text
     */
    public ViewText(String[] _text) {
        this(_text, null, ViewColor.cThemeFont);
    }

    /**
     *
     * @param _text
     * @param color
     */
    public ViewText(String[] _text, AColor color) {
        this(_text, null, color);
    }

    /**
     *
     * @param _text
     * @param font
     */
    public ViewText(String[] _text, AFont font) {
        this(_text, font, ViewColor.cThemeFont);
    }

    /**
     *
     * @param _text
     * @param font
     * @param fontColor
     */
    public ViewText(String[] _text, AFont font, AColor fontColor) {
        super();
        if (font == null) {
            setFont(UV.fonts[UV.cText]);
        } else {
            setFont(font);
        }
        setText(_text);
        setColor(fontColor);
        font = getFont();
    }

    /**
     *
     * @return
     */
    public String[] getText() {
        return text;
    }

    /**
     *
     * @param _text
     */
    public void setText(String[] _text) {
        if (_text == null || _text.length == 0) {
            //_text = new String[0];
            _text = new String[]{""};//!!11/9/2009 hack
        }
        text = _text;
        for (int i = 0; i < text.length; i++) {
            String line = text[i];
            if (line == null) {
                continue;
            }
            line = line.replace('\n', ' ');
            line = line.replace('\r', ' ');
            line = line.replace('\t', ' ');
            //line = line.trim();
            text[i] = line;
            w = Math.max(w, (float)font.getW( line));
        }
        h = (float) (((font.getSize()) * text.length) + font.getDescent());
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
     * @param font
     */
    public void setFont(AFont font) {
        this.font = font;
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
        g.setFont(this.font);
        int size = font.getSize();
        int start = (_layer.ty > 0) ? 0 : (int) (Math.abs(_layer.ty) / size);
        int end = (int) ((start) + (_layer.h() / size) + 1);
        if (end > text.length) {
            end = text.length;
        }
        int y = (start + 1) * size;
        for (int i = start; i < end; i++, y += size) {
            if (text[i] == null) {
                continue;
            }
            g.drawString(text[i], 0, y);
        }
    }

    @Override
    public String toString() {
        String toString = "";
        for (int i = 0; i < text.length; i++) {
            if (text[i] == null) {
                continue;
            }
            toString += text[i] + "\n";
        }
        return toString;
    }
}
