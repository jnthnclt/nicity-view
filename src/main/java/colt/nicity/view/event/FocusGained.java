/*
 * FocusGained.java.java
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
public class FocusGained extends AFocusEvent {
    /**
     *
     * @param _who
     * @param source
     * @param _isHardFocus
     * @return
     */
    public static FocusGained newInstance(long _who,Object source,boolean _isHardFocus) {
		FocusGained e = (FocusGained)new FocusGained();
        e.who = _who;
		e.setSource(source);
		e.setHardFocus(_isHardFocus);
		return e;
	}
}
