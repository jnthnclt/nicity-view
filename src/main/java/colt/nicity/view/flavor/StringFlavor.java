/*
 * StringFlavor.java.java
 *
 * Created on 03-12-2010 06:35:04 PM
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

import colt.nicity.core.lang.MinMaxDouble;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class StringFlavor extends AFlavor {
    Object string,color,font;
    double xAlign = 0.5d;
    double yAlign = 0.5d;
    /**
     *
     * @param _string
     */
    public StringFlavor(Object _string) {
        this(_string, ViewColor.cThemeFont, UV.fonts[UV.cText]);
    }
    /**
     *
     * @param _string
     * @param _color
     * @param _font
     */
    public StringFlavor(Object _string,Object _color,Object _font) {
        this(_string, _color, _font, 0.5d, 0.5d);
    }
    /**
     *
     * @param _string
     * @param _color
     * @param _font
     * @param _xAlign
     * @param _yAlign
     */
    public StringFlavor(Object _string,Object _color,Object _font,double _xAlign,double _yAlign) {
        string = _string;
        color = _color;
        font = _font;
        xAlign = _xAlign;
        yAlign = _yAlign;
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
    public void paintFlavor(ICanvas _g, int _x, int _y, int _w, int _h, AColor _color) {
        AColor c = (color instanceof AColor) ? (AColor)color : (AColor)((Value)color).getValue();
        _g.setColor(c);
        AFont f = (font instanceof AFont) ? (AFont)font : (AFont)((Value)font).getValue();
        String s = (string instanceof String) ? (String)string : (String)((Value)string).getValue();
        float sw = f.getW(s);
        float sh = f.getH(s);


        double px = MinMaxDouble.unzeroToOne(_x, _x+_w, xAlign);
        double py = MinMaxDouble.unzeroToOne(_y, _y+_h, yAlign);
        
        double sx = MinMaxDouble.unzeroToOne(0, sw, xAlign);
        double sy = MinMaxDouble.unzeroToOne(0, sh, yAlign);

        int dx = (int)(px-sx);
        int dy = (int)((py-sy)+(sh/2));

        _g.drawString(s,dx,dy);

    }
}
