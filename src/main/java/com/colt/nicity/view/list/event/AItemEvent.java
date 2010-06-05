/*
 * AItemEvent.java.java
 *
 * Created on 01-03-2010 01:32:32 PM
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
package com.colt.nicity.view.list.event;

import com.colt.nicity.view.event.AEvent;
import com.colt.nicity.view.event.AMouseEvent;
import com.colt.nicity.view.list.NullListController;
import com.colt.nicity.view.list.VList;
import com.colt.nicity.core.collection.CArray;
import com.colt.nicity.view.interfaces.IEvent;
import com.colt.nicity.view.interfaces.IListController;
import com.colt.nicity.view.interfaces.IVItem;

/**
 *
 * @author Administrator
 */
abstract public class AItemEvent extends AEvent {

    /**
     *
     */
    protected IEvent event;
    /**
     *
     */
    protected IVItem[] selected;

    /**
     *
     * @return
     */
    public VList getVList() {
        try {
            return (VList) ((IVItem) source).getParentView();
        } catch (Exception x) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public IListController getListController() {
        try {
            return ((VList) ((IVItem) source).getParentView()).getListController();
        } catch (Exception x) {
            return NullListController.cNull;
        }
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        if (source == null) {
            return null;
        }
        return ((IVItem) source).getValue();
    }

    /**
     *
     * @return
     */
    public IEvent getEvent() {
        return event;
    }

    /**
     *
     * @return
     */
    public boolean isModifierDown() {
        if (event instanceof AMouseEvent) {
            AMouseEvent me = (AMouseEvent) event;
            if (me.isControlDown()) {
                return true;
            }
            if (me.isShiftDown()) {
                return true;
            }
            if (me.isAltDown()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public long getMask() {
        return 0;
    }

    /**
     *
     * @return
     */
    public IVItem[] getSelected() {
        if (selected == null || selected.length == 0) {
            return new IVItem[]{(IVItem) source};
        }
        return selected;
    }

    /**
     *
     * @return
     */
    public Object[] getSelectedValues() {
        return getSelectedValues(Object.class);
    }

    /**
     *
     * @param _class
     * @return
     */
    public Object[] getSelectedValues(Class _class) {
        CArray array = new CArray(_class);
        if (selected == null || selected.length == 0) {
            Object value = ((IVItem) source).getValue();
            if (_class.isInstance(value)) {
                array.insertLast(value);
            }
            return array.getAll();
        }
        for (int i = 0; i < selected.length; i++) {
            Object value = selected[i].getValue();
            if (_class.isInstance(value)) {
                array.insertLast(value);
            }
        }
        return array.getAll();
    }
}
