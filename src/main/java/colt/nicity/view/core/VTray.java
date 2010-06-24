/*
 * VTray.java.java
 *
 * Created on 01-03-2010 01:31:35 PM
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

import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VTray extends VButton {

    /**
     *
     */
    protected IView open = NullView.cNull;
    /**
     *
     */
    protected IView closed = NullView.cNull;

    /**
     *
     * @param _open
     * @param _closed
     * @param _initial
     */
    public VTray(IView _open, IView _closed, boolean _initial) {
        init(_open, _closed, _initial);
        setBorder(null);
    }

    /**
     *
     * @param _open
     * @param _closed
     * @param _initial
     */
    protected void init(IView _open, IView _closed, boolean _initial) {
        open = _open;
        closed = _closed;
        if (closed == open) {
            throw new RuntimeException(" VTray: open and closed views cannot be the same instance");
        }
        setState(_initial);
    }

    /**
     *
     * @return
     */
    public boolean isTrue() {
        return (placer.getViewable() == open);
    }

    /**
     *
     * @return
     */
    public boolean isFalse() {
        return (placer.getViewable() == closed);
    }

    /**
     *
     * @param _state
     */
    public void setState(boolean _state) {
        if (_state) {
            placer = new Placer(open);
            selectBorder();
        } else {
            placer = new Placer(closed);
            deselectBorder();
        }
        layoutInterior();
        paint();
    }

    public Object getValue() {
        return new Boolean(isTrue());
    }

    public void picked(IEvent _e) {
        setState(!isTrue());
    }

    //  IMouseEvents
    public void mouseEntered(MouseEntered _e) {
        if (isFalse()) {
            setState(true);
        }
    }

    public void mouseExited(MouseExited _e) {
        if (isTrue()) {
            setState(false);
        }
    }
}
