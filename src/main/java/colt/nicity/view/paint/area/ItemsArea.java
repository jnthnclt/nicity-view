/*
 * ItemsArea.java.java
 *
 * Created on 01-31-2010 09:39:29 AM
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

import colt.nicity.core.lang.UArray;
import colt.nicity.core.memory.struct.V_D;

/**
 *
 * @author Administrator
 */
public class ItemsArea extends RectPaintedArea implements IPaintableCollection {
    /**
     *
     */
    public int rows;
    /**
     *
     */
    public int columns;
    ItemArea[] items = new ItemArea[0];

    private V_D countH = new V_D() {
        public double getV() {
            return (ItemsArea.this.h.getV()/items.length);
        }
    };
    private V_D proxyX = new V_D() {
        public double getV() {
            return ItemsArea.this.x.getV();
        }
    };
    private V_D proxyW = new V_D() {
        public double getV() {
            return ItemsArea.this.w.getV();
        }
    };

    /**
     *
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _rows
     * @param _columns
     */
    public ItemsArea(V_D _x,V_D _y,V_D _w,V_D _h,int _rows,int _columns) {
        super(_x, _y, _w, _h);
        rows = _rows;
        columns = _columns;
    }
    /**
     *
     * @param _areas
     */
    public void add(ItemArea... _areas) {
        items = (ItemArea[])UArray.push(items, _areas, new ItemArea[items.length+_areas.length]);
        for(int i=0;i<items.length;i++) {
            final int ii = i;
            items[i].x = proxyX;
            items[i].y = new V_D() {
                public double getV() {
                    double h = ii*countH.getV();
                    return ItemsArea.this.y.getV()+h;
                }
            };
            items[i].w = proxyW;
            items[i].h = countH;
        }
    }

    /**
     *
     * @return
     */
    public IPaintableArea[] areas() {
        return items;
    }

}
