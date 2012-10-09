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
import colt.nicity.view.adaptor.IEventConstants;
import colt.nicity.view.adaptor.IKeyEventConstants;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IKeyEvents;

/**
 *
 * @author Administrator
 */
public class PrimativeEvent extends ASetObject {

    private long who = 0;
    public Object source = null;
    public int family = 0, id = 0;
    public int modifiers = 0;
    public int clickCount = 0;
    public float x = 0, y = 0, z = 0;
    public int scrollType = 0, scrollAmount = 0, wheelRotation = 0;
    public double presure = 1, tiltAngle = 0, tiltDirection = 0;
    public int keyCode = 0;
    public char keyChar = 0;
    public PrimativeEvent() {
    }
    public PrimativeEvent(long _who) {
        who = _who;
    }

    public long who() {
        return who;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(ADK.family[family] + " ");
        s.append(ADK.familyID[family][id] + " ");

        if ((modifiers & IEventConstants.cShiftMask) != 0) {
            s.append("SHIFT ");
        }
        if ((modifiers & IEventConstants.cCrtlMask) != 0) {
            s.append("CTRL ");
        }
        if ((modifiers & IEventConstants.cMetaMask) != 0) {
            s.append("META ");
        }
        if ((modifiers & IEventConstants.cAltMask) != 0) {
            s.append("ALT ");
        }

        if ((modifiers & IEventConstants.cButton1Mask) == IEventConstants.cButton1Mask) {
            s.append("MB1 ");
        }
        if ((modifiers & IEventConstants.cButton2Mask) == IEventConstants.cButton2Mask) {
            s.append("MB2 ");
        }
        if ((modifiers & IEventConstants.cButton3Mask) == IEventConstants.cButton3Mask) {
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
            case IKeyEventConstants.cShift:
            case IKeyEventConstants.cCtrl:
            case IKeyEventConstants.cAlt:
            case IKeyEventConstants.cMeta:
            case IKeyEventConstants.cAltGraph:
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
            case IKeyEventConstants.cHome:
            case IKeyEventConstants.cEnd:
            case IKeyEventConstants.cPageDown:
            case IKeyEventConstants.cPageUp:
            case IKeyEventConstants.cUp:
            case IKeyEventConstants.cDown:
            case IKeyEventConstants.cLeft:
            case IKeyEventConstants.cRight:
            case IKeyEventConstants.cBegin:

            case IKeyEventConstants.cF1:
            case IKeyEventConstants.cF2:
            case IKeyEventConstants.cF3:
            case IKeyEventConstants.cF4:
            case IKeyEventConstants.cF5:
            case IKeyEventConstants.cF6:
            case IKeyEventConstants.cF7:
            case IKeyEventConstants.cF8:
            case IKeyEventConstants.cF9:
            case IKeyEventConstants.cF10:
            case IKeyEventConstants.cF11:
            case IKeyEventConstants.cF12:
            case IKeyEventConstants.cF13:
            case IKeyEventConstants.cF14:
            case IKeyEventConstants.cF15:
            case IKeyEventConstants.cF16:
            case IKeyEventConstants.cF17:
            case IKeyEventConstants.cF18:
            case IKeyEventConstants.cF19:
            case IKeyEventConstants.cF20:
            case IKeyEventConstants.cF21:
            case IKeyEventConstants.cF22:
            case IKeyEventConstants.cF23:
            case IKeyEventConstants.cF24:
            case IKeyEventConstants.cPrintScreen:
            case IKeyEventConstants.cScrollLock:
            case IKeyEventConstants.cCapsLock:
            case IKeyEventConstants.cNumLock:
            case IKeyEventConstants.cPause:
            case IKeyEventConstants.cInsert:

//            case IKeyEventConstants.cFINAL:
//            case IKeyEventConstants.cCONVERT:
//            case IKeyEventConstants.cNONCONVERT:
//            case IKeyEventConstants.cACCEPT:
//            case IKeyEventConstants.cMODECHANGE:
//            case IKeyEventConstants.cKANA:
//            case IKeyEventConstants.cKANJI:
            case IKeyEventConstants.cAlphaNumeric:

//            case IKeyEventConstants.cKATAKANA:
//            case IKeyEventConstants.cHIRAGANA:
//            case IKeyEventConstants.cFULL_WIDTH:
//            case IKeyEventConstants.cHALF_WIDTH:
//            case IKeyEventConstants.cROMAN_CHARACTERS:
//            case IKeyEventConstants.cALL_CANDIDATES:
//            case IKeyEventConstants.cPREVIOUS_CANDIDATE:
//            case IKeyEventConstants.cCODE_INPUT:
//            case IKeyEventConstants.cJAPANESE_KATAKANA:
//            case IKeyEventConstants.cJAPANESE_HIRAGANA:
//            case IKeyEventConstants.cJAPANESE_ROMAN:
//            case IKeyEventConstants.cKANA_LOCK:
//            case IKeyEventConstants.cINPUT_METHOD_ON_OFF:

            case IKeyEventConstants.cAgain:
            case IKeyEventConstants.cUndo:
            case IKeyEventConstants.cCopy:
            case IKeyEventConstants.cPaste:
            case IKeyEventConstants.cCut:
            case IKeyEventConstants.cFind:
            case IKeyEventConstants.cProps:
            case IKeyEventConstants.cStop:

            case IKeyEventConstants.cHelp:
            case IKeyEventConstants.cWindows:
            case IKeyEventConstants.cContextMenu:
                return true;
        }
        return false;
    }
}
