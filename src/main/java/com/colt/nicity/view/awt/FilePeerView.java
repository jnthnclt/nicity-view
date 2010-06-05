/*
 * FilePeerView.java.java
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
package com.colt.nicity.view.awt;

import com.colt.nicity.view.adaptor.VS;
import com.colt.nicity.core.memory.struct.WH_F;
import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.interfaces.IEventClient;
import com.colt.nicity.view.interfaces.IPeerView;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class FilePeerView implements IPeerView {

    File saveTo;
    IEventClient client;
    /**
     *
     */
    public long version;
    boolean disposed = false;
    boolean visible = false;
    /**
     *
     */
    public BufferedImage buffer;

    /**
     *
     * @param _saveTo
     * @param _client
     */
    public FilePeerView(File _saveTo, IEventClient _client) {
        saveTo = _saveTo;
        client = _client;
    }

    /**
     *
     */
    public void saveBuffer() {
        if (buffer == null) {
            return;
        }
        System.out.println("Saving");
        version++;
        VS.writeImageToFile(buffer, "png", saveTo);
    }

    /**
     *
     * @param _visible
     */
    public void setVisible(boolean _visible) {
        if (_visible == false) {
            disposed = true;
        }
        visible = _visible;
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
    public void dispose() {
        client = null;
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
    public IEventClient getClientView() {
        return client;
    }

    /**
     *
     * @param eventsToEnable
     */
    public void enablePeerEvents(long eventsToEnable) {
    }

    /**
     *
     * @param eventsToDisable
     */
    public void disablePeerEvents(long eventsToDisable) {
    }

    /**
     *
     * @param g
     */
    public void update(Graphics g) {
        paintBuffer(g);
    }

    /**
     *
     * @param g
     */
    public void paint(Graphics g) {
        paintBuffer(g);
    }

    /**
     *
     * @return
     */
    public Graphics getGraphics() {
        if (buffer == null) {
            return null;
        }
        return buffer.getGraphics();
    }

    /**
     *
     */
    public void fullscreen() {
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
    public int getW() {
        if (buffer != null) {
            return buffer.getWidth(null);
        }
        return 0;
    }

    /**
     *
     * @return
     */
    public int getH() {
        if (buffer != null) {
            return buffer.getHeight(null);
        }
        return 0;
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public void setWH(int _w, int _h) {
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

        int w = getW();
        int h = getH();
        if (buffer == null || w != _w || h != _h) {
            setWH(_w, _h);
            buffer = ensureBuffer(buffer, _w, _h);
        }
        if (buffer == null) {
            return null;
        }
        return buffer.getGraphics();

    }

    private BufferedImage ensureBuffer(BufferedImage _old, int _w, int _h) {
        if (_w == 0 || _h == 0) {
            if (_old != null) {
                _old.flush();
            }
            BufferedImage newBuffer = newBuffer(1, 1);
            return newBuffer;
        }
        if (_old == null) {
            BufferedImage newBuffer = newBuffer(_w, _h);
            return newBuffer;
        } else {
            if (_old.getWidth(null) != _w || _old.getHeight(null) != _h) {
                BufferedImage newBuffer = newBuffer(_w, _h);
                Graphics bg = newBuffer.getGraphics();
                bg.drawImage(_old, 0, 0, null);
                bg.dispose();
                _old.flush();
                return newBuffer;
            }
            return _old;
        }
    }

    private BufferedImage newBuffer(int _w, int _h) {
        return new BufferedImage(_w, _h, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     *
     * @param g
     */
    public void paintBuffer(Graphics g) {
        if (disposed) {
            setVisible(false);
        }
        g.drawImage(buffer, 0, 0, null);
    }

    /**
     *
     * @param _region
     */
    public void modifiedRegion(XYWH_I _region) {
        Graphics g = getGraphics();
        if (_region != null && _region.x != Integer.MIN_VALUE) {
            g.drawImage(
                    buffer,
                    _region.x, _region.y, _region.x + _region.w, _region.y + _region.h,
                    _region.x, _region.y, _region.x + _region.w, _region.y + _region.h,
                    null);
        } else {
            Insets insets = getInsets();
            g.drawImage(buffer, insets.left, insets.top, null);
        }
        g.dispose();
        saveBuffer();
    }

    /**
     *
     * @param _buffer
     * @param _bufferRegion
     * @param _screenRegion
     */
    public void directRegion(
            BufferedImage _buffer,
            XYWH_I _bufferRegion,
            XYWH_I _screenRegion) {
        Insets insets = getInsets();
        Graphics g = getGraphics();
        g.drawImage(
                _buffer,
                _screenRegion.x, _screenRegion.y, _screenRegion.x + _screenRegion.w, _screenRegion.y + _screenRegion.h,
                _bufferRegion.x, _bufferRegion.y, _bufferRegion.x + _bufferRegion.w, _bufferRegion.y + _bufferRegion.h,
                null);
        g.dispose();
        saveBuffer();
    }

    //public Image createImage(int x, int y) {
    //    return newBuffer(x, y);
    //}
    /**
     *
     * @return
     */
    public String getTitle() {
        return "Not Supported";
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
    public void show() {
    }

    /**
     *
     */
    public void hide() {
    }

    /**
     *
     * @return
     */
    public boolean isDisplayable() {
        return true;
    }
    Cursor cursor = Cursor.getDefaultCursor();

    /**
     *
     * @return
     */
    public Cursor getCursor() {
        return cursor;
    }

    /**
     *
     * @param _cursor
     */
    public void setCursor(Cursor _cursor) {
        cursor = _cursor;
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
    public WH_F getWH() {
        return new WH_F(getW(), getH());
    }
}
