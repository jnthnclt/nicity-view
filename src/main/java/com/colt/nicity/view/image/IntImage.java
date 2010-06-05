/*
 * IntImage.java.java
 *
 * Created on 03-12-2010 06:35:13 PM
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
package com.colt.nicity.view.image;

import com.colt.nicity.view.awt.UAWT;
import com.colt.nicity.view.adaptor.VS;
import com.colt.nicity.view.value.VColorPalette;
import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.lang.MutableDouble;
import com.colt.nicity.core.lang.UArray;
import com.colt.nicity.core.lang.UDouble;
import com.colt.nicity.core.lang.UInteger;
import com.colt.nicity.core.lang.UMath;
import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.core.value.IValue;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VButton;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.VPaintable;
import com.colt.nicity.view.core.VPan;
import com.colt.nicity.view.core.VPrinter;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IComposite;
import com.colt.nicity.view.interfaces.IEvent;
import com.colt.nicity.view.interfaces.IPaintable;
import com.colt.nicity.view.interfaces.IView;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class IntImage extends Image implements IPaintable, ImageProducer {

    
    /**
     *
     */
    public int[] pixels;
    /**
     *
     */
    public int w;
    /**
     *
     */
    public int h;

    /**
     *
     */
    public IntImage() {
        w = 0;
        h = 0;
        pixels = new int[0];
    }

    /**
     *
     * @param _fileName
     */
    public IntImage(String _fileName) {
        try {
            ViewImage viewImage = new ViewImage(_fileName);
            Image _image = viewImage.getImage();
            w = (int) _image.getWidth(null);
            h = (int) _image.getHeight(null);
            pixels = grabPixels(_image);
        } catch (Exception x) {
            throw new RuntimeException();
        }
    }

    /**
     *
     * @param _image
     */
    public IntImage(Image _image) {
        try {
            w = (int) _image.getWidth(null);
            h = (int) _image.getHeight(null);
            pixels = grabPixels(_image);
        } catch (Exception x) {
            x.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public IntImage(int _w, int _h) {
        this(new int[_w * _h], _w, _h);
    }

    /**
     *
     * @param _pixels
     * @param _w
     * @param _h
     */
    public IntImage(int[] _pixels, int _w, int _h) {
        pixels = _pixels;
        w = _w;
        h = _h;
    }

    /**
     *
     * @param _image
     */
    public void set(Image _image) {
        try {
            w = (int) _image.getWidth(null);
            h = (int) _image.getHeight(null);
            pixels = grabPixels(_image);
        } catch (Exception x) {
            throw new RuntimeException();
        }
    }

    // IPaintable
    /**
     *
     * @return
     */
    public Image getImage() {
        return toImage();
    }

    /**
     *
     * @param g
     * @param _xywh
     */
    public void paint(ICanvas g, XYWH_I _xywh) {
        if (_xywh.w == -1) {
            _xywh.w = (int) (getW(null, null));
        }
        if (_xywh.h == -1) {
            _xywh.h = (int) (getH(null, null));
        }
        Image image = getImage();
        if (image != null) {
            g.drawImage(image, _xywh.x, _xywh.y, _xywh.w, _xywh.h, null);
        }
    }

    /**
     *
     * @param _under
     * @param _over
     * @return
     */
    public float getW(IPaintable _under, IPaintable _over) {
        return w;
    }

    /**
     *
     * @param _under
     * @param _over
     * @return
     */
    public float getH(IPaintable _under, IPaintable _over) {
        return h;
    }

    /**
     *
     * @param _scale
     * @return
     */
    public boolean scale(double _scale) {
        int nw = (int) ((w * _scale) + 0.5d);
        int nh = (int) ((h * _scale) + 0.5d);
        if (nw < 1 || nh < 1) {
            return false;
        }
        resize(nw, nh);
        return true;
    }
    /**
     *
     * @param _w
     */
    public void setW(int _w) {
        double r = h/w;
        IntImage image = new IntImage(_w,(int)(r*_w));
        image.fillToFit(this);
        w = image.w;
        h = image.h;
        pixels = image.pixels;
        modified();
    }

    /**
     *
     * @param _h
     */
    public void setH(int _h) {
        double r = w/h;
        IntImage image = new IntImage((int)(r*_h),_h);
        image.fillToFit(this);
        w = image.w;
        h = image.h;
        pixels = image.pixels;
        modified();
    }


    /**
     *
     * @param _w
     * @param _h
     */
    public void resize(int _w, int _h) {
        IntImage image = new IntImage(_w, _h);
        image.fillToFit(this);
        w = image.w;
        h = image.h;
        pixels = image.pixels;
        modified();
    }

    /**
     *
     * @return
     */
    public IntImage copy() {
        int[] copy = UArray.copy(pixels);
        return new IntImage(copy, w, h);
    }
    // pcx = percentage of w to be interpereted as center x
    private static final int cAlpha = AColor.rgbaToInt(0, 0, 0, 0);

    /**
     *
     * @param _pcx
     * @param _pcy
     * @param _radians
     * @return
     */
    public IntImage rotate(float _pcx, float _pcy, double _radians) {

        IntImage rotated = new IntImage(w, h);
        double cx = _pcx * w;
        double cy = _pcy * h;
        double cos = Math.cos(_radians);
        double sin = Math.sin(_radians);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int index = (y * w) + x;
                double x2 = (cos * (x - cx)) - (sin * (y - cy)) + cx;
                double y2 = (sin * (x - cx)) + (cos * (y - cy)) + cy;
                int index2 = ((int) (y2 + 0.5d) * w) + (int) (x2 + 0.5d);
                if (index2 < 0) {
                    rotated.pixels[index] = cAlpha;
                } else if (index2 >= pixels.length) {
                    rotated.pixels[index] = cAlpha;
                } else {
                    rotated.pixels[index] = pixels[index2];
                }
            }
        }
        //pixels = rotated.pixels;
        //modified();
        return rotated;
    }

    /**
     *
     * @param _image
     */
    public void fillToFit(IntImage _image) {
        if (_image == null) {
            return;
        }
        if (_image.pixels == null) {
            return;
        }
        if (pixels == null) {
            return;
        }
        for (int y = 0; y < h; y++) {
            double yp = (double) y / (double) h;
            for (int x = 0; x < w; x++) {
                double xp = (double) x / (double) w;
                int i = (y * w) + x;
                int fx = (int) (xp * _image.w);
                int fy = (int) (yp * _image.h);
                int fi = (fy * _image.w) + fx;
                if (pixels.length != 0 && _image.pixels.length != 0) {
                    pixels[i] = _image.pixels[fi];
                }
            }
        }
        modified();
    }

    /**
     *
     * @param _pixels
     */
    public void fill(int[] _pixels) {
        int l = Math.min(_pixels.length, pixels.length);
        for (int p = 0; p < l; p++) {
            pixels[p] = _pixels[p];
        }
        modified();
    }

    /**
     *
     * @param _
     * @param _pallete
     */
    public void palette(IOut _, VColorPalette _pallete) {
        for (int p = 0; p < pixels.length; p++) {
            if (p % 1000 == 0) {
                _.out(p, pixels.length);
            }
            _pallete.addColor(pixels[p]);
        }
    }

    /**
     *
     * @param _
     * @param _pallete
     */
    public void fill(IOut _, VColorPalette _pallete) {
        for (int p = 0; p < pixels.length; p++) {
            if (p % 1000 == 0) {
                _.out(p, pixels.length);
            }
            pixels[p] = _pallete.lucolor(pixels[p]);
        }
    }

    /**
     *
     * @param _
     * @param _pallete
     */
    public void alpha(IOut _, VColorPalette _pallete) {
        for (int p = 0; p < pixels.length; p++) {
            if (p % 1000 == 0) {
                _.out(p, pixels.length);
            }
            pixels[p] = _pallete.lualpha(pixels[p]);
        }
    }
    private static int mediaTrackerID = 0;
    private static final Component component = new Component() {
    };
    private static final MediaTracker tracker = new MediaTracker(component);

    /**
     *
     * @param image
     */
    public static void load(Image image) {
        if (image == null) {
            return;
        }
        synchronized (tracker) {
            int id = getNextID();
            tracker.addImage(image, id);
            try {
                tracker.waitForID(id, 0);
            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED while loading Image");
            }
            tracker.statusID(id, false);
            tracker.removeImage(image, id);
            image.getWidth(null);
            image.getHeight(null);
        }
    }

    private static int getNextID() {
        synchronized (tracker) {
            return ++mediaTrackerID;
        }
    }

    /**
     *
     * @return
     */
    public IView view() {
        return new Viewer(new VPaintable(this));
    }

    /**
     *
     * @param _title
     * @param _w
     * @param _h
     * @param _save
     * @return
     */
    public IView view(Object _title, int _w, int _h, final IValue _save) {
        if (_title == null) {
            _title = "Image:" + w + "x" + h;
        }
        final Object title = _title;
        final IValue plugin = new Value();
        VChain c = new VChain(UV.cSN);
        final Viewer icon = new Viewer(new VPaintable(this));
        if (w > UAWT.getScreenWidth()) {
            _w = (int) (UAWT.getScreenWidth() - 100);
        }
        if (h > UAWT.getScreenHeight()) {
            _h = (int) (UAWT.getScreenHeight() - 100);
        }
        if (_w == -1 && _h == -1) {
            c.add(icon);
        } else {
            c.add(new VPan(icon, _w, _h));
        }
        Object pluginString = new Object() {

            @Override
            public String toString() {
                return " Process " + plugin;
            }
        };


        VButton save = new VButton(" Save ") {

            @Override
            public void picked(IEvent _e) {
                _save.setValue(IntImage.this);
            }
        };

        VButton print = new VButton(" Print ") {

            @Override
            public void picked(IEvent _e) {
                VPrinter printer = new VPrinter(
                        title,
                        icon, 300, 600);
                printer.toFront(this);
            }
        };
        VChain menu = new VChain(UV.cEW);
        if (_save != null) {
            menu.add(save);
        }
        menu.add(print);

        c.add(menu);
        return c;
    }

    /**
     *
     * @param _title
     * @param _w
     * @param _h
     */
    public void display(Object _title, int _w, int _h) {
        display(_title, _w, _h, null);
    }

    /**
     *
     * @param _title
     * @param _w
     * @param _h
     * @param _save
     */
    public void display(Object _title, int _w, int _h, final IValue _save) {
        if (_title == null) {
            _title = "Image:" + w + "x" + h;
        }
        UV.frame(view(_title, _w, _h, _save), _title.toString());
    }

    /**
     *
     * @return
     */
    public IntImage getCopy() {
        return new IntImage(UArray.copy(pixels), w, h);
    }

    /**
     *
     * @param _alpha
     * @return
     */
    public IImage toSystemImage(boolean _alpha) {
        IImage image = VS.systemImage(w, h, (_alpha) ? VS.c32BitARGB : VS.c32BitRGB, pixels);
        return image;
    }

    /**
     *
     * @param _scaleX
     * @param _scaleY
     * @param _alpha
     * @return
     */
    public IImage toSystemImage(double _scaleX, double _scaleY, boolean _alpha) {
        int nw = (int) ((w * _scaleX) + 0.5d);
        int nh = (int) ((h * _scaleY) + 0.5d);
        if (nw < 1 || nh < 1) {
            return null;
        }
        IntImage _image = new IntImage(nw, nh);
        _image.fillToFit(this);

        IImage image = VS.systemImage(_image.w, _image.h, (_alpha) ? VS.c32BitARGB : VS.c32BitRGB, _image.pixels);
        return image;
    }
    Image cache;

    /**
     *
     * @return
     */
    public Image toImage() {
        if (pixels == null || pixels.length == 0) {
            return null;
        }
        if (cache == null) {
            cache = (Image) toSystemImage(true).data(0);

        }
        return cache;
    }

    /**
     *
     */
    public void modified() {
        cache = null;
    }

    /**
     *
     */
    public void clear() {
        fill(0, 0, 0, 255);
    }

    /**
     *
     * @param _r
     * @param _g
     * @param _b
     * @param _a
     */
    public void fill(int _r, int _g, int _b, int _a) {
        Arrays.fill(pixels, AColor.rgbaToInt(_r, _g, _b, _a));
        modified();
    }

    /**
     *
     * @param _
     * @param _shift
     */
    public void shiftAlpha(IOut _, int _shift) {
        for (int p = 0; p < pixels.length; p++) {
            if (p % 1000 == 0) {
                _.out(p, pixels.length);
            }
            int pixel = pixels[p];
            int _r = (pixel >> 16) & 0xFF;
            int _g = (pixel >> 8) & 0xFF;
            int _b = (pixel >> 0) & 0xFF;
            int _a = (pixel >> 24) & 0xFF;

            _a += _shift;
            if (_a < 0) {
                _a = 0;
            }
            if (_a > 255) {
                _a = 255;
            }
            pixels[p] = AColor.rgbaToInt(_r, _g, _b, _a);
        }
        modified();
    }

    /**
     *
     * @param _fx
     * @param _fy
     * @param _tx
     * @param _ty
     * @param _color
     */
    public void line(int _fx, int _fy, int _tx, int _ty, int _color) {
        float steps = Math.max(Math.abs(_fx - _tx), Math.abs(_fy - _ty));
        for (float p = 0; p < 1; p += 1f / steps) {
            int x = (int) (UDouble.linearInterpolation(_fx, _tx, p) + 0.5f);
            int y = (int) (UDouble.linearInterpolation(_fy, _ty, p) + 0.5f);
            pixels[y * w + x] = _color;
        }
    }

    /**
     *
     * @param _image
     */
    public void clobber(BufferedImage _image) {
        try {
            w = (int) _image.getWidth();
            h = (int) _image.getHeight();
            pixels = grabPixels(_image);
        } catch (Exception x) {
            throw new RuntimeException();
        }
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public AColor colorAt(int _x, int _y) {
        if (_x < 0) {
            _x = 0;
        } else if (_x >= w) {
            _x = w - 1;
        }
        if (_y < 0) {
            _y = 0;
        } else if (_y >= h) {
            _y = h - 1;
        }
        return new AColor(intAt(_x, _y));
    }

    /**
     *
     * @param _color
     * @param _x
     * @param _y
     */
    public void colorAt(AColor _color, int _x, int _y) {
        if (_x < 0) {
            _x = 0;
        } else if (_x >= w) {
            _x = w - 1;
        }
        if (_y < 0) {
            _y = 0;
        } else if (_y >= h) {
            _y = h - 1;
        }
        pixels[_y * w + _x] = _color.toInt();
    }

    /**
     *
     * @param _p
     * @param _x
     * @param _y
     */
    public void intAt(int _p, int _x, int _y) {
        pixels[_y * w + _x] = _p;
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public int intAt(int _x, int _y) {
        return pixels[_y * w + _x];
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public int rAt(int _x, int _y) {
        return (pixels[_y * w + _x] >> 16) & 0xFF;
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public int gAt(int _x, int _y) {
        return (pixels[_y * w + _x] >> 8) & 0xFF;
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public int bAt(int _x, int _y) {
        return (pixels[_y * w + _x] >> 0) & 0xFF;
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public int aAt(int _x, int _y) {
        return (pixels[_y * w + _x] >> 24) & 0xFF;
    }

    /**
     *
     * @param _v
     * @param _x
     * @param _y
     */
    public void rAt(int _v, int _x, int _y) {
        int p = pixels[_y * w + _x];
        pixels[_y * w + _x] = (((p >> 24) & 0xFF) << 24)
                | (((_v) & 0xFF) << 16)
                | (((p >> 8) & 0xFF) << 8)
                | (((p >> 0) & 0xFF) << 0);
    }

    /**
     *
     * @param _v
     * @param _x
     * @param _y
     */
    public void gAt(int _v, int _x, int _y) {
        int p = pixels[_y * w + _x];
        pixels[_y * w + _x] = (((p >> 24) & 0xFF) << 24)
                | (((p >> 16) & 0xFF) << 16)
                | (((_v) & 0xFF) << 8)
                | (((p >> 0) & 0xFF) << 0);
    }

    /**
     *
     * @param _v
     * @param _x
     * @param _y
     */
    public void bAt(int _v, int _x, int _y) {
        int p = pixels[_y * w + _x];
        pixels[_y * w + _x] = (((p >> 24) & 0xFF) << 24)
                | (((p >> 16) & 0xFF) << 16)
                | (((p >> 8) & 0xFF) << 8)
                | (((_v) & 0xFF) << 0);
    }

    /**
     *
     * @param _v
     * @param _x
     * @param _y
     */
    public void aAt(int _v, int _x, int _y) {
        int p = pixels[_y * w + _x];
        pixels[_y * w + _x] = (((_v) & 0xFF) << 24)
                | (((p >> 16) & 0xFF) << 16)
                | (((p >> 8) & 0xFF) << 8)
                | (((p >> 0) & 0xFF) << 0);
    }

    /**
     *
     * @param _r
     * @param _g
     * @param _b
     * @param _x
     * @param _y
     */
    public void rgbAt(int _r, int _g, int _b, int _x, int _y) {
        pixels[(_y * w) + _x] = ((255 & 0xFF) << 24)
                | ((_r & 0xFF) << 16)
                | ((_g & 0xFF) << 8)
                | ((_b & 0xFF) << 0);
    }

    /**
     *
     * @param _image
     * @param _output
     * @param _x
     * @param _y
     * @return
     */
    public static IntImage clip(IntImage _image, IntImage _output, int _x, int _y) {
        int w = _image.w;
        int h = _image.h;
        int[] pixels = _image.pixels;

        int _w = _output.w;
        int _h = _output.h;
        int[] outPixels = _output.pixels;

        for (int yi = _y, yo = 0; yi < h && yo < _h; yi++, yo++) {
            for (int xi = _x, xo = 0; xi < w && xo < _w; xi++, xo++) {
                outPixels[(yo * _h) + xo] = pixels[(yi * h) + xi];
            }
        }

        return _output;
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public float getHue(int _x, int _y) {
        if (_x < 0 || _y < 0) {
            return 0;
        }
        if (_x >= w || _y >= h) {
            return 0;
        }
        int c = pixels[(_y * w) + _x];
        int a = (c >> 24) & 0xFF;
        int r = (c >> 16) & 0xFF;
        int g = (c >> 8) & 0xFF;
        int b = (c >> 0) & 0xFF;
        return Color.RGBtoHSB(r, g, b, null)[0];
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public int getHueVector(int _x, int _y) {
        if (_x < 0 || _y < 0) {
            return 0;
        }
        if (_x >= w || _y >= h) {
            return 0;
        }
        int c = pixels[(_y * w) + _x];
        return AColor.degreeForInt(c);
    }

    /**
     *
     * @param _start
     * @param _end
     * @param _radius
     * @param _startIntensity
     * @param _endIntensity
     */
    public void modifyHueWithVector(XY_I _start, XY_I _end, double _radius, double _startIntensity, double _endIntensity) {
        double cx = _start.x;
        double cy = _start.y;

        if (_radius < 0) {
            _radius = UMath.distance(cx, cy, _end.x, _end.y);
        }
        if (_radius < 1) {
            return;
        }
        double radians = UMath.angle(cx, cy, _end.x, _end.y);

        int xs = (int) (cx - _radius);
        if (xs < 0) {
            xs = 0;
        }
        int ys = (int) (cy - _radius);
        if (ys < 0) {
            ys = 0;
        }
        int xe = (int) (xs + (_radius * 2));
        if (xe > w) {
            xe = w;
        }
        int ye = (int) (ys + (_radius * 2));
        if (ye > h) {
            ye = h;

        }
        double[] vector = new double[2];
        double[] hueVector = new double[2];

        for (int y = ys; y < ye; y++) {
            for (int x = xs; x < xe; x++) {
                double distance = UMath.distance(cx, cy, x, y);
                if (distance > _radius) {
                    continue;
                }
                double dropoff = distance / _radius;
                int index = (y * w) + x;

                double _radians = AColor.radiansForInt(pixels[index]);
                UMath.vector(radians, 1 - dropoff, vector);//!! 1-dropoff

                UMath.vector(_radians, dropoff, hueVector);
                double vx = hueVector[0] + vector[0];
                double vy = hueVector[1] + vector[1];

                _radians = UMath.angle(0, 0, vx, vy);
                pixels[index] = AColor.intForRadians(_radians);
            }
        }
        modified();
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public float getVelocity(int _x, int _y) {
        if (_x < 0 || _y < 0) {
            return 0;
        }
        if (_x >= w || _y >= h) {
            return 0;
        }
        int c = pixels[(_y * w) + _x];
        return AColor.intForChannel(c, 1) / 255f;
    }

    /**
     *
     * @param _start
     * @param _end
     * @param _radius
     * @param _startIntensity
     * @param _endIntensity
     * @param _color
     */
    public void modifyVelocity(XY_I _start, XY_I _end, double _radius, double _startIntensity, double _endIntensity, AColor _color) {
        if (_start == null || _end == null) {
            return;

        }
        int[] channels = AColor.channelsForInt(_color.toInt());

        double cx = _start.x;
        double cy = _start.y;

        if (_radius < 0) {
            _radius = UMath.distance(cx, cy, _end.x, _end.y);
        }
        if (_radius < 1) {
            return;
        }
        double angle = UMath.angle(cx, cy, _end.x, _end.y);
        double[] v = UMath.vector(angle, _radius);

        int xs = (int) (cx - _radius);
        if (xs < 0) {
            xs = 0;
        }
        int ys = (int) (cy - _radius);
        if (ys < 0) {
            ys = 0;
        }
        int xe = (int) (xs + (_radius * 2));
        if (xe > w) {
            xe = w;
        }
        int ye = (int) (ys + (_radius * 2));
        if (ye > h) {
            ye = h;

        }
        double continuum = _startIntensity - _endIntensity; // negative is ok; means inverse intensity

        for (int y = ys; y < ye; y++) {
            for (int x = xs; x < xe; x++) {
                double distance = UMath.distance(cx, cy, x, y);
                if (distance > _radius) {
                    continue;
                }
                double factor = _startIntensity - ((distance / _radius) * continuum);

                int index = (y * w) + x;
                int c = pixels[index];

                int a = (c >> 24) & 0xFF;
                int r = (c >> 16) & 0xFF;
                int g = (c >> 8) & 0xFF;
                int b = (c >> 0) & 0xFF;

                a = (int) ((channels[0] * (factor)) + (a * (1 - factor)));//??

                r = (int) ((channels[1] * (factor)) + (r * (1 - factor)));//??

                g = (int) ((channels[2] * (factor)) + (g * (1 - factor)));//??

                b = (int) ((channels[3] * (factor)) + (b * (1 - factor)));//??


                pixels[index] = AColor.rgbaToInt(r, g, b, a);
            }
        }
        modified();
    }

    /**
     *
     * @param _x
     * @param _y
     * @param _radius
     * @param _color
     */
    public void fillCircle(double _x, double _y, double _radius, int _color) {

        int xs = (int) (_x - _radius);
        if (xs < 0) {
            xs = 0;
        }
        int ys = (int) (_y - _radius);
        if (ys < 0) {
            ys = 0;
        }
        int xe = (int) (xs + (_radius * 2));
        if (xe > w) {
            xe = w;
        }
        int ye = (int) (ys + (_radius * 2));
        if (ye > h) {
            ye = h;

        }
        for (int y = ys; y < ye; y++) {
            for (int x = xs; x < xe; x++) {
                double distance = UMath.distance(_x, _y, x, y);
                if (distance > _radius) {
                    continue;
                }
                int index = (y * w) + x;
                pixels[index] = _color;
            }
        }
        modified();
    }

    /**
     *
     * @param _x
     * @param _y
     * @param _color
     * @param _size
     * @param _alpha
     */
    public void fillCircle(double _x, double _y, int[] _color, int _size, float _alpha) {
        int _radius = _size / 2;

        int xs = (int) (_x - _radius);
        if (xs < 0) {
            xs = 0;
        }
        int ys = (int) (_y - _radius);
        if (ys < 0) {
            ys = 0;
        }
        int xe = (int) (xs + (_radius * 2));
        if (xe > w) {
            xe = w;
        }
        int ye = (int) (ys + (_radius * 2));
        if (ye > h) {
            ye = h;

        }
        for (int y = ys; y < ye; y++) {
            for (int x = xs; x < xe; x++) {
                double distance = UMath.distance(_x, _y, x, y);
                if (distance + 1 > _radius) {
                    continue;// removes edje artifacts !! hacky
                }
                int cx = (int) (x - (_x - _radius));
                int cy = (int) (y - (_y - _radius));
                int cindex = (cx * _size) + cy;

                int index = (y * w) + x;

                float a = _alpha;
                float ra = (float) (1d - (distance / (double) _radius));
                float ca = (float) AColor.correlateRGB(pixels[index], _color[cindex]);

                ra = (float) Math.sqrt(ra);
                ra = (float) Math.sqrt(ra);
                //ca = (float)Math.sqrt(Math.sqrt(ca));

                int pixel = AColor.compositeSrcOverBlend(
                        _color[cindex],
                        pixels[index],
                        (float) (a * ra));

                /*
                a = a*((ra+ca)/2);
                
                int pixel = AColor.compositeSrcOver(
                _color[cindex],
                pixels[index],
                a
                );*/

                pixels[index] = pixel;
            }
        }
        modified();
    }

    /**
     *
     * @param _sx
     * @param _sy
     * @param _copy
     * @param _dw
     * @param _dh
     * @param _mask
     */
    public void copy(int _sx, int _sy, int[] _copy, int _dw, int _dh, int[][] _mask) {
        int center = ((_sy * w) + _sx);
        for (int y = 0; y < _dh; y++) {
            for (int x = 0; x < _dw; x++) {
                int cindex = (y * _dw) + x;
                int offest = _mask[x][y];
                int index = center + offest;
                if (index < 0) {
                    _copy[cindex] = 0;
                } else if (index >= pixels.length) {
                    _copy[cindex] = 0;
                } else {
                    _copy[cindex] = pixels[index];
                }
            }
        }
    }

    /**
     *
     * @param _a
     * @param _b
     * @param _p
     * @return
     */
    public static IntImage tween(IntImage _a, IntImage _b, double _p) {
        int l = _a.pixels.length;
        float[] aHSB = new float[3];
        float[] bHSB = new float[3];
        float[] result = new float[3];
        IntImage out = new IntImage(_a.w, _a.h);

        for (int i = 0; i < l; i++) {
            int pa = _a.pixels[i];
            int ar = (pa >> 16) & 0xFF;
            int ag = (pa >> 8) & 0xFF;
            int ab = (pa >> 0) & 0xFF;
            AColor.RGBtoHSB(ar, ag, ab, aHSB);

            int pb = _b.pixels[i];
            int br = (pb >> 16) & 0xFF;
            int bg = (pb >> 8) & 0xFF;
            int bb = (pb >> 0) & 0xFF;
            AColor.RGBtoHSB(br, bg, bb, bHSB);
            AColor.interpolateHueDistanceTo(aHSB, bHSB, (float) _p, result);
            int np = Color.HSBtoRGB((float) result[0], (float) result[1], (float) result[2]);
            out.pixels[i] = np;

        }
        return out;
    }

    /**
     *
     * @param _
     * @param _image
     * @param _angle
     * @param _velocity
     * @param _step
     */
    public static void morph(IOut _, IntImage _image, IntImage _angle, IntImage _velocity, int _step) {
        if (_image == null) {
            return;
        }
        int w = _image.w;
        int h = _image.h;
        int[] pixels = _image.pixels;
        int[] copy = UArray.copy(pixels);
        int pixelCount = copy.length;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int index = (y * w) + x;
                double radians = AColor.radiansForInt(_angle.intAt(x, y));

                int velocity = AColor.grayForInt(_velocity.intAt(x, y));

                double[] v = UMath.vector(radians, _step * (velocity / 255f));
                int vx = (int) (x - v[0]);
                if (vx < 0) {
                    vx = 0;//vx += w;
                }
                if (vx >= w) {
                    vx = w - 1;//vx -= w;
                }
                int vy = (int) (y - v[1]);
                if (vy < 0) {
                    vy = 0;//vy += h;
                }
                if (vy >= h) {
                    vy = h - 1;//vy -= h;
                }
                int vindex = (vy * w) + vx;
                pixels[index] = copy[vindex];
            }
        }
        _image.modified();
    }

    /**
     *
     * @param _
     * @param _image
     * @param _radius
     */
    final public static void covertToAngles(IOut _, IntImage _image, int _radius) {
        int w = _image.w;
        int h = _image.h;

        int[] pixels = _image.pixels;
        int pixelCount = pixels.length;
        int[] copy = UArray.copy(pixels);

        int diameter = _radius * 2;
        int size = diameter * diameter;

        double[] vector = new double[2];

        double[] distances = new double[size];
        double[] vxs = new double[size];
        double[] vys = new double[size];

        int[] offsets = new int[size];
        for (int s = 0; s < size; s++) {
            int y = s / diameter;
            int x = s - (y * diameter);
            offsets[s] = (x - _radius) + ((y - _radius) * w);

            double distance = UMath.distance(_radius, _radius, x, y);
            if (distance > _radius) {
                distances[s] = 0;
            } else {
                distances[s] = distance;
            }
            double radians = UMath.angle(_radius, _radius, x, y);
            if (!Double.isNaN(radians)) { // protect against NaN

                vector = UMath.vector(radians, 1, vector);
                vxs[s] = vector[0];
                vys[s] = vector[1];
            }

        }

        double vx = 0;
        double vy = 0;

        for (int index = 0; index < pixelCount; index++) {

            int c = copy[index];
            int a = (c >> 24) & 0xFF;
            int r = (c >> 16) & 0xFF;
            int g = (c >> 8) & 0xFF;
            int b = (c >> 0) & 0xFF;
            vx = 0;
            vy = 0;

            for (int s = 0; s < size; s++) {
                int index2 = index + offsets[s];
                if (index2 < 0) {
                    continue;
                }
                if (index2 >= pixelCount) {
                    continue;
                }
                int c2 = copy[index2];
                int a2 = (c2 >> 24) & 0xFF;
                int r2 = (c2 >> 16) & 0xFF;
                int g2 = (c2 >> 8) & 0xFF;
                int b2 = (c2 >> 0) & 0xFF;

                double distance = Math.abs((r + g + b) - (r2 + g2 + b2));

                vx += (vxs[s] * distance);
                vy += (vys[s] * distance);
            }
            double angle = UMath.angle(0, 0, vx, vy);
            if (angle != angle) {
                pixels[index] = 0;//!NaN
            }
            pixels[index] = AColor.intForRadians(angle);
        }
        _image.modified();
    }

    /**
     *
     * @param _
     * @param _image
     */
    final public static void invert(IOut _, IntImage _image) {
        if (_image == null) {
            return;
        }
        int[] pixels = _image.pixels;
        int pixelCount = pixels.length;
        for (int i = 0; i < pixelCount; i++) {
            int c = pixels[i];
            int a = (c >> 24) & 0xFF;
            int r = (c >> 16) & 0xFF;
            int g = (c >> 8) & 0xFF;
            int b = (c >> 0) & 0xFF;
            pixels[i] = AColor.rgbaToInt(255 - r, 255 - g, 255 - b, a);
        }
        _image.modified();
    }

    /**
     *
     * @param _
     * @param _image
     * @param _amount
     */
    final public static void contrastSimpleAverage(IOut _, IntImage _image, double _amount) {
        if (_image == null) {
            return;
        }
        int[] pixels = _image.pixels;
        int pixelCount = pixels.length;
        for (int i = 0; i < pixelCount; i++) {
            int c = pixels[i];
            int a = (c >> 24) & 0xFF;
            int r = (c >> 16) & 0xFF;
            int g = (c >> 8) & 0xFF;
            int b = (c >> 0) & 0xFF;

            int avg = (r + g + b) / 3;
            int _r = (int) (avg + ((r - avg) * _amount));
            int _g = (int) (avg + ((g - avg) * _amount));
            int _b = (int) (avg + ((b - avg) * _amount));

            if (_r > 255) {
                _r = 255;
            }
            if (_r < 0) {
                _r = 0;
            }
            if (_g > 255) {
                _g = 255;
            }
            if (_g < 0) {
                _g = 0;
            }
            if (_b > 255) {
                _b = 255;
            }
            if (_b < 0) {
                _b = 0;
            }
            pixels[i] = AColor.rgbaToInt(_r, _g, _b, a);
        }
        _image.modified();
    }

    /**
     *
     * @param _
     * @param _image
     * @param _amount
     */
    final public static void contrastSimpleMiddle(IOut _, IntImage _image, double _amount) {
        if (_image == null) {
            return;
        }
        int[] pixels = _image.pixels;
        int pixelCount = pixels.length;
        for (int i = 0; i < pixelCount; i++) {
            int c = pixels[i];
            int a = (c >> 24) & 0xFF;
            int r = (c >> 16) & 0xFF;
            int g = (c >> 8) & 0xFF;
            int b = (c >> 0) & 0xFF;

            int avg = 127;
            int _r = (int) (avg + ((r - avg) * _amount));
            int _g = (int) (avg + ((g - avg) * _amount));
            int _b = (int) (avg + ((b - avg) * _amount));


            if (_r > 255) {
                _r = 255;
            }
            if (_r < 0) {
                _r = 0;
            }
            if (_g > 255) {
                _g = 255;
            }
            if (_g < 0) {
                _g = 0;
            }
            if (_b > 255) {
                _b = 255;
            }
            if (_b < 0) {
                _b = 0;
            }
            pixels[i] = AColor.rgbaToInt(_r, _g, _b, a);
        }
        _image.modified();
    }

    /**
     *
     * @param _
     * @param _image
     */
    final public static void invertAlpha(IOut _, IntImage _image) {
        if (_image == null) {
            return;
        }
        int[] pixels = _image.pixels;
        int pixelCount = pixels.length;
        for (int i = 0; i < pixelCount; i++) {
            int c = pixels[i];
            int a = (c >> 24) & 0xFF;
            int r = (c >> 16) & 0xFF;
            int g = (c >> 8) & 0xFF;
            int b = (c >> 0) & 0xFF;
            pixels[i] = AColor.rgbaToInt(r, g, b, 255 - a);
        }
        _image.modified();
    }

    /**
     *
     * @param _
     * @param _ai
     * @param _bi
     * @param _r
     * @param _g
     * @param _b
     * @param _a
     * @param _o
     */
    final public static void swapChannels(IOut _, IntImage _ai, IntImage _bi, int _r, int _g, int _b, int _a, IntImage _o) {
        int[] apixels = _ai.pixels;
        int[] bpixels = _bi.pixels;
        int[] opixels = _o.pixels;
        int[] channels = new int[8];
        int pixelCount = opixels.length;
        for (int i = 0; i < pixelCount; i++) {

            int ap = apixels[i];
            channels[0] = (ap >> 16) & 0xFF;
            channels[1] = (ap >> 8) & 0xFF;
            channels[2] = (ap >> 0) & 0xFF;
            channels[3] = (ap >> 24) & 0xFF;

            int bp = bpixels[i];
            channels[4] = (bp >> 16) & 0xFF;
            channels[5] = (bp >> 8) & 0xFF;
            channels[6] = (bp >> 0) & 0xFF;
            channels[7] = (bp >> 24) & 0xFF;

            opixels[i] = AColor.rgbaToInt(channels[_r], channels[_g], channels[_b], channels[_a]);

        }
        _o.modified();
    }

    /**
     *
     * @param _
     * @param _swaps
     * @param _inverts
     * @param _image
     */
    final public static void swapChannels(IOut _, boolean[] _swaps, boolean[] _inverts, IntImage _image) {
        if (_image == null) {
            return;
        }
        int[] pixels = _image.pixels;
        int pixelCount = pixels.length;
        float[] _hsba = new float[4];
        float[] hsba = new float[4];
        for (int i = 0; i < pixelCount; i++) {

            int c = pixels[i];
            int _a = (c >> 24) & 0xFF;
            int _r = (c >> 16) & 0xFF;
            int _g = (c >> 8) & 0xFF;
            int _b = (c >> 0) & 0xFF;

            _hsba = Color.RGBtoHSB(_r, _g, _b, _hsba);
            _hsba[3] = (float) _a / 255f;


            if (_swaps[0]) {
                hsba[0] = (_inverts[0]) ? 1f - _hsba[0] : _hsba[0];
            }
            if (_swaps[1]) {
                hsba[0] = (_inverts[1]) ? 1f - _hsba[1] : _hsba[1];
            }
            if (_swaps[2]) {
                hsba[0] = (_inverts[2]) ? 1f - _hsba[2] : _hsba[2];
            }
            if (_swaps[3]) {
                hsba[0] = (_inverts[3]) ? 1f - _hsba[3] : _hsba[3];
            }
            if (_swaps[4]) {
                hsba[1] = (_inverts[4]) ? 1f - _hsba[0] : _hsba[0];
            }
            if (_swaps[5]) {
                hsba[1] = (_inverts[5]) ? 1f - _hsba[1] : _hsba[1];
            }
            if (_swaps[6]) {
                hsba[1] = (_inverts[6]) ? 1f - _hsba[2] : _hsba[2];
            }
            if (_swaps[7]) {
                hsba[1] = (_inverts[7]) ? 1f - _hsba[3] : _hsba[3];
            }
            if (_swaps[8]) {
                hsba[2] = (_inverts[8]) ? 1f - _hsba[0] : _hsba[0];
            }
            if (_swaps[9]) {
                hsba[2] = (_inverts[9]) ? 1f - _hsba[1] : _hsba[1];
            }
            if (_swaps[10]) {
                hsba[2] = (_inverts[10]) ? 1f - _hsba[2] : _hsba[2];
            }
            if (_swaps[11]) {
                hsba[2] = (_inverts[11]) ? 1f - _hsba[3] : _hsba[3];
            }
            if (_swaps[12]) {
                hsba[3] = (_inverts[12]) ? 1f - _hsba[0] : _hsba[0];
            }
            if (_swaps[13]) {
                hsba[3] = (_inverts[13]) ? 1f - _hsba[1] : _hsba[1];
            }
            if (_swaps[14]) {
                hsba[3] = (_inverts[14]) ? 1f - _hsba[2] : _hsba[2];
            }
            if (_swaps[15]) {
                hsba[3] = (_inverts[15]) ? 1f - _hsba[3] : _hsba[3];
            }
            c = Color.HSBtoRGB(hsba[0], hsba[1], hsba[2]);
            _a = (int) (hsba[3] * 255);
            _r = (c >> 16) & 0xFF;
            _g = (c >> 8) & 0xFF;
            _b = (c >> 0) & 0xFF;


            pixels[i] = AColor.rgbaToInt(_r, _g, _b, _a);

        }
        _image.modified();
    }

    /**
     *
     * @param _
     * @param _image
     */
    public static void monochrome(IOut _, IntImage _image) {
        if (_image == null) {
            return;
        }
        int[] pixels = _image.pixels;
        int pixelCount = pixels.length;
        for (int i = 0; i < pixelCount; i++) {
            if (_ != null && i % 10000 == 0) {
                _.out(i, (float) pixelCount);
                _image.modified();
            }
            int c = pixels[i];
            pixels[i] = AColor.intToGrayInt(c);
        }
        _image.modified();
    }

    /**
     *
     * @param _
     * @param _image
     */
    public static void flipVertical(IOut _, IntImage _image) {
        if (_image == null) {
            return;
        }
        int w = _image.w;
        int h = _image.h;
        int[] pixels = _image.pixels;
        int[] copy = UArray.copy(pixels);
        int pixelCount = pixels.length;
        for (int i = 0; i < pixelCount; i++) {

            int from = i;
            int y = (i / w);
            int x = i - (y * w);
            int to = (((h - 1) - y) * w) + x;
            pixels[to] = copy[from];
        }
        _image.modified();
    }

    /**
     *
     * @param _
     * @param _image
     */
    public static void flipHorizontal(IOut _, IntImage _image) {
        if (_image == null) {
            return;
        }
        int w = _image.w;
        int h = _image.h;
        int[] pixels = _image.pixels;
        int[] copy = UArray.copy(pixels);
        int pixelCount = pixels.length;
        for (int i = 0; i < pixelCount; i++) {

            int from = i;
            int y = ((i / w) * w);
            int x = i - y;
            int to = y + ((w - 1) - x);
            pixels[to] = copy[from];
        }
        _image.modified();
    }

    /**
     *
     * @param _
     * @param _image
     * @param _radius
     */
    public static void blur(IOut _, IntImage _image, int _radius) {
        int w = _image.w;
        int h = _image.h;

        int[] pixels = _image.pixels;
        int pixelCount = pixels.length;
        int[] copy = UArray.copy(pixels);

        int diameter = _radius * 2;
        int size = diameter * diameter;

        double[] distances = new double[size];
        double[] vxs = new double[size];
        double[] vys = new double[size];

        int[] offsets = new int[size];
        for (int s = 0; s < size; s++) {
            int y = s / diameter;
            int x = s - (y * diameter);
            offsets[s] = (x - _radius) + ((y - _radius) * w);

            double distance = UMath.distance(_radius, _radius, x, y);
            if (distance > _radius) {
                distances[s] = 0;
            } else {
                distances[s] = distance;
            }
        }

        for (int index = 0; index < pixelCount; index++) {
            if (_ != null && index % 10000 == 0) {
                _.out(index, (float) pixelCount);
                _image.modified();
            }
            int c = copy[index];

            int a = 0;
            int r = 0;
            int g = 0;
            int b = 0;

            int avg = 0;

            for (int s = 0; s < size; s++) {

                double distance = distances[s];
                if (distance == 0) {
                    continue;
                }
                int index2 = index + offsets[s];
                if (index2 < 0) {
                    continue;
                }
                if (index2 >= pixelCount) {
                    continue;
                }
                avg++;

                int c2 = copy[index2];
                a += (c2 >> 24) & 0xFF;
                r += (c2 >> 16) & 0xFF;
                g += (c2 >> 8) & 0xFF;
                b += (c2 >> 0) & 0xFF;
            }
            if (avg > 0) {
                pixels[index] = AColor.rgbaToInt(r / avg, g / avg, b / avg, a / avg);
            } else {
                pixels[index] = 0;
            }
        }
        _image.modified();
    }
    /**
     *
     */
    final public static int cRGB = 0;
    /**
     *
     */
    final public static int cHSB = 1;
    /**
     *
     */
    final public static int cHue = 2;
    /**
     *
     */
    final public static int cSat = 3;
    /**
     *
     */
    final public static int cBri = 4;
    /**
     *
     */
    final public static int cHS = 5;

    /**
     *
     * @param _
     * @param _image
     * @param _mask
     * @param masked
     * @param _mode
     * @param _radius
     */
    public static void blurMask(
            IOut _,
            IntImage _image,
            IntImage _mask,
            boolean masked,
            int _mode,
            int _radius) {
        int w = _image.w;
        int h = _image.h;

        int[] pixels = _image.pixels;
        int pixelCount = pixels.length;
        int[] copy = UArray.copy(pixels);

        int diameter = _radius * 2;
        diameter++;//?? make odd

        int size = diameter * diameter;

        double[] distances = new double[size];
        double[] vxs = new double[size];
        double[] vys = new double[size];
        int centerOffset = 0;

        int[] offsets = new int[size];
        for (int s = 0; s < size; s++) {
            int y = s / diameter;
            int x = s - (y * diameter);
            offsets[s] = (x - _radius) + ((y - _radius) * w);
            if (s == size / 2) {
                centerOffset = offsets[s];
            }
            double distance = UMath.distance(_radius, _radius, x, y);
            if (distance > _radius) {
                distances[s] = 0;
            } else {
                distances[s] = distance;
            }
        }

        for (int _index = 0; _index < pixelCount; _index++) {
            if (_ != null && _index % 10000 == 0) {
                _.out(_index, (float) pixelCount);
                _image.modified();
            }
            int index = _index + centerOffset;
            if (index < 0) {
                continue;
            }
            if (index >= pixelCount) {
                continue;
            }
            int c = copy[index];
            int oa = (c >> 24) & 0xFF;
            int or = (c >> 16) & 0xFF;
            int og = (c >> 8) & 0xFF;
            int ob = (c >> 0) & 0xFF;

            float[] ohsb = Color.RGBtoHSB(or, og, ob, null);

            int a = 0;
            int r = 0;
            int g = 0;
            int b = 0;

            float hue = 0;
            float sat = 0;
            float bri = 0;

            int avg = 0;
            int blurable = (_mask == null) ? 1 : 0;

            for (int s = 0; s < size; s++) {

                double distance = distances[s];
                if (distance == 0) {
                    continue;
                }
                int index2 = index + offsets[s];
                if (index2 < 0) {
                    continue;
                }
                if (index2 >= pixelCount) {
                    continue;
                }
                avg++;

                int c2 = copy[index2];
                a += (c2 >> 24) & 0xFF;
                r += (c2 >> 16) & 0xFF;
                g += (c2 >> 8) & 0xFF;
                b += (c2 >> 0) & 0xFF;

                float[] hsb = Color.RGBtoHSB((c2 >> 16) & 0xFF, (c2 >> 8) & 0xFF, (c2 >> 0) & 0xFF, null);
                hue += hsb[0];
                sat += hsb[1];
                bri += hsb[2];

                if (_mask != null) {
                    int mr = (_mask.pixels[index2] >> 16) & 0xFF;
                    //System.out.println(mr);
                    if ((mr == 0) == masked) {
                        blurable++;
                        //System.out.println(".");
                    }
                }


            }
            if (blurable > 0 && avg > 0) {
                if (_mode == cRGB) {
                    pixels[index] = AColor.rgbaToInt(r / avg, g / avg, b / avg, a / avg);
                } else if (_mode == cHSB) {
                    pixels[index] = Color.HSBtoRGB(hue / avg, sat / avg, bri / avg);
                } else if (_mode == cHue) {
                    pixels[index] = Color.HSBtoRGB(hue / (float) avg, ohsb[1], ohsb[2]);
                } else if (_mode == cSat) {
                    pixels[index] = Color.HSBtoRGB(ohsb[0], sat / (float) avg, ohsb[2]);
                } else if (_mode == cBri) {
                    pixels[index] = Color.HSBtoRGB(ohsb[0], ohsb[1], bri / (float) avg);
                } else if (_mode == cHS) {
                    pixels[index] = Color.HSBtoRGB(hue / avg, sat / avg, ohsb[2]);
                }
            }
            //else pixels[index] = 0;

        }
        _image.modified();
    }

    /**
     *
     * @param _
     * @param _composite
     * @param _over
     * @param _under
     * @param _alpha
     * @return
     */
    public static boolean composite(
            IOut _,
            IComposite _composite,
            IntImage _over,
            IntImage _under,
            float _alpha) {

        if (_over.w != _under.w) {
            return false;
        }
        if (_over.h != _under.h) {
            return false;
        }
        int[] opixels = _over.pixels;
        int[] upixels = _under.pixels;

        for (int i = 0; i < opixels.length; i++) {
            upixels[i] = _composite.composite(opixels[i], _alpha, upixels[i]);
        }

        return true;
    }

    /**
     *
     * @param _image
     * @param _function
     * @param _arg
     */
    public static void modifyUsingXYMath(IntImage _image, int _function, int _arg) {
        int[] pixels = _image.pixels;
        int w = _image.w;
        int h = _image.h;
        int cw = w / 2;
        int ch = h / 2;
        double factor = _arg;
        factor /= 100;
        if (_function == 1) {
            synchronized (pixels) { // x

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        pixels[y * w + x] = AColor.intForDegree(x % 360);
                    }
                }
            }
        } else if (_function == 2) {
            synchronized (pixels) { // y

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        pixels[y * w + x] = AColor.intForDegree(y % 360);
                    }
                }
            }
        } else if (_function == 3) {
            synchronized (pixels) { // cw-x^2 + ch-y^2

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        float fx = Math.abs(cw - x);
                        float fy = Math.abs(ch - y);
                        double d = (double) (fx * fx + fy * fy);
                        d *= factor * d;
                        int angle = (int) d;
                        angle %= 360;
                        pixels[y * w + x] = AColor.intForDegree(angle);
                    }
                }
            }
        } else if (_function == 4) {
            synchronized (pixels) { // distance

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        float fx = Math.abs(cw - x);
                        float fy = Math.abs(ch - y);
                        double d = Math.sqrt((double) (fx * fx + fy * fy));
                        d *= factor * d;
                        int angle = (int) d;
                        angle %= 360;
                        pixels[y * w + x] = AColor.intForDegree(angle);
                    }
                }
            }
        } else if (_function == 5) {
            synchronized (pixels) { // kx^2 + y

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        pixels[y * w + x] = AColor.intForDegree(((_arg * x * x + y) % 360));
                    }
                }
            }
        } else if (_function == 6) {
            synchronized (pixels) { // ky^2 + x

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        pixels[y * w + x] = AColor.intForDegree(((_arg * y * y + x) % 360));
                    }
                }
            }
        } else if (_function == 7) {
            synchronized (pixels) { // kx^2 + y

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        pixels[y * w + x] = AColor.intForDegree(((_arg * x * x + y) % 360));
                    }
                }
            }
        } else if (_function == 8) {
            synchronized (pixels) { // kx^2 + ky^2

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        pixels[y * w + x] = AColor.intForDegree(((_arg * x * x + _arg * y * y) % 360));
                    }
                }
            }
        } else if (_function == 9) {
            synchronized (pixels) { // x^3 + y^2 + k

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        pixels[y * w + x] = AColor.intForDegree(((x * x * x + y * y + _arg) % 360));
                    }
                }
            }
        } else if (_function == 10) {
            synchronized (pixels) { // y^3 + x^2 + k

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        pixels[y * w + x] = AColor.intForDegree(((x * x + y * y * y + _arg) % 360));
                    }
                }
            }
        } else if (_function == 11) {
            synchronized (pixels) { // distance v2

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        float fx = Math.abs(cw - x);
                        float fy = Math.abs(ch - y);
                        double d = Math.sqrt((double) (_arg * fx * fx + fy * fy));
                        int angle = (int) d;
                        angle %= 360;
                        pixels[y * w + x] = AColor.intForDegree(angle);
                    }
                }
            }
        } else if (_function == 12) {
            synchronized (pixels) { // distance v3

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        float fx = Math.abs(cw - x);
                        float fy = Math.abs(ch - y);
                        double d = Math.sqrt((double) (_arg * fx * fx * fx + fy * fy));
                        int angle = (int) d;
                        angle %= 360;
                        pixels[y * w + x] = AColor.intForDegree(angle);
                    }
                }
            }
        } else if (_function == 13) {
            synchronized (pixels) { // distance v4

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        float fx = Math.abs(cw - x);
                        float fy = Math.abs(ch - y);
                        double d = Math.sqrt((double) (_arg * fx * fx + fy * fy * fy));
                        int angle = (int) d;
                        angle %= 360;
                        pixels[y * w + x] = AColor.intForDegree(angle);
                    }
                }
            }
        } else if (_function == 14) {
            synchronized (pixels) { // distance v5

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        float fx = Math.abs(cw - x);
                        float fy = Math.abs(ch - y);
                        double d = Math.sqrt((double) (_arg * fx * fx + fy * fy * fy));
                        d = Math.sin(d);
                        d = Math.toDegrees(d);
                        int angle = (int) (Math.abs(d));
                        angle %= 360;
                        pixels[y * w + x] = AColor.intForDegree(angle);
                    }
                }
            }
        } else if (_function == 15) {
            synchronized (pixels) { // distance v6

                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        float fx = Math.abs(cw - x);
                        float fy = Math.abs(ch - y);
                        double d = Math.sqrt((double) (_arg * fx * fx + fy * fy * fy));
                        d = Math.cos(d);
                        d = Math.toDegrees(d);
                        int angle = (int) (Math.abs(d));
                        angle %= 360;
                        pixels[y * w + x] = AColor.intForDegree(angle);
                    }
                }
            }
        }
        _image.modified();
    }

    /**
     *
     * @param _image
     * @param _a
     * @param _h
     * @param _s
     * @param _b
     */
    public static void modifyAHSB(IntImage _image, double _a, double _h, double _s, double _b) {



        float[] hsb = new float[3];
        int[] c = new int[4];
        int[] pixels = _image.pixels;
        synchronized (pixels) {
            for (int x = 0; x < pixels.length; x++) {
                AColor.channelsForInt(pixels[x], c);
                Color.RGBtoHSB(c[1], c[2], c[3], hsb);

                if (_h != 0) {
                    hsb[0] = (float) (hsb[0] + _h);
                    if (hsb[0] > 1) {
                        hsb[0] = 1 - hsb[0];
                    }
                    if (hsb[0] < 0) {
                        hsb[0] = hsb[0] - 1;
                    }
                }

                if (_s != 0) {
                    hsb[1] = (float) (hsb[1] + _s);
                    if (hsb[1] > 1) {
                        hsb[1] = 1;
                    }
                    if (hsb[1] < 0) {
                        hsb[1] = 0;
                    }
                }

                if (_b != 0) {
                    hsb[2] = (float) (hsb[2] + _b);
                    if (hsb[2] > 1) {
                        hsb[2] = 1;
                    }
                    if (hsb[2] < 0) {
                        hsb[2] = 0;
                    }
                }

                if (_a != 0) {// then modify a; else tiny mod would be useless

                    c[0] = UInteger.range(c[0] + (int) (_a * 255), 0, 255);
                }

                int color = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                color |= ((c[0] & 0xFF) << 24); // restore with a modified alpha

                pixels[x] = color;
            }
        }
        _image.modified();
    }

    /**
     *
     * @param _image
     * @param _r
     * @param _g
     * @param _b
     * @param _a
     */
    public static void modifyRGBA(IntImage _image, int _r, int _g, int _b, int _a) {
        int[] c = new int[4];
        int[] pixels = _image.pixels;
        synchronized (pixels) {
            for (int x = 0; x < pixels.length; x++) {
                AColor.channelsForInt(pixels[x], c);

                c[0] = UInteger.range(c[0] + _a, 0, 255);
                c[1] = UInteger.range(c[1] + _r, 0, 255);
                c[2] = UInteger.range(c[2] + _g, 0, 255);
                c[3] = UInteger.range(c[3] + _b, 0, 255);

                pixels[x] = AColor.rgbaToInt(c[1], c[2], c[3], c[0]);
            }
        }
        _image.modified();
    }

    // Image
    public int getWidth(ImageObserver observer) {
//System.out.println("getWidth "+observer);
        return w;
    }

    public int getHeight(ImageObserver observer) {
//System.out.println("getHeight "+observer);
        return h;
    }

    public ImageProducer getSource() {
//System.out.println("getSource ");
        return this;
    }

    public Graphics getGraphics() {
//System.out.println("getGraphics ");
        return null;
    }

    public Image getScaledInstance(int width, int height, int hints) {
//System.out.println("getScaledInstance "+width+" "+height+" "+hints);
        return this;
    }

    public Object getProperty(String name, ImageObserver observer) {
//System.out.println("getProperty "+name);
        return Image.UndefinedProperty;
    }

    public void flush() {
//System.out.println("flush ");
        consumers.removeAll();
    }
    // ImageProducer
    ColorModel colorModel = ColorModel.getRGBdefault();
    private CSet consumers = new CSet();

    public void addConsumer(ImageConsumer ic) {
//System.out.println("addConsumer"+ic);
        consumers.add(ic);
    }

    public boolean isConsumer(ImageConsumer ic) {
//System.out.println("isConsumer"+ic);
        return consumers.get(ic) != null;
    }

    public void removeConsumer(ImageConsumer ic) {
//System.out.println("removeConsumer"+ic);
        consumers.remove(ic);
    }

    public void startProduction(ImageConsumer ic) {
//System.out.println("startProduction"+ic+" "+consumers.getCount());
        ic.setDimensions(w, h);
        ic.setColorModel(colorModel);
        ic.setHints(ImageConsumer.TOPDOWNLEFTRIGHT | ImageConsumer.SINGLEPASS);
        ic.setPixels(0, 0, w, h, colorModel, pixels, 0, w);
        ic.imageComplete(ImageConsumer.STATICIMAGEDONE);
    }

    public void requestTopDownLeftRightResend(ImageConsumer ic) {
//System.out.println("requestTopDownLeftRightResend");
        ic.setDimensions(w, h);
        ic.setHints(ImageConsumer.TOPDOWNLEFTRIGHT | ImageConsumer.SINGLEPASS);
        ic.setPixels(0, 0, w, h, ColorModel.getRGBdefault(), pixels, 0, w);
        ic.imageComplete(ImageConsumer.STATICIMAGEDONE);
    }

    /**
     *
     * @param _imageW
     * @param _diameter
     * @param _mask
     * @param _pieMask
     * @param _angularDistinctions
     */
    public void circleOffsetMask(
            int _imageW, int _diameter,
            int[][] _mask, int[][] _pieMask, int _angularDistinctions) {
        int center = _diameter / 2;
        double angularStep = (Math.PI * 2) / _angularDistinctions;
        for (int y = 0; y < _diameter; y++) {
            for (int x = 0; x < _diameter; x++) {
                double distance = UMath.distance(center, center, x, y);
                if (distance > center) {
                    _mask[x][y] = Integer.MAX_VALUE;
                    _pieMask[x][y] = -1;
                } else {
                    int index = ((y - center) * _imageW) + (x - center);
                    _mask[x][y] = index;
                    double pi = UMath.angle(center, center, x, y) / angularStep;
                    if (pi + 0.5d < _angularDistinctions) {
                        pi += 0.5d;
                    }
                    _pieMask[x][y] = (int) (pi);
                }
            }
        }
    }

    /**
     *
     * @param _sx
     * @param _sy
     * @param _copy
     * @param _dw
     * @param _dh
     * @param _mask
     * @param _pieMask
     * @param _pieR
     * @param _pieG
     * @param _pieB
     * @param _pieNums
     */
    public void copyPieSlices(
            int _sx, int _sy, int[] _copy, int _dw, int _dh, int[][] _mask,
            int[][] _pieMask,
            double[] _pieR, double[] _pieG, double[] _pieB,
            int[] _pieNums) {
        int center = ((_sy * w) + _sx);
        for (int y = 0; y < _dh; y++) {
            for (int x = 0; x < _dw; x++) {
                int cindex = (y * _dw) + x;
                int offset = _mask[x][y];
                if (offset == Integer.MAX_VALUE) {
                    continue;
                }
                int index = center + offset;
                if (index < 0) {
                    _copy[cindex] = 0;
                } else if (index >= pixels.length) {
                    _copy[cindex] = 0;
                } else {
                    int pixel = pixels[index];
                    _copy[cindex] = pixel;

                    int r = (pixel >> 16) & 0xFF;
                    int g = (pixel >> 8) & 0xFF;
                    int b = (pixel >> 0) & 0xFF;
                    int pieSliceIndex = _pieMask[x][y];
                    _pieR[pieSliceIndex] += r;
                    _pieG[pieSliceIndex] += g;
                    _pieB[pieSliceIndex] += b;
                    _pieNums[pieSliceIndex]++;

                }
            }
        }
    }

    /**
     *
     * @param _sx
     * @param _sy
     * @param _copy
     * @param _size
     */
    public void copySquare(
            int _sx, int _sy, int[] _copy, int _size) {
        int center = ((_sy * w) + _sx);
        int hs = _size / 2;
        for (int fy = _sy - hs, ty = 0; fy < _sy + hs; fy++, ty++) {
            for (int fx = _sx - hs, tx = 0; fx < _sx + hs; fx++, tx++) {
                int findex = (fy * w) + fx;
                int tindex = (ty * _size) + tx;
                _copy[tindex] = pixels[findex];
            }
        }
    }

    /**
     *
     * @param _sx
     * @param _sy
     * @param _copy
     * @param _size
     */
    public void copyCircle(
            int _sx, int _sy, int[] _copy, int _size) {
        circle(_sx, _sy, _copy, _size, new Operator() {

            public void op(int[] _a, int _ai, int[] _b, int _bi, Object _options) {
                _b[_bi] = _a[_ai];
            }
        }, null);
    }

    /**
     *
     * @param _sx
     * @param _sy
     * @param _copy
     * @param _size
     */
    public void writeCircle(
            int _sx, int _sy, int[] _copy, int _size) {
        circle(_sx, _sy, _copy, _size, new Operator() {

            public void op(int[] _a, int _ai, int[] _b, int _bi, Object _options) {
                _a[_ai] = _b[_bi];
            }
        }, null);
    }

    /**
     *
     * @param _sx
     * @param _sy
     * @param _copy
     * @param _size
     * @param _alpha
     * @param _featherAlpha
     * @param _composite
     */
    public void compositeCircle(
            int _sx, int _sy, int[] _copy, int _size, final float _alpha, final double _featherAlpha, final IComposite _composite) {
        circle(_sx, _sy, _copy, _size, new Operator() {

            public void op(int[] _a, int _ai, int[] _b, int _bi, Object _options) {
                float alpha = _alpha;
                MutableDouble distanceFromCenter = (MutableDouble) _options;
                if (_featherAlpha != 0) {
                    alpha *= 1f - (float) (Math.pow(distanceFromCenter.doubleValue(), _featherAlpha));
                }
                if (_ai < 0 || _ai >= _a.length) {
                    return;
                }
                if (_bi < 0 || _bi >= _b.length) {
                    return;
                }
                int pixel = _composite.composite(_b[_bi], alpha, _a[_ai]);
                _a[_ai] = pixel;
            }
        }, null);
    }

    /**
     *
     * @param _sx
     * @param _sy
     * @param _copy
     * @param _size
     * @param _alpha
     * @param _featherAlpha
     * @param _composite
     */
    public void compositeSquare(
            int _sx, int _sy, int[] _copy, int _size, final float _alpha, final double _featherAlpha, final IComposite _composite) {

        int center = ((_sy * w) + _sx);
        int hs = _size / 2;
        for (int fy = _sy - hs, ty = 0; fy < _sy + hs; fy++, ty++) {
            for (int fx = _sx - hs, tx = 0; fx < _sx + hs; fx++, tx++) {
                int findex = (fy * w) + fx;
                int tindex = (ty * _size) + tx;

                float alpha = _alpha;
                if (_featherAlpha != 0) {
                    int d = UMath.distance(fx, fy, _sx, _sy);
                    if (d > hs) {
                        continue;
                    }
                    float pd = (float) d / (float) (hs);
                    alpha *= 1f - (float) (Math.pow(pd, _featherAlpha));
                }
                int pixel = _composite.composite(_copy[tindex], alpha, pixels[findex]);
                pixels[findex] = pixel;
                _copy[tindex] = pixels[findex];
            }
        }
    }

    /**
     *
     * @param _image
     * @param _sliceR
     * @param _sliceG
     * @param _sliceB
     * @param _sliceNums
     * @param _ringR
     * @param _ringG
     * @param _ringB
     * @param _ringNums
     */
    public static void copySliceRings(
            IntImage _image,
            double[] _sliceR, double[] _sliceG, double[] _sliceB, int[] _sliceNums,
            double[] _ringR, double[] _ringG, double[] _ringB, int[] _ringNums) {
        int numSlices = _sliceR.length;
        int numRings = _ringR.length;

        int dim = _image.w;
        if (_image.h != dim) {
            throw new RuntimeException("pieRingHistogram image must be a square");
        }
        double center = dim / 2;
        int[] pixels = _image.pixels;
        for (int y = 0; y < dim; y++) {
            int yindex = y * dim;
            for (int x = 0; x < dim; x++) {
                int ring = (int) ((UMath.distance(center, center, x, y) / center) * numRings);
                int slice = (int) ((UMath.angle(center, center, x, y) / (Math.PI * 2)) * numSlices);

                if (ring >= numRings) {
                    continue;
                }
                if (slice >= numSlices) {
                    continue;
                }
                int pixel = pixels[yindex + x];
                int r = (pixel >> 16) & 0xFF;
                int g = (pixel >> 8) & 0xFF;
                int b = (pixel >> 0) & 0xFF;

                _sliceR[slice] += r;
                _sliceG[slice] += g;
                _sliceB[slice] += b;
                _sliceNums[slice]++;

                _ringR[ring] += r;
                _ringG[ring] += g;
                _ringB[ring] += b;
                _ringNums[ring]++;
            }
        }
        for (int slice = 0; slice < numSlices; slice++) {
            _sliceR[slice] /= _sliceNums[slice];
            _sliceG[slice] /= _sliceNums[slice];
            _sliceB[slice] /= _sliceNums[slice];
        }
        for (int ring = 0; ring < numRings; ring++) {
            _ringR[ring] /= _ringNums[ring];
            _ringG[ring] /= _ringNums[ring];
            _ringB[ring] /= _ringNums[ring];
        }
    }

    // return[0] = max difference; 0=>min (identical); 0=>max (all white vs all black)
    // return[1] = radians of best division for max difference
    /**
     *
     * @param _AsliceR
     * @param _AsliceG
     * @param _AsliceB
     * @param _AsliceNums
     * @param _AringR
     * @param _AringG
     * @param _AringB
     * @param _AringNums
     * @param _BsliceR
     * @param _BsliceG
     * @param _BsliceB
     * @param _BsliceNums
     * @param _BringR
     * @param _BringG
     * @param _BringB
     * @param _BringNums
     * @return
     */
    final public static double sliceRingCorrelation(
            double[] _AsliceR, double[] _AsliceG, double[] _AsliceB, int[] _AsliceNums,
            double[] _AringR, double[] _AringG, double[] _AringB, int[] _AringNums,
            double[] _BsliceR, double[] _BsliceG, double[] _BsliceB, int[] _BsliceNums,
            double[] _BringR, double[] _BringG, double[] _BringB, int[] _BringNums) {

        /*
        double unorderedCorrelation = 0;
        unorderedCorrelation = UDouble.maximizedUnorderedCorrelation(_AsliceR,_BsliceR);
        unorderedCorrelation = UDouble.maximizedUnorderedCorrelation(_AsliceG,_BsliceG);
        unorderedCorrelation = UDouble.maximizedUnorderedCorrelation(_AsliceB,_BsliceB);
        unorderedCorrelation /= 3;
         */

        double orderedCorrelation = 0;
        orderedCorrelation += UMath.correlationCoefficient(_AsliceR, _BsliceR);
        orderedCorrelation += UMath.correlationCoefficient(_AsliceG, _BsliceG);
        orderedCorrelation += UMath.correlationCoefficient(_AsliceB, _BsliceB);
        orderedCorrelation += UMath.correlationCoefficient(_AringR, _BringR);
        orderedCorrelation += UMath.correlationCoefficient(_AringG, _BringG);
        orderedCorrelation += UMath.correlationCoefficient(_AringB, _BringB);
        orderedCorrelation /= 6;

        //return (unorderedCorrelation+orderedCorrelation)/2d;
        return orderedCorrelation;
    }

    /**
     *
     * @param _sx
     * @param _sy
     * @param _copy
     * @param _size
     * @param _inside
     * @param _outside
     */
    public void circle(
            int _sx, int _sy, int[] _copy, int _size, Operator _inside, Operator _outside) {
        int center = ((_sy * w) + _sx);
        float scenter = _size / 2f;

        MutableDouble distanceFromCenter = new MutableDouble(0);
        for (int y = 0; y < _size; y++) {
            for (int x = 0; x < _size; x++) {
                int cindex = (y * _size) + x;
                double distance = UMath.distance(scenter, scenter, x, y);//-0.5d;// round down

                if (distance > scenter) {
                    continue;
                } else {
                    int offset = (int) (((y - scenter) * w) + (x - scenter));
                    int index = center + offset;
                    if (index < 0) {
                        _copy[cindex] = 0;
                    } else if (index >= pixels.length) {
                        _copy[cindex] = 0;
                    } else {
                        distanceFromCenter.set((double) distance / (double) scenter);
                        _inside.op(pixels, index, _copy, cindex, distanceFromCenter);
                    }
                }
            }
        }
    }

    // return[0] = max difference; 0=>min (identical); 0=>max (all white vs all black)
    // return[1] = radians of best division for max difference
    /**
     *
     * @param _results
     * @param _pieR
     * @param _pieG
     * @param _pieB
     * @param _pieNums
     * @param _angularDistinctions
     * @return
     */
    final public static double[] maxDeltaSemicircles(
            double[] _results,
            double[] _pieR, double[] _pieG, double[] _pieB,
            int[] _pieNums,
            int _angularDistinctions) {

        if (_results == null) {
            _results = new double[20];
        }
        _results[0] = -1;

        float[] hsb1 = new float[3];
        float[] hsb2 = new float[3];
        float[] hsb3 = new float[3];
        float[] hsb4 = new float[3];

        double angularStep = (Math.PI * 2) / _angularDistinctions;
        int halfAD = _angularDistinctions / 2;
        int quarterAD = _angularDistinctions / 4;
        int si = 0, i = 0;

        for (int ad = 0; ad < halfAD; ad++) {
            si = ad;
            float sumr1 = 0, sumg1 = 0, sumb1 = 0;
            int num1 = 0;
            for (int h = 0; h < quarterAD; h++, si++) {
                i = si;
                if (i >= _angularDistinctions) {
                    i -= _angularDistinctions;
                }
                sumr1 += _pieR[i];
                sumg1 += _pieG[i];
                sumb1 += _pieB[i];
                num1 += _pieNums[i];
            }
            float sumr2 = 0, sumg2 = 0, sumb2 = 0;
            int num2 = 0;
            for (int h = 0; h < quarterAD; h++, si++) {
                i = si;
                if (i >= _angularDistinctions) {
                    i -= _angularDistinctions;
                }
                sumr2 += _pieR[i];
                sumg2 += _pieG[i];
                sumb2 += _pieB[i];
                num2 += _pieNums[i];
            }
            float sumr3 = 0, sumg3 = 0, sumb3 = 0;
            int num3 = 0;
            for (int h = 0; h < quarterAD; h++, si++) {
                i = si;
                if (i >= _angularDistinctions) {
                    i -= _angularDistinctions;
                }
                sumr3 += _pieR[i];
                sumg3 += _pieG[i];
                sumb3 += _pieB[i];
                num3 += _pieNums[i];
            }
            float sumr4 = 0, sumg4 = 0, sumb4 = 0;
            int num4 = 0;
            for (int h = 0; h < quarterAD; h++, si++) {
                i = si;
                if (i >= _angularDistinctions) {
                    i -= _angularDistinctions;
                }
                sumr4 += _pieR[i];
                sumg4 += _pieG[i];
                sumb4 += _pieB[i];
                num4 += _pieNums[i];
            }


            sumr1 /= num1;
            sumg1 /= num1;
            sumb1 /= num1;
            sumr2 /= num2;
            sumg2 /= num2;
            sumb2 /= num2;
            sumr3 /= num3;
            sumg3 /= num3;
            sumb3 /= num3;
            sumr4 /= num4;
            sumg4 /= num4;
            sumb4 /= num4;

            AColor.RGBtoHSB((int) sumr1, (int) sumg1, (int) sumb1, hsb1);
            AColor.RGBtoHSB((int) sumr2, (int) sumg2, (int) sumb2, hsb2);
            AColor.RGBtoHSB((int) sumr3, (int) sumg3, (int) sumb3, hsb3);
            AColor.RGBtoHSB((int) sumr4, (int) sumg4, (int) sumb4, hsb4);

            float adeltah = (hsb1[0] + hsb2[0]) - (hsb3[0] + hsb4[0]);
            float adeltas = (hsb1[1] + hsb2[1]) - (hsb3[1] + hsb4[1]);
            float adeltab = (hsb1[2] + hsb2[2]) - (hsb3[2] + hsb4[2]);

            double adelta = (Math.abs(adeltah) + Math.abs(adeltas) + Math.abs(adeltab)) / 3;
            if (adelta > 1) {
                adelta = 1;
            }


            double bdeltah = (hsb4[0] + hsb1[0]) - (hsb2[0] + hsb3[0]);
            double bdeltas = (hsb4[1] + hsb1[1]) - (hsb2[1] + hsb3[1]);
            double bdeltab = (hsb4[2] + hsb1[2]) - (hsb2[2] + hsb3[2]);

            double bdelta = (Math.abs(bdeltah) + Math.abs(bdeltas) + Math.abs(bdeltab)) / 3;
            if (bdelta > 1) {
                bdelta = 1;
            }

            double rank = 0;
            if (bdelta < adelta) {
                rank = adelta - bdelta;
            }
            if (rank > 1) {
                System.out.println("Error:" + rank);
            }
            if (rank >= _results[0]) {
                _results[0] = rank;
                _results[1] = (ad * angularStep);

                _results[2] = (sumr1 + sumr2) / 2;
                _results[3] = (sumg1 + sumg2) / 2;
                _results[4] = (sumb1 + sumb2) / 2;

                _results[5] = (sumr3 + sumr4) / 2;
                _results[6] = (sumg3 + sumg4) / 2;
                _results[7] = (sumb3 + sumb4) / 2;

            }
        }
        return _results;
    }

    /**
     *
     * @param _image
     * @param _numRings
     * @param _numSlices
     * @return
     */
    public static Object pieRingHistogram(IntImage _image, int _numRings, int _numSlices) {
        int numChannels = 6;
        int numLevels = 64; //!! see below for 6 lines of dependency

        float[][][] result = new float[_numRings * _numSlices][numChannels][numLevels];
        int[] num = new int[_numRings * _numSlices];

        int dim = _image.w;
        if (_image.h != dim) {
            throw new RuntimeException("pieRingHistogram image must be a square");
        }
        double center = dim / 2;
        int[] pixels = _image.pixels;

        for (int y = 0; y < dim; y++) {
            int yindex = y * dim;

            for (int x = 0; x < dim; x++) {

                int ring = (int) ((UMath.distance(center, center, x, y) / center) * _numRings);
                int slice = (int) ((UMath.angle(center, center, x, y) / (Math.PI * 2)) * _numSlices);

                if (ring >= _numRings) {
                    continue;
                }
                if (slice >= _numSlices) {
                    continue;
                }
                int rs = ring * _numSlices + slice;
                int pix = pixels[yindex + x];


                //!! BEGIN: BEWARE! hardcoded for numLevels = 64 for performance reasons! 				
                int R = ((pix >> 16) & 0xFF) >> 2; // >>2 because 256/4=64 and 64 is desired number of rgb distinctions

                int G = ((pix >> 8) & 0xFF) >> 2;
                int B = ((pix >> 0) & 0xFF) >> 2;

                int Y = R >> 3;
                Y <<= 3;
                Y += G % 8;	// keeps good separation of two color channels within one number < 64

                int M = R >> 3;
                M <<= 3;
                M += B % 8;
                int C = G >> 3;
                C <<= 3;
                C += B % 8;
                //!! END: BEWARE! hardcoded for numLevels = 64 for performance reasons! 				


                result[rs][0][R]++;
                result[rs][1][G]++;
                result[rs][2][B]++;
                result[rs][3][Y]++;
                result[rs][4][M]++;
                result[rs][5][C]++;
                num[rs]++;
            }
        }
        // normalize 0-1 so any size images can be compared using correlatePieRingHistograms
        for (int r = 0; r < _numRings; r++) {
            int rx = r * _numSlices;

            for (int s = 0; s < _numSlices; s++) {
                int rs = rx + s;
                int cnt = num[rs];

                for (int c = 0; c < numChannels; c++) {

                    for (int v = 0; v < numLevels; v++) { // normalize so any size images can be compared

                        result[rs][c][v] /= cnt;
                    }
                }
            }
        }
        return result;
    }

    /**
     *
     * @param _a
     * @param _b
     * @param _numRings
     * @param _numSlices
     * @param _numChannels
     * @param _numLevels
     * @return
     */
    public static double correlatePieRingHistograms(
            Object _a, // must be a pieRingHistogram
            Object _b, // must be a pieRingHistogram
            int _numRings, // probably a small number around 10
            int _numSlices, // probably a small number around 10
            int _numChannels, // r,g,b,y,m,c exist in pieRingHistogram, so use 6 for all, or 3 for just rgb
            int _numLevels // i.e. max=256 but we are trying with 256/4 = 64 levels for every color
            ) {
        float[][][] a = (float[][][]) _a;
        float[][][] b = (float[][][]) _b;
        double correlate = 0;
        int num = 0;

        for (int r = 0; r < _numRings; r++) {
            int rx = r * _numSlices;

            for (int s = 0; s < _numSlices; s++) {
                int rs = rx + s;

                for (int c = 0; c < _numChannels; c++) {

                    for (int v = 0; v < _numLevels; v++) {

                        float delta = a[rs][c][v] - b[rs][c][v];
                        if (delta < 0) {
                            delta = -delta;
                        }
                        correlate += delta;
                        num++;
                    }
                }
            }
        }
        return correlate / num;
    }

    /*
     * 
    public void switchColors(AColor _is,AColor _with) {
    
    int w = (int)width;
    int h = (int)height;
    BufferedImage buffer = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    
    Graphics g = buffer.getGraphics();
    if (image != null) g.drawImage(image,0,0,null);
    g.dispose();
    
    int[] rgbArray = buffer.getRGB(0,0,w,h,null,0,w);
    int is = _is.get8BitRGBA();
    int with = _with.get8BitRGBA();
    for (int i=0;i<rgbArray.length;i++) if (rgbArray[i] == is) rgbArray[i] = with;
    buffer.setRGB(0,0,w,h,rgbArray,0,w);
    
    image = buffer;
    }*/
    /**
     *
     * @param _image
     * @return
     */
    public static int[] grabPixels(Image _image) {
        try {
            if (_image == null) {
                return new int[0];
            }
            int w = (int) _image.getWidth(null);
            int h = (int) _image.getHeight(null);
            int[] pixels = new int[w * h];

            try {
                PixelGrabber pg = new PixelGrabber(_image, 0, 0, w, h, pixels, 0, w);
                pg.grabPixels();
                if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
                    throw new IOException("failed to load image contents");
                }
                return pixels;
            } catch (InterruptedException e) {
                throw new IOException("image load interrupted");
            }
        } catch (Exception e) {
            return new int[0];
        }
    }

    /**
     *
     * @param _image
     * @param _row
     * @return
     * @throws Exception
     */
    public int[] grabRowOfPixels(Image _image, int _row) throws Exception {
        if (_image == null) {
            return new int[0];
        }
        int w = (int) _image.getWidth(null);
        int h = (int) _image.getHeight(null);
        int[] _pixels = new int[w];

        try {
            PixelGrabber pg = new PixelGrabber(_image, 0, _row, w, 1, _pixels, 0, 1);
            pg.grabPixels();
            if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
                throw new IOException("failed to load image contents");
            }
            return _pixels;
        } catch (InterruptedException e) {
            throw new IOException("image load interrupted");
        }
    }

    /**
     *
     * @param _image
     * @param _colum
     * @return
     * @throws Exception
     */
    public int[] grabColumOfPixels(Image _image, int _colum) throws Exception {
        if (_image == null) {
            return new int[0];
        }
        int w = (int) _image.getWidth(null);
        int h = (int) _image.getHeight(null);
        int[] _pixels = new int[h];

        try {
            PixelGrabber pg = new PixelGrabber(_image, _colum, 0, 1, h, _pixels, 0, 1);
            pg.grabPixels();
            if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
                throw new IOException("failed to load image contents");
            }
            return pixels;
        } catch (InterruptedException e) {
            throw new IOException("image load interrupted");
        }
    }
}

