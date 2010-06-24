/*
 * VPickList.java.java
 *
 * Created on 03-12-2010 06:34:17 PM
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

import colt.nicity.view.event.MousePressed;
import colt.nicity.core.value.IValue;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VPickList extends Viewer {

    // Overloadables
    /**
     *
     * @param _value
     */
    public void pickedValue(Object _value) {
        if (pickedValue != null) {
            pickedValue.setValue(_value);
        }
        close();
    }
    private IValue pickedValue;

    /**
     *
     */
    public VPickList() {
    }

    /**
     *
     * @param _views
     * @param _values
     */
    public VPickList(Object[] _views, Object[] _values) {
        rebuild(_views, _values);
    }

    /**
     *
     * @param _values
     * @param _picked
     */
    public VPickList(Object[] _values, IValue _picked) {
        this(_values, _values, _picked);
    }

    /**
     *
     * @param _views
     * @param _values
     * @param _picked
     */
    public VPickList(Object[] _views, Object[] _values, IValue _picked) {
        rebuild(_views, _values);
        pickedValue = _picked;
    }

    /**
     * 
     */
    public void close() {
        getRootView().dispose();
    }

    /**
     *
     * @param _views
     * @param _values
     */
    public void rebuild(Object[] _views, Object[] _values) {
        VChain c = new VChain(UV.cSWNW);
        for (int i = 0; i < _views.length; i++) {
            IView v = null;
            if (_views[i] instanceof IView) {
                v = (IView) _views[i];
            } else {
                v = new VString(_views[i]);
            }
            GlassViewer behindGlass = new GlassViewer(v);

            final Object picked = _values[i];
            VButton pick = new VButton(behindGlass) {

                @Override
                public void mousePressed(MousePressed _e) {
                    super.mousePressed(_e);
                    pickedValue(picked);
                }
            };
            pick.spans(UV.cXEW);
            c.add(pick);
        }
        setContent(c);
        layoutInterior();
    }
}
