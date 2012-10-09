/*
 * ViewTextBox.java.java
 *
 * Created on 01-03-2010 01:31:34 PM
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
public class ViewTextBox extends AViewableWH {
    
    /**
     *
     */
    protected String text;
    /**
     *
     */
    protected AFont font = UV.fonts[UV.cText];
    /**
     *
     */
    protected AColor fontColor;
    /**
     *
     */
    protected int colums = 80;
    /**
     *
     */
    public ViewTextBox() {
        super();
    }
    /**
     *
     * @param _text
     * @param _colums
     */
    public ViewTextBox(String _text, int _colums) {
        this(_text, null, ViewColor.cThemeFont, _colums);
    }
    /**
     *
     * @param _text
     * @param color
     * @param _colums
     */
    public ViewTextBox(String _text, AColor color, int _colums) {
        this(_text, null, color, _colums);
    }
    /**
     *
     * @param _text
     * @param font
     * @param _colums
     */
    public ViewTextBox(String _text, AFont font, int _colums) {
        this(_text, font, ViewColor.cThemeFont, _colums);
    }
    /**
     *
     * @param _text
     * @param font
     * @param fontColor
     * @param _colums
     */
    public ViewTextBox(String _text, AFont font, AColor fontColor, int _colums) {
        colums = _colums;
        setFont(font);
        setColor(fontColor);
        setText(_text);
    }
    /**
     *
     * @return
     */
    public String getText() {
        return text;
    }
    /**
     *
     * @param _text
     */
    public void setText(String _text) {
        if (_text == null) {
            _text = "";
        }
        text = _text;
        char[] chars = text.toCharArray();
        int rowCount = 0;
        for (int c = 0; c < chars.length;) {
            int lastWhitespace = c;
            for (int i = 0; i < colums && c < chars.length; i++) {
                if (Character.isWhitespace(chars[c])) {
                    lastWhitespace = c;
                }
                int type = Character.getType(chars[c]);
                if (type == Character.CONTROL ||
                        type == Character.PARAGRAPH_SEPARATOR ||
                        type == Character.LINE_SEPARATOR) {
                    lastWhitespace = c;
                    if (chars[c] == '\t') {
                        i += 6;
                    } else {
                        break;
                    }
                }
                c++;
            }
            c = lastWhitespace + 1;
            rowCount++;
        }
        w = (float) (font.getSize() * colums);
        h = (float) (font.getSize() * rowCount);
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
     * @param _fontColor
     */
    public void setColor(AColor _fontColor) {
        fontColor = _fontColor;
    }
    @Override
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        super.paintBody(g, _layer, mode, _painted);
        g.setColor(fontColor);
        g.setFont(font);
        int size = font.getSize();
        int start = (_layer.ty > 0) ? 0 : (int) (Math.abs(_layer.ty) / size);
        int end = (int) ((start) + ((_layer.h() / size)) + 1);


        int y = (start + 1) * size;


        char[] chars = text.toCharArray();
        char[] row = new char[colums];
        int rowCount = 0;
        for (int c = 0; c < chars.length;) {
            int lastWhitespace = c;
            int columStart = c;
            int i = 0;
            int l = 0;
            for (; i < colums && c < chars.length; i++, c++) {
                if (Character.isWhitespace(chars[c])) {
                    lastWhitespace = c;
                    l = i;
                }
                int type = Character.getType(chars[c]);
                if (type == Character.CONTROL ||
                        type == Character.PARAGRAPH_SEPARATOR ||
                        type == Character.LINE_SEPARATOR) {
                    lastWhitespace = c;
                    if (chars[c] == '\t') {
                        for (int t = 0; t < 4 && i < colums; t++, i++) {
                            row[i] = ' ';
                        }
                        i--;
                        l = i;
                    } else {
                        l = i;
                        break;
                    }
                } else {
                    row[i] = chars[c];
                }
            }
            c = lastWhitespace;
            rowCount++;
            if (rowCount > start && rowCount <= end) {
                g.drawString(new String(row, 0, i), 0, y);
                y += size;
            }
            c++;
        }
    }
    @Override
    public String toString() {
        return text;
    }
}
