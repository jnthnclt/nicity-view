/*
 * IListController.java.java
 *
 * Created on 01-03-2010 01:32:10 PM
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
package com.colt.nicity.view.interfaces;

import com.colt.nicity.view.list.AVList;
import com.colt.nicity.core.collection.IBackcall;
import com.colt.nicity.core.lang.IOut;
import java.util.Comparator;

/**
 *
 * @author Administrator
 */
public interface IListController extends Cloneable, ISortable {

    /**
     *
     * @return
     */
    public AVList getVList();

    /**
     *
     * @param _list
     */
    public void setVList(AVList _list);

    /**
     *
     * @return
     */
    public IVItem getSelectedItem();

    /**
     *
     * @return
     */
    public IVItem[] getSelectedItems();

    /**
     *
     */
    public void selectAllItems();

    /**
     *
     */
    public void deselectAllItems();

    /**
     *
     * @return
     */
    public boolean hasMultiSelect();

    /**
     *
     * @param _toItem
     */
    public void selectFromSelectedItemTo(IVItem _toItem);

    /**
     *
     * @param _toItem
     */
    public void deselectFromSelectedItemTo(IVItem _toItem);

    /**
     *
     * @param _itemToSelect
     */
    public void selectOneItem(IVItem _itemToSelect);

    /**
     *
     * @param _itemToSelect
     */
    public void selectItem(IVItem _itemToSelect);

    /**
     *
     * @param _itemToDeselect
     */
    public void deselectItem(IVItem _itemToDeselect);

    /**
     *
     * @param _itemToToggle
     */
    public void toggleItem(IVItem _itemToToggle);

    /**
     *
     * @param _itemsToSelect
     */
    public void selectItems(IVItem[] _itemsToSelect);

    /**
     *
     * @param _itemsToDeselect
     */
    public void deselectItems(IVItem[] _itemsToDeselect);

    /**
     *
     * @param _itemsToToggle
     */
    public void toggleItems(IVItem[] _itemsToToggle);

    /**
     *
     * @return
     */
    public IVItem[] getItems();

    /**
     *
     * @return
     */
    public IBackcall getBackcall();

    /**
     *
     * @param _backcall
     */
    public void setBackcall(IBackcall _backcall);

    /**
     *
     * @param _comparator
     */
    public void setComparator(Comparator _comparator);

    /**
     *
     * @param _
     */
    public void listModified(IOut _);

    /**
     *
     * @param _
     */
    public void filterModified(IOut _);

    /**
     *
     * @return
     */
    public String getFilter();

    /**
     *
     * @param _rex
     */
    public void setFilter(String _rex);
}
