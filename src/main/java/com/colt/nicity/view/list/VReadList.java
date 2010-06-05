/*
 * VReadList.java.java
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

import com.colt.nicity.view.awt.UAWT;
import com.colt.nicity.core.memory.struct.WH_F;
import com.colt.nicity.view.core.Flex;
import com.colt.nicity.view.core.NullView;
import com.colt.nicity.view.core.ToolTipFactory;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.interfaces.IFocusable;
import com.colt.nicity.view.interfaces.IListController;
import com.colt.nicity.view.interfaces.IToolTip;
import com.colt.nicity.view.interfaces.IVItem;
import com.colt.nicity.view.interfaces.IVList;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VReadList extends AVList implements IVList {

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
    protected Object newLine;

    /**
     *
     */
    public VReadList() {
    }

    /**
     *
     * @param _controller
     * @param _w
     */
    public VReadList(IListController _controller, int _w) {
        this(_controller, -1, newLine(_w));
    }

    /**
     *
     * @param _w
     * @return
     */
    public static Object newLine(final int _w) {
        Object newLine = new Object() {

            int width = 0;

            public boolean equals(Object _instance) {
                if (_instance == null) {
                    width = 0;
                    return false;
                }
                if (_instance instanceof IView) {
                    int w = (int) ((IView) _instance).getW();
                    width += w;
                    if (width + w > 700) {
                        width = 0;
                        return true;
                    }
                }
                return false;
            }
        };
        return newLine;
    }

    /**
     *
     * @param _controller
     * @param _rows_colums
     * @param _newLine
     */
    public VReadList(IListController _controller, int _rows_colums, Object _newLine) {
        this(NullView.cNull, _controller, _rows_colums, _newLine);
    }

    /**
     *
     * @param _empty
     * @param _controller
     * @param _rows_colums
     * @param _newLine
     */
    public VReadList(IView _empty, IListController _controller, int _rows_colums, Object _newLine) {
        newLine = _newLine;
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
        // this allows a newline object to know when layout is called
        // since it is expected that getItems() list should not contain any nulls.
        newLine.equals(null);

        IView currentParent = parent;
        parent = NullView.cNull;

        IVItem[] items = getItems();

        int x = (int) _parent.getBorder().getX();
        int y = (int) _parent.getBorder().getY();

        WH_F size = new WH_F(1, 1);//??1,1 vs 0,0

        if (getRowsColums() != 0) {
            int len = (items == null) ? 0 : items.length;
            if (len == 0) {
                WH_F itemSize = new WH_F(0, 0);
                IView view = getEmptyView();
                if (view != null) {
                    view.setParentView(this);
                    view.layoutInterior(_flex);

                    view.setLocation(x, y);
                    itemSize.max(view.getW(), view.getH());
                    view.layoutExterior(itemSize, _flex);
                    size.max(itemSize.getW(), y + itemSize.getH());
                }
            } else {
                WH_F lineSize = new WH_F(0, 0);
                for (int i = 0; i < len; i++) {
                    WH_F itemSize = new WH_F(0, 0);
                    IView view = items[i];
                    if (view == null) {
                        continue;
                    }

                    view.layoutInterior();
                    view.setParentView(this);

                    view.setLocation(x, y);
                    itemSize.max(view.getW(), view.getH());
                    //view.layoutExterior(itemSize,_flex);//??
                    size.max(x + itemSize.getW(), y + itemSize.getH());
                    lineSize.max(lineSize.getH(), itemSize.getH());
                    x += itemSize.getW();//Horizontal list;



                    if (newLine.equals(items[i])) {
                        //if (newLine.equals(items[i].getValue())) {

                        y += lineSize.getH();
                        x = 0;
                        lineSize = new WH_F(0, 0);
                    }
                }
            }
        }

        parent = currentParent;
        w = (size.getW()) + getBorder().getW();
        h = (size.getH()) + getBorder().getH();
    }
}

