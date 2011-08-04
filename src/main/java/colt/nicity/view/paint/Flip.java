/*
 * Flip.java.java
 *
 * Created on 01-03-2010 01:33:52 PM
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
package colt.nicity.view.paint;

/**
 *
 * @author Administrator
 */
public class Flip {

    /**
     *
     */
    public boolean x = false;//read only please
    /**
     *
     */
    public boolean y = false;//read only please
    /**
     *
     */
    public boolean z = false;//read only please

    /**
     *
     */
    public Flip() {
    }

    /**
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public Flip(boolean _x, boolean _y, boolean _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    /**
     *
     * @return
     */
    public boolean flipX() {
        return x;
    }

    /**
     *
     * @return
     */
    public boolean flipY() {
        return y;
    }

    /**
     *
     * @return
     */
    public boolean flipZ() {
        return z;
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public double addX(double _a, double _b) {
        return (x) ? _a - _b : _a + _b;
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public double addY(double _a, double _b) {
        return (y) ? _a - _b : _a + _b;
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public double addZ(double _a, double _b) {
        return (z) ? _a - _b : _a + _b;
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public double subtractX(double _a, double _b) {
        return (x) ? _a + _b : _a - _b;
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public double subtractY(double _a, double _b) {
        return (y) ? _a + _b : _a - _b;
    }

    /**
     *
     * @param _a
     * @param _b
     * @return
     */
    public double subtractZ(double _a, double _b) {
        return (z) ? _a + _b : _a - _b;
    }

    /**
     *
     * @param _x
     * @return
     */
    public double flipX(double _x) {
        return (x) ? 1 - _x : _x;
    }

    /**
     *
     * @param _y
     * @return
     */
    public double flipY(double _y) {
        return (y) ? 1 - _y : _y;
    }

    /**
     *
     * @param _z
     * @return
     */
    public double flipZ(double _z) {
        return (z) ? 1 - _z : _z;
    }
}
