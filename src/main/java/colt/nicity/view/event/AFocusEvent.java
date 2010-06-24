/*
 * AFocusEvent.java.java
 *
 * Created on 01-03-2010 01:31:36 PM
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

/**
 *
 * @author Administrator
 */
abstract public class AFocusEvent extends AViewEvent {
	//----------------------------------------------------------------------------	
	private boolean isHardFocus = false;
	//----------------------------------------------------------------------------	
        /**
         *
         * @param _isHardFocus
         */
        public void setHardFocus(boolean _isHardFocus) { isHardFocus = _isHardFocus; }
        /**
         *
         * @return
         */
        public boolean isHardFocus() { return isHardFocus; }
	//----------------------------------------------------------------------------	
        /**
         *
         * @param parent
         * @param view
         * @return
         */
        public IView disbatchEvent(IView parent,IView view) {
		if (!(view.isEventEnabled(AViewEvent.cFocusEvent))) return NullView.cNull;
		return view.disbatchEvent(parent,this);
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @return
         */
        public long getMask() { return AViewEvent.cFocusEvent; }
	//----------------------------------------------------------------------------
	public String toString() { return super.toString()+" isHardFocus="+isHardFocus; }
}
