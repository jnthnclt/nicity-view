/*
 * WindowPopup.java.java
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

import colt.nicity.view.event.AMouseEvent;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class WindowPopup extends AWindowPopup implements IMouseEvents, IMouseMotionEvents {
    /**
     *
     * @param _reference
     * @param _placePopup
     * @param _view
     * @param _hideOnExit
     * @return
     */
    public static WindowPopup clicked(IView _reference, Place _placePopup, IView _view, boolean _hideOnExit) {
        return clicked(_reference, _placePopup, _view, _hideOnExit, true);
    }
    
    /**
     *
     * @param _reference
     * @param _placePopup
     * @param _view
     * @param _hideOnExit
     * @param _hideOnLost
     * @return
     */
    public static WindowPopup clicked(IView _reference, Place _placePopup, IView _view, boolean _hideOnExit, boolean _hideOnLost) {
        WindowPopup popup = new WindowPopup(_reference, _view, _hideOnExit, _hideOnLost);
        popup.layoutInterior();
        popup.setAutoFocusable(false);
        popup.showPopup(_reference, _placePopup, _view);
        return popup;
    }
    
    /**
     *
     * @param _reference
     * @param _event
     * @param _view
     * @param _hideOnExit
     * @return
     */
    public static WindowPopup clicked(IView _reference, IEvent _event, IView _view, boolean _hideOnExit) {
        WindowPopup popup = new WindowPopup(_reference, _view, _hideOnExit);
        popup.setAutoFocusable(false);
        popup.showPopup(_reference, _event, _view);
        return popup;
    }
    
    /**
     *
     * @param _reference
     * @param _p
     * @param _view
     * @param _hideOnExit
     * @return
     */
    public static WindowPopup clicked(IView _reference, XY_I _p, IView _view, boolean _hideOnExit) {
        WindowPopup popup = new WindowPopup(_reference, _view, _hideOnExit);
        popup.setAutoFocusable(false);
        popup.showPopup(_reference, _p, _view);
        return popup;
    }
    
    /**
     *
     */
    protected IView owner;
    
    /**
     *
     * @param _reference
     * @param _view
     */
    public WindowPopup(IView _reference, IView _view) {
        super(_reference, UV.zones(_view));
    }
    /**
     *
     * @param _reference
     * @param _view
     * @param _hideOnExit
     */
    public WindowPopup(IView _reference, IView _view, boolean _hideOnExit) {
        this(_reference, _view);
        hideOnExit = _hideOnExit;
    }
    /**
     *
     * @param _reference
     * @param _view
     * @param _hideOnExit
     * @param _hideOnLost
     */
    public WindowPopup(IView _reference, IView _view, boolean _hideOnExit, boolean _hideOnLost) {
        this(_reference, _view);
        hideOnExit = _hideOnExit;
        hideOnLost = _hideOnLost;
    }
    
    public void showPopup(XY_I locationOnScreen, IView popupParent) {
        owner = popupParent;
        super.showPopup(locationOnScreen, popupParent);
    }
    
    /**
     *
     * @param _parent
     */
    public void showPopup(IView _parent) {
        showPopup(_parent, UV.cCC, _parent);
    }
    
    /**
     *
     * @param _reference
     * @param _place
     * @param _view
     */
    public void showPopup(IView _reference, Place _place, IView _view) {
        XY_I p = UV.pointRelativeToView(_reference, _place, _view);
        showPopup(p, null);
    }
    
    /**
     *
     * @param _reference
     * @param _e
     * @param _view
     */
    public void showPopup(IView _reference, IEvent _e, IView _view) {
        XY_I p = UV.pointRelativeToView(_reference, UV.cNWC, _view);
        if (_e instanceof AMouseEvent)
        {
            XY_I ep = ((AMouseEvent) _e).getPoint();
            if (p != null)
            {
                p.x += ep.x;
                p.y += ep.y;
            }
        }
        showPopup(p, null);
    }
    
    /**
     *
     * @param _reference
     * @param _p
     * @param _view
     */
    public void showPopup(IView _reference, XY_I _p, IView _view) {
        XY_I p = UV.pointRelativeToView(_reference, UV.cNWC, _view);
        if (p != null)
        {
            p.x += _p.x;
            p.y += _p.y;
        }
        showPopup(p, null);
    }
    
    // IMouseEvents
    
    private XY_I offset;
    public void mouseEntered(MouseEntered e) {
        grabFocus(e.who());
    }
    public void mouseExited(MouseExited e) {
    }
    public void mousePressed(MousePressed e) {
        offset = e.getPoint();
    }
    
    public void mouseReleased(MouseReleased e) {
    }
    
    // IMouseMotionEvents
   
    public void mouseMoved(MouseMoved e) {
    }
    public void mouseDragged(MouseDragged e) {
        if (offset == null) return;
        XY_I p = e.getPoint();
        p.x -= offset.x;
        p.y -= offset.y;


        IView clientView = getRootView();
        if (clientView == null) return;
        XY_I screenPoint = clientView.getLocationOnScreen();
        clientView.setLocation(screenPoint.x + p.x, screenPoint.y + p.y);
    }
}
