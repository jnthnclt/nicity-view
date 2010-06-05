/*
 * VFlavor.java.java
 *
 * Created on 01-03-2010 01:31:34 PM
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

import com.colt.nicity.view.flavor.AFlavor;
import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class VFlavor extends AViewableWH {

    /**
     *
     */
    protected AFlavor flavor;

    /**
     *
     * @param _w
     * @param _h
     * @param _flavor
     */
    public VFlavor(float _w, float _h, AFlavor _flavor) {
        this(_w, _h);
        if (flavor != null) {
            flavor = _flavor;
        }
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public VFlavor(float _w, float _h) {
        w = _w;
        h = _h;
    }

    /**
     *
     * @return
     */
    public AFlavor getFlavor() {
        return flavor;
    }

    /**
     *
     * @param _flavor
     */
    public void setFlavor(AFlavor _flavor) {
        if (_flavor != null) {
            flavor = _flavor;
        }
    }

    @Override
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        super.paintBody(g, _layer, mode, _painted);
        if (flavor != null) {
            flavor.paintFlavor(g, 0, 0, (int) w, (int) h, null);
        }
    }
}
