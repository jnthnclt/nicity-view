/*
 * AskForString.java.java
 *
 * Created on 03-12-2010 06:40:59 PM
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

import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.list.AItem;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.value.LockValue;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.EditString;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VEditString;
import colt.nicity.view.core.VString;

/**
 *
 * @author Administrator
 */
public class AskForString extends AItem {

    Value string;

    /**
     *
     * @param _label
     * @param _initial
     */
    public AskForString(Object _label, Value _initial) {
        string = _initial;
        VChain c = new VChain(UV.cEW);
        c.add(new VString(_label));

        EditString value = new EditString(string, 200, 200) {

            @Override
            public void stringSet(String _string) {
                if (_string == null) {
                    return;
                }
                _string = _string.trim();
                string.setValue(_string);
            }
        };
        VEditString valueViewer = new VEditString(value);
        c.add(valueViewer);
        setContent(c);
        setBorder(new ViewBorder());
    }

    /**
     *
     * @param _message
     * @param _
     * @return
     */
    public Value askFor(String[] _message, IOut _) {
        final LockValue modal = new LockValue();
        new AcceptDecline(_message, this) {

            public void accept() {
                modal.setValue(new Integer(1));
            }

            public void decline() {
                modal.setValue(new Integer(0));
            }
        }.toFront(null);
        int mode = ((Integer) modal.getValue()).intValue();
        if (mode == 1) {
            return string;
        }
        return null;
    }
}
