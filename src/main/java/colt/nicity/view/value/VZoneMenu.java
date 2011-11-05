/*
 * VZoneMenu.java.java
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

import colt.nicity.view.border.PadBorder;
import colt.nicity.view.border.TabBorder;
import colt.nicity.view.border.ZonesBorder;
import colt.nicity.view.event.AViewEvent;
import colt.nicity.view.event.FocusGained;
import colt.nicity.view.event.FocusLost;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.list.VItem;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.NullView;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.VTrapFlex;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IFocusEvents;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.rpp.IRPPViewable;

/**
 *
 * @author Administrator
 */
public class VZoneMenu extends Viewer implements IRPPViewable {
    
    public static IView viewable(String[] args) {
        ViewColor.onGray();
        VZoneMenu zoneMenu = new VZoneMenu(new VString("Hello"), new RigidBox(300, 300), true, true) {

            @Override
            public IView menu() {
                return new VChain(UV.cSWNW, new VItem("A"), new VItem("B"), new VItem("C"), new VItem("D"));
            }
        };
        return zoneMenu;
    }

    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        
        UV.exitFrame(viewable(_args), "Test");
    }

    /**
     *
     * @return
     */
    public IView menu() {
        return new VString("Empty");
    }
    Locate locateMenu;
    Menu menu;
    boolean openOnEnter, closeOnExit;

    /**
     *
     * @param _label
     * @param _zone
     * @param _openOnEnter
     * @param _closeOnExit
     */
    public VZoneMenu(IView _label, IView _zone, boolean _openOnEnter, boolean _closeOnExit) {
        openOnEnter = _openOnEnter;
        closeOnExit = _closeOnExit;
        menu = new Menu();
        locateMenu = new Locate(_label);
        locateMenu.place(new VTrapFlex(menu), UV.cSWNW);
        Viewer zone = new Viewer(_zone) {

            @Override
            public IView disbatchEvent(IView parent, AViewEvent event) {
                IView disbatched = super.disbatchEvent(parent, event);
                if (disbatched != NullView.cNull) {
                    close();
                }
                return disbatched;
            }
        };
        locateMenu.place(zone, UV.cSWNW);
        setContent(locateMenu);
        setBorder(new PadBorder(ViewColor.cTheme, 4, 4));
    }

    /**
     *
     * @param g
     * @param _x
     * @param _y
     * @param w
     * @param h
     */
    @Override
    public void paintBackground(ICanvas g, int _x, int _y, int w, int h) {
        super.paintBackground(g, _x, _y, w, h);
        int sw = (int) menu.getW();
        AColor color = ViewColor.cTheme;
        g.setColor(color.darken(0.15f));
        g.line(_x + 0, _y + 0, _x + 0, _y + h - (1));// Vertical Left
        g.line(_x + sw + 1, _y + 0, _x + w - (1), _y + 0);// Horzontal Top
        g.line(_x + 2, _y + 0, _x + sw + 2, _y + 0);// Horizontal Top

        g.setColor(color.darken(0.2f));
        g.line(_x + 1, _y + 1, _x + 1, _y + h - (2));// Vertical Left
        g.line(_x + sw + 2, _y + _x + 1, w - (2), _y + 1);// Horizontal Top
        g.line(_x + 2, _y + 1, _x + sw + 2, _y + 1);// Horizontal Top

        g.setColor(color.darken(0.05f));
        g.line(_x + 1, _y + h - (1), _x + w - (1), _y + h - (1));
        g.line(_x + w - (1), _y + 1, _x + w - (1), _y + h - (2));

        g.setColor(color.darken(0.1f));
        g.line(_x + 2, _y + h - (2), _x + w - (2), _y + h - (2));
        g.line(_x + w - (2), _y + 2, _x + w - (2), _y + h - (3));
    }

    /**
     *
     */
    public void open() {
        menu.setBorder(new ZonesBorder(ViewColor.cItemTheme, 4));
        menu.setView(menu());
    }

    /**
     *
     */
    public void close() {
        menu.setBorder(null);
        menu.setView(new RigidBox(1, 1));
    }

    class Locate extends Viewer implements IMouseEvents {

        Locate(IView _v) {
            setContent(_v);
            setBorder(new TabBorder(ViewColor.cItemTheme, 4, 0));
        }

        public void mouseEntered(MouseEntered e) {
            if (openOnEnter) {
                open();
            }
        }

        public void mouseExited(MouseExited e) {
        }

        public void mousePressed(MousePressed e) {
            open();
        }

        public void mouseReleased(MouseReleased e) {
        }
    }

    class Menu extends Viewer implements IMouseEvents, IFocusEvents {

        public void mouseEntered(MouseEntered e) {
        }

        public void mouseExited(MouseExited e) {
            XY_I mp = e.getPoint();
            if (mp.x < 0 || mp.y < 0 || mp.x > getW() || mp.y > getH()) {
                if (closeOnExit) {
                    close();
                }
            }
        }

        public void mousePressed(MousePressed e) {
        }

        public void mouseReleased(MouseReleased e) {
        }

        public void focusGained(FocusGained e) {
        }

        public void focusLost(FocusLost e) {
            close();
        }
    }
}
