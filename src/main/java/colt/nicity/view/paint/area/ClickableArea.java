/*
 * ClickableArea.java.java
 *
 * Created on 01-30-2010 06:56:10 PM
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

import colt.nicity.view.flavor.AFlavorCondition;
import colt.nicity.core.lang.UArray;
import colt.nicity.core.memory.struct.V_D;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.flavor.AFlavor;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
abstract public class ClickableArea extends RectPaintedArea {
    /**
     *
     */
    abstract public void picked();
    /**
     *
     */
    abstract public void selected();
    /**
     *
     */
    public void entered() {}
    /**
     *
     */
    public void exited() {}

    /**
     *
     * @param _flavor
     * @return
     */
    public AFlavorCondition whenOver(final AFlavor _flavor) {
        return new AFlavorCondition<ClickableArea>() {
            @Override
            public AFlavor paintFlavor(ClickableArea _condition) {
                if (_condition.over != null) return _flavor.paintFlavor(_condition);
                return null;
            }
        };
    }
    /**
     *
     * @param _flavor
     * @return
     */
    public AFlavorCondition whenPressed(final AFlavor _flavor) {
        return new AFlavorCondition<ClickableArea>() {
            @Override
            public AFlavor paintFlavor(ClickableArea _condition) {
                if (_condition.pressed != null) return _flavor.paintFlavor(_condition);
                return null;
            }
        };
    }

    /**
     *
     */
    public PaintableEvent over;
    /**
     *
     */
    public PaintableEvent pressed;
    /**
     *
     */
    public AFlavorCondition[] flavorConditions = new AFlavorCondition[0];

    /**
     *
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public ClickableArea(V_D _x,V_D _y,V_D _w,V_D _h) {
        super(_x, _y, _w, _h);
    }
    /**
     *
     * @param _g
     * @param _xywh
     */
    @Override
    public void paint(ICanvas _g,XYWH_I _xywh) {
        for(AFlavorCondition fc:flavorConditions) {
            AFlavor f = fc.paintFlavor(this);
            if (f != null) _g.paintFlavor(f, _xywh.x, _xywh.y, _xywh.w, _xywh.h, ViewColor.cTheme);
        }
    }
    /**
     *
     * @param _flavorCondition
     */
    public void add(AFlavorCondition _flavorCondition) {
        flavorConditions = (AFlavorCondition[])UArray.push(flavorConditions, _flavorCondition,AFlavorCondition.class);
    }
    
    /**
     *
     * @param _flavorCondition
     */
    public void remove(AFlavorCondition _flavorCondition) {

    }


    /**
     *
     * @param _e
     */
    @Override
    public void mouse(PaintableEvent _e) {
        if (_e.mode == UPaintableEvent.cExited) {
            over = null;
        } else {
            over = _e;
        }
        if (_e.mode == UPaintableEvent.cPressed) {
            pressed = _e;
        }
        if (_e.mode == UPaintableEvent.cReleased) {
            if (_e.m.isDoubleClick()) {
                selected();
            }
            else if (_e.m.isSingleClick()) {
                picked();
            }
            pressed = null;
        }
    }
}
