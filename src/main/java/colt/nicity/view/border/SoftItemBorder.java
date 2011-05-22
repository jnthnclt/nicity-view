/*
 * SoftItemBorder.java.java
 *
 * Created on 03-12-2010 06:32:15 PM
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

import colt.nicity.view.flavor.OutlineFlavor;
import colt.nicity.view.flavor.ScrollFlavor;
import colt.nicity.core.value.IValue;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class SoftItemBorder extends AFlaggedBorder {

    private int padW = 3;
    private int padH = 3;
    private Object itemColor = ViewColor.cItemTheme;
    
    /**
     *
     */
    public SoftItemBorder() {
        this(ViewColor.cItemTheme, 0);
    }

    /**
     *
     * @param _color
     */
    public SoftItemBorder(Object _color) {
        this(_color, 0);
    }
    
    /**
     *
     * @param _color
     * @param _pad
     */
    public SoftItemBorder(Object _color, int _pad) {
        this(_color, _pad, _pad);
    }
    
    /**
     *
     * @param _color
     */
    public void setColor(AColor _color) {
        itemColor = _color;
    }
    
    /**
     *
     * @param _color
     * @param _padW
     * @param _padH
     */
    public SoftItemBorder(Object _color, int _padW, int _padH) {
        itemColor = _color;
        padW = _padW;
        padH = _padH;
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
    public void paintBorder(ICanvas g,int x,int y, int w, int h) {
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
    public void paintBackground(ICanvas g,int x,int y, int _w, int _h) {
        AColor color = ViewColor.cItemTheme;
        if (itemColor instanceof AColor) {
            color = (AColor) itemColor;
        } else if (itemColor instanceof IValue) {
            color = (AColor) ((IValue) itemColor).getValue();
        }
        g.setColor(color);
        g.paintFlavor(ScrollFlavor.flavor,x,y,_w,_h,color);

        if (is(cActive)) {
            if (is(cSelected)) {
                color = ViewColor.cThemeSelected;
                if (ViewColor.inverted)color = color.lighten(0.1f);
                else color = color.darken(0.1f);
                g.paintFlavor(OutlineFlavor.flavor,x+1,y+1, _w - 3, _h - 3,color);
            }
            else {
                color = ViewColor.cThemeActive;
                if (ViewColor.inverted) color = color.lighten(0.1f);
                else color = color.darken(0.1f);
                g.paintFlavor(OutlineFlavor.flavor,x+1, y+1, _w - 3, _h - 3,color);
            }
        } else if (is(cSelected)) {
            color = ViewColor.cThemeSelected;
            if (ViewColor.inverted)color = color.lighten(0.1f);
            else color = color.darken(0.1f);
            g.paintFlavor(ScrollFlavor.flavor,x+1,y+1, _w - 3, _h - 3,color);
        } else if (itemColor == null) {
            return;
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return padW;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return padH;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return padW;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return padH;
    }
    
}
