/*
 * ViewBox.java.java
 *
 * Created on 01-03-2010 01:31:39 PM
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

/**
 *
 * @author Administrator
 */
public class ViewBox extends AViewableWH {

    /**
     *
     */
    protected AColor color = AColor.white;

    /**
     *
     * @param _w
     * @param _h
     * @param _color
     */
    public ViewBox(float _w, float _h, AColor _color) {
        this(_w, _h);
        if (_color != null) {
            color = _color;
        }
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public ViewBox(float _w, float _h) {
        super();
        w = _w;
        h = _h;
    }

    /**
     *
     * @return
     */
    public AColor getColor() {
        return color;
    }

    /**
     *
     * @param _color
     */
    public void setColor(AColor _color) {
        if (_color != null) {
            color = _color;
        }
    }

    @Override
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        super.paintBody(g, _layer, mode, _painted);
        g.setColor(color);
        g.rect(true, 0, 0, (int) w, (int) h);
    }
}
