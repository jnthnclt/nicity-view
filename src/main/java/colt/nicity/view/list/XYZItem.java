/*
 * XYZItem.java.java
 *
 * Created on 01-03-2010 01:32:11 PM
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
package colt.nicity.view.list;

import colt.nicity.core.memory.struct.IXYZ;

/**
 *
 * @author Administrator
 */
public class XYZItem extends VItem implements IXYZ {

    double px = 0, py = 0, pz = 0;

    /**
     *
     */
    public XYZItem() {
    }

    // IXYZ
    /**
     *
     * @return
     */
    public double x() {
        return px;
    }

    /**
     *
     * @return
     */
    public double y() {
        return py;
    }

    /**
     *
     * @return
     */
    public double z() {
        return pz;
    }

    /**
     *
     * @param _px
     */
    public void x(double _px) {
        px = _px;
    }

    /**
     *
     * @param _py
     */
    public void y(double _py) {
        py = _py;
    }

    /**
     *
     * @param _pz
     */
    public void z(double _pz) {
        pz = _pz;
    }
}
