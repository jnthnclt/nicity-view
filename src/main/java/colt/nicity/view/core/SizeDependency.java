/*
 * SizeDependency.java.java
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

import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class SizeDependency extends XY_I {

    XY_I parent;
    Object mx;
    Object my;

    /**
     *
     * @param _parent
     * @param _x
     * @param _y
     */
    public SizeDependency(XY_I _parent, Object _x, Object _y) {
        parent = _parent;
        mx = _x;
        my = _y;
    }

    /**
     *
     * @return
     */
    @Override
    public int getX() {
        if (mx != null) {
            if (mx instanceof IView) {
                return parent.getX() - (int) ((IView) mx).getW();
            } else if (mx instanceof XY_I) {
                return parent.getX() - ((XY_I) mx).getX();
            } else if (mx instanceof Integer) {
                return parent.getX() - ((Integer) mx);
            } else if (mx instanceof Float) {
                return (int) (parent.getX() - ((Float) mx));
            } else {
                return parent.getX();
            }
        } else {
            return parent.getX();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int getY() {
        if (my != null) {
            if (my instanceof IView) {
                return parent.getY() - (int) ((IView) my).getH();
            } else if (my instanceof XY_I) {
                return parent.getX() - ((XY_I) my).getX();
            } else if (mx instanceof Integer) {
                return parent.getY() - ((Integer) my);
            } else if (my instanceof Float) {
                return (int) (parent.getY() - ((Float) my));
            } else {
                return parent.getY();
            }
        } else {
            return parent.getY();
        }
    }
}
