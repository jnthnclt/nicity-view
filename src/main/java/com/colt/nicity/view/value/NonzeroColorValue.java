/*
 * NonzeroColorValue.java.java
 *
 * Created on 03-12-2010 06:41:04 PM
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

import com.colt.nicity.core.collection.IHaveCount;
import com.colt.nicity.core.value.IValue;
import com.colt.nicity.view.core.AColor;

/**
 *
 * @author Administrator
 */
public class NonzeroColorValue implements IValue<AColor> {

    IHaveCount hasCount;
    AColor zero;
    AColor nonzero;

    /**
     *
     * @param _hasCount
     * @param _zero
     * @param _nonzero
     */
    public NonzeroColorValue(IHaveCount _hasCount, AColor _zero, AColor _nonzero) {
        hasCount = _hasCount;
        zero = _zero;
        nonzero = _nonzero;
    }

    /**
     *
     * @return
     */
    public AColor getValue() {
        if (hasCount == null) {
            return zero;
        }
        if (hasCount.getCount() > 0) {
            return nonzero;
        }
        return zero;
    }

    /**
     *
     * @param _value
     */
    public void setValue(AColor _value) {
    }
}
