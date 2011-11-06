/*
 * VFrame.java.java
 *
 * Created on 03-12-2010 06:34:13 PM
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

import colt.nicity.view.event.KeyPressed;
import colt.nicity.view.event.KeyReleased;
import colt.nicity.view.event.KeyTyped;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.list.VItem;
import colt.nicity.view.list.VList;
import colt.nicity.view.value.VFontBrowser;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.core.value.Value;
import colt.nicity.view.border.BuldgeBorder;
import colt.nicity.view.border.ButtonBorder;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IKeyEvents;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VFrame extends Viewer implements IKeyEvents, IMouseEvents, IMouseMotionEvents {

    /**
     *
     */
    private XY_I offset;
    private VString title;
    private VPopupViewer popupViewer;

    /**
     *
     * @param _framing
     * @param _name
     */
    public VFrame(IView _framing, Object _name) {
        this(_framing, null, _name, true, true);
    }

    /**
     *
     * @param _framing
     * @param _name
     * @param _canClose
     * @param _canMinimize
     */
    public VFrame(IView _framing, Object _name, boolean _canClose, boolean _canMinimize) {
        this(_framing, null, _name, _canClose, _canMinimize);
    }

    /**
     *
     * @param _framing
     * @param _nameView
     * @param _name
     * @param _canClose
     * @param _canMinimize
     */
    public VFrame(IView _framing, IView _nameView, Object _name, boolean _canClose, boolean _canMinimize) {
        if (_name == null) {
            _name = "";
        }
        title = new VString(_name, UV.fonts[UV.cText], ViewColor.cWindowThemeFont);
        title.setWRange(0, 200);

        VChain buttons = new VChain(UV.cEW);
        if (_canClose) {
            VItem close = new VItem(new VString(" X ", UV.fonts[UV.cText],ViewColor.cWindowThemeFont)) {

                @Override
                public void picked(IEvent _e) {
                    close();
                }
            };
            close.setBorder(new ButtonBorder(ViewColor.cWindowTheme, 2));
            buttons.add(close);
        }
        if (_canMinimize) {
            VItem maximize = new VItem(new VString(" + ", UV.fonts[UV.cText],ViewColor.cWindowThemeFont)) {

                @Override
                public void picked(IEvent _e) {
                    maximize();
                }
            };
            maximize.setBorder(new ButtonBorder(ViewColor.cWindowTheme, 2));
            VItem minimize = new VItem(new VString(" - ", UV.fonts[UV.cText],ViewColor.cWindowThemeFont)) {

                @Override
                public void picked(IEvent _e) {
                    minimize();
                }
            };
            minimize.setBorder(new ButtonBorder(ViewColor.cWindowTheme, 2));
            buttons.add(maximize);
            buttons.add(minimize);
        }
        if (_nameView != null) {
            buttons.add(_nameView);
        }
        buttons.setBorder(null);

        VChain chain = new VChain(UV.cEW);
        chain.add(4, 4);
        chain.add(buttons);
        chain.add(4, 4);
        chain.add(title, UV.cFII);
        Viewer v = new Viewer(chain);
        v.setBorder(new BuldgeBorder(ViewColor.cWindowTheme, 4));

        VChain main = new VChain(UV.cSWNW);
        main.add(v);
        popupViewer = new VPopupViewer(_framing);
        main.add(popupViewer);
        main.setBorder(new ViewBorder());
        setContent(main);
    }

    /**
     *
     * @param _title
     */
    public void setTitle(Object _title) {
        title.setText(_title);
    }

    /**
     *
     */
    public void close() {
        getRootView().dispose();
    }

    /**
     *
     */
    public void maximize() {
        getRootView().maximize();
    }

    /**
     *
     */
    public void minimize() {
        getRootView().iconify();
    }

    // IKeyEvents
    @Override
    public void keyPressed(KeyPressed e) {
    }

    @Override
    public void keyReleased(KeyReleased e) {
    }

    @Override
    public void keyTyped(KeyTyped e) {
    }

    // IMouseEvents
    @Override
    public void mouseEntered(MouseEntered e) {
        grabFocus(e.who());
    }

    @Override
    public void mouseExited(MouseExited e) {
    }

    @Override
    public void mousePressed(MousePressed e) {
        offset = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseReleased e) {
        if (e.getClickCount() == 1 && e.isRightClick()) {

            VChain presets = new VChain(UV.cSWNW);
            presets.add(new VItem(" On White ") {

                @Override
                public void picked(IEvent _e) {
                    ViewColor.onWhite();
                    getRootView().dispose();
                }
            });
            presets.add(new VItem(" On Gray ") {

                @Override
                public void picked(IEvent _e) {
                    ViewColor.onGray();
                    getRootView().dispose();
                }
            });
            presets.add(new VItem(" On Black ") {

                @Override
                public void picked(IEvent _e) {
                    ViewColor.onBlack();
                    getRootView().dispose();
                }
            });
            

            VChain fonts = new VChain(UV.cSWNW);
            for (int i = 0; i < UV.cFontUsageNames.length; i++) {
                final int index = i;
                final Value v = new Value(UV.fonts[index]) {

                    @Override
                    public void setValue(Object _value) {
                        super.setValue(_value);
                        UV.fonts[index] = (AFont) _value;
                    }
                };
                fonts.add(new VItem(new VString(UV.cFontUsageNames[i])) {

                    @Override
                    public void picked(IEvent _e) {
                        UV.popup(this, _e, new VFontBrowser(v), true, true);
                    }
                });
            }

            VChain m = new VChain(UV.cNENW);
            m.add(UV.zone("Presets", new VPan(presets, -1, 300)));
            m.add(UV.zone("Fonts", new VPan(fonts, -1, 300)));
            m.add(UV.zone("Colors", new VPan(ViewColor.edit(), -1, 300)));
            m.add(UV.zone("Missing  Icons", new VPan(new VList(VIcon.missing, 1), -1, 300)));

            VPopupViewer pv = new VPopupViewer(m);
            Viewer wrap = new Viewer(pv);
            popupViewer.popup(wrap, wrap, new XY_I(0, 0), true, true);
        }
    }

    // IMouseMotionEvents
    @Override
    public void mouseMoved(MouseMoved e) {
    }

    @Override
    public void mouseDragged(MouseDragged e) {
        if (offset == null) {
            return;
        }
        XY_I p = e.getPoint();
        p.x -= offset.x;
        p.y -= offset.y;


        IView clientView = getRootView();
        if (clientView == null) {
            return;
        }
        XY_I screenPoint = clientView.getLocationOnScreen();
        clientView.setLocation(screenPoint.x + p.x, screenPoint.y + p.y);
    }
}
