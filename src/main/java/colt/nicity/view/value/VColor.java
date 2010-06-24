/*
 * VColor.java.java
 *
 * Created on 03-12-2010 06:41:09 PM
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

import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.event.WindowActivated;
import colt.nicity.view.event.WindowClosed;
import colt.nicity.view.event.WindowDeactivated;
import colt.nicity.view.event.WindowDeiconified;
import colt.nicity.view.event.WindowIconified;
import colt.nicity.view.event.WindowOpened;
import colt.nicity.view.list.AItem;
import colt.nicity.core.value.IValue;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AWindow;
import colt.nicity.view.core.HSBBox;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VFrame;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IWindowEvents;

/**
 *
 * @author Administrator
 */
public class VColor extends AItem implements Comparable, IValue, IWindowEvents {

    Value hue;
    Value sat;
    Value bri;
    Value alp;
    Value<AColor> value;
    Object title;
    HSBBox hsb;
    float jw;
    float jh;
    private AColor old;
    private AColor color;

    /**
     *
     * @param _color
     * @param _title
     * @param _w
     * @param _h
     * @param _init
     * @param _dialog
     */
    public VColor(final AColor _color, Object _title, float _w, float _h, boolean _init, boolean _dialog) {
        this(new Value<AColor>(_color) {

            @Override
            public void setValue(AColor _value) {
                _color.set8BitColor(_value.get8BitRGBA());
            }
        }, _title, _w, _h, _init, _dialog);
    }

    /**
     *
     * @param _value
     * @param _title
     * @param _w
     * @param _h
     * @param _init
     * @param _dialog
     */
    public VColor(Value<AColor> _value, Object _title, float _w, float _h, boolean _init, boolean _dialog) {
        value = _value;
        color = old = value.getValue();

        hue = new Value(new Double(value.getValue().getHue())) {

            @Override
            public void setValue(Object _value) {
                if (_value instanceof AColor) {
                    super.setValue(new Double(((AColor) _value).getHue()));
                } else {
                    super.setValue(_value);
                    if (hsb != null) {
                        hsb.setHSB(hue, sat, bri, alp);
                    }
                }
            }
        };
        sat = new Value(new Double(value.getValue().getSaturation())) {

            @Override
            public void setValue(Object _value) {
                if (_value instanceof AColor) {
                    super.setValue(new Double(((AColor) _value).getSaturation()));
                } else {
                    super.setValue(_value);
                    if (hsb != null) {
                        hsb.setHSB(hue, sat, bri, alp);
                    }
                }
            }
        };
        bri = new Value(new Double(value.getValue().getBrightness())) {

            @Override
            public void setValue(Object _value) {
                if (_value instanceof AColor) {
                    super.setValue(new Double(((AColor) _value).getBrightness()));
                } else {
                    super.setValue(_value);
                    if (hsb != null) {
                        hsb.setHSB(hue, sat, bri, alp);
                    }
                }
            }
        };
        alp = new Value(new Double(value.getValue().getA())) {

            @Override
            public void setValue(Object _value) {
                if (_value instanceof AColor) {
                    super.setValue(new Double(((AColor) _value).getAlpha()));
                } else {
                    super.setValue(_value);
                    if (hsb != null) {
                        hsb.setHSB(hue, sat, bri, alp);
                    }
                }
            }
        };

        title = _title;
        jw = _w;
        jh = _h;
        if (_init) {
            init(_dialog);
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public Object getValue() {
        return color;
    }

    /**
     *
     * @param _value
     */
    public void setValue(Object _value) {
        color = (AColor) _value;

        hue.setValue(_value);
        sat.setValue(_value);
        bri.setValue(_value);
        alp.setValue(_value);

        shue.repair();
        ssat.repair();
        sbri.repair();
        salp.repair();
        shue.flush();
        ssat.flush();
        sbri.flush();
        salp.flush();

        repair();
        flush();
    }

    @Override
    public String toString() {
        return title.toString();
    }
    VSlider shue;
    VSlider ssat;
    VSlider sbri;
    VSlider salp;
    boolean dialog;

    /**
     *
     * @param _dialog
     */
    public void init(boolean _dialog) {
        dialog = _dialog;
        VChain chain = new VChain(UV.cEW);
        hsb = new HSBBox(this, (int) jw, (int) jh);
        chain.add(hsb);
        chain.add(new RigidBox(8, 8));
        shue = new VSlider(hue, "H", 16, jh, false, true);
        shue.setBorder(null);
        chain.add(shue);
        ssat = new VSlider(sat, "S", 16, jh, false, true);
        ssat.setBorder(null);
        chain.add(ssat);
        sbri = new VSlider(bri, "B", 16, jh, false, true);
        sbri.setBorder(null);
        chain.add(sbri);
        salp = new VSlider(alp, "A", 16, jh, false, true);
        salp.setBorder(null);
        chain.add(salp);
        chain.add(new RigidBox(10, 10));

        VChain colors = new VChain(UV.cSN);

        RigidBox oldColor = new RigidBox(64, jh / 2) {

            @Override
            public void paintBackground(ICanvas _g, int _x, int _y, int _w, int _h) {
                super.paintBackground(_g, _x, _y, _w, _h);
                _g.setColor(old);
                _g.rect(true, _x, _y, _w, _h);
            }
        };
        colors.add(oldColor);

        RigidBox newColor = new RigidBox(64, jh / 2) {

            @Override
            public void paintBackground(ICanvas _g, int _x, int _y, int _w, int _h) {
                super.paintBackground(_g, _x, _y, _w, _h);
                _g.setColor(color);
                _g.rect(true, _x, _y, _w, _h);
            }
        };
        colors.add(newColor);

        chain.add(colors);


        VButton accept = new VButton(" Accept " + title + " ") {

            @Override
            public void picked(IEvent _e) {
                _accept();
                if (window != null) {
                    window.dispose();
                }
            }
        };

        VButton decline = new VButton(" Cancel ") {

            @Override
            public void picked(IEvent _e) {
                _decline();
                if (window != null) {
                    window.dispose();
                }
            }
        };

        VChain menu = new VChain(UV.cEW);
        menu.add(new RigidBox(10, 10));
        menu.add(accept);
        menu.add(new RigidBox(10, 10));
        if (_dialog) {
            menu.add(decline);
            menu.add(new RigidBox(10, 10));
        }
        menu.spans(UV.cXEW);

        VChain main = new VChain(UV.cSENE);
        main.add(chain);
        main.add(menu);

        setPlacer(new Placer(new Viewer(main)));
        setBorder(new ViewBorder());
    }

    // Comparable
    public int compareTo(Object o) {
        if (o instanceof VColor) {
            return 0;//!!return ((Double) value.getValue()).compareTo((Double) ((VColor) o).value.getValue());
        }
        return -1;
    }

    @Override
    public int hashCode() {
        return value.getValue().hashCode() + title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof VColor) {
            VColor pv = (VColor) obj;
            return title == pv.title && value.getValue().equals(pv.getValue());
        }
        return false;
    }

    /**
     *
     */
    public void reset() {
        choosed = false;
    }
    boolean choosed = false;

    private void _accept() {
        if (choosed) {
            return;
        }
        if (dialog) {
            choosed = true;
        }
        value.setValue(color);
        accept();
        if (window != null) {
            window.dispose();
        }
    }

    /**
     *
     */
    public void accept() {
    }

    private void _decline() {
        if (choosed) {
            return;
        }
        if (dialog) {
            choosed = true;
        }
        //value.setValue(old);
        decline();
        if (window != null) {
            window.dispose();
        }
    }

    /**
     *
     */
    public void decline() {
    }
    AWindow window;

    /**
     *
     * @param _centerRelativeTo
     */
    public void toFront(IView _centerRelativeTo) {
        toFront(_centerRelativeTo, true);
    }

    /**
     *
     * @param _centerRelativeTo
     * @param _prompt
     */
    public void toFront(IView _centerRelativeTo, boolean _prompt) {
        if (!_prompt) {
            _accept();
            return;
        }
        if (window == null) {
            VFrame frameViewer = new VFrame(
                    null,
                    this,
                    this,
                    false,
                    false);
            window = new AWindow(frameViewer);
            window.setTitle(this.toString());
            if (_centerRelativeTo == null) {
                UV.centerWindow(window);
            } else {
                UV.centerWindowRelativeToView(window, _centerRelativeTo);
            }
            window.show();
        } else {
            window.toFront();
        }
    }

    // IWindowEvents
    /**
     *
     * @param _e
     */
    public void windowOpened(WindowOpened _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowClosed(WindowClosed _e) {
        window = null;
        _decline();
    }

    /**
     *
     * @param _e
     */
    public void windowActivated(WindowActivated _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowDeactivated(WindowDeactivated _e) {
        _decline();
    }

    /**
     *
     * @param _e
     */
    public void windowIconified(WindowIconified _e) {
        _decline();
    }

    /**
     *
     * @param _e
     */
    public void windowDeiconified(WindowDeiconified _e) {
    }
}
