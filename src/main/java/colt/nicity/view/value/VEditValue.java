/*
 * VEditValue.java.java
 *
 * Created on 03-12-2010 06:40:19 PM
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
package colt.nicity.view.value;

import colt.nicity.core.observer.AObserver;
import colt.nicity.core.observer.Change;
import colt.nicity.core.observer.IObservable;
import colt.nicity.core.value.IValue;
import colt.nicity.view.core.EditString;
import colt.nicity.view.core.Place;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VEditString;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VEditValue extends Viewer {

    /**
     *
     */
    protected IValue value;
    /**
     *
     */
    protected boolean setValueOnEnterOnly;
    private AObserver observer;

    /**
     *
     * @param _value
     */
    public VEditValue(IValue _value) {
        this(_value, false);
    }

    /**
     *
     * @param _value
     * @param _w
     */
    public VEditValue(IValue _value, int _w) {
        this(null, _value, null, false, _w);
    }

    /**
     *
     * @param _value
     * @param _setValueOnEnterOnly
     */
    public VEditValue(IValue _value, boolean _setValueOnEnterOnly) {
        this(null, _value, _setValueOnEnterOnly);
    }

    /**
     *
     * @param _value
     * @param _setValueOnEnterOnly
     * @param _w
     */
    public VEditValue(IValue _value, boolean _setValueOnEnterOnly, int _w) {
        this(null, _value, null, _setValueOnEnterOnly, _w);
    }

    /**
     *
     * @param _prefix
     * @param _value
     * @param _setValueOnEnterOnly
     */
    public VEditValue(Object _prefix, IValue _value, boolean _setValueOnEnterOnly) {
        this(_prefix, _value, null, _setValueOnEnterOnly);
    }
    private EditString number = null;//dg

    /**
     *
     */
    public void valueChanged() {//dg this enables another object (like a slider) to change the value and then get the EditString.text updated
        number.text = this.toString();
    }

    /**
     *
     * @param _prefix
     * @param _value
     * @param _suffix
     * @param _setValueOnEnterOnly
     */
    public VEditValue(Object _prefix, IValue _value, Object _suffix, boolean _setValueOnEnterOnly) {
        this(_prefix, _value, _suffix, _setValueOnEnterOnly, 40);
    }

    /**
     *
     * @param _prefix
     * @param _value
     * @param _suffix
     * @param _setValueOnEnterOnly
     * @param _w
     */
    public VEditValue(Object _prefix, IValue _value, Object _suffix, boolean _setValueOnEnterOnly, int _w) {
        this(UV.cEW, _prefix, _value, _w, _suffix, _setValueOnEnterOnly);
    }

    /**
     *
     * @param _place
     * @param _prefix
     * @param _value
     * @param _w
     * @param _suffix
     * @param _setValueOnEnterOnly
     */
    public VEditValue(Place _place, Object _prefix, IValue _value, int _w, Object _suffix, boolean _setValueOnEnterOnly) {
        value = _value;
        setValueOnEnterOnly = _setValueOnEnterOnly;
        observer = new AObserver() {

            @Override
            public void change(Change _change) {
                number.paint();
            }

            public void bound(IObservable _observable) {
            }

            public void released(IObservable _observable) {
            }
        };

        number = new EditString(this, _w, _w) {

            @Override
            public void stringChanged(String _string) {
                string(_string);
                if (!setValueOnEnterOnly) {
                    processString(_string);
                }
            }

            @Override
            public void stringSet(String _string) {
                processString(_string);
                _setText(VEditValue.this.toString());//??
                //setCaret(0);//??
            }
        };
        VChain c = new VChain(_place);
        if (_prefix != null) {
            if (_prefix instanceof IView) {
                c.add((IView) _prefix);
            } else {
                c.add(new VString(_prefix));
            }
        }
        c.add(new VEditString(number));
        if (_suffix != null) {
            if (_suffix instanceof IView) {
                c.add((IView) _suffix);
            } else {
                c.add(new VString(_suffix));
            }
        }
        setPlacer(new Placer(c));
        setBorder(null);
    }

    private void processString(String _string) {
        if (_string == null) {
            return;
        }
        _string = _string.trim();
        try {
            if (value.getValue() instanceof String) {
                value.setValue(_string);
            }
            if (_string.length() == 0) {
                return;
            }
            if (_string.length() == 1 && _string.equals("-")) {// invalid number
                return;
            }
            if (value.getValue() instanceof Integer) {
                value.setValue(new Integer(Integer.parseInt(_string)));
            }
            if (value.getValue() instanceof Long) {
                value.setValue(new Long((long) Long.parseLong(_string)));
            }
            if (value.getValue() instanceof Double) {
                if (!_string.equals(".")) {
                    value.setValue(new Double(Double.parseDouble(_string)));
                }
            }
            if (value.getValue() instanceof Float) {
                if (!_string.equals(".")) {
                    value.setValue(new Float(Double.parseDouble(_string)));
                }
            }
            valueSet();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    // Overloadable
    /**
     *
     * @param _string
     */
    public void string(String _string) {
    }

    /**
     *
     */
    public void valueSet() {
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        return value;
    }

    /**
     *
     * @param _value
     */
    public void setValue(IValue _value) {
        value = _value;
        valueChanged();
    }//dg

    @Override
    public String toString() {
        if (value == null) {
            return "";
        }
        Object _value = value.getValue();
        if (_value == null) {
            return "";
        }
        return _value.toString();
    }
}
