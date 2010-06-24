/*
 * VTrapFlex.java.java
 *
 * Created on 01-03-2010 01:31:33 PM
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

import colt.nicity.view.interfaces.IPlacer;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VTrapFlex extends Viewer {

    /**
     *
     */
    public VTrapFlex() {
        super();
    }

    /**
     *
     * @param _placer
     */
    public VTrapFlex(IPlacer _placer) {
        super(_placer);
    }

    /**
     *
     * @param _view
     */
    public VTrapFlex(IView _view) {
        super(_view);
    }

    /**
     *
     * @param _view
     * @param _flex
     */
    public VTrapFlex(IView _view, Flex _flex) {
        super(_view, _flex);
    }

    /**
     *
     * @param _flex
     */
    protected void layoutParent(Flex _flex) {
        _flex.x = 0;
        _flex.y = 0;
        super.layoutParent(_flex);
    }
}
