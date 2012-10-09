/*
 * Placer.java.java
 *
 * Created on 01-03-2010 01:31:39 PM
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

import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.adaptor.IViewEventConstants;
import colt.nicity.view.event.AViewEvent;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IPlacer;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IViewable;

/**
 *
 * @author Administrator
 */
public class Placer extends APlacer implements IPlacer {

    /**
     *
     */
    protected IViewable viewable;
    /**
     *
     */
    protected Place place;

    /**
     *
     */
    public Placer() {
    }

    /**
     *
     * @param _viewable
     */
    public Placer(IViewable _viewable) {
        viewable = _viewable;
        if (viewable == null) {
            viewable = NullView.cNull;
        }
        place = UV.cOrigin;
    }

    /**
     *
     * @param _viewable
     * @param _flex
     */
    public Placer(IViewable _viewable, Flex _flex) {
        viewable = _viewable;
        if (viewable == null) {
            viewable = NullView.cNull;
        }
        place = new Place(UV.cOrigin, _flex);
    }

    /**
     *
     * @param _viewable
     * @param _place
     */
    public Placer(IViewable _viewable, Place _place) {
        viewable = _viewable;
        if (viewable == null) {
            viewable = NullView.cNull;
        }
        place = _place;
    }

    @Override
    public IPlacer getPlacer() {
        return this;
    }

    @Override
    public IViewable getViewable() {
        return viewable;
    }

    @Override
    public void setViewable(IViewable _viewable) {
        viewable = _viewable;
        if (viewable == null) {
            viewable = NullView.cNull;
        }
    }

    @Override
    public Place getPlace() {
        return place;
    }

    @Override
    public void setPlace(Place _place) {
        place = _place;
    }

    @Override
    public void placeInside(IView _parent, WH_F _size, Flex _flex) {
        UPlacer.placeInside(viewable.getView(), place, _parent, _size, _flex);
    }

    @Override
    public void placeInside(IView _parent, IView _anchor, WH_F _size, Flex _flex) {
        IView v = viewable.getView();
        if (v == _anchor) {
            return;//??
        }
        if (_parent == _anchor) {
            return;//??
        }
        UPlacer.placeInside(v, place, _parent, _anchor, _size, _flex);
    }

    @Override
    public void paintPlacer(
            IView _parent, ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        IView view = viewable.getView();
        if (view != _parent) {
            view.paint(_parent, g, _layer, mode, _painted);
        }
        view.getPlacers().paintPlacers(_parent, g, _layer, mode, _painted);
    }

    @Override
    public IView disbatchEvent(IView parent, AViewEvent event) {
        IView view = viewable.getView();
        if (parent != NullView.cNull) {
            view.setParentView(parent);//??
        }
        IView v = view.getPlacers().disbatchEventToPlacers(parent, event);
        if (v != NullView.cNull) {
            return v;
        }
        v = event.disbatchEvent(parent, view);
        return v;
    }

    @Override
    public IView transferFocusToChild(long _who) {
        IView child = viewable.getView();
        if (child.isEventEnabled(IViewEventConstants.cKeyEvent)) {
            child.grabFocus(_who);
            return child;
        } else {
            return child.transferFocusToChild(_who);
        }
    }

    // IViewable
    @Override
    public IView getView() {
        return viewable.getView();
    }
}
