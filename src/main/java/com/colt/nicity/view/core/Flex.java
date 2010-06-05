/*
 * Flex.java.java
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
package com.colt.nicity.view.core;

import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class Flex implements Cloneable {

    /**
     *
     */
    protected boolean interior = true;
    private IView creator = NullView.cNull;
    /**
     *
     */
    public float x;
    /**
     *
     */
    public float y;

    /**
     *
     * @param _x
     * @param _y
     */
    public Flex(float _x, float _y) {
        this(NullView.cNull, _x, _y);
    }

    /**
     *
     * @param _creator
     * @param _x
     * @param _y
     */
    public Flex(IView _creator, float _x, float _y) {
        creator = _creator;
        x = _x;
        y = _y;
    }

    /**
     *
     * @return
     */
    public IView getCreator() {
        return creator;
    }

    /**
     *
     * @param _creator
     */
    public void setCreator(IView _creator) {
        creator = _creator;
    }

    /**
     *
     * @param _w
     * @return
     */
    public float flexW(float _w) {
        if (_w + x < 0) {
            _w = 0;
            x = -(_w);
        } else {
            _w += x;
        }
        return _w;
    }

    /**
     *
     * @param _h
     * @return
     */
    public float flexH(float _h) {
        if (_h + y < 0) {
            _h = 0;
            y = -(_h);
        } else {
            _h += y;
        }
        return _h;
    }

    public String toString() {
        return creator + " [" + x + "," + y + "]";
    }

    // Cloneable
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    // Helper
    /**
     *
     * @param _content
     * @param _w
     * @param _h
     */
    static public void flex(IView _content, float _w, float _h) {

        float cw = _content.getW();
        float ch = _content.getH();

        if ((int) (cw) != (int) (_w) || (int) (ch) != (int) (_h)) {
            int flexDeltaX = 0;
            int flexDeltaY = 0;
            int testFlexX = 0;
            int testFlexY = 0;
            if ((int) cw == 0 || (int) cw == 1 || (int) (cw) != (int) (_w)) {
                flexDeltaX = ((int) _w) - ((int) cw);
                testFlexX = (flexDeltaX > 0) ? 1 : -1;
            }
            if ((int) ch == 0 || (int) ch == 1 || (int) (ch) != (int) (_h)) {
                flexDeltaY = ((int) _h) - ((int) ch);
                testFlexY = (flexDeltaY > 0) ? 1 : -1;
            }

            // test flex
            _content.setParentView(NullView.cNull);
            _content.disableFlag(UV.cInterior);
            _content.layoutInterior(_content, new Flex(_content, testFlexX, testFlexY));

            if (testFlexX > 0 && (int) _content.getW() < cw) {
                flexDeltaX = -(flexDeltaX + testFlexX);
            } else if (testFlexX < 0 && (int) _content.getW() > cw) {
                flexDeltaX = -(flexDeltaX - testFlexX);
            }

            if (testFlexY > 0 && (int) _content.getH() < ch) {
                flexDeltaY = -(flexDeltaY + testFlexY);
            } else if (testFlexY < 0 && (int) _content.getH() > ch) {
                flexDeltaY = -(flexDeltaY - testFlexY);
            }

            _content.setParentView(NullView.cNull);
            _content.disableFlag(UV.cInterior);
            _content.layoutInterior(_content, new Flex(_content, flexDeltaX, flexDeltaY));
            flexDeltaX = ((int) _w) - ((int) _content.getW());
            flexDeltaY = ((int) _h) - ((int) _content.getH());
            if (flexDeltaX != 0 || flexDeltaY != 0) {
                _content.setParentView(NullView.cNull);
                _content.disableFlag(UV.cInterior);
                _content.layoutInterior(_content, new Flex(_content, flexDeltaX, flexDeltaY));
            }

        }
    }
}
