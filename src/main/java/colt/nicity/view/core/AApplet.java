/*
 * AApplet.java.java
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
package colt.nicity.view.core;

import colt.nicity.view.awt.PApplet;
import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.IPeerView;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class AApplet extends AWindow {
	//----------------------------------------------------------------------------		
    /**
     *
     * @param _view
     * @param _peer
     */
    public AApplet(IView _view,PApplet _peer) {
		setPlacer(new Placer(_view));
		peer = _peer;
        _peer.setClient(this);
		init();
	}
	//----------------------------------------------------------------------------
	public IPeerView getPeerView() { return peer; }
        /**
         *
         * @param _peer
         */
        public void setPeer(PApplet _peer) { peer = _peer; }
	//----------------------------------------------------------------------------
        /**
         *
         * @return
         */
        public IPeerView getPeer() {
		if (peer == null) throw new RuntimeException();
		return peer;
	}
	//----------------------------------------------------------------------------
	public void setBorder(IBorder border) {}
	//----------------------------------------------------------------------------
}
