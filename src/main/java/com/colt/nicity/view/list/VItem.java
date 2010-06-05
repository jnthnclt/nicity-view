/*
 * VItem.java.java
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

import com.colt.nicity.view.border.ItemBorder;
import com.colt.nicity.view.core.Placer;
import com.colt.nicity.view.core.VString;
import com.colt.nicity.view.interfaces.IAlignableItem;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VItem extends AItem implements IAlignableItem {

    /**
     *
     */
    protected Object value;

    /**
     *
     */
    public VItem() {
    }

    /**
     *
     * @param _view
     */
    public VItem(IView _view) {
        this(_view, _view);
    }

    /**
     *
     * @param _value
     */
    public VItem(Object _value) {
        this(new VString(_value), _value);
    }

    /**
     *
     * @param _view
     * @param _value
     */
    public VItem(IView _view, Object _value) {
        setPlacer(new Placer(_view));
        value = _value;
        setBorder(new ItemBorder());
    }

    /**
     *
     * @return
     */
    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return (value == null) ? "" : value.toString();
    }

    // IAlignableItem
    /**
     *
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public void justify(float _x, float _y, float _w, float _h) {
        //setLocation(_x+((_w-w)/2),_y+((_h-h)/2));
        setLocation(_x, _y);
    }
}
