/*
 * VFloater.java.java
 *
 * Created on 03-12-2010 06:33:56 PM
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
package com.colt.nicity.view.core;

import com.colt.nicity.view.event.WindowActivated;
import com.colt.nicity.view.event.WindowClosed;
import com.colt.nicity.view.event.WindowDeactivated;
import com.colt.nicity.view.event.WindowDeiconified;
import com.colt.nicity.view.event.WindowIconified;
import com.colt.nicity.view.event.WindowOpened;
import com.colt.nicity.view.list.VItem;
import com.colt.nicity.core.value.IValue;
import com.colt.nicity.view.interfaces.IEvent;
import com.colt.nicity.view.interfaces.IView;
import com.colt.nicity.view.interfaces.IViewable;
import com.colt.nicity.view.interfaces.IWindowEvents;

/**
 *
 * @author Administrator
 */
public class VFloater extends VItem {

    /**
     *
     */
    protected Floater floater;
    /**
     *
     */
    protected Object floaterView;
    /**
     *
     */
    public String toString = "";

    /**
     *
     * @param _preview
     * @param _content
     */
    public VFloater(String _preview, Object _content) {
        super(_preview);
        toString = _preview;
        floaterView = _content;
    }

    /**
     *
     * @param _preview
     * @param _content
     */
    public VFloater(IView _preview, Object _content) {
        super(new Viewer(_preview));
        toString = _preview.toString();
        floaterView = _content;
    }

    @Override
    public String toString() {
        return toString;
    }

    /**
     *
     * @return
     */
    public IView floaterView() {
        if (floaterView instanceof IView) {
            return (IView) floaterView;
        } else if (floaterView instanceof IValue) {
            return (IView) ((IValue) floaterView).getValue();
        } else if (floaterView instanceof IViewable) {
            return ((IViewable) floaterView).getView();
        }
        return null;
    }

    @Override
    public void picked(IEvent _e) {
        toFront();
    }

    /**
     *
     */
    public void toFront() {
        if (floater == null) {
            floater = new Floater(floaterView());
            floater.toFront(this);
        } else {
            floater.toFront(this);
        }
    }

    /**
     *
     * @return
     */
    public boolean isOpen() {
        return floater != null;
    }

    /**
     *
     */
    public void open() {
        if (floater == null) {
            floater = new Floater(floaterView());
            floater.toFront(this);
        }
    }

    /**
     *
     */
    public void close() {
        if (floater != null) {
            floater.close();
        }
    }

    @Override
    public Object getValue() {
        return this;
    }

    class Floater extends Viewer implements IWindowEvents {

        AWindow window;
        IView content;

        public Floater(IView _content) {
            content = _content;
            setContent(_content);
        }

        public void toFront(IView _centerRelativeTo) {
            AWindow _window = window;
            if (_window == null || _window.closed()) {
                window = UV.frame(this, VFloater.this.toString());
            } else {
                _window.toFront();
            }
        }

        public void close() {
            if (window != null) {
                window.dispose();
            }
            floater = null;
        }

        // IWindowEvents
        public void windowOpened(WindowOpened _e) {
        }

        public void windowClosed(WindowClosed _e) {
            window = null;
            floater = null;
        }

        public void windowActivated(WindowActivated _e) {
        }

        public void windowDeactivated(WindowDeactivated _e) {
        }

        public void windowIconified(WindowIconified _e) {
        }

        public void windowDeiconified(WindowDeiconified _e) {
        }
    }
}
