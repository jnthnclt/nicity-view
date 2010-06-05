/*
 * UPopup.java.java
 *
 * Created on 01-03-2010 01:31:34 PM
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
package com.colt.nicity.view.core;

import com.colt.nicity.view.awt.UAWT;
import com.colt.nicity.view.border.ViewBorder;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class UPopup {

    /**
     *
     * @param _p
     * @param _w
     * @param _h
     * @return
     */
    static public XY_I adjustOrigin(XY_I _p, float _w, float _h) {


        float _sw = UAWT.getScreenWidth() - 20;
        float _sh = UAWT.getScreenHeight() - 20;

        if (_p.x + _w < _sw
                && _p.y + _h < _sh) {
            return _p;
        }

        XY_I p = new XY_I(_p.x, _p.y);
        if (p.x > (_sw - _w)) {
            p.x -= _w * (p.x / _sw);
            if (p.x < 20) {
                p.x = 20;
            }
        }
        if (p.y > (_sh - _h)) {
            p.y -= _h * (p.y / _sh);
            if (p.y < 20) {
                p.y = 20;
            }
        }
        return p;

    }

    /**
     *
     * @param _view
     * @param _p
     * @param _w
     * @param _h
     * @return
     */
    static public IView scrollView(IView _view, XY_I _p, float _w, float _h) {

        _p = adjustOrigin(_p, _w, _h);


        float _sw = UAWT.getScreenWidth() - 20;
        float _sh = UAWT.getScreenHeight() - 20;


        if ((_w + _p.x) > _sw) {
            _w = _sw - _p.x;
        }

        if ((_h + _p.y) > _sh) {
            _h = _sh - _p.y;
        }

        Viewer scroll;
        if (_w == -1 && _h == -1) {
            scroll = new Viewer(_view);
        } else {
            scroll = new VPan(_view, _w, _h);
        }
        scroll.setBorder(new ViewBorder());
        return scroll;
    }

    /**
     *
     * @param _view
     * @param _p
     * @return
     */
    static public IView scrollView(IView _view, XY_I _p) {

        _p = adjustOrigin(_p, _view.getW(), _view.getH());

        float _w = _view.getW();
        float _h = _view.getH();

        float _sw = UAWT.getScreenWidth() - 20;
        float _sh = UAWT.getScreenHeight() - 20;




        if ((_w + _p.x) > _sw) {
            _w = _sw - _p.x;
        } else {
            _w = -1;
        }

        if ((_h + _p.y) > _sh) {
            _h = _sh - _p.y;
        } else {
            _h = -1;
        }

        Viewer scroll;
        if (_w == -1 && _h == -1) {
            scroll = new Viewer(_view);
        } else {
            scroll = new VPan(_view, _w, _h);
        }
        scroll.setBorder(new ViewBorder());
        return scroll;
    }
}
