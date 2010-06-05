/*
 * IPaintableArea.java.java
 *
 * Created on 01-30-2010 07:03:59 PM
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

import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.paint.lens.ALens;

/**
 *
 * @author Administrator
 */
public interface IPaintableArea {
    /**
     *
     * @param _painter
     */
    public void begin(Painter _painter);
    /**
     *
     * @param _painter
     */
    public void end(Painter _painter);
    /**
     *
     * @return
     */
    public double getLayer();
    /**
     *
     * @param _g
     * @param _xywh
     */
    public void paint(ICanvas _g,XYWH_I _xywh);
    /**
     *
     * @param _lens
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @return
     */
    public XYWH_I visibleBounds(ALens _lens,int _x,int _y,int _w,int _h);

    /**
     *
     * @param _e
     */
    public void key(PaintableEvent _e);
    /**
     *
     * @param _e
     */
    public void mouse(PaintableEvent _e);
}
