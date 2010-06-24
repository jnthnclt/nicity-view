/*
 * VPaintable.java.java
 *
 * Created on 01-03-2010 01:31:38 PM
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
package colt.nicity.view.core;

import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IPaintable;

/**
 *
 * @author Administrator
 */
public class VPaintable extends AViewableWH {

    /**
     *
     */
    protected IPaintable paintable;

    /**
     *
     */
    public VPaintable() {
        super();
    }

    /**
     *
     * @param _paintable
     */
    public VPaintable(IPaintable _paintable) {
        super();
        setPaintable(_paintable);
    }

    /**
     *
     * @return
     */
    public IPaintable getPaintable() {
        return paintable;
    }

    /**
     *
     * @param _paintable
     */
    public void setPaintable(IPaintable _paintable) {
        if (_paintable == null) {
            _paintable = NullPaintable.cNull;
        }
        paintable = _paintable;
        w = _paintable.getW(null, null);
        h = _paintable.getH(null, null);
    }

    @Override
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        super.paintBody(g, _layer, mode, _painted);
        if (paintable != null) {
            paintable.paint(g, new XYWH_I(0, 0, w, h));
        }
    }

    @Override
    public float getW() {
        w = paintable.getW(null, null);
        return w;
    }

    @Override
    public float getH() {
        h = paintable.getH(null, null);
        return h;
    }
}
