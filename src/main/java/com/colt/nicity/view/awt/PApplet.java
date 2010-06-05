/*
 * PApplet.java.java
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
package com.colt.nicity.view.awt;

import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.memory.struct.WH_F;
import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.core.AApplet;
import com.colt.nicity.view.interfaces.IEventClient;
import com.colt.nicity.view.interfaces.IPeerView;
import com.colt.nicity.view.interfaces.IRootView;
import java.applet.Applet;
import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Administrator
 */
public class PApplet extends Applet implements IPeerView {

    private AApplet client;
    private IOut _;
    /**
     *
     */
    public PApplet() {
        enableEvents(
                AWTEvent.MOUSE_EVENT_MASK
                | AWTEvent.MOUSE_MOTION_EVENT_MASK
                | AWTEvent.KEY_EVENT_MASK
                | AWTEvent.WINDOW_EVENT_MASK
                | AWTEvent.FOCUS_EVENT_MASK);
        _ = new IOut() {
            public boolean canceled() {
                return false;
            }

            public void out(double _at, double _outof) {
                
            }

            public void out(Object... _status) {
                
            }
        };
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
     * @param _client
     */
    public void setClient(AApplet _client) {
        client = _client;
    }

    /**
     *
     * @param _modal
     */
    public void setModal(boolean _modal) {
    }

    /**
     *
     * @return
     */
    public IRootView getClient() {
        return client;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return "";
    }

    /**
     *
     * @param _title
     */
    public void setTitle(String _title) {
    }

    /**
     *
     * @return
     */
    public Image getIconImage() {
        return null;
    }

    /**
     *
     * @param image
     */
    public void setIconImage(Image image) {
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
    public void dispose() {
    }

    /**
     *
     * @param eventsToEnable
     */
    public void enablePeerEvents(long eventsToEnable) {
        super.enableEvents(eventsToEnable);
    }

    /**
     *
     * @param eventsToDisable
     */
    public void disablePeerEvents(long eventsToDisable) {
        super.disableEvents(eventsToDisable);
    }

    @Override
    public void update(Graphics g) {
        paintBuffer(g);
    }

    @Override
    public void paint(Graphics g) {
        paintBuffer(g);
    }
    /**
     *
     */
    public Image buffer;

    /**
     *
     * @param g
     */
    public void paintBuffer(Graphics g) {
        if (buffer != null) {
            g.drawImage(buffer, 0, 0, null);
        }
    }

    @Override
    protected void processEvent(AWTEvent event) {
        client.processEvent(_,UAWT.toPrimativeEvent(event));
    }

    /**
     *
     * @return
     */
    public IEventClient getClientView() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param _w
     * @param _h
     * @return
     */
    public Graphics ensureSize(int _w, int _h) {
        Insets insets = getInsets();
        _w += (insets.left + insets.right);
        _h += (insets.top + insets.bottom);

        int w = getWidth();
        int h = getHeight();
        if (buffer == null || w != _w || h != _h) {
            setSize(_w, _h);
            buffer = ensureBuffer(buffer, _w, _h);
        }
        if (buffer == null) {
            return null;
        }
        return buffer.getGraphics();

    }

    private Image ensureBuffer(Image _old, int _w, int _h) {
        if (_w == 0 || _h == 0) {
            if (_old != null) {
                _old.flush();
            }
            Image newBuffer = newBuffer(1, 1);
            return newBuffer;
        }
        if (_old == null) {
            Image newBuffer = newBuffer(_w, _h);
            return newBuffer;
        } else {
            if (_old.getWidth(null) != _w || _old.getHeight(null) != _h) {
                Image newBuffer = newBuffer(_w, _h);
                Graphics bg = newBuffer.getGraphics();
                bg.drawImage(_old, 0, 0, null);
                bg.dispose();
                _old.flush();
                return newBuffer;
            }
            return _old;
        }
    }

    private Image newBuffer(int _w, int _h) {
        BufferedImage bi = new BufferedImage(_w, _h, BufferedImage.TYPE_INT_ARGB);
        return bi;
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
        UAWT.modifiedRegion(getInsets(), getGraphics(), _region, buffer);
    }

    /**
     *
     * @return
     */
    public XY_I getCorner() {
        Point p = super.getLocation();
        return new XY_I(p.x, p.y);
    }

    /**
     *
     * @return
     */
    public XY_I getCornerOnScreen() {
        Point p = super.getLocationOnScreen();
        return new XY_I(p.x, p.y);
    }

    /**
     *
     * @return
     */
    public WH_F getWH() {
        Dimension d = super.getSize();
        return new WH_F(d.width, d.height);
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setCorner(int x, int y) {
        super.setLocation(x, y);
    }

    /**
     *
     * @param w
     * @param h
     */
    public void setWH(int w, int h) {
        super.setSize(w, h);
    }

    /**
     *
     * @return
     */
    public int getW() {
        return super.getWidth();
    }

    /**
     *
     * @return
     */
    public int getH() {
        return super.getHeight();
    }
}
