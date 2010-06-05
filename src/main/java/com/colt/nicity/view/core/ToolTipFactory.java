/*
 * ToolTipFactory.java.java
 *
 * Created on 01-03-2010 01:31:33 PM
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

import com.colt.nicity.view.border.NullBorder;
import com.colt.nicity.view.border.ToolTipBorder;
import com.colt.nicity.view.interfaces.IBorder;
import com.colt.nicity.view.interfaces.IToolTip;

/**
 *
 * @author Administrator
 */
public class ToolTipFactory {

    /**
     *
     */
    public static final ToolTipFactory cDefault = new ToolTipFactory();
    /**
     *
     */
    protected AFont font = UV.fonts[UV.cToolTip];
    /**
     *
     */
    protected AColor fontColor = AColor.black;
    /**
     *
     */
    protected IBorder border = NullBorder.cNull;

    /**
     *
     */
    public ToolTipFactory() {
    }

    /**
     *
     * @param _font
     * @param _fontColor
     * @param _border
     */
    public ToolTipFactory(
            AFont _font,
            AColor _fontColor,
            IBorder _border) {
        if (_font != null) {
            font = _font;
        }
        if (_fontColor != null) {
            fontColor = _fontColor;
        }
        if (_border != null) {
            border = _border;
        }
    }

    /**
     *
     * @param _string
     * @return
     */
    public IToolTip createToolTip(Object _string) {
        VString a = new VString(_string, font, fontColor);
        a.setBorder(border);
        ToolTipView toolTipViewer = new ToolTipView(a, 5000);
        toolTipViewer.setBorder(new ToolTipBorder());
        return toolTipViewer;
    }

    /**
     *
     * @param _strings
     * @return
     */
    public IToolTip createToolTip(String[] _strings) {
        Viewer a = new Viewer(new ViewText(_strings, font, fontColor));
        a.setBorder(border);
        ToolTipView toolTipViewer = new ToolTipView(a, 5000);
        toolTipViewer.setBorder(new ToolTipBorder());
        return toolTipViewer;
    }
}
