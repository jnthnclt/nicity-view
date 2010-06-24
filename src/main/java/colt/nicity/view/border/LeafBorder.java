/*
 * LeafBorder.java.java
 *
 * Created on 01-03-2010 01:31:38 PM
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

import colt.nicity.view.core.AColor;
import colt.nicity.view.core.Place;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;
import java.awt.geom.GeneralPath;

/**
 *
 * @author Administrator
 */
public class LeafBorder extends AFlaggedBorder {

    private float x = 2;
    private float y = 2;
    private float w = 2;
    private float h = 2;
    private AColor buttonColor = ViewColor.cThemeFont;
    private Place place = UV.cEW;
    private int size = 3;

    /**
     *
     * @param _place
     * @param _size
     */
    public LeafBorder(Place _place, int _size) {
        this(ViewColor.cThemeFont, _place, _size);
    }

    /**
     *
     * @param _color
     * @param _place
     * @param _size
     */
    public LeafBorder(AColor _color, Place _place, int _size) {
        buttonColor = _color;
        place = _place;
        size = _size;
    }

    /**
     *
     * @param _color
     */
    public void setColor(AColor _color) {
        buttonColor = _color;
    }

    /**
     *
     * @param _place
     */
    public void setPlace(Place _place) {
        place = _place;
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
        AColor color = buttonColor;
        g.setColor(color);
        w -= 1;
        h -= 1;
        if (place == UV.cEW) {
            //g.line(x + size, y + 0, x + size, y + h);
            //g.line(x + size, y + 0, x + size * 2, y + 0);
            //g.line(x + size, y + h - 1, x + size * 2, y + h - 1);
            //g.line(x + 0, y + (h / 2), x + size, y + (h / 2));

            GeneralPath p = new GeneralPath();
            p.moveTo(x, y + (h / 2));
            p.curveTo(x + (size * 1.5f), y + (h / 2), x - (size / 2), y, x + size, y);
            p.lineTo(x + w - size, y);
            p.quadTo(x + w, y, x + w, y + size);
            p.lineTo(x + w, y + h - size);
            p.quadTo(x + w, y + h, x + w - size, y + h);
            p.lineTo(x + size, y + h);

            g.draw(p);

            p = new GeneralPath();
            p.moveTo(x, y + (h / 2));
            p.curveTo(x + (size * 1.5f), y + (h / 2), x - (size / 2), y + h, x + size, y + h);
            g.draw(p);

        } else if (place == UV.cWE) {
            g.line(x + w - size, y + 0, x + w - size, y + h);
            g.line(x + w - size, y + 0, x + w - size * 2, y + 0);
            g.line(x + w - size, y + h - 1, x + w - size * 2, y + h - 1);
            g.line(x + w, y + (h / 2), x + w - size, y + (h / 2));
        } else if (place == UV.cSN) {
            g.line(x + 0, y + size, x + w, y + size);
            g.line(x + 0, y + size, x + 0, y + size * 2);
            g.line(x + w - 1, y + size, x + w - 1, y + size * 2);
            g.line(x + w / 2, y + 0, x + w / 2, y + size);
        } else if (place == UV.cNS) {
            g.line(x + 0, y + h - size, x + w, y + h - size);
            g.line(x + 0, y + h - size, x + 0, y + h - size * 2);
            g.line(x + w - 1, y + h - size, x + w - 1, y + h - size * 2);
            g.line(x + w / 2, y + h, x + (w / 2), y + h - size);
        }
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
    public void paintBackground(ICanvas g, int x, int y, int w, int h) {
    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        if (place == UV.cEW) {
            return x + (size * 2);
        }
        return x + size;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        if (place == UV.cSN) {
            return y + (size * 2);
        }
        return y + size;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return w + size;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return h + size;
    }
}
