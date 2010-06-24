/*
 * VLED.java.java
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
package colt.nicity.view.monitor;

import colt.nicity.view.border.RoundButtonBorder;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.list.AItem;
import colt.nicity.core.time.RateMonitor;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class VLED extends AItem {

    Object key;
    /**
     *
     */
    public AColor color;
    RateMonitor monitor;
    /**
     *
     */
    public double[] history;

    /**
     *
     * @param _key
     * @param _color
     * @param _w
     * @param _h
     * @param _history
     * @param _rateVolume
     */
    public VLED(Object _key, final AColor _color, int _w, int _h, int _history, boolean _rateVolume) {
        key = _key;
        color = _color;
        monitor = new RateMonitor(" ", _rateVolume);
        monitor.setUnits("per/sec", 1);

        history = new double[_history];

        VChain rates = new VChain(UV.cSN);
        VButton b = new VButton(new RigidBox(_w, _h)) {

            public void paintBorder(ICanvas _g, int _x, int _y, int _w, int _h) {
                super.paintBorder(_g, _x, _y, _w, _h);
                double v = monitor.getZeroToOne();
                //if (v > 0) {
                int r = 2;
                if (_w > _h) {
                    //_g.setColor(_color);
                    //_g.rect(true,0,0,(int)(_w*v),_h);
                    RoundButtonBorder.paint(_g, _x + 0, _y + _h - ((int) (_h * v)), 8, (int) (_h * v), r, r, _color);

                    int l = history.length;
                    int rw = _w / l;
                    for (int i = 0; i < l; i++) {
                        float fp = (float) (i) / (float) l;

                        int rx = (int) (_w * fp);
                        int rh = (int) (_h * history[i]);
                        int ry = _h - rh;

                        _g.rect(true, _x + rx, _y + ry, rw + 1, rh);
                    }

                    _g.setFont(UV.fonts[UV.cSmall]);
                    _g.setColor(ViewColor.cTheme);
                    _g.drawString(key + " " + monitor.toString(), _x + 9, _y + _h - 3);
                    _g.setColor(ViewColor.cThemeFont);
                    _g.drawString(key + " " + monitor.toString(), _x + 8, _y + _h - 4);



                } else {
                    //_g.setColor(_color.getColor());
                    //_g.rect(true,0,_h-((int)(_h*v)),_w,(int)(_h*v));

                    RoundButtonBorder.paint(_g, _x + 0, _y + _h - 8, (int) (_w * v), _h, r, r, _color);



                    _g.setColor(ViewColor.cThemeFont);
                    _g.translate(_x + 0, _y + _h);
                    _g.rotate(Math.toRadians(-90));
                    _g.setFont(UV.fonts[UV.cSmall]);
                    _g.drawString(monitor.toString(), 0, UV.fonts[UV.cSmall].getSize());
                    _g.rotate(Math.toRadians(90));
                    _g.translate(_x + 0, _y + (-_h));
                }
                //}
            }
        };
        b.setBorder(new ViewBorder());
        rates.add(b);
        setContent(rates);
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        return key;
    }

    /**
     *
     * @param _count
     * @param _inc
     */
    public void pulse(long _count, boolean _inc) {
        if (_inc) {
            monitor.inc((int) _count);
        } else {
            monitor.count(_count);
        }
        monitor.calculate();
        System.arraycopy(history, 0, history, 1, history.length - 1);
        history[0] = monitor.getZeroToOne();
        repair();
        flush();
    }
}
