/*
 * PFrame.java.java
 *
 * Created on 01-03-2010 01:32:53 PM
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

import com.colt.nicity.view.adaptor.VS;
import com.colt.nicity.view.monitor.VException;
import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.lang.NullOut;
import com.colt.nicity.core.memory.struct.WH_F;
import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.core.PrimativeEvent;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.interfaces.IEventClient;
import com.colt.nicity.view.interfaces.IPeerView;
import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

/**
 *
 * @author Administrator
 */
public class PFrame extends Frame implements IPeerView {
    		
    /**
     *
     */
    public static boolean debugEvents = false;
    private IOut _;
    private IEventClient client;
   
    /**
     *
     * @param _client
     */
    public PFrame(IEventClient _client) {

        setBackground(VS.systemColor(ViewColor.cTheme));
        _ = NullOut.cNull;

        client = _client;
        enableEvents(
                AWTEvent.MOUSE_EVENT_MASK |
                AWTEvent.MOUSE_MOTION_EVENT_MASK |
                AWTEvent.KEY_EVENT_MASK |
                AWTEvent.FOCUS_EVENT_MASK |
                AWTEvent.COMPONENT_EVENT_MASK |
                AWTEvent.WINDOW_EVENT_MASK |
                AWTEvent.MOUSE_WHEEL_EVENT_MASK |
                AWTEvent.WINDOW_STATE_EVENT_MASK |
                AWTEvent.WINDOW_FOCUS_EVENT_MASK |
                AWTEvent.ACTION_EVENT_MASK |
                AWTEvent.PAINT_EVENT_MASK |
                AWTEvent.ADJUSTMENT_EVENT_MASK |
                AWTEvent.CONTAINER_EVENT_MASK |
                AWTEvent.HIERARCHY_BOUNDS_EVENT_MASK |
                AWTEvent.HIERARCHY_EVENT_MASK |
                AWTEvent.INPUT_METHOD_EVENT_MASK |
                AWTEvent.INVOCATION_EVENT_MASK |
                AWTEvent.ITEM_EVENT_MASK);

        
        try {
            setDropTarget(new DropTarget(this, (DropTargetListener) client));
        } catch (Exception x) {
            new VException(x, "Error Attaching DagNDrop").toFront(null);
        }

    }
    		
    /**
     *
     */
    protected void refreshBackground() {
        if (this.isVisible()) {
            repaint();
        }
    }
    
    boolean disposed = false;
    @Override
    public void setVisible(boolean _visible) {
        if (_visible == false) {
            disposed = true;
        }
        super.setVisible(_visible);
    }
    
    /**
     *
     * @param _modal
     */
    public void setModal(boolean _modal) {
    }
    
    @Override
    public void dispose() {
        client = null;
        Image _buffer = buffer;
        buffer = null;
        if (_buffer != null) {
            _buffer.flush();
        }
        super.dispose();
    }
    
    /**
     *
     */
    public void iconify() {
        setState(Frame.ICONIFIED);
    }
    /**
     *
     */
    public void deiconify() {
        setState(Frame.NORMAL);
    }
    /**
     *
     */
    public void maximize() {
        setExtendedState(Frame.MAXIMIZED_BOTH);
    }
    
    /**
     *
     * @return
     */
    public IEventClient getClientView() {
        return client;
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

    
    
    @Override
    protected void processEvent(AWTEvent event) {
        if (disposed) {
            setVisible(false);
        }
        super.processEvent(event);
        PrimativeEvent _event = UAWT.toPrimativeEvent(event);
        if (client != null) {
            client.processEvent(_,_event);
        }
    }
    
    /**
     *
     */
    public Image buffer;
    
    /**
     *
     */
    public void fullscreen() {
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
            buffer = UAWT.ensureBuffer(buffer, _w, _h);
        }
        if (buffer == null) {
            return null;
        }
        return buffer.getGraphics();

    }
    
    /**
     *
     * @param g
     */
    public void paintBuffer(Graphics g) {
        if (disposed) {
            dispose();
        } else {
            g.drawImage(buffer, 0, 0, null);
        }
    }
    
    /**
     *
     * @param _region
     */
    public void modifiedRegion(XYWH_I _region) {
        //System.out.println("modifiedRegion:"+_region);
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
