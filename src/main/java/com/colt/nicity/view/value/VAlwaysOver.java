/*
 * VAlwaysOver.java.java
 *
 * Created on 01-03-2010 01:34:45 PM
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

import com.colt.nicity.view.core.Place;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VAlwaysOver extends VChain {

    IView over;

    /**
     *
     * @param _over
     * @param _under
     * @param _placement
     */
    public VAlwaysOver(IView _over, IView _under, Place _placement) {
        super(_placement, _under, _over);
        over = _over;
    }

    /**
     *
     */
    @Override
    public void mend() {
        enableFlag(UV.cRepair);
        over.enableFlag(UV.cRepair);
        super.mend();
    }
}
