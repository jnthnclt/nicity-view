/*
 * VEditText.java.java
 *
 * Created on 03-12-2010 06:40:24 PM
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

import colt.nicity.core.lang.UString;
import colt.nicity.core.observer.AObserver;
import colt.nicity.core.observer.Change;
import colt.nicity.core.observer.IObservable;
import colt.nicity.core.observer.IObserver;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.EditText;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.Viewer;

/**
 *
 * @author Administrator
 */
public class VEditText extends Viewer {

    Value value;
    IObserver observer;

    /**
     *
     * @param _value
     */
    public VEditText(Value _value) {
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
        EditText editText = new EditText(UString.toStringArray(value.toString(), "\n")) {

            @Override
            public void stringChanged(String[] _value) {
                value.setValue(UString.toString(_value, "\n"));
            }

            @Override
            public void stringSet(String[] _value) {
                value.setValue(UString.toString(_value, "\n"));
            }
        };
        editText.spans(UV.cXNSEW);
        placer = new Placer(editText);
        paint();
    }
}
