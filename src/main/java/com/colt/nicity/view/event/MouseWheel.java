/*
 * MouseWheel.java.java
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
package com.colt.nicity.view.event;

/**
 *
 * @author Administrator
 */
public class MouseWheel extends AMouseEvent {
	private int scrollType;
	private int scrollAmount;
	private int wheelRotation;
	//----------------------------------------------------------------------------	
        /**
         *
         * @param _who
         * @param source
         * @param x
         * @param y
         * @param clickCount
         * @param modifiers
         * @param _scrollType
         * @param _scrollAmount
         * @param _wheelRotation
         * @param _cx
         * @param _cy
         * @param _cw
         * @param _ch
         * @return
         */
        public static AMouseEvent newInstance(
            long _who,
		Object source,
		int x,int y,
		int clickCount,int modifiers,
		int _scrollType,int _scrollAmount,int _wheelRotation,
		float _cx,float _cy,float _cw,float _ch
	) {
		MouseWheel e = new MouseWheel();
        e.who = _who;
		e.setSource(source);
		e.setX(x);
		e.setY(y);
		e.setClickCount(clickCount);
		e.setModifiers(modifiers);
		
		e.scrollType = _scrollType;
		e.scrollAmount = _scrollAmount;
		e.wheelRotation = _wheelRotation;
		
		e.cx = _cx;
		e.cy = _cy;
		e.cw = _cw;
		e.ch = _ch;
		e.isDragging = false;
		return e;
	}
	//----------------------------------------------------------------------------	
        /**
         *
         * @return
         */
        public int getScrollType() {
		return scrollType;
	}
        /**
         *
         * @return
         */
        public int getScrollAmount() {
		return scrollAmount;
	}
        /**
         *
         * @return
         */
        public int getWheelRotation() {
		return wheelRotation;
	}
	//----------------------------------------------------------------------------	
}
