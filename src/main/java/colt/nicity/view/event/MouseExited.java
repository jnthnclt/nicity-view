/*
 * MouseExited.java.java
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

/**
 *
 * @author Administrator
 */
public class MouseExited extends AMouseEvent {
    private Object entered;
    /**
     *
     * @param _who
     * @param source
     * @param x
     * @param y
     * @param clickCount
     * @param modifiers
     * @param _cx
     * @param _cy
     * @param _cw
     * @param _ch
     * @param _isDragging
     * @param _entered
     * @return
     */
    public static AMouseEvent newInstance(
        long _who,
		Object source,
		int x,int y,
		int clickCount,int modifiers,
		float _cx,float _cy,float _cw,float _ch,
		boolean _isDragging,
        Object _entered
	) {
		MouseExited e = new MouseExited();
		e.who = _who;
        e.setSource(source);
		e.setX(x);
		e.setY(y);
		e.setClickCount(clickCount);
		e.setModifiers(modifiers);
		e.cx = _cx;
		e.cy = _cy;
		e.cw = _cw;
		e.ch = _ch;
		e.isDragging = _isDragging;
        e.entered = _entered;
		return e;
	}
        /**
         *
         * @return
         */
        public Object getEntered() {
        return entered;
    }
}
