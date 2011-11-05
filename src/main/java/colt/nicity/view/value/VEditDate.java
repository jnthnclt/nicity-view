/*
 * VEditDate.java.java
 *
 * Created on 03-12-2010 06:41:44 PM
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

import colt.nicity.core.time.UTime;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VPopupButton;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.Viewer;
import java.util.TimeZone;

/**
 *
 * @author Administrator
 */
public class VEditDate extends Viewer {

    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        ViewColor.onGray();
        UV.exitFrame(new Viewer(new VEditDate(new Value<Long>(System.currentTimeMillis()))), "");
    }

    private Value<Long> value;
    private TimeZone tz;

    /**
     *
     * @param _value
     */
    public VEditDate(Value<Long> _value) {
        this(_value, TimeZone.getDefault());
    }

    /**
     *
     * @param _value
     * @param _tz
     */
    public VEditDate(Value<Long> _value, TimeZone _tz) {
        value = _value;
        tz = _tz;
        setContent(new VPopupButton(new VString(this), new VDate(value)));
    }

    @Override
    public String toString() {
        return UTime.basicTime(value.longValue(), tz);
    }
}
