/*
 * FilterString.java.java
 *
 * Created on 01-03-2010 01:32:11 PM
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
package colt.nicity.view.list;

import colt.nicity.view.adaptor.IKeyEventConstants;
import colt.nicity.view.border.NullBorder;
import colt.nicity.view.event.AKeyEvent;
import colt.nicity.view.event.KeyPressed;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.EditString;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.IBorder;

/**
 *
 * @author Administrator
 */
public class FilterString extends EditString {

    /**
     *
     */
    public FilterString() {
    }

    /**
     *
     * @param text
     */
    public FilterString(Object text) {
        this(text, null, ViewColor.cThemeFont, NullBorder.cNull);
    }

    /**
     *
     * @param text
     * @param _minW
     * @param _maxW
     */
    public FilterString(Object text, float _minW, float _maxW) {
        super(text, _minW, _maxW);
    }

    /**
     *
     * @param text
     * @param color
     */
    public FilterString(Object text, AColor color) {
        this(text, null, color, NullBorder.cNull);
    }

    /**
     *
     * @param text
     * @param font
     */
    public FilterString(Object text, AFont font) {
        this(text, font, ViewColor.cThemeFont, NullBorder.cNull);
    }

    /**
     *
     * @param text
     * @param font
     * @param fontColor
     */
    public FilterString(Object text, AFont font, AColor fontColor) {
        this(text, font, fontColor, NullBorder.cNull);
    }

    /**
     *
     * @param text
     * @param font
     * @param fontColor
     * @param _border
     */
    public FilterString(Object text, AFont font, AColor fontColor, IBorder _border) {
        super(text, font, fontColor, _border);
    }

    // IKeyEvents
    /**
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyPressed e) {
        int code = e.getKeyCode();
        if (code == IKeyEventConstants.cEscape) {
            at = 0;
            start = 0;
            end = 0;
            setText("");
        } else {
            super.keyPressed(e);
        }
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean modify(AKeyEvent e) {
        String f = toString();
        int code = e.getKeyCode();
        switch (code) {
            case (IKeyEventConstants.cAlt):
            case (IKeyEventConstants.cCtrl):
            case (IKeyEventConstants.cDelete):
            case (IKeyEventConstants.cUp):
            case (IKeyEventConstants.cDown):
            case (IKeyEventConstants.cRight):
            case (IKeyEventConstants.cLeft):
            case (IKeyEventConstants.cShift):
            case (0x9d):// mac apple
                return false;
            case (IKeyEventConstants.cBackspace):
                if (f.length() > 0) {
                    setText(f.substring(0, f.length() - 1));
                    return true;
                }
                return false;
            case (IKeyEventConstants.cEscape):
                setText("");
                return true;
            case (IKeyEventConstants.cExclamationMark):
            case (IKeyEventConstants.cBackSlash):// java dosen't provide '|' so we are using '\' to mean '|'
            //case (IKeyEventConstants.cSpace):
            //	filter.setText(f+OR);
            //delayedModify=true; // wait until next char after "or"
            //return;
            default: // break instead of return
                break;
        }
        char c = e.getKeyChar();
        if (Character.isDefined(c) || c == '"') {
            setText(f + String.valueOf(c));
        }
        return true;
    }
}
