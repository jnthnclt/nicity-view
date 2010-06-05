/*
 * VIcon.java.java
 *
 * Created on 01-03-2010 01:31:12 PM
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

import com.colt.nicity.view.image.ViewImage;
import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.io.UIO;
import com.colt.nicity.core.memory.SoftIndex;

/**
 *
 * @author Administrator
 */
public class VIcon {

    /**
     *
     */
    public static String cPathToIcons = "icons/";
    /**
     *
     */
    public static String cIconExtension = ".GIF";
    private static SoftIndex icons = new SoftIndex();
    /**
     *
     */
    public static CSet missing = new CSet();

    /**
     *
     * @param _name
     * @param _fallback
     * @return
     */
    public static VPaintable icon(String _name, String _fallback) {
        ViewImage i = image(_name, true);
        if (i != null) {
            return new VPaintable(i);
        }
        i = image(_fallback, false);
        return new VPaintable(i);
    }

    /**
     *
     * @param _name
     * @param _fallback
     * @return
     */
    public static ViewImage image(String _name, String _fallback) {
        ViewImage i = image(_name, true);
        if (i != null) {
            return i;
        }
        return image(_fallback, false);
    }

    /**
     *
     * @param _name
     * @param _size
     * @return
     */
    public static VPaintable icon(String _name, int _size) {
        if (_name == null) {
            return null;
        }
        ViewImage vi = image(_name);
        if (vi == null) {
            return null;
        }
        return new VPaintable(vi.getThumbnail(_size, _size));
    }

    /**
     *
     * @param _name
     * @return
     */
    public static VPaintable icon(String _name) {
        return new VPaintable(image(_name, false));
    }

    /**
     *
     * @param _name
     * @return
     */
    public static ViewImage image(String _name) {
        return image(_name, false, false);
    }

    /**
     *
     * @param _name
     * @param _null
     * @return
     */
    public static ViewImage image(String _name, boolean _null) {
        return image(_name, _null, false);
    }

    /**
     *
     * @param _name
     * @param _null
     * @param _find
     * @return
     */
    public static ViewImage image(String _name, boolean _null, boolean _find) {
        _name = _name.toUpperCase();
        ViewImage image = (ViewImage) icons.get(_name);
        if (image != null) {
            return image;
        }
        if (missing.get(_name) != null) {
            return null;
        }

        try {
            image = new ViewImage(
                    UIO.toByteArray(
                    ClassLoader.getSystemResourceAsStream(cPathToIcons + _name + cIconExtension),
                    4096));
            icons.set(image, _name);
            return image;
        } catch (Exception x) {
            image = null;
            missing.add(cPathToIcons + _name + cIconExtension);
            //System.out.println("System is Missing:"+cPathToIcons+_name+cIconExtension);
            return null;//??
        }
    }
}
