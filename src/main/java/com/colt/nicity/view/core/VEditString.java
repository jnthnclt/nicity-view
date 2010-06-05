/*
 * VEditString.java.java
 *
 * Created on 01-03-2010 01:29:42 PM
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

import com.colt.nicity.view.border.EditTextBorder;

/**
 *
 * @author Administrator
 */
public class VEditString extends Viewer {

    private EditString editString;

    /**
     *
     */
    public VEditString() {
        this(null, null, null);
    }

    /**
     *
     * @param _text
     */
    public VEditString(Object _text) {
        this(_text, null, null);
    }

    /**
     *
     * @param _text
     * @param _color
     */
    public VEditString(Object _text, AColor _color) {
        this(_text, null, _color);
    }

    /**
     *
     * @param _text
     * @param _font
     */
    public VEditString(Object _text, AFont _font) {
        this(_text, _font, null);
    }

    /**
     *
     * @param _text
     * @param _font
     * @param _color
     */
    public VEditString(Object _text, AFont _font, AColor _color) {
        editString = new EditString(_text, _font, _color);
        setPlacer(new Placer(editString));
        setBorder(new EditTextBorder());
    }

    /**
     *
     * @param _text
     */
    public VEditString(EditString _text) {
        editString = _text;
        setPlacer(new Placer(editString));
        setBorder(new EditTextBorder());
    }

    /**
     *
     * @param _text
     * @param _minW
     * @param _maxW
     */
    public VEditString(Object _text, float _minW, float _maxW) {
        editString = new EditString(_text, _minW, _maxW);
        setPlacer(new Placer(editString));
        setBorder(new EditTextBorder());
    }

    /**
     *
     * @param _text
     */
    public void setText(Object _text) {
        editString.setCaret(0);
        editString.setText(_text);
    }

    @Override
    public void mend() {
        enableFlag(UV.cRepair);//??
        super.mend();
    }

    /**
     *
     * @return
     */
    public EditString getEditString() {
        return editString;
    }

    @Override
    public String toString() {
        return editString.toString();
    }
}
