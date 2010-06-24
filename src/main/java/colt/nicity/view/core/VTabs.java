/*
 * VTabs.java.java
 *
 * Created on 03-12-2010 06:34:37 PM
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

import colt.nicity.view.border.TabBorder;
import colt.nicity.view.list.VGrid;
import colt.nicity.view.value.VToggle;
import colt.nicity.core.collection.CArray;
import colt.nicity.core.value.IValue;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VTabs extends Viewer {

    IView pan;
    Tab currentTab;
    CArray tabs = new CArray(Tab.class);
    Place placement = UV.cSWNW;

    /**
     *
     */
    public VTabs() {
    }

    /**
     *
     * @param _placement
     */
    public VTabs(Place _placement) {
        placement = _placement;
    }

    /**
     *
     * @param _w
     * @param _h
     * @param _menuSupplement
     */
    public void init(int _w, int _h, IView _menuSupplement) {
        init(_w, _h, _menuSupplement, 1);
    }

    /**
     *
     * @param _w
     * @param _h
     * @param _menuSupplement
     * @param _grid
     */
    public void init(int _w, int _h, IView _menuSupplement, int _grid) {
        if (_w == -1 && _h == -1) {
            pan = new Viewer(new VString(""));
        } else {
            pan = new VPan(new VString(""), _w, _h, true);
        }
        if (currentTab != null) {
            currentTab.setState(true);
            currentTab.picked(null);
        }
        VChain c = new VChain(placement.tangent());
        c.add(menu(_menuSupplement, _grid));
        c.add(pan);
        setPlacer(new Placer(c));
    }

    /**
     *
     * @param _menuSupplement
     * @param _grid
     * @return
     */
    public IView menu(IView _menuSupplement, int _grid) {
        VGrid list = new VGrid(tabs, ((placement.isVertical()) ? -_grid : _grid));
        VChain c = new VChain(placement);
        if (_menuSupplement != null) {
            c.add(_menuSupplement);
        }
        c.add(list);
        return c;
    }

    /**
     *
     * @param _true
     * @param _false
     * @param _view
     * @return
     */
    public VToggle addTab(IView _true, IView _false, IValue _view) {
        return addTab(_true, _false, _view, false);
    }

    /**
     *
     * @param _true
     * @param _false
     * @param _view
     * @param _current
     * @return
     */
    public VToggle addTab(IView _true, IView _false, IValue _view, boolean _current) {
        Tab tab = new Tab(_true, _false, _view);
        if (placement.isVertical()) {
            tab.spans(UV.cXEW);//??
        }
        tabs.insertLast(tab);
        if (_current) {
            currentTab = tab;
        }
        return tab;
    }

    /**
     *
     * @param _tab
     */
    public void addTab(VToggle _tab) {
        tabs.insertLast(_tab);
    }

    /**
     *
     * @param _tab
     */
    public void removeTab(VToggle _tab) {
        int index = tabs.getIndex(_tab);
        if (index != -1) {
            tabs.removeAt(index);
        }
    }

    class Tab extends VToggle {

        IValue view;

        public Tab(IView _true, IView _false, IValue _view) {
            super(_true, _false, false, new TabBorder(ViewColor.cTheme, 2, 2));
            view = _view;
            picked(null);

        }

        public Object hashObject() {
            return this;
        }

        public Object getValue() {
            if (isFalse()) {
                return NullView.cNull;
            }
            return view.getValue();
        }

        public void picked(IEvent _e) {
            boolean wasTrue = isTrue();
            super.picked(_e);
            if (pan != null) {
                if (wasTrue) {
                    pan.setView(new RigidBox(1, 1));
                } else {
                    pan.setView((IView) view.getValue());
                }
            }
            Tab[] all = (Tab[]) tabs.getAll();
            for (int a = 0; a < all.length; a++) {
                if (all[a] != this) {
                    all[a].setState(false);
                }
            }
        }
    }
}
