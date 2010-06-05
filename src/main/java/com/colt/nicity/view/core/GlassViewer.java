/*
 * GlassViewer.java.java
 *
 * Created on 01-03-2010 01:31:34 PM
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
import com.colt.nicity.view.interfaces.IPlacer;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class GlassViewer extends Viewer {

    /**
     *
     */
    public GlassViewer() {
        super();
    }

    /**
     *
     * @param _placer
     */
    public GlassViewer(IPlacer _placer) {
        super(_placer);
    }

    /**
     *
     * @param _view
     */
    public GlassViewer(IView _view) {
        super(_view);
    }

    /**
     *
     * @param _view
     * @param _flex
     */
    public GlassViewer(IView _view, Flex _flex) {
        super(_view, _flex);
    }
    // causes all events to be intercepted

    @Override
    public IView disbatchEvent(IView parent, AViewEvent event) {
        return this;
    }

    /**
     *
     * @return
     */
    public IView transferFocusToChild() {
        return NullView.cNull;
    }
}
