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
package com.colt.nicity.view.list;

import com.colt.nicity.view.border.NullBorder;
import com.colt.nicity.view.event.AKeyEvent;
import com.colt.nicity.view.event.KeyPressed;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.AFont;
import com.colt.nicity.view.core.EditString;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.interfaces.IBorder;
import java.awt.event.KeyEvent;

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
    public void keyPressed(KeyPressed e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ESCAPE) {
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
            case (KeyEvent.VK_ALT):
            case (KeyEvent.VK_CONTROL):
            case (KeyEvent.VK_DELETE):
            case (KeyEvent.VK_UP):
            case (KeyEvent.VK_DOWN):
            case (KeyEvent.VK_RIGHT):
            case (KeyEvent.VK_LEFT):
            case (KeyEvent.VK_SHIFT):
            case (0x9d):// mac apple
                return false;
            case (KeyEvent.VK_BACK_SPACE):
                if (f.length() > 0) {
                    setText(f.substring(0, f.length() - 1));
                    return true;
                }
                return false;
            case (KeyEvent.VK_ESCAPE):
                setText("");
                return true;
            case (KeyEvent.VK_EXCLAMATION_MARK):
            case (KeyEvent.VK_BACK_SLASH):// java dosen't provide '|' so we are using '\' to mean '|'
            //case (KeyEvent.VK_SPACE):
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
