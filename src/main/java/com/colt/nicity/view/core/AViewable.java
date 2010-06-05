/*
 * AViewable.java.java
 *
 * Created on 01-03-2010 01:31:34 PM
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

import com.colt.nicity.view.border.NullBorder;
import com.colt.nicity.view.border.UBorder;
import com.colt.nicity.view.event.AViewEvent;
import com.colt.nicity.view.event.UEvent;
import com.colt.nicity.core.lang.ASetObject;
import com.colt.nicity.core.memory.struct.WH_F;
import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.interfaces.IBorder;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IEvent;
import com.colt.nicity.view.interfaces.IFocusEvents;
import com.colt.nicity.view.interfaces.IKeyEvents;
import com.colt.nicity.view.interfaces.IMouseEvents;
import com.colt.nicity.view.interfaces.IMouseMotionEvents;
import com.colt.nicity.view.interfaces.IPlacer;
import com.colt.nicity.view.interfaces.IPlacers;
import com.colt.nicity.view.interfaces.IPopup;
import com.colt.nicity.view.interfaces.IRootView;
import com.colt.nicity.view.interfaces.IToolTip;
import com.colt.nicity.view.interfaces.IView;
import com.colt.nicity.view.interfaces.IWindowEvents;

/**
 *
 * @author Administrator
 */
public abstract class AViewable extends ASetObject implements IView {

    /**
     *
     */
    public AViewable() {
    }

    /**
     *
     * @return
     */
    public Object hashObject() {
        return this;
    }

    public IRootView getRootView() {
        return getParentView().getRootView();
    }

    public IView getParentView() {
        return NullView.cNull;
    }

    public void setParentView(IView parent) {
    }

    public IView getView() {
        return this;
    }

    public void setView(IView view) {
        throw new RuntimeException("Unsupported");
    }

    public IView getContent() {
        return this;
    }

    public void setContent(IView view) {
    }

    public IPlacer getPlacer() {
        return NullPlacer.cNull;
    }

    public void setPlacer(IPlacer placer) {
    }

    public IPlacers getPlacers() {
        return NullPlacers.cNull;
    }

    public void setPlacers(IPlacers placers) {
        throw new RuntimeException("Unsupported");
    }

    // public void place(IView _child);
    public IView place(IView _child, Place _place) {
        throw new RuntimeException("Unsupported");
    }

    public IView place(IView _child, Place _place, Flex _flex) {
        throw new RuntimeException("Unsupported");
    }

    public IView place(IView _child, Place _place, Flex _interior, Flex _exterior) {
        throw new RuntimeException("Unsupported");
    }

    public void add(IPlacer placer) {
        throw new RuntimeException("Unsupported");
    }

    public IPlacer find(IView view) {
        throw new RuntimeException("Unsupported");
    }

    public IPlacer remove(IPlacer placer) {
        throw new RuntimeException("Unsupported");
    }

    public void grabFocus(long _who) {
    }

    public void grabHardFocus(long _who) {
    }

    public void releaseHardFocus(long _who) {
    }

    public IView transferFocusToParent(long _who) {
        return NullView.cNull;
    }

    public IView transferFocusToChild(long _who) {
        return NullView.cNull;
    }

    public IView transferFocusToNearestNeighbor(long _who, int direction) {
        return NullView.cNull;
    }

    public IView spans(int spanMasks) {
        return this;
    }

    public void unspans(int spanMasks) {
    }

    public void disableFlag(int flagMask) {
    }

    public void enableFlag(int flagsToEnable) {
    }

    public boolean hasFlag(int flag) {
        return false;
    }

    public void layoutInterior() {
    }

    public void layoutAllInterior() {
    }

    public void layoutInterior(Flex _flex) {
    }

    public void layoutInterior(IView _parent, Flex _flex) {
    }

    public void layoutExterior(WH_F size, Flex _flex) {
    }

    public void paint(IView _parent, ICanvas g, Layer _layer, int _mode, XYWH_I _painted) {
        int tx = (int) (_layer.tx + _parent.getBorder().getX());
        int ty = (int) (_layer.ty + _parent.getBorder().getY());
        g.translate(tx, ty);
        paintBody(g, _layer, _mode, _painted);
        g.translate(-tx, -ty);
    }

    public void paintBackground(ICanvas g, int _x, int _y, int _w, int _h) {
    }

    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        if (mode == UV.cRepair) {
            _painted.union((int) _layer.x(), (int) _layer.y(), (int) _layer.w(), (int) _layer.h());
        }
    }

    public void paintBorder(ICanvas g, int _x, int _y, int _w, int _h) {
    }

    /**
     *
     * @param _parent
     * @param g
     * @param _layer
     * @param _mode
     * @param _painted
     */
    public void paintPlacer(IView _parent, ICanvas g, Layer _layer, int _mode, XYWH_I _painted) {
        int tx = Math.round(_layer.tx + _parent.getBorder().getX());
        int ty = Math.round(_layer.ty + _parent.getBorder().getY());
        g.translate(tx, ty);
        paintBody(g, _layer, _mode, _painted);
        g.translate(-tx, -ty);
    }

    public void mend() {
        getParentView().mend();
    }

    public void repair() {
        getRootView().addToRepaint(this);
    }

    public void flush() {
        getParentView().flush();
    }

    public void paint() {
        repair();
        flush();
    }

    public void scrollTo(float _x, float _y, float _w, float _h) {
        getParentView().scrollTo(_x + getX(), _y + getY(), _w, _h);
    }

    public boolean isVisible(int _x, int _y, int _w, int _h) {
        return true;
    }

    public boolean isVisible() {
        return true;
    }

    public void promoteEvent(IEvent _task) {
        IView parent = getParentView();
        if (parent != null) {
            parent.promoteEvent(_task);
        }
    }

    public void frame(IView _view, Object _title) {
        IView parent = getParentView();
        if (parent != null) {
            parent.frame(_view, _title);
        }
    }

    // Location
    /**
     *
     * @return
     */
    public XY_I getNE() {
        return new XY_I((int) (getX() + getW()), (int) getY());
    }

    /**
     *
     * @return
     */
    public XY_I getNW() {
        return new XY_I((int) getX(), (int) getY());
    }

    /**
     *
     * @return
     */
    public XY_I getSE() {
        return new XY_I((int) (getX() + getW()), (int) (getY() + getH()));
    }

    /**
     *
     * @return
     */
    public XY_I getSW() {
        return new XY_I((int) getX(), (int) (getY() + getH()));
    }

    /**
     *
     * @return
     */
    public XY_I getCenter() {
        return new XY_I((int) getX() + (int) (getW() / 2), (int) getY() + (int) (getH() / 2));
    }

    /**
     *
     * @return
     */
    public XY_I getLocation() {
        return new XY_I((int) getX(), (int) getY());
    }

    public XY_I getLocationOnScreen() {
        XY_I p = getParentView().getLocationOnScreen();
        p = new XY_I(p.x + (int) getOriginX(), p.y + (int) getOriginY());
        return p;
    }

    public XY_I getLocationInWindow() {
        XY_I p = getParentView().getLocationInWindow();
        p = new XY_I(p.x + (int) getOriginX(), p.y + (int) getOriginY());
        return p;
    }

    /**
     *
     * @param _mousePoint
     * @return
     */
    public XY_I getScreenPoint(XY_I _mousePoint) {
        XY_I sp = getLocationOnScreen();
        sp.x += _mousePoint.x;
        sp.y += _mousePoint.y;
        return sp;
    }

    public WH_F getSize() {
        return new WH_F(getW(), getH());
    }

    /**
     *
     * @return
     */
    public XYWH_I getBounds() {
        return new XYWH_I((int) getX(), (int) getY(), (int) getW(), (int) getH());
    }

    public XYWH_I getEventBounds() {
        return new XYWH_I((int) getX(), (int) getY(), (int) getW(), (int) getH());
    }

    public float getOriginX() {
        return getX();
    }

    public float getOriginY() {
        return getY();
    }

    public float getX() {
        return 0;
    }

    public float getY() {
        return 0;
    }

    public float getW() {
        return 0;
    }

    public float getH() {
        return 0;
    }

    public void setLocation(float x, float y) {
    }

    public void setX(float x) {
    }

    public void setY(float y) {
    }

    // Placer
    /**
     *
     * @param _parent
     * @param _size
     * @param _flex
     */
    public void placeInside(IView _parent, WH_F _size, Flex _flex) {
        _size.max(getW(), getH());
    }

    // Events
    public IView disbatchEvent(IView parent, AViewEvent event) {
        if (!isEventEnabled(event.getMask())) {
            return NullView.cNull;
        }
        event.setSource(this);
        return this;
    }

    public IToolTip getToolTip() {
        return NullToolTip.cNull;
    }

    public void setToolTip(IToolTip _toolTip) {
    }

    public IBorder getBorder() {
        return NullBorder.cNull;
    }

    public void setBorder(IBorder border) {
    }

    public boolean isActive() {
        return getBorder().isActive();
    }

    public boolean isSelected() {
        return getBorder().isSelected();
    }

    public void selectBorder() {
        UBorder.selectBorder(this);
    }

    public void deselectBorder() {
        UBorder.deselectBorder(this);
    }

    public void activateBorder() {
        UBorder.activateBorder(this);
    }

    public void deactivateBorder() {
        UBorder.deactivateBorder(this);
    }

    public IPopup getPopup() {
        return NullPopup.cNull;
    }

    public void setPopup(IPopup popup) {
        throw new RuntimeException("Unsupported");
    }

    public long getEventMask() {
        long taskMask = 0;
        if (this instanceof IWindowEvents) {
            taskMask |= AViewEvent.cWindowEvent;
        }
        if (this instanceof IMouseEvents) {
            taskMask |= AViewEvent.cMouseEvent;
        }
        if (this instanceof IKeyEvents) {
            taskMask |= AViewEvent.cKeyEvent;
        }
        if (this instanceof IMouseMotionEvents) {
            taskMask |= AViewEvent.cMouseMotionEvent;
        }
        if (this instanceof IFocusEvents) {
            taskMask |= AViewEvent.cFocusEvent;
        }
        return taskMask;
    }

    public void enableEvents(long tasksToEnable) {
    }

    public void disableEvents(long tasksToDisable) {
    }

    public boolean isEventEnabled(long isMask) {
        return (getEventMask() & isMask) == isMask;
    }

    public Object processEvent(IEvent task) {
        return UEvent.processEvent(this, task);
    }
}
