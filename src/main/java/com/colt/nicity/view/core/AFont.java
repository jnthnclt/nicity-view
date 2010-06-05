/*
 * AFont.java.java
 *
 * Created on 01-03-2010 01:29:41 PM
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
import com.colt.nicity.core.memory.struct.XY_I;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 *
 * @author Administrator
 */
public class AFont {

    /**
     *
     */
    public static int cPlain = Font.PLAIN;
    /**
     *
     */
    public static int cBold = Font.BOLD;
    /**
     *
     */
    public static int cItalic = Font.ITALIC;
    /**
     *
     */
    protected final static Component component = new Component() {
    };
    /**
     *
     */
    protected Font cacheFont;

    /**
     *
     */
    public AFont() {
    }

    /**
     *
     * @param _font
     */
    public AFont(Font _font) {
        cacheFont = _font;
    }

    /**
     *
     * @param _name
     * @param _style
     * @param _size
     */
    public AFont(String _name, int _style, int _size) {
        setFont(new Font(_name, _style, _size));
    }

    /**
     *
     * @param _style
     * @param _size
     */
    public AFont(int _style, int _size) {
        setFont(new Font(UV.cDefaultFontName, _style, _size));
    }

    /**
     *
     * @return
     */
    public int getSize() {
        Font f = getFont();
        return f.getSize();
    }

    /**
     *
     * @return
     */
    public int getStyle() {
        Font f = getFont();
        return f.getStyle();
    }

    /**
     *
     * @return
     */
    public String getFontName() {
        Font f = getFont();
        return f.getFontName();
    }

    /**
     *
     * @param _font
     */
    public void setFont(Font _font) {
        cacheFont = _font;
    }

    /**
     *
     * @return
     */
    public Font getFont() {
        if (cacheFont != null) {
            return cacheFont;
        }
        cacheFont = new Font(UV.cDefaultFontName, Font.PLAIN, 12);
        return cacheFont;
    }

    /**
     *
     * @param _string
     * @return
     */
    public float getW(String _string) {
        Font f = getFont();
        FontMetrics fm = component.getFontMetrics(f);
        return (float) fm.stringWidth(_string);
    }

    /**
     *
     * @param _string
     * @return
     */
    public float getH(String _string) {
        Font f = getFont();
        FontMetrics fm = component.getFontMetrics(f);
        return (float) (fm.getHeight());
    }

    /**
     *
     * @param _string
     * @return
     */
    public float getDescent(String _string) {
        Font f = getFont();
        FontMetrics fm = component.getFontMetrics(f);
        return (float) (fm.getDescent());
    }

    /**
     *
     * @param _string
     * @param _x
     * @param _y
     * @param _place
     * @return
     */
    public XY_I place(String _string, float _x, float _y, Place _place) {
        _x -= (int) (_place.getChildX() * getW(_string));
        _y += (int) (_place.getChildY() * getH(_string));
        return new XY_I((int) _x, (int) _y);
    }

    /**
     *
     * @param _string
     * @param _x
     * @param _y
     * @param _place
     * @return
     */
    public XYWH_I rect(String _string, float _x, float _y, Place _place) {
        int _w = (int) getW(_string);
        int _h = (int) getH(_string);
        _x -= (int) (_place.getChildX() * _w);
        _y -= (int) (_place.getChildY() * _h);
        return new XYWH_I((int) _x, (int) _y, (int) _w, (int) _h);
    }

   
}
