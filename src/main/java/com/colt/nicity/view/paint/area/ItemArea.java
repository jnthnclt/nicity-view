/*
 * ItemArea.java.java
 *
 * Created on 01-30-2010 06:12:48 PM
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

import com.colt.nicity.core.memory.struct.V_D;
import com.colt.nicity.view.flavor.AFlavor;
import com.colt.nicity.view.flavor.AFlavorCondition;

/**
 *
 * @author Administrator
 */
abstract public class ItemArea extends ClickableArea {
    
    /**
     *
     */
    public PaintableEvent choosen;

    /**
     *
     * @param _flavor
     * @return
     */
    @Override
    public AFlavorCondition whenOver(final AFlavor _flavor) {
        return new AFlavorCondition<ItemArea>() {
            @Override
            public AFlavor paintFlavor(ItemArea _condition) {
                if (_condition.choosen == null && _condition.over != null) return _flavor.paintFlavor(_condition);
                return null;
            }
        };
    }
    /**
     *
     * @param _flavor
     * @return
     */
    public AFlavorCondition whenChoosen(final AFlavor _flavor) {
        return new AFlavorCondition<ItemArea>() {
            @Override
            public AFlavor paintFlavor(ItemArea _condition) {
                if (_condition.choosen != null) return _flavor.paintFlavor(_condition);
                return null;
            }
        };
    }

    /**
     *
     * @param _flavor
     * @return
     */
    public AFlavorCondition whenOverChoosen(final AFlavor _flavor) {
        return new AFlavorCondition<ItemArea>() {
            @Override
            public AFlavor paintFlavor(ItemArea _condition) {
                if (_condition.over != null && _condition.choosen != null) return _flavor.paintFlavor(_condition);
                return null;
            }
        };
    }

    /**
     *
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public ItemArea(V_D _x,V_D _y,V_D _w,V_D _h) {
        super(_x, _y, _w, _h);
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
                if (choosen == null) choosen = _e;
                else choosen = null;
            }
            pressed = null;
        }
    }
}
