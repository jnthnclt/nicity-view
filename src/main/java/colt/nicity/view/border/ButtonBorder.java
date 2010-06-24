/*
 * ButtonBorder.java.java
 *
 * Created on 03-12-2010 06:31:38 PM
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
package colt.nicity.view.border;

import colt.nicity.view.flavor.AFlavor;
import colt.nicity.view.flavor.ButtonFlavor;
import colt.nicity.core.value.IValue;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class ButtonBorder extends AFlaggedBorder {

    /**
     *
     */
    public static AFlavor flavor = new ButtonFlavor();
    
    private int pad = 4;
    
    private Object buttonColor = ViewColor.cButtonTheme;
    
    /**
     *
     */
    public ButtonBorder() {
        this(ViewColor.cButtonTheme, 4);
    }
    

    /**
     *
     * @param _pad
     */
    public ButtonBorder(int _pad) {
        this(ViewColor.cButtonTheme, _pad);
    }
    

    /**
     *
     * @param _color
     */
    public ButtonBorder(Object _color) {
        this(_color, 4);
    }
    

    /**
     *
     * @param _color
     * @param _pad
     */
    public ButtonBorder(Object _color, int _pad) {
        buttonColor = _color;
        pad = _pad;
    }
    

    /**
     *
     * @param _color
     */
    public void setColor(Object _color) {
        buttonColor = _color;
    }
    

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    @Override
    public void paintBorder(ICanvas g, int x, int y, int w, int h) {
    }
    

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param _w
     * @param _h
     */
    @Override
    public void paintBackground(ICanvas g, int x, int y, int _w, int _h) {
        if (buttonColor == null) {
            return;
        }
        AColor color = ViewColor.cButtonTheme;
        if (buttonColor instanceof AColor) {
            color = (AColor) buttonColor;
        } else if (buttonColor instanceof IValue) {
            color = (AColor) ((IValue) buttonColor).getValue();
        }

        if (is(cActive)) {
            color = ViewColor.cThemeActive;
            flavor.paintFlavor(g, x, y, _w, _h, color);
        } else if (is(cSelected)) {
            color = ViewColor.cThemeSelected;
            flavor.paintFlavor(g, x, y, _w, _h, color);
        } else {
            flavor.paintFlavor(g, x, y, _w, _h, color);
        }
    }
    

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return pad;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return pad;
    }
    
}
