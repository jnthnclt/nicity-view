/*
 * Placers.java.java
 *
 * Created on 01-03-2010 01:31:35 PM
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
import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IPlacer;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class Placers extends APlacers {

    /**
     *
     */
    private Object set = null;

    /**
     *
     */
    public Placers() {
    }

    /**
     *
     * @param instance
     */
    public Placers(IPlacer instance) {
        set = instance;
    }

    //!! addPlacer should be directly improved such that it is impossible to add a
    // IPlacer that will cause recursion. For now use careful programing!
    @Override
    public IPlacer addPlacer(IPlacer value) {
        IPlacer found = findPlacer(value);
        if (found != NullPlacer.cNull) {
            return found;
        }
        if (set == null) {
            set = value;
            return (IPlacer) set;
        }

        if (!(set instanceof IPlacer[])) {
            set = new IPlacer[]{(IPlacer) set};
        }
        IPlacer[] array = (IPlacer[]) set;
        int l = array.length;

        IPlacer[] tmp = new IPlacer[l + 1];
        System.arraycopy(array, 0, tmp, 0, l);
        tmp[l] = value;
        set = tmp;

        return value;
    }

    @Override
    public IPlacer findPlacer(IPlacer value) {
        if (value == null || set == null) {
            return NullPlacer.cNull;
        }

        if (!(set instanceof IPlacer[])) {
            IPlacer p = (IPlacer) set;
            if (p.getView() == value.getView()) {
                return p;
            } else {
                return NullPlacer.cNull;
            }
        }
        IPlacer[] array = (IPlacer[]) set;
        for (int i = 0; i < array.length; i++) {
            if (array[i].getView() == value.getView()) {
                return (IPlacer) array[i];
            }
        }
        return NullPlacer.cNull;
    }

    @Override
    public IPlacer findView(IView view) {

        if (view == null || set == null) {
            return NullPlacer.cNull;
        }

        if (!(set instanceof IPlacer[])) {
            IView v = ((IPlacer) set).getView();
            if (v == view) {
                return (IPlacer) set;
            }
            return v.find(view);
        }

        IPlacer[] array = (IPlacer[]) set;
        for (int i = 0; i < array.length; i++) {
            IView v = array[i].getView();
            if (v == view) {
                return (IPlacer) array[i];
            } else {
                IPlacer found = v.find(view);
                if (found != NullPlacer.cNull) {
                    return found;
                }
            }
        }
        return NullPlacer.cNull;
    }

    @Override
    public IPlacer removePlacer(IPlacer value) {
        if (value == null || set == null) {
            return NullPlacer.cNull;
        }
        if (!(set instanceof IPlacer[])) {
            IPlacer p = (IPlacer) set;
            if (p.getView() == value.getView()) {
                set = null;
                return p;
            }
            return NullPlacer.cNull;
        }

        IPlacer[] array = (IPlacer[]) set;
        for (int i = 0; i < array.length; i++) {
            if (array[i].getView() == value.getView()) {
                value = (IPlacer) array[i];
                IPlacer[] tmp = new IPlacer[array.length - 1];
                System.arraycopy(array, 0, tmp, 0, i);
                if (i < tmp.length) {
                    System.arraycopy(array, i + 1, tmp, i, tmp.length - i);
                }
                set = (tmp.length == 0) ? null : tmp;
                return value;
            }
        }
        return NullPlacer.cNull;
    }

    @Override
    public IPlacer removeView(IView view) {
        if (view == null || set == null) {
            return NullPlacer.cNull;
        }
        if (!(set instanceof IPlacer[])) {
            IPlacer p = (IPlacer) set;
            if (p.getView() == view) {
                set = null;
                return p;
            }
            return NullPlacer.cNull;
        }

        IPlacer[] array = (IPlacer[]) set;
        for (int i = 0; i < array.length; i++) {
            if (array[i].getView() == view) {
                IPlacer rv = (IPlacer) array[i];
                IPlacer[] tmp = new IPlacer[array.length - 1];
                System.arraycopy(array, 0, tmp, 0, i);
                if (i < tmp.length) {
                    System.arraycopy(array, i + 1, tmp, i, tmp.length - i);
                }
                set = (tmp.length == 0) ? null : tmp;
                return rv;
            }
        }
        return NullPlacer.cNull;
    }

    @Override
    public int size() {
        if (set == null) {
            return 0;
        }
        if (!(set instanceof IPlacer[])) {
            return 1;
        }
        return ((IPlacer[]) set).length;
    }

    @Override
    public Object[] toArray() {
        if (set == null) {
            return new IPlacer[0];
        }
        if (!(set instanceof IPlacer[])) {
            return new IPlacer[]{(IPlacer) set};
        }
        return (IPlacer[]) set;
    }

    @Override
    public void clear() {
        set = null;
    }

    @Override
    public void placeInside(IView parent, IView anchor, WH_F size, Flex _flex) {
        if (set == null) {
            return;
        }

        if (set instanceof IPlacer[]) {
            IPlacer[] array = (IPlacer[]) set;
            for (int i = 0; i < array.length; i++) {
                array[i].placeInside(parent, anchor, size, _flex);
            }
            return;
        }

        ((IPlacer) set).placeInside(parent, anchor, size, _flex);
    }

    @Override
    public void paintPlacers(IView _parent, ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        if (set == null) {
            return;
        }

        if (set instanceof IPlacer[]) {
            IPlacer[] array = (IPlacer[]) set;
            for (int i = array.length - 1; i > -1; i--) {
                array[i].paintPlacer(_parent, g, _layer, mode, _painted);
            }
            return;
        }

        ((IPlacer) set).paintPlacer(_parent, g, _layer, mode, _painted);
        return;
    }

    @Override
    public IView disbatchEventToPlacers(IView parent, AViewEvent event) {
        if (set == null) {
            return NullView.cNull;
        }

        if (set instanceof IPlacer[]) {
            IPlacer[] array = (IPlacer[]) set;
            for (int i = 0; i < array.length; i++) {
                IView v = array[i].disbatchEvent(parent, event);
                if (v != NullView.cNull) {
                    return v;
                }
            }
            return NullView.cNull;
        }

        return ((IPlacer) set).disbatchEvent(parent, event);
    }

    @Override
    public IView transferFocusToChild(long _who) {
        if (set == null) {
            return NullView.cNull;
        }

        if (set instanceof IPlacer[]) {
            IPlacer[] array = (IPlacer[]) set;
            for (int i = 0; i < array.length; i++) {
                IView v = array[i].transferFocusToChild(_who);
                if (v != NullView.cNull) {
                    return v;
                }
            }
            return NullView.cNull;
        }

        return ((IPlacer) set).transferFocusToChild(_who);
    }
}
