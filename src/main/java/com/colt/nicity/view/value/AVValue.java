/*
 * AVValue.java.java
 *
 * Created on 03-12-2010 06:40:48 PM
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
package com.colt.nicity.view.value;

import com.colt.nicity.core.observer.AObserver;
import com.colt.nicity.core.observer.Change;
import com.colt.nicity.core.observer.IObservable;
import com.colt.nicity.core.observer.IObserver;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.Placer;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.IView;

/**
 * A viewer that auto updates content when content changes
 * @author Jonathan Colt
 * @param <V>
 */
abstract public class AVValue<V> extends Viewer {

    /**
     *
     * @param _value
     * @return
     */
    abstract public IView viewValue(V _value);
    Value<V> value;
    IObserver observer;

    /**
     *
     * @param _value
     */
    public AVValue(Value<V> _value) {
        value = _value;
        observer = new AObserver() {

            @Override
            public void change(Change _change) {
                refresh();
            }
            public void bound(IObservable _observable) {
                refresh();
            }
            public void released(IObservable _observable) {
            }
        };
        value.bind(observer);
    }

    /**
     *
     */
    public void refresh() {
        placer = new Placer(viewValue(value.getValue()));
        layoutInterior();
        repair();
        flush();
    }
}
