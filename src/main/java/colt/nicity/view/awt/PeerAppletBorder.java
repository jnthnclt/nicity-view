/*
 * PeerAppletBorder.java.java
 *
 * Created on 01-03-2010 01:31:33 PM
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
package colt.nicity.view.awt;

import colt.nicity.view.interfaces.IActiveBorder;
import colt.nicity.view.interfaces.IActiveSelectedBorder;
import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.ISelectedBorder;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Administrator
 */
public class PeerAppletBorder implements IActiveSelectedBorder, ISelectedBorder, IActiveBorder, IBorder {
	//----------------------------------------------------------------------------
	private PApplet peerApplet;
	//----------------------------------------------------------------------------
        /**
         *
         * @param _peerApplet
         */
        public PeerAppletBorder(PApplet _peerApplet) {
		if (_peerApplet == null) throw new RuntimeException();
		peerApplet = _peerApplet;
	}
	//----------------------------------------------------------------------------
        /**
         *
         * @param g
         * @param w
         * @param h
         */
        public void paintBorder(Graphics g, int w, int h) {}
        /**
         *
         * @param g
         * @param w
         * @param h
         */
        public void paintBackground(Graphics g,int w, int h) {
		
		g.setColor(Color.white);
		g.fillRect(0,0,w,h);
		
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
        /**
         *
         * @return
         */
        public float getX() { return peerApplet.getLocationOnScreen().x; }
        /**
         *
         * @return
         */
        public float getY() { return peerApplet.getLocationOnScreen().y; }
        /**
         *
         * @return
         */
        public float getW() { return peerApplet.getLocationOnScreen().x; }
        /**
         *
         * @return
         */
        public float getH() { return peerApplet.getLocationOnScreen().y; }
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

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void paintBackground(ICanvas g,int x,int y, int w, int h) {
    }
   //----------------------------------------------------------------------------
   
}
