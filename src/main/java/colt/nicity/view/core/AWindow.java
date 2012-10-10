/*
 * AWindow.java.java
 *
 * Created on 01-03-2010 01:31:33 PM
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

import colt.nicity.core.collection.CSet;
import colt.nicity.core.lang.ICallback;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.memory.struct.UXYWH_I;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.awt.NullPeerView;
import colt.nicity.view.awt.PFrame;
import colt.nicity.view.awt.PeerViewBorder;
import colt.nicity.view.border.NullBorder;
import colt.nicity.view.event.ADK;
import colt.nicity.view.event.AViewEvent;
import colt.nicity.view.event.WindowClosed;
import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IDropView;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IEventClient;
import colt.nicity.view.interfaces.IPeerView;
import colt.nicity.view.interfaces.IPlacer;
import colt.nicity.view.interfaces.IRootView;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class AWindow extends AViewer implements IEventClient, IRootView, IDropView {

    /**
     *
     */
    static public int openWindowCount = 0;
    /**
     *
     */
    static public ICallback onLastWindowClosed;
    /**
     *
     */
    static public CSet openWindows = new CSet();
    /**
     *
     */
    public ICallback windowClosedCallback;
    /**
     *
     */
    protected IPeerView peer;
    boolean systemExitOnWindowClose = false;
    private ADisplay display;
    private AInput input;

    /**
     *
     */
    public AWindow() {
        init();
        openWindowCount++;
    }

    /**
     *
     * @param _placer
     */
    public AWindow(IPlacer _placer) {
        super(_placer);
        init();
        openWindowCount++;
    }

    /**
     *
     * @param _view
     */
    public AWindow(IView _view) {
        super(_view);
        init();
        openWindowCount++;
    }

    /**
     *
     * @param _view
     * @param _flex
     */
    public AWindow(IView _view, Flex _flex) {
        super(_view, _flex);
        init();
        openWindowCount++;
    }

    /**
     *
     */
    protected void init() {
        openWindows.add(this);
        display = new ADisplay(this) {

            @Override
            public ICanvas display(long _who, float _w, float _h) {
                if (peer == null) {
                    return null;
                }
                ICanvas g = peer.ensureSize(_who, (int) _w, (int) _h);
                if (g == null) {
                    return null;
                }

                return g;
            }

            @Override
            public void displayable(ICanvas _g, XYWH_I _region) {
                if (input != null) {
                    input.paintWhos(_g);//??
                }
                if (peer != null) {
                    peer.modifiedRegion(_region);
                }
                if (_g != null) {
                    _g.dispose();
                }
            }

            @Override
            public void toFront() {
                AWindow.this.toFront();
            }

            @Override
            public float ow() {
                return getPeer().getW() - peerBorder.getW();
            }

            @Override
            public float oh() {
                return getPeer().getH() - peerBorder.getH();
            }

            @Override
            public String toString() {
                return "Portal Display Handler " + super.toString();
            }
        };
        input = new AInput(display) {

            @Override
            public XY_I mp(PrimativeEvent _pe) {
                int mx = (int) (_pe.x - peerBorder.getW());
                int my = (int) (_pe.y - peerBorder.getH());
                return new XY_I(mx, my);
            }

            @Override
            public String toString() {
                return "Portal Input Handler " + super.toString();
            }
        };
        super.setParentView(NullRootView.cNull);
    }
    /**
     *
     */
    protected boolean gotPeer = false;
    /**
     *
     */
    final protected Object getPeerLock = new Object();
    /**
     *
     */
    protected IBorder peerBorder = NullBorder.cNull;

    /**
     *
     * @param _peer
     */
    public void forcePeer(IPeerView _peer) {
        synchronized (getPeerLock) {
            if (requestedDispose) {
                return;
            }
            gotPeer = true;
            peer = _peer;
        }
    }

    /**
     *
     * @return
     */
    public IPeerView getPeer() {

        PFrame _peer = null;
        synchronized (getPeerLock) {
            if (requestedDispose) {
                return NullPeerView.cNull;
            }
            if (gotPeer) {
                return peer;
            }
            _peer = new PFrame(this);
            gotPeer = true;
            peer = _peer;
        }

        _peer.setUndecorated(true);
        setBorder(null);

        _peer.enableDND(this);
        peerBorder = new PeerViewBorder(_peer);
        return peer;
    }

    @Override
    public void setBorder(IBorder border) {
        super.setBorder(null);
    }

    @Override
    public void setParentView(IView parent) {
    }

    /**
     *
     * @param _boolean
     */
    public void systemExitOnClose(boolean _boolean) {
        systemExitOnWindowClose = _boolean;
    }

    /**
     *
     * @return
     */
    public boolean systemExitOnClose() {
        return systemExitOnWindowClose;
    }

    /**
     *
     * @param _boolean
     */
    public void setAutoFocusable(boolean _boolean) {
        ADisplay _display = display;
        if (_display != null) {
            _display.setAutoFocusable(_boolean);
        }
    }

    /**
     *
     * @return
     */
    public boolean isAutoFocusable() {
        ADisplay _display = display;
        if (_display != null) {
            return _display.isAutoFocusable();
        }
        return false;
    }

    // IRootView
    @Override
    public IPeerView getPeerView() {
        return getPeer();
    }

    /**
     *
     */
    public void show() {
        super.setParentView(NullRootView.cNull);
        IPeerView p = null;
        synchronized (getPeerLock) {
            if (requestedDispose) {
                return;
            }
            p = getPeer();
           
        }
        p.setVisible(true);
        ADisplay.topDisplay = display;
        flush();
    }

    @Override
    public void layoutInterior() {
        flush();
    }

    /**
     *
     */
    @Override
    public void iconify() {
        getPeer().iconify();
    }

    /**
     *
     */
    public void deiconify() {
        getPeer().deiconify();
    }

    /**
     *
     */
    @Override
    public void maximize() {
        getPeer().maximize();
    }

    @Override
    public void toFront() {
        getPeer().toFront();
    }

    /**
     *
     */
    public void toBack() {
        getPeer().toBack();
    }

    /**
     *
     * @return
     */
    public boolean closed() {
        return peer == NullPeerView.cNull;
    }

    @Override
    public boolean isValid() {
        return !requestedDispose;
    }
    private boolean requestedDispose = false;

    @Override
    public void dispose() {
        openWindows.remove(this);
        ADisplay _display = display;
        if (_display != null) {
            _display.dispose();
        }
        display = null;
        AInput _input = input;
        if (_input != null) {
            _input.dispose();
        }
        input = null;
        if (ADisplay.topDisplay == display) {
            ADisplay.topDisplay = null;
        }
        synchronized (getPeerLock) {
            requestedDispose = true;
            if (gotPeer && peer != NullPeerView.cNull) {
                peer.dispose();
                openWindowCount--;
                peer = NullPeerView.cNull;
            }
        }
        if (openWindowCount < 0) {
            openWindowCount = 0;
        }
        if (openWindowCount == 0) {
            if (onLastWindowClosed != null) {
                new Thread() {

                    @Override
                    public void run() {
                        onLastWindowClosed.callback(null);
                    }
                }.start();
            }
        }

        placer = NullPlacer.cNull;
        super.setParentView(null);

        if (systemExitOnWindowClose) {
            System.exit(0);
        }
        if (windowClosedCallback != null) {
            windowClosedCallback.callback(this);
        }
        IView content = getContent();
        if (content != null) {
            content.setParentView(null);
        }
    }
    // !! this is still a little ugly
    // !! i would like to avoid synch
    /**
     *
     */
    public static CSet popups = new CSet();

    @Override
    public void processEvent(IOut _, PrimativeEvent event) {
        if (popups.getCount() > 0) {// hacky
            if (event.family == ADK.cComponent) {
                if (event.id == ADK.cFocusGained) {
                    for (Object p : popups.getAll(Object.class)) {
                        if (event.source == ((AWindowPopup) p).referencePeer()) {
                            continue;
                        }
                        ((AWindow) p).dispose();
                    }
                }
            } else if (event.family == ADK.cComponent) {
                if (event.id == ADK.cWindowActivated) {
                    for (Object p : popups.getAll(Object.class)) {
                        if (event.source == ((AWindowPopup) p).referencePeer()) {
                            continue;
                        }
                        ((AWindow) p).dispose();
                    }
                }
            }
        }
        if (input != null) {
            input.handleEvent(_, event);
        }
    }

    @Override
    public void promoteEvent(IEvent _task) {
        if (_task instanceof WindowClosed) {
            AViewEvent awe = WindowClosed.newInstance(((WindowClosed) _task).who(), this);
            IView v = awe.disbatchEvent(this, this);
            v.processEvent(awe);
            dispose();
            input.setHardFocusedView(0, NullView.cNull);
        } else {
            super.promoteEvent(_task);
        }
    }

    // This is intentionally not synchronized
    @Override
    public void addToRepaint(IView _view) {
        ADisplay _display = display;
        if (_display != null) {
            _display.addToRepaint(_view);
        }
    }

    @Override
    public void flush() {
        ADisplay _display = display;
        if (_display != null) {
            _display.repaint();
        }
    }

    /**
     *
     */
    public void fullscreen() {
        getPeer().fullscreen();
    }

    // IView overloading
    @Override
    public boolean isVisible(int _x, int _y, int _w, int _h) {
        XYWH_I r = UXYWH_I.intersection(_x, _y, _w, _h, 0, 0, (int) getW(), (int) getH());
        if (r == null) {
            return false;
        }
        return true;
    }

    @Override
    public IRootView getRootView() {
        return this;
    }

    @Override
    public void enableEvents(long tasksToEnable) {
        super.enableEvents(tasksToEnable);
        getPeer().enablePeerEvents(tasksToEnable);
    }

    @Override
    public void disableEvents(long tasksToDisable) {
        super.disableEvents(tasksToDisable);
        getPeer().disablePeerEvents(tasksToDisable);
    }

    @Override
    public void setLocation(float x, float y) {
        getPeer().setCorner((int) x, (int) y);
        super.setLocation(0, 0);
    }

    @Override
    public XY_I getLocationOnScreen() {
        return getPeer().getCorner();
    }

    @Override
    public XY_I getLocationInWindow() {
        return new XY_I(0, 0);
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return getPeer().getTitle();
    }

    /**
     *
     * @param _title
     */
    public void setTitle(String _title) {
        getPeer().setTitle(_title);
    }

    @Override
    public IView getFocusedView(long _who) {
        return input.getFocusedView(_who);
    }

    @Override
    public void setFocusedView(long _who, IView view) {
        if (input != null) {
            input.setFocusedView(_who, view);
        }
    }

    @Override
    public IView getHardFocusedView(long _who) {
        return input.getHardFocusedView(_who);
    }

    @Override
    public void setHardFocusedView(long _who, IView view) {
        if (input != null) {
            input.setHardFocusedView(_who, view);
        }
    }

    @Override
    public void setMouseWheelFocus(long _who, IView _mouseWheelFocus) {
        if (input != null) {
            input.setMouseWheelFocus(_who, _mouseWheelFocus);
        }
    }

    @Override
    public void frame(IView _view, Object _title) {
        UV.frame(_view, _title);
    }
}
