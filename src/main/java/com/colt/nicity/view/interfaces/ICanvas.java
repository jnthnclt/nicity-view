/*
 * ICanvas.java.java
 *
 * Created on 01-03-2010 01:31:40 PM
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
package com.colt.nicity.view.interfaces;

import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.AFont;

/**
 *
 * @author Administrator
 */
public interface ICanvas {
    //public void clearRect(int x, int y, int width, int height);
    //public void clipRect(int x, int y, int width, int height);
    //public void copyArea(int x, int y, int width, int height, int dx, int dy);

    /**
     *
     * @return
     */
    public long who();

    /**
     *
     */
    public void dispose();
    /**
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void line(int x1, int y1, int x2, int y2);
    /**
     *
     * @param xPoints
     * @param yPoints
     * @param nPoints
     */
    public void polyline(int[] xPoints, int[] yPoints, int nPoints);
    /**
     *
     * @param str
     * @param x
     * @param y
     */
    public void drawString(String str, int x, int y);

    /**
     *
     * @param _fill
     * @param x
     * @param y
     * @param width
     * @param height
     * @param start
     * @param arcAngle
     */
    public void arc(boolean _fill,int x, int y, int width, int height, int start, int arcAngle);
    /**
     *
     * @param _fill
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void oval(boolean _fill,int x, int y, int width, int height);
    /**
     *
     * @param _fill
     * @param x
     * @param y
     * @param width
     * @param height
     * @param arcWidth
     * @param arcHeight
     */
    public void roundRect(boolean _fill,int x, int y, int width, int height, int arcWidth, int arcHeight);
    /**
     *
     * @param _fill
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void rect(boolean _fill,int x, int y, int width, int height);
    /**
     *
     * @param _fill
     * @param xPoints
     * @param yPoints
     * @param nPoints
     */
    public void polygon(boolean _fill,int[] xPoints, int[] yPoints, int nPoints);

    /**
     *
     * @param _fill
     */
    public void fill(Object _fill);
    /**
     *
     * @param _draw
     */
    public void draw(Object _draw);

    /**
     *
     * @return
     */
    public Object getTransform();
    /**
     *
     * @param restore
     */
    public void setTransform(Object restore);
    /**
     *
     * @param x
     * @param y
     */
    public void translate(double x, double y);
    /**
     *
     * @param _radians
     */
    public void rotate(double _radians);
    /**
     *
     * @param s
     * @param fx
     * @param fy
     */
    public void rotate(double s, double fx, double fy);

    /**
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void setClip(int x, int y, int width, int height);
    /**
     *
     * @param _clip
     */
    public void setClip(Object _clip);
    /**
     *
     * @return
     */
    public Object getClip();

    /**
     *
     * @param _color
     */
    public void setColor(AColor _color);
    /**
     *
     * @param _font
     */
    public void setFont(AFont _font);

    /**
     *
     * @param _image
     * @param _x
     * @param _y
     * @param _obsrever
     */
    public void drawImage(Object _image, int _x, int _y, Object _obsrever);
    /**
     *
     * @param _image
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _obsrever
     */
    public void drawImage(Object _image, int _x, int _y, int _w, int _h, Object _obsrever);
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
    public void drawImage(Object _image,
            int _dx1, int _dy1, int _dx2, int _dy2,
            int _sx1, int _sy1, int _sx2, int _sy2,
            Object _observer);

    /**
     *
     * @param _paint
     */
    public void setPaint(Object _paint);


    /**
     *
     */
    public static final int	cCLEAR		= 1;
    /**
     *
     */
    public static final int	cSRC		= 2;
    /**
     *
     */
    public static final int	cDST		= 9;
    /**
     *
     */
    public static final int	cSRC_OVER	= 3;
    /**
     *
     */
    public static final int	cDST_OVER	= 4;
    /**
     *
     */
    public static final int	cSRC_IN		= 5;
    /**
     *
     */
    public static final int	cDST_IN		= 6;
    /**
     *
     */
    public static final int	cSRC_OUT	= 7;
    /**
     *
     */
    public static final int	cDST_OUT	= 8;
    /**
     *
     */
    public static final int	cSRC_ATOP	= 10;
    /**
     *
     */
    public static final int	cDST_ATOP	= 11;
    /**
     *
     */
    public static final int	cXOR		= 12;


    /**
     *
     * @param _alpha
     * @param _composite
     */
    public void setAlpha(float _alpha, int _composite);
    /**
     *
     * @param _from
     * @param _fx
     * @param _fy
     * @param _to
     * @param _tx
     * @param _ty
     */
    public void setGradient(AColor _from, int _fx, int _fy, AColor _to, int _tx, int _ty);
}
