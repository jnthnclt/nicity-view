/*
 * VInternalPopup.java.java
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

import com.colt.nicity.view.event.AViewEvent;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VInternalPopup extends Viewer {

    /**
     *
     */
    public VInternalPopup() {
        super();
    }

    /**
     *
     * @param _view
     */
    public VInternalPopup(IView _view) {
        super(_view);
    }

    /**
     *
     * @param _view
     * @param _flex
     */
    public VInternalPopup(IView _view, Flex _flex) {
        super(_view, _flex);
    }

    @Override
    public IView disbatchEvent(IView parent, AViewEvent _e) {
        return super.disbatchEvent(parent, _e);
    }

    /**
     *
     * @param popupView
     * @param _relativeTo
     * @param _placement
     */
    public void add(IView popupView, IView _relativeTo, Place _placement) {
        XY_I placement = UV.getLocationInView(this, _relativeTo);
        //getPlacers().addPlacer();
    }
}
