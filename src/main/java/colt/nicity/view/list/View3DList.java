/*
 * View3DList.java.java
 *
 * Created on 01-03-2010 01:34:24 PM
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
package colt.nicity.view.list;

import colt.nicity.view.event.AViewEvent;
import colt.nicity.view.threeD.Object3D;
import colt.nicity.view.threeD.U3D;
import colt.nicity.core.collection.IBackcall;
import colt.nicity.core.memory.struct.IXYZ;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XYZ_D;
import colt.nicity.view.core.Flex;
import colt.nicity.view.core.Layer;
import colt.nicity.view.core.NullView;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IListController;
import colt.nicity.view.interfaces.IVItem;
import colt.nicity.view.interfaces.IVList;
import colt.nicity.view.interfaces.IView;


/**
 *
 * @author Administrator
 */
public class View3DList extends AVList implements IVList {

    /**
     *
     */
    protected IListController controller = NullListController.cNull;
    /**
     *
     */
    protected IView empty = NullView.cNull;
    private boolean events = true;

    /**
     *
     */
    public View3DList() {
        ;
    }

    /**
     *
     * @param _controller
     * @param _w
     * @param _h
     */
    public View3DList(IListController _controller, float _w, float _h) {
        controller = _controller;
        w = _w;
        h = _h;
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public void setSize(float _w, float _h) {
        w = _w;
        h = _h;
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public void fixedSize(float _w, float _h) {
        w = _w;
        h = _h;
    }

    /**
     *
     * @param _events
     */
    public void setEvents(boolean _events) {
        events = _events;
    }

    /**
     *
     * @param _empty
     */
    public void setEmptyView(IView _empty) {
    }

    /**
     *
     * @return
     */
    public IView getEmptyView() {
        return NullView.cNull;
    }

    /**
     *
     * @return
     */
    public IListController getListController() {
        return controller;
    }

    /**
     *
     * @param _controller
     */
    public void setListController(IListController _controller) {
        if (_controller == null) {
            _controller = NullListController.cNull;
        }
        controller = _controller;
        layoutInterior();//??
        flush();//??
    }

    /**
     *
     * @param _backcall
     */
    public void setBackcall(IBackcall _backcall) {
        getListController().setBackcall(_backcall);
    }

    /**
     *
     * @return
     */
    public IVItem[] getItems() {
        IListController controller = getListController();
        if (controller == NullListController.cNull) {
            return null;
        }
        return controller.getItems();
    }
    // Layout
    Object3D eye = new Object3D(new XYZ_D(0, 0, -500));
    Object3D center = new Object3D(new XYZ_D(0, 0, 0));

    /**
     *
     * @return
     */
    public IView input() {
        return new Input3D(center, new RigidBox(100, 100), this);
    }

    /**
     *
     * @return
     */
    public IView eye() {
        return new Input3D(eye, new RigidBox(100, 100), this);
    }

    /**
     *
     * @param _parent
     * @param _flex
     */
    public void layoutInterior(IView _parent, Flex _flex) {

        IVItem[] items = getItems();
        if (items == null) {
            return;
        }
        /*
        if (center.getCount() != items.length) {
        XYZ[] xyz = new XYZ[items.length];
        for(int i=0;i<xyz.length;i++) xyz[i] = new XYZ(0,0,0);
        U3D.sphere(center,xyz,300);
        }*/
        center.mapPoints(items);
        XYZ_D centerEye = eye.getCenter();
        XYZ_D centerObj = center.getCenter();
        U3D.translate(items, centerObj.x - centerEye.x, centerObj.y - centerEye.y, centerObj.z - centerEye.z);
        U3D.perspective(eye, items, 500);


        //UXYZ.sort(items);

        int len = (items == null) ? 0 : items.length;
        for (int i = 0; i < len; i++) {
            IView view = items[i];
            if (view == null) {
                continue;
            }
            //if (view.hidden()) continue;
            IXYZ xyz = (IXYZ) items[i];
            //if (xyz.z() <= 0) continue;

            synchronized (view) {
                view.setParentView(this);//??
                view.layoutInterior(_flex);
                float _x = (float) xyz.x();
                float _y = (float) xyz.y();

                _x += (view.getW() / 2) + (getW() / 2);
                _y += (view.getH() / 2) + (getH() / 2);
                view.setLocation(_x, _y);
            }
        }

    }

    /**
     *
     * @param _parent
     * @param event
     * @return
     */
    public IView disbatchEvent(IView _parent, AViewEvent event) {
        if (events) {
            IVItem[] items = getItems();
            if (items == null || items.length == 0) {
                getEmptyView().setParentView(this);
                IView v = event.disbatchEvent(this, getEmptyView());
                if (v != NullView.cNull) {
                    return v;
                }
                return super.disbatchEvent(_parent, event);
            }
            // assumes items are in paint order
            for (int i = items.length - 1; i > -1; i--) {
                IView view = items[i];
                if (view == null) {
                    continue;
                }
                IXYZ xyz = (IXYZ) items[i];
                if (xyz.z() <= 0) {
                    continue;
                }
                view.setParentView(this);
                IView v = event.disbatchEvent(this, view);
                if (v != NullView.cNull) {
                    return v;
                }
            }
            return super.disbatchEvent(_parent, event);
        } else {
            return parent;
        }
    }

    /**
     *
     * @param item
     * @param direction
     * @return
     */
    public IView transferFocusToNearestNeighbor(IVItem item, int direction) {
        return NullView.cNull;
    }

    /**
     *
     * @param _who
     * @return
     */
    public IView transferFocusToChild(long _who) {
        IVItem[] items = getItems();
        if (items == null || items.length == 0) {
            return getEmptyView().transferFocusToChild(_who);
        }
        if (items.length > 0) {
            IView view = items[0];
            if (view != null) {
                view.grabFocus(_who);
                return view;
            }
        }
        return NullView.cNull;
    }

    /**
     *
     * @param g
     * @param _layer
     * @param _mode
     * @param _painted
     */
    public void paintBody(ICanvas g, Layer _layer, int _mode, XYWH_I _painted) {
        IVItem[] items = getItems();
        if (items == null || items.length == 0) {
            getEmptyView().paint(this, g, _layer, _mode, _painted);
            getEmptyView().getPlacers().paintPlacers(this, g, _layer, _mode, _painted);
            super.paintBody(g, _layer, _mode, _painted);
            return;
        }
        // assumes items are in paint order
        for (int i = 0; i < items.length; i++) {
            IView view = items[i];
            if (view == null) {
                continue;
            }
            IXYZ xyz = (IXYZ) items[i];
            //if (xyz.z() <= 0) continue;

            float _x = view.getX();
            float _y = view.getY();
            float _w = view.getW();
            float _h = view.getH();

            //if (_x > _layer.tx + _layer.w()) continue;
            //if (_y > _layer.ty + _layer.h()) continue;

            //if (_x+_w < _layer.tx) continue;
            //if (_y+_h < _layer.ty) continue;

            view.paint(this, g, _layer, _mode, _painted);
            view.getPlacers().paintPlacers(this, g, _layer, _mode, _painted);
        }
        super.paintBody(g, _layer, _mode, _painted);
    }
}

