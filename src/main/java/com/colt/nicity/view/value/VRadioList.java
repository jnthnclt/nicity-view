/*
 * VRadioList.java.java
 *
 * Created on 03-12-2010 06:38:30 PM
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
package com.colt.nicity.view.value;

import com.colt.nicity.view.border.ItemBorder;
import com.colt.nicity.core.collection.CArray;
import com.colt.nicity.core.value.IValue;
import com.colt.nicity.view.core.Place;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.VIcon;
import com.colt.nicity.view.core.VString;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.IEvent;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VRadioList extends Viewer {

    /**
     *
     */
    public VRadioList() {
        super();
    }
    private VChain c;
    private Object current;
    private CArray toggles = new CArray();
    private IValue changed;

    /**
     *
     * @param _place
     * @param _changed
     */
    public VRadioList(Place _place, IValue _changed) {
        c = new VChain(_place);
        changed = _changed;
        setContent(c);
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        if (current == null) {
            return "";
        }
        return current;
    }

    /**
     *
     * @param _current
     */
    public void setValue(Object _current) {
        Object[] all = toggles.getAll();
        for (Object a : all) {
            if (((VToggle) a).getValue() == _current) {
                ((VToggle) a).picked(null);
            }
        }
    }

    /**
     *
     * @param _view
     */
    public void add(IView _view) {
        c.add(_view);
    }

    /**
     *
     * @param _name
     * @param _payload
     * @param _initial
     * @return
     */
    public VToggle add(Object _name, final Object _payload, boolean _initial) {
        return add(_name, _payload, _initial, UV.cEW);
    }

    /**
     *
     * @param _name
     * @param _payload
     * @param _initial
     * @param _place
     * @return
     */
    public VToggle add(Object _name, final Object _payload, boolean _initial, Place _place) {
        if (current != null) {
            _initial = false;
        } else if (_initial == true) {
            current = _payload;
        }
        IView t = new VChain(_place, VIcon.icon("radioOn"), ((_name instanceof IView) ? (IView) _name : new VString(_name)));
        IView f = new VChain(_place, VIcon.icon("radioOff"), ((_name instanceof IView) ? (IView) _name : new VString(_name)));


        VToggle toggle = new VToggle(t, f, _initial, new ItemBorder(ViewColor.cItemTheme, 4, 1)) {

            @Override
            public void picked(IEvent _e) {
                super.picked(_e);
                for (Object a : toggles.getAll()) {
                    if (a != this) {
                        ((VToggle) a).setState(false);
                    } else {
                        ((VToggle) a).setState(true);
                    }
                }
                current = _payload;
                if (changed != null) {
                    changed.setValue(_payload);
                }
            }

            @Override
            public Object getValue() {
                return _payload;
            }
        };
        toggles.insertLast(toggle);
        c.add(toggle);
        return toggle;
    }
}
