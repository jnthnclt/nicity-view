/*
 * VPickScreenResolution.java.java
 *
 * Created on 03-12-2010 06:39:08 PM
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

import colt.nicity.view.border.PopupBorder;
import colt.nicity.view.list.AItem;
import colt.nicity.view.list.VItem;
import colt.nicity.core.memory.struct.SWH_I;
import colt.nicity.core.value.IValue;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VString;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VPickScreenResolution extends AItem implements IValue {

    /**
     *
     */
    public static SWH_I[] res5x4 = new SWH_I[]{
        new SWH_I("SXGA", 1280, 1024),
        new SWH_I("QSXGA", 2560, 2048)
    };
    /**
     *
     */
    public static SWH_I[] res4x3 = new SWH_I[]{
        new SWH_I("QVGA", 320, 240),
        new SWH_I("VGA", 640, 480),
        new SWH_I("PAL", 768, 576),
        new SWH_I("SVGA", 800, 600),
        new SWH_I("XGA", 1024, 768),
        new SWH_I("---", 1280, 960),
        new SWH_I("SXGA+", 1400, 1050),
        new SWH_I("UXGA", 1600, 1200),
        new SWH_I("QXGA", 2048, 1536)
    };
    /**
     *
     */
    public static SWH_I[] res3x2 = new SWH_I[]{
        new SWH_I("NTSC", 720, 480),
        new SWH_I("----", 1152, 768),
        new SWH_I("----", 1280, 854),
        new SWH_I("----", 1440, 960)
    };
    /**
     *
     */
    public static SWH_I[] res16x10 = new SWH_I[]{
        new SWH_I("CGA", 320, 200),
        new SWH_I("WSXGA+", 1680, 1050),
        new SWH_I("WUXGA", 1920, 1200),
        new SWH_I("WQXGA", 2560, 1600),
        new SWH_I("16x10", 5120, 3200)
    };
    /**
     *
     */
    public static SWH_I[] res16x9 = new SWH_I[]{
        new SWH_I("QWVGA", 427, 240),
        new SWH_I("WVGA", 854, 480),
        new SWH_I("HD 720", 1280, 720),
        new SWH_I("HD 1080", 1920, 1080),
        new SWH_I("HD 2160", 3840, 2160),
        new SWH_I("HD 4320", 7680, 4320)
    };
    /**
     *
     */
    public SWH_I resolution;

    /**
     *
     * @param _resolution
     */
    public VPickScreenResolution(SWH_I _resolution) {
        if (_resolution == null) {
            _resolution = res16x9[1];
        }
        resolution = _resolution;
        setContent(new VString(this));
    }

    /**
     *
     * @return
     */
    @Override
    public Object getValue() {
        return resolution;
    }

    /**
     *
     * @param _value
     */
    public void setValue(Object _value) {
        if (_value == null) {
            return;
        }
        resolution = (SWH_I) _value;
        modified(resolution);
    }

    /**
     *
     * @param _manual
     * @return
     */
    public IView popup(boolean _manual) {
        VChain c = new VChain(UV.cNENW);
        c.add(ratio("16:10", res16x10));
        c.add(ratio("16:9", res16x9));
        c.add(ratio("5:4", res5x4));
        c.add(ratio("4:3", res4x3));
        c.add(ratio("3:2", res3x2));

        if (_manual) {
            final Value cw = new Value(new Integer(720));
            final Value ch = new Value(new Integer(480));
            VChain m = new VChain(UV.cEW);
            m.add(new VString("Manual:"));
            m.add(new VEditValue(" Width(", cw, ")", false));
            m.add(new VEditValue(" Height(", ch, ")", false));
            VButton b = new VButton(m) {

                @Override
                public void picked(IEvent _e) {
                    VPickScreenResolution.this.setValue(new SWH_I("Custom", cw.intValue(), ch.intValue()));
                }
            };
            c.add(b);
        }
        c.setBorder(new PopupBorder(10));
        return c;
    }

    private IView ratio(String _name, SWH_I[] _resolutions) {
        VChain r = new VChain(UV.cSWNW);
        r.add(new VString(_name));
        for (int i = 0; i < _resolutions.length; i++) {
            VItem vi = new VItem(new VString(_resolutions[i]), _resolutions[i]) {

                @Override
                public void picked(IEvent _) {
                    VPickScreenResolution.this.setValue(getValue());
                }
            };
            vi.spans(UV.cXEW);
            r.add(vi);
        }
        r.setBorder(new PopupBorder(5));
        r.spans(UV.cXNS);
        return r;
    }

    /**
     *
     * @param _
     */
    @Override
    public void picked(IEvent _) {
        UV.popup(this, UV.cCC, popup(true), true,true);
    }

    /**
     *
     * @param _resolution
     */
    public void modified(SWH_I _resolution) {
        
    }
}
