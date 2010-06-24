/*
 * IPlacer.java.java
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
package colt.nicity.view.interfaces;

import colt.nicity.view.event.AViewEvent;
import colt.nicity.core.lang.BitMasks;
import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.core.Flex;
import colt.nicity.view.core.Layer;
import colt.nicity.view.core.Place;

/**
 *
 * @author Administrator
 */
public interface IPlacer extends IViewable {

    /**
     *
     */
    public final static int cParent = BitMasks.c1;
    /**
     *
     */
    public final static int cChild = BitMasks.c2;
    /**
     *
     */
    public final static int cOffset = BitMasks.c3;
    /**
     *
     */
    public final static int cFlex = BitMasks.c4;

    /**
     *
     * @return
     */
    public IViewable getViewable();

    /**
     *
     * @param viewable
     */
    public void setViewable(IViewable viewable);

    /**
     *
     * @return
     */
    public IPlacer getPlacer();

    /**
     *
     * @return
     */
    public Place getPlace();

    /**
     *
     * @param _place
     */
    public void setPlace(Place _place);

    /**
     *
     * @param parent
     * @param size
     * @param _flex
     */
    public void placeInside(IView parent, WH_F size, Flex _flex);

    /**
     *
     * @param parent
     * @param view
     * @param size
     * @param _flex
     */
    public void placeInside(IView parent, IView view, WH_F size, Flex _flex);

    /**
     *
     * @param _parent
     * @param g
     * @param _layer
     * @param mode
     * @param _painted
     */
    public void paintPlacer(IView _parent, ICanvas g, Layer _layer, int mode, XYWH_I _painted);

    /**
     *
     * @param parent
     * @param e
     * @return
     */
    public IView disbatchEvent(IView parent, AViewEvent e);

    /**
     *
     * @param _who
     * @return
     */
    public IView transferFocusToChild(long _who);
}
