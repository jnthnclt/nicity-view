/*
 * AView.java.java
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
package colt.nicity.view.core;

import colt.nicity.view.event.AViewEvent;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IRootView;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public abstract class AView extends AViewableParentFlagsXY {

    /**
     *
     */
    public AView() {
        super();
    }

    @Override
    public IRootView getRootView() {
        IView i = this;
        //?? maybe NullView should implement IRootView
        while (i != NullView.cNull && !(i instanceof IRootView)) {
            i = i.getParentView();
        }
        if (i == NullView.cNull) {
            return NullRootView.cNull;
        }
        return (IRootView) i;
    }

    @Override
    public void grabFocus(long _who) {
        IRootView root = getRootView();
        if (root == null || root == NullRootView.cNull) {
            flags |= UV.cFocus;
        }
        root.setFocusedView(_who, this);
    }

    @Override
    public void grabHardFocus(long _who) {
        getRootView().setHardFocusedView(_who, this);
    }

    @Override
    public void releaseHardFocus(long _who) {
        getRootView().setHardFocusedView(_who, null);
    }

    @Override
    public IView transferFocusToChild(long _who) {
        if (isEventEnabled(AViewEvent.cKeyEvent)) {
            grabFocus(_who);
            return this;
        }
        return NullView.cNull;
    }

    @Override
    public IView transferFocusToNearestNeighbor(long _who, int direction) {
        return NullView.cNull;
    }

    @Override
    public void layoutInterior() {
        flags &= ~UV.cInterior;
        layoutInterior(new Flex(this, 0, 0));
    }

    @Override
    public void layoutAllInterior() {
        flags &= ~UV.cInterior;
        flags |= UV.cAllInterior;
        layoutInterior(new Flex(this, 0, 0));
    }

    @Override
    public void layoutInterior(Flex _flex) {
        if (_flex.interior && (flags & UV.cInterior) == UV.cInterior) {
            if (parent.hasFlag(UV.cAllInterior)) {
                flags |= UV.cAllInterior;
            } else if ((flags & UV.cAllInterior) != UV.cAllInterior) {
                return;
            }
        }
        flags |= UV.cInterior;
        if ((flags & UV.cActive) == UV.cActive) {
            return;
        }
        flags |= UV.cActive;
        flags |= UV.cFlexing;

        float _w = getW();
        float _h = getH();

        layoutInterior(this, _flex);

        float w = getW();
        float h = getH();

        if (parent != NullView.cNull
                && !parent.hasFlag(UV.cActive)
                && ((w != _w || h != _h) || hasFlag(UV.cAllInterior))) {
            if (_flex != null) {
                _flex.x = -(_w - w);
                _flex.y = -(_h - h);
            }
            layoutParent(_flex);
        }

        /////flags &= ~cInterior;//??
        flags &= ~UV.cAllInterior;
        flags &= ~UV.cActive;
        flags &= ~UV.cFlexing;

        repair();
    }

    /**
     *
     * @return
     */
    public float ox() {
        return 0;
    }

    /**
     *
     * @return
     */
    public float oy() {
        return 0;
    }

    @Override
    public void paint(IView _parent, ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        if (mode == UV.cMend && (flags & UV.cMend) != UV.cMend) {
            return;
        }
        Layer l = (Layer) _layer.clone();
        float w = getW();
        float h = getH();
        float en = ((flags & UV.cExpandN) == UV.cExpandN) ? getExpandedN() : 0.0f;
        float es = ((flags & UV.cExpandS) == UV.cExpandS) ? getExpandedS() : 0.0f;
        float ee = ((flags & UV.cExpandE) == UV.cExpandE) ? getExpandedE() : 0.0f;
        float ew = ((flags & UV.cExpandW) == UV.cExpandW) ? getExpandedW() : 0.0f;
        float tx = l.tx + x;
        float ty = l.ty + y;
        if (!l.intersection(tx - ew, ty - en, ew + w + ee, en + h + es)) {// area out of view so why paint
            flags &= ~UV.cMend;// remove mend
            return;
        }
        int px = (int) (tx - ew);
        int py = (int) (ty - en);
        int pw = (int) (ew + w + ee);
        int ph = (int) (en + h + es);
        l.clobber(tx + ox(), ty + oy(), pw, ph);

        if (mode == UV.cRepair || (flags & UV.cRepair) == UV.cRepair) {
            mode = UV.cRepair;
            g.setClip((int) l.x, (int) l.y, (int) l.w, (int) l.h);
            g.translate(px, py);
            paintBackground(g, 0, 0, pw, ph);
            g.translate(-(px), -(py));
            g.setClip((int) l.x, (int) l.y, (int) l.w, (int) l.h);

            paintBody(g, l, mode, _painted);

            g.setClip((int) l.x, (int) l.y, (int) l.w, (int) l.h);
            g.translate(px, py);
            paintBorder(g, 0, 0, pw, ph);
            g.translate(-(px), -(py));
        } else {
            paintBody(g, l, mode, _painted);//??
        }

        flags &= ~UV.cRepair;// remove repair flag
        flags &= ~UV.cMend;// remove mend flag
        return;
    }

    // Place
    @Override
    public IView place(IView _view, Place _place) {
        add(new Placer(_view, _place));
        return _view;
    }

    @Override
    public IView place(IView _view, Place _place, Flex _flex) {
        add(new Placer(_view, new Place(_place, _flex)));
        return _view;
    }

    @Override
    public IView place(IView _view, Place _place, Flex _interior, Flex _exterior) {
        add(new Placer(_view, new Place(_place, _interior, _exterior)));
        return _view;
    }

    // Object
    @Override
    public String toString() {
        return super.toString() + "[" + x + "," + y + "," + getW() + "," + getH() + "]";
    }
}
