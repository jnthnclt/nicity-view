/*
 * AreaTransition.java.java
 *
 * Created on 01-30-2010 10:48:19 PM
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

import com.colt.nicity.core.lang.UDouble;
import com.colt.nicity.core.lang.URandom;
import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.paint.lens.ALens;

/**
 *
 * @author Administrator
 */
public class AreaTransition implements IPaintableArea {
    Painter painter;
    RectPaintedArea area;
    long started;
    long duration;

    double sx,sy,sw,sh;
    double nx,ny,nw,nh;
    /**
     *
     * @param _area
     * @param _duration
     */
    public AreaTransition(RectPaintedArea _area,long _duration) {
        area = _area;
        duration = _duration;

        sx = _area.x.getV();
        sy = _area.y.getV();
        sw = _area.w.getV();
        sh = _area.h.getV();

        nx = URandom.rand(0.75);
        ny = URandom.rand(0.75);
        nw = URandom.rand(0.5);
        nh = URandom.rand(0.5);
    }
    /**
     *
     * @return
     */
    public IPaintableArea getArea() {
        return area;
    }

    /**
     *
     * @param _painter
     */
    public void begin(Painter _painter) {
        painter = _painter;
        started = System.currentTimeMillis();
    }
    /**
     *
     * @param _painter
     */
    public void end(Painter _painter) {
        
    }

    /**
     *
     * @param _p
     */
    public void position(double _p) {
        area.x.setV(UDouble.linearInterpolation(sx, nx, _p));
        area.y.setV(UDouble.linearInterpolation(sy, ny, _p));
        area.w.setV(UDouble.linearInterpolation(sw, nw, _p));
        area.h.setV(UDouble.linearInterpolation(sh, nh, _p));
    }
    

    /**
     *
     * @return
     */
    public double getLayer() {
        return area.getLayer();
    }

    /**
     *
     * @param _g
     * @param _xywh
     */
    public void paint(ICanvas _g, XYWH_I _xywh) {
        area.paint(_g, _xywh);
        long t = System.currentTimeMillis();
        long d = t-started;
        double p = (double)d/(double)duration;
        if (p > 1) painter.swap(this, area);
        else position(p);
        painter.paint();//??
    }

    /**
     *
     * @param _lens
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @return
     */
    public XYWH_I visibleBounds(ALens _lens, int _x, int _y, int _w, int _h) {
        return area.visibleBounds(_lens, _x, _y, _w, _h);
    }

    /**
     *
     * @param _e
     */
    public void key(PaintableEvent _e) {
        area.key(_e);
    }

    /**
     *
     * @param _e
     */
    public void mouse(PaintableEvent _e) {
        area.mouse(_e);
    }
    
}
