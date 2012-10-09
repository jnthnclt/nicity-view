/*
 * VToggle.java.java
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
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.NullView;
import colt.nicity.view.core.Place;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VIcon;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewString;
import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VToggle extends VButton {

    /**
     *
     */
    protected IView trueView = NullView.cNull;
    /**
     *
     */
    protected IView falseView = NullView.cNull;

    /**
     *
     */
    public VToggle() {
        super();
    }

    /**
     *
     * @param _name
     * @param _initial
     */
    public VToggle(Object _name, boolean _initial) {
        this(_name, _initial, UV.cEW);
    }

    /**
     *
     * @param _name
     * @param _initial
     * @param _place
     */
    public VToggle(Object _name, boolean _initial, Place _place) {
        this(
                new VChain(_place, VIcon.icon("checkon24x24"), ((_name instanceof IView) ? (IView) _name : new VString(_name))),
                new VChain(_place, VIcon.icon("checkoff24x24"), ((_name instanceof IView) ? (IView) _name : new VString(_name))),
                _initial, new ViewBorder());
    }

    /**
     *
     * @param _trueName
     * @param _falseName
     * @param _initial
     */
    public VToggle(String _trueName, String _falseName, boolean _initial) {
        this(new ViewString(_trueName), new ViewString(_falseName), _initial);
    }

    /**
     *
     * @param _trueName
     * @param _falseName
     * @param _initial
     * @param _border
     */
    public VToggle(String _trueName, String _falseName, boolean _initial, IBorder _border) {
        this(new ViewString(_trueName), new ViewString(_falseName), _initial, _border);
    }

    /**
     *
     * @param _trueView
     * @param _falseView
     * @param _initial
     */
    public VToggle(IView _trueView, IView _falseView, boolean _initial) {
        init(_trueView, _falseView, _initial);
    }

    /**
     *
     * @param _trueView
     * @param _falseView
     * @param _initial
     * @param _border
     */
    public VToggle(IView _trueView, IView _falseView, boolean _initial, IBorder _border) {
        setBorder(_border);
        init(_trueView, _falseView, _initial);
    }

    /**
     *
     * @param _trueView
     * @param _falseView
     * @param _initial
     * @param _color
     */
    public VToggle(IView _trueView, IView _falseView, boolean _initial, AColor _color) {
        setBorder(new ButtonBorder(_color));
        init(_trueView, _falseView, _initial);
    }

    /**
     *
     * @param _trueView
     * @param _falseView
     * @param _initial
     */
    protected void init(IView _trueView, IView _falseView, boolean _initial) {
        trueView = _trueView;
        falseView = _falseView;
        if (falseView == trueView) {
            throw new RuntimeException(" ToggleBCWutton: true and false views cannot be the same instance");
        }
        setState(_initial);
    }

    /**
     *
     * @return
     */
    public boolean isTrue() {
        return (placer.getViewable() == trueView);
    }

    /**
     *
     * @return
     */
    public boolean isFalse() {
        return (placer.getViewable() == falseView);
    }

    /**
     *
     * @param _state
     */
    public void setState(boolean _state) {
        if (_state) {
            placer = new Placer(trueView);
            selectBorder();
        } else {
            placer = new Placer(falseView);
            deselectBorder();
        }
        paint();
    }

    /**
     *
     * @return
     */
    @Override
    public Object getValue() {
        return new Boolean(isTrue());
    }

    /**
     *
     * @param _e
     */
    @Override
    public void picked(IEvent _e) {
        setState(!isTrue());
    }

    /**
     *
     * @param _toggles
     * @param _clickedToggle
     */
    public static void toggleClicked(VToggle[] _toggles, VToggle _clickedToggle) {
        if (_toggles == null) {
            return;
        }
        for (int t = 0; t < _toggles.length; t++) {
            _toggles[t].setState(_toggles[t] == _clickedToggle);
        }
    }
}
