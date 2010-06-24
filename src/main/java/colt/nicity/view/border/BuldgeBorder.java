/*
 * BuldgeBorder.java.java
 *
 * Created on 01-03-2010 01:31:35 PM
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
package colt.nicity.view.border;

import colt.nicity.view.flavor.ScrollFlavor;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.Place;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class BuldgeBorder extends AFlaggedBorder {

    private Place direction = UV.cNS;
    private int pad = 2;
    private double rate = 0.2;
    private AColor color = ViewColor.cTheme;

    /**
     *
     */
    public BuldgeBorder() {
        this(ViewColor.cTheme, 2);
    }

    /**
     *
     * @param _color
     */
    public BuldgeBorder(AColor _color) {
        this(_color, 2);
    }

    /**
     *
     * @param _pad
     */
    public BuldgeBorder(int _pad) {
        this(ViewColor.cTheme, _pad);
    }

    /**
     *
     * @param _pad
     * @param _direction
     */
    public BuldgeBorder(int _pad, Place _direction) {
        this(ViewColor.cTheme, _pad, _direction);
    }

    /**
     *
     * @param _color
     * @param _pad
     */
    public BuldgeBorder(AColor _color, int _pad) {
        this(_color, _pad, UV.cNS, 0.2);
    }

    /**
     *
     * @param _color
     * @param _pad
     * @param _direction
     */
    public BuldgeBorder(AColor _color, int _pad, Place _direction) {
        this(_color, _pad, _direction, 0.2);
    }

    /**
     *
     * @param _color
     * @param _pad
     * @param _direction
     * @param _rate
     */
    public BuldgeBorder(AColor _color, int _pad, Place _direction, double _rate) {
        color = _color;
        pad = _pad;
        direction = _direction;
        rate = _rate;
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    @Override
    public void paintBorder(ICanvas g, int x, int y, int w, int h) {
    }

    /**
     *
     * @param _g
     * @param x
     * @param y
     * @param _w
     * @param _h
     */
    @Override
    public void paintBackground(ICanvas _g, int x, int y, int _w, int _h) {
        new ScrollFlavor().paintFlavor(_g, x, y, _w, _h, color);
    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return pad;
    }
}
