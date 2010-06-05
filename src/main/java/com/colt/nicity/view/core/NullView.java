/*
 * NullView.java.java
 *
 * Created on 01-03-2010 01:31:36 PM
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
import com.colt.nicity.view.event.AViewEvent;
import com.colt.nicity.core.memory.struct.WH_F;
import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.interfaces.IBorder;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IEvent;
import com.colt.nicity.view.interfaces.IPlacer;
import com.colt.nicity.view.interfaces.IPlacers;
import com.colt.nicity.view.interfaces.IPopup;
import com.colt.nicity.view.interfaces.IRootView;
import com.colt.nicity.view.interfaces.IToolTip;
import com.colt.nicity.view.interfaces.IView;
import java.awt.Cursor;

/**
 *
 * @author Administrator
 */
public class NullView implements IView {

    /**
     *
     */
    public static final NullView cNull = new NullView();

    public IRootView getRootView() {
        return NullRootView.cNull;
    }//??

    public IView getParentView() {
        return NullView.cNull;
    }

    public void setParentView(IView parent) {
    }

    public IView getView() {
        return NullView.cNull;
    }

    public void setView(IView view) {
    }

    public IPlacer getPlacer() {
        return NullPlacer.cNull;
    }

    public void setPlacer(IPlacer placer) {
    }

    public IView getContent() {
        return NullView.cNull;
    }

    public void setContent(IView view) {
    }

    public IPlacers getPlacers() {
        return NullPlacers.cNull;
    }

    public void setPlacers(IPlacers placers) {
    }

    // public void place(IView _child);
    public IView place(IView _child, Place _place) {
        return NullView.cNull;
    }

    public IView place(IView _child, Place _place, Flex _flex) {
        return NullView.cNull;
    }

    public IView place(IView _child, Place _place, Flex _interior, Flex _exterior) {
        return NullView.cNull;
    }

    public void add(IPlacer placer) {
    }

    public IPlacer find(IView view) {
        return NullPlacer.cNull;
    }

    public IPlacer remove(IPlacer placer) {
        return NullPlacer.cNull;
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
        return NullView.cNull;
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

    public void layoutInterior(IView parent, Flex _flex) {
    }

    public void layoutExterior(WH_F size, Flex _flex) {
    }

    public void paint(IView _parent, ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
    }

    public void paintBackground(ICanvas g, int _x, int _y, int _w, int _h) {
    }

    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
    }

    public void paintBorder(ICanvas g, int _x, int _y, int _w, int _h) {
    }

    /**
     *
     * @return
     */
    public Cursor getCursor() {
        return null;
    }

    /**
     *
     * @param _cursor
     */
    public void setRestoreCursor(Cursor _cursor) {
    }

    /**
     *
     * @param _cursor
     */
    public void setCursor(Cursor _cursor) {
    }

    /**
     *
     */
    public void restoreCursor() {
    }

    public void mend() {
    }

    public void repair() {
    }

    public void flush() {
    }

    public void paint() {
    }

    public void scrollTo(float _x, float _y, float _w, float _h) {
    }

    public boolean isVisible(int _x, int _y, int _w, int _h) {
        return false;
    }

    public boolean isVisible() {
        return false;
    }

    // Location
    /**
     *
     * @return
     */
    public XY_I getNE() {
        return new XY_I(0, 0);
    }

    /**
     *
     * @return
     */
    public XY_I getNW() {
        return new XY_I(0, 0);
    }

    /**
     *
     * @return
     */
    public XY_I getSE() {
        return new XY_I(0, 0);
    }

    /**
     *
     * @return
     */
    public XY_I getSW() {
        return new XY_I(0, 0);
    }

    /**
     *
     * @return
     */
    public XY_I getCenter() {
        return new XY_I(0, 0);
    }

    /**
     *
     * @return
     */
    public XY_I getLocation() {
        return new XY_I(0, 0);
    }

    public XY_I getLocationOnScreen() {
        return new XY_I(0, 0);
    }

    public XY_I getLocationInWindow() {
        return new XY_I(0, 0);
    }

    public WH_F getSize() {
        return new WH_F(0, 0);
    }

    /**
     *
     * @return
     */
    public XYWH_I getBounds() {
        return new XYWH_I(0, 0, 0, 0);
    }

    public XYWH_I getEventBounds() {
        return new XYWH_I(0, 0, 0, 0);
    }

    public float getOriginX() {
        return getX();
    }

    public float getOriginY() {
        return getY();
    }

    public float getX() {
        return 0.0f;
    }

    public float getY() {
        return 0.0f;
    }

    public float getW() {
        return 0.0f;
    }

    public float getH() {
        return 0.0f;
    }

    public void setLocation(float x, float y) {
    }

    public void setX(float x) {
    }

    public void setY(float y) {
    }

    // Events
    public IView disbatchEvent(IView _parent, AViewEvent _event) {
        return NullView.cNull;
    }

    public void promoteEvent(IEvent _task) {
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
        return false;
    }

    public boolean isSelected() {
        return false;
    }

    public void selectBorder() {
    }

    public void deselectBorder() {
    }

    public void activateBorder() {
    }

    public void deactivateBorder() {
    }

    public IPopup getPopup() {
        return NullPopup.cNull;
    }

    public void setPopup(IPopup popup) {
    }

    public long getEventMask() {
        return 0;
    }

    public void enableEvents(long tasksToEnable) {
    }

    public void disableEvents(long tasksToDisable) {
    }

    public boolean isEventEnabled(long isMask) {
        return false;
    }

    public Object processEvent(IEvent task) {
        return null;
    }

    public void frame(IView _view, Object _title) {
    }
}
