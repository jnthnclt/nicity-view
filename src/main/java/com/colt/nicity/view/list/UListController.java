/*
 * UListController.java.java
 *
 * Created on 01-03-2010 01:34:43 PM
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

import com.colt.nicity.view.value.VToggle;
import com.colt.nicity.core.collection.CArray;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VButton;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.VIcon;
import com.colt.nicity.view.interfaces.IEvent;
import com.colt.nicity.view.interfaces.IListController;
import com.colt.nicity.view.interfaces.ISortable;
import com.colt.nicity.view.interfaces.IVItem;
import com.colt.nicity.view.interfaces.IView;
import java.util.Comparator;

/**
 *
 * @author Administrator
 */
public class UListController {

    /**
     *
     * @param _label
     * @param _sortable
     * @param _comparator
     * @return
     */
    public static IView sortButton(IView _label, final ISortable _sortable, final Comparator _comparator) {
        final VToggle ascendingDescending = new VToggle(VIcon.icon("up"), VIcon.icon("down"), false) {

            public void picked(IEvent _e) {
                sortButtonClicked(this, _sortable, _comparator);
            }
        };
        VButton sort = new VButton(
                new VChain(UV.cEW, _label, ascendingDescending)) {

            public void picked(IEvent _e) {
                sortButtonClicked(ascendingDescending, _sortable, _comparator);
            }
        };
        sort.spans(UV.cXEW);
        return sort;
    }

    private static void sortButtonClicked(VToggle ascendingDescending, final ISortable _sortable, final Comparator _comparator) {
       if (_sortable instanceof IListController) {
            ((IListController) _sortable).listModified(null);
        }
    }

    /**
     *
     * @param _controller
     * @param _type
     * @return
     */
    public static Object[] getSelectedValues(IListController _controller, Class _type) {
        if (_type == null) {
            _type = Object.class;
        }
        CArray array = new CArray(_type);
        if (_controller != null) {
            IVItem[] items = _controller.getSelectedItems();
            for (int i = 0; i < items.length; i++) {
                Object value = items[i].getValue();
                if (_type.isInstance(value)) {
                    array.insertLast(value);
                }
            }
        }
        return array.getAll();
    }

    /**
     *
     * @param _controller
     * @param _value
     * @return
     */
    public static IView getVItemWithValue(IListController _controller, Object _value) {
        IVItem[] items = _controller.getItems();
        for (int i = 0; i < items.length; i++) {
            if (_value.equals(items[i].getValue())) {
                return items[i];
            }
        }
        return null;
    }
}
