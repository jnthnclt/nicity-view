/*
 * VList.java.java
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
package colt.nicity.view.list;

import colt.nicity.view.awt.UAWT;
import colt.nicity.core.collection.IBackcall;
import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.view.core.Flex;
import colt.nicity.view.core.NullView;
import colt.nicity.view.core.ToolTipFactory;
import colt.nicity.view.core.UV;
import colt.nicity.view.interfaces.IFocusable;
import colt.nicity.view.interfaces.IListController;
import colt.nicity.view.interfaces.IToolTip;
import colt.nicity.view.interfaces.IVItem;
import colt.nicity.view.interfaces.IVList;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VList extends AVList implements IVList {

    /**
     *
     */
    protected int rows_colums = 0;
    /**
     *
     */
    protected IListController controller = NullListController.cNull;
    /**
     *
     */
    protected IView empty = NullView.cNull;

    /**
     *
     */
    public VList() {
    }

    /**
     *
     * @param _controller
     * @param _rows_colums
     */
    public VList(IListController _controller, int _rows_colums) {
        this(NullView.cNull, _controller, _rows_colums);
    }

    /**
     *
     * @param _empty
     * @param _controller
     * @param _rows_colums
     */
    public VList(IView _empty, IListController _controller, int _rows_colums) {
        empty = (_empty == null) ? NullView.cNull : _empty;
        if (_controller == null) {
            _controller = NullListController.cNull;
        }
        controller = _controller;
        rows_colums = _rows_colums;
        controller.setVList(this);
    }

    /**
     *
     * @param _backcall
     * @param _rows_colums
     */
    public VList(IBackcall _backcall, int _rows_colums) {
        controller = new ListController(_backcall);
        rows_colums = _rows_colums;
        controller.setVList(this);
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

    /**
     *
     * @return
     */
    public int getRowsColums() {
        return rows_colums;
    }

    /**
     *
     * @param _rows_colums
     */
    public void setRowsColums(int _rows_colums) {
        rows_colums = _rows_colums;
    }

    /**
     *
     * @param _who
     */
    public void grabFocus(long _who) {
        transferFocusToChild(_who);
    }

    /**
     *
     * @return
     */
    public IToolTip getToolTip() {
        String count = "count(";
        if (controller.getItems() != null) {
            count += controller.getItems().length;
        }
        count += ")";
        return ToolTipFactory.cDefault.createToolTip(count);
    }

    /**
     *
     * @param _who
     * @param item
     * @param direction
     * @return
     */
    public IView transferFocusToNearestNeighbor(long _who, IVItem item, int direction) {
        IVItem[] items = getItems();
        if (items == null) {
            return NullView.cNull;
        }
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                if (direction == UAWT.cUp || direction == UAWT.cLeft) {
                    if (i - 1 < 0) {
                        break;
                    }
                    items[i - 1].grabFocus(_who);
                    return items[i - 1];
                }
                if (direction == UAWT.cDown || direction == UAWT.cRight) {
                    if (i + 1 == items.length) {
                        break;
                    }
                    items[i + 1].grabFocus(_who);
                    return items[i + 1];
                }
                break;
            }
        }
        IView parent = UV.findParent(this, IFocusable.class);
        parent.grabFocus(_who);
        return parent;
    }

    // Layout
    /**
     *
     * @param _parent
     * @param _flex
     */
    public void layoutInterior(IView _parent, Flex _flex) {
        Object[] items = getItems();

        int x = (int) _parent.getBorder().getX();
        int y = (int) _parent.getBorder().getY();

        WH_F size = new WH_F(0, 0);//??1,1 vs 0,0

        if (getRowsColums() != 0) {
            int len = (items == null) ? 0 : items.length;
            if (len == 0) {
                IView view = getEmptyView();
                if (view != null) {
                    items = new Object[]{view};
                    len = 1;
                }
            }
            for (int i = 0; i < len; i++) {
                WH_F itemSize = new WH_F(0, 0);
                IView v = (IView) items[i];
                if (v == null) {
                    continue;
                }
                if (v instanceof ISkip && ((ISkip) v).skip()) {
                    continue;
                }
                synchronized (v) {
                    v.setParentView(this);
                    v.layoutInterior(_flex);
                    v.setLocation(x, y);
                }
                itemSize.max(v.getW(), v.getH());
                v.layoutExterior(itemSize, _flex);//??
                if (getRowsColums() > 0) {
                    size.max(itemSize.getW(), v.getY() + itemSize.getH());
                    y = (int) (v.getY() + itemSize.getH());//Vertical list;
                } else {
                    size.max(x + itemSize.getW(), itemSize.getH());
                    x += itemSize.getW();//Horizontal list;
                }
            }
        }

        w = (size.getW()) + getBorder().getW();
        h = (size.getH()) + getBorder().getH();
        parent.get().repair();//??2-25-09
    }
}

