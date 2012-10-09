/*
 * VPopupViewer.java.java
 *
 * Created on 01-03-2010 01:31:36 PM
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

import colt.nicity.core.lang.IOut;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.border.PopupBorder;
import colt.nicity.view.event.AMouseEvent;
import colt.nicity.view.event.AViewEvent;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IPeerView;
import colt.nicity.view.interfaces.IRootView;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.value.VAlwaysOver;

/**
 *
 * @author Administrator
 */
public class VPopupViewer extends Viewer implements IRootView {

    public static void main(String[] args) {
        VChain x = new VChain(UV.cSN);
        for (int i = 0; i < 100; i++) {
            x.add(new VButton("popup " + i) {

                @Override
                public void picked(IEvent _e) {
                    VChain pop = new VChain(UV.cSN);
                    for (int p = 0; p < 10; p++) {
                        pop.add(new VButton("nested " + p) {

                            @Override
                            public void picked(IEvent _e) {
                                UV.popup(this, UV.cSENW, new VButton("end of line") {

                                    @Override
                                    public void picked(IEvent _e) {
                                        getRootView().dispose();
                                    }
                                }, true, true);
                            }
                        });
                    }
                    UV.popup(this, UV.cSENW, pop, true, true);
                }
            });
        }

        UV.exitFrame(new VPopupViewer(new VPan(x, 600, 600)), "view pop test");
    }
    private final RigidBox empty = new RigidBox(1, 1);
    private Viewer over;
    private boolean hadChildEvent = false;

    public VPopupViewer(IView _view) {
        over = new Viewer(empty);
        setContent(new VAlwaysOver(new VTrapFlex(over), _view, UV.cOrigin));
    }

    @Override
    public IView disbatchEvent(IView parent, AViewEvent event) {
        IView v = super.disbatchEvent(parent, event);
        if (!UV.isChild(over, v)) {
            if (event instanceof AMouseEvent && ((AMouseEvent) event).isDragging()) {
                return v;
            }
            if (!hadChildEvent) {
                return v;
            }
            over.setView(empty);
            layoutAllInterior();
            paint();
        } else {
            hadChildEvent = true;
        }
        return v;
    }

    @Override
    public void flush() {
        getParentView().getRootView().flush();
    }

    /**
     *
     * @param _relativeTo
     * @param _popup
     * @param _place
     */
    public void popup(IView _relativeTo, IView _popup, XY_I pp, boolean _hideOnExit, boolean _hideOnLost) {
        hadChildEvent = false;
        int pad = 8;

        //IView parent = getParentView();
        //int cw = (int) parent.getW() - (pad * 2);
        //int ch = (int) parent.getH() - (pad * 2);

        int cw = (int) getW() - (pad * 2);
        int ch = (int) getH() - (pad * 2);

        VPopUp popUp = new VPopUp(_popup,empty, _hideOnExit, _hideOnLost);
        popUp.layoutInterior();
        int pw = (int) popUp.getW();
        int ph = (int) popUp.getH();

        XY_I p = getLocationInView(this, _relativeTo);
        if (p == null) {
            p = new XY_I(pad, pad);
        }
        p.x += pp.x;
        p.y += pp.y;

        if (p.x + pw > cw) {
            p.x -= ((p.x + pw) - (cw));
        }
        if (p.y + ph > ch) {
            p.y -= ((p.y + ph) - (ch));
        }

        if (p.x < 0 || p.y < 0) {
            int ppw = -1;
            int pph = -1;
            if (p.x < 0) {
                p.x = 0;
                if (p.x + pw > cw) {
                    ppw = cw;

                }
            }
            if (p.y < 0) {
                p.y = 0;
                if (p.y + ph > ch) {
                    pph = ch;

                }
            }
            if (ppw != -1 || pph != -1) {
                popUp = new VPopUp(new VPan(_popup, ppw, pph),empty, _hideOnExit, _hideOnLost);
                popUp.paint();
            }
        }

        over.setPlacer(new Placer(popUp, new Place(UV.cOrigin, p.x, p.y)));
        over.paint();
    }

    private static XY_I getLocationInView(IView _parent, IView child) {
        int x = 0;
        int y = 0;
        IView parent = child;
        while (_parent != parent
                && parent != null
                && parent != NullView.cNull) {
            if (parent instanceof VClip) {
                x += parent.getX() + ((VClip) parent).ox();
                y += parent.getY() + ((VClip) parent).oy();
            } else {
                x += parent.getX();
                y += parent.getY();
            }
            parent = parent.getParentView();
        }
        if (parent == _parent) {
            return new XY_I(x, y);
        }
        return null;
    }

    static private class VPopUp extends Viewer implements IMouseEvents, IMouseMotionEvents {

        boolean closeOnFocusLost;
        boolean closeOnMouseExited;
        IView empty;

        VPopUp(IView view,IView empty, boolean closeOnMouseExited, boolean closeOnFocusLost) {
            this.empty = empty;
            this.closeOnMouseExited = closeOnMouseExited;
            this.closeOnFocusLost = closeOnFocusLost;
            setContent(view);
            setBorder(new PopupBorder(8));
        }

        @Override
        public IView disbatchEvent(IView parent, AViewEvent event) {
            IView v = super.disbatchEvent(parent, event);
            if (event instanceof MouseMoved) {
            }
            return v;
        }

        @Override
        public void mouseEntered(MouseEntered e) {
            grabFocus(e.who());
        }

        @Override
        public void mouseExited(MouseExited e) {
            if (e.getEntered() instanceof IView) {
                if (UV.isChild(this, (IView) e.getEntered())) {
                    return;
                }
            }
            if (closeOnMouseExited) {
                getParentView().setView(empty);
            }
        }

        @Override
        public void mousePressed(MousePressed e) {
        }

        @Override
        public void mouseReleased(MouseReleased e) {
        }

        @Override
        public void mouseMoved(MouseMoved e) {
        }

        @Override
        public void mouseDragged(MouseDragged e) {
        }
    }

    @Override
    public IPeerView getPeerView() {
        return getParentView().getRootView().getPeerView();
    }

    @Override
    public IView getFocusedView(long _who) {
        return getParentView().getRootView().getFocusedView(_who);
    }

    @Override
    public void setFocusedView(long _who, IView view) {
        getParentView().getRootView().setFocusedView(_who, view);
    }

    @Override
    public IView getHardFocusedView(long _who) {
        return getParentView().getRootView().getHardFocusedView(_who);
    }

    @Override
    public void setHardFocusedView(long _who, IView view) {
        getParentView().getRootView().setHardFocusedView(_who, view);
    }

    @Override
    public void setMouseWheelFocus(long _who, IView _mouseWheelFocus) {
        getParentView().getRootView().setMouseWheelFocus(_who, _mouseWheelFocus);
    }

    @Override
    public void addToRepaint(IView _view) {
        getParentView().getRootView().addToRepaint(_view);
    }

    @Override
    public void processEvent(IOut _, PrimativeEvent event) {
        getParentView().getRootView().processEvent(_, event);
    }

    @Override
    public void toFront() {
        getParentView().getRootView().toFront();
    }

    @Override
    public boolean isValid() {
        return getRootView().isValid();
    }

    @Override
    public void dispose() {
        if (over.getContent() == null || over.getContent() instanceof RigidBox) {
            getParentView().getRootView().dispose();
        } else {
            over.setView(empty);
            paint();
        }
    }

    @Override
    public void maximize() {
        getParentView().getRootView().maximize();
    }

    @Override
    public void iconify() {
        getParentView().getRootView().iconify();
    }
}
