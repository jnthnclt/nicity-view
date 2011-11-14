/*
 * UAWT.java.java
 *
 * Created on 01-03-2010 01:30:21 PM
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
package colt.nicity.view.awt;

import colt.nicity.core.memory.struct.TRLB_I;
import colt.nicity.view.event.ADK;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.core.PrimativeEvent;
import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Administrator
 */
public class UAWT {

    /**
     *
     */
    public static float cManualScreenWidth = 0.0f;
    /**
     *
     */
    public static float cManualScreenHeight = 0.0f;
    private static float screenWidth = 0.0f;
    private static float screenHeight = 0.0f;
    private static Toolkit tk;

    /**
     *
     */
    public static void init() {
        if (tk != null) {
            return;
        }
        tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
    }

    /**
     *
     * @return
     */
    public static float getScreenWidth() {
        if (tk == null) {
            init();
        }
        if (cManualScreenWidth > 0.0f) {
            return cManualScreenWidth;
        }
        return screenWidth;
    }

    /**
     *
     * @return
     */
    public static float getScreenHeight() {
        if (tk == null) {
            init();
        }
        if (cManualScreenHeight > 0.0f) {
            return cManualScreenHeight;
        }
        return screenHeight;
    }
    /**
     *
     */
    public static Cursor cDefault = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    /**
     *
     */
    public static Cursor cCrossHair = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    /**
     *
     */
    public static Cursor cText = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
    /**
     *
     */
    public static Cursor cWait = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
    /**
     *
     */
    public static Cursor cSWResize = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
    /**
     *
     */
    public static Cursor cSEResize = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
    /**
     *
     */
    public static Cursor cNWResize = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
    /**
     *
     */
    public static Cursor cNEResize = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
    /**
     *
     */
    public static Cursor cNResize = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
    /**
     *
     */
    public static Cursor cSResize = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
    /**
     *
     */
    public static Cursor cWResize = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
    /**
     *
     */
    public static Cursor cEResize = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
    /**
     *
     */
    public static Cursor cHand = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    /**
     *
     */
    public static Cursor cMove = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    /**
     *
     */
    public static Cursor cMoveNoDrop = cDefault;
    /**
     *
     */
    public static Cursor cMoveDrop = cHand;
    /**
     *
     */
    public static final int cRight = KeyEvent.VK_RIGHT;
    /**
     *
     */
    public static final int cLeft = KeyEvent.VK_LEFT;
    /**
     *
     */
    public static final int cUp = KeyEvent.VK_UP;
    /**
     *
     */
    public static final int cDown = KeyEvent.VK_DOWN;

    /**
     *
     * @param event
     * @return
     */
    public static PrimativeEvent toPrimativeEvent(AWTEvent event) {

        PrimativeEvent pe = new PrimativeEvent();
        pe.source = event.getSource();
        if (event instanceof MouseEvent) {
            pe.family = ADK.cMouse;
            int id = event.getID();
            switch (id) {
                case MouseEvent.MOUSE_PRESSED:
                    pe.id = ADK.cMousePressed;
                    break;
                case MouseEvent.MOUSE_DRAGGED:
                    pe.id = ADK.cMouseDragged;
                    break;
                case MouseEvent.MOUSE_MOVED:
                    pe.id = ADK.cMouseMoved;
                    break;
                case MouseEvent.MOUSE_ENTERED:
                    pe.id = ADK.cMouseEntered;
                    break;
                case MouseEvent.MOUSE_EXITED:
                    pe.id = ADK.cMouseExited;
                    break;
                case MouseEvent.MOUSE_RELEASED:
                    pe.id = ADK.cMouseReleased;
                    break;
                case MouseEvent.MOUSE_CLICKED:
                    pe.id = ADK.cMouseClicked;
                    break;
                case MouseEvent.MOUSE_WHEEL:
                    pe.id = ADK.cMouseWheel;
                    MouseWheelEvent mw = (MouseWheelEvent) event;
                    pe.scrollType = mw.getScrollType();
                    pe.scrollAmount = mw.getScrollAmount();
                    pe.wheelRotation = mw.getWheelRotation();

                    break;

            }


            pe.modifiers = ((MouseEvent) event).getModifiers();
            pe.clickCount = ((MouseEvent) event).getClickCount();
            Point p = ((MouseEvent) event).getPoint();
            pe.x = p.x;
            pe.y = p.y;
            pe.z = 0;
        } else if (event instanceof KeyEvent) {
            pe.family = ADK.cKey;
            int id = event.getID();
            switch (id) {
                case KeyEvent.KEY_PRESSED:
                    pe.id = ADK.cKeyPressed;
                    break;
                case KeyEvent.KEY_RELEASED:
                    pe.id = ADK.cKeyReleased;
                    break;
                case KeyEvent.KEY_TYPED:
                    pe.id = ADK.cKeyTyped;
                    break;
            }

            pe.modifiers = ((KeyEvent) event).getModifiers();
            pe.keyCode = ((KeyEvent) event).getKeyCode();
            pe.keyChar = ((KeyEvent) event).getKeyChar();
        } else {
            pe.family = ADK.cComponent;
            int id = event.getID();
            if (event instanceof FocusEvent) {
                switch (id) {
                    case FocusEvent.FOCUS_GAINED:
                        pe.id = ADK.cFocusGained;
                        break;
                    case FocusEvent.FOCUS_LOST:
                        pe.id = ADK.cFocusLost;
                        break;
                }
            } else if (event instanceof WindowEvent) {
                switch (id) {
                    case WindowEvent.WINDOW_OPENED:
                        pe.id = ADK.cWindowOpened;
                        break;
                    case WindowEvent.WINDOW_CLOSING:
                        pe.id = ADK.cWindowClosing;
                        break;
                    case WindowEvent.WINDOW_ACTIVATED:
                        pe.id = ADK.cWindowActivated;
                        break;
                    case WindowEvent.WINDOW_DEACTIVATED:
                        pe.id = ADK.cWindowDeactivated;
                        break;
                    case WindowEvent.WINDOW_ICONIFIED:
                        pe.id = ADK.cWindowIconified;
                        break;
                    case WindowEvent.WINDOW_DEICONIFIED:
                        pe.id = ADK.cWindowDeiconified;
                        break;
                    case WindowEvent.WINDOW_LOST_FOCUS:
                        pe.id = ADK.cWindowLostFocus;
                        break;
                }
            }
            if (event instanceof ComponentEvent) {
                switch (id) {
                    case ComponentEvent.COMPONENT_RESIZED:
                        pe.id = ADK.cWindowResized;
                        break;
                }
            }

        }

        return pe;

    }

    /**
     *
     * @param _old
     * @param _w
     * @param _h
     * @return
     */
    static public Image ensureBuffer(Image _old, int _w, int _h) {
        if (_w == 0 || _h == 0) {
            Image newBuffer = newBuffer(1, 1);
            if (_old != null) {
                _old.flush();
            }
            return newBuffer;
        }
        if (_old == null) {
            Image newBuffer = newBuffer(_w, _h);
            return newBuffer;
        } else {
            if (_old.getWidth(null) != _w || _old.getHeight(null) != _h) {
                Image newBuffer = newBuffer(_w, _h);
                Graphics bg = newBuffer.getGraphics();
                bg.drawImage(_old, 0, 0, null);
                bg.dispose();
                _old.flush();
                return newBuffer;
            }
            return _old;
        }
    }

    static private Image newBuffer(int _w, int _h) {
        BufferedImage bi = new BufferedImage(_w, _h, BufferedImage.TYPE_INT_ARGB);
        return bi;
    }

    /**
     *
     * @param _insets
     * @param _g
     * @param _region
     * @param _buffer
     */
    static public void modifiedRegion(TRLB_I _insets, Graphics _g, XYWH_I _region, Image _buffer) {
        if (_insets == null) {
            return;
        }
        if (_g == null) {
            return;
        }
        if (_region != null && _region.x != Integer.MIN_VALUE) {
            _g.drawImage(
                    _buffer,
                    _region.x, _region.y, _region.x + _region.w, _region.y + _region.h,
                    _region.x, _region.y, _region.x + _region.w, _region.y + _region.h,
                    null);
        } else {
            _g.drawImage(
                    _buffer, _insets.left, _insets.top, null);
        }
        _g.dispose();
    }
}
