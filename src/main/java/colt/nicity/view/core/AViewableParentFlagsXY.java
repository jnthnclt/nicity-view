/*
 * AViewableParentFlagsXY.java.java
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
package colt.nicity.view.core;

import colt.nicity.core.memory.struct.UXYWH_I;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.adaptor.IViewEventConstants;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Administrator
 */
public abstract class AViewableParentFlagsXY extends AViewable {

    /**
     *
     */
    protected AtomicReference<IView> parent = new AtomicReference<IView>(NullView.cNull);
    /**
     *
     */
    protected float x;
    /**
     *
     */
    protected float y;
    /**
     *
     */
    protected int flags;

    /**
     *
     */
    public AViewableParentFlagsXY() {
        super();
    }

    @Override
    public IView getParentView() {
        return parent.get();
    }

    @Override
    public void setParentView(IView _parent) {
        if (_parent == this || parent.get() == _parent) {
            return;
        }
        if (_parent == null) {
            _parent = NullView.cNull;
        }
        parent.set(_parent);
    }

    @Override
    public IView transferFocusToParent(long _who) {
        IView got = parent.get();
        if (got == NullView.cNull) {
            return NullView.cNull;
        }
        if (got.isEventEnabled(IViewEventConstants.cKeyEvent)) {
            got.grabFocus(_who);
            return got;
        } else {
            return got.transferFocusToParent(_who);
        }
    }

    @Override
    public IView spans(int spanMasks) {
        flags |= spanMasks;
        return this;
    }

    @Override
    public void unspans(int spanMasks) {
        flags &= ~spanMasks;
    }

    @Override
    public void enableFlag(int flagsToEnable) {
        flags |= flagsToEnable;
    }

    @Override
    public void disableFlag(int flagsToDisable) {
        flags &= ~flagsToDisable;
    }

    @Override
    public boolean hasFlag(int flag) {
        return (flags & flag) == flag;
    }

    /**
     *
     * @param _flex
     */
    protected void layoutParent(Flex _flex) {
        IView got = parent.get();
        got.disableFlag(UV.cInterior);
        got.layoutInterior(_flex);
    }

    @Override
    public void mend() {
        if ((flags & UV.cMend) == UV.cMend) {
            return;
        }
        flags |= UV.cMend;
        parent.get().mend();
    }

    @Override
    public void flush() {
        getRootView().flush();
    }

    @Override
    public boolean isVisible(int _x, int _y, int _w, int _h) {
        XYWH_I r = UXYWH_I.intersection(_x, _y, _w, _h, 0, 0, (int) getW(), (int) getH());
        if (r == null) {
            return false;
        }
        return parent.get().isVisible(r.x + (int) x, r.y + (int) y, r.w, r.h);
    }

    @Override
    public boolean isVisible() {
        return parent.get().isVisible((int) x, (int) y, (int) getW(), (int) getH());
    }

    /**
     *
     * @return
     */
    protected float getExpandedW() {
        float e = 0;
        IView p = this;
        for (; p.hasFlag(UV.cExpandW); p = p.getParentView()) {
            e += p.getX();
        }
        return (e < 0) ? 0 : e;
    }

    /**
     *
     * @return
     */
    protected float getExpandedN() {
        float e = 0;
        IView p = this;
        for (; p.hasFlag(UV.cExpandN); p = p.getParentView()) {
            e += p.getY();
        }
        return (e < 0) ? 0 : e;
    }

    /**
     *
     * @return
     */
    protected float getExpandedE() {
        float e = 0;
        IView p = this;
        for (; p.hasFlag(UV.cExpandE); p = p.getParentView()) {
            e += p.getParentView().getW() - (p.getX() + p.getW());
        }
        return (e < 0) ? 0 : e;
    }

    /**
     *
     * @return
     */
    protected float getExpandedS() {
        float e = 0;
        IView p = this;
        for (; p.hasFlag(UV.cExpandS); p = p.getParentView()) {
            e += p.getParentView().getH() - (p.getY() + p.getH());
        }
        return (e < 0) ? 0 : e;
    }

    // Location
    /**
     *
     * @return
     */
    @Override
    public XY_I getLocation() {
        return new XY_I((int) x, (int) y);
    }

    /**
     *
     * @return
     */
    @Override
    public XYWH_I getBounds() {
        return new XYWH_I((int) x, (int) y, (int) getW(), (int) getH());
    }

    @Override
    public XYWH_I getEventBounds() {
        float en = ((flags & UV.cExpandN) == UV.cExpandN) ? getExpandedN() : 0.0f;
        float es = ((flags & UV.cExpandS) == UV.cExpandS) ? getExpandedS() : 0.0f;
        float ee = ((flags & UV.cExpandE) == UV.cExpandE) ? getExpandedE() : 0.0f;
        float ew = ((flags & UV.cExpandW) == UV.cExpandW) ? getExpandedW() : 0.0f;

        //tx-ew,ty-en,ew+w+ee,en+h+es
        return new XYWH_I((int) (x - ew), (int) (y - en), (int) (ew + getW() + ee), (int) (en + getH() + es));
    }

    /**
     *
     * @return
     */
    public XYWH_I getLocalEventBounds() {
        float en = ((flags & UV.cExpandN) == UV.cExpandN) ? getExpandedN() : 0.0f;
        float es = ((flags & UV.cExpandS) == UV.cExpandS) ? getExpandedS() : 0.0f;
        float ee = ((flags & UV.cExpandE) == UV.cExpandE) ? getExpandedE() : 0.0f;
        float ew = ((flags & UV.cExpandW) == UV.cExpandW) ? getExpandedW() : 0.0f;
        //tx-ew,ty-en,ew+w+ee,en+h+es
        return new XYWH_I((int) (-ew), (int) (-en), (int) (ew + getW() + ee), (int) (en + getH() + es));
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setLocation(float _x, float _y) {
        x = _x;
        y = _y;
        if (x != _x || y != _y) {
            parent.get().repair();
        }
    }

    @Override
    public void setX(float _x) {
        x = _x;
    }

    @Override
    public void setY(float _y) {
        y = _y;
    }

    @Override
    public void promoteEvent(IEvent _task) {
        IView got = parent.get();
        if (got != null) {
            got.promoteEvent(_task);
        }
    }
}
