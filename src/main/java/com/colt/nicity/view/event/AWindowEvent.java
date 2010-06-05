/*
 * AWindowEvent.java.java
 *
 * Created on 01-03-2010 01:31:37 PM
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

import com.colt.nicity.view.core.NullView;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
abstract public class AWindowEvent extends AViewEvent {
	//----------------------------------------------------------------------------	
    /**
     *
     * @param parent
     * @param view
     * @return
     */
    public IView disbatchEvent(IView parent,IView view) {
		if (parent != NullView.cNull && parent != view) view.setParentView(parent);
		return view.disbatchEvent(parent,this);
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @return
         */
        public long getMask() { return AViewEvent.cWindowEvent; }
}
