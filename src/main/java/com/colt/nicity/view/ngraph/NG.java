/*
 * NG.java.java
 *
 * Created on 01-03-2010 01:33:11 PM
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
package com.colt.nicity.view.ngraph;

import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.lang.OrderedKeys;
import com.colt.nicity.core.lang.UMath;

/**
 *
 * @author Administrator
 */
public class NG {

    /**
     *
     */
    public CSet nodes = new CSet();
    /**
     *
     */
    public CSet links = new CSet();
    /**
     *
     */
    public long maxNodeCount = 0;
    /**
     *
     */
    public long maxLinkCount = 0;

    /**
     *
     */
    public NG() {
    }

    /**
     *
     */
    public void reset() {
        nodes.removeAll();
        links.removeAll();
        maxLinkCount = 0;
        maxNodeCount = 0;
    }

    /**
     *
     * @param _fromValueKey
     * @param _toValueKey
     */
    public void order(Object _fromValueKey, Object _toValueKey) {
        order(_fromValueKey, _fromValueKey, 1, _toValueKey, _toValueKey, 1, 1);
    }

    /**
     *
     * @param _fromValueKey
     * @param _toValueKey
     * @param _drawer
     */
    public void order(Object _fromValueKey, Object _toValueKey, LinkDrawer _drawer) {
        order(_fromValueKey, _fromValueKey, 1, _toValueKey, _toValueKey, 1, 1, _drawer);
    }

    /**
     *
     * @param _fromValue
     * @param _fromKey
     * @param _fromCount
     * @param _toValue
     * @param _toKey
     * @param _toCount
     * @param _linkCount
     */
    public void order(
            Object _fromValue, Object _fromKey, int _fromCount,
            Object _toValue, Object _toKey, int _toCount,
            int _linkCount) {
        order(_fromValue, _fromKey, _fromCount, _toValue, _toKey, _toCount, _linkCount, ULinkDrawer.cLine);
    }

    /**
     *
     * @param _fromValue
     * @param _fromKey
     * @param _fromCount
     * @param _toValue
     * @param _toKey
     * @param _toCount
     * @param _linkCount
     * @param _drawer
     */
    public void order(
            Object _fromValue, Object _fromKey, int _fromCount,
            Object _toValue, Object _toKey, int _toCount,
            int _linkCount, LinkDrawer _drawer) {
        NGNode f = set(_fromValue, _fromKey, _fromCount);
        NGNode t = set(_toValue, _toKey, _fromCount);
        if (f != null && t != null) {
            link(f, t, _linkCount, _drawer);
        }
    }

    private NGLink link(NGNode _from, NGNode _to, int _count, LinkDrawer _drawer) {
        NGLink l = (NGLink) links.get(new OrderedKeys(_from, _to, _drawer));
        if (l == null) {
            l = new NGLink(_from, _to, _drawer);
            links.add(l);
        }
        l.count += _count;
        if (l.count > maxLinkCount) {
            maxLinkCount = l.count;
        }
        _from.linkTo.add(l);
        _to.linkFrom.add(l);
        return l;
    }

    /**
     *
     * @param _value
     * @param _key
     * @param _count
     * @return
     */
    public NGNode set(Object _value, Object _key, int _count) {
        if (_key == null) {
            return null;
        }
        NGNode c = (NGNode) nodes.get(_key);
        if (c == null) {
            c = (NGNode) construct(_value, _key);
            nodes.add(c);
        }
        c.count += _count;
        if (c.count > maxNodeCount) {
            maxNodeCount = c.count;
        }
        return c;
    }

    /**
     *
     * @param _value
     * @param _key
     * @return
     */
    public NGNode get(Object _value, Object _key) {
        if (_key == null) {
            return null;
        }
        NGNode c = (NGNode) nodes.get(_key);
        return c;
    }

    /**
     *
     * @param _value
     * @param _key
     * @return
     */
    public NGNode construct(Object _value, Object _key) {
        return new NGNode(_value, _key);
    }

    /**
     *
     * @param _numSTDs
     * @param _minSTD
     * @param _maxSTD
     */
    public void symetricalBellCurve(double _numSTDs, double _minSTD, double _maxSTD) {
        Object[] _links = links.getAll(Object.class);
        double mean = 0;
        double[] counts = new double[_links.length];
        for (int l = 0; l < _links.length; l++) {
            NGLink link = (NGLink) _links[l];
            mean += link.count;
            counts[l] = link.count;
        }
        mean /= _links.length;

        double std = UMath.std(counts, mean);
        double delta = std * _numSTDs;
        CSet keptLinks = new CSet();
        CSet keptNodes = new CSet();
        for (int l = 0; l < _links.length; l++) {
            double rank = Math.abs((counts[l] - mean) / delta);
            if (rank >= _minSTD && rank <= _maxSTD) {
                keptLinks.add(_links[l]);
                NGLink link = (NGLink) _links[l];
                keptNodes.add(link.key(0));
                keptNodes.add(link.key(1));
            }
        }
        links = keptLinks;
        nodes = keptNodes;
    }
} 


