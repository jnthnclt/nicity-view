/*
 * UModal.java.java
 *
 * Created on 03-12-2010 06:33:43 PM
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

/**
 *
 * @author Administrator
 */
public class UModal implements IValue {

    final private Object getLock = new Object();
    final private Object setLock = new Object();
    IValue value;

    /**
     *
     * @param _value
     */
    public UModal(IValue _value) {
        value = _value;
    }

    /**
     *
     * @return
     */
    public Object getModalValue() {
        synchronized (getLock) {
            try {
                getLock.wait();
            } catch (Exception x) {
            }
        }
        return value.getValue();
    }

    /**
     *
     * @param _value
     */
    public void setModalValue(Object _value) {
        synchronized (setLock) {
            try {
                setLock.wait();
            } catch (Exception x) {
            }
        }
        value.setValue(_value);
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        synchronized (setLock) {
            setLock.notifyAll();
        }
        return value.getValue();
    }

    /**
     *
     * @param _value
     */
    public void setValue(Object _value) {
        value.setValue(_value);
        synchronized (getLock) {
            getLock.notifyAll();
        }
    }
}
