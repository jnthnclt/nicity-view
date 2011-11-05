/*
 * IPeerView.java.java
 *
 * Created on 01-03-2010 01:31:40 PM
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
package colt.nicity.view.interfaces;

import colt.nicity.core.memory.struct.TRLB_I;
import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import java.awt.Graphics;

/**
 *
 * @author Administrator
 */
public interface IPeerView {

    /**
     *
     * @param tasksToEnable
     */
    public void enablePeerEvents(long tasksToEnable);

    /**
     *
     * @param tasksToDisable
     */
    public void disablePeerEvents(long tasksToDisable);

    /**
     *
     * @return
     */
    public IEventClient getClientView();

    /**
     *
     * @return
     */
    public String getTitle();

    /**
     *
     * @param _title
     */
    public void setTitle(String _title);

    /**
     *
     * @param _modal
     */
    public void setModal(boolean _modal);

    /**
     *
     */
    public void toFront();

    /**
     *
     */
    public void toBack();

    /**
     *
     */
    public void show();

    /**
     *
     */
    public void hide();

    /**
     *
     */
    public void dispose();

    /**
     *
     * @return
     */
    public boolean isDisplayable();

    /**
     *
     * @param _visible
     */
    public void setVisible(boolean _visible);

    /**
     *
     */
    public void iconify();

    /**
     *
     */
    public void deiconify();

    /**
     *
     */
    public void maximize();

    /**
     *
     * @return
     */
    public XY_I getCorner();

    /**
     *
     * @return
     */
    public XY_I getCornerOnScreen();

    /**
     *
     * @param x
     * @param y
     */
    public void setCorner(int x, int y);

    /**
     *
     * @return
     */
    public TRLB_I getTRLB();

    /**
     *
     * @return
     */
    public WH_F getWH();

    /**
     *
     * @param w
     * @param h
     */
    public void setWH(int w, int h);

    /**
     *
     * @return
     */
    public int getW();

    /**
     *
     * @return
     */
    public int getH();

    /**
     *
     * @param _w
     * @param _h
     * @return
     */
    public Graphics ensureSize(int _w, int _h);

    /**
     *
     */
    public void fullscreen();

    /**
     *
     * @param _region
     */
    public void modifiedRegion(XYWH_I _region);
}
