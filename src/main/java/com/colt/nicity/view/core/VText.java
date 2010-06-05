/*
 * VText.java.java
 *
 * Created on 01-03-2010 01:31:36 PM
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

import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VText extends Viewer {

    //private String toString;
    private float min = -1;
    private float max = -1;
    private float width;
    private ViewText vs;

    /**
     *
     */
    public VText() {
        this(null, null, null);
    }

    /**
     *
     * @param _text
     */
    public VText(Object _text) {
        this(_text, null, null);
    }

    /**
     *
     * @param _text
     * @param _color
     */
    public VText(Object _text, AColor _color) {
        this(_text, null, _color);
    }

    /**
     *
     * @param _text
     * @param _font
     */
    public VText(Object _text, AFont _font) {
        this(_text, _font, null);
    }

    /**
     *
     * @param _text
     * @param _font
     * @param _color
     */
    public VText(Object _text, AFont _font, AColor _color) {
        vs = new ViewText((String[]) _text, _font, _color);
        setPlacer(new Placer(vs));
        super.layoutInterior();
        width = getWidth();
    }

    /*
    public void layoutInterior() {
    super.layoutInterior();
    width = getWidth();
    }*/
    /**
     *
     * @return
     */
    public float getWidth() {
        IView content = getContent();
        float _w = content.getW();
        if (min >= 0 && _w < min) {
            _w = min;
        }
        if (max >= 0 && _w > max) {
            _w = max;
        }
        return _w;
    }

    /**
     *
     * @param _min
     * @param _max
     */
    public void setWRange(float _min, float _max) {
        min = _min;
        max = _max;
    }

    /**
     *
     * @return
     */
    public ViewText getViewText() {
        return vs;
    }

    @Override
    public float getW() {
        if (min >= 0 && width < min) {
            return min + border.getW();
        }
        if (max >= 0 && width > max) {
            return max + border.getW();
        }
        return width + border.getW();
    }

    @Override
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        super.paintBody(g, _layer, mode, _painted);
        float _w = getWidth();
        if (_w != width) {
            width = _w;
            layoutInterior();
            parent.layoutInterior();
            flush();
        }
    }
}
