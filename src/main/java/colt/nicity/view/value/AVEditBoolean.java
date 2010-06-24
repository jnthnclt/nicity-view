/*
 * AVEditBoolean.java.java
 *
 * Created on 03-12-2010 06:40:37 PM
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
package colt.nicity.view.value;

import colt.nicity.view.list.AItem;
import colt.nicity.core.observer.AObserver;
import colt.nicity.core.observer.Change;
import colt.nicity.core.observer.IObservable;
import colt.nicity.core.observer.IObserver;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.Placer;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
abstract public class AVEditBoolean extends AItem {

    /**
     *
     * @return
     */
    abstract public IView trueView();
    /**
     *
     * @return
     */
    abstract public IView falseView();

    Value<Boolean> value;
    IObserver observer;

    /**
     *
     * @param _value
     */
    public AVEditBoolean(Value<Boolean> _value) {
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
     * @param _e
     */
    @Override
    public void picked(IEvent _e) {
        value.setValue(!isTrue());
    }

    /**
     *
     * @return
     */
    public boolean isTrue() {
        return value.booleanValue();
    }

    /**
     *
     * @return
     */
    public boolean isFalse() {
        return !value.booleanValue();
    }

    /**
     *
     */
    public void refresh() {
        if (value.booleanValue()) {
            placer = new Placer(trueView());
            selectBorder();
        } else {
            placer = new Placer(falseView());
            deselectBorder();
        }
        layoutInterior();
        repair();
        flush();
    }
}
