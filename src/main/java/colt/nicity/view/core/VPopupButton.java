/*
 * VPopupButton.java.java
 *
 * Created on 03-12-2010 06:34:21 PM
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
import colt.nicity.core.value.IValue;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IViewable;

/**
 *
 * @author Administrator
 */
public class VPopupButton extends VButton {

    Object popupView;
    Place placePopup = UV.cSWNW;
    boolean showOnEnter = false;
    boolean hideOnExit = false;

    /**
     *
     * @param _view
     * @param _popupView
     */
    public VPopupButton(IView _view, Object _popupView) {
        this(_view, _popupView, false);
    }

    /**
     *
     * @param _view
     * @param _popupView
     * @param _hideOnExit
     */
    public VPopupButton(IView _view, Object _popupView, boolean _hideOnExit) {
        super(_view);
        hideOnExit = _hideOnExit;
        popupView = _popupView;
    }

    /**
     *
     * @param _view
     * @param _popupView
     * @param _showOnEnter
     * @param _hideOnExit
     */
    public VPopupButton(IView _view, Object _popupView, boolean _showOnEnter, boolean _hideOnExit) {
        super(_view);
        showOnEnter = _showOnEnter;
        hideOnExit = _hideOnExit;
        popupView = _popupView;
    }

    /**
     *
     * @param _view
     * @param _placePopup
     * @param _popupView
     * @param _showOnEnter
     * @param _hideOnExit
     */
    public VPopupButton(IView _view, Place _placePopup, Object _popupView, boolean _showOnEnter, boolean _hideOnExit) {
        super(_view);
        placePopup = _placePopup;
        popupView = _popupView;
        showOnEnter = _showOnEnter;
        hideOnExit = _hideOnExit;
    }

    /**
     *
     * @param _placePopup
     */
    public void setPlacePopup(Place _placePopup) {
        placePopup = _placePopup;
    }
    WindowPopup popupedUp;

    @Override
    public void picked(IEvent _e) {
        if (popupedUp != null && popupedUp.isVisible()) {
            return;
        }
        IView v = null;
        if (popupView instanceof IView) {
            v = (IView) popupView;
        } else if (popupView instanceof IValue) {
            v = (IView) ((IValue) popupView).getValue();
        } else if (popupView instanceof IViewable) {
            v = ((IViewable) popupView).getView();
        } else {
            v = new VString("Error :" + popupView.getClass());
        }
        popupedUp = UV.popup(this, placePopup, wrapper(v), hideOnExit);
    }

    /**
     *
     * @param _view
     * @return
     */
    public static IView wrapper(IView _view) {
        return new Viewer(_view);
    }

    @Override
    public void mouseEntered(MouseEntered _e) {
        super.mouseEntered(_e);
        if (showOnEnter) {
            picked(_e);
        }
    }
}
