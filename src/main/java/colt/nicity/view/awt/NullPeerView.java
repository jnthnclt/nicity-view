/*
 * NullPeerView.java.java
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
package colt.nicity.view.awt;

import colt.nicity.core.memory.struct.TRLB_I;
import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IEventClient;
import colt.nicity.view.interfaces.IPeerView;

/**
 *
 * @author Administrator
 */
public class NullPeerView implements IPeerView {

    /**
     *
     */
    public static final IPeerView cNull = new NullPeerView();

    /**
     *
     */
    public NullPeerView() {
    }

    /**
     *
     * @param tasksToEnable
     */
    @Override
    public void enablePeerEvents(long tasksToEnable) {
    }

    /**
     *
     * @param tasksToDisable
     */
    @Override
    public void disablePeerEvents(long tasksToDisable) {
    }

    /**
     *
     * @return
     */
    @Override
    public IEventClient getClientView() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public String getTitle() {
        return "stale";
    }

    /**
     *
     * @param _title
     */
    @Override
    public void setTitle(String _title) {
    }

    /**
     *
     * @param _modal
     */
    @Override
    public void setModal(boolean _modal) {
    }

    /**
     *
     */
    @Override
    public void toFront() {
    }

    /**
     *
     */
    @Override
    public void toBack() {
    }

    /**
     *
     */
    @Override
    public void show() {
    }

    /**
     *
     */
    @Override
    public void hide() {
    }

    /**
     *
     */
    @Override
    public void dispose() {
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isDisplayable() {
        return false;
    }

    /**
     *
     * @param _visible
     */
    @Override
    public void setVisible(boolean _visible) {
    }

    /**
     *
     */
    @Override
    public void iconify() {
    }

    /**
     *
     */
    @Override
    public void deiconify() {
    }

    /**
     *
     */
    @Override
    public void maximize() {
    }

    /**
     *
     * @return
     */
    @Override
    public XY_I getCorner() {
        return new XY_I(0, 0);
    }

    /**
     *
     * @return
     */
    @Override
    public XY_I getCornerOnScreen() {
        return new XY_I(0, 0);
    }

    /**
     *
     * @param x
     * @param y
     */
    @Override
    public void setCorner(int x, int y) {
    }

    /**
     *
     * @return
     */
    @Override
    public TRLB_I getTRLB() {
        return new TRLB_I(0, 0, 0, 0);
    }

    /**
     *
     * @return
     */
    @Override
    public WH_F getWH() {
        return new WH_F(0, 0);
    }

    /**
     *
     * @param w
     * @param h
     */
    @Override
    public void setWH(int w, int h) {
    }

    /**
     *
     * @return
     */
    @Override
    public int getW() {
        return 0;
    }

    /**
     *
     * @return
     */
    @Override
    public int getH() {
        return 0;
    }

    /**
     *
     * @param _w
     * @param _h
     * @return
     */
    @Override
    public ICanvas ensureSize(long _who, int _w, int _h) {
        return null;
    }

    /**
     *
     */
    @Override
    public void fullscreen() {
    }

    /**
     *
     * @param _region
     */
    @Override
    public void modifiedRegion(XYWH_I _region) {
    }

    @Override
    public void setUndecorated(boolean undecorated) {
    }
}
