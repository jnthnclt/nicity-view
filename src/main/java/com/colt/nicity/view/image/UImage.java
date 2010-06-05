/*
 * UImage.java.java
 *
 * Created on 01-03-2010 01:31:12 PM
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

import com.colt.nicity.core.lang.MinMaxInt;
import com.colt.nicity.view.core.AColor;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.IOException;

/**
 *
 * @author Administrator
 */
public class UImage {

    /**
     *
     * @param _image
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @return
     */
    public static IntImage grabRegion(
            Image _image,
            int _x, int _y, int _w, int _h) {
        try {
            if (_image == null) {
                return new IntImage(new int[0], 0, 0);
            }
            int w = _image.getWidth(null);
            int h = _image.getHeight(null);
            int[] pixels = new int[_w * _h];

            try {
                PixelGrabber pg = new PixelGrabber(
                        _image, _x, _y, _w, _h, pixels, 0, _w);
                pg.grabPixels();
                if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
                    throw new IOException("failed to load image contents");
                }
                return new IntImage(pixels, _w, _h);
            } catch (InterruptedException e) {
                throw new IOException("image load interrupted");
            }
        } catch (Exception e) {
            return new IntImage();
        }
    }

    /**
     *
     * @param _image
     * @return
     */
    public static AColor[] bounds(IntImage _image) {
        MinMaxInt rmm = new MinMaxInt();
        MinMaxInt gmm = new MinMaxInt();
        MinMaxInt bmm = new MinMaxInt();
        int[] channels = new int[4];
        for (int i = 0; i < _image.pixels.length; i++) {
            AColor.channelsForInt(_image.pixels[i], channels);
            rmm.value(channels[1]);
            gmm.value(channels[2]);
            bmm.value(channels[3]);
        }
        return new AColor[]{
                    new AColor(rmm.min(), gmm.min(), bmm.min()),
                    new AColor(rmm.max(), gmm.max(), bmm.max())
                };
    }
    /**
     *
     */
    final public static int cX = 0;
    /**
     *
     */
    final public static int cY = 1;
    /**
     *
     */
    final public static int cW = 2;
    /**
     *
     */
    final public static int cH = 3;

    /**
     *
     * @param _pixels
     * @param i
     * @param argb
     */
    final public static void assign(int[] _pixels, int i, int argb) {
        _pixels[i] = argb;
    }

    /**
     *
     * @param _pixels
     * @param _w
     * @param _h
     * @param _argb
     * @param x
     * @param y
     * @param _clip
     */
    final public static void drawPoint(int[] _pixels, int _w, int _h, int _argb, int x, int y, int[] _clip) {
        if (x < _clip[cX] || x >= _clip[cX] + _clip[cW] || y < _clip[cY] || y >= _clip[cY] + _clip[cH]) {
            return;
        }
        assign(_pixels, x + y * _w, _argb);
    }

    /**
     *
     * @param _pixels
     * @param _w
     * @param _h
     * @param _argb
     * @param x1
     * @param x2
     * @param y
     * @param clip
     */
    final public static void drawHorizontalLine(
            int[] _pixels, int _w, int _h, int _argb,
            int x1, int x2, int y,
            int[] clip) {
        if (y < clip[cY]) {
            return;
        }
        if (y >= clip[cY] + clip[cH]) {
            return;
        }
        int offset = y * _w;
        int s = Math.max(x1, clip[cX]) + offset;
        int e = Math.min(x2, clip[cX] + clip[cW] - 1) + offset;
        for (int i = s; i <= e; i++) {
            assign(_pixels, i, _argb);
        }
    }
    // y1 < y2

    /**
     *
     * @param _pixels
     * @param _w
     * @param _h
     * @param _argb
     * @param x
     * @param y1
     * @param y2
     * @param clip
     */
    public static void drawVerticalLine(
            int[] _pixels, int _w, int _h, int _argb,
            int x, int y1, int y2,
            int[] clip) {
        if (x < clip[cX]) {
            return;
        }
        if (x >= clip[cX] + clip[cW]) {
            return;
        }
        int s = x + Math.max(y1, clip[cY]) * _w;
        int e = x + Math.min(y2, clip[cY] + clip[cH] - 1) * _w;
        for (int i = s; i <= e; i += _w) {
            assign(_pixels, i, _argb);
        }
    }

    /**
     *
     * @param _pixels
     * @param _w
     * @param _h
     * @param _argb
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param clip
     */
    public static void drawLine(
            int[] _pixels, int _w, int _h, int _argb,
            int x1, int y1, int x2, int y2,
            int[] clip) {
        //x1 += translateX;
        //y1 += translateY;
        //x2 += translateX;
        //y2 += translateY;

        int dx = x2 - x1;
        int dy = y2 - y1;
        synchronized (_pixels) {
            if (dx == 0) {
                if (y1 < y2) {
                    drawVerticalLine(_pixels, _w, _h, _argb, x1, y1, y2, clip);
                } else {
                    drawVerticalLine(_pixels, _w, _h, _argb, x1, y2, y1, clip);
                }
            } else if (dy == 0) {
                if (x1 < x2) {
                    drawHorizontalLine(_pixels, _w, _h, _argb, x1, x2, y1, clip);
                } else {
                    drawHorizontalLine(_pixels, _w, _h, _argb, x2, x1, y1, clip);
                }
            } else {
                // From Bresenham's line generation algorithm
                // v1.1 : suppressed all float use
                boolean swapXY = false;
                int dxNeg = 1;
                int dyNeg = 1;
                boolean negativeSlope = false;
                if (Math.abs(dy) > Math.abs(dx)) {
                    int temp = x1;
                    x1 = y1;
                    y1 = temp;
                    temp = x2;
                    x2 = y2;
                    y2 = temp;
                    dx = x2 - x1;
                    dy = y2 - y1;
                    swapXY = true;
                }

                if (x1 > x2) {
                    int temp = x1;
                    x1 = x2;
                    x2 = temp;
                    temp = y1;
                    y1 = y2;
                    y2 = temp;
                    dx = x2 - x1;
                    dy = y2 - y1;
                }

                if (dy * dx < 0) {
                    if (dy < 0) {
                        dyNeg = -1;
                        dxNeg = 1;
                    } else {
                        dyNeg = 1;
                        dxNeg = -1;
                    }
                    negativeSlope = true;
                }

                int d = 2 * (dy * dyNeg) - (dx * dxNeg);
                int incrH = 2 * dy * dyNeg;
                int incrHV = 2 * ((dy * dyNeg) - (dx * dxNeg));
                int x = x1;
                int y = y1;
                int tempX = x;
                int tempY = y;

                if (swapXY) {
                    int temp = x;
                    x = y;
                    y = temp;
                }

                drawPoint(_pixels, _w, _h, _argb, x, y, clip);
                x = tempX;
                y = tempY;

                while (x < x2) {
                    if (d <= 0) {
                        x++;
                        d += incrH;
                    } else {
                        d += incrHV;
                        x++;
                        if (!negativeSlope) {
                            y++;
                        } else {
                            y--;
                        }
                    }

                    tempX = x;
                    tempY = y;
                    if (swapXY) {
                        int temp = x;
                        x = y;
                        y = temp;
                    }
                    drawPoint(_pixels, _w, _h, _argb, x, y, clip);
                    x = tempX;
                    y = tempY;
                }
            }
        }
    }

    /**
     *
     * @param _pixels
     * @param _w
     * @param _h
     * @param _argb
     * @param x
     * @param y
     * @param width
     * @param height
     * @param _clip
     */
    final public static void drawRect(
            int[] _pixels, int _w, int _h, int _argb,
            int x, int y, int width, int height,
            int[] _clip) {
        //x += translateX;
        //y += translateY;

        int xMin = Math.max(x, _clip[cX]);
        int xMax = Math.min(x + width, _clip[cX] + _clip[cW]) - 1;
        int yMin = Math.max(y, _clip[cY]);
        int yMax = Math.min(y + height, _clip[cY] + _clip[cH]) - 1;

        // Check if completely clipped out
        if (xMin > xMax) {
            return;
        }
        if (yMin > yMax) {
            return;
        }
        synchronized (_pixels) {
            drawHorizontalLine(_pixels, _w, _h, _argb, xMin, xMax, yMin, _clip);
            drawHorizontalLine(_pixels, _w, _h, _argb, xMin, xMax, yMax, _clip);
            drawVerticalLine(_pixels, _w, _h, _argb, yMin, yMax, xMin, _clip);
            drawVerticalLine(_pixels, _w, _h, _argb, yMin, yMax, xMax, _clip);
        }
    }
    // could speed up with array fills

    /**
     *
     * @param _pixels
     * @param _w
     * @param _h
     * @param _argb
     * @param x
     * @param y
     * @param width
     * @param height
     * @param _clip
     */
    public void fillRect(
            int[] _pixels, int _w, int _h, int _argb,
            int x, int y, int width, int height,
            int[] _clip) {
        //x += translateX;
        //y += translateY;

        int xMin = Math.max(x, _clip[cX]);
        int xMax = Math.min(x + width, _clip[cX] + _clip[cW]) - 1;
        int yMin = Math.max(y, _clip[cY]);
        int yMax = Math.min(y + height, _clip[cY] + _clip[cH]) - 1;

        // Check if completely clipped out
        if (xMin > xMax) {
            return;
        }
        if (yMin > yMax) {
            return;
        }
        synchronized (_pixels) {
            int imageWidth = _w;
            int rowOffset = yMin * imageWidth;
            int s = xMin + rowOffset;
            int e = xMax + rowOffset;
            for (int y1 = yMin; y1 <= yMax; y1++, s += imageWidth, e += imageWidth) {
                for (int i = s; i <= e; i++) {
                    assign(_pixels, i, _argb);
                }
            }
        }
    }

    /**
     *
     * @param _pixels
     * @param _w
     * @param _h
     * @param _argb
     * @param xPoints
     * @param yPoints
     * @param nPoints
     * @param _clip
     */
    public void drawPolyline(
            int[] _pixels, int _w, int _h, int _argb,
            int xPoints[], int yPoints[], int nPoints,
            int[] _clip) {
        for (int i = 1; i < nPoints; i++) {
            drawLine(_pixels, _w, _h, _argb, xPoints[i - 1], yPoints[i - 1], xPoints[i], yPoints[i], _clip);
        }
    }

    /**
     *
     * @param _pixels
     * @param _w
     * @param _h
     * @param _argb
     * @param xPoints
     * @param yPoints
     * @param nPoints
     * @param _clip
     */
    public void drawPolygon(
            int[] _pixels, int _w, int _h, int _argb,
            int xPoints[], int yPoints[], int nPoints,
            int[] _clip) {
        drawPolyline(_pixels, _w, _h, _argb, xPoints, yPoints, nPoints, _clip);
        drawLine(_pixels, _w, _h, _argb, xPoints[nPoints - 1], yPoints[nPoints - 1], xPoints[0], yPoints[0], _clip);
    }
}
