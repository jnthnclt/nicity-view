/*
 * PrimativeEvent.java.java
 *
 * Created on 01-03-2010 01:31:40 PM
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

import colt.nicity.view.event.ADK;
import colt.nicity.core.lang.ASetObject;
import colt.nicity.view.interfaces.ICanvas;
import java.awt.Event;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author Administrator
 */
public class PrimativeEvent extends ASetObject {

    private long who = 0;
    /**
     *
     */
    public Object source = null;
    /**
     *
     */
    /**
     *
     */
    public int family = 0, id = 0;
    /**
     *
     */
    public int modifiers = 0;
    /**
     *
     */
    public int clickCount = 0;
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public float x = 0, y = 0, z = 0;
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public int scrollType = 0, scrollAmount = 0, wheelRotation = 0;
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public double presure = 1, tiltAngle = 0, tiltDirection = 0;
    /**
     *
     */
    public int keyCode = 0;
    /**
     *
     */
    public char keyChar = 0;

    /**
     *
     */
    public PrimativeEvent() {
    }

    /**
     *
     * @param _who
     */
    public PrimativeEvent(long _who) {
        who = _who;
    }

    /**
     *
     * @return
     */
    public long who() {
        return who;
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(ADK.family[family] + " ");
        s.append(ADK.familyID[family][id] + " ");

        if ((modifiers & Event.SHIFT_MASK) != 0) {
            s.append("SHIFT ");
        }
        if ((modifiers & Event.CTRL_MASK) != 0) {
            s.append("CTRL ");
        }
        if ((modifiers & Event.META_MASK) != 0) {
            s.append("META ");
        }
        if ((modifiers & Event.ALT_MASK) != 0) {
            s.append("ALT ");
        }

        if ((modifiers & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            s.append("MB1 ");
        }
        if ((modifiers & InputEvent.BUTTON2_MASK) == InputEvent.BUTTON2_MASK) {
            s.append("MB2 ");
        }
        if ((modifiers & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
            s.append("MB3 ");
        }

        if (clickCount > 0) {
            s.append("Click(" + clickCount + ") ");
        }
        s.append("Point(" + x + "," + y + "," + z + ") ");
        if (keyCode > 0) {
            s.append("Key(" + keyChar + "," + keyCode + ") ");
        }
        return s.toString();
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public void paint(ICanvas _g, int _x, int _y, int _w, int _h) {
        AFont font = UV.fonts[UV.cText];
        String ts = toString();
        int w = (int) font.getW(ts);
        int h = (int) font.getH(ts);
        _g.setFont(font);
        _g.drawString(ts, _x + ((_w / 2) - (w / 2)), _y + (_h / 2) + (h / 2));
    }

    /**
     *
     * @return
     */
    public Object hashObject() {
        return this;
    } //!! todo

    /**
     *
     * @param keyCode
     * @return
     */
    public static boolean isModifierKey(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_SHIFT:
            case KeyEvent.VK_CONTROL:
            case KeyEvent.VK_ALT:
            case KeyEvent.VK_META:
            case KeyEvent.VK_ALT_GRAPH:
                return true;
        }
        return false;
    }

    /**
     *
     * @param keyCode
     * @return
     */
    public static boolean isActionKey(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_HOME:
            case KeyEvent.VK_END:
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_BEGIN:

            case KeyEvent.VK_KP_LEFT:
            case KeyEvent.VK_KP_UP:
            case KeyEvent.VK_KP_RIGHT:
            case KeyEvent.VK_KP_DOWN:

            case KeyEvent.VK_F1:
            case KeyEvent.VK_F2:
            case KeyEvent.VK_F3:
            case KeyEvent.VK_F4:
            case KeyEvent.VK_F5:
            case KeyEvent.VK_F6:
            case KeyEvent.VK_F7:
            case KeyEvent.VK_F8:
            case KeyEvent.VK_F9:
            case KeyEvent.VK_F10:
            case KeyEvent.VK_F11:
            case KeyEvent.VK_F12:
            case KeyEvent.VK_F13:
            case KeyEvent.VK_F14:
            case KeyEvent.VK_F15:
            case KeyEvent.VK_F16:
            case KeyEvent.VK_F17:
            case KeyEvent.VK_F18:
            case KeyEvent.VK_F19:
            case KeyEvent.VK_F20:
            case KeyEvent.VK_F21:
            case KeyEvent.VK_F22:
            case KeyEvent.VK_F23:
            case KeyEvent.VK_F24:
            case KeyEvent.VK_PRINTSCREEN:
            case KeyEvent.VK_SCROLL_LOCK:
            case KeyEvent.VK_CAPS_LOCK:
            case KeyEvent.VK_NUM_LOCK:
            case KeyEvent.VK_PAUSE:
            case KeyEvent.VK_INSERT:

            case KeyEvent.VK_FINAL:
            case KeyEvent.VK_CONVERT:
            case KeyEvent.VK_NONCONVERT:
            case KeyEvent.VK_ACCEPT:
            case KeyEvent.VK_MODECHANGE:
            case KeyEvent.VK_KANA:
            case KeyEvent.VK_KANJI:
            case KeyEvent.VK_ALPHANUMERIC:

            case KeyEvent.VK_KATAKANA:
            case KeyEvent.VK_HIRAGANA:
            case KeyEvent.VK_FULL_WIDTH:
            case KeyEvent.VK_HALF_WIDTH:
            case KeyEvent.VK_ROMAN_CHARACTERS:
            case KeyEvent.VK_ALL_CANDIDATES:
            case KeyEvent.VK_PREVIOUS_CANDIDATE:
            case KeyEvent.VK_CODE_INPUT:
            case KeyEvent.VK_JAPANESE_KATAKANA:
            case KeyEvent.VK_JAPANESE_HIRAGANA:
            case KeyEvent.VK_JAPANESE_ROMAN:
            case KeyEvent.VK_KANA_LOCK:
            case KeyEvent.VK_INPUT_METHOD_ON_OFF:

            case KeyEvent.VK_AGAIN:
            case KeyEvent.VK_UNDO:
            case KeyEvent.VK_COPY:
            case KeyEvent.VK_PASTE:
            case KeyEvent.VK_CUT:
            case KeyEvent.VK_FIND:
            case KeyEvent.VK_PROPS:
            case KeyEvent.VK_STOP:

            case KeyEvent.VK_HELP:
            case KeyEvent.VK_WINDOWS:
            case KeyEvent.VK_CONTEXT_MENU:
                return true;
        }
        return false;
    }
}
