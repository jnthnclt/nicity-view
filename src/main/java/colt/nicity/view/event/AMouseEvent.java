/*
 * AMouseEvent.java.java
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
package colt.nicity.view.event;

import colt.nicity.core.memory.struct.UXYWH_I;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.adaptor.IViewEventConstants;
import colt.nicity.view.core.NullView;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
abstract public class AMouseEvent extends AInputEvent {

    private float tx = 0;
    private float ty = 0;
    /**
     *
     */
    protected float cx;
    /**
     *
     */
    protected float cy;
    /**
     *
     */
    protected float cw;
    /**
     *
     */
    protected float ch;
    /**
     *
     */
    protected int x;
    /**
     *
     */
    protected int y;
    /**
     *
     */
    protected int z;
    /**
     *
     */
    protected int clickCount;
    /**
     *
     */
    protected boolean isDragging = false;

    /**
     *
     * @param _x
     * @param _y
     */
    public void shiftTXY(float _x, float _y) {
        tx += _x;
        ty += _y;
    }

    /**
     *
     * @param _x
     * @param _y
     */
    public void shiftXY(float _x, float _y) {
        x += (int) _x;
        y += (int) _y;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @param _x
     */
    public void setX(int _x) {
        x = _x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param _y
     */
    public void setY(int _y) {
        y = _y;
    }

    /**
     *
     * @return
     */
    public int getZ() {
        return z;
    }

    /**
     *
     * @param _z
     */
    public void setZ(int _z) {
        z = _z;
    }

    /**
     *
     * @return
     */
    public XY_I getPoint() {
        return new XY_I(x, y);
    }

    /**
     *
     * @return
     */
    public int getClickCount() {
        return clickCount;
    }

    /**
     *
     * @param clickCount
     */
    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    /**
     *
     * @return
     */
    public boolean isSingleClick() {
        return clickCount == 1;
    }

    /**
     *
     * @return
     */
    public boolean isDoubleClick() {
        return clickCount == 2;
    }

    /**
     *
     * @return
     */
    public boolean isTripleClick() {
        return clickCount == 3;
    }

    /**
     *
     * @return
     */
    public boolean isDragging() {
        return isDragging;
    }

    /**
     *
     * @param _isDragging
     */
    public void setIsDragging(boolean _isDragging) {
        isDragging = _isDragging;
    }

    /**
     *
     * @param parent
     * @param view
     * @return
     */
    @Override
    public IView disbatchEvent(IView parent, IView view) {
        if (parent != NullView.cNull && parent != view) {
            view.setParentView(parent);
        }

        XYWH_I viewRect = view.getEventBounds();
        XYWH_I eventClip = UXYWH_I.intersection(
                (int) cx, (int) cy, (int) cw, (int) ch,
                (int) tx + viewRect.x, (int) ty + viewRect.y, viewRect.w, viewRect.h);
        if (eventClip == null) {
            return NullView.cNull;
        }
        if (!(eventClip.contains((int) (x + tx), (int) (y + ty)))) {
            return NullView.cNull;
        }

        cx = eventClip.x;
        cy = eventClip.y;
        cw = eventClip.w;
        ch = eventClip.h;
        int vx = (int) view.getX();
        int vy = (int) view.getY();
        shiftXY(-vx, -vy);
        shiftTXY(vx, vy);
        IView disbatch = view.disbatchEvent(parent, this);
        if (disbatch == NullView.cNull) {
            shiftXY(vx, vy);
            shiftTXY(-vx, -vy);
            return disbatch;
        } else {//1-6-09
            shiftXY(vx, vy);
            shiftTXY(-vx, -vy);
            vx = viewRect.x;
            vy = viewRect.y;
            shiftXY(-vx, -vy);
            shiftTXY(vx, vy);
            return disbatch;
        }
    }

    @Override
    public String toString() {
        return this.getClass() + " x=" + x + " y=" + y + " clicks=" + clickCount + " " + super.toString();
    }

    /**
     *
     * @return
     */
    @Override
    public long getMask() {
        return IViewEventConstants.cMouseEvent;
    }

    /**
     *
     * @param _e
     */
    public void inherit(AMouseEvent _e) {
        tx = _e.tx;
        ty = _e.ty;
        cx = _e.cx;
        cy = _e.cy;
        cw = _e.cw;
        ch = _e.ch;

        x = _e.x;
        y = _e.y;
        z = _e.z;
        clickCount = _e.clickCount;
        isDragging = _e.isDragging;
        super.inherit(_e);
    }
}
