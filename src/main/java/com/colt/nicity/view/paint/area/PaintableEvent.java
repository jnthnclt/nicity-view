/*
 * PaintableEvent.java.java
 *
 * Created on 01-30-2010 09:40:55 AM
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

package com.colt.nicity.view.paint.area;

import com.colt.nicity.core.lang.MinMaxDouble;
import com.colt.nicity.core.memory.struct.XY_D;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.event.AKeyEvent;
import com.colt.nicity.view.event.AMouseEvent;
import com.colt.nicity.view.paint.lens.ALens;

/**
 *
 * @author Administrator
 */
public class PaintableEvent {
    ALens lens;
    int x,y,w,h;
    String mode;
    AMouseEvent m;
    AKeyEvent k;
    /**
     *
     * @param _lens
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _mode
     * @param _m
     * @param _k
     */
    public PaintableEvent(ALens _lens,int _x,int _y,int _w,int _h,String _mode, AMouseEvent _m,AKeyEvent _k) {
        lens = _lens;
        x = _x;
        y = _y;
        w = _w;
        h = _h;
        mode = _mode;
        m = _m;
        k = _k;
    }
    /**
     *
     * @return
     */
    public boolean isMouse() { return m != null; }
    /**
     *
     * @return
     */
    public XY_D areaPoint() {
        XY_I p = m.getPoint();
        double nx = MinMaxDouble.zeroToOne(x, x+w, p.x);
        double ny = MinMaxDouble.zeroToOne(y, y+h, p.y);
        return new XY_D(nx,ny);
    }
    /**
     *
     * @return
     */
    public XY_D lensPoint() {
        XY_I p = m.getPoint();
        double nx = MinMaxDouble.zeroToOne(x, x+w, p.x);
        double ny = MinMaxDouble.zeroToOne(y, y+h, p.y);
        XY_D lp = new XY_D(nx,ny);
        lens.undoLens(lp);
        return lp;
    }

}
