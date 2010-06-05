/*
 * VEditColor.java.java
 *
 * Created on 03-12-2010 06:41:38 PM
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
package com.colt.nicity.view.value;

import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.core.Viewer;

/**
 *
 * @author Administrator
 */
public class VEditColor extends Viewer {

    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        ViewColor.onBlack();
        UV.exitFrame(new Viewer(new VEditColor(new Value<AColor>(AColor.blue))), "");
    }

    Value<AColor> color;
    /**
     *
     * @param _color
     */
    public VEditColor(Value<AColor> _color) {
        color = _color;
        setContent(new VColorWheel(color, 100));
    }
}
