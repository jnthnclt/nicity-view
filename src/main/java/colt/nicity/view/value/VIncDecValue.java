/*
 * VIncDecValue.java.java
 *
 * Created on 03-12-2010 06:39:37 PM
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
package colt.nicity.view.value;

import colt.nicity.core.value.Value;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IEvent;

/**
 *
 * @author Jonathan Colt
 * Mar 3, 2008
 */
public class VIncDecValue extends Viewer {

    Value v;
    int min, max;

    /**
     *
     * @param _v
     * @param _min
     * @param _max
     */
    public VIncDecValue(Value _v, int _min, int _max) {
        v = _v;
        min = _min;
        max = _max;
        
        VChain c = new VChain(UV.cEW);
        c.add(new VButton(" - ") {
            @Override
            public void picked(IEvent _e) {
                int nv = v.intValue() - 1;
                if (nv < min) {
                    nv = min;
                }
                if (nv > max) {
                    nv = max;
                }
                v.setValue(nv);
            }
        });
        c.add(new VEditValue(_v, true, 32));
        c.add(new VButton(" + ") {
            @Override
            public void picked(IEvent _e) {
                int nv = v.intValue() + 1;
                if (nv < min) {
                    nv = min;
                }
                if (nv > max) {
                    nv = max;
                }
                v.setValue(nv);
            }
        });
        setContent(c);
    }
}
