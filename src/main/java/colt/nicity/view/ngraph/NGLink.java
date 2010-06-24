/*
 * NGLink.java.java
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
package colt.nicity.view.ngraph;

import colt.nicity.core.collection.IHaveCount;
import colt.nicity.core.lang.OrderedKeys;

/**
 *
 * @author Administrator
 */
public class NGLink extends OrderedKeys implements IHaveCount {

    /**
     *
     */
    public long count = 0;

    /**
     *
     * @param _from
     * @param _to
     * @param _drawer
     */
    public NGLink(IVNode _from, IVNode _to, LinkDrawer _drawer) {
        super(_from, _to, _drawer);
    }

    /**
     *
     * @param _minLink
     * @param _maxLink
     * @param _maxLinkCount
     * @return
     */
    public boolean visible(double _minLink, double _maxLink, long _maxLinkCount) {
        double rank = (double) count / (double) _maxLinkCount;
        if (rank < _minLink || rank > _maxLink) {
            return false;
        }
        return true;
    }

    // IHaveCount
    @Override
    public long getCount() {
        return count;
    }
}
	
	
