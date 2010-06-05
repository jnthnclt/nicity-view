/*
 * VLoose.java.java
 *
 * Created on 01-03-2010 01:32:11 PM
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
package com.colt.nicity.view.list;

import com.colt.nicity.core.collection.IBackcall;
import com.colt.nicity.core.memory.struct.WH_F;
import com.colt.nicity.view.core.Flex;
import com.colt.nicity.view.core.NullView;
import com.colt.nicity.view.interfaces.IListController;
import com.colt.nicity.view.interfaces.IMouseEvents;
import com.colt.nicity.view.interfaces.IMouseMotionEvents;
import com.colt.nicity.view.interfaces.IVItem;
import com.colt.nicity.view.interfaces.IVList;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VLoose extends AVList implements IVList, IMouseEvents, IMouseMotionEvents {

    /**
     *
     */
    protected IListController controller = NullListController.cNull;
    /**
     *
     */
    protected IView empty = NullView.cNull;
    int fixedW = -1;
    int fixedH = -1;

    /**
     *
     */
    public VLoose() {
    }

    /**
     *
     * @param _controller
     */
    public VLoose(IListController _controller) {
        this(NullView.cNull, _controller);
    }

    /**
     *
     * @param _backcall
     */
    public VLoose(IBackcall _backcall) {
        controller = new ListController(_backcall);
        controller.setVList(this);
    }

    /**
     *
     * @param _empty
     * @param _controller
     */
    public VLoose(IView _empty, IListController _controller) {
        empty = (_empty == null) ? NullView.cNull : _empty;
        if (_controller == null) {
            _controller = NullListController.cNull;
        }
        controller = _controller;
        controller.setVList(this);
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public void fixedSize(int _w, int _h) {
        fixedW = _w;
        fixedH = _h;
    }

    /**
     *
     * @param _empty
     */
    public void setEmptyView(IView _empty) {
        empty = (_empty == null) ? NullView.cNull : _empty;
    }

    /**
     *
     * @return
     */
    public IView getEmptyView() {
        return empty;
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

    // Layout
    /**
     *
     * @param _parent
     * @param _flex
     */
    public void layoutInterior(IView _parent, Flex _flex) {

        IView currentParent = parent;
        parent = NullView.cNull;

        IVItem[] items = getItems();

        WH_F size = new WH_F(1, 1);//??1,1 vs 0,0

        int len = (items == null) ? 0 : items.length;
        if (len == 0) {
            IView view = getEmptyView();
            if (view != NullView.cNull && view != null) {
                locate(view, size);
            }
        } else {
            for (int i = 0; i < len; i++) {
                IView view = items[i];
                if (view == null) {
                    continue;
                }
                locate(view, size);
            }
        }

        parent = currentParent;
        if (fixedW == -1) {
            w = (size.getW()) + getBorder().getW();
        } else {
            w = fixedW;
        }
        if (fixedH == -1) {
            h = (size.getH()) + getBorder().getH();
        } else {
            h = fixedH;
        }
    }

    private void locate(IView _view, WH_F _size) {
        WH_F itemSize = new WH_F(0, 0);
        _view.layoutInterior();
        _view.setParentView(this);
        float x = _view.getX();
        float y = _view.getY();
        _view.setLocation(x, y);
        itemSize.max(_view.getW(), _view.getH());
        _size.max(x + itemSize.getW(), y + itemSize.getH());
    }
}

