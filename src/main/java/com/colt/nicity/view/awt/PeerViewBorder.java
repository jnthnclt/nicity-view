/*
 * PeerViewBorder.java.java
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
package com.colt.nicity.view.awt;

import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.interfaces.IActiveBorder;
import com.colt.nicity.view.interfaces.IActiveSelectedBorder;
import com.colt.nicity.view.interfaces.IBorder;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IPeerView;
import com.colt.nicity.view.interfaces.ISelectedBorder;
import java.awt.Insets;

/**
 *
 * @author Administrator
 */
public class PeerViewBorder implements IActiveSelectedBorder, ISelectedBorder, IActiveBorder, IBorder {
	//----------------------------------------------------------------------------
	private IPeerView peerView;
	private Insets insets = null;
	//----------------------------------------------------------------------------
        /**
         *
         * @param peerView
         */
        public PeerViewBorder(IPeerView peerView) {
		if (peerView == null) throw new RuntimeException();
		this.peerView = peerView;
		insets = peerView.getInsets();
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param g
         * @param x
         * @param y
         * @param w
         * @param h
         */
        public void paintBorder(ICanvas g,int x,int y, int w, int h) {
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param g
         * @param x
         * @param y
         * @param w
         * @param h
         */
        public void paintBackground(ICanvas g,int x,int y,int w, int h) {
		g.setColor(AColor.white);
		g.rect(true,x,y,w,h);
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @return
         */
        public boolean isActive() { return false; }
        /**
         *
         * @return
         */
        public boolean isSelected() { return false; }
	//----------------------------------------------------------------------------
	private Insets insets() { 
		return peerView.getInsets();
	}
    //----------------------------------------------------------------------------
        /**
         *
         * @return
         */
        public float getX() { return (float)insets().left; }
        /**
         *
         * @return
         */
        public float getY() { return (float)insets().top; }
        /**
         *
         * @return
         */
        public float getW() { return (float)insets().left+insets().right; }
        /**
         *
         * @return
         */
        public float getH() { return (float)insets().top+insets().bottom; }
	//----------------------------------------------------------------------------
        /**
         *
         * @return
         */
        public IBorder getDefaultBorder() { return this; }
   /**
    *
    * @return
    */
   public IActiveBorder getActiveBorder() { return this; }
   /**
    *
    * @return
    */
   public ISelectedBorder getSelectedBorder() { return this; }
   /**
    *
    * @return
    */
   public IActiveSelectedBorder getActiveSelectedBorder() { return this; }
   //----------------------------------------------------------------------------
   
}
