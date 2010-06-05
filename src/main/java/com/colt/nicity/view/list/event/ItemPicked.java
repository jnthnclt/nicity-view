/*
 * ItemPicked.java.java
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

import com.colt.nicity.view.event.AViewEvent;
import com.colt.nicity.view.interfaces.IVItem;

/**
 *
 * @author Administrator
 */
public class ItemPicked extends AItemEvent {

    /**
     *
     * @param _item
     * @return
     */
    public static ItemPicked newInstance(IVItem _item) {
        ItemPicked e = new ItemPicked();
        e.source = _item;
        return e;
    }

    /**
     *
     * @param _item
     * @param _e
     * @return
     */
    public static ItemPicked newInstance(IVItem _item, AViewEvent _e) {
        ItemPicked e = new ItemPicked();
        e.source = _item;
        e.event = _e;
        return e;
    }

    /**
     *
     * @param _item
     * @param _selected
     * @param _e
     * @return
     */
    public static ItemPicked newInstance(IVItem _item, IVItem[] _selected, AViewEvent _e) {
        ItemPicked e = new ItemPicked();
        e.source = _item;
        e.selected = _selected;
        e.event = _e;
        return e;
    }
}
