/*
 * VHueShifter.java.java
 *
 * Created on 01-03-2010 01:34:45 PM
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

import colt.nicity.view.paint.UPaint;
import colt.nicity.view.adaptor.VS;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.image.IImage;
import colt.nicity.view.list.AItem;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import java.awt.geom.GeneralPath;

/**
 *
 * @author Administrator
 */
public class VHueShifter extends AItem implements IMouseEvents, IMouseMotionEvents {

    float[] shiftHues;
    int xres;
    //----------------------------------------------------

    /**
     *
     * @param _w
     * @param _h
     * @param _xres
     */
    public VHueShifter(int _w, int _h, int _xres) {
        xres = _xres;
        shiftHues = new float[_w / _xres];
        for (int i = 0; i < shiftHues.length; i++) {
            float hue = (float) i / (float) shiftHues.length;
            shiftHues[i] = hue;
        }
        VChain c = new VChain(UV.cSN);
        c.add(new RigidBox(_w, _h));
        setContent(c);
        setBorder(new ViewBorder());
    }
    //----------------------------------------------------------------

    /**
     *
     * @return
     */
    public float[] getShifts() {
        return shiftHues;
    }

    /**
     *
     * @param _shifts
     */
    public void setShifts(float[] _shifts) {
        shiftHues = _shifts;
    }
    //----------------------------------------------------------------

    /**
     *
     * @param _hue
     * @return
     */
    public float shift(float _hue) {
        int index = (int) (shiftHues.length * _hue);
        float shift = shiftHues[index];
        return shift;
    }
    //----------------------------------------------------------------
    IImage hueMap;
    //----------------------------------------------------------------

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    @Override
    public void paintBorder(ICanvas _g, int _x, int _y, int _w, int _h) {
        if (hueMap == null || hueMap.getWidth() != _w || hueMap.getHeight() != _h) {
            hueMap = VS.systemImage(_w, _h, VS.c32BitRGB);
            ICanvas g = hueMap.canvas(_g.who());
            for (int i = 0; i < _h; i++) {
                float hue = (float) i / (float) _h;
                g.setColor(new AColor(hue, 1f, 1f));
                g.line(_x + 0, _y + i, _x + 10, _y + i);
                g.line(_x + i, _y + 0, _x + i, _y + 10);
            }
        }
        _g.drawImage(hueMap, 0, 0, null);
        GeneralPath arrows = new GeneralPath();
        for (int i = 0; i < _w; i += xres) {
            float hue = (float) i / (float) _w;
            int index = (int) (shiftHues.length * hue);
            float shift = shiftHues[index];
            int x = (int) (_w * shift);
            _g.setColor(AColor.gray);
            _g.line(_x + x, _y + 0, _x + x, _y + i);
            _g.line(_x + 0, _y + i, _x + x, _y + i);


            UPaint.arrowTo(arrows, _x + x, _y + i, _x + x, _y + 0, 4, 90);
            UPaint.arrowTo(arrows, _x + 0, _y + i, _x + x, _y + i, 4, 90);

        }
        _g.draw(arrows);
    }

    /**
     *
     * @param _e
     */
    @Override
    public void mouseMoved(MouseMoved _e) {
    }

    /**
     *
     * @param _e
     */
    @Override
    public void mouseDragged(MouseDragged _e) {
        XY_I p = _e.getPoint();
        if (p.y < 0 || p.y > getH()) {
            return;
        }
        int index = (int) (shiftHues.length * ((float) p.y / getH()));

        if (p.x < 0 || p.x > getW()) {
            return;
        }
        float px = (float) p.x / getW();
        shiftHues[index] = px;

        paint();
    }
}
