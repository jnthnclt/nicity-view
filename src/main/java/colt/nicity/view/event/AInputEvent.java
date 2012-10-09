/*
 * AInputEvent.java.java
 *
 * Created on 01-03-2010 01:30:23 PM
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

/**
 *
 * @author Administrator
 */
abstract public class AInputEvent extends AViewEvent {

    /**
     *
     */
    protected int modifiers;

    /**
     *
     * @return
     */
    public int getModifiers() {
        return modifiers;
    }

    /**
     *
     * @param modifiers
     */
    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
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
        return (modifiers & IEventConstants.cShiftMask) != 0;
    }

    /**
     *
     * @return
     */
    public boolean isControlDown() {
        return (modifiers & IEventConstants.cCrtlMask) != 0;
    }

    /**
     *
     * @return
     */
    public boolean isMetaDown() {
        return (modifiers & IEventConstants.cMetaMask) != 0;
    }

    /**
     *
     * @return
     */
    public boolean isAltDown() {
        return (modifiers & IEventConstants.cAltMask) != 0;
    }

    /**
     *
     * @return
     */
    public boolean isLeftMouseDown() {
        return ((modifiers & IEventConstants.cButton1DownMask) == IEventConstants.cButton1DownMask);
    }

    /**
     *
     * @return
     */
    public boolean isLeftClick() {
        return ((modifiers & IEventConstants.cButton1Mask) == IEventConstants.cButton1Mask);
    }

    /**
     *
     * @return
     */
    public boolean isMiddleClick() {
        return ((modifiers & IEventConstants.cButton2Mask) == IEventConstants.cButton2Mask);
    }

    /**
     *
     * @return
     */
    public boolean isRightClick() {
        return ((modifiers & IEventConstants.cButton3Mask) == IEventConstants.cButton3Mask);
    }

    @Override
    public String toString() {
        return "mod = " + modifiers;
    }

    /**
     *
     * @param _e
     */
    public void inherit(AInputEvent _e) {
        modifiers = _e.modifiers;
        super.inherit(_e);
    }
}
