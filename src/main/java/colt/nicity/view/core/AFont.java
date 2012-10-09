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
package colt.nicity.view.core;

import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.adaptor.IFont;
import colt.nicity.view.adaptor.IFontConstants;
import colt.nicity.view.adaptor.VS;

/**
 *
 * @author Administrator
 */
public class AFont {

    /**
     *
     */
    protected IFont cacheFont;

    /**
     *
     */
    public AFont() {
    }

    /**
     *
     * @param _font
     */
    public AFont(IFont _font) {
        cacheFont = _font;
    }

    /**
     *
     * @param _name
     * @param _style
     * @param _size
     */
    public AFont(String _name, int _style, int _size) {
        setFont(VS.getFont(_name, _style, _size));
    }

    /**
     *
     * @param _style
     * @param _size
     */
    public AFont(int _style, int _size) {
        setFont(VS.getFont(IFontConstants.cDefaultFontName, _style, _size));
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return getFont().getSize();
    }

    /**
     *
     * @return
     */
    public int getStyle() {
        return getFont().getStyle();
    }

    /**
     *
     * @return
     */
    public String getFontName() {
        return getFont().getFontName();
    }

    /**
     *
     * @param _font
     */
    public void setFont(IFont _font) {
        cacheFont = _font;
    }

    /**
     *
     * @return
     */
    public IFont getFont() {
        if (cacheFont != null) {
            return cacheFont;
        }
        cacheFont = VS.getSystemFont();
        return cacheFont;
    }

    /**
     *
     * @param _string
     * @return
     */
    public float getW(String _string) {
        return (float) getFont().stringWidth(_string);
    }

    /**
     *
     * @param _string
     * @return
     */
    public float getH(String _string) {
        return (float) getFont().stringHeight(_string);
    }

    /**
     *
     * @param _string
     * @return
     */
    public float getDescent() {
        return (float) getFont().descent();
    }

    public float getAscent() {
        return (float) getFont().ascent();
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

    float getCharW(char c) {
        return getFont().charWidth(c);
    }

    public Object getName() {
        return getFont().getFontName();
    }
}
