/*
 * UVItem.java.java
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

import com.colt.nicity.core.collection.CArray;
import com.colt.nicity.core.lang.UArray;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.interfaces.IListController;
import com.colt.nicity.view.interfaces.IVItem;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class UVItem {

    /**
     *
     * @param _item
     * @return
     */
    public static IListController getListController(IVItem _item) {
        //IView parent = _item.getParentView();
        IView parent = UV.findParent(_item, AVList.class);//??
        if (!(parent instanceof AVList)) {
            return null;
        }
        AVList list = (AVList) parent;
        IListController lc = list.getListController();
        return lc;
    }

    /**
     *
     * @param _items
     * @param _type
     * @return
     */
    public static Object[] toValues(Object[] _items, Class _type) {
        if (_items == null) {
            _items = new Object[0];
        }
        if (_type == null) {
            _type = Object.class;
        }
        CArray array = new CArray(_type);
        for (int i = 0; i < _items.length; i++) {
            if (_items[i] instanceof IVItem) {
                Object value = ((IVItem) _items[i]).getValue();
                if (_type.isInstance(value)) {
                    array.insertLast(value);
                }
            }
        }
        return array.getAll();
    }

    /**
     *
     * @param _items
     * @param _class
     * @return
     */
    public static Object[] castSelectedItems(IVItem[] _items, Class _class) {
        CArray array = new CArray(_class);
        for (IVItem t : _items) {
            if (_class.isInstance(t)) {
                array.insertLast(t);
            }
        }
        return array.getAll();
    }

    /**
     *
     * @param _item
     * @return
     */
    public static IVItem[] getSelectedItems(IVItem _item) {
        IVItem[] values = UVItem.getSelected(_item);
        if (values == null || values.length == 0) {
            return new IVItem[]{_item};
        }
        included:
        {
            for (int i = 0; i < values.length; i++) {
                if (values[i] == _item) {
                    break included;
                }
            }
            values = (IVItem[]) UArray.join(values, new IVItem[]{_item}, new IVItem[values.length + 1]);
        }
        return values;
    }

    /**
     *
     * @param _item
     * @return
     */
    public static IVItem[] getSelected(IVItem _item) {
        IView parent = UV.findParent(_item, AVList.class);
        if (!(parent instanceof AVList)) {
            return new IVItem[0];
        }
        AVList list = (AVList) parent;
        IListController lc = list.getListController();
        return lc.getSelectedItems();
    }

    /**
     *
     * @param _item
     * @return
     */
    public static IVItem[] getAll(IVItem _item) {
        IView parent = UV.findParent(_item, AVList.class);
        if (!(parent instanceof AVList)) {
            return new IVItem[0];
        }
        AVList list = (AVList) parent;
        IListController lc = list.getListController();
        return lc.getItems();
    }

    /**
     *
     * @param _item
     * @param _type
     * @return
     */
    public static Object[] getSelectedValues(IVItem _item, Class _type) {
        if (_type == null) {
            _type = Object.class;
        }
        return UListController.getSelectedValues(getListController(_item), _type);
    }
}
