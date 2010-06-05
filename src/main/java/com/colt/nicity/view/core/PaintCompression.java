/*
 * PaintCompression.java.java
 *
 * Created on 01-03-2010 01:34:44 PM
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
package com.colt.nicity.view.core;

import com.colt.nicity.view.value.VRange;
import com.colt.nicity.view.flavor.ScrollFlavor;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class PaintCompression {
    //----------------------------------------------------------------------

    /**
     *
     */
    public static String cModeless = "Modeless";
    /**
     *
     */
    public static String cModeX = "Mode X";
    /**
     *
     */
    public static String cModeY = "Mode Y";
    //----------------------------------------------------------------------
    /**
     *
     */
    public String pointMode = cModeless;
    /**
     *
     */
    public int snapX = -1;
    /**
     *
     */
    public double minX = 0;
    /**
     *
     */
    public double maxX = 1;
    /**
     *
     */
    public int snapY = -1;
    /**
     *
     */
    public double minY = 0;
    /**
     *
     */
    public double maxY = 1;

    /**
     *
     */
    public PaintCompression() {
    }

    /**
     *
     * @return
     */
    public PaintCompression copy() {
        PaintCompression fnp = new PaintCompression();
        fnp.minX = minX;
        fnp.maxX = maxX;
        fnp.minY = minY;
        fnp.maxY = maxY;
        return fnp;
    }

    /**
     *
     */
    public void reset() {
        minX = 0d;
        maxX = 1d;
        minY = 0d;
        maxY = 1d;
    }

    /**
     *
     * @param _px
     * @param _py
     * @param _dx
     * @param _dy
     * @param _w
     * @param _h
     */
    public void pan(int _px, int _py, int _dx, int _dy, int _w, int _h) {
        double fxp = xFalloffInverse((double) (_px - _dx) / (double) _w);
        double fyp = yFalloffInverse((double) (_py - _dy) / (double) _h);
        double txp = xFalloffInverse((double) (_px) / (double) _w);
        double typ = yFalloffInverse((double) (_py) / (double) _h);


        minX -= txp - fxp;
        maxX -= txp - fxp;
        minY -= typ - fyp;
        maxY -= typ - fyp;
        checkOverflow();
        persist();
    }

    /**
     *
     * @param _px
     * @param _py
     * @param _dx
     * @param _dy
     * @param _w
     * @param _h
     * @param _constrained
     */
    public void zoom(int _px, int _py, int _dx, int _dy, int _w, int _h, boolean _constrained) {
        double fxp = xFalloffInverse((double) (_px - _dx) / (double) _w);
        double fyp = yFalloffInverse((double) (_py - _dy) / (double) _h);
        double txp = xFalloffInverse((double) (_px) / (double) _w);
        double typ = yFalloffInverse((double) (_py) / (double) _h);

        double dx = txp - fxp;
        double dy = typ - fyp;
        if (_constrained) {
            dx = Math.max(dx, dy);
            dy = dx;
        }

        minX += dx;
        maxX -= dx;
        minY += dy;
        maxY -= dy;

        checkOverflow();
        persist();
    }

    private void checkOverflow() {
        if (maxX > 1) {
            double overage = maxX - 1d;
            minX -= overage;
            maxX -= overage;
        }
        if (minX < 0) {
            double overage = 0 - minX;
            minX += overage;
            maxX += overage;
        }

        if (maxY > 1) {
            double overage = maxY - 1d;
            minY -= overage;
            maxY -= overage;
        }

        if (minY < 0) {
            double overage = 0 - minY;
            minY += overage;
            maxY += overage;
        }
    }

    /**
     *
     */
    public void persist() {
    }

    /**
     *
     * @param _at
     * @return
     */
    public double xFalloff(double _at) {
        double zeroToOne = (_at - minX) / (maxX - minX);
        return zeroToOne;
    }

    /**
     *
     * @param _at
     * @return
     */
    public double yFalloff(double _at) {
        double zeroToOne = (_at - minY) / (maxY - minY);
        return zeroToOne;
    }

    /**
     *
     * @param _at
     * @return
     */
    public double xFalloffInverse(double _at) {
        double unzeroToOne = ((maxX - minX) * _at) + minX;
        return unzeroToOne;
    }

    /**
     *
     * @param _at
     * @return
     */
    public double yFalloffInverse(double _at) {
        double unzeroToOne = ((maxY - minY) * _at) + minY;
        return unzeroToOne;
    }
    // Static
    static ScrollFlavor controlsFlavor = new ScrollFlavor();

    /**
     *
     * @param _compression
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _time
     * @param _size
     * @param _solid
     */
    public static void paintBorder(PaintCompression _compression, ICanvas _g, int _x, int _y, int _w, int _h, double _time, int _size, boolean _solid) {
        _g.setColor(ViewColor.cThemeSelected);
        //_g.rect(false,_x,_y+_h-_size,_w,_size);
        //VRange.paintHorizontal(_g,_x,_y+_h-_size,_w-_size,_size,_compression.minX,Double.MAX_VALUE,_compression.maxX);
        VRange.paintHorizontal(_g, _x + _size, _y, _w - _size, _size, _compression.minX, Double.MAX_VALUE, _compression.maxX, _solid);
        //_g.rect(false,_x+_w-_size,_y,_size,_h);
        //VRange.paintVertical(_g,_x+_w-_size,_y,_size,_h-_size,_compression.minY,Double.MAX_VALUE,_compression.maxY);
        VRange.paintVertical(_g, _x, _y + _size, _size, _h - _size, _compression.minY, Double.MAX_VALUE, _compression.maxY, _solid);
    }
    //----------------------------------------------------------------------

    /**
     *
     * @param _compression
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _time
     * @param _size
     */
    public static void paintBackground(PaintCompression _compression, ICanvas _g, int _x, int _y, int _w, int _h, double _time, int _size) {
    }
    //----------------------------------------------------------------------
    // Events
    //----------------------------------------------------------------------

    /**
     *
     * @param _compression
     * @param _px
     * @param _py
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _size
     * @return
     */
    public static boolean pointMode(PaintCompression _compression, int _px, int _py, int _x, int _y, int _w, int _h, int _size) {
        if (_compression.snapX != -1) {
            return true;
        }
        if (_compression.snapY != -1) {
            return true;
        }
        if (_px > _x + _size && _px < _x + (_w - _size) && _py < _y + _size) {
            _compression.pointMode = cModeX;
        } else if (_px < _x + _size && _py > _y + _size && _py < _y + (_h - _size)) {
            _compression.pointMode = cModeY;
        } else {
            modeless(_compression);
            return false;
        }
        return true;
    }
    //----------------------------------------------------------------------

    /**
     *
     * @param _compression
     * @param _px
     * @param _py
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _size
     * @return
     */
    public static boolean event(PaintCompression _compression, int _px, int _py, int _x, int _y, int _w, int _h, int _size) {
        //?? _x && _y ok for all conditions?
        boolean _constrainScale = true;
        if (_compression.pointMode == cModeless) {
            return false;
        }
        if (_compression.pointMode == cModeX) {
            if (_px < 0 || _px > _w - _size) {
                return true;
            }
            if (_py < _h - _size || _py > _h) {
                return true;
            }
            _py = _py - (_h - _size);
            double[] smam = VRange.set(new XY_I(_px, _py), _w - _size, _size, _compression.snapX, _compression.minX, Double.MAX_VALUE, _compression.maxX, true);
            if (smam != null) {
                _compression.snapX = (int) smam[0];
                _compression.minX = smam[1];
                _compression.maxX = smam[3];
                if (_constrainScale) {
                    double xd = _compression.maxX - _compression.minX;
                    double yd = _compression.maxY - _compression.minY;
                    if (xd != yd) {
                        _compression.maxY += (xd - yd) / 2;
                        _compression.minY -= (xd - yd) / 2;
                    }
                }
            }
        } else if (_compression.pointMode == cModeY) {
            if (_px < _w - _size || _px > _w) {
                return true;
            }
            if (_py < 0 || _py > _h - _size) {
                return true;
            }
            _px = _px - (_w - _size);
            double[] smam = VRange.set(new XY_I(_px, _py), _size, _h - _size, _compression.snapY, _compression.minY, Double.MAX_VALUE, _compression.maxY, false);
            if (smam != null) {
                _compression.snapY = (int) smam[0];
                _compression.minY = smam[1];
                _compression.maxY = smam[3];

                if (_constrainScale) {
                    double xd = _compression.maxX - _compression.minX;
                    double yd = _compression.maxY - _compression.minY;
                    if (yd != xd) {
                        _compression.maxX += (yd - xd) / 2;
                        _compression.minX -= (yd - xd) / 2;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param _compression
     */
    public static void modeless(PaintCompression _compression) {
        _compression.snapX = -1;
        _compression.snapY = -1;
        _compression.pointMode = cModeless;
    }
}

