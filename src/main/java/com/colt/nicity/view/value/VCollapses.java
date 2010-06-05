/*
 * VCollapses.java.java
 *
 * Created on 01-03-2010 01:34:45 PM
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
package com.colt.nicity.view.value;

import com.colt.nicity.view.border.ViewBorder;
import com.colt.nicity.view.list.AItem;
import com.colt.nicity.view.list.ListController;
import com.colt.nicity.view.list.VList;
import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.comparator.AValueComparator;
import com.colt.nicity.core.comparator.UValueComparator;
import com.colt.nicity.core.lang.ICallback;
import com.colt.nicity.view.core.Place;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.Viewer;

/**
 *
 * @author Administrator
 */
public class VCollapses extends AItem {

    /**
     *
     */
    protected CSet closed = new CSet();
    /**
     *
     */
    protected CSet open = new CSet();
    int closedDirection = 1;
    int openDirection = 1;
    int closeSpans;
    int openSpans;

    /**
     *
     */
    public VCollapses() {
        this(1, -1);
    }

    /**
     *
     * @param _closedDirection
     * @param _openDirection
     */
    public VCollapses(int _closedDirection, int _openDirection) {
        this(_closedDirection, _openDirection, UV.cNENW, UV.cXNS, UV.cXNS);
    }

    /**
     *
     * @param _closedDirection
     * @param _openDirection
     * @param _place
     * @param _openSpans
     * @param _closeSpans
     */
    public VCollapses(int _closedDirection, int _openDirection, Place _place, int _openSpans, int _closeSpans) {
        VChain c = new VChain(_place);
        closedDirection = _closedDirection;
        openDirection = _openDirection;
        openSpans = _openSpans;
        closeSpans = _closeSpans;

        VList closeList = new VList(new ListController(closed, 0), _closedDirection);
        closeList.spans(_closeSpans);
        closeList.setComparator(UValueComparator.value(AValueComparator.cAscending));
        closeList.setBorder(null);
        Viewer vCloseList = new Viewer(closeList);
        vCloseList.setBorder(new ViewBorder());
        vCloseList.spans(_closeSpans);
        c.add(vCloseList);

        VList openList = new VList(new ListController(open, 0), _openDirection);
        openList.spans(_openSpans);
        openList.setComparator(UValueComparator.value(AValueComparator.cAscending));
        openList.setBorder(null);
        Viewer vOpenList = new Viewer(openList);
        vOpenList.setBorder(new ViewBorder());
        vOpenList.spans(_openSpans);

        c.add(vOpenList);
        c.spans(UV.cXNSEW);
        setContent(c);
        setBorder(new ViewBorder());
    }

    /**
     *
     * @param _collapse
     */
    public void add(VCollapse _collapse) {
        if (_collapse.open) {
            open.add(_collapse);
        } else {
            closed.add(_collapse);
        }
        if (_collapse.order == -1) {
            _collapse.order = (int) open.getCount() + (int) closed.getCount();
        }
        _collapse.picked = new ICallback() {

            public Object callback(Object _value) {
                VCollapse collapse = (VCollapse) _value;
                open.remove(collapse);
                closed.remove(collapse);
                if (collapse.open) {
                    open.add(collapse);
                } else {
                    closed.add(collapse);
                }
                return _value;
            }
        };
    }

    /**
     *
     * @param _collapse
     */
    public void remove(VCollapse _collapse) {
        open.remove(_collapse);
        closed.remove(_collapse);
        _collapse.picked = null;
    }

    /**
     *
     */
    public void clear() {
        open.removeAll();
        closed.removeAll();
    }

    /**
     *
     */
    public void onlyOneOpenAtATime() {
        CSet all = new CSet();
        all.add((Object[]) open.getAll(Object.class));
        all.add((Object[]) closed.getAll(Object.class));
        Object[] array = all.getAll(Object.class);
        for (Object a : array) {
            for (Object b : array) {
                if (a == b) {
                    continue;
                }
                ((VCollapse) a).closeWhenOpening.add(b);
            }
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Object getValue() {
        return this;
    }
}
