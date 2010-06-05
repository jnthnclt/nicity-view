/*
 * IntValueToColorValueProxy.java.java
 *
 * Created on 03-12-2010 06:33:14 PM
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
package com.colt.nicity.view.core;

import com.colt.nicity.core.value.IValue;
import com.colt.nicity.core.value.Value;

/**
 *
 * @author Administrator
 */
public class IntValueToColorValueProxy extends Value {

    IValue intValue;

    /**
     *
     * @param _intValue
     */
    public IntValueToColorValueProxy(Value _intValue) {
        super(new AColor(_intValue.intValue()));
        intValue = _intValue;
    }

    /**
     *
     * @param _value
     */
    @Override
    public void setValue(Object _value) {
        super.setValue(_value);
        intValue.setValue(((AColor) _value).intValue());
    }
}
