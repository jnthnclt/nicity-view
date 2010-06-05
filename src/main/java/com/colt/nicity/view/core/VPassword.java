/*
 * VPassword.java.java
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
public class VPassword extends Viewer {

    /**
     *
     * @param _string
     */
    public void stringChanged(String _string) {
    }

    /**
     *
     * @param _string
     */
    public void stringSet(String _string) {
    }
    private EditString editString;

    /**
     *
     */
    public VPassword() {
        this(null, null, null);
    }

    /**
     *
     * @param _text
     */
    public VPassword(Object _text) {
        this(_text, null, null);
    }

    /**
     *
     * @param _text
     * @param _color
     */
    public VPassword(Object _text, AColor _color) {
        this(_text, null, _color);
    }

    /**
     *
     * @param _text
     * @param _font
     */
    public VPassword(Object _text, AFont _font) {
        this(_text, _font, null);
    }

    /**
     *
     * @param _text
     * @param _font
     * @param _color
     */
    public VPassword(Object _text, AFont _font, AColor _color) {
        editString = new EditString(_text, _font, _color) {

            @Override
            public void stringChanged(String _string) {
                super.stringChanged(_string);
                VPassword.this.stringChanged(_string);
            }

            @Override
            public void stringSet(String _string) {
                super.stringSet(_string);
                VPassword.this.stringSet(_string);
            }
        };
        editString.password = true;
        setPlacer(new Placer(editString));
        setBorder(new EditTextBorder());
    }

    /**
     *
     * @param _text
     */
    public VPassword(EditString _text) {
        editString = _text;
        editString.password = true;
        setPlacer(new Placer(editString));
        setBorder(new EditTextBorder());
    }

    /**
     *
     * @param _text
     * @param _minW
     * @param _maxW
     */
    public VPassword(Object _text, float _minW, float _maxW) {
        editString = new EditString(_text, _minW, _maxW);
        editString.password = true;
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
