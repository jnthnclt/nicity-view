/*
 * NullPlacer.java.java
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
package colt.nicity.view.core;

import colt.nicity.view.event.AViewEvent;
import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IPlacer;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IViewable;

/**
 *
 * @author Administrator
 */
public class NullPlacer implements IPlacer {

    /**
     *
     */
    public static final IPlacer cNull = new NullPlacer();

    public IPlacer getPlacer() {
        return cNull;
    }

    public IViewable getViewable() {
        return null;
    }

    public void setViewable(IViewable viewable) {
    }

    public Place getPlace() {
        return UV.cOrigin;
    }

    public void setPlace(Place _place) {
    }

    public void placeInside(IView parent, WH_F size, Flex _flex) {
    }

    public void placeInside(IView parent, IView view, WH_F size, Flex _flex) {
    }

    public void paintPlacer(IView _parent, ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
    }

    public IView disbatchEvent(IView parent, AViewEvent e) {
        return NullView.cNull;
    }

    public IView transferFocusToChild(long _who) {
        return NullView.cNull;
    }

    // IViewable
    public IView getView() {
        return NullView.cNull;
    }
}
