/*
 * Paintable.java.java
 *
 * Created on 03-12-2010 06:42:20 PM
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
package colt.nicity.view.paint;

import colt.nicity.view.paint.lens.ALens;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.observer.IObserver;
import colt.nicity.core.value.Value;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IPaintable;

/**
 *
 * @author Administrator
 */
public class Paintable implements IPaintable {
    private Value lens = new Value();
    private Value paintables = new Value();
    /**
     *
     */
    public Paintable() {
        
    }
    /**
     *
     * @param _observer
     */
    public void observeLens(IObserver _observer) {
        lens.bind(_observer);
    }
    /**
     *
     * @param _observer
     */
    public void observePaintables(IObserver _observer) {
        paintables.bind(_observer);
    }

    /**
     *
     * @param _lens
     */
    public void setLens(ALens _lens) {
        lens.setValue(_lens);
    }
    /**
     *
     * @param _paintables
     */
    public void setPaintable(Object _paintables) {
        paintables.setValue(_paintables);
    }


    /**
     *
     * @param g
     * @param xywh
     */
    public void paint(ICanvas g,XYWH_I xywh) {
        Object l = lens.getValue();
        if (l instanceof ALens) {
            
        }
        Object paint = paintables.getValue();
        if (paint instanceof IPaintable) {
            ((IPaintable)paint).paint(g,xywh);
        }
        if (paint instanceof IPaintable[]) {
            IPaintable[] ps = (IPaintable[])paint;
            for(int i=0;i<ps.length;i++) {
                ((IPaintable)paint).paint(g,xywh);
            }
        }
    }

    /**
     *
     * @param _under
     * @param _over
     * @return
     */
    public float getW(IPaintable _under,IPaintable _over) {
        Object paint = paintables.getValue();
        if (paint instanceof IPaintable) {
            return ((IPaintable)paint).getW(null,null);
        }
        if (paint instanceof IPaintable[]) {
            IPaintable[] ps = (IPaintable[])paint;
            float max = 0;
            for(int i=0;i<ps.length;i++) {
                IPaintable over = null;
                IPaintable under = null;
                if (i > 0) over = ps[i-1];
                if (i < ps.length) over = ps[i+1];
                float v = ps[i].getW(under,over);
                if (v > max) max = v;
            }
            return max;
        }
        return 0;
    }

    /**
     *
     * @param _under
     * @param _over
     * @return
     */
    public float getH(IPaintable _under,IPaintable _over) {
        Object paint = paintables.getValue();
        if (paint instanceof IPaintable) {
            return ((IPaintable)paint).getH(null,null);
        }
        if (paint instanceof IPaintable[]) {
            IPaintable[] ps = (IPaintable[])paint;
            float max = 0;
            for(int i=0;i<ps.length;i++) {
                IPaintable over = null;
                IPaintable under = null;
                if (i > 0) over = ps[i-1];
                if (i < ps.length) over = ps[i+1];
                float v = ps[i].getH(under,over);
                if (v > max) max = v;
            }
            return max;
        }
        return 0;
    }

}
