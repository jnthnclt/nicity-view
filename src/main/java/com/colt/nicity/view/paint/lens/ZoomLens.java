/*
 * ZoomLens.java.java
 *
 * Created on 01-24-2010 10:08:12 PM
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
package com.colt.nicity.view.paint.lens;

import com.colt.nicity.core.memory.struct.XY_D;

/**
 *
 * @author Administrator
 */
public class ZoomLens extends ALens {

    /**
     *
     */
    public double zoom = 1d;

    /**
     *
     * @param p
     */
    @Override
    public void applyLens(XY_D p) {
        p.x = p.x * Math.pow(2, zoom / 10.0);
        p.y = p.y * Math.pow(2, zoom / 10.0);

    }

    /**
     *
     * @param p
     */
    @Override
    public void undoLens(XY_D p) {
        p.x = p.x / Math.pow(2, zoom / 10.0);
        p.y = p.y / Math.pow(2, zoom / 10.0);
    }
}
