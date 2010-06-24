/*
 * RectPaintedArea.java.java
 *
 * Created on 01-30-2010 10:47:26 PM
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

package colt.nicity.view.paint.area;

import colt.nicity.core.memory.struct.UXYWH_I;
import colt.nicity.core.memory.struct.V_D;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_D;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.paint.lens.ALens;

/**
 *
 * @author Administrator
 */
public class RectPaintedArea implements IPaintableArea {
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public V_D x,y,w,h;
    /**
     *
     */
    public Painter painter;
    /**
     *
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public RectPaintedArea(V_D _x,V_D _y,V_D _w,V_D _h) {
        x = _x;
        y = _y;
        w = _w;
        h = _h;
    }
    /**
     *
     * @param _painter
     */
    public void begin(Painter _painter) {
        painter = _painter;
    }
    /**
     *
     * @param _painter
     */
    public void end(Painter _painter) {

    }

    /**
     *
     * @return
     */
    public double getLayer() {
        return 0;
    }
    
    /**
     *
     * @param _g
     * @param _xywh
     */
    public void paint(ICanvas _g,XYWH_I _xywh) {
        
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
        XY_D ul = new XY_D(x.getV(),y.getV());
        XY_D ur = new XY_D(x.getV()+w.getV(),y.getV());
        XY_D ll = new XY_D(x.getV(),y.getV()+h.getV());
        XY_D lr = new XY_D(x.getV()+w.getV(),y.getV()+h.getV());
        _lens.applyLens(ul);
        _lens.applyLens(ur);
        _lens.applyLens(ll);
        _lens.applyLens(lr);

        return UXYWH_I.boundingBox(
                new XY_I((int)(_x+(ul.x*_w)),(int)(_y+(ul.y*_h))),
                new XY_I((int)(_x+(ur.x*_w)),(int)(_y+(ur.y*_h))),
                new XY_I((int)(_x+(ll.x*_w)),(int)(_y+(ll.y*_h))),
                new XY_I((int)(_x+(lr.x*_w)),(int)(_y+(lr.y*_h)))
        );
    }

    /**
     *
     * @param _e
     */
    public void key(PaintableEvent _e) {
        
    }

    /**
     *
     * @param _e
     */
    public void mouse(PaintableEvent _e) {
        
    }

    

    
}
