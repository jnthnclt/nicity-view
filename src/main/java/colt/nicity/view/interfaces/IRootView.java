/*
 * IRootView.java.java
 *
 * Created on 01-03-2010 01:31:40 PM
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
package colt.nicity.view.interfaces;

import colt.nicity.core.lang.IOut;
import colt.nicity.view.core.PrimativeEvent;

/**
 *
 * @author Administrator
 */
public interface IRootView extends IView {

    /**
     *
     * @return
     */
    public IPeerView getPeerView();

    /**
     *
     * @param _who
     * @return
     */
    public IView getFocusedView(long _who);

    /**
     *
     * @param _who
     * @param view
     */
    public void setFocusedView(long _who, IView view);

    /**
     *
     * @param _who
     * @return
     */
    public IView getHardFocusedView(long _who);

    /**
     *
     * @param _who
     * @param view
     */
    public void setHardFocusedView(long _who, IView view);

    /**
     *
     * @param _who
     * @param _mouseWheelFocus
     */
    public void setMouseWheelFocus(long _who, IView _mouseWheelFocus);

    /**
     *
     * @param _view
     */
    public void addToRepaint(IView _view);

    /**
     *
     * @param _
     * @param event
     */
    public void processEvent(IOut _, PrimativeEvent event);

    /**
     *
     */
    public void toFront();

    /**
     *
     * @return
     */
    public boolean isValid();

    /**
     *
     */
    public void flush();

    /**
     *
     */
    public void dispose();
}
