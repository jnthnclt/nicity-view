/*
 * Layer.java.java
 *
 * Created on 01-03-2010 01:29:42 PM
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

import com.colt.nicity.core.lang.ASetObject;

/**
 *
 * @author Administrator
 */
final public class Layer extends ASetObject implements Cloneable {

    private Object session;
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public float tx, ty, x, y, w, h;

    /**
     *
     * @param _session
     * @param _tx
     * @param _ty
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public Layer(
            Object _session,
            float _tx, float _ty,
            float _x, float _y,
            float _w, float _h) {
        session = _session;
        tx = _tx;
        ty = _ty;
        x = _x;
        y = _y;
        w = _w;
        h = _h;
    }

    /**
     *
     * @return
     */
    public Object hashObject() {
        return session;
    }

    /**
     *
     * @return
     */
    public float x() {
        return x;
    }

    /**
     *
     * @return
     */
    public float y() {
        return y;
    }

    /**
     *
     * @return
     */
    public float w() {
        return w;
    }

    /**
     *
     * @return
     */
    public float h() {
        return h;
    }

    /**
     *
     * @param _x
     */
    public void x(float _x) {
        x = _x;
    }

    /**
     *
     * @param _y
     */
    public void y(float _y) {
        y = _y;
    }

    /**
     *
     * @param _w
     */
    public void w(float _w) {
        w = _w;
    }

    /**
     *
     * @param _h
     */
    public void h(float _h) {
        h = _h;
    }

    /**
     *
     * @return
     */
    public final float getTX() {
        return tx;
    }

    /**
     *
     * @return
     */
    public final float getTY() {
        return ty;
    }

    /**
     *
     * @param _tx
     * @param _ty
     * @param _w
     * @param _h
     */
    final public void clobber(float _tx, float _ty, float _w, float _h) {
        tx = _tx;
        ty = _ty;
    }

    /**
     *
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @return
     */
    final public boolean intersection(float _x, float _y, float _w, float _h) {
        float x1 = (x >= _x) ? x : _x;
        float x2 = (x + w <= _x + _w) ? x + w : _x + _w;
        float y1 = (y >= _y) ? y : _y;
        float y2 = (y + h <= _y + _h) ? y + h : _y + _h;
        if (((x2 - x1) < 0) || ((y2 - y1) < 0)) {
            return false;
        }

        x = x1;
        y = y1;
        w = (x2 - x1);
        h = (y2 - y1);
        return true;
    }

    @Override
    public Object clone() {
        try {
            Layer clone = (Layer) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
    public String toString() {
        return "[" + tx + "," + ty + "][" + x + "," + y + "," + w + "," + h + "]";
    }
}
