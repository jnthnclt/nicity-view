/*
 * VPan.java.java
 *
 * Created on 01-03-2010 01:33:52 PM
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

import colt.nicity.core.lang.ICallback;
import colt.nicity.core.lang.MinMaxInt;
import colt.nicity.core.lang.UDouble;
import colt.nicity.core.lang.UFloat;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.event.AInputEvent;
import colt.nicity.view.event.AViewEvent;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.event.MouseWheel;
import colt.nicity.view.flavor.ScrollFlavor;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IDrop;
import colt.nicity.view.interfaces.IDropMode;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IMouseWheelEvents;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.paint.UPaint;
import java.awt.geom.GeneralPath;

/**
 *
 * @author Administrator
 */
public class VPan extends VClip implements IDrop, IMouseWheelEvents, IMouseEvents, IMouseMotionEvents {

    public static void main(String[] args) {
        UV.exitFrame(UV.zones(UV.zone("Pan",new VPan(new VChain(UV.cSN,new VButton("Button"),new VBox(800, 800) {

            @Override
            public void paintBorder(ICanvas g, int _x, int _y, int _w, int _h) {
                UPaint.checked(g, _x, _y, _w, _h, AColor.lightGray, AColor.darkGray);
            }
        }), 400, 400))), "Pan");
    }
    static ScrollFlavor flavor = new ScrollFlavor();
    float maxWBeforePan = -1;
    float maxHBeforePan = -1;
    float fixedW = -1;
    float fixedH = -1;
    int resize = 6;
    /**
     *
     */
    public int scrollBarSize = 16;
    /**
     *
     */
    public boolean operable = true;
    /**
     *
     */
    public ICallback dropCallback;
    /**
     *
     */
    public ICallback droppedCallback;
    /**
     *
     */
    public AColor barColor = ViewColor.cThemeScroll;
    /**
     *
     */
    public boolean resizeable = true;
    /**
     *
     */
    protected boolean resizingX = false;
    /**
     * 
     */
    protected boolean resizingY = false;
    /**
     *
     */
    protected boolean scrollingX = false;
    /**
     *
     */
    protected boolean scrollingY = false;
    private boolean pan = false;
    private boolean paintYScrollbar = false;
    private boolean paintXScrollbar = false;
    private boolean paintXResizing = false;
    private boolean paintYResizing = false;

    /**
     *
     * @param _view
     * @param _w
     * @param _h
     * @param _autoCenterX
     * @param _autoCenterY
     */
    public VPan(
            IView _view,
            float _w, float _h,
            boolean _autoCenterX, boolean _autoCenterY) {
        this(_view, _w, _h);
        setAutoCenter(_autoCenterX, _autoCenterY);
    }

    /**
     *
     * @param _view
     * @param _w
     * @param _h
     * @param _autoCenter
     */
    public VPan(IView _view, float _w, float _h, boolean _autoCenter) {
        this(_view, _w, _h);
        setAutoCenter(_autoCenter);
    }

    /**
     *
     * @param _view
     * @param _w
     * @param _h
     */
    public VPan(IView _view, float _w, float _h) {
        super(_view, _w, _h);
        setBorder(new ViewBorder());
        overScroll = resize+scrollBarSize;
    }

    /**
     *
     * @param _barColor
     */
    public void setBarColor(AColor _barColor) {
        barColor = _barColor;
    }

    @Override
    public synchronized IView spans(int spanMasks) {
        return this;
    }

    @Override
    public void mend() {
        enableFlag(UV.cRepair);//??
        super.mend();
    }

    /**
     *
     * @param _autoCenter
     */
    public final void setAutoCenter(boolean _autoCenter) {
        autoCenterX = _autoCenter;
        autoCenterY = _autoCenter;
    }

    /**
     *
     * @param _autoCenterX
     * @param _autoCenterY
     */
    public final void setAutoCenter(boolean _autoCenterX, boolean _autoCenterY) {
        autoCenterX = _autoCenterX;
        autoCenterY = _autoCenterY;
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public void setSizeBeforeScroll(int _w, int _h) {
        maxWBeforePan = _w;
        maxHBeforePan = _h;
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public void fixedSize(int _w, int _h) {
        fixedW = _w;
        fixedH = _h;
        if (fixedW != -1) {
            w = fixedW;
        }
        if (fixedH != -1) {
            h = fixedH;
        }
    }

    @Override
    public void paintBody(ICanvas g, Layer _layer, int _mode, XYWH_I _painted) {
        super.paintBody(g, _layer, _mode, _painted);
        if (operable) {
            if (alignY > -1 && view.getH() > getH()) {//paintYScrollbar
                XYWH_I yr = panY();
                _painted.union((int) (_layer.x() + yr.x), (int) (_layer.y() + yr.y), yr.w, yr.h);
            }
            if (alignX > -1 && view.getW() > getW()) {//paintXScrollbar
                XYWH_I xr = panX();
                _painted.union((int) (_layer.x() + xr.x), (int) (_layer.y() + xr.y), xr.w, xr.h);
            }
        }
    }

    @Override
    public void paintBorder(ICanvas _g, int _x, int _y, int _w, int _h) {
        super.paintBorder(_g, _x, _y, _w, _h);

        if (operable) {
            AColor c = barColor;
            if (alignY > -1 && view.getH() > getH()) {//paintYScrollbar
                c = barColor;
                if (scrollingY || paintYScrollbar) {
                    c = ViewColor.cThemeActive;
                }
                _g.setColor(barColor);


                XYWH_I r = panY();
                flavor.paintFlavor(_g, _x + r.x, _y + r.y, r.w, r.h, c);

                r = incUp();
                flavor.paintFlavor(_g, _x + r.x, _y + r.y, r.w, r.h, c);
                _g.setColor(ViewColor.cThemeFont);
                GeneralPath p = new GeneralPath();
                UPaint.arrowHead(p, _x + r.x + (r.w / 2), _y + r.y + (r.h / 2), 0, scrollBarSize / 3, (float) 90);
                _g.draw(p);

                r = incDown();
                flavor.paintFlavor(_g, _x + r.x, _y + r.y, r.w, r.h, c);
                _g.setColor(ViewColor.cThemeFont);
                p = new GeneralPath();
                UPaint.arrowHead(p, _x + r.x + (r.w / 2), _y + r.y + (r.h / 2), (float) 180, scrollBarSize / 3, (float) 90);
                _g.draw(p);


            }
            if (alignX > -1 && view.getW() > getW()) {//paintXScrollbar
                c = barColor;
                if (scrollingX || paintXScrollbar) {
                    c = ViewColor.cThemeActive;
                }
                _g.setColor(c);

                XYWH_I r = panX();
                flavor.paintFlavor(_g, _x + r.x, _y + r.y, r.w, r.h, c);

                r = incLeft();
                flavor.paintFlavor(_g, _x + r.x, _y + r.y, r.w, r.h, c);
                _g.setColor(ViewColor.cThemeFont);
                GeneralPath p = new GeneralPath();
                UPaint.arrowHead(p, _x + r.x + (r.w / 2), _y + r.y + (r.h / 2), 90, scrollBarSize / 3, (float) 90);
                _g.draw(p);

                r = incRight();
                flavor.paintFlavor(_g, _x + r.x, _y + r.y, r.w, r.h, c);
                _g.setColor(ViewColor.cThemeFont);
                p = new GeneralPath();
                UPaint.arrowHead(p, _x + r.x + (r.w / 2), _y + r.y + (r.h / 2), (float) 270, scrollBarSize / 3, (float) 90);
                _g.draw(p);

            }
            if (resizeable && (alignX > -1 || alignY > -1)) {
                c = barColor;
                if (resizingX && resizingY) {
                    c = ViewColor.cThemeActive;
                }
                flavor.paintFlavor(_g, _x + _w - scrollBarSize - resize, _y + _h - scrollBarSize - resize, scrollBarSize, scrollBarSize, c);
            }

            c = barColor;
            if (resizingX || resizingX) {
                c = ViewColor.cThemeActive;
            }
            XYWH_I r = resizeX();
            c = barColor;
            if (resizingX || paintXResizing) {
                c = ViewColor.cThemeActive;
            }
            flavor.paintFlavor(_g, r.x, r.y, r.w, r.h, c);
            c = barColor;
            if (resizingY || paintYResizing) {
                c = ViewColor.cThemeActive;
            }
            r = resizeY();
            flavor.paintFlavor(_g, r.x, r.y, r.w, r.h, c);

        }
    }

    /**
     *
     * @return
     */
    public XYWH_I incUp() {
        float _w = getW();
        return new XYWH_I(_w - (scrollBarSize + resize), 0, scrollBarSize, scrollBarSize);
    }

    /**
     *
     * @return
     */
    public XYWH_I incDown() {
        float _w = getW();
        float _h = getH();
        return new XYWH_I(_w - (scrollBarSize + resize), _h - (scrollBarSize + scrollBarSize + resize), scrollBarSize, scrollBarSize);
    }

    /**
     *
     * @return
     */
    public XYWH_I incRight() {
        float _w = getW();
        float _h = getH();
        return new XYWH_I(_w - (scrollBarSize + scrollBarSize + resize), _h - (scrollBarSize + resize), scrollBarSize, scrollBarSize);
    }

    /**
     *
     * @return
     */
    public XYWH_I incLeft() {
        float _h = getH();
        return new XYWH_I(0, _h - (scrollBarSize + resize), scrollBarSize, scrollBarSize);
    }

    /**
     *
     * @return
     */
    public XYWH_I panY() {
        float _h = getH() - ((scrollBarSize * 3) + resize);// top botton and resize areas = scrollBarSize*3
        int _y = (int) (alignY * _h);
        int ph = 0;
        if (hSlack != 0) {
            ph = (int) ((_h / (_h + hSlack)) * _h);
        }
        if (ph < scrollBarSize) {
            ph = scrollBarSize;
        }
        return new XYWH_I((int) (getW() - (scrollBarSize + resize)), scrollBarSize + (int) (_y - (ph * alignY)), scrollBarSize, ph);
    }

    /**
     *
     * @return
     */
    public XYWH_I resizeY() {
        return new XYWH_I((int) (getW() - resize), 0, resize, getH());
    }

    /**
     *
     * @return
     */
    public XYWH_I resizeX() {
        return new XYWH_I(0, (int) (getH() - resize), getW(), resize);
    }

    /**
     *
     * @return
     */
    public XYWH_I panX() {
        float _w = getW() - ((scrollBarSize * 3) + resize);// top botton and resize areas = scrollBarSize*3
        int _x = (int) (alignX * _w);
        int pw = 0;
        if (wSlack != 0) {
            pw = (int) ((_w / (_w + wSlack)) * _w);
        }
        if (pw < scrollBarSize) {
            pw = scrollBarSize;
        }
        return new XYWH_I(scrollBarSize + (int) (_x - (pw * alignX)), (int) (getH() - (scrollBarSize + resize)), pw, scrollBarSize);
    }

    @Override
    public void paintBackground(ICanvas _g, int _x, int _y, int _w, int _h) {
        if (maxWBeforePan > -1 && maxWBeforePan > view.getW()) {
            //alignX = -1;
            w = view.getW();
            parent.layoutInterior();
            parent.repair();
            parent.flush();
        } else if (maxWBeforePan > -1 && alignX < 0) {
            w = maxWBeforePan;
            alignX = 0;
            parent.layoutInterior();
            parent.repair();
            parent.flush();
        }
        if (maxHBeforePan > -1 && maxHBeforePan > view.getH()) {
            //alignY = -1;
            h = view.getH();
            parent.layoutInterior();
            parent.repair();
            parent.flush();
        } else if (maxHBeforePan > -1 && alignY < 0) {
            h = maxHBeforePan;
            alignY = 0;
            parent.layoutInterior();
            parent.repair();
            parent.flush();
        }
        super.paintBackground(_g, _x, _y, _w, _h);
    }

    @Override
    public IView disbatchEvent(IView parent, AViewEvent event) {
        if (isPanEvent(event) || scrollingX || scrollingY || resizingX || resizingY) {
            return this;
        }
        if (event instanceof MouseMoved) {
            XY_I p = ((MouseMoved) event).getPoint();
            if ((alignX > -1 && p.x > getW() - scrollBarSize) || (alignY > -1 && p.y > getH() - scrollBarSize)) {
                setPaintingScrollBars(p, isPanEvent(event));
                return this;//??

            } else if ((p.x > getW() - 4) || (p.y > getH() - 4)) {
                setPaintingScrollBars(p, isPanEvent(event));
                return this;//??
            } else {
                paintXScrollbar = false;
                paintYScrollbar = false;
                paintXResizing = false;
                paintYResizing = false;
                return super.disbatchEvent(parent, event);
            }
        } else if (event instanceof MousePressed) {
            XY_I p = ((MousePressed) event).getPoint();
            setModePoint(p, isPanEvent(event));
            if (p.x > getW() - scrollBarSize && p.y > getH() - scrollBarSize) {
                setPan(false);
                return this;
            } else if ((p.x > getW() - 4) || (p.y > getH() - 4)) {
                setPan(false);
                return this;
            }

            if (scrollingX || scrollingY || resizingX || resizingY) {
                return this;
            } else {
                return super.disbatchEvent(parent, event);
            }
        } else if (event instanceof MouseReleased) {
            XY_I p = ((MouseReleased) event).getPoint();
            setModePoint(p, isPanEvent(event));
            if (p.x > getW() - scrollBarSize && p.y > getH() - scrollBarSize) {
                setPan(false);
                return this;
            } else if ((p.x > getW() - 4) || (p.y > getH() - 4)) {
                setPan(false);
                return this;
            }
            if (scrollingX || scrollingY || resizingX || resizingY) {
                return this;
            } else {
                return super.disbatchEvent(parent, event);
            }
        } else {
            return super.disbatchEvent(parent, event);
        }
    }

    boolean isPanEvent(IEvent event) {
        if (event instanceof AInputEvent) {
            boolean isPanEvent = (((AInputEvent) event).isShiftDown() && ((AInputEvent) event).isControlDown());
            if (isPanEvent) {
                return isPanEvent;
            }
        }
        lmp = null;
        return false;
    }

    // IMouseWheelEvents
    @Override
    public void mouseWheel(MouseWheel _e) {
        int rotation = _e.getWheelRotation();
        if (_e.isShiftDown()) {
            if (rotation < 0) {
                for (int i = rotation; i < 0; i++) {
                    incRightScroll();
                }
            } else {
                for (int i = 0; i < rotation; i++) {
                    incLeftScroll();
                }
            }
        } else {
            if (rotation < 0) {
                for (int i = rotation; i < 0; i++) {
                    incUpScroll();
                }
            } else {
                for (int i = 0; i < rotation; i++) {
                    incDownScroll();
                }
            }
        }
    }

    /**
     *
     */
    protected void incUpScroll() {
        float p = getAlignY() - (((float) getH() / 3) / ((float) getContent().getH()));
        setPositionY(UFloat.checkFloat(p, 0.0f));
        new Thread() {

            @Override
            public void run() {
                while (mouseIsDown) {
                    try {
                        Thread.sleep(1000);
                        if (!mouseIsDown) break;
                    } catch (InterruptedException ex) {
                    }
                    float p = getAlignY() - (((float) getH() / 3) / ((float) getContent().getH()));
                    setPositionY(UFloat.checkFloat(p, 0.0f));
                }
            }
        }.start();
    }

    /**
     *
     */
    protected void incDownScroll() {
        float p = getAlignY() + (((float) getH() / 3) / ((float) getContent().getH()));
        setPositionY(UFloat.checkFloat(p, 1.0f));
        new Thread() {

            @Override
            public void run() {
                while (mouseIsDown) {
                    try {
                        Thread.sleep(1000);
                        if (!mouseIsDown) break;
                    } catch (InterruptedException ex) {
                    }
                    float p = getAlignY() + (((float) getH() / 3) / ((float) getContent().getH()));
                    setPositionY(UFloat.checkFloat(p, 1.0f));
                }
            }
        }.start();
    }

    /**
     *
     */
    protected void incLeftScroll() {
        float p = getAlignX() - (((float) getW() / 3) / ((float) getContent().getW()));
        setPositionX(UFloat.checkFloat(p, 0.0f));
        new Thread() {

            @Override
            public void run() {
                while (mouseIsDown) {
                    try {
                        Thread.sleep(1000);
                        if (!mouseIsDown) break;
                    } catch (InterruptedException ex) {
                    }
                    float p = getAlignX() - (((float) getW() / 3) / ((float) getContent().getW()));
                    setPositionX(UFloat.checkFloat(p, 0.0f));
                }
            }
        }.start();
    }

    /**
     *
     */
    protected void incRightScroll() {
        float p = getAlignX() + (((float) getW() / 3) / ((float) getContent().getW()));
        setPositionX(UFloat.checkFloat(p, 1.0f));
        new Thread() {

            @Override
            public void run() {
                while (mouseIsDown) {
                    try {
                        Thread.sleep(1000);
                        if (!mouseIsDown) break;
                    } catch (InterruptedException ex) {
                    }
                    float p = getAlignX() + (((float) getW() / 3) / ((float) getContent().getW()));
                    setPositionX(UFloat.checkFloat(p, 1.0f));
                }
            }
        }.start();
    }

    /**
     *
     * @param _position
     */
    public void setPositionX(float _position) {
        if (_position < 0.0f) {
            _position = 0.0f;
        }
        if (_position > 1.0f) {
            _position = 1.0f;
        }
        setAlignX(_position, this);
        paint();
    }

    /**
     *
     * @param _position
     */
    public void setPositionY(float _position) {
        if (_position < 0.0f) {
            _position = 0.0f;
        }
        if (_position > 1.0f) {
            _position = 1.0f;
        }
        setAlignY(_position, this);
        paint();
    }

    /**
     *
     * @return
     */
    public float getPositionX() {
        return getAlignX();
    }

    /**
     *
     * @return
     */
    public float getPositionY() {
        return getAlignY();
    }

    /**
     *
     * @param _pan
     */
    public void setPan(boolean _pan) {
        pan = _pan;
        //grabFocus();
        //getRootView().setMouseWheelFocus(0,this);//!!
    }
    boolean mouseIsDown = false;
    // IMouseEvents

    @Override
    public void mouseEntered(MouseEntered _e) {
        DragAndDrop.cDefault.mouseEntered(_e);
        mouseIsDown = false;
    }

    @Override
    public void mouseExited(MouseExited _e) {
        DragAndDrop.cDefault.mouseExited(_e);
        mouseIsDown = false;
    }

    @Override
    public void mousePressed(MousePressed _e) {
        if (_e.getClickCount() > 0) {
            DragAndDrop.cDefault.mousePressed(_e);
        }
        mouseIsDown = true;
        setModePoint(_e.getPoint(), isPanEvent(_e));
        if (incDown().contains(_e.getPoint())) {
            incDownScroll();
        }
        if (incLeft().contains(_e.getPoint())) {
            incLeftScroll();
        }
        if (incRight().contains(_e.getPoint())) {
            incRight();
        }
        if (incUp().contains(_e.getPoint())) {
            incUpScroll();
        }

    }

    /**
     *
     * @param _p
     * @param _panXY
     */
    public void setModePoint(XY_I _p, boolean _panXY) {
        if (_panXY) {
            scrollingX = true;
            scrollingY = true;
        } else if (resizeable && (_p.x > getW() - scrollBarSize - resize && _p.y > getH() - scrollBarSize - resize)) {
            resizingX = true;
            resizingY = true;
            scrollingX = false;
            scrollingY = false;
        } else if (resizeable && resizeY().contains(_p)) {
            resizingX = true;
            resizingY = false;
            scrollingX = false;
            scrollingY = false;
        } else if (resizeable && resizeX().contains(_p)) {
            resizingX = false;
            resizingY = true;
            scrollingX = false;
            scrollingY = false;
        } else if (panY().contains(_p.x, _p.y) || incRight().contains(_p.x, _p.y) || incLeft().contains(_p.x, _p.y)) {
            resizingX = false;
            resizingY = false;
            scrollingX = false;
            scrollingY = true;
        } else if (panX().contains(_p.x, _p.y) || incUp().contains(_p.x, _p.y) || incDown().contains(_p.x, _p.y)) {
            resizingX = false;
            resizingY = false;
            scrollingX = true;
            scrollingY = false;
        } else {
            resizingX = false;
            resizingY = false;
            scrollingX = false;
            scrollingY = false;
        }
    }

    @Override
    public void mouseReleased(MouseReleased _e) {
        mouseIsDown = false;
        PickupAndDrop.cDefault.event(_e);
        if (_e.getClickCount() > 0) {
            DragAndDrop.cDefault.mouseReleased(_e);

        }
        resizingX = false;
        resizingY = false;
        scrollingX = false;
        scrollingY = false;
        lmp = null;
    }

    // IMouseMotionEvents
    /**
     *
     * @param _p
     * @param _pan
     */
    public void setPaintingScrollBars(XY_I _p, boolean _pan) {

        if (resizeX().contains(_p)) {
            paintXResizing = true;
            paint();
            return;
        } else {
            paintXResizing = false;
        }
        if (resizeY().contains(_p)) {
            paintYResizing = true;
            paint();
            return;
        } else {
            paintYResizing = false;
        }
        if (_p.x > getW() - scrollBarSize || _pan) {
            if (paintYScrollbar); else {
                getRootView().setMouseWheelFocus(0, this);//!!
                paintYScrollbar = true;
                paint();
            }
        } else {
            paintYScrollbar = false;
            paint();
        }

        if (_p.y > getH() - scrollBarSize || _pan) {
            if (paintXScrollbar); else {
                getRootView().setMouseWheelFocus(0, this);//!!
                paintXScrollbar = true;
                paint();
            }
        } else {
            paintXScrollbar = false;
            paint();
        }
    }
    XY_I lmp = null;

    @Override
    public void mouseMoved(MouseMoved _e) {
        XY_I p = _e.getPoint();
        setPaintingScrollBars(p, isPanEvent(_e));
        if (isPanEvent(_e)) {
            if (lmp == null) {
                lmp = _e.getPoint();
                return;
            }
            XY_I mp = _e.getPoint();

            float yrate = (lmp.y - mp.y) / (getH() / 2);
            setPositionY(getPositionY() - yrate);
            float xrate = (lmp.x - mp.x) / (getW() / 2);
            setPositionX(getPositionX() - xrate);

            lmp = mp;
        }
    }

    @Override
    public void mouseDragged(MouseDragged _e) {
        DragAndDrop.cDefault.mouseDragged(_e);
        if (!operable) {
            return;
        }
        if (resizeable && (resizingX || resizingY)) {

            if (fixedW == -1 && resizingX) {
                w += _e.getDeltaX();
            }
            if (fixedH == -1 && resizingY) {
                h += _e.getDeltaY();
            }
            layoutInterior();
            parent.layoutInterior();
            parent.paint();
        }
        if (scrollingY) {
            XYWH_I py = panY();
            int hh = py.h / 2;
            MinMaxInt mm = new MinMaxInt();
            mm.value(hh);
            mm.value((int) (getH() - hh));
            double position = mm.zeroToOne(_e.getPoint().y);
            position = UDouble.clamp(position, 0, 1);
            setPositionY((float) position);
        }
        if (scrollingX) {
            XYWH_I px = panX();
            int hw = px.w / 2;
            MinMaxInt mm = new MinMaxInt();
            mm.value(hw);
            mm.value((int) (getW() - hw));
            double position = mm.zeroToOne(_e.getPoint().x);
            position = UDouble.clamp(position, 0, 1);
            setPositionX((float) position);
        }
    }

    // IDrop
    @Override
    public IDropMode accepts(Object value, AInputEvent _e) {
        if (dropCallback == null) {
            return null;
        }
        return (IDropMode) dropCallback.callback(new Object[]{value, _e});
    }

    @Override
    public void dropParcel(final Object value, final IDropMode mode) {
        if (droppedCallback == null) {
            return;
        }
        new Thread() {// refactor add to drop callstack instead

            @Override
            public void run() {
                droppedCallback.callback(new Object[]{value, mode});
            }
        }.start();
    }
}
