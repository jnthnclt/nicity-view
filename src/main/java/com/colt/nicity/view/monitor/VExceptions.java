/*
 * VExceptions.java.java
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
package com.colt.nicity.view.monitor;

import com.colt.nicity.view.border.MenuBorder;
import com.colt.nicity.view.list.AItem;
import com.colt.nicity.view.list.VList;
import com.colt.nicity.core.collection.CArray;
import com.colt.nicity.view.core.AWindow;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.VPan;
import com.colt.nicity.view.core.VString;

/**
 *
 * @author Administrator
 */
public class VExceptions extends AItem {

    private static VExceptions cExceptions;

    private static synchronized VExceptions e() {
        if (cExceptions == null) {
            cExceptions = new VExceptions();
        }
        return cExceptions;
    }

    /**
     *
     * @param _t
     */
    public static void e(Throwable _t) {
        e().add(_t);
    }

    /**
     *
     * @param _ve
     */
    public static void e(VException _ve) {
        e().add(_ve);
    }
    private CArray exceptions = new CArray();

    /**
     *
     */
    public VExceptions() {

        VChain c = new VChain(UV.cSWNW);
        VChain logo = new VChain(UV.cEW);
        logo.add(new VString(this));
        logo.setBorder(new MenuBorder());
        logo.spans(UV.cXEW);
        c.add(logo);

        VList list = new VList(exceptions, 1);
        VPan pan = new VPan(list, 800, 600);
        c.add(pan, UV.cFII);
        setContent(c);
    }

    @Override
    public String toString() {
        return "" + exceptions.getCount();
    }

    /**
     *
     * @param _t
     */
    public void add(Throwable _t) {
        add(new VException(_t));
    }

    /**
     *
     * @param _ve
     */
    public void add(VException _ve) {
        exceptions.insertLast(_ve);
        toFront();
    }
    AWindow window;

    /**
     *
     */
    public void toFront() {
        AWindow _window = window;
        if (_window == null || _window.closed()) {
            window = UV.frame(this, "Exceptions", true, false);
        } else {
            _window.toFront();
        }
    }

    /**
     *
     */
    public void close() {
        if (window != null) {
            window.dispose();
            window = null;
        }
    }
}

