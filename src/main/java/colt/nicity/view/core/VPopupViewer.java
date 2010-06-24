/*
 * VPopupViewer.java.java
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
package colt.nicity.view.core;

import colt.nicity.view.event.AViewEvent;
import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IPlacer;
import colt.nicity.view.interfaces.IPlacers;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IViewable;

/**
 *
 * @author Administrator
 */
public class VPopupViewer extends AViewWHBorder {

    /**
     *
     */
    protected IPlacers placers = new Placers();
    /**
     *
     */
    protected IPlacer placer = NullPlacer.cNull;

    /**
     *
     * @param _view
     */
    public VPopupViewer(IView _view) {
        setPlacer(new Placer(_view, UV.cOrigin));
    }

    /**
     *
     * @param _relativeTo
     * @param _popup
     * @param _place
     */
    public void popup(IView _relativeTo, IView _popup, Place _place) {
        XY_I liv = UV.getLocationInView(this, _relativeTo);
        add(new Placer(_popup, new Place(UV.cOrigin, liv.x, liv.y)));
    }

    @Override
    public IPlacers getPlacers() {
        return placers;
    }

    @Override
    public void setPlacers(IPlacers _placers) {
        if (placers == null) {
            placers.clear();
        } else {
            placers = _placers;
        }
    }

    @Override
    public void add(IPlacer placer) {
        placers.addPlacer(placer);
    }

    @Override
    public IPlacer find(IView view) {
        if (view.getParentView() == parent) {
            return placers.findView(view);
        }
        return view.find(view);
    }

    @Override
    public IPlacer remove(IPlacer placer) {
        return placers.removePlacer(placer);
    }

    @Override
    public void layoutExterior(WH_F _size, Flex _flex) {
        placers.placeInside(parent, this, _size, _flex);
    }

    @Override
    public IPlacer getPlacer() {
        return placer.getPlacer();
    }

    @Override
    public void setPlacer(IPlacer _placer) {
        if (placer != null) {
            IViewable viewable = placer.getViewable();
            if (viewable instanceof IView) {
                ((IView) viewable).setParentView(NullView.cNull);
            }
        }
        if (_placer == null) {
            placer = NullPlacer.cNull;
        } else {
            placer = _placer;
        }
    }

    @Override
    public IView getContent() {
        return placer.getView();
    }

    @Override
    public void setContent(IView _content) {
        setPlacer(new Placer(_content));
    }

    @Override
    public IView transferFocusToChild(long _who) {
        IView focusedView = placer.transferFocusToChild(_who);
        if (focusedView != NullView.cNull) {
            return focusedView;
        }
        return super.transferFocusToChild(_who);
    }

    /**
     *
     * @param direction
     * @return
     */
    public IView transferFocusToNearestNeighbor(int direction) {
        return NullView.cNull;
    }

    // Layout
    @Override
    public void layoutInterior(IView _parent, Flex _flex) {
        WH_F size = new WH_F(0, 0);
        placer.placeInside(_parent, size, _flex);
        float sw = size.getW();
        float sh = size.getH();
        if (sw > 0) {
            w = sw;
        }
        if (sh > 0) {
            h = sh;
        }
    }

    // Paint
    @Override
    public void paintBody(ICanvas g, Layer _layer, int _mode, XYWH_I _painted) {
        super.paintBody(g, _layer, _mode, _painted);
        placer.paintPlacer(this, g, _layer, _mode, _painted);
        placers.paintPlacers(this, g, _layer, _mode, _painted);
    }

    // Events
    @Override
    public IView disbatchEvent(IView parent, AViewEvent event) {
        IView v = NullView.cNull;//placers.disbatchEvent(this, event);
        if (v != NullView.cNull && v.isEventEnabled(event.getMask())) {
            event.setSource(v);
            return v;
        }
        v = placer.disbatchEvent(this, event);
        if (v != NullView.cNull && v.isEventEnabled(event.getMask())) {
            event.setSource(v);
            return v;
        }
        return super.disbatchEvent(parent, event);
    }
}
