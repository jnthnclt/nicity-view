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
package colt.nicity.view.awt;

import colt.nicity.core.memory.struct.TRLB_I;
import colt.nicity.view.core.AColor;
import colt.nicity.view.interfaces.IActiveBorder;
import colt.nicity.view.interfaces.IActiveSelectedBorder;
import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IPeerView;
import colt.nicity.view.interfaces.ISelectedBorder;

/**
 *
 * @author Administrator
 */
public class PeerViewBorder implements IActiveSelectedBorder, ISelectedBorder, IActiveBorder, IBorder {

    private IPeerView peerView;
    private TRLB_I insets = null;

    /**
     *
     * @param peerView
     */
    public PeerViewBorder(IPeerView peerView) {
        if (peerView == null) {
            throw new RuntimeException();
        }
        this.peerView = peerView;
        insets = peerView.getTRLB();
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    @Override
    public void paintBorder(ICanvas g, int x, int y, int w, int h) {
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    @Override
    public void paintBackground(ICanvas g, int x, int y, int w, int h) {
        g.setColor(AColor.white);
        g.rect(true, x, y, w, h);
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isActive() {
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isSelected() {
        return false;
    }

    private TRLB_I insets() {
        return peerView.getTRLB();
    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return (float) insets().left;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return (float) insets().top;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return (float) insets().left + insets().right;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return (float) insets().top + insets().bottom;
    }

    /**
     *
     * @return
     */
    @Override
    public IBorder getDefaultBorder() {
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public IActiveBorder getActiveBorder() {
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public ISelectedBorder getSelectedBorder() {
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public IActiveSelectedBorder getActiveSelectedBorder() {
        return this;
    }
}
