/*
 * FilerCanvas.java.java
 *
 * Created on 01-03-2010 01:31:36 PM
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
package colt.nicity.view.canvas;

import colt.nicity.core.io.IFiler;
import colt.nicity.core.io.UIO;
import colt.nicity.core.lang.ICallback;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AFont;
import colt.nicity.view.flavor.AFlavor;
import colt.nicity.view.interfaces.ICanvas;
import java.awt.geom.AffineTransform;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class FilerCanvas implements ICanvas {

    static CanvasMethod[] methods = new CanvasMethod[128];

    @Override
    public void drawImage(File _image, int _x, int _y, int _w, int _h) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    static abstract class CanvasMethod {

        abstract public void render(IFiler _f, ICanvas _c) throws Exception;
    }
    private long who = 0;
    private IFiler f;
    /**
     *
     */
    protected ICallback exceptions;

    /**
     *
     * @param _who
     * @param _f
     * @param _exception
     */
    public FilerCanvas(long _who, IFiler _f, ICallback _exception) {
        who = _who;
        f = _f;
        exceptions = _exception;
    }

    /**
     *
     * @return
     */
    @Override
    public long who() {
        return who;
    }

    /**
     *
     * @return
     */
    public IFiler filer() {
        return f;
    }

    /**
     *
     * @param _c
     * @throws Exception
     */
    public void renderTo(ICanvas _c) throws Exception {
        boolean done = false;
        while (!done) {
            int methodIndex = UIO.readByte(f, "method");
            if (methods[methodIndex] == null) {
                System.err.println("unsupported method "+methodIndex);
                break;
            }
            methods[methodIndex].render(f, _c);
            done = UIO.readBoolean(f, "done");
        }
    }
    static byte cDispose = 1;

    /**
     *
     */
    public void dispose() {
        try {
            UIO.writeByte(f, cDispose, "method");
            UIO.writeBoolean(f, true, "done");
            f.flush();
            f.close();
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cDispose] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.dispose();
            }
        };
    }
    static byte cArc = 2;

    /**
     *
     * @param _fill
     * @param x
     * @param y
     * @param w
     * @param h
     * @param start
     * @param arcAngle
     */
    public void arc(boolean _fill, int x, int y, int w, int h, int start, int arcAngle) {
        try {
            UIO.writeByte(f, cArc, "method");
            UIO.writeBoolean(f, _fill, "fill");
            UIO.writeInt(f, x, "x");
            UIO.writeInt(f, y, "y");
            UIO.writeInt(f, w, "w");
            UIO.writeInt(f, h, "h");
            UIO.writeInt(f, start, "start");
            UIO.writeInt(f, arcAngle, "arcAngle");
            UIO.writeBoolean(f, false, "done");

        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cArc] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.arc(
                        UIO.readBoolean(f, "fill"),
                        UIO.readInt(f, "x"),
                        UIO.readInt(f, "y"),
                        UIO.readInt(f, "w"),
                        UIO.readInt(f, "h"),
                        UIO.readInt(f, "start"),
                        UIO.readInt(f, "arcAngle"));
            }
        };
    }
    static byte cLine = 3;

    /**
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void line(int x1, int y1, int x2, int y2) {
        try {
            UIO.writeByte(f, cLine, "method");
            UIO.writeInt(f, x1, "x1");
            UIO.writeInt(f, y1, "y1");
            UIO.writeInt(f, x2, "x2");
            UIO.writeInt(f, y2, "y2");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cLine] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.line(
                        UIO.readInt(f, "x1"),
                        UIO.readInt(f, "y1"),
                        UIO.readInt(f, "x2"),
                        UIO.readInt(f, "y2"));
            }
        };
    }
    static byte cOval = 4;

    /**
     *
     * @param _fill
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void oval(boolean _fill, int x, int y, int w, int h) {
        try {
            UIO.writeByte(f, cOval, "method");

            UIO.writeBoolean(f, _fill, "fill");
            UIO.writeInt(f, x, "x");
            UIO.writeInt(f, y, "y");
            UIO.writeInt(f, w, "w");
            UIO.writeInt(f, h, "h");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cOval] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.oval(
                        UIO.readBoolean(f, "fill"),
                        UIO.readInt(f, "x"),
                        UIO.readInt(f, "y"),
                        UIO.readInt(f, "w"),
                        UIO.readInt(f, "h"));

            }
        };
    }
    static byte cRect = 5;

    /**
     *
     * @param _fill
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void rect(boolean _fill, int x, int y, int w, int h) {
        try {
            UIO.writeByte(f, cRect, "method");

            UIO.writeBoolean(f, _fill, "fill");
            UIO.writeInt(f, x, "x");
            UIO.writeInt(f, y, "y");
            UIO.writeInt(f, w, "w");
            UIO.writeInt(f, h, "h");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cRect] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.rect(
                        UIO.readBoolean(f, "fill"),
                        UIO.readInt(f, "x"),
                        UIO.readInt(f, "y"),
                        UIO.readInt(f, "w"),
                        UIO.readInt(f, "h"));

            }
        };
    }
    static byte cRoundRect = 6;

    /**
     *
     * @param _fill
     * @param x
     * @param y
     * @param w
     * @param h
     * @param arcWidth
     * @param arcHeight
     */
    public void roundRect(boolean _fill, int x, int y, int w, int h, int arcWidth, int arcHeight) {
        try {
            UIO.writeByte(f, cRoundRect, "method");

            UIO.writeBoolean(f, _fill, "fill");
            UIO.writeInt(f, x, "x");
            UIO.writeInt(f, y, "y");
            UIO.writeInt(f, w, "w");
            UIO.writeInt(f, h, "h");
            UIO.writeInt(f, arcWidth, "arcWidth");
            UIO.writeInt(f, arcHeight, "arcHeight");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cRoundRect] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.roundRect(
                        UIO.readBoolean(f, "fill"),
                        UIO.readInt(f, "x"),
                        UIO.readInt(f, "y"),
                        UIO.readInt(f, "w"),
                        UIO.readInt(f, "h"),
                        UIO.readInt(f, "aw"),
                        UIO.readInt(f, "ah"));


            }
        };
    }
    static byte cPolyline = 7;

    /**
     *
     * @param xPoints
     * @param yPoints
     * @param nPoints
     */
    public void polyline(int[] xPoints, int[] yPoints, int nPoints) {
        try {
            UIO.writeByte(f, cPolyline, "method");

            UIO.writeIntArray(f, xPoints, "xs");
            UIO.writeIntArray(f, yPoints, "ys");
            UIO.writeInt(f, nPoints, "length");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cPolyline] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.polyline(
                        UIO.readIntArray(f, "xs"),
                        UIO.readIntArray(f, "ys"),
                        UIO.readInt(f, "length"));

            }
        };
    }
    static byte cPolygon = 8;

    /**
     *
     * @param _fill
     * @param xPoints
     * @param yPoints
     * @param nPoints
     */
    public void polygon(boolean _fill, int[] xPoints, int[] yPoints, int nPoints) {
        try {
            UIO.writeByte(f, cPolygon, "method");

            UIO.writeBoolean(f, _fill, "fill");
            UIO.writeIntArray(f, xPoints, "xs");
            UIO.writeIntArray(f, yPoints, "ys");
            UIO.writeInt(f, nPoints, "length");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cPolygon] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.polygon(
                        UIO.readBoolean(f, "fill"),
                        UIO.readIntArray(f, "xs"),
                        UIO.readIntArray(f, "ys"),
                        UIO.readInt(f, "length"));

            }
        };
    }
    static byte cString = 9;

    /**
     *
     * @param str
     * @param x
     * @param y
     */
    public void drawString(String str, int x, int y) {
        try {
            UIO.writeByte(f, cString, "method");

            UIO.writeString(f, str, "string");
            UIO.writeInt(f, x, "x");
            UIO.writeInt(f, y, "y");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cString] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.drawString(
                        UIO.readString(f, "string"),
                        UIO.readInt(f, "y"),
                        UIO.readInt(f, "w"));
            }
        };
    }
    static byte cClip = 10;
    Object clip;

    /**
     *
     * @return
     */
    public Object getClip() {
        return clip;
    }

    /**
     * 
     * @param _clip
     */
    public void setClip(Object _clip) {
        clip = _clip;
    }
    static byte cClipRect = 11;

    /**
     *
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void setClip(int x, int y, int w, int h) {
        try {
            UIO.writeByte(f, cClipRect, "method");

            UIO.writeInt(f, x, "x");
            UIO.writeInt(f, y, "y");
            UIO.writeInt(f, w, "w");
            UIO.writeInt(f, h, "h");
            clip = new XYWH_I(x, y, w, h);
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cClipRect] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.setClip(
                        UIO.readInt(f, "x"),
                        UIO.readInt(f, "y"),
                        UIO.readInt(f, "w"),
                        UIO.readInt(f, "h"));
            }
        };
    }
    static byte cTranslate = 12;

    /**
     *
     * @param x
     * @param y
     */
    public void translate(int x, int y) {
        try {
            UIO.writeByte(f, cTranslate, "method");

            UIO.writeInt(f, x, "x");
            UIO.writeInt(f, y, "y");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cTranslate] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.translate(
                        UIO.readInt(f, "x"),
                        UIO.readInt(f, "y"));

            }
        };
    }
    static byte cColor = 13;
    Integer lastColor = null;

    /**
     *
     * @param _color
     */
    public void setColor(AColor _color) {
        try {
            Integer c = _color.intValue();
            if (c != lastColor) {
                lastColor = c;
                UIO.writeByte(f, cColor, "method");

                UIO.writeInt(f, c, "color");
                UIO.writeBoolean(f, false, "done");
            }
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cColor] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.setColor(
                        new AColor(UIO.readInt(f, "color")));

            }
        };
    }
    static byte cFont = 14;

    /**
     *
     * @param _font
     */
    public void setFont(AFont _font) {
        try {
            UIO.writeByte(f, cFont, "method");

            UIO.writeString(f, _font.getFont().getFontName(), "name");
            UIO.writeInt(f, _font.getFont().getStyle(), "style");
            UIO.writeInt(f, _font.getFont().getSize(), "size");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cFont] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.setFont(
                        new AFont(UIO.readString(f, "name"), UIO.readInt(f, "size"), UIO.readInt(f, "size")));

            }
        };
    }
    static byte cFill = 15;

    /**
     *
     * @param _fill
     */
    public void fill(Object _fill) {
    }
    static byte cDraw = 16;

    /**
     *
     * @param _draw
     */
    public void draw(Object _draw) {
    }
    static byte cPaint = 17;

    /**
     *
     * @param _draw
     */
    public void setPaint(Object _draw) {
    }
    static byte cRotateA = 18;

    /**
     *
     * @param angle
     */
    public void rotate(double angle) {
        try {
            UIO.writeByte(f, cRotateA, "method");

            UIO.writeDouble(f, angle, "angle");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cRotateA] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.rotate(
                        UIO.readDouble(f, "angle"));

            }
        };
    }

    /**
     *
     * @return
     */
    public AffineTransform getTransform() {
        return null;
    }
    static byte cTransform = 19;

    /**
     *
     * @param restore
     */
    public void setTransform(Object restore) {
    }
    static byte cRotateAXY = 20;

    /**
     *
     * @param angle
     * @param x
     * @param y
     */
    public void rotate(double angle, double x, double y) {
        try {
            UIO.writeByte(f, cRotateAXY, "method");

            UIO.writeDouble(f, angle, "angle");
            UIO.writeDouble(f, x, "x");
            UIO.writeDouble(f, y, "y");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cRotateAXY] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.rotate(
                        UIO.readDouble(f, "angle"),
                        UIO.readDouble(f, "x"),
                        UIO.readDouble(f, "y"));

            }
        };
    }
    static byte cTranslateDXY = 21;

    /**
     *
     * @param x
     * @param y
     */
    public void translate(double x, double y) {
        try {
            UIO.writeByte(f, cTranslateDXY, "method");

            UIO.writeDouble(f, x, "x");
            UIO.writeDouble(f, y, "y");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cTranslateDXY] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.translate(
                        UIO.readDouble(f, "x"),
                        UIO.readDouble(f, "y"));

            }
        };
    }
    static byte cAlpha = 22;

    /**
     *
     * @param _alpha
     * @param _composite
     */
    public void setAlpha(float _alpha, int _composite) {
        try {
            UIO.writeByte(f, cAlpha, "method");

            UIO.writeFloat(f, _alpha, "alpha");
            UIO.writeInt(f, _composite, "composite");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cAlpha] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.setAlpha(
                        UIO.readFloat(f, "alpha"),
                        UIO.readInt(f, "composite"));

            }
        };
    }
    static byte cGradient = 23;

    /**
     *
     * @param _from
     * @param _fx
     * @param _fy
     * @param _to
     * @param _tx
     * @param _ty
     */
    public void setGradient(AColor _from, int _fx, int _fy, AColor _to, int _tx, int _ty) {
        try {
            UIO.writeByte(f, cGradient, "method");

            UIO.writeInt(f, _from.intValue(), "fromColor");
            UIO.writeInt(f, _fx, "fx");
            UIO.writeInt(f, _fy, "fy");
            UIO.writeInt(f, _to.intValue(), "toColor");
            UIO.writeInt(f, _tx, "tx");
            UIO.writeInt(f, _ty, "ty");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cGradient] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.setGradient(
                        new AColor(UIO.readInt(f, "fromColor")),
                        UIO.readInt(f, "fx"),
                        UIO.readInt(f, "fy"),
                        new AColor(UIO.readInt(f, "toColor")),
                        UIO.readInt(f, "tx"),
                        UIO.readInt(f, "ty"));

            }
        };
    }
    static byte cImageXY = 24;

    /**
     *
     * @param _image
     * @param _x
     * @param _y
     * @param _observer
     */
    public void drawImage(Object _image, int _x, int _y, Object _observer) {
        try {
            UIO.writeByte(f, cImageXY, "method");

            UIO.writeInt(f, _x, "x");
            UIO.writeInt(f, _y, "y");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cImageXY] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.drawImage(
                        null,
                        UIO.readInt(f, "fx"),
                        UIO.readInt(f, "fy"),
                        null);

            }
        };
    }
    static byte cImageXYWH = 25;

    /**
     *
     * @param _image
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _observer
     */
    public void drawImage(Object _image, int _x, int _y, int _w, int _h, Object _observer) {
        try {
            UIO.writeByte(f, cImageXYWH, "method");

            UIO.writeInt(f, _x, "x");
            UIO.writeInt(f, _y, "y");
            UIO.writeInt(f, _w, "w");
            UIO.writeInt(f, _h, "h");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cImageXYWH] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.drawImage(
                        null,
                        UIO.readInt(f, "x"),
                        UIO.readInt(f, "y"),
                        UIO.readInt(f, "w"),
                        UIO.readInt(f, "h"),
                        null);

            }
        };
    }
    static byte cImageDXYSXY = 26;

    /**
     *
     * @param _image
     * @param _dx1
     * @param _dy1
     * @param _dx2
     * @param _dy2
     * @param _sx1
     * @param _sy1
     * @param _sx2
     * @param _sy2
     * @param _observer
     */
    public void drawImage(Object _image, int _dx1, int _dy1, int _dx2, int _dy2, int _sx1, int _sy1, int _sx2, int _sy2, Object _observer) {
        try {
            UIO.writeByte(f, cImageDXYSXY, "method");

            UIO.writeInt(f, _dx1, "dx1");
            UIO.writeInt(f, _dy1, "dy1");
            UIO.writeInt(f, _dx2, "dx2");
            UIO.writeInt(f, _dy2, "dy2");
            UIO.writeInt(f, _sx1, "sx1");
            UIO.writeInt(f, _sy1, "sy1");
            UIO.writeInt(f, _sx2, "sx2");
            UIO.writeInt(f, _sy2, "sy2");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }

    static {
        methods[cImageDXYSXY] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.drawImage(
                        null,
                        UIO.readInt(f, "dx1"),
                        UIO.readInt(f, "dy1"),
                        UIO.readInt(f, "dx2"),
                        UIO.readInt(f, "dy2"),
                        UIO.readInt(f, "sx1"),
                        UIO.readInt(f, "sy1"),
                        UIO.readInt(f, "sx2"),
                        UIO.readInt(f, "sy2"),
                        null);
            }
        };
    }
    
    static byte cPaintFlavor = 27;
    @Override
    public void paintFlavor(AFlavor flavor, int _x, int _y, int _w, int _h, AColor _color) {
        try {
            UIO.writeByte(f, cPaintFlavor, "method");
            UIO.writeLong(f, flavor.hashObject(), "flavor");
            UIO.writeInt(f, _x, "x");
            UIO.writeInt(f, _y, "y");
            UIO.writeInt(f, _w, "w");
            UIO.writeInt(f, _h, "h");
            UIO.writeInt(f, _color.intValue(), "color");
            UIO.writeBoolean(f, false, "done");
        } catch (Exception ex) {
            exceptions.callback(ex);
        }
    }
    
    static {
        methods[cPaintFlavor] = new CanvasMethod() {

            public void render(IFiler f, ICanvas _c) throws Exception {
                _c.paintFlavor(
                        AFlavor.getFlavor(UIO.readLong(f, "flavor")),
                        UIO.readInt(f, "x"),
                        UIO.readInt(f, "y"),
                        UIO.readInt(f, "w"),
                        UIO.readInt(f, "h"),
                        new AColor(UIO.readInt(f, "color"))
                        );
            }
        };
    }
}
