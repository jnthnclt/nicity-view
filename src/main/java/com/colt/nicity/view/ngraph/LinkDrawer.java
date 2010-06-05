/*
 * LinkDrawer.java.java
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

import com.colt.nicity.core.lang.ASetObject;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 * @param <K>
 */
public class LinkDrawer<K> extends ASetObject<K> {

    private K key;

    /**
     *
     * @param _key
     */
    public LinkDrawer(K _key) {
        key = _key;
    }

    /**
     *
     * @return
     */
    public K hashObject() {
        return key;
    }

    /**
     *
     * @param _g
     * @param from
     * @param to
     * @param _rank
     * @param _size
     */
    public void draw(ICanvas _g, XY_I from, XY_I to, double _rank, int _size) {
    }
    
}
	
	
