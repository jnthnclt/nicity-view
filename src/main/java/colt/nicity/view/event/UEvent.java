/*
 * UEvent.java.java
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
package colt.nicity.view.event;

import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IFocusEvents;
import colt.nicity.view.interfaces.IKeyEvents;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IMouseWheelEvents;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IWindowEvents;

/**
 *
 * @author Administrator
 */
public class UEvent {

    /**
     *
     * @param _v
     * @param _e
     * @return
     */
    public static XY_I point(IView _v, IEvent _e) {
        XY_I p = _v.getLocationOnScreen();
        if (_e instanceof AMouseEvent) {
            XY_I ep = ((AMouseEvent) _e).getPoint();
            p.x -= ep.x;
            p.y -= ep.y;
        }
        return p;
    }

    /**
     *
     * @param instance
     * @param task
     * @return
     */
    public static Object processEvent(Object instance, IEvent task) {

        if (!(task instanceof AViewEvent)) {
            return null;
        }
        AViewEvent e = (AViewEvent) task;

        if (e instanceof AMouseEvent && instance instanceof IMouseEvents) {
            /*if (instance instanceof IView) {
            XYWH_I b = ((IView)instance).getEventBounds();
            AMouseEvent me = (AMouseEvent)e;
            me.x = me.x+b.x;
            me.y = me.y+b.y;
            }*/
            if (instance instanceof IMouseMotionEvents) {
                IMouseMotionEvents mme = (IMouseMotionEvents) instance;
                if (e instanceof MouseMoved) {
                    mme.mouseMoved((MouseMoved) e);
                } else if (e instanceof MouseDragged) {
                    mme.mouseDragged((MouseDragged) e);
                }
            }
            if (instance instanceof IMouseWheelEvents) {
                IMouseWheelEvents mwe = (IMouseWheelEvents) instance;
                if (e instanceof MouseWheel) {
                    mwe.mouseWheel((MouseWheel) e);
                }
            }
            IMouseEvents me = (IMouseEvents) instance;
            if (e instanceof MousePressed) {
                me.mousePressed((MousePressed) e);
            } else if (e instanceof MouseReleased) {
                me.mouseReleased((MouseReleased) e);
            } else if (e instanceof MouseEntered) {
                me.mouseEntered((MouseEntered) e);
            } else if (e instanceof MouseExited) {
                me.mouseExited((MouseExited) e);
            }
            return null;
        }

        if (e instanceof AKeyEvent && instance instanceof IKeyEvents) {
            IKeyEvents ke = (IKeyEvents) instance;
            if (e instanceof KeyPressed) {
                ke.keyPressed((KeyPressed) e);
            } else if (e instanceof KeyReleased) {
                ke.keyReleased((KeyReleased) e);
            } else if (e instanceof KeyTyped) {
                ke.keyTyped((KeyTyped) e);
            }
            return null;
        }

        if (e instanceof AFocusEvent && instance instanceof IFocusEvents) {
            IFocusEvents fe = (IFocusEvents) instance;
            if (e instanceof FocusGained) {
                fe.focusGained((FocusGained) e);
            } else if (e instanceof FocusLost) {
                fe.focusLost((FocusLost) e);
            }
            return null;
        }

        if (e instanceof AWindowEvent && instance instanceof IWindowEvents) {
            IWindowEvents we = (IWindowEvents) instance;
            if (e instanceof WindowOpened) {
                we.windowOpened((WindowOpened) e);
            } else if (e instanceof WindowClosed) {
                we.windowClosed((WindowClosed) e);
            } else if (e instanceof WindowActivated) {
                we.windowActivated((WindowActivated) e);
            } else if (e instanceof WindowDeactivated) {
                we.windowDeactivated((WindowDeactivated) e);
            } else if (e instanceof WindowIconified) {
                we.windowIconified((WindowIconified) e);
            } else if (e instanceof WindowDeiconified) {
                we.windowDeiconified((WindowDeiconified) e);
            }
            return null;
        }
        return null;
    }
}
