/*
 * Painter.java.java
 *
 * Created on 01-31-2010 10:01:23 AM
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

import com.colt.nicity.core.collection.CArray;
import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.comparator.AValueComparator;
import com.colt.nicity.core.lang.UArray;
import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.border.ViewBorder;
import com.colt.nicity.view.event.AKeyEvent;
import com.colt.nicity.view.event.AMouseEvent;
import com.colt.nicity.view.event.KeyPressed;
import com.colt.nicity.view.event.KeyReleased;
import com.colt.nicity.view.event.KeyTyped;
import com.colt.nicity.view.event.MouseDragged;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MouseMoved;
import com.colt.nicity.view.event.MousePressed;
import com.colt.nicity.view.event.MouseReleased;
import com.colt.nicity.view.core.VPan;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IKeyEvents;
import com.colt.nicity.view.interfaces.IMouseEvents;
import com.colt.nicity.view.interfaces.IMouseMotionEvents;
import com.colt.nicity.view.interfaces.IMouseWheelEvents;
import com.colt.nicity.view.paint.lens.LensStack;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class Painter extends VPan implements IMouseMotionEvents, IMouseEvents, IKeyEvents, IMouseWheelEvents {
    /**
     *
     */
    public LensStack lensStack = new LensStack();
    CArray<IPaintableArea> paintables = new CArray<IPaintableArea>(IPaintableArea.class);
    /**
     *
     * @param _w
     * @param _h
     */
    public Painter(int _w,int _h) {
        super(new Viewer(), _w, _h);
        setBorder(new ViewBorder());
    }

    /**
     *
     * @param _areas
     */
    public void add(IPaintableArea... _areas) {
        for (IPaintableArea a:_areas) {
            paintables.insertLast(a);
            a.begin(this);
        }
        sorted = null;
    }

    /**
     *
     * @param _areaTransitions
     */
    public void add(AreaTransition... _areaTransitions) {
        
    }

    /**
     *
     * @param _old
     * @param _new
     */
    public void swap(IPaintableArea _old,IPaintableArea _new) {
        IPaintableArea[] areas = paintables.getAll();
        for(int i=0;i<areas.length;i++) {
            if (areas[i] == _old) {
                areas[i] = _new;
                _old.end(this);
            }
        }
        paintables.removeAll();
        paintables.insertFirst(areas);
        sorted = null;
        paint();
    }

    /**
     *
     * @param _areaTransitions
     */
    public void move(AreaTransition... _areaTransitions) {
        IPaintableArea[] areas = paintables.getAll();
        for(AreaTransition at:_areaTransitions) {
            for(int i=0;i<areas.length;i++) {
                if (areas[i] == at.getArea()) areas[i] = at;
            }
        }
        for(AreaTransition at:_areaTransitions) {
            at.begin(this);
        }
        paintables.removeAll();
        paintables.insertFirst(areas);
        sorted = null;
        paint();
    }

    /**
     *
     * @param _areaTransitions
     */
    public void remove(AreaTransition... _areaTransitions) {

    }

    /**
     *
     * @param _areas
     */
    public void remove(IPaintableArea... _areas) {

    }


    /**
     *
     */
    public void clear() {
        paintables.removeAll();
        sorted = null;
    }

    private IPaintableArea[] sorted;
    private IPaintableArea[] areas() {
        IPaintableArea[] _sorted = sorted;
        if (_sorted != null) return _sorted;
        IPaintableArea[] areas = paintables.getAll();
        CArray<IPaintableArea> expand = new CArray<IPaintableArea>(IPaintableArea.class);
        expand(expand,areas);
        areas = expand.getAll();

        Arrays.sort(areas,new AValueComparator<IPaintableArea>(AValueComparator.cDescending) {
            @Override
            public Object value(IPaintableArea _value) {
                return _value.getLayer();
            }
        });
        sorted = areas;
        return sorted;
    }
    private void expand(CArray<IPaintableArea> _expand,IPaintableArea[] areas) {
        for(int i=0;i<areas.length;i++) {
            IPaintableArea area = areas[i];
            _expand.insertLast(area);
            if (area instanceof AreaTransition) {
                area = ((AreaTransition)area).getArea();
            }
            if (area instanceof IPaintableCollection) {
                IPaintableArea[] _areas = ((IPaintableCollection)area).areas();
                expand(_expand,_areas);
            }
        }
    }


    private int lx,ly,lw,lh;
    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    @Override
    public void paintBorder(ICanvas _g, int _x, int _y, int _w, int _h) {
        lx = _x;ly = _y;lh = _h;lw = _w;
        paintAreas(areas(),_g,_x,_y,_w,_h);
    }
    private void paintAreas(IPaintableArea[] areas,ICanvas _g, int _x, int _y, int _w, int _h) {
        if (areas == null) return;
        for(int i=0;i<areas.length;i++) {
            IPaintableArea area = areas[i];
            area.paint(_g, area.visibleBounds(lensStack,_x,_y,_w,_h));
        }
    }

    private IPaintableArea[] over(XY_I p) {
        IPaintableArea[] over = new IPaintableArea[0];
        IPaintableArea[] areas = areas();
        for(int i=areas.length-1;i>-1;i--) {
            XYWH_I bounds = areas[i].visibleBounds(lensStack,lx,ly,lw,lh);
            if (bounds.contains(p)) {
                over = (IPaintableArea[])UArray.push(over, areas[i], IPaintableArea.class);
            }
        }
        return over;
    }

    
    // IMouseEvents
    IPaintableArea[] pressed = new IPaintableArea[0];
    /**
     *
     * @param _e
     */
    @Override
    public void mousePressed(MousePressed _e) {
        super.mousePressed(_e);
        pressed = over(_e.getPoint());
        for(IPaintableArea p:pressed) p.mouse(new PaintableEvent(lensStack,lx,ly,lw,lh,UPaintableEvent.cPressed,(AMouseEvent)_e,null));
        paint();
    }

    IPaintableArea[] released = new IPaintableArea[0];
    /**
     *
     * @param _e
     */
    @Override
    public void mouseReleased(MouseReleased _e) {
        super.mouseReleased(_e);
        released = over(_e.getPoint());
        for(IPaintableArea r:released) r.mouse(new PaintableEvent(lensStack,lx,ly,lw,lh,UPaintableEvent.cReleased,(AMouseEvent)_e,null));
        paint();
    }

    IPaintableArea[] moved = new IPaintableArea[0];
    /**
     *
     * @param _e
     */
    @Override
    public void mouseMoved(MouseMoved _e) {
        super.mouseMoved(_e);
        IPaintableArea[] over = over(_e.getPoint());
        CSet<IPaintableArea> set = new CSet<IPaintableArea>(over);
        for(IPaintableArea m:moved) {
            if (set.contains(m)) {
                set.remove(m);
            }
            else {
                m.mouse(new PaintableEvent(lensStack,lx,ly,lw,lh,UPaintableEvent.cExited, _e,null));
            }
        }
        for (IPaintableArea entered:set.getAll(IPaintableArea.class)) {
            entered.mouse(new PaintableEvent(lensStack,lx,ly,lw,lh,UPaintableEvent.cEntered, _e,null));
        }
        for(IPaintableArea o:over) o.mouse(new PaintableEvent(lensStack,lx,ly,lw,lh,UPaintableEvent.cMoved,(AMouseEvent)_e,null));
        moved = over;
        paint();
    }

    /**
     *
     * @param _e
     */
    @Override
    public void mouseExited(MouseExited _e) {
        super.mouseExited(_e);
        for(IPaintableArea m:moved) m.mouse(new PaintableEvent(lensStack,lx,ly,lw,lh,UPaintableEvent.cExited,(AMouseEvent)_e,null));
        paint();
    }


    /**
     *
     * @param _e
     */
    @Override
    public void mouseDragged(MouseDragged _e) {
        super.mouseDragged(_e);
        for(IPaintableArea p:pressed) p.mouse(new PaintableEvent(lensStack,lx,ly,lw,lh,UPaintableEvent.cDragged,(AMouseEvent)_e,null));
        IPaintableArea[] over = over(_e.getPoint());
        for(IPaintableArea o:over) o.mouse(new PaintableEvent(lensStack,lx,ly,lw,lh,UPaintableEvent.cMoved,(AMouseEvent)_e,null));
        paint();
    }

    IPaintableArea[] focused = new IPaintableArea[0];
    // IKeyEvents
    /**
     *
     * @param e
     */
    public void keyPressed(KeyPressed e) {
        for(IPaintableArea f:focused) f.key(new PaintableEvent(lensStack,lx,ly,lw,lh,UPaintableEvent.cPressed,null,(AKeyEvent)e));
        paint();
    }

    /**
     *
     * @param e
     */
    public void keyReleased(KeyReleased e) {
        for(IPaintableArea f:focused) f.key(new PaintableEvent(lensStack,lx,ly,lw,lh,UPaintableEvent.cReleased,null,(AKeyEvent)e));
        paint();
    }

    /**
     *
     * @param e
     */
    public void keyTyped(KeyTyped e) {
        
    }


}
