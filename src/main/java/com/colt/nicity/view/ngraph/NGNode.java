/*
 * NGNode.java.java
 *
 * Created on 01-03-2010 01:33:11 PM
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
package com.colt.nicity.view.ngraph;

import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.lang.ASetObject;

/**
 *
 * @author Administrator
 */
public class NGNode extends ASetObject implements IVNode {

    /**
     *
     */
    public Object value;
    /**
     *
     */
    public Object key;
    /**
     *
     */
    public long count = 0;
    /**
     *
     */
    public CSet linkTo = new CSet();
    /**
     *
     */
    public CSet linkFrom = new CSet();

    /**
     *
     * @param _value
     * @param _key
     */
    public NGNode(
            Object _value, Object _key) {
        value = _value;
        key = _key;
    }

    /**
     *
     * @return
     */
    public Object key() {
        return key;
    }

    /**
     *
     * @return
     */
    public Object value() {
        return value;
    }

    /**
     *
     * @return
     */
    public int linkFromCount() {
        return (int) linkFrom.getCount();
    }

    /**
     *
     * @return
     */
    public int linkToCount() {
        return (int) linkTo.getCount();
    }

    /**
     *
     * @return
     */
    public Object[] linkFroms() {
        return linkFrom.getAll(Object.class);
    }

    /**
     *
     * @return
     */
    public Object[] linkTos() {
        return linkTo.getAll(Object.class);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    /**
     *
     * @return
     */
    public Object hashObject() {
        return key;
    }

    /**
     *
     * @param _minNode
     * @param _maxNode
     * @param _maxNodeCount
     * @return
     */
    public boolean visible(double _minNode, double _maxNode, long _maxNodeCount) {
        double rank = (double) count / (double) _maxNodeCount;
        if (rank < _minNode) {
            return false;
        }
        if (rank > _maxNode) {
            return false;
        }
        return true;
    }

    // IHaveCount
    public long getCount() {
        return count;
    }
}
	
	
