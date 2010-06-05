/*
 * VFixed.java.java
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
package com.colt.nicity.view.core;

import com.colt.nicity.view.interfaces.IPlacer;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VFixed extends Viewer {

    /**
     *
     */
    public VFixed() {
        super();
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public VFixed(float _w, float _h) {
        super();
        fixW = _w;
        fixH = _h;
    }

    /**
     *
     * @param _view
     * @param _w
     * @param _h
     */
    public VFixed(IView _view, float _w, float _h) {
        super(_view);
        fixW = _w;
        fixH = _h;
    }

    /**
     *
     * @param _placer
     */
    public VFixed(IPlacer _placer) {
        super(_placer);
    }

    /**
     *
     * @param _view
     */
    public VFixed(IView _view) {
        super(_view);
    }

    /**
     *
     * @param _view
     * @param _placement
     */
    public VFixed(IView _view, Place _placement) {
        super(_view, _placement);
    }

    /**
     *
     * @param _view
     * @param _flex
     */
    public VFixed(IView _view, Flex _flex) {
        super(_view, _flex);
    }
    private float fixW = -1;
    private float fixH = -1;

    /**
     *
     * @param _w
     */
    public void setFixedW(float _w) {
        fixW = _w;
    }

    /**
     *
     * @param _h
     */
    public void setFixedH(float _h) {
        fixH = _h;
    }

    @Override
    public float getW() {
        if (fixW < 0) {
            return super.getW();
        }
        return fixW;
    }

    @Override
    public float getH() {
        if (fixH < 0) {
            return super.getH();
        }
        return fixH;
    }
}
