/*
 * VChain.java.java
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

import colt.nicity.view.interfaces.IVItem;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VChain extends Viewer implements IVItem {

    /**
     *
     */
    protected IView last;
    /**
     *
     */
    protected Place placement;

    /**
     *
     * @param _placement
     * @param _vs
     */
    public VChain(Place _placement, IView... _vs) {
        this(_placement);
        add(_vs);
    }

    /**
     *
     * @param _placement
     */
    public VChain(Place _placement) {
        super();
        if (_placement == null) {
            _placement = UV.cEW;
        }
        placement = _placement;
        setBorder(null);
    }

    @Override
    public String toString() {
        Object v = getValue();
        if (v == null || v == this) {
            return super.toString();
        } else {
            return v.toString();
        }
    }

    public Object getValue() {
        return this;
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public void add(int _w, int _h) {
        add(new RigidBox(_w, _h));
    }

    /**
     *
     * @param _vs
     */
    public void add(IView... _vs) {
        for (IView v : _vs) {
            UV.spans(v, placement);
            add(v, placement, UV.cFGG);
        }
    }

    /**
     *
     * @param _view
     * @param _flex
     */
    public void add(IView _view, Flex _flex) {
        UV.spans(_view, placement);
        add(_view, placement, _flex);
    }

    /**
     *
     * @param _view
     * @param _placement
     */
    public void add(IView _view, Place _placement) {
        add(_view, _placement, UV.cFGG);
    }

    /**
     *
     * @param _view
     * @param _placement
     * @param _flex
     */
    public void add(IView _view, Place _placement, Flex _flex) {
        if (!(_view instanceof AViewer)) {
            _view = new Viewer(_view);
            UV.spans(_view, placement);//??
        } else {
            ((AViewer) _view).setPlacers(new Placers());
        }
        if (last == null) {
            setPlacer(new Placer(_view, new Place(UV.cNWNW, _flex)));
            last = _view;
        } else {
            last.place(_view, _placement, _flex);
            last = _view;
        }
    }
}
