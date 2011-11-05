/*
 * IView.java.java
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

import colt.nicity.view.event.AViewEvent;
import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.core.Flex;
import colt.nicity.view.core.Layer;
import colt.nicity.view.core.Place;

/**
 *
 * @author Administrator
 */
public interface IView extends IViewable {

    /**
     *
     * @return
     */
    public IRootView getRootView();

    /**
     *
     * @return
     */
    public IView getParentView();

    /**
     *
     * @param parent
     */
    public void setParentView(IView parent);

    /**
     *
     * @param view
     */
    public void setView(IView view);

    /**
     *
     * @return
     */
    public IView getContent();

    /**
     *
     * @param view
     */
    public void setContent(IView view);

    /**
     *
     * @return
     */
    public IPlacer getPlacer();

    /**
     *
     * @param placer
     */
    public void setPlacer(IPlacer placer);

    /**
     *
     * @return
     */
    public IPlacers getPlacers();

    /**
     *
     * @param placers
     */
    public void setPlacers(IPlacers placers);

    // place should return _child
    /**
     *
     * @param _child
     * @param _place
     * @return
     */
    public IView place(IView _child, Place _place);

    /**
     *
     * @param _child
     * @param _place
     * @param _flex
     * @return
     */
    public IView place(IView _child, Place _place, Flex _flex);

    /**
     *
     * @param _child
     * @param _place
     * @param _interior
     * @param _exterior
     * @return
     */
    public IView place(IView _child, Place _place, Flex _interior, Flex _exterior);

    /**
     *
     * @param placer
     */
    public void add(IPlacer placer);

    /**
     *
     * @param view
     * @return
     */
    public IPlacer find(IView view);

    /**
     *
     * @param placer
     * @return
     */
    public IPlacer remove(IPlacer placer);

    /**
     *
     * @param _who
     */
    public void grabFocus(long _who);

    /**
     *
     * @param _who
     */
    public void grabHardFocus(long _who);

    /**
     *
     * @param _who
     */
    public void releaseHardFocus(long _who);

    /**
     *
     * @param _who
     * @return
     */
    public IView transferFocusToParent(long _who);

    /**
     *
     * @param _who
     * @return
     */
    public IView transferFocusToChild(long _who);

    /**
     *
     * @param _who
     * @param direction
     * @return
     */
    public IView transferFocusToNearestNeighbor(long _who, int direction);

    /**
     *
     * @param spanMasks
     * @return
     */
    public IView spans(int spanMasks);

    /**
     *
     * @param spanMasks
     */
    public void unspans(int spanMasks);

    /**
     *
     * @param flagMask
     */
    public void disableFlag(int flagMask);

    /**
     *
     * @param flagMask
     */
    public void enableFlag(int flagMask);

    /**
     *
     * @param flag
     * @return
     */
    public boolean hasFlag(int flag);

    /**
     *
     */
    public void layoutInterior();

    /**
     *
     */
    public void layoutAllInterior();

    /**
     *
     * @param _flex
     */
    public void layoutInterior(Flex _flex);

    /**
     *
     * @param parent
     * @param _flex
     */
    public void layoutInterior(IView parent, Flex _flex);

    /**
     *
     * @param size
     * @param _flex
     */
    public void layoutExterior(WH_F size, Flex _flex);

    /**
     *
     * @param _parent
     * @param g
     * @param _layer
     * @param mode
     * @param _painted
     */
    public void paint(IView _parent, ICanvas g, Layer _layer, int mode, XYWH_I _painted);

    /**
     *
     * @param g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public void paintBackground(ICanvas g, int _x, int _y, int _w, int _h);

    /**
     *
     * @param g
     * @param _layer
     * @param mode
     * @param _painted
     */
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted);

    /**
     *
     * @param g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public void paintBorder(ICanvas g, int _x, int _y, int _w, int _h);

    /**
     *
     */
    public void mend();

    /**
     *
     */
    public void repair();

    /**
     *
     */
    public void flush();

    /**
     *
     */
    public void paint();

    /**
     *
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public void scrollTo(float _x, float _y, float _w, float _h);

    /**
     *
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @return
     */
    public boolean isVisible(int _x, int _y, int _w, int _h);

    /**
     *
     * @return
     */
    public boolean isVisible();

    // Location
    /**
     *
     * @return
     */
    public XY_I getLocationOnScreen();

    /**
     *
     * @return
     */
    public XY_I getLocationInWindow();

    /**
     *
     * @return
     */
    public WH_F getSize();

    /**
     *
     * @return
     */
    public XYWH_I getEventBounds();

    /**
     *
     * @return
     */
    public float getOriginX();

    /**
     *
     * @return
     */
    public float getOriginY();

    /**
     *
     * @return
     */
    public float getX();

    /**
     *
     * @return
     */
    public float getY();

    /**
     *
     * @return
     */
    public float getW();

    /**
     *
     * @return
     */
    public float getH();

    /**
     *
     * @param x
     * @param y
     */
    public void setLocation(float x, float y);

    /**
     *
     * @param x
     */
    public void setX(float x);

    /**
     *
     * @param y
     */
    public void setY(float y);

    // Events
    /**
     *
     * @param _parent
     * @param _event
     * @return
     */
    public IView disbatchEvent(IView _parent, AViewEvent _event);

    /**
     *
     * @param _task
     */
    public void promoteEvent(IEvent _task);

    /**
     *
     * @return
     */
    public long getEventMask();

    /**
     *
     * @param tasksToEnable
     */
    public void enableEvents(long tasksToEnable);

    /**
     *
     * @param tasksToDisable
     */
    public void disableEvents(long tasksToDisable);

    /**
     *
     * @param isMask
     * @return
     */
    public boolean isEventEnabled(long isMask);

    /**
     *
     * @param _task
     * @return
     */
    public Object processEvent(IEvent _task);

    /**
     *
     * @return
     */
    public IToolTip getToolTip();

    /**
     *
     * @param _toolTip
     */
    public void setToolTip(IToolTip _toolTip);

    /**
     *
     * @return
     */
    public IBorder getBorder();

    /**
     *
     * @param border
     */
    public void setBorder(IBorder border);

    /**
     *
     * @return
     */
    public boolean isActive();

    /**
     *
     * @return
     */
    public boolean isSelected();

    /**
     *
     */
    public void selectBorder();

    /**
     *
     */
    public void deselectBorder();

    /**
     *
     */
    public void activateBorder();

    /**
     *
     */
    public void deactivateBorder(IView view);

    /**
     *
     * @return
     */
    public IPopup getPopup();// IView

    /**
     *
     * @param popup
     */
    public void setPopup(IPopup popup); //IView

    /**
     *
     * @param _view
     * @param _title
     */
    public void frame(IView _view, Object _title);
}
