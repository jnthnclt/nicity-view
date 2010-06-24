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

import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.interfaces.IEventClient;
import colt.nicity.view.interfaces.IPeerView;
import java.awt.Graphics;
import java.awt.Insets;

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
    public void enablePeerEvents(long tasksToEnable) {
    }

    /**
     *
     * @param tasksToDisable
     */
    public void disablePeerEvents(long tasksToDisable) {
    }

    /**
     *
     * @return
     */
    public IEventClient getClientView() {
        return null;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return "stale";
    }

    /**
     *
     * @param _title
     */
    public void setTitle(String _title) {
    }

    /**
     *
     * @param _modal
     */
    public void setModal(boolean _modal) {
    }

    /**
     *
     */
    public void toFront() {
    }

    /**
     *
     */
    public void toBack() {
    }

    /**
     *
     */
    public void show() {
    }

    /**
     *
     */
    public void hide() {
    }

    /**
     *
     */
    public void dispose() {
    }

    /**
     *
     * @return
     */
    public boolean isDisplayable() {
        return false;
    }

    /**
     *
     * @param _visible
     */
    public void setVisible(boolean _visible) {
    }

    /**
     *
     */
    public void iconify() {
    }

    /**
     *
     */
    public void deiconify() {
    }

    /**
     *
     */
    public void maximize() {
    }

    /**
     *
     * @return
     */
    public XY_I getCorner() {
        return new XY_I(0, 0);
    }

    /**
     *
     * @return
     */
    public XY_I getCornerOnScreen() {
        return new XY_I(0, 0);
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setCorner(int x, int y) {
    }

    /**
     *
     * @return
     */
    public Insets getInsets() {
        return new Insets(0, 0, 0, 0);
    }

    /**
     *
     * @return
     */
    public WH_F getWH() {
        return new WH_F(0, 0);
    }

    /**
     *
     * @param w
     * @param h
     */
    public void setWH(int w, int h) {
    }

    /**
     *
     * @return
     */
    public int getW() {
        return 0;
    }

    /**
     *
     * @return
     */
    public int getH() {
        return 0;
    }

    /**
     *
     * @param _w
     * @param _h
     * @return
     */
    public Graphics ensureSize(int _w, int _h) {
        return null;
    }

    /**
     *
     */
    public void fullscreen() {
    }

    /**
     *
     * @param _region
     */
    public void modifiedRegion(XYWH_I _region) {
    }
}
