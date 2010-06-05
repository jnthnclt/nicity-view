/*
 * IBorder.java.java
 *
 * Created on 01-03-2010 01:31:40 PM
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
package com.colt.nicity.view.interfaces;

import com.colt.nicity.core.lang.BitMasks;

/**
 *
 * @author Administrator
 */
public interface IBorder {

    /**
     *
     */
    public static final int cActive = BitMasks.c1;
    /**
     *
     */
    public static final int cSelected = BitMasks.c2;

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void paintBorder(ICanvas g, int x, int y, int w, int h);

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void paintBackground(ICanvas g, int x, int y, int w, int h);

    /**
     *
     * @return
     */
    public float getX();

    /**
     *
     * @return
     */
    public float getY();

    /**
     *
     * @return
     */
    public float getW();

    /**
     *
     * @return
     */
    public float getH();

    /**
     *
     * @return
     */
    public boolean isActive();

    /**
     *
     * @return
     */
    public boolean isSelected();

    /**
     *
     * @return
     */
    public IBorder getDefaultBorder();

    /**
     *
     * @return
     */
    public IActiveBorder getActiveBorder();

    /**
     * 
     * @return
     */
    public ISelectedBorder getSelectedBorder();

    /**
     *
     * @return
     */
    public IActiveSelectedBorder getActiveSelectedBorder();
}
