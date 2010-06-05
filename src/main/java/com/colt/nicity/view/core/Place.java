/*
 * Place.java.java
 *
 * Created on 01-03-2010 01:31:37 PM
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

import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class Place implements Cloneable {

    float px = 0.0f;
    float py = 0.0f;
    float cx = 0.0f;
    float cy = 0.0f;
    float ox = 0.0f;
    float oy = 0.0f;
    float ifx = 1.0f;//interior
    float ify = 1.0f;//interior
    float efx = 1.0f;//exterior
    float efy = 1.0f;//exterior

    /**
     *
     * @return
     */
    public boolean isVertical() {
        return Math.abs(px - cx) < Math.abs(py - cy);
    }

    /**
     *
     * @return
     */
    public Place tangent() {
        return new Place(py, px, cy, cx, ox, oy);
    }

    /**
     *
     */
    public Place() {
    }

    /**
     *
     * @param _px
     * @param _py
     * @param _cx
     * @param _cy
     * @param _ox
     * @param _oy
     */
    public Place(float _px, float _py, float _cx, float _cy, float _ox, float _oy) {
        px = _px;
        py = _py;
        cx = _cx;
        cy = _cy;
        ox = _ox;
        oy = _oy;
    }

    /**
     *
     * @param _place
     * @param _flex
     */
    public Place(Place _place, Flex _flex) {
        this(_place, _flex, _flex);
    }

    /**
     *
     * @param _place
     * @param _interior
     * @param _exterior
     */
    public Place(Place _place, Flex _interior, Flex _exterior) {
        this(_place);
        ifx = _interior.x;
        ify = _interior.y;
        efx = _exterior.x;
        efy = _exterior.y;

    }

    /**
     *
     * @param _place
     */
    public Place(Place _place) {
        this(_place.px, _place.py, _place.cx, _place.cy, _place.ox, _place.oy);
    }

    /**
     *
     * @param _place
     * @param _ox
     * @param _oy
     */
    public Place(Place _place, float _ox, float _oy) {
        this(_place.px, _place.py, _place.cx, _place.cy, _ox, _oy);
    }

    /**
     *
     * @param _place
     * @param _ox
     * @param _oy
     * @param _flex
     */
    public Place(Place _place, float _ox, float _oy, Flex _flex) {
        this(_place, _ox, _oy);
        ifx = _flex.x;
        ify = _flex.y;
        efx = _flex.x;
        efy = _flex.y;
    }

    /**
     *
     * @return
     */
    public float getParentX() {
        return px;
    }

    /**
     *
     * @return
     */
    public float getParentY() {
        return py;
    }

    /**
     *
     * @param _px
     */
    public void setParentX(float _px) {
        px = _px;
    }

    /**
     *
     * @param _py
     */
    public void setParentY(float _py) {
        py = _py;
    }

    /**
     *
     * @return
     */
    public float getChildX() {
        return cx;
    }

    /**
     *
     * @return
     */
    public float getChildY() {
        return cy;
    }

    /**
     *
     * @param _cx
     */
    public void setChildX(float _cx) {
        cx = _cx;
    }

    /**
     *
     * @param _cy
     */
    public void setChildY(float _cy) {
        cy = _cy;
    }

    /**
     *
     * @return
     */
    public float getOffsetX() {
        return ox;
    }

    /**
     *
     * @return
     */
    public float getOffsetY() {
        return oy;
    }

    /**
     *
     * @param _ox
     */
    public void setOffsetX(float _ox) {
        ox = _ox;
    }

    /**
     *
     * @param _oy
     */
    public void setOffsetY(float _oy) {
        oy = _oy;
    }

    /**
     *
     * @param _ox
     */
    public void setOffsetX(int _ox) {
        ox = _ox;
    }

    /**
     *
     * @param _oy
     */
    public void setOffsetY(int _oy) {
        oy = _oy;
    }

    /**
     *
     * @return
     */
    public float getInteriorFlexX() {
        return ifx;
    }

    /**
     *
     * @return
     */
    public float getInteriorFlexY() {
        return ify;
    }

    /**
     *
     * @param _ifx
     */
    public void setInteriorFlexX(int _ifx) {
        ifx = _ifx;
    }

    /**
     *
     * @param _ify
     */
    public void setInteriorFlexY(int _ify) {
        ify = _ify;
    }

    /**
     *
     * @return
     */
    public float getExteriorFlexX() {
        return efx;
    }

    /**
     *
     * @return
     */
    public float getExteriorFlexY() {
        return efy;
    }

    /**
     *
     * @param _efx
     */
    public void setExteriorFlexX(int _efx) {
        efx = _efx;
    }

    /**
     *
     * @param _efy
     */
    public void setExteriorFlexY(int _efy) {
        efy = _efy;
    }

    /**
     *
     * @param _view
     * @param _x
     * @param _y
     */
    public void placeView(IView _view, float _x, float _y) {
        //_x += (int)(getParentX()*0);
        //_y += (int)(getParentY()*0);
        _x -= (int) (getChildX() * _view.getW());
        _y -= (int) (getChildY() * _view.getH());
        _view.setLocation(_x, _y);
    }

    /**
     *
     * @param _x
     * @param _y
     * @param yw
     * @param yh
     * @return
     */
    public XY_I place(double _x, double _y, double yw, double yh) {
        _x += (int) (getChildX() * yw);
        _y += (int) (getChildY() * yh);
        return new XY_I((int) _x, (int) _y);
    }

    // Cloneable
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

}
