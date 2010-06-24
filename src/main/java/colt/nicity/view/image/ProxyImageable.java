/*
 * ProxyImageable.java.java
 *
 * Created on 03-12-2010 07:02:51 PM
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
package colt.nicity.view.image;

import java.io.File;

/**
 *
 * @author Administrator
 */
public class ProxyImageable extends Imageable {

    private Imageable imageable;
    private IImage image;

    /**
     *
     * @param _imageable
     * @param _image
     */
    public ProxyImageable(Imageable _imageable, IImage _image) {
        imageable = _imageable;
        image = _image;
    }

    /**
     *
     * @return
     */
    @Override
    public IImage getImage() {
        return image;
    }

    /**
     *
     * @return
     */
    @Override
    public File getFile() {
        return imageable.getFile();
    }
}
