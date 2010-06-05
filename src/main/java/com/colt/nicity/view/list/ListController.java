/*
 * ListController.java.java
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

import com.colt.nicity.view.border.SolidBorder;
import com.colt.nicity.view.border.ViewBorder;
import com.colt.nicity.view.event.KeyPressed;
import com.colt.nicity.view.event.KeyReleased;
import com.colt.nicity.view.event.KeyTyped;
import com.colt.nicity.core.collection.CArray;
import com.colt.nicity.core.collection.IBackcall;
import com.colt.nicity.core.collection.NullBackcall;
import com.colt.nicity.core.comparator.AValueComparator;
import com.colt.nicity.core.comparator.UValueComparator;
import com.colt.nicity.core.lang.ICallback;
import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.lang.NullOut;
import com.colt.nicity.core.lang.UArray;
import com.colt.nicity.core.observer.AObserver;
import com.colt.nicity.core.observer.Observable;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VString;
import com.colt.nicity.view.core.ViewString;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.IEvent;
import com.colt.nicity.view.interfaces.IKeyEvents;
import com.colt.nicity.view.interfaces.IListController;
import com.colt.nicity.view.interfaces.IObservableSelectionChanges;
import com.colt.nicity.view.interfaces.IVItem;
import com.colt.nicity.view.interfaces.IView;
import com.colt.nicity.view.interfaces.IViewable;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class ListController extends AListController implements IListController, IKeyEvents, IObservableSelectionChanges {

    /**
     *
     */
    protected Comparator comparator = UValueComparator.toString(AValueComparator.cAscending);
    /**
     *
     */
    protected IVItem selectedItem = null;
    /**
     *
     */
    protected IVItem[] items = null;
    /**
     *
     */
    protected IVItem[] rawItems = null;
    /**
     *
     */
    protected FilterString filter = new FilterString("", 72, 200);
    /**
     *
     */
    protected Observable observeSelectionChanges;

    /**
     *
     */
    public ListController() {
        this(NullBackcall.cNull, null);
    }

    /**
     *
     * @param _backcall
     */
    public ListController(IBackcall _backcall) {
        this(_backcall, null);
    }

    /**
     *
     * @param _backcall
     * @param _autoUpdateElapse
     */
    public ListController(IBackcall _backcall, long _autoUpdateElapse) {
        super(_backcall, _autoUpdateElapse);
        observeSelectionChanges = new Observable(this);
    }

    /**
     *
     * @param _backcall
     * @param _comparator
     */
    public ListController(IBackcall _backcall, Comparator _comparator) {
        super(_backcall);
        if (_comparator != null) {
            comparator = _comparator;
        }
        observeSelectionChanges = new Observable(this);
    }

    /**
     *
     * @param _observer
     */
    public void observeSelectionChanges(AObserver _observer) {
        observeSelectionChanges.bind(_observer);
    }

    /**
     *
     * @param _observer
     */
    public void releaseSelectionChanges(AObserver _observer) {
        observeSelectionChanges.release(_observer);
    }

    /**
     *
     * @return
     */
    public FilterString getFilterString() {
        return filter;
    }

    /**
     *
     * @return
     */
    public Comparator getComparator() {
        return comparator;
    }

    /**
     *
     * @param _comparator
     */
    public void setComparator(Comparator _comparator) {
        comparator = _comparator;
    }

    /**
     *
     * @param _list
     * @param _task
     * @return
     */
    public IEvent processEvent(VList _list, IEvent _task) {
        return _task;
    }

    /**
     *
     * @param _value
     * @return
     */
    public IVItem getItem(Object _value) {
        if (_value == null) {
            return null;
        }
        if (items == null) {
            getItems();
        }
        IVItem[] _rawItems = rawItems;
        for (int i = 0; i < _rawItems.length; i++) {
            IVItem item = _rawItems[i];
            if (item.getValue() == _value) {
                return item;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    synchronized public IVItem[] getItems() {

        if (items == null) {
            rawItems = rawItems(NullOut.cNull, backcall);
            releaseItems();
            items = filterItems(NullOut.cNull, rawItems);
        }
        if ((items == null || items.length == 0) && !(filter.toString().equals(""))) {
            Viewer titleViewer = new Viewer(new ViewString(" filter= "));
            titleViewer.setBorder(new SolidBorder(AColor.yellow));

            Viewer filterViewer = new Viewer(new ViewString(filter));
            filterViewer.setBorder(new SolidBorder(AColor.yellow));

            titleViewer.place(filterViewer, UV.cEW);

            Viewer v = new Viewer(titleViewer);
            VItem item = new VItem(v);
            item.setBorder(new ViewBorder());
            item.layoutInterior();
            return new IVItem[]{item};
        }
        return items;
    }

    /**
     *
     * @return
     */
    synchronized public int getCount() {
        return (items == null) ? 0 : items.length;
    }

    /**
     *
     * @return
     */
    public IVItem getSelectedItem() {
        return selectedItem;
    }

    /**
     *
     * @return
     */
    synchronized public IVItem[] getSelectedItems() {
        if (items == null) {
            return new IVItem[0];
        }
        CArray selectedItems = new CArray(IVItem.class);
        for (int i = 0; i < items.length; i++) {
            IVItem item = items[i];
            if (item != null && item.isSelected()) {
                selectedItems.insertLast(item);
            }
        }
        return (IVItem[]) selectedItems.getAll();
    }

    /**
     *
     */
    synchronized public void selectAllItems() {
        if (items == null) {
            return;
        }
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                items[i].selectBorder();
            }
        }
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    /**
     *
     * @return
     */
    public boolean hasMultiSelect() {
        if (items == null) {
            return false;
        }
        int selectedCount = 0;
        for (int i = 0; i < items.length; i++) {
            IVItem item = items[i];
            if (item != null && item.isSelected()) {
                selectedCount++;
                if (selectedCount > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     */
    synchronized public void deselectAllItems() {
        if (items == null) {
            return;
        }
        selectedItem = null;
        for (int i = 0; i < items.length; i++) {
            IVItem item = items[i];
            if (item != null) {
                item.deselectBorder();
            }
        }
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    // Forces singleSelection mode
    /**
     *
     * @param _itemToSelect
     */
    synchronized public void selectOneItem(IVItem _itemToSelect) {
        if (_itemToSelect == null) {
            return;
        }
        deselectAllItems();
        if (selectedItem != null) {
            selectedItem.deselectBorder();
        }
        selectedItem = _itemToSelect;
        selectedItem.selectBorder();
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    /**
     *
     * @param _itemToSelect
     */
    synchronized public void selectItem(IVItem _itemToSelect) {
        if (_itemToSelect == null) {
            return;
        }
        selectedItem = _itemToSelect;
        selectedItem.selectBorder();
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    /**
     *
     * @param _itemToDeselect
     */
    synchronized public void deselectItem(IVItem _itemToDeselect) {
        if (_itemToDeselect == null) {
            return;
        }
        _itemToDeselect.deselectBorder();
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    /**
     *
     * @param _itemToToggle
     */
    synchronized public void toggleItem(IVItem _itemToToggle) {
        if (_itemToToggle == null) {
            return;
        }
        if (_itemToToggle.isSelected()) {
            deselectItem(_itemToToggle);
        } else {
            selectItem(_itemToToggle);
        }

    }
    // Forces multiSelect mode

    /**
     *
     * @param _toItem
     */
    synchronized public void selectFromSelectedItemTo(IVItem _toItem) {
        if (selectedItem == null) {
            selectOneItem(_toItem);
            return;
        }
        if (_toItem == null || items == null || selectedItem == null) {
            return;
        }
        if (selectedItem == _toItem) {
            return;
        }
        int i = 0;
        for (; i < items.length; i++) {
            if (items[i] == _toItem || items[i] == selectedItem) {
                break;
            }
        }

        if (i < items.length) {
            items[i].selectBorder();

        }
        for (i++; i < items.length; i++) {
            if (items[i] == _toItem || items[i] == selectedItem) {
                break;
            }
            items[i].selectBorder();
        }

        if (i < items.length) {
            items[i].selectBorder();

        }
        selectedItem = _toItem; //
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    /**
     *
     * @param _toItem
     */
    synchronized public void deselectFromSelectedItemTo(IVItem _toItem) {
        if (_toItem == null || items == null) {
            return;
        }
        int i = 0;
        for (; i < items.length; i++) {
            if (items[i] == _toItem || items[i] == selectedItem) {
                break;
            }
        }

        if (i < items.length) {
            items[i].deselectBorder();

        }
        for (i++; i < items.length; i++) {
            if (items[i] == _toItem || items[i] == selectedItem) {
                break;
            }
            items[i].deselectBorder();
        }
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    /**
     *
     * @param _itemsToSelect
     */
    public void selectItems(IVItem[] _itemsToSelect) {
        if (_itemsToSelect == null) {
            return;
        }
        for (int i = 0; i < _itemsToSelect.length; i++) {
            IVItem item = _itemsToSelect[i];
            if (item != null) {
                item.selectBorder();
            }
        }
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    /**
     *
     * @param _itemsToDeselect
     */
    public void deselectItems(IVItem[] _itemsToDeselect) {
        if (_itemsToDeselect == null) {
            return;
        }
        for (int i = 0; i < _itemsToDeselect.length; i++) {
            IVItem item = _itemsToDeselect[i];
            if (item != null) {
                item.deselectBorder();
            }
        }
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    /**
     *
     * @param _itemsToToggle
     */
    public void toggleItems(IVItem[] _itemsToToggle) {
        if (_itemsToToggle == null) {
            return;
        }
        for (int i = 0; i < _itemsToToggle.length; i++) {
            IVItem item = _itemsToToggle[i];
            if (item != null) {
                if (item.isSelected()) {
                    item.deselectBorder();
                } else {
                    item.selectBorder();
                }
            }
        }
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    private void releaseItems() {
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                if (items[i] instanceof IView) {
                    ((IView) items[i]).setParentView(null);
                }
            }
        }
        items = null;
    }

    /**
     *
     * @param _backcall
     */
    @Override
    synchronized public void setBackcall(IBackcall _backcall) {
        releaseItems();
        super.setBackcall(_backcall);
    }

    /**
     *
     * @param _
     */
    @Override
    synchronized public void listModified(IOut _) {
        if (_ == null) {
            _ = NullOut.cNull;
        }
        releaseItems();
        rawItems = rawItems(_, backcall);
        items = filterItems(_, rawItems);
        super.listModified(_);
    }

    /**
     *
     * @param _
     */
    @Override
    synchronized public void filterModified(IOut _) {
        if (rawItems != null) {
            releaseItems();
            items = filterItems(_, rawItems);
        }
        super.filterModified(_);
        if (items != null && items.length > 0) {
            items[0].grabFocus(0);//!!who needs to be propagated
        }
        super.listModified(_);
    }

    /**
     *
     * @param _
     * @param _backcall
     * @return
     */
    protected IVItem[] rawItems(IOut _, IBackcall _backcall) {
        final CArray array = new CArray(IVItem.class);
        ICallback callback = new ICallback() {

            public Object callback(Object _value) {
                IVItem vi = vItem(_value);
                if (vi != null) {
                    array.insertLast(vi);
                }
                return _value;
            }
        };
        _backcall.backcall(_,callback);
        return (IVItem[]) array.getAll();
    }

    /**
     *
     * @param _
     * @param _rawItems
     * @return
     */
    protected IVItem[] filterItems(IOut _, IVItem[] _rawItems) {
        if (_rawItems == null) {
            return new IVItem[0];
        }
        if (comparator != null) {
            Arrays.sort(_rawItems, comparator);
        }
        int count = 0;
        int len = _rawItems.length;
        IVItem[] filteredItems = new IVItem[len];
        Pattern pattern = null;
        String regx = "";
        if (filter != null) {
            regx = filter.toString();
        }
        if (regx.length() > 0) {
            try {
                pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
            } catch (Exception x) {
                if (_ != null) {
                    _.out(x);
                }
            }
        }
        for (int i = 0; i < len; i++) {
            if (_ != null) {
                _.out(i, len);
            }
            IVItem next = _rawItems[i];
            IVItem filtered = filter(next, pattern);
            if (filtered == null) {
                continue;
            }
            filteredItems[count++] = filtered;
        }
        if (count < len) {
            filteredItems = (IVItem[]) UArray.trim(filteredItems, new IVItem[count]);
        } else if (count == 0) {
            filteredItems = new IVItem[0];
        }
        return filteredItems;
    }

    // IListController
    /**
     *
     * @param _filter
     */
    public void setFilter(String _filter) {
        filter.setText((_filter == null) ? "" : _filter);
        filterModified(null);//??
    }

    /**
     *
     * @return
     */
    @Override
    public String getFilter() {
        return filter.toString();
    }

    /**
     *
     * @param _value
     * @return
     */
    public IVItem vItem(Object _value) {
        if (list != null) {
            return list.vItem(_value);
        } else {
            if (_value == null) {
                return null;
            }
            if (_value instanceof IVItem) {
                return (IVItem) _value;
            }
            if (_value instanceof IViewable) {
                IView view = ((IViewable) _value).getView();
                if (view instanceof IVItem) {
                    return (IVItem) view;
                }
                return new VItem(view, _value);
            }
            return new VItem(new VString(_value), _value);
        }
    }

    /**
     *
     * @param _instance
     * @param _pattern
     * @return
     */
    public IVItem filter(IVItem _instance, Pattern _pattern) {
        if (_instance == null || filter == null) {
            return null;
        }
        if (_pattern == null) {
            return _instance;
        }
        String b = _instance.toString();
        boolean found = _pattern.matcher(b).find();
        if (found) {
            return _instance;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return (filter == null) ? "" : filter.toString();
    }

    // IKeyEvents
    /**
     *
     * @param e
     */
    public void keyTyped(KeyTyped e) {
    }

    /**
     *
     * @param e
     */
    public void keyReleased(KeyReleased e) {
    }

    /**
     *
     * @param e
     */
    public void keyPressed(KeyPressed e) {
        if (e.isControlDown()) {
            int code = e.getKeyCode();
            switch (code) {
                //case KeyEvent.VK_X: cut
                //case KeyEvent.VK_C: copy
                //case KeyEvent.VK_V: paste

                case KeyEvent.VK_A:
                    selectAllItems();
                    break;
            }
            return;
        }
        if (filter.modify(e)) {
            filterModified(null);
        }
    }

    /**
     *
     * @param _item
     * @return
     */
    public static IListController getListController(IVItem _item) {
        IView parent = _item.getParentView();
        if (!(parent instanceof VList)) {
            return NullListController.cNull;
        }
        VList list = (VList) parent;
        IListController lc = list.getListController();
        return lc;
    }
}
