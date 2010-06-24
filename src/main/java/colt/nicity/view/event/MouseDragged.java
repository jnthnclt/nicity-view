/*
 * MouseDragged.java.java
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
public class MouseDragged extends AMouseEvent {
	//----------------------------------------------------------------------------	
    /**
     *
     */
    public static final int cXLatched = 1;
    /**
     *
     */
    public static final int cYLatched = -1;
	//----------------------------------------------------------------------------	
        /**
         *
         */
        public static final int cCenter = 0;
        /**
         *
         */
        public static final int cNorth = 1;
        /**
         *
         */
        public static final int cSouth = 2;
        /**
         *
         */
        public static final int cEast = 3;
        /**
         *
         */
        public static final int cWest = 4;
	//----------------------------------------------------------------------------	
        /**
         *
         */
        protected int latch = 0;
        /**
         *
         */
        protected int direction = 0;
	//----------------------------------------------------------------------------	
        /**
         *
         */
        protected int dx;
        /**
         *
         */
        protected int dy;
        /**
         *
         */
        protected int sumdx;
        /**
         *
         */
        protected int sumdy;
	//----------------------------------------------------------------------------	
        /**
         *
         */
        protected int pressedx;
        /**
         *
         */
        protected int pressedy;
	//----------------------------------------------------------------------------	
        /**
         *
         * @param _who
         * @param source
         * @param _x
         * @param _y
         * @param _z
         * @param _clickCount
         * @param _modifiers
         * @param _dx
         * @param _dy
         * @param _sumdx
         * @param _sumdy
         * @param _pressedx
         * @param _pressedy
         * @param _latch
         * @param _direction
         * @param _cx
         * @param _cy
         * @param _cw
         * @param _ch
         * @return
         */
        public static MouseDragged newInstance(
            long _who,
		Object source,
		int _x,int _y,int _z,
		int _clickCount,
		int _modifiers,
		int _dx,int _dy,
		int _sumdx,int _sumdy,
		int _pressedx,int _pressedy,
		int _latch,
		int _direction,
		float _cx,float _cy,float _cw,float _ch
	) {
		MouseDragged e = new MouseDragged();
        e.who = _who;
		e.setSource(source);
		e.x = _x;
		e.y = _y;
		e.z = _z;
		e.clickCount = _clickCount;
		e.modifiers = _modifiers;
		e.dx = _dx;
		e.dy = _dy;
		e.sumdx = _sumdx;
		e.sumdy = _sumdy;
		
		e.pressedx = _pressedx;
		e.pressedy = _pressedy;
		
		e.latch = _latch;
		e.direction =_direction;
		e.cx = _cx;
		e.cy = _cy;
		e.cw = _cw;
		e.ch = _ch;
		e.isDragging = true;
		return e;
	}
	//----------------------------------------------------------------------------	
        /**
         *
         * @return
         */
        public boolean isXLatched() { return (latch == cXLatched); }
        /**
         *
         * @return
         */
        public boolean isYLatched() { return (latch == cYLatched); }
	//----------------------------------------------------------------------------	
        /**
         *
         * @return
         */
        public boolean isNorth() { return direction == cNorth; }
        /**
         *
         * @return
         */
        public boolean isSouth() { return direction == cSouth; }
        /**
         *
         * @return
         */
        public boolean isEast() { return direction == cEast; }
        /**
         *
         * @return
         */
        public boolean isWest() { return direction == cWest; }
        /**
         *
         * @return
         */
        public boolean isCenter() { return direction == cCenter; }
	//----------------------------------------------------------------------------	
        /**
         *
         * @return
         */
        public int getDirection() { return direction; }
	//----------------------------------------------------------------------------	
        /**
         *
         * @return
         */
        public int getPressedX() { return pressedx; }
        /**
         *
         * @return
         */
        public int getPressedY() { return pressedy; }
	//----------------------------------------------------------------------------	
        /**
         *
         * @return
         */
        public int getDeltaX() { return dx; }
        /**
         *
         * @return
         */
        public int getDeltaY() { return dy; }
	//----------------------------------------------------------------------------	
        /**
         *
         * @return
         */
        public int getSumDeltaX() { return sumdx; }
        /**
         *
         * @return
         */
        public int getSumDeltaY() { return sumdy; }
	//----------------------------------------------------------------------------	
}
