/*
 * NullRootView.java.java
 *
 * Created on 01-03-2010 01:31:37 PM
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
import colt.nicity.view.interfaces.IPeerView;
import colt.nicity.view.interfaces.IRootView;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class NullRootView extends NullView implements IRootView {

    /**
     *
     */
    public static final IRootView cNull = new NullRootView();

    public IPeerView getPeerView() {
        return null;
    }

    public void setMouseWheelFocus(long _who, IView _mouseWheelFocus) {
    }

    public IView getFocusedView(long _who) {
        return NullView.cNull;
    }

    public void setFocusedView(long _who, IView view) {
    }

    public IView getHardFocusedView(long _who) {
        return NullView.cNull;
    }

    public void setHardFocusedView(long _who, IView view) {
    }

    public void processEvent(IOut _,PrimativeEvent event) {
    }

    public void toFront() {
    }

    public boolean isValid() {
        return false;
    }

    public void dispose() {
    }

    public void addToRepaint(IView _view) {
    }

    /**
     *
     * @param _modal
     * @return
     */
    public IRootView aquireModal(IRootView _modal) {
        return this;
    }

    /**
     *
     * @param _modal
     * @return
     */
    public IRootView releaseModal(IRootView _modal) {
        return this;
    }
}
