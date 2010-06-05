/*
 * VEditHSBColor.java.java
 *
 * Created on 03-12-2010 06:41:58 PM
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
package com.colt.nicity.view.value;

import com.colt.nicity.view.event.MouseDragged;
import com.colt.nicity.view.event.MouseEntered;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MouseMoved;
import com.colt.nicity.view.event.MousePressed;
import com.colt.nicity.view.event.MouseReleased;
import com.colt.nicity.view.paint.UPaint;
import com.colt.nicity.core.lang.UDouble;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.RigidBox;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VClip;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IMouseEvents;
import com.colt.nicity.view.interfaces.IMouseMotionEvents;

/**
 *
 * @author Administrator
 */
public class VEditHSBColor extends VClip implements IMouseEvents, IMouseMotionEvents {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        ViewColor.onBlack();
        UV.exitFrame(new VEditHSBColor("test", new Value<AColor>(new AColor(.5f, 0.5f, 0.5f)), 200, 80), "test");
    }
    Object name;
    Value<AColor> color;
    int pw, ph;

    /**
     *
     * @param _name
     * @param _color
     * @param _w
     * @param _h
     */
    public VEditHSBColor(Object _name, Value<AColor> _color, int _w, int _h) {
        super(new RigidBox(_w, _h), _w, _h);
        name = _name;
        color = _color;
    }

    /**
     *
     * @param g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    @Override
    public void paintBackground(ICanvas g, int _x, int _y, int _w, int _h) {
        super.paintBackground(g, _x, _y, _w, _h);
        pw = _w;
        ph = _h;
        if (color == null) {
            return;
        }
        AColor cc = color.getValue();

        int h = _h / 4;
        g.setColor(ViewColor.cThemeFont);
        int step = _w / 2;
        for (int i = 0; i < step; i++) {
            g.setColor(new AColor((float) i / (float) step, 1f, 1f));
            g.rect(true, _x + (i * 2), _y, 2, h);
        }
        g.setColor(AColor.white);
        int ax = (int) (_w * cc.getHue());
        g.line(_x + ax, _y, _x + ax, _y + _h);
        g.setColor(AColor.white);
        g.rect(false, _x, _y, _w, h);// hue

        for (int i = 0; i < step; i++) {
            g.setColor(new AColor(cc.getHue(), (float) i / (float) step, 1f));
            g.rect(true, _x + (i * 2), _y + h, 2, h);
        }
        g.setColor(AColor.black);
        ax = (int) (_w * cc.getSaturation());
        g.line(_x + ax, _y + h, _x + ax, _y + _h + h);
        g.setColor(AColor.white);
        g.rect(false, _x, _y + h, _w, h); // sat

        for (int i = 0; i < step; i++) {
            g.setColor(new AColor((float) i / (float) step));
            g.rect(true, _x + (i * 2), _y + h + h, 2, h);
        }
        g.setColor(AColor.red);
        ax = (int) (_w * cc.getBrightness());
        g.line(_x + ax, _y + h + h, _x + ax, _y + _h + h + h);
        g.setColor(AColor.white);
        g.rect(false, _x, _y + h + h, _w, h); // bri

        UPaint.drawCheckerBoard(g, _x, _y + h + h + h, _w, h, h / 4, AColor.black, AColor.white);
        for (int i = 0; i < step; i++) {
            AColor c = new AColor(cc.intValue());
            c.setA((float) i / (float) step);
            g.setColor(c);
            g.rect(true, _x + (i * 2), _y + h + h + h, 2, h);
        }
        g.setColor(AColor.red);
        ax = (int) (_w * cc.getAlpha());
        g.line(_x + ax, _y + h + h + h, _x + ax, _y + _h + h + h + h);
        g.setColor(AColor.white);
        g.rect(false, _x, _y + h + h + h, _w, h); // alpha

        g.setColor(ViewColor.cThemeFont);
        g.setFont(UV.fonts[UV.cText]);
        UPaint.string(g, name.toString(), UV.fonts[UV.cText], _x + (_w / 2), _y + (_h / 2), UV.cCC);
    }

    /**
     *
     * @param e
     */
    public void mouseEntered(MouseEntered e) {
    }

    /**
     *
     * @param e
     */
    public void mouseExited(MouseExited e) {
    }

    /**
     *
     * @param e
     */
    public void mousePressed(MousePressed e) {
        if (e.isLeftClick()) {
            double xp = UDouble.clamp(e.getX() / (float) pw, 0, 1);
            double yp = UDouble.clamp(e.getY() / (float) ph, 0, 1);
            AColor cc = color.getValue();
            AColor nc = new AColor(cc.intValue());
            nc.setAlpha(cc.getAlpha());
            if (yp <= 0.25) {
                nc.setHue((float) xp);
            } else if (yp <= 0.5) {
                nc.setSaturation((float) xp);
            } else if (yp <= 0.75) {
                nc.setBrightness((float) xp);
            } else if (yp <= 1) {
                nc.setAlpha((float) xp);
            }
            color.setValue(nc);
            paint();
        }
        if (e.isRightClick()) {
            UV.popup(this, e, UV.zone(new VEditNumericColor(name, color)), false);
        }
    }

    /**
     *
     * @param e
     */
    public void mouseReleased(MouseReleased e) {
    }

    /**
     *
     * @param e
     */
    public void mouseMoved(MouseMoved e) {
    }

    /**
     *
     * @param e
     */
    public void mouseDragged(MouseDragged e) {
        double xp = UDouble.clamp(e.getX() / (float) pw, 0, 1);
        double yp = UDouble.clamp(e.getY() / (float) ph, 0, 1);
        AColor cc = color.getValue();
        AColor nc = new AColor(cc.intValue());
        nc.setAlpha(cc.getAlpha());
        if (yp <= 0.25) {
            nc.setHue((float) xp);
        } else if (yp <= 0.5) {
            nc.setSaturation((float) xp);
        } else if (yp <= 0.75) {
            nc.setBrightness((float) xp);
        } else if (yp <= 1) {
            nc.setAlpha((float) xp);
        }
        color.setValue(nc);
        paint();
    }
}
