/*
 * IPlacers.java.java
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
import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.core.Flex;
import colt.nicity.view.core.Layer;

/**
 *
 * @author Administrator
 */
public interface IPlacers {

    /**
     *
     * @param parent
     * @param e
     * @return
     */
    public IView disbatchEventToPlacers(IView parent, AViewEvent e);

    /**
     *
     * @param parent
     * @param anchor
     * @param size
     * @param _flex
     */
    public void placeInside(IView parent, IView anchor, WH_F size, Flex _flex);

    /**
     *
     * @param _parent
     * @param g
     * @param _layer
     * @param mode
     * @param _painted
     */
    public void paintPlacers(IView _parent, ICanvas g, Layer _layer, int mode, XYWH_I _painted);

    /**
     *
     * @param __who
     * @return
     */
    public IView transferFocusToChild(long __who);

    /**
     *
     * @param value
     * @return
     */
    public IPlacer addPlacer(IPlacer value);

    /**
     *
     * @param value
     * @return
     */
    public IPlacer findPlacer(IPlacer value);

    /**
     *
     * @param view
     * @return
     */
    public IPlacer findView(IView view);

    /**
     *
     * @param value
     * @return
     */
    public IPlacer removePlacer(IPlacer value);

    /**
     *
     * @param view
     * @return
     */
    public IPlacer removeView(IView view);

    /**
     *
     * @return
     */
    public int size();

    /**
     *
     * @return
     */
    public Object[] toArray();

    /**
     *
     */
    public void clear();
}
