/*
 * AViewWHBorder.java.java
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

import colt.nicity.view.border.NullBorder;
import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
abstract public class AViewWHBorder extends AView {

    /**
     *
     */
    protected float w = 0.0f;
    /**
     *
     */
    protected float h = 0.0f;
    /**
     *
     */
    protected IBorder border = NullBorder.cNull;

    /**
     *
     */
    public AViewWHBorder() {
        super();
    }

    // IView
    @Override
    public IBorder getBorder() {
        return border;
    }

    @Override
    public void setBorder(IBorder _border) {
        if (_border == null) {
            border = NullBorder.cNull;
        } else {
            border = _border;
        }
    }

    @Override
    public boolean isActive() {
        return border.isActive();
    }

    @Override
    public boolean isSelected() {
        return border.isSelected();
    }

    // Border
    @Override
    public void paintBorder(ICanvas g, int _x, int _y, int _w, int _h) {
        IBorder border = getBorder();
        if (border != null) {
            border.paintBorder(g, _x, _y, _w, _h);
        }
    }

    @Override
    public void paintBackground(ICanvas g, int _x, int _y, int _w, int _h) {
        IBorder border = getBorder();
        if (border != null) {
            border.paintBackground(g, _x, _y, _w, _h);
        }
    }

    // Location
    @Override
    public float getW() {
        if (border == this) {
            return w;//??
        }
        return w + border.getW();
    }

    @Override
    public float getH() {
        if (border == this) {
            return h;//??
        }
        return h + border.getH();
    }
}
