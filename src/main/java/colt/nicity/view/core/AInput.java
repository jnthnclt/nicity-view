/*
 * AInput.java.java
 *
 * Created on 01-13-2010 07:11:46 AM
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
import colt.nicity.view.event.AMouseEvent;
import colt.nicity.view.event.AViewEvent;
import colt.nicity.view.event.FocusGained;
import colt.nicity.view.event.FocusLost;
import colt.nicity.view.event.KeyPressed;
import colt.nicity.view.event.KeyReleased;
import colt.nicity.view.event.KeyTyped;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.event.MouseWheel;
import colt.nicity.view.event.WindowActivated;
import colt.nicity.view.event.WindowClosed;
import colt.nicity.view.event.WindowDeactivated;
import colt.nicity.view.event.WindowDeiconified;
import colt.nicity.view.event.WindowIconified;
import colt.nicity.view.event.WindowOpened;
import colt.nicity.view.image.IImage;
import colt.nicity.asynchro.CallStack;
import colt.nicity.core.process.ICall;
import colt.nicity.core.collection.CSet;
import colt.nicity.core.lang.ASetObject;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IMouseWheelEvents;
import colt.nicity.view.interfaces.IToolTip;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class AInput {

    // Overridables
    /**
     *
     * @param _pe
     * @return
     */
    public XY_I mp(PrimativeEvent _pe) {
        int mx = (int) _pe.x;
        int my = (int) _pe.y;
        return new XY_I(mx, my);
    }
    /**
     *
     */
    static public boolean debug = true;
    /**
     *
     */
    static public boolean toolTips = true;
    /**
     *
     */
    static public boolean isDragging = false;
    /**
     *
     */
    static protected CallStack kernel = new CallStack(5,-1);
    /**
     *
     */
    static public boolean autoFocus = false;
    /**
     *
     */
    static public long cClickInterval = 200;
    /**
     *
     */
    public ToolTipTimer toolTipTrigger = null;
    /**
     *
     */
    public IToolTip toolTip = NullToolTip.cNull;
    /**
     *
     */
    public IImage toolTipBuffer = null;
    private CSet<UserEventState> userEventStates = new CSet<UserEventState>();

    private UserEventState userEventState(long _who) {
        UserEventState ues = userEventStates.get(_who);
        if (ues == null) {
            ues = new UserEventState(_who);
            userEventStates.add(ues);
        }
        return ues;
    }

    class UserEventState extends ASetObject {

        long who;
        XY_I mp, viewPressedPoint, pressedPoint, lastPoint, lastMovePoint;
        ClickTimer clickCounter = null;
        int latch = 0;
        MouseDragged lastDrag = null;
        IView mouseDragView = NullView.cNull;
        IView mouseWheelFocus = null;
        IView hardFocusView = NullView.cNull;
        IView focusedView = NullView.cNull;
        IView mouseView = NullView.cNull;
        IView mousePressedView = NullView.cNull;

        UserEventState(long _who) {
            who = _who;
        }

        public Object hashObject() {
            return who;
        }
    }

    /**
     *
     * @param _g
     */
    public void paintWhos(ICanvas _g) {
        Object[] all = userEventStates.getAll(Object.class);
        for (Object a : all) {
            UserEventState ues = (UserEventState) a;
            if (UV.paintWhos && ues.mp != null) {
                _g.setColor(AColor.getHashColor(a));
                _g.line(ues.mp.x - 0, ues.mp.y - 4, ues.mp.x + 0, ues.mp.y + 4);
                _g.line(ues.mp.x - 4, ues.mp.y - 0, ues.mp.x + 4, ues.mp.y + 0);
            }
        }
    }
    ADisplay display;

    /**
     *
     * @param _display
     */
    public AInput(ADisplay _display) {
        display = _display;
    }

    /**
     *
     * @return
     */
    public ADisplay display() {
        return display;
    }

    /**
     *
     */
    public void dispose() {
        toolTipBuffer = null;
    }

    // Generates Focus Events
    private void setMouseView(UserEventState _ues, IView view, AMouseEvent e) {

        if (view == _ues.mouseView) {
            return;
        }

        AViewEvent ae = MouseExited.newInstance(_ues.who, _ues.mouseView, e.getX(), e.getY(), e.getClickCount(), e.getModifiers(), 0f, 0f, display.w(), display.h(), e.isDragging(), view);
        _ues.mouseView.processEvent(ae);

        IView exited = _ues.mouseView;
        _ues.mouseView = view;

        ae = MouseEntered.newInstance(_ues.who, _ues.mouseView, e.getX(), e.getY(), e.getClickCount(), e.getModifiers(), 0f, 0f, display.w(), display.h(), e.isDragging(), exited);
        _ues.mouseView.processEvent(ae);
        if (view == NullView.cNull) {
            display.displaying().processEvent(FocusLost.newInstance(_ues.who, this, false));//??
        }
    }

    /**
     *
     * @param _who
     * @param _mouseWheelFocus
     */
    public void setMouseWheelFocus(long _who, IView _mouseWheelFocus) {
        userEventState(_who).mouseWheelFocus = _mouseWheelFocus;
    }

    /**
     *
     * @param _who
     * @return
     */
    public IView getMouseView(long _who) {
        return userEventState(_who).mouseView;
    }

    /**
     *
     * @param _who
     * @return
     */
    public IView getFocusedView(long _who) {
        return userEventState(_who).focusedView;
    }

    /**
     *
     * @param _who
     * @param view
     */
    public void setFocusedView(long _who, IView view) {
        UserEventState ues = userEventState(_who);
        if (view == ues.focusedView) {
            return;
        }
        if (ues.focusedView != null) {
            FocusLost fl = FocusLost.newInstance(_who, view, false);
            ues.focusedView.processEvent(fl);
        }
        IView lostFocus = ues.focusedView;
        ues.focusedView = view;
        if (ues.focusedView != null) {
            FocusGained fg = FocusGained.newInstance(_who, lostFocus, false);
            ues.focusedView.processEvent(fg);
        }
    }

    /**
     *
     * @param _who
     * @return
     */
    public IView getHardFocusedView(long _who) {
        return userEventState(_who).hardFocusView;
    }

    /**
     *
     * @param _who
     * @param _view
     */
    public void setHardFocusedView(long _who, IView _view) {
        UserEventState ues = userEventState(_who);
        if (_view == ues.hardFocusView) {
            return;
        }
        if (ues.hardFocusView != NullView.cNull) {
            FocusLost fl = FocusLost.newInstance(_who, _view, true);
            ues.hardFocusView.processEvent(fl);
        }
        IView lostFocus = ues.hardFocusView;
        ues.hardFocusView = (_view == null) ? NullView.cNull : _view;
        if (ues.hardFocusView != NullView.cNull) {
            FocusGained fg = FocusGained.newInstance(_who, lostFocus, true);
            ues.hardFocusView.processEvent(fg);
        }
    }

    /**
     *
     * @param _
     * @param event
     */
    public void handleEvent(IOut _,PrimativeEvent event) {
        if (event.family == ADK.cMouse) {
            mouse(_,event);
        } else if (event.family == ADK.cKey) {
            key(_,event);
        } else if (event.family == ADK.cComponent) {
            componenet(event);
        }
    }

    private void mouse(IOut _,PrimativeEvent event) {
        UserEventState ues = userEventState(event.who());
        IView window = display.displaying();
        ues.mp = mp(event);
        if (event.id == ADK.cMousePressed) {
            if (toolTips && toolTip != null) {
                toolTip.elaborate();
            }
        } else {
            if (toolTip != null) {
                toolTip = null;
                toolTipBuffer = null;
            }
        }
        if (toolTipTrigger != null) {
            toolTipTrigger.movement(event);
        } else {
            toolTipTrigger = new ToolTipTimer(event);
            kernel.enqueueCall(_,toolTipTrigger);
        }
        AMouseEvent ame = null;
        switch (event.id) {
            case ADK.cMouseDragged: {
                boolean _isDragging = isDragging;
                ADisplay _activePeer = ADisplay.activeDisplay;
                ADisplay _peer = display;


                if (_isDragging || (_activePeer != null && _peer != null && _activePeer != _peer)) {
                    ADisplay client = _activePeer;
                    if (_activePeer == null) {
                        System.out.println("AWindow impossibe");
                        //return;// should be impossibe??
                    } else {
                        //Point cp = ((IView)client).getLocationOnScreen();
                        XY_I cp = _activePeer.displaying().getLocationOnScreen();
                        XY_I p = window.getLocationOnScreen();
                        int dx = p.x - cp.x;
                        int dy = p.y - cp.y;

                        ame = MouseMoved.newInstance(
                                ues.who,
                                client, ues.mp.x + dx, ues.mp.y + dy, (int) event.z,
                                event.clickCount, event.modifiers, 0, 0,
                                0f, 0f, client.w(), client.h());
                        ame.setIsDragging(true);

                        IView view = ame.disbatchEvent(client.displaying(), client.displaying());
                        if (view == NullView.cNull) {
                            view = client.displaying();
                        }
                        setMouseView(ues, view, ame);
                        view.processEvent(ame);
                    }
                }

                if (ues.lastPoint == null) {
                    ues.lastPoint = new XY_I(0, 0);//!!bug fix
                }
                if (ues.pressedPoint == null) {
                    ues.pressedPoint = new XY_I(0, 0);//!!bug fix
                }

                XY_I p = new XY_I((int) event.x, (int) event.y);
                int dx = p.x - ues.lastPoint.x;
                int dy = p.y - ues.lastPoint.y;
                int sumdx = p.x - ues.pressedPoint.x;
                int sumdy = p.y - ues.pressedPoint.y;
                ues.lastPoint = p;

                if (ues.latch == 0) {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        ues.latch = MouseDragged.cXLatched;
                    } else if (Math.abs(dx) < Math.abs(dy)) {
                        ues.latch = MouseDragged.cYLatched;
                    }
                }
                int direction = MouseDragged.cCenter;
                if (ues.lastDrag != null) {
                    direction = ues.lastDrag.getDirection();
                }

                if (direction == MouseDragged.cCenter) {
                    if (sumdx > 5) {
                        direction = MouseDragged.cEast;
                    } else if (sumdx < -5) {
                        direction = MouseDragged.cWest;
                    } else if (sumdy > 5) {
                        direction = MouseDragged.cSouth;
                    } else if (sumdy < -5) {
                        direction = MouseDragged.cNorth;
                    }
                }

                if (ues.mouseDragView == NullView.cNull) {
                    ues.mouseDragView = ues.mouseView;
                }

                ame = MouseDragged.newInstance(
                        ues.who,
                        ues.mouseDragView,
                        ues.viewPressedPoint.x + sumdx, ues.viewPressedPoint.y + sumdy, (int) event.z,
                        event.clickCount, event.modifiers,
                        dx, dy,
                        sumdx, sumdy,
                        ues.viewPressedPoint.x, ues.viewPressedPoint.y,
                        ues.latch,
                        direction,
                        0f, 0f, display.w(), display.h());
                ues.lastDrag = (MouseDragged) ame;
                ues.mouseDragView.processEvent(ame);

                // used to produce mouse entered and exited events
                ame = MouseDragged.newInstance(
                        ues.who,
                        ues.mouseDragView,
                        ues.mp.x, ues.mp.y, (int) event.z,
                        event.clickCount,
                        event.modifiers,
                        dx, dy,
                        sumdx, sumdy,
                        ues.viewPressedPoint.x, ues.viewPressedPoint.y,
                        ues.latch,
                        direction,
                        0f, 0f, display.w(), display.h());
                IView view = ame.disbatchEvent(NullRootView.cNull, window);
                if (ADisplay.activeDisplay == display) {
                    setMouseView(ues, view, ame);
                }

                return;
            }
            case ADK.cMouseMoved: {
                XY_I p = new XY_I((int) event.x, (int) event.y);
                if (ues.lastMovePoint == null) {
                    ues.lastMovePoint = p;
                }
                int dx = p.x - ues.lastMovePoint.x;
                int dy = p.y - ues.lastMovePoint.y;
                ues.lastMovePoint = p;
                ame = MouseMoved.newInstance(
                        ues.who,
                        window, ues.mp.x, ues.mp.y, (int) event.z,
                        event.clickCount,
                        event.modifiers,
                        dx, dy,
                        0f, 0f, display.w(), display.h());
                break;
            }
            case ADK.cMouseEntered: {
                if (autoFocus && ADisplay.activeDisplay != null) {
                    ADisplay client = ADisplay.activeDisplay;
                    if (client != null && client.isAutoFocusable()) {
                        AutoFocusTimer timer = new AutoFocusTimer(display);
                        kernel.enqueueCall(_,timer);
                    }
                }
                boolean _isDragging = (ues.mouseDragView == NullView.cNull) ? false : true;
                //if (event.source instanceof IPeerView) {
                //	activePeer = (IPeerView)event.source; 
                //}
                ADisplay.activeDisplay = display;

                ame = MouseEntered.newInstance(
                        ues.who,
                        window, ues.mp.x, ues.mp.y,
                        event.clickCount,
                        event.modifiers,
                        0f, 0f, display.w(), display.h(), _isDragging, null);
                break;
            }
            case ADK.cMouseExited: {
                boolean _isDragging = (ues.mouseDragView == NullView.cNull) ? false : true;
                ame = MouseExited.newInstance(
                        ues.who,
                        window, ues.mp.x, ues.mp.y,
                        event.clickCount,
                        event.modifiers,
                        0f, 0f, display.w(), display.h(), _isDragging, null);
                break;
            }
            case ADK.cMousePressed: {

                ues.latch = 0;
                ues.pressedPoint = null;
                ues.lastPoint = null;
                ues.lastMovePoint = null;

                ues.pressedPoint = ues.lastPoint = new XY_I((int) event.x, (int) event.y);
                ame = MousePressed.newInstance(
                        ues.who,
                        window, ues.mp.x, ues.mp.y, (int) event.z,
                        event.clickCount,
                        event.modifiers,
                        0f, 0f, display.w(), display.h());
                ues.mousePressedView = ame.disbatchEvent(window, window);

                ues.viewPressedPoint = ame.getPoint();


                if (ues.mousePressedView != ues.hardFocusView) {
                    setHardFocusedView(ues.who, NullView.cNull);
                }
                if (ues.mousePressedView == NullView.cNull) {
                    ues.mousePressedView = window;
                }

                if (ues.clickCounter == null) {

                    setMouseView(ues, ues.mousePressedView, ame);
                    ues.mousePressedView.processEvent(ame);//?? ordering question

                    ues.clickCounter = new ClickTimer(ues);
                    ues.clickCounter.pressed(this, event, ues.mousePressedView);
                    kernel.enqueueCall(_,ues.clickCounter);

                } else {
                    ues.clickCounter.pressed(this, event, ues.mousePressedView);
                }
                return;
            }
            case ADK.cMouseReleased: {

                if (ues.pressedPoint == null) {
                    ues.pressedPoint = new XY_I(0, 0);//!!bug fix
                }
                XY_I p = new XY_I((int) event.x, (int) event.y);
                int sumdx = p.x - ues.pressedPoint.x;
                int sumdy = p.y - ues.pressedPoint.y;

                XY_I releasePoint = new XY_I(
                        ues.viewPressedPoint.x + sumdx,
                        ues.viewPressedPoint.y + sumdy);

                ues.latch = 0;
                ues.pressedPoint = null;
                ues.lastPoint = null;
                ues.lastMovePoint = null;

                if (ues.clickCounter != null) {
                    ues.clickCounter.released(
                            this, releasePoint, event, ues.mouseDragView, ues.lastDrag);
                } else {
                    System.out.println("lost release");
                }
                ues.mouseDragView = NullView.cNull;
                ues.mousePressedView = NullView.cNull;
                ues.lastDrag = null;
                return;
            }
            case ADK.cMouseClicked:
                return;
            case ADK.cMouseWheel:


                MouseWheel amwe = (MouseWheel) MouseWheel.newInstance(
                        ues.who,
                        window, ues.mp.x, ues.mp.y,
                        event.clickCount, event.modifiers,
                        event.scrollType, event.scrollAmount,
                        event.wheelRotation,
                        0f, 0f, display.w(), display.h());

                if (ues.mouseWheelFocus instanceof IMouseWheelEvents) {
                    ues.mouseWheelFocus.processEvent(amwe);
                } else {
                    IView view = amwe.disbatchEvent(window, window);
                    view.processEvent(amwe);
                }

                //System.out.println(amwe+" "+focusedView);
                return;
            default:
                //System.out.println(event);
                return;
        }
        IView view = ame.disbatchEvent(window, window);
        if (view == NullView.cNull) {
            view = window;
        }
        setMouseView(ues, view, ame);
        view.processEvent(ame);
        return;
    }

    private void key(IOut _,PrimativeEvent e) {
        UserEventState ues = userEventState(e.who());
        if (e.id == ADK.cKeyPressed) {
            if (ues.hardFocusView != NullView.cNull) {
                AViewEvent ake = KeyPressed.newInstance(
                        ues.who,
                        ues.hardFocusView, e.keyCode, e.keyChar, e.modifiers);
                ues.hardFocusView.processEvent(ake);
            } else {
                AViewEvent ake = KeyPressed.newInstance(
                        ues.who,
                        ues.focusedView, e.keyCode, e.keyChar, e.modifiers);
                ues.focusedView.processEvent(ake);
            }
            return;
        }
        if (e.id == ADK.cKeyReleased) {
            if (ues.hardFocusView != NullView.cNull) {
                AViewEvent ake = KeyReleased.newInstance(
                        ues.who,
                        ues.hardFocusView, e.keyCode, e.keyChar, e.modifiers);
                ues.hardFocusView.processEvent(ake);
            } else {
                AViewEvent ake = KeyReleased.newInstance(
                        ues.who,
                        ues.focusedView, e.keyCode, e.keyChar, e.modifiers);
                ues.focusedView.processEvent(ake);
            }
        }
        if (e.id == ADK.cKeyTyped) {
            if (ues.hardFocusView != NullView.cNull) {
                AViewEvent ake = KeyTyped.newInstance(
                        ues.who,
                        ues.hardFocusView, e.keyCode, e.keyChar, e.modifiers);
                ues.hardFocusView.processEvent(ake);
            } else {
                AViewEvent ake = KeyTyped.newInstance(
                        ues.who,
                        ues.focusedView, e.keyCode, e.keyChar, e.modifiers);
                ues.focusedView.processEvent(ake);
            }
        }
    }

    private void componenet(PrimativeEvent e) {
        UserEventState ues = userEventState(e.who());
        IView window = display.displaying();
        if (e.id == ADK.cFocusGained) {
            AViewEvent afe = FocusGained.newInstance(ues.who, window, false);
            IView view = window.disbatchEvent(window, afe);
            view.processEvent(afe);
            return;
        }
        if (e.id == ADK.cFocusLost) {
            AViewEvent afe = FocusLost.newInstance(ues.who, window, false);
            IView view = window.disbatchEvent(window, afe);
            view.processEvent(afe);
            return;
        }

        if (e.id == ADK.cWindowOpened) {
            //window.activePeer = window.peer;
            ADisplay.activeDisplay = display;
            AViewEvent awe = WindowOpened.newInstance(ues.who, window);
            IView v = awe.disbatchEvent(window, window);
            v.processEvent(awe);
            return;
        }
        if (e.id == ADK.cWindowClosing) {
            AViewEvent awe = WindowClosed.newInstance(ues.who, window);
            IView v = awe.disbatchEvent(window, window);
            v.processEvent(awe);
            display.dispose();
            setHardFocusedView(ues.who, NullView.cNull);
            return;
        }
        if (e.id == ADK.cWindowActivated) {
            //topWindow = AWindow.this;
            //window.activePeer = window.getPeer();
            ADisplay.topDisplay = display;
            ADisplay.activeDisplay = display;
            AViewEvent awe = WindowActivated.newInstance(ues.who, window);
            IView v = awe.disbatchEvent(window, window);
            v.processEvent(awe);
            display.repaint();
            return;
        }
        if (e.id == ADK.cWindowDeactivated) {
            AViewEvent awe = WindowDeactivated.newInstance(ues.who, window);
            IView v = awe.disbatchEvent(window, window);
            v.processEvent(awe);
            setHardFocusedView(ues.who, NullView.cNull);
            return;
        }
        if (e.id == ADK.cWindowIconified) {
            AViewEvent awe = WindowIconified.newInstance(ues.who, window);
            IView v = awe.disbatchEvent(window, window);
            v.processEvent(awe);
            setHardFocusedView(ues.who, NullView.cNull);
            return;
        }
        if (e.id == ADK.cWindowDeiconified) {
            AViewEvent awe = WindowDeiconified.newInstance(ues.who, window);
            IView v = awe.disbatchEvent(window, window);
            v.processEvent(awe);
            display.repaint();
            return;
        }
        if (e.id == ADK.cWindowResized) {

            float dw = display.ow() - display.w();
            float dh = display.oh() - display.h();

            if (dw != 0 || dh != 0) {
                window.disableFlag(UV.cInterior);
                window.layoutInterior(new Flex(window, dw, dh));
                window.repair();
                window.flush();
            }
        }
    }

    private class ClickTimer implements ICall {

        private int pressedCount = 0;
        private int releasedCount = 0;
        private AInput pressedInput;
        private AInput releasedInput;
        private IView pressedView;
        private IView releasedView;
        private IView mouseDragView;
        private MouseDragged lastDrag;
        private XY_I releasePoint;
        private PrimativeEvent e;
        private PrimativeEvent oldE;
        UserEventState ues;

        ClickTimer(UserEventState _ues) {
            ues = _ues;
        }

        public void pressed(AInput _pressedDisplay, PrimativeEvent _pressed, IView _pressedView) {
            e = _pressed;
            pressedCount++;
            pressedInput = _pressedDisplay;
            pressedView = _pressedView;
        }

        @Override
        public String toString() {
            return "Click Timer";
        }

        public void released(AInput _releasedWindow, XY_I _releasePoint, PrimativeEvent _released, IView _mouseDragView, MouseDragged _lastDrag) {
            e = _released;
            releasePoint = _releasePoint;
            releasedCount++;
            releasedInput = _releasedWindow;
            mouseDragView = _mouseDragView;
            lastDrag = _lastDrag;


            boolean isDragging = (mouseDragView != NullView.cNull) ? true : false;
            ues.mp = mp(e);
            AMouseEvent ame = MouseReleased.newInstance(
                    ues.who,
                    pressedInput.display, ues.mp.x, ues.mp.y, (int) _released.z,
                    -1, e.modifiers,
                    0f, 0f, releasedInput.display.w(), releasedInput.display.h(),
                    isDragging, lastDrag);

            IView v = ame.disbatchEvent(releasedInput.display.displaying(), releasedInput.display.displaying());
            if (mouseDragView == NullView.cNull) {
                v.processEvent(ame);
            } else {
                mouseDragView.processEvent(ame);
            }
        }

        public void invoke(IOut _) {
            try {
                do {
                    oldE = e;
                    try {
                        Thread.sleep(cClickInterval);
                    } catch (Exception x) {
                    }
                    ;
                } while (((oldE != e) || pressedCount > releasedCount));

                pressedInput.userEventState(ues.who).clickCounter = null;
                boolean isDragging = (mouseDragView != NullView.cNull) ? true : false;
                ues.mp = mp(e);
                AMouseEvent ame = MouseReleased.newInstance(
                        ues.who,
                        pressedInput, ues.mp.x, ues.mp.y, (int) e.z,
                        releasedCount, e.modifiers,
                        0f, 0f, releasedInput.display.w(), releasedInput.display.h(),
                        isDragging, lastDrag);
                IView v = ame.disbatchEvent(releasedInput.display.displaying(), releasedInput.display.displaying());

                if (debug && ame.getClickCount() == 5) {
                    IView p = v;
                    System.out.println("----------View Containment----------");
                    while (p != null && p != NullView.cNull) {
                        System.out.println(p.getClass() + " " + p);
                        p = p.getParentView();
                    }
                }

                if (mouseDragView == NullView.cNull) {
                    v.processEvent(ame);
                } else {
                    mouseDragView.processEvent(ame);
                }
            } catch (Exception x) {
                x.printStackTrace();
                pressedInput.userEventState(ues.who).clickCounter = null;
            }
        }
    }

    private class AutoFocusTimer implements ICall {

        private ADisplay focus;

        private AutoFocusTimer(ADisplay _focus) {
            focus = _focus;
        }

        @Override
        public String toString() {
            return "Auto Focus Timer";
        }

        public void invoke(IOut _) {
            try {
                Thread.sleep(500);
            } catch (Exception x) {
            }

            if (focus.activeDisplay == focus) {
                focus.toFront();
            }
        }
    }

    /**
     *
     */
    public class ToolTipTimer implements ICall {

        private PrimativeEvent e;
        private PrimativeEvent oldE;

        ToolTipTimer(PrimativeEvent _event) {
            e = _event;
        }

        @Override
        public String toString() {
            return "Tooltip Timer";
        }

        /**
         *
         * @param _event
         */
        public void movement(PrimativeEvent _event) {
            e = _event;
        }

        /**
         *
         * @param _
         */
        public void invoke(IOut _) {
            do {
                oldE = e;
                try {
                    Thread.sleep(1000);
                } catch (Exception x) {
                }

            } while (oldE != e);
            showToolTip();
        }
    }

    /**
     *
     */
    protected void showToolTip() {
        toolTipTrigger = null;

        /*
        IToolTip tt = input.getMouseView().getToolTip();
        if (tt == null || tt == NullToolTip.cNull) return;
        toolTip = tt;

        toolTip.setParentView(NullRootView.cNull);
        toolTip.layoutInterior();

        BufferedImage toolTipBuffer = new BufferedImage((int)toolTip.getW(),(int)toolTip.getH(),BufferedImage.TYPE_INT_RGB);
        Graphics ttbg = toolTipBuffer.getGraphics();

        ARectangle region = new ARectangle(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
        toolTip.paint(
        NullView.cNull,ttbg,
        new Layer(0f,0f,0f,0f,toolTip.getW(),toolTip.getH()),
        UV.cRepair,
        region
        );
        ttbg.dispose();

        Point mp = input.mp();
        
        int tx = (int)(mp.x-(toolTip.getW()/2));
        int ty = (int)(mp.y-(toolTip.getH())+4);
        
        if (tx+toolTip.getW() > getW()) tx -= (int)((tx+toolTip.getW()) - getW());
        if (ty+toolTip.getH() > getH()) ty -= (int)((ty+toolTip.getH()) - getH());

        if (tx < peerBorder.getX()) tx = (int)peerBorder.getX();
        if (ty < peerBorder.getY()) ty = (int)peerBorder.getY();

        getPeer().directRegion(
        toolTipBuffer,region,
        new ARectangle(tx,ty,region.w,region.h)
        );*/
    }
}
