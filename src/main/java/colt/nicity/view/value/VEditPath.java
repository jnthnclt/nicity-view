/*
 * VEditPath.java.java
 *
 * Created on 03-12-2010 06:40:32 PM
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
package colt.nicity.view.value;

import colt.nicity.core.value.IValue;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IEvent;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class VEditPath extends Viewer {

    /**
     *
     */
    public static File lastDir = new File(System.getProperty("user.dir"));
    private IValue value;

    /**
     *
     * @param _name
     * @param _value
     * @param _w
     * @param _fileOk
     * @param _folderOk
     */
    public VEditPath(final String _name, IValue _value, int _w, final boolean _fileOk, final boolean _folderOk) {
        value = _value;
        VButton browse = new VButton(_name) {

            @Override
            public void picked(IEvent _e) {
                VFiles selectFile = new VFiles(lastDir, _name, false);
                selectFile.init(false, true, 500, 600);
                selectFile.toFront(_name);
                File got = selectFile.waitForSet();
                if (got != null) {
                    if (_fileOk && !got.isDirectory()) {
                        value.setValue(got.getAbsolutePath());
                    }
                    if (_folderOk && got.isDirectory()) {
                        value.setValue(got.getAbsolutePath());
                    }
                }
            }
        };
        setContent(new VChain(UV.cEW, new VEditValue(value, false, _w), browse));
    }
}
