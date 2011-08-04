/*
 * AViewEvent.java.java
 *
 * Created on 01-03-2010 01:31:38 PM
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

import colt.nicity.view.core.NullView;
import colt.nicity.view.interfaces.IView;
import java.awt.AWTEvent;

/**
 *
 * @author Administrator
 */
abstract public class AViewEvent extends AEvent {

    /**
     *
     */
    public final static long cFocusEvent = AWTEvent.FOCUS_EVENT_MASK;
    /**
     *
     */
    public final static long cKeyEvent = AWTEvent.KEY_EVENT_MASK;
    /**
     *
     */
    public final static long cMouseEvent = AWTEvent.MOUSE_EVENT_MASK;
    /**
     *
     */
    public final static long cMouseMotionEvent = AWTEvent.MOUSE_MOTION_EVENT_MASK;
    /**
     *
     */
    public final static long cWindowEvent = AWTEvent.WINDOW_EVENT_MASK;

    /**
     *
     */
    public AViewEvent() {
    }

    /**
     *
     * @param _source
     */
    public AViewEvent(Object _source) {
        super(_source);
    }
    // Usually overriden

    /**
     *
     * @param parent
     * @param view
     * @return
     */
    public IView disbatchEvent(IView parent, IView view) {
        return NullView.cNull;
    }

    /**
     *
     * @param _e
     */
    public void inherit(AViewEvent _e) {
        source = _e.source;
    }
}
