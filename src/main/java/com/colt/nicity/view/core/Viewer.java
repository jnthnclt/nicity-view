/*
 * Viewer.java.java
 *
 * Created on 01-03-2010 01:31:33 PM
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

import com.colt.nicity.core.memory.struct.WH_F;
import com.colt.nicity.view.interfaces.IBorder;
import com.colt.nicity.view.interfaces.IPlacer;
import com.colt.nicity.view.interfaces.IPlacers;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class Viewer extends AViewer {

    /**
     *
     */
    protected IPlacers placers = new Placers();

    /**
     *
     */
    public Viewer() {
        super();
    }

    /**
     *
     * @param _placer
     */
    public Viewer(IPlacer _placer) {
        super(_placer);
    }

    /**
     *
     * @param _view
     */
    public Viewer(IView _view) {
        super(_view);
    }

    /**
     *
     * @param _view
     * @param _placement
     */
    public Viewer(IView _view, Place _placement) {
        super(_view, _placement);
    }

    /**
     *
     * @param _view
     * @param _flex
     */
    public Viewer(IView _view, Flex _flex) {
        super(_view, _flex);
    }

    /**
     *
     * @param _view
     * @param _border
     * @param _expands
     * @param _flex
     */
    public Viewer(IView _view, IBorder _border, int _expands, Flex _flex) {
        super(_view, _flex);
        if (_border != null) {
            setBorder(_border);
        }
        spans(_expands);
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
}
