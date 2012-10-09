/*
 * EKey.java.java
 *
 * Created on 01-03-2010 01:31:34 PM
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
package colt.nicity.view.event;

import colt.nicity.view.adaptor.IEventConstants;
import colt.nicity.view.core.PrimativeEvent;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class EKey {

    IView view;
    PrimativeEvent e;

    /**
     *
     * @param _view
     * @param _e
     */
    public EKey(IView _view, PrimativeEvent _e) {
        view = _view;
        e = _e;
    }

    /**
     *
     * @return
     */
    public int getModifiers() {
        return e.modifiers;
    }

    /**
     *
     * @return
     */
    public boolean isModifierDown() {
        if (isShiftDown()
                || isAltDown()
                || isControlDown()
                || isMetaDown()) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean isShiftDown() {
        return (e.modifiers & IEventConstants.cShiftMask) != 0;
    }

    /**
     *
     * @return
     */
    public boolean isControlDown() {
        return (e.modifiers & IEventConstants.cCrtlMask) != 0;
    }

    /**
     *
     * @return
     */
    public boolean isMetaDown() {
        return (e.modifiers & IEventConstants.cMetaMask) != 0;
    }

    /**
     *
     * @return
     */
    public boolean isAltDown() {
        return (e.modifiers & IEventConstants.cAltMask) != 0;
    }

    /**
     *
     * @return
     */
    public boolean isLeftMouseDown() {
        return ((e.modifiers & IEventConstants.cButton1DownMask) == IEventConstants.cButton1DownMask);
    }

    /**
     *
     * @return
     */
    public boolean isLeftClick() {
        return ((e.modifiers & IEventConstants.cButton1Mask) == IEventConstants.cButton1Mask);
    }

    /**
     *
     * @return
     */
    public boolean isMiddleClick() {
        return ((e.modifiers & IEventConstants.cButton2Mask) == IEventConstants.cButton2Mask);
    }

    /**
     *
     * @return
     */
    public boolean isRightClick() {
        return ((e.modifiers & IEventConstants.cButton3Mask) == IEventConstants.cButton3Mask);
    }
}
