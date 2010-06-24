/*
 * AcceptSelectedDecline.java.java
 *
 * Created on 01-03-2010 01:34:45 PM
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
package colt.nicity.view.value;

import colt.nicity.view.border.ButtonBorder;
import colt.nicity.view.border.PopupBorder;
import colt.nicity.view.event.WindowActivated;
import colt.nicity.view.event.WindowClosed;
import colt.nicity.view.event.WindowDeactivated;
import colt.nicity.view.event.WindowDeiconified;
import colt.nicity.view.event.WindowIconified;
import colt.nicity.view.event.WindowOpened;
import colt.nicity.view.list.UVItem;
import colt.nicity.view.list.VItem;
import colt.nicity.view.list.VList;
import colt.nicity.core.collection.IBackcall;
import colt.nicity.view.core.AWindow;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VFrame;
import colt.nicity.view.core.VPan;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewText;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IVItem;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IWindowEvents;

/**
 *
 * @author Administrator
 */
public class AcceptSelectedDecline extends Viewer implements IWindowEvents {

    String[] message;
    IBackcall backcall;
    VList list;

    /**
     *
     * @param _explain
     * @param _backcall
     */
    public AcceptSelectedDecline(String _explain, IBackcall _backcall) {
        this(new String[]{_explain}, _backcall);
    }

    /**
     *
     * @param _explain
     * @param _backcall
     */
    public AcceptSelectedDecline(String[] _explain, IBackcall _backcall) {
        this(_explain, null, _backcall);
    }

    /**
     *
     * @param _explain
     * @param _view
     * @param _backcall
     */
    public AcceptSelectedDecline(String _explain, IView _view, IBackcall _backcall) {
        this(new String[]{_explain}, _view, _backcall);
    }

    /**
     *
     * @param _explain
     * @param _view
     * @param _backcall
     */
    public AcceptSelectedDecline(String[] _explain, IView _view, IBackcall _backcall) {
        backcall = _backcall;

        VChain chain = new VChain(UV.cSN);
        if (_explain != null) {
            VChain viewer = new VChain(UV.cEW, new ViewText(_explain));
            viewer.setBorder(new PopupBorder(null, 20));
            viewer.spans(UV.cXEW);
            chain.add(viewer);
        }
        if (_view != null) {
            Viewer viewer = new Viewer(_view);
            viewer.setBorder(new PopupBorder(null, 20));
            viewer.spans(UV.cXEW);
            chain.add(viewer);
        }

        list = new VList(backcall, 1) {

            public IVItem vItem(Object _value) {
                return new VItem(new VString(_value), _value) {

                    public void picked(IEvent _e) {
                        _accept(UVItem.getSelected((IVItem) this));
                    }

                    public void selected(IEvent _e) {
                        _accept(UVItem.getSelected((IVItem) this));
                    }
                };
            }
        };
        VPan accept = new VPan(list, 320, 240);

        VButton selectAll = new VButton(" Accept All ") {

            public void picked(IEvent _e) {
                list.selectAllItems();
                _accept(list.getSelectedItems());
            }
        };

        VButton acceptSelected = new VButton(" Accept Selected ") {

            public void picked(IEvent _e) {
                _accept(list.getSelectedItems());
            }
        };

        VButton decline = new VButton(" Decline ") {

            public void picked(IEvent _e) {
                _decline();
                if (window != null) {
                    window.dispose();
                }
            }
        };

        VChain menu = new VChain(UV.cEW);
        menu.add(selectAll);
        menu.add(new RigidBox(20, 20));
        menu.add(acceptSelected);
        menu.add(new RigidBox(20, 20));
        menu.add(decline);
        menu.spans(UV.cXEW);
        menu.setBorder(new PopupBorder(null, 20));

        chain.add(accept);
        //chain.add(selectAll);
        //chain.add(deselectAll);
        chain.add(menu);
        setPlacer(new Placer(chain));
        setBorder(new PopupBorder(20));
    }

    private void pleaseWait() {
        VChain chain = new VChain(UV.cSN);
        String[] _explain = new String[]{
            "Please Wait the system ",
            "is processing your response"
        };
        VChain viewer = new VChain(UV.cEW, new ViewText(_explain));
        viewer.setBorder(new PopupBorder(null, 20));
        viewer.spans(UV.cXEW);
        chain.add(viewer);
        setPlacer(new Placer(chain));
        setBorder(new ButtonBorder());
        layoutInterior();
        flush();
    }
    private boolean choosed = false;

    private void _accept(Object _accepted) {
        if (choosed) {
            return;
        }
        choosed = true;
        pleaseWait();
        accept(_accepted);
        if (window != null) {
            window.dispose();
        }
    }

    /**
     *
     * @param _accepted
     */
    public void accept(Object _accepted) {
    }

    private void _decline() {
        if (choosed) {
            return;
        }
        choosed = true;
        pleaseWait();
        decline();
        if (window != null) {
            window.dispose();
        }
    }

    /**
     *
     */
    public void decline() {
    }

    public String toString() {
        return "Please Help :)";
    }
    AWindow window;

    /**
     *
     * @param _centerRelativeTo
     */
    public void toFront(IView _centerRelativeTo) {
        if (window == null) {
            VFrame frameViewer = new VFrame(this, this);
            window = new AWindow(frameViewer);
            window.setTitle(this.toString());
            if (_centerRelativeTo == null) {
                UV.centerWindow(window);
            } else {
                UV.centerWindowRelativeToView(window, _centerRelativeTo);
            }
            window.show();
        } else {
            window.toFront();
        }
    }

    // IWindowEvents
    /**
     *
     * @param _e
     */
    public void windowOpened(WindowOpened _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowClosed(WindowClosed _e) {
        window = null;
        _decline();
    }

    /**
     *
     * @param _e
     */
    public void windowActivated(WindowActivated _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowDeactivated(WindowDeactivated _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowIconified(WindowIconified _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowDeiconified(WindowDeiconified _e) {
    }
}
