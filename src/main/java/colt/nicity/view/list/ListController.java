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
package colt.nicity.view.list;

import colt.nicity.core.collection.CArray;
import colt.nicity.core.collection.IBackcall;
import colt.nicity.core.collection.NullBackcall;
import colt.nicity.core.comparator.AValueComparator;
import colt.nicity.core.comparator.UValueComparator;
import colt.nicity.core.lang.ICallback;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.lang.NullOut;
import colt.nicity.core.lang.UArray;
import colt.nicity.core.observer.AObserver;
import colt.nicity.core.observer.Observable;
import colt.nicity.view.adaptor.IKeyEventConstants;
import colt.nicity.view.border.SolidBorder;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.ViewString;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.event.KeyPressed;
import colt.nicity.view.event.KeyReleased;
import colt.nicity.view.event.KeyTyped;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IKeyEvents;
import colt.nicity.view.interfaces.IListController;
import colt.nicity.view.interfaces.IObservableSelectionChanges;
import colt.nicity.view.interfaces.IVItem;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IViewable;
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
    @Override
    public IVItem[] getItems() {
        IVItem[] _items = items;
        if (_items == null) {
            IVItem[] _rawItems = rawItems(NullOut.cNull, backcall);
            rawItems = _rawItems;
            releaseItems();
            _items = items = filterItems(NullOut.cNull, _rawItems);
        }
        if ((_items == null || _items.length == 0) && !(filter.toString().equals(""))) {
            Viewer titleViewer = new Viewer(new ViewString(" filter= "));
            titleViewer.setBorder(new SolidBorder(ViewColor.cThemeAccent));

            Viewer filterViewer = new Viewer(new ViewString(filter));
            filterViewer.setBorder(new SolidBorder(ViewColor.cThemeAccent));

            titleViewer.place(filterViewer, UV.cEW);

            Viewer v = new Viewer(titleViewer);
            VItem item = new VItem(v);
            item.setBorder(new ViewBorder());
            item.paint();
            return new IVItem[]{item};
        }
        return _items;
    }

    /**
     *
     * @return
     */
    public int getCount() {
         IVItem[] _items = items;
        return (_items == null) ? 0 : _items.length;
    }

    /**
     *
     * @return
     */
    @Override
    public IVItem getSelectedItem() {
        return selectedItem;
    }

    /**
     *
     * @return
     */
    @Override
    public IVItem[] getSelectedItems() {
         IVItem[] _items = items;
        if (_items == null) {
            return new IVItem[0];
        }
        CArray selectedItems = new CArray(IVItem.class);
        for (int i = 0; i < _items.length; i++) {
            IVItem item = _items[i];
            if (item != null && item.isSelected()) {
                selectedItems.insertLast(item);
            }
        }
        return (IVItem[]) selectedItems.getAll();
    }

    /**
     *
     */
    @Override
    public void selectAllItems() {
         IVItem[] _items = items;
        if (_items == null) {
            return;
        }
        for (int i = 0; i < _items.length; i++) {
            if (_items[i] != null) {
                _items[i].selectBorder();
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
    @Override
    public boolean hasMultiSelect() {
         IVItem[] _items = items;
        if (_items == null) {
            return false;
        }
        int selectedCount = 0;
        for (int i = 0; i < _items.length; i++) {
            IVItem item = _items[i];
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
    @Override
    public void deselectAllItems() {
         IVItem[] _items = items;
        if (_items == null) {
            return;
        }
        selectedItem = null;
        for (int i = 0; i < _items.length; i++) {
            IVItem item = _items[i];
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
    @Override
    public void selectOneItem(IVItem _itemToSelect) {
        if (_itemToSelect == null) {
            return;
        }
        deselectAllItems();
        IVItem _selectedItem = selectedItem;
        if (_selectedItem != null) {
            _selectedItem.deselectBorder();
        }
        selectedItem = _itemToSelect;
        _itemToSelect.selectBorder();
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    /**
     *
     * @param _itemToSelect
     */
    @Override
    public void selectItem(IVItem _itemToSelect) {
        if (_itemToSelect == null) {
            return;
        }
        selectedItem = _itemToSelect;
        _itemToSelect.selectBorder();
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    /**
     *
     * @param _itemToDeselect
     */
    @Override
    public void deselectItem(IVItem _itemToDeselect) {
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
    @Override
    public void toggleItem(IVItem _itemToToggle) {
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
    @Override
    public void selectFromSelectedItemTo(IVItem _toItem) {
        IVItem _selectedItem = selectedItem;
        if (_selectedItem == null) {
            selectOneItem(_toItem);
            return;
        }
        IVItem[] _items = items;
        if (_toItem == null || _items == null || _selectedItem == null) {
            return;
        }
        if (_selectedItem == _toItem) {
            return;
        }
        int i = 0;
        for (; i < _items.length; i++) {
            if (_items[i] == _toItem || _items[i] == _selectedItem) {
                break;
            }
        }

        if (i < _items.length) {
            _items[i].selectBorder();

        }
        for (i++; i < _items.length; i++) {
            if (_items[i] == _toItem || _items[i] == _selectedItem) {
                break;
            }
            _items[i].selectBorder();
        }

        if (i < _items.length) {
            _items[i].selectBorder();

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
    @Override
    public void deselectFromSelectedItemTo(IVItem _toItem) {
        IVItem[] _items = items;
        if (_toItem == null || _items == null) {
            return;
        }
        IVItem _selectedItem = selectedItem;
        int i = 0;
        for (; i < _items.length; i++) {
            if (_items[i] == _toItem || _items[i] == _selectedItem) {
                break;
            }
        }

        if (i < _items.length) {
            _items[i].deselectBorder();

        }
        for (i++; i < _items.length; i++) {
            if (_items[i] == _toItem || _items[i] == selectedItem) {
                break;
            }
            _items[i].deselectBorder();
        }
        if (observeSelectionChanges.isBeingObserved()) {
            observeSelectionChanges.change(this);
        }
    }

    /**
     *
     * @param _itemsToSelect
     */
    @Override
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
    @Override
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
    @Override
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
        IVItem[] _items = items;
        if (_items != null) {
            for (int i = 0; i < _items.length; i++) {
                if (_items[i] instanceof IView) {
                    ((IView) _items[i]).setParentView(null);
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
    public void setBackcall(IBackcall _backcall) {
        releaseItems();
        super.setBackcall(_backcall);
    }

    /**
     *
     * @param _
     */
    @Override
    public void listModified(IOut _) {
        if (_ == null) {
            _ = NullOut.cNull;
        }
        releaseItems();
        IVItem[] _rawItems = rawItems(_, backcall);
        rawItems = _rawItems;
        items = filterItems(_, _rawItems);
        super.listModified(_);
    }

    /**
     *
     * @param _
     */
    @Override
    public void filterModified(IOut _) {
        IVItem[] _rawItems  = rawItems;
        if (_rawItems != null) {
            releaseItems();
            items = filterItems(_, _rawItems);
        }
        super.filterModified(_);
        IVItem[] _items  = items;
        if (_items != null && _items.length > 0) {
            _items[0].grabFocus(0);//!!who needs to be propagated
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

            @Override
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
                pattern = Pattern.compile(regx);
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
    @Override
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
    @Override
    public void keyTyped(KeyTyped e) {
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyReleased e) {
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyPressed e) {
        if (e.isControlDown()) {
            int code = e.getKeyCode();
            switch (code) {
                case IKeyEventConstants.cA:
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
