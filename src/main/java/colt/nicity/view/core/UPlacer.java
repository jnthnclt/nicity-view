/*
 * UPlacer.java.java
 *
 * Created on 01-03-2010 01:31:37 PM
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

import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.view.interfaces.IPlacer;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class UPlacer {

    static void placeInside(IView _view, Place _place, IView _parent, WH_F _size, Flex _flex) {
        if (_parent == _view || _parent == NullView.cNull) {
            return;
        }

        _view.setParentView(_parent);
        _view.layoutInterior(_flex);

        flexInterior(_view, _parent, _place, _flex);

        float x = _parent.getBorder().getX() + _place.ox;
        float y = _parent.getBorder().getY() + _place.oy;

        resize(_view, x, y, _size);

        _view.layoutExterior(_size, _flex);
    }

    static void placeInside(IView _view, Place _place, IView _parent, IView _anchor, WH_F _size, Flex _flex) {
        if (_view == _parent || _view == _anchor || _parent == NullView.cNull || _anchor == NullView.cNull) {
            return;
        }

        _view.setParentView(_parent);
        _view.layoutInterior(_flex);

        flexExterior(_view, _parent, _place, _flex);

        float x = _place.ox + (_anchor.getX() + ((_anchor.getW() * _place.px) - (_view.getW() * _place.cx)));
        float y = _place.oy + (_anchor.getY() + ((_anchor.getH() * _place.py) - (_view.getH() * _place.cy)));

        resize(_view, x, y, _size);

        _view.layoutExterior(_size, _flex);

    }

    private static void flexInterior(IView _view, IView _parent, Place _place, Flex _flex) {
        if (_flex == null) {
            return;
        }
        if (_view.hasFlag(UV.cFlexing)) {
            return;
        }
        if (_flex.x == 0.0f && _flex.y == 0.0f) {
            return;
        }

        float dx = _flex.x * _place.ifx;
        float dy = _flex.y * _place.ify;

        _view.layoutInterior(_view, new Flex(_flex.getCreator(), dx, dy));
    }

    private static void flexExterior(IView _view, IView _parent, Place _place, Flex _flex) {
        if (_flex == null) {
            return;
        }
        if (_view.hasFlag(UV.cFlexing)) {
            return;
        }
        if (_flex.x == 0.0f && _flex.y == 0.0f) {
            return;
        }

        float dx = _flex.x * _place.efx;
        float dy = _flex.y * _place.efy;

        _view.layoutInterior(_view, new Flex(_flex.getCreator(), dx, dy));
    }

    private static void resize(IView _view, float _x, float _y, WH_F _size) {
        // handle the positive space x > 0 and y > 0
        if (_x >= 0 && _y >= 0) {
            _view.setLocation(_x, _y);
            _size.max(_x + _view.getW(), _y + _view.getH());
            return;
        }
        IView parent = _view.getParentView();
        float bx = parent.getBorder().getX();
        float by = parent.getBorder().getY();
        // handle the negative space x < 0 or y < 0
        if (_x < 0 && _y < 0) {
            _view.setLocation(bx, by);
            _size.translate(-(_x), -(_y));
            _size.max(bx + _view.getW(), by + _view.getH());
            translate(parent.getPlacer().getViewable().getView(), _x, _y, _view);
        } else if (_x < 0) {
            _view.setLocation(bx, _y);
            _size.translate(-(_x), 0);
            _size.max(bx + _view.getW(), _y + _view.getH());
            translate(parent.getPlacer().getViewable().getView(), _x, 0, _view);
        } else { // y is < 0
            _view.setLocation(_x, by);
            _size.translate(0, -(_y));
            _size.max(_x + _view.getW(), by + _view.getH());
            translate(parent.getPlacer().getViewable().getView(), 0, _y, _view);
        }
    }

    private static void translate(IView view, float x, float y, IView exit) {
        view.setLocation(view.getX() - x, view.getY() - y);
        Object[] placers = view.getPlacers().toArray();
        for (int i = 0; i < placers.length; i++) {
            view = ((IPlacer) placers[i]).getViewable().getView();
            if (view == exit) {
                return;
            }
            translate(view, x, y, exit);
        }
    }
}
