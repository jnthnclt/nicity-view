/*
 * AColor.java.java
 *
 * Created on 01-03-2010 01:29:41 PM
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
package colt.nicity.view.core;

import colt.nicity.view.adaptor.VS;
import colt.nicity.core.lang.ASetObject;
import colt.nicity.core.lang.UDouble;
import colt.nicity.core.lang.UFloat;
import colt.nicity.core.lang.UInteger;
import colt.nicity.core.lang.URandom;
import colt.nicity.core.memory.SoftIndex;
import java.awt.Color;

/**
 *
 * @author Administrator
 */
public class AColor extends ASetObject {

    /**
     *
     */
    public final static AColor offWhite = new AColor(250, 250, 255);
    /**
     *
     */
    public final static AColor lightBrown = new AColor(255, 160, 80);
    /**
     *
     */
    public final static AColor brown = new AColor(215, 120, 40);
    /**
     *
     */
    public final static AColor white = new AColor(255, 255, 255);
    /**
     *
     */
    public final static AColor lightestGray = new AColor(220, 220, 220);
    /**
     *
     */
    public final static AColor lightGray = new AColor(192, 192, 192);
    /**
     *
     */
    public final static AColor gray = new AColor(128, 128, 128);
    /**
     *
     */
    public final static AColor darkGray = new AColor(64, 64, 64);
    /**
     *
     */
    public final static AColor black = new AColor(0, 0, 0);
    /**
     *
     */
    public final static AColor red = new AColor(255, 0, 0);
    /**
     *
     */
    public final static AColor lightBlue = new AColor(136, 197, 255);
    /**
     *
     */
    public final static AColor lightRed = new AColor(255, 225, 225);
    /**
     *
     */
    public final static AColor lightRedGray = new AColor(255, 192, 192);
    /**
     *
     */
    public final static AColor lightPurple = new AColor(215, 128, 255);
    /**
     *
     */
    public final static AColor purple = new AColor(175, 0, 255);
    /**
     *
     */
    public final static AColor darkPurple = new AColor(88, 0, 128);
    /**
     *
     */
    public final static AColor pink = new AColor(255, 175, 255);
    /**
     *
     */
    public final static AColor aqua = new AColor(175, 255, 255);
    /**
     *
     */
    public final static AColor orange = new AColor(255, 200, 0);
    /**
     *
     */
    public final static AColor yellowOrange = new AColor(255, 227, 0);
    /**
     *
     */
    public final static AColor yellow = new AColor(255, 255, 0);
    /**
     *
     */
    public final static AColor green = new AColor(0, 255, 0);
    /**
     *
     */
    public final static AColor magenta = new AColor(255, 0, 255);
    /**
     *
     */
    public final static AColor cyan = new AColor(0, 255, 255);
    /**
     *
     */
    public final static AColor lightBlueGray = new AColor(192, 192, 255);
    /**
     *
     */
    public final static AColor grayBlue = new AColor(128, 128, 192);
    /**
     *
     */
    public final static AColor blue = new AColor(0, 0, 255);
    /**
     *
     */
    public final static AColor darkBlue = new AColor(0, 0, 128);
    /**
     *
     */
    public final static AColor salmon = new AColor(255, 0, 0).desaturate(0.70f);
    /**
     *
     */
    public final static AColor[] colors = new AColor[]{
        lightBrown,
        brown,
        white,
        lightestGray,
        lightGray,
        gray,
        darkGray,
        black,
        red,
        lightRed,
        lightRedGray,
        lightPurple,
        purple,
        pink,
        aqua,
        orange,
        yellowOrange,
        yellow,
        green,
        magenta,
        cyan,
        lightBlueGray,
        lightBlue,
        grayBlue,
        blue,
        salmon
    };

    /**
     *
     * @return
     */
    static public final AColor randomNamed() {
        return AColor.colors[URandom.rand(AColor.colors.length)];
    }
    // fast static color utilities
    final static double[] rs = new double[]{1, 0, 1};
    final static double[] gs = new double[]{0.5, 1, 0.5, 0, 0.5};
    final static double[] bs = new double[]{0, 1, 0};
    static int[] v255 = null;

    static {
        v255 = new int[360];
        for (int i = 0; i < 180; i++) {
            double v = i;
            v /= 180.0;
            v *= 255;
            v += .5;
            v255[i] = (int) v;
            v255[359 - i] = (int) v;
        }
    }

    /**
     *
     * @param _degree
     * @return
     */
    public final static int intForDegree(int _degree) { // faster than using hue from HSB model
        int i;
        int a = 255;
        i = (_degree + 180) % 360;
        int r = v255[i];
        i = (_degree + 90) % 360;
        int g = v255[i];
        i = (_degree + 0) % 360;
        int b = v255[i];

        return ((a << 24) | (r << 16) | (g << 8) | (b << 0));
    }

    /**
     *
     * @param _degree
     * @return
     */
    public final static int intForDegree2(int _degree) { // faster than using hue from HSB model
        if (_degree < 0) {
            _degree *= -1;
        }
        double d = (_degree) % 360;
        d /= 360;

        int a = 255;
        int r = (int) (UDouble.linearInterpolation(rs, d) * 255);
        int g = (int) (UDouble.linearInterpolation(gs, d) * 255);
        int b = (int) (UDouble.linearInterpolation(bs, d) * 255);

        return ((a << 24) | (r << 16) | (g << 8) | (b << 0));
    }

    /**
     *
     * @param _radians
     * @return
     */
    public final static int intForRadians(double _radians) { // faster than using hue from HSB model
        if (_radians < 0) {
            _radians *= -1;
        }
        double d = _radians % (Math.PI * 2);
        d /= (Math.PI * 2);

        int a = 255;
        int r = (int) (UDouble.linearInterpolation(rs, d) * 255);
        int g = (int) (UDouble.linearInterpolation(gs, d) * 255);
        int b = (int) (UDouble.linearInterpolation(bs, d) * 255);

        return ((a << 24) | (r << 16) | (g << 8) | (b << 0));
    }

    /**
     *
     * @param _c
     * @return
     */
    public final static double radiansForInt(int _c) { // faster than using hue from HSB model
        double r = (double) ((_c >> 16) & 0xFF);
        double g = (double) ((_c >> 8) & 0xFF);
        double b = (double) ((_c >> 0) & 0xFF);
        if (g > 127) {
            return (b / 255d) * Math.PI;
        } else {
            return Math.PI + ((r / 255d) * Math.PI);
        }
    }

    /**
     *
     * @param _c
     * @return
     */
    public final static int degreeForInt(int _c) { // faster than using hue from HSB model
        double g = (double) ((_c >> 8) & 0xFF);
        double b = (double) ((_c >> 0) & 0xFF);
        if (g > 127) {
            return (int) ((b * 180d) / 255d);
        } else {
            return (int) (360d - ((b * 180d) / 255d));
        }

    }

    /**
     *
     * @param _c1
     * @param _c2
     * @param _p
     * @return
     */
    public final static int linearInterpolation(int _c1, int _c2, double _p) { // 0=>lowest; 1=>highest (identical)
        int a1 = (_c1 >> 24) & 0xFF;
        int r1 = (_c1 >> 16) & 0xFF;
        int g1 = (_c1 >> 8) & 0xFF;
        int b1 = (_c1 >> 0) & 0xFF;
        int a2 = (_c2 >> 24) & 0xFF;
        int r2 = (_c2 >> 16) & 0xFF;
        int g2 = (_c2 >> 8) & 0xFF;
        int b2 = (_c2 >> 0) & 0xFF;

        return rgbaToInt(
                (int) UDouble.linearInterpolation(r1, r2, _p),
                (int) UDouble.linearInterpolation(g1, g2, _p),
                (int) UDouble.linearInterpolation(b1, b2, _p),
                (int) UDouble.linearInterpolation(a1, a2, _p));
    }

    /**
     *
     * @param _c1
     * @param _c2
     * @return
     */
    public final static double correlateRGB(int _c1, int _c2) { // 0=>lowest; 1=>highest (identical)
        int r1 = (_c1 >> 16) & 0xFF;
        int g1 = (_c1 >> 8) & 0xFF;
        int b1 = (_c1 >> 0) & 0xFF;

        int r2 = (_c2 >> 16) & 0xFF;
        int g2 = (_c2 >> 8) & 0xFF;
        int b2 = (_c2 >> 0) & 0xFF;

        double dr = r1 - r2;
        if (dr < 0) {
            dr = -dr;
        }
        double dg = g1 - g2;
        if (dg < 0) {
            dg = -dg;
        }
        double db = b1 - b2;
        if (db < 0) {
            db = -db;
        }
        return 1d - Math.sqrt((dr + db + dg) / 765);
    }//dg

    /**
     *
     * @param _c1
     * @param _c2
     * @return
     */
    public final static double correlateHSB(int _c1, int _c2) { // 0=>lowest; 1=>highest (identical)
        return correlateHSB(_c1, _c2, new float[3], new float[3]);
    }//dgG

    /**
     *
     * @param _c1
     * @param _hsb1
     * @return
     */
    public final static float[] hsb(int _c1, float[] _hsb1) {
        int r1 = (_c1 >> 16) & 0xFF;
        int g1 = (_c1 >> 8) & 0xFF;
        int b1 = (_c1 >> 0) & 0xFF;
        return RGBtoHSB(r1, g1, b1, _hsb1);
    }

    /**
     *
     * @param _c1
     * @param _c2
     * @param _hsb1
     * @param _hsb2
     * @return
     */
    public final static double correlateHSB(int _c1, int _c2, float[] _hsb1, float[] _hsb2) { // 0=>lowest; 1=>highest (identical)
        return correlateHSB(hsb(_c1, _hsb1), hsb(_c2, _hsb2));
    }//dg

    // As the Sat and Bri Diminish Hue Become less important
    /**
     *
     * @param _hsb1
     * @param _hsb2
     * @return
     */
    public final static double correlateHSB(float[] _hsb1, float[] _hsb2) { // 0=>lowest; 1=>highest (identical)
        double ave = 0;
        ave += Math.abs((_hsb1[0] * _hsb1[1]) - (_hsb2[0] * _hsb2[1]));
        ave += Math.abs((_hsb1[1] * _hsb1[2]) - (_hsb2[1] * _hsb2[2]));
        ave += Math.abs((_hsb1[2]) - (_hsb2[2]));
        return 1d - (ave / 3);
    }

    /**
     *
     * @param _hsb1
     * @param _hsb2
     * @param _weightH
     * @param _weightS
     * @param _weightB
     * @param _weightTotal
     * @return
     */
    public final static double correlateHSB(
            float[] _hsb1, float[] _hsb2,
            int _weightH, int _weightS, int _weightB, int _weightTotal) { // 0=>lowest; 1=>highest (identical)
        double ave = 0;
        ave += Math.abs(_hsb1[0] - _hsb2[0]) * _weightH;//!! hack
        ave += Math.abs(_hsb1[1] - _hsb2[1]) * _weightS;
        ave += Math.abs(_hsb1[2] - _hsb2[2]) * _weightB;
        //return 1d-Math.sqrt(ave/7);
        return 1d - (ave / _weightTotal);
    }
    /*

    We use the colour space (r,g,L) where L=R+G+B, r=R/L, and g=G/L. We divide the
    space into discrete bins. The resolution of the discretization of the three components do not need to
    be equal. There is no reason to make the first two different from each other, but, as discussed
    above, it can be advantageous to use a different value for the third. For all experiments we used 50
    divisions for (r,g), which is consistent with the discretization resolution used in this work for twodimensional
    Colour by Correlation. When specularities are added, as discussed shortly, the overall
    number of bins required for L increases. We express the resolution for L in terms of the number of
    bins devoted to matte reflection. For the experiments with generated data, we generally used a
    value for L which also leads to 50 divisions for matte reflection, but this is likely higher resolution
    than is necessary, and in fact, preliminary results indicate that a smaller number is likely better.
    Thus for the image data experiments, we used 25 divisions.
     */

    /**
     *
     * @param _c1
     * @param _c2
     * @return
     */
    public final static double correlateRGL(int _c1, int _c2) { // 0=>lowest; 1=>highest (identical)
        int r1 = (_c1 >> 16) & 0xFF;
        int g1 = (_c1 >> 8) & 0xFF;
        int b1 = (_c1 >> 0) & 0xFF;

        double l1 = r1 + g1 + b1;
        double rp1 = (double) r1 / l1;
        double gp1 = (double) g1 / l1;
        double bp1 = (double) b1 / l1;

        int r2 = (_c2 >> 16) & 0xFF;
        int g2 = (_c2 >> 8) & 0xFF;
        int b2 = (_c2 >> 0) & 0xFF;

        double l2 = r2 + g2 + b2;
        double rp2 = (double) r2 / l2;
        double gp2 = (double) g2 / l2;
        double bp2 = (double) b2 / l2;

        double ave = 0;
        ave += Math.abs(rp1 - rp2);
        ave += Math.abs(gp1 - gp2);
        ave += Math.abs(bp1 - bp2);

        return 1d - (ave / 3);
    }

    /**
     *
     * @param _c
     * @return
     */
    public final static int grayForInt(int _c) { // faster than using hue from HSB model
        int r = (_c >> 16) & 0xFF;
        int g = (_c >> 8) & 0xFF;
        int b = (_c >> 0) & 0xFF;
        int gray = r;
        if (g > gray) {
            gray = g;
        }
        if (b > gray) {
            gray = b;
        }
        return gray;
    }
    //ColorR = ColorS*AlphaS*FractionS + ColorD*AlphaD*FractionD
    //AlphaR = AlphaS*FractionS + AlphaD*FractionD

    /**
    Src OVer
    ColorR = ColorS + (1-AlphaS)*ColorD
    AlphaR = AlphaS + (1-AlphaS)*AlphaD
     *
     * @param sa
     * @param da
     * @param s
     * @param alpha
     * @param d
     * @return
     */
    public final static float colorSrcOver(float sa, float da, float s, float d, float alpha) {
        float v = (s * sa * alpha) + (d);
        if (v > 1) {
            v = 1;
        }
        return v;
    }

    /**
     *
     * @param sa
     * @param da
     * @param alpha
     * @return
     */
    public final static float alphaSrcOver(float sa, float da, float alpha) {
        float v = (sa * alpha) + (da);
        if (v > 1) {
            v = 1;
        }
        return v;
    }

    /**
     *
     * @param _color
     * @return
     */
    public final static double average(int _color) {
        double result =
                (double) (((_color >> 16) & 0xFF) + ((_color >> 8) & 0xFF) + ((_color >> 0) & 0xFF)) / 3d;
        return result;
    }

    /**
     *
     * @param _color
     * @param _channel
     * @return
     */
    public final static int intForChannel(int _color, int _channel) { // extract int 0-255
        int result = 0;
        if (_channel == 0) {
            result = (_color >> 24) & 0xFF;
        }
        if (_channel == 1) {
            result = (_color >> 16) & 0xFF;
        }
        if (_channel == 2) {
            result = (_color >> 8) & 0xFF;
        }
        if (_channel == 3) {
            result = (_color >> 0) & 0xFF;
        }
        return result;
    }

    /**
     *
     * @param _color
     * @return
     */
    public final static int[] channelsForInt(int _color) {
        return channelsForInt(_color, null);
    }

    /**
     *
     * @param _color
     * @param _channels
     * @return
     */
    public final static int[] channelsForInt(int _color, int[] _channels) {
        if (_channels == null || _channels.length < 4) {
            _channels = new int[4];
        }
        _channels[0] = (_color >> 24) & 0xFF;
        _channels[1] = (_color >> 16) & 0xFF;
        _channels[2] = (_color >> 8) & 0xFF;
        _channels[3] = (_color >> 0) & 0xFF;
        return _channels;
    }

    /**
     *
     * @param _rgba
     * @param _a
     * @param _h
     * @param _s
     * @param _b
     * @return
     */
    public static int modifyAHSB(int _rgba, double _a, double _h, double _s, double _b) {
        float[] hsb = new float[3];
        int[] c = new int[4];
        AColor.channelsForInt(_rgba, c);
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
        return color;
    }
    /**
     *
     */
    protected int color = 0; //
    /**
     *
     */
    protected Object cacheColor;

    /**
     *
     */
    public AColor() {
    }

    /**
     *
     * @param _rgba
     */
    public AColor(int _rgba) {
        color = _rgba;
    }

    /**
     *
     * @param _r
     * @param _g
     * @param _b
     */
    public AColor(int _r, int _g, int _b) {
        this(_r, _g, _b, 255);
    }

    /**
     *
     * @param _r
     * @param _g
     * @param _b
     * @param _a
     */
    public AColor(int _r, int _g, int _b, int _a) {
        color = rgba(_r, _g, _b, _a);
    }

    /**
     *
     * @param _hue
     * @param _sat
     * @param _bri
     */
    public AColor(float _hue, float _sat, float _bri) {
        this(HSBtoRGB(_hue, _sat, _bri));
    }

    /**
     *
     * @param _gray
     */
    public AColor(float _gray) {
        _gray = UFloat.range(_gray, 0, 1);
        int gray = (int) (255 * _gray);
        color = rgba(gray, gray, gray, 255);
    }

    /**
     *
     * @param _gray
     * @param _alpha
     */
    public AColor(float _gray, float _alpha) {
        _gray = UFloat.range(_gray, 0, 1);
        int gray = (int) (255 * _gray);
        color = rgba(gray, gray, gray, (int) (255 * _alpha));
    }

    /**
     *
     * @return
     */
    public Object hashObject() {
        return new Integer(color);
    }
    private static SoftIndex colorIndex = new SoftIndex();

    /**
     *
     * @param _rgba
     * @return
     */
    public static AColor color(int _rgba) {
        AColor color = (AColor) colorIndex.get(new Integer(_rgba));
        if (color != null) {
            return color;
        }
        color = new AColor(_rgba);
        colorIndex.set(color, color);
        return color;
    }

    /**
     *
     * @param _color
     */
    public void set8BitColor(int _color) {
        cacheColor = null;
        color = _color;
    }

    /**
     *
     * @param _precision
     * @return
     */
    public AColor color(double _precision) {
        int max = (int) (255 * _precision);
        float[] hsb = RGBtoHSB(getR(), getG(), getB(), null);
        hsb[0] = (float) ((int) (hsb[0] * max)) / (float) max;
        hsb[1] = (float) ((int) (hsb[1] * max)) / (float) max;
        hsb[2] = (float) ((int) (hsb[2] * max)) / (float) max;
        return new AColor(HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    /**
     *
     * @return
     */
    final public int getR() {
        return (color >> 16) & 0xFF;
    }

    /**
     *
     * @return
     */
    final public int getG() {
        return (color >> 8) & 0xFF;
    }

    /**
     *
     * @return
     */
    final public int getB() {
        return (color >> 0) & 0xFF;
    }

    /**
     *
     * @return
     */
    final public int getA() {
        return (color >> 24) & 0xFF;
    }

    /**
     *
     * @param _color
     * @return
     */
    static final public int getR(int _color) {
        return (_color >> 16) & 0xFF;
    }

    /**
     *
     * @param _color
     * @return
     */
    static final public int getG(int _color) {
        return (_color >> 8) & 0xFF;
    }

    /**
     *
     * @param _color
     * @return
     */
    static final public int getB(int _color) {
        return (_color >> 0) & 0xFF;
    }

    /**
     *
     * @param _color
     * @return
     */
    static final public int getA(int _color) {
        return (_color >> 24) & 0xFF;
    }

    /**
     *
     * @param _color
     * @return
     */
    static final public float getHue(int _color) {
        return RGBtoHSB(getR(_color), getG(_color), getB(_color), null)[0];
    }

    /**
     *
     * @param _color
     * @return
     */
    static final public float getSaturation(int _color) {
        return RGBtoHSB(getR(_color), getG(_color), getB(_color), null)[1];
    }

    /**
     *
     * @param _color
     * @return
     */
    static final public float getBrightness(int _color) {
        return RGBtoHSB(getR(_color), getG(_color), getB(_color), null)[2];
    }

    /**
     *
     * @param _hsb
     * @return
     */
    public float[] hsb(float[] _hsb) {
        return RGBtoHSB(getR(), getG(), getB(), _hsb);
    }

    /**
     *
     * @return
     */
    public float getHue() {
        return RGBtoHSB(getR(), getG(), getB(), null)[0];
    }

    /**
     *
     * @return
     */
    public float getSaturation() {
        return RGBtoHSB(getR(), getG(), getB(), null)[1];
    }

    /**
     *
     * @return
     */
    public float getBrightness() {
        return RGBtoHSB(getR(), getG(), getB(), null)[2];
    }

    /**
     *
     * @return
     */
    public float getAlpha() {
        return getA() / 255f;
    }

    /**
     *
     * @param _hue
     * @param _sat
     * @param _bri
     */
    public void setHSB(float _hue, float _sat, float _bri) {
        float a = getAlpha();
        color = HSBtoRGB(_hue, _sat, _bri);
        setAlpha(a);
    }

    /**
     *
     * @param _h
     */
    public void setHue(float _h) {
        setHSB(_h, getSaturation(), getBrightness());
    }

    /**
     *
     * @param _s
     */
    public void setSaturation(float _s) {
        setHSB(getHue(), _s, getBrightness());
    }

    /**
     *
     * @param _b
     */
    public void setBrightness(float _b) {
        setHSB(getHue(), getSaturation(), _b);
    }

    /**
     *
     * @param _a
     */
    public void setAlpha(float _a) {
        setA(_a);
    }

    /**
     *
     * @param _v
     */
    public void setR(float _v) {
        setR((int) (_v * 255));
    }

    /**
     *
     * @param _v
     */
    public void setG(float _v) {
        setG((int) (_v * 255));
    }

    /**
     *
     * @param _v
     */
    public void setB(float _v) {
        setB((int) (_v * 255));
    }

    /**
     *
     * @param _v
     */
    public void setA(float _v) {
        setA((int) (_v * 255));
    }

    /**
     *
     * @param _r
     */
    public void setR(int _r) {
        color = rgba(_r, getG(), getB(), getA());
    }

    /**
     *
     * @param _g
     */
    public void setG(int _g) {
        color = rgba(getR(), _g, getB(), getA());
    }

    /**
     *
     * @param _b
     */
    public void setB(int _b) {
        color = rgba(getR(), getG(), _b, getA());
    }

    /**
     *
     * @param _a
     */
    public void setA(int _a) {
        color = rgba(getR(), getG(), getB(), _a);
    }

    /**
     *
     * @param _color
     */
    public void setColor(AColor _color) {
        color = _color.color;
        cacheColor = null;
    }

    /**
     *
     * @param _color
     */
    public void setColor(int _color) {
        color = _color;
        cacheColor = null;
    }

    /**
     *
     * @param _r
     * @param _g
     * @param _b
     */
    public void setRGB(int _r, int _g, int _b) {
        color = rgba(_r, _g, _b, getA());
    }

    /**
     *
     * @param _r
     * @param _g
     * @param _b
     * @param _a
     */
    public void setRGBA(int _r, int _g, int _b, int _a) {
        color = rgba(_r, _g, _b, getA());
    }

    private final int rgba(int _r, int _g, int _b, int _a) {
        _r = UInteger.range(_r, 0, 255);
        _g = UInteger.range(_g, 0, 255);
        _b = UInteger.range(_b, 0, 255);
        _a = UInteger.range(_a, 0, 255);

        cacheColor = null;

        return ((_a & 0xFF) << 24)
                | ((_r & 0xFF) << 16)
                | ((_g & 0xFF) << 8)
                | ((_b & 0xFF) << 0);
    }

    /**
     *
     * @param _r
     * @param _g
     * @param _b
     * @param _a
     * @return
     */
    public static final int rgbaToInt(int _r, int _g, int _b, int _a) {

        return ((_a & 0xFF) << 24)
                | ((_r & 0xFF) << 16)
                | ((_g & 0xFF) << 8)
                | ((_b & 0xFF) << 0);
    }

    /**
     *
     * @param _gray
     * @return
     */
    public static final int grayToInt(float _gray) {
        int v = (int) (_gray * 255);
        return rgbaToInt(v, v, v, 255);
    }

    /**
     *
     * @param _h
     * @param _s
     * @param _b
     * @return
     */
    public static final int hsbToInt(float _h, float _s, float _b) {
        return HSBtoRGB(_h, _s, _b);
    }
    private static final float cFactor = 0.7f;

    /**
     *
     * @param _color
     * @param _hueDeviation
     * @param _satDeviation
     * @param _briDeviation
     * @return
     */
    public double isSimilar(AColor _color, float _hueDeviation, float _satDeviation, float _briDeviation) {
        float ah = getHue();
        float as = getSaturation();
        float ab = getBrightness();

        float bh = _color.getHue();
        float bs = _color.getSaturation();
        float bb = _color.getBrightness();

        double deviation = 0;
        float delta = 0;

        delta = Math.abs(ah - bh);
        if (delta > _hueDeviation) {
            return 1.0d;
        }
        deviation += delta / _hueDeviation;

        delta = Math.abs(as - bs);
        if (delta > _satDeviation) {
            return 1.0d;
        }
        deviation += delta / _satDeviation;

        delta = Math.abs(ab - bb);
        if (delta > _briDeviation) {
            return 1.0d;
        }
        deviation += delta / _briDeviation;

        return deviation / 3;
    }

    /**
     *
     * @param _color
     * @param _percentage
     * @return
     */
    public AColor interpolateShortestDistanceTo(AColor _color, float _percentage) {
        if (_color == null) {
            return null;
        }
        int rd = _color.getR() - getR();
        int gd = _color.getG() - getG();
        int bd = _color.getB() - getB();
        return new AColor(getR() + (int) (rd * _percentage), getG() + (int) (gd * _percentage), getB() + (int) (bd * _percentage));
    }

    /**
     *
     * @param _color
     * @param _percentage
     * @param _forward
     * @return
     */
    public AColor interpolateHueDistanceTo(AColor _color, float _percentage, boolean _forward) {
        if (_color == null) {
            return null;
        }
        float hd = getHue() - _color.getHue();
        float sd = getSaturation() - _color.getSaturation();
        float bd = getBrightness() - _color.getBrightness();
        return new AColor(getHue() + (hd * _percentage), getSaturation() + (sd * _percentage), getBrightness() + (bd * _percentage));
    }

    /**
     *
     * @param _a
     * @param _b
     * @param _percentage
     * @param _out
     * @return
     */
    public static float[] interpolateHueDistanceTo(
            float[] _a, float[] _b, float _percentage, float[] _out) {
        if (_out == null) {
            _out = new float[3];
        }
        float hd = _a[0] - _b[0];
        float sd = _a[1] - _b[1];
        float bd = _a[2] - _b[2];
        _out[0] = _a[0] + (hd * _percentage);
        _out[1] = _a[1] + (sd * _percentage);
        _out[2] = _a[2] + (bd * _percentage);
        return _out;
    }

    /**
     *
     * @return
     */
    public AColor brighter() {
        int r = getR();
        int g = getG();
        int b = getB();

        int i = (int) (1.0f / (1.0f - cFactor));
        if (r == 0 && g == 0 && b == 0) {
            return new AColor(i, i, i);
        }

        if (r > 0 && r < i) {
            r = i;
        }
        if (g > 0 && g < i) {
            g = i;
        }
        if (b > 0 && b < i) {
            b = i;
        }

        return new AColor((int) (r / cFactor), (int) (g / cFactor), (int) (b / cFactor));
    }

    /**
     *
     * @return
     */
    public AColor darker() {
        int r = getR();
        int g = getG();
        int b = getB();
        return new AColor((int) (r * cFactor), (int) (g * cFactor), (int) (b * cFactor));
    }

    private AColor role(int _index, float _amount) {
        float[] hsb = RGBtoHSB(getR(), getG(), getB(), null);
        hsb[_index] += _amount;
        if (hsb[_index] < 0.0f) {
            hsb[_index] = 0.0f;
        }
        if (hsb[_index] > 1.0f) {
            hsb[_index] = 1.0f;
        }
        return new AColor(HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    /**
     *
     * @param _amount
     * @return
     */
    public AColor lessHue(float _amount) {
        return role(0, -_amount);
    }

    /**
     *
     * @param _amount
     * @return
     */
    public AColor moreHue(float _amount) {
        return role(0, _amount);
    }

    /**
     *
     * @param _amount
     * @return
     */
    public AColor desaturate(float _amount) {
        return role(1, -_amount);
    }

    /**
     *
     * @param _amount
     * @return
     */
    public AColor saturate(float _amount) {
        return role(1, _amount);
    }

    /**
     *
     * @param _amount
     * @return
     */
    public AColor darken(float _amount) {
        return role(2, -_amount);
    }

    /**
     *
     * @param _amount
     * @return
     */
    public AColor lighten(float _amount) {
        return role(2, _amount);
    }

    /**
     *
     * @return
     */
    public AColor invert() {
        int r = 255 - getR();
        int g = 255 - getG();
        int b = 255 - getB();
        return new AColor((int) (r * cFactor), (int) (g * cFactor), (int) (b * cFactor));
    }

    /**
     *
     * @return
     */
    public AColor bw() {
        if ((getR() + getG() + getB()) > (127 * 3)) {
            return AColor.white;
        }
        return AColor.black;
    }

    /**
     *
     * @return
     */
    public int get8BitRGBA() {
        return color;
    }//(0xff000000 | color); }

    /**
     *
     * @return
     */
    public int intValue() {
        return color;
    }//(0xff000000 | color); }

    /**
     *
     * @param _color
     * @return
     */
    static public double[] rgbDoubles(int _color) {
        int a = (_color >> 24) & 0xFF;
        int r = (_color >> 16) & 0xFF;
        int g = (_color >> 8) & 0xFF;
        int b = (_color >> 0) & 0xFF;
        return new double[]{(double) r / 255d, (double) g / 255d, (double) b / 255d};
    }

    /**
     *
     * @param _color
     * @return
     */
    static public double[] rgbaDoubles(int _color) {
        int a = (_color >> 24) & 0xFF;
        int r = (_color >> 16) & 0xFF;
        int g = (_color >> 8) & 0xFF;
        int b = (_color >> 0) & 0xFF;
        return new double[]{(double) r / 255d, (double) g / 255d, (double) b / 255d, (double) a / 255d};
    }

    /**
     *
     * @param _rgb
     * @return
     */
    static public int rgbDoubles(double[] _rgb) {
        return AColor.rgbaToInt((int) (255 * _rgb[0]), (int) (255 * _rgb[1]), (int) (255 * _rgb[2]), 255);
    }

    /**
     *
     * @param _rgb
     * @return
     */
    static public int rgbaDoubles(double[] _rgb) {
        return AColor.rgbaToInt((int) (255 * _rgb[0]), (int) (255 * _rgb[1]), (int) (255 * _rgb[2]), (int) (255 * _rgb[3]));
    }

    /**
     *
     * @param _hsba
     * @return
     */
    static public int hsbaDoubles(double[] _hsba) {
        int color = hsbToInt((float) _hsba[0], (float) _hsba[1], (float) _hsba[2]);
        int a = (int) (255 * _hsba[3]);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return rgbaToInt(r, g, b, a);
    }

    /**
     *
     * @param _a
     * @param _b
     * @param _p
     * @return
     */
    static public int rgbMorph(int _a, int _b, double _p) {
        return AColor.rgbDoubles(UDouble.linearInterpolation(AColor.rgbDoubles(_a), AColor.rgbDoubles(_b), _p));
    }

    /**
     *
     * @return
     */
    public Object getColor() {
        if (cacheColor != null) {
            return cacheColor;
        }
        cacheColor = VS.systemColor(this);
        return cacheColor;
    }

    /**
     *
     * @return
     */
    public String getHexRGB() {
        String hexColor = UInteger.toString(getR(), 16, 2) + UInteger.toString(getG(), 16, 2) + UInteger.toString(getB(), 16, 2);
        return hexColor;
    }

    @Override
    public String toString() {
        return getR() + " " + getG() + " " + getB() + " " + getA();
    }

    /**
     *
     * @param _zeroToOne
     * @return
     */
    public static AColor getWarmToCool(double _zeroToOne) {
        float h = (float) (_zeroToOne * 0.7);
        return new AColor(h, 1f, 1f);
    }

    /**
     *
     * @param _instance
     * @return
     */
    public static AColor getHashColor(Object _instance) {
        int h = URandom.rand(new URandom.Seed(_instance.hashCode()));

        int b = (h % 222) + 32;
        h >>= 2;
        int g = (h % 222) + 32;
        h >>= 4;
        int r = (h % 222) + 32;
        h >>= 8;

        return new AColor(r, g, b);
    }

    /**
     *
     * @param _instance
     * @param _min
     * @return
     */
    public static AColor getHashGray(Object _instance, float _min) {
        int h = _instance.hashCode();
        if (h < 0) {
            h = -h;
        }
        return new AColor(_min + ((1f - _min) * ((float) h / (float) Integer.MAX_VALUE)));
    }

    /**
     *
     * @param _instance
     * @return
     */
    public static AColor getHashSolid(Object _instance) {
        int h = URandom.rand(new URandom.Seed(_instance.hashCode()));

        int b = (h % 96) + 128;
        h >>= 2;
        int g = (h % 96) + 128;
        h >>= 4;
        int r = (h % 96) + 128;
        h >>= 8;

        AColor color = null;
        int _12 = (h % 2) + 1;
        if (_12 == 1) {
            int _123 = (h % 3) + 1;
            switch (_123) {
                case 1:
                    color = new AColor(r, 64, 64);
                    break;
                case 2:
                    color = new AColor(64, g, 64);
                    break;
                default:
                    color = new AColor(64, 64, b);
            }

        } else {
            int _123 = (h % 3) + 1;
            switch (_123) {
                case 1:
                    color = new AColor(r, g, 64);
                    break;
                case 2:
                    color = new AColor(r, 64, b);
                    break;
                default:
                    color = new AColor(64, g, b);
            }
        }
        return new AColor(r, g, b);
    }

    /**
     *
     * @param _min
     * @param _max
     * @return
     */
    public static AColor randomPastel(float _min, float _max) {
        float r = _min + ((255f - _max) * (URandom.rand(255) / 255f));
        float g = _min + ((255f - _max) * (URandom.rand(255) / 255f));
        float b = _min + ((255f - _max) * (URandom.rand(255) / 255f));
        return new AColor((int) r, (int) g, (int) b);
    }

    /**
     *
     * @param _min
     * @param _max
     * @return
     */
    public static AColor randomSolid(float _min, float _max) {
        int r = (int) (_min + ((255f - _max) * (URandom.rand(255) / 255f)));
        int g = (int) (_min + ((255f - _max) * (URandom.rand(255) / 255f)));
        int b = (int) (_min + ((255f - _max) * (URandom.rand(255) / 255f)));
        AColor color = null;
        int _12 = URandom.rand(2) + 1;
        if (_12 == 1) {
            int _123 = URandom.rand(3) + 1;
            switch (_123) {
                case 1:
                    color = new AColor(r, 0, 0);
                    break;
                case 2:
                    color = new AColor(0, g, 0);
                    break;
                case 3:
                    color = new AColor(0, 0, b);
                    break;
                default:
                    color = new AColor(r, g, b);
            }

        } else {
            int _123 = URandom.rand(3) + 1;
            switch (_123) {
                case 1:
                    color = new AColor(r, g, 0);
                    break;
                case 2:
                    color = new AColor(r, 0, b);
                    break;
                case 3:
                    color = new AColor(0, g, b);
                    break;
                default:
                    color = new AColor(r, g, b);
            }
        }
        return color;
    }

    /**
     *
     * @param _color
     * @return
     */
    public AColor multiply(AColor _color) {
        if (_color == null) {
            return new AColor(color);
        }
        return new AColor((getR() * _color.getR()) / 2, (getG() * _color.getG()) / 2, (getB() * _color.getB()) / 2);
    }

    /**
     *
     * @param _color
     * @return
     */
    public AColor darken(AColor _color) {
        if (_color == null) {
            return new AColor(color);
        }
        return new AColor(Math.min(getR(), _color.getR()), Math.min(getG(), _color.getG()), Math.min(getB(), _color.getB()));
    }

    /**
     *
     * @param _color
     * @return
     */
    public AColor lighten(AColor _color) {
        if (_color == null) {
            return new AColor(color);
        }
        return new AColor(Math.max(getR(), _color.getR()), Math.max(getG(), _color.getG()), Math.max(getB(), _color.getB()));
    }

    /**
     *
     * @param _color
     * @return
     */
    public AColor subtract(AColor _color) {
        if (_color == null) {
            return new AColor(color);
        }
        return new AColor(Math.max(getR(), _color.getR()) - Math.min(getR(), _color.getR()), Math.max(getG(), _color.getG()) - Math.min(getG(), _color.getG()), Math.max(getB(), _color.getB()) - Math.min(getB(), _color.getB()));
    }

    /**
     *
     * @param _color
     * @return
     */
    public AColor add(AColor _color) {
        if (_color == null) {
            return new AColor(color);
        }
        return new AColor((getR() + _color.getR()) / 2, (getG() + _color.getG()) / 2, (getB() + _color.getB()) / 2);
    }

    /**
     *
     * @return
     */
    public AColor toGray() {
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        int v = r;
        if (g > v) {
            v = g;
        }
        if (b > v) {
            v = b;
        }
        return new AColor(rgbaToInt(v, v, v, a));
    }

    /**
     *
     * @return
     */
    public AColor monochrome() {
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        int v = r;
        if (g > v) {
            v = g;
        }
        if (b > v) {
            v = b;
        }
        return new AColor(rgbaToInt(v, v, v, a));
    }

    /**
     *
     * @param _c
     * @return
     */
    public static final int intToGrayInt(int _c) {
        int a = (_c >> 24) & 0xFF;
        int r = (_c >> 16) & 0xFF;
        int g = (_c >> 8) & 0xFF;
        int b = (_c >> 0) & 0xFF;
        int v = r;
        if (g > v) {
            v = g;
        }
        if (b > v) {
            v = b;
        }
        return rgbaToInt(v, v, v, a);
    }

    /**
     *
     * @param _int
     * @return
     */
    public static String stringForInt(int _int) {
        int a = (_int >> 24) & 0xFF;
        int r = (_int >> 16) & 0xFF;
        int g = (_int >> 8) & 0xFF;
        int b = (_int >> 0) & 0xFF;
        return "ARGB[" + a + "," + r + "," + g + "," + b + "]";
    }

    /**
     *
     * @return
     */
    public int sum() {
        int a = (color >> 24) & 0xFF;
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return r + g + b;
    }

    /**
     *
     * @return
     */
    public int toInt() {
        return color;
    }

    @Override
    public int hashCode() {
        return color;
    }

    @Override
    public boolean equals(Object _instance) {
        if (_instance == this) {
            return true;
        }
        if (_instance instanceof Integer) {
            return color == ((Integer) _instance).intValue();
        }
        if (_instance instanceof AColor) {
            return color == ((AColor) _instance).color;
        }
        return false;
    }
    // source over rules plus blend
    // fs = 1
    // fd = (1-as)
    // cd = cs*fs + cd*fd
    // ad = as*fs + ad*fd
    // alpha is increases as a funtion of pa and pb channel delta mean

    /**
     *
     * @param _s
     * @param _d
     * @param _alpha
     * @return
     */
    public static int compositeSrcOverBlend(int _s, int _d, float _alpha) {

        float as = ((_s >> 24) & 0xFF) / 255f;
        float rs = ((_s >> 16) & 0xFF) / 255f;
        float gs = ((_s >> 8) & 0xFF) / 255f;
        float bs = ((_s >> 0) & 0xFF) / 255f;

        float ad = ((_d >> 24) & 0xFF) / 255f;
        float rd = ((_d >> 16) & 0xFF) / 255f;
        float gd = ((_d >> 8) & 0xFF) / 255f;
        float bd = ((_d >> 0) & 0xFF) / 255f;

        float delta = Math.max(Math.max(Math.max(Math.abs(as - ad), Math.abs(rs - rd)), Math.abs(gs - gd)), Math.abs(bs - bd));
        float blend = (float) delta;

        _alpha = _alpha * (float) Math.sqrt(blend);

        as *= _alpha;
        rs *= _alpha;
        gs *= _alpha;
        bs *= _alpha;

        float fs = 1f;
        float fd = 1f - as;

        int a = (int) (((as * fs + ad * fd) * 255f) + .5f);
        int r = (int) (((rs * fs + rd * fd) * 255f) + .5f);
        int g = (int) (((gs * fs + gd * fd) * 255f) + .5f);
        int b = (int) (((bs * fs + bd * fd) * 255f) + .5f);

        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }

        return ((a & 0xFF) << 24)
                | ((r & 0xFF) << 16)
                | ((g & 0xFF) << 8)
                | ((b & 0xFF) << 0);
    }
    // source over rules
    // fs = 1
    // fd = (1-as)
    // cd = cs*fs + cd*fd
    // ad = as*fs + ad*fd

    /**
     *
     * @param _s
     * @param _d
     * @param _alpha
     * @return
     */
    public static int compositeSrcOver(int _s, int _d, float _alpha) {

        float as = ((_s >> 24) & 0xFF) / 255f;
        float rs = ((_s >> 16) & 0xFF) / 255f;
        float gs = ((_s >> 8) & 0xFF) / 255f;
        float bs = ((_s >> 0) & 0xFF) / 255f;

        float ad = ((_d >> 24) & 0xFF) / 255f;
        float rd = ((_d >> 16) & 0xFF) / 255f;
        float gd = ((_d >> 8) & 0xFF) / 255f;
        float bd = ((_d >> 0) & 0xFF) / 255f;


        as *= _alpha;
        rs *= _alpha;
        gs *= _alpha;
        bs *= _alpha;

        float fs = 1f;
        float fd = 1f - as;

        int a = (int) (((as * fs + ad * fd) * 255f) + .5f);
        int r = (int) (((rs * fs + rd * fd) * 255f) + .5f);
        int g = (int) (((gs * fs + gd * fd) * 255f) + .5f);
        int b = (int) (((bs * fs + bd * fd) * 255f) + .5f);

        if (a > 255) {
            a = 255;
        }
        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }

        return ((a & 0xFF) << 24)
                | ((r & 0xFF) << 16)
                | ((g & 0xFF) << 8)
                | ((b & 0xFF) << 0);

    }

    /**
     *
     * @param _values
     * @param _distance
     * @param _wrap
     * @return
     */
    public static AColor linearInterpolation(Object[] _values, double _distance, boolean _wrap) {
        if (_distance < 0) {
            _distance = 0;
        }
        if (_distance > 1) {
            _distance = 1;
        }
        int l = _values.length;
        if (l == 0) {
            return AColor.white;
        }
        if (l == 1) {
            return (AColor) _values[0];
        }
        double distance = (l - 1) * _distance;
        int index = (int) distance;
        if (index >= l - 1) {
            return (AColor) _values[l - 1];
        }
        distance -= index;// turns distance into remainder
        AColor a = (AColor) _values[index];
        AColor b = (AColor) _values[index + 1];
        return new AColor(linearInterpolation(a.color, b.color, distance));
    }
    /*
    From RGB to YUV
    Y = 0.299R + 0.587G + 0.114B
    U = 0.492 (B-Y)
    V = 0.877 (R-Y)
    It can also be represented as:
    Y =  0.299R + 0.587G + 0.114B
    U = -0.147R - 0.289G + 0.436B
    V =  0.615R - 0.515G - 0.100B
    From YUV to RGB
    R = Y + 1.140V
    G = Y - 0.395U - 0.581V
    B = Y + 2.032U
     */

    /**
     *
     * @param _r
     * @param _g
     * @param _b
     * @return
     */
    public static int[] rgbToYUV(int _r, int _g, int _b) {
        int y = _r + _g + _b; // positive number between 0 and 255*3
        int u = _b - y;
        int v = _r - y;
        return new int[]{y, u, v};
    }

    /**
     *
     * @param _y
     * @param _u
     * @param _v
     * @return
     */
    public static int[] yuvToRGB(int _y, int _u, int _v) {
        int r = _y + _v;
        int g = _y - _u - _v;
        int b = _y + _u;
        return new int[]{r, g, b};
    }

    /**
     *
     * @param r
     * @param g
     * @param b
     * @param hsbvals
     * @return
     */
    public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        float hue, saturation, brightness;
        if (hsbvals == null) {
            hsbvals = new float[3];
        }
        int cmax = (r > g) ? r : g;
        if (b > cmax) {
            cmax = b;
        }
        int cmin = (r < g) ? r : g;
        if (b < cmin) {
            cmin = b;
        }

        brightness = ((float) cmax) / 255.0f;
        if (cmax != 0) {
            saturation = ((float) (cmax - cmin)) / ((float) cmax);
        } else {
            saturation = 0;
        }
        if (saturation == 0) {
            hue = 0;
        } else {
            float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
            float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
            float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
            if (r == cmax) {
                hue = bluec - greenc;
            } else if (g == cmax) {
                hue = 2.0f + redc - bluec;
            } else {
                hue = 4.0f + greenc - redc;
            }
            hue = hue / 6.0f;
            if (hue < 0) {
                hue = hue + 1.0f;
            }
        }
        hsbvals[0] = hue;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;
        return hsbvals;
    }

    /**
     *
     * @param hue
     * @param saturation
     * @param brightness
     * @return
     */
    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float) Math.floor(hue)) * 6.0f;
            float f = h - (float) java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
            }
        }
        return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
    }


    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        for (double rad = 0; rad < (Math.PI * 2); rad += ((Math.PI * 2) / 255d)) {

            int color = AColor.intForRadians(rad);
            double _rad = AColor.radiansForInt(color);
            int _color = AColor.intForRadians(_rad);
            double __rad = AColor.radiansForInt(_color);
            int __color = AColor.intForRadians(__rad);

            if (_color != __color) {
                System.out.println(_rad + " " + __rad + " " + _color + " " + color);
            }
        }

    }
}
