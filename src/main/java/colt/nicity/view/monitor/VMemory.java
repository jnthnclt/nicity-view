/*
 * VMemory.java.java
 *
 * Created on 01-03-2010 01:32:53 PM
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
package colt.nicity.view.monitor;

import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VMemory extends Viewer {

    /**
     *
     */
    public VMemory() {
        setContent(aboutMemory());
    }

    private IView aboutMemory() {
        VChain c = new VChain(UV.cSWNW);
        Object s = new Object() {

            @Override
            public String toString() {
                return "Processors:" + Runtime.getRuntime().availableProcessors();
            }
        };
        c.add(new VString(s, UV.fonts[UV.cSmall]));

        s = new Object() {

            @Override
            public String toString() {
                return "Free Memory:" + (Runtime.getRuntime().freeMemory() / 1024) + "kb";
            }
        };
        c.add(new VString(s, UV.fonts[UV.cSmall]));

        s = new Object() {

            @Override
            public String toString() {
                return "Total Memory:" + (Runtime.getRuntime().totalMemory() / 1024) + "kb";
            }
        };
        c.add(new VString(s, UV.fonts[UV.cSmall]));

        s = new Object() {

            @Override
            public String toString() {
                return "Max Memory:" + (Runtime.getRuntime().maxMemory() / 1024) + "kb";
            }
        };
        c.add(new VString(s, UV.fonts[UV.cSmall]));


        VButton gc = new VButton("Reclaim Memory", UV.fonts[UV.cSmall]) {

            @Override
            public void picked(IEvent _e) {
                Runtime.getRuntime().gc();
            }
        };
        c.add(gc);
        return c;
    }
}
