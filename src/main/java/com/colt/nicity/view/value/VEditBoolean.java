/*
 * VEditBoolean.java.java
 *
 * Created on 03-12-2010 06:41:33 PM
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

import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.VString;
import com.colt.nicity.view.interfaces.IView;
import com.colt.nicity.view.interfaces.IViewable;

/**
 *
 * @author Administrator
 */
public class VEditBoolean extends AVEditBoolean {

    private Object trueView;
    private Object falseView;

    /**
     *
     * @param _value
     * @param _true
     * @param _false
     */
    public VEditBoolean(Value<Boolean> _value, Object _true, Object _false) {
        super(_value);
        if (_true instanceof IViewable) {
            trueView = _true;
        } else if (_true instanceof IView) {
            trueView = _true;
        }
        if (trueView == null) {
            trueView = new VString(_true);
        }

        if (_false instanceof IViewable) {
            falseView = _false;
        } else if (_false instanceof IView) {
            falseView = _false;
        }
        if (falseView == null) {
            falseView = new VString(_false);
        }
        refresh();
    }

    /**
     *
     * @return
     */
    @Override
    public IView trueView() {
        if (trueView instanceof IViewable) {
            return ((IViewable) trueView).getView();
        } else {
            return (IView) trueView;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public IView falseView() {
        if (falseView instanceof IViewable) {
            return ((IViewable) falseView).getView();
        } else {
            return (IView) falseView;
        }
    }
}
