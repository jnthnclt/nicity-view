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
import colt.nicity.view.border.LineBorder;
import colt.nicity.view.border.ZoneBorder;
import colt.nicity.view.border.ZonesBorder;
import colt.nicity.view.interfaces.ICanvas;
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
    public static AColor active = null;
    private XY_I offset;
    private VString title;

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
            VItem close = new VItem(new VString(" X ", UV.fonts[UV.cText])) {

                public void picked(IEvent _e) {
                    close();
                }
            };
            close.setBorder(new LineBorder(AColor.red.darken(0.3f).desaturate(0.4f), 1,4,2));
            buttons.add(close);
        }
        if (_canMinimize) {
            VItem maximize = new VItem(new VString(" + ", UV.fonts[UV.cText])) {

                public void picked(IEvent _e) {
                    maximize();
                }
            };
            maximize.setBorder(new LineBorder( AColor.orange.darken(0.3f).desaturate(0.4f),1,4,2));
            VItem minimize = new VItem(new VString(" _ ", UV.fonts[UV.cText] )) {

                public void picked(IEvent _e) {
                    minimize();
                }
            };
            minimize.setBorder(new LineBorder(AColor.yellow.darken(0.3f).desaturate(0.4f), 1,4,2));
            buttons.add(maximize);
            buttons.add(minimize);
        }
        if (_nameView != null) {
            buttons.add(_nameView);
        }
        buttons.setBorder(null);

        VChain chain = new VChain(UV.cEW);
        chain.add(buttons);
        chain.add(title, UV.cFII);
        //chain.setBorder(new LineBorder(ViewColor.cWindowTheme, 2));

        VChain main = new VChain(UV.cSWNW);
        main.add(chain);
        main.add(new Viewer(new VPopupViewer(_framing)));
        setContent(UV.border(main, new ZonesBorder(ViewColor.cTheme, 2)));
    }

    public void paintBorder(ICanvas g, int _x, int _y, int _w, int _h) {
        super.paintBorder(g, _x, _y, _w, _h);
        AColor _active = active;
        if (_active != null) {
            int hh = _h / 2;
            g.paintFlavor(ULAF.cButtonBG, _x + _w - 15, _y + 5, 10, 10, _active);
        }
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
    public void keyPressed(KeyPressed e) {
    }

    public void keyReleased(KeyReleased e) {
    }

    public void keyTyped(KeyTyped e) {
    }

    // IMouseEvents
    public void mouseEntered(MouseEntered e) {
        grabFocus(e.who());
    }

    public void mouseExited(MouseExited e) {
    }

    public void mousePressed(MousePressed e) {
        offset = e.getPoint();
    }

    public void mouseReleased(MouseReleased e) {
        if (e.getClickCount() == 1 && e.isRightClick()) {

            VChain presets = new VChain(UV.cEW);
            presets.add(new VButton(" On Black ") {

                public void picked(IEvent _e) {
                    ViewColor.onBlack();
                    getRootView().dispose();
                }
            });
            presets.add(new VButton(" On Blue ") {

                public void picked(IEvent _e) {
                    ViewColor.onBlue();
                    getRootView().dispose();
                }
            });
            presets.add(new VButton(" On White ") {

                public void picked(IEvent _e) {
                    ViewColor.onWhite();
                    getRootView().dispose();
                }
            });

            VChain fonts = new VChain(UV.cSWNW);
            for (int i = 0; i < UV.cFontUsageNames.length; i++) {
                final int index = i;
                Value v = new Value(UV.fonts[index]) {

                    public void setValue(Object _value) {
                        super.setValue(_value);
                        UV.fonts[index] = (AFont) _value;
                    }
                };
                fonts.add(new VFloater(new VString(UV.cFontUsageNames[i]), new VFontBrowser(v)));
            }

            VChain cs = new VChain(UV.cSN);
            cs.add(presets);
            cs.add(new VPan(ViewColor.edit(), -1, 300), UV.cSN);

            VChain m = new VChain(UV.cNENW);
            m.add(UV.zone("Fonts", new VPan(fonts, -1, 300)));
            m.add(UV.zone("Colors", cs));
            m.add(UV.zone("Missing  Icons", new VPan(new VList(VIcon.missing, 1), -1, 300)));
            UV.frame(m, "Look and Feel");
        }
    }

    // IMouseMotionEvents
    public void mouseMoved(MouseMoved e) {
    }

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

