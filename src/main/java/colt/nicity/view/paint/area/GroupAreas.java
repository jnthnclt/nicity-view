/*
 * GroupAreas.java.java
 *
 * Created on 01-31-2010 09:30:43 PM
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



import colt.nicity.core.lang.MinMaxDouble;
import colt.nicity.core.lang.UArray;
import colt.nicity.core.memory.struct.V_D;

/**
 *
 * @author Administrator
 */
public class GroupAreas extends RectPaintedArea implements IPaintableCollection {
    ItemArea[] items = new ItemArea[0];

    /**
     *
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public GroupAreas(V_D _x,V_D _y,V_D _w,V_D _h) {
        super(_x, _y, _w, _h);
    }
    /**
     *
     * @param _areas
     */
    public void add(ItemArea... _areas) {
        items = (ItemArea[])UArray.push(items, _areas, new ItemArea[items.length+_areas.length]);
        for(int i=0;i<items.length;i++) {
            if (!(items[i].x instanceof GroupV)) items[i].x = new GroupV(items[i].x,0);
            if (!(items[i].y instanceof GroupV)) items[i].y = new GroupV(items[i].y,1);
            if (!(items[i].w instanceof GroupV)) items[i].w = new GroupV(items[i].w,2);
            if (!(items[i].h instanceof GroupV)) items[i].h = new GroupV(items[i].h,3);          
        }
    }

    /**
     *
     * @return
     */
    public IPaintableArea[] areas() {
        return items;
    }

    class GroupV extends V_D {
        V_D was;
        int mode;
        GroupV(V_D _was,int _mode) {
            was = _was;
            mode = _mode;
        }
        @Override
        public double getV() {
            if (mode == 0) return MinMaxDouble.unzeroToOne(x.getV(), x.getV()+w.getV(), was.getV());
            else if (mode == 1) return MinMaxDouble.unzeroToOne(y.getV(), y.getV()+h.getV(), was.getV());
            else if (mode == 2) return w.getV()*was.getV();
            else return h.getV()*was.getV();

        }
        @Override
        public void setV(double _v) {
            if (mode == 0) was.setV(MinMaxDouble.unzeroToOne(x.getV(), x.getV()+w.getV(),_v));
            else if (mode == 1) was.setV(MinMaxDouble.unzeroToOne(y.getV(), y.getV()+h.getV(),_v));
            else if (mode == 2) was.setV(w.getV()/_v);
            else was.setV(h.getV()/_v);
        }
    }

}
