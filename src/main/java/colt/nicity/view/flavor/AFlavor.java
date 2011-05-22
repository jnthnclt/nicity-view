/*
 * AFlavor.java.java
 *
 * Created on 01-30-2010 10:22:51 AM
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
package colt.nicity.view.flavor;

import colt.nicity.core.collection.CSet;
import colt.nicity.core.lang.UString;
import colt.nicity.view.core.AColor;
import colt.nicity.view.interfaces.ICanvas;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 * @param <C>
 */
public abstract class AFlavor<C> extends AFlavorCondition<C> {

    static private final CSet<AFlavor> flavors = new CSet<AFlavor>();
    static private boolean onlyOnceish = true;

    static public AFlavor getFlavor(long hashId) {
        return flavors.get(hashId);
    }

    public static void main(String[] args) {
        getFlavor(0);
    }

    public AFlavor() {
        flavors.add(this);
    }

    @Override
    public Long hashObject() {
        return UString.stringToLong(flavorName());
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _color
     */
    abstract public void paintFlavor(ICanvas _g, int _x, int _y, int _w, int _h, AColor _color);

    /**
     * 
     * @return 
     */
    public String flavorName() {
        return getClass().getCanonicalName();
    }
}
