/*
 * AKeyEvent.java.java
 *
 * Created on 01-03-2010 01:31:35 PM
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

import colt.nicity.view.adaptor.IViewEventConstants;
import colt.nicity.view.core.NullView;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
abstract public class AKeyEvent extends AInputEvent {

    private int keyCode;
    private char keyChar;

    /**
     *
     * @return
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     *
     * @param keyCode
     */
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     *
     * @return
     */
    public char getKeyChar() {
        return keyChar;
    }

    /**
     *
     * @param keyChar
     */
    public void setKeyChar(char keyChar) {
        this.keyChar = keyChar;
    }

    /**
     *
     * @param parent
     * @param view
     * @return
     */
    @Override
    public IView disbatchEvent(IView parent, IView view) {
        if (view == null) {
            return NullView.cNull;
        }
        if (!(view.isEventEnabled(IViewEventConstants.cKeyEvent))) {
            return NullView.cNull;
        }
        this.setSource(view);
        return view;
    }

    /**
     *
     * @return
     */
    public long getMask() {
        return IViewEventConstants.cKeyEvent;
    }

    @Override
    public String toString() {
        return super.toString() + " keyCode=" + keyCode + " keyChar=" + (char) keyChar;
    }
}
