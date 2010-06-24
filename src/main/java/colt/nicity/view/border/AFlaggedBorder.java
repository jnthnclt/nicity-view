/*
 * AFlaggedBorder.java.java
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
package colt.nicity.view.border;

import colt.nicity.core.lang.BitMasks;
import colt.nicity.view.interfaces.IActiveBorder;
import colt.nicity.view.interfaces.IActiveSelectedBorder;
import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.ISelectedBorder;

/**
 *
 * @author Administrator
 */
public abstract class AFlaggedBorder implements IBorder, IActiveBorder, ISelectedBorder, IActiveSelectedBorder {

    /**
     *
     */
    public static final int cActive = BitMasks.c1;
    /**
     *
     */
    public static final int cSelected = BitMasks.c2;
    private static float x = 0;
    private static float y = 0;
    private static float w = 0;
    private static float h = 0;
    private int flags = 0;

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void paintBorder(ICanvas g, int x, int y, int w, int h) {
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param _w
     * @param _h
     */
    public void paintBackground(ICanvas g, int x, int y, int _w, int _h) {
    }

    /**
     *
     * @param _flagMask
     */
    public void addFlag(int _flagMask) {
        flags |= _flagMask;
    }

    /**
     *
     * @param _flagMask
     */
    public void removeFlag(int _flagMask) {
        flags &= ~_flagMask;
    }

    /**
     *
     * @param _isMask
     * @return
     */
    public boolean is(int _isMask) {
        return (flags & _isMask) == _isMask;
    }

    /**
     *
     * @return
     */
    public boolean isActive() {
        return is(cActive);
    }

    /**
     *
     * @return
     */
    public boolean isSelected() {
        return is(cSelected);
    }

    /**
     *
     * @return
     */
    public float getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public float getY() {
        return y;
    }

    /**
     *
     * @return
     */
    public float getW() {
        return w;
    }

    /**
     *
     * @return
     */
    public float getH() {
        return h;
    }

    /**
     *
     * @return
     */
    public IBorder getDefaultBorder() {
        removeFlag(cActive | cSelected);
        return this;
    }

    /**
     *
     * @return
     */
    public IActiveBorder getActiveBorder() {
        addFlag(cActive);
        removeFlag(cSelected);
        return this;
    }

    /**
     *
     * @return
     */
    public ISelectedBorder getSelectedBorder() {
        addFlag(cSelected);
        removeFlag(cActive);
        return this;
    }

    /**
     *
     * @return
     */
    public IActiveSelectedBorder getActiveSelectedBorder() {
        addFlag(cActive | cSelected);
        return this;
    }
}
