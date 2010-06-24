/*
 * Plane.java.java
 *
 * Created on 01-03-2010 01:33:52 PM
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
package colt.nicity.view.paint;

import colt.nicity.core.lang.MinMaxLong;
import colt.nicity.core.lang.UDouble;
import colt.nicity.core.time.UTime;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.UV;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class Plane {

    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public double nwx, nwy, nwz;//read only please
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public double nex, ney, nez;//read only please
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public double sex, sey, sez;//read only please
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public double swx, swy, swz;//read only please

    /**
     *
     * @param _nwx
     * @param _nwy
     * @param _nwz
     * @param _nex
     * @param _ney
     * @param _nez
     * @param _sex
     * @param _sey
     * @param _sez
     * @param _swx
     * @param _swy
     * @param _swz
     */
    public Plane(
            double _nwx, double _nwy, double _nwz,
            double _nex, double _ney, double _nez,
            double _sex, double _sey, double _sez,
            double _swx, double _swy, double _swz) {
        nwx = _nwx;
        nwy = _nwy;
        nwz = _nwz;
        nex = _nex;
        ney = _ney;
        nez = _nez;
        sex = _sex;
        sey = _sey;
        sez = _sez;
        swx = _swx;
        swy = _swy;
        swz = _swz;
    }

    /**
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public void translate(double _x, double _y, double _z) {
        nwx += _x;
        nwy += _y;
        nwz += _z;
        nex += _x;
        ney += _y;
        nez += _z;
        sex += _x;
        sey += _y;
        sez += _z;
        swx += _x;
        swy += _y;
        swz += _z;
    }

    /**
     *
     * @param _corner
     * @param _x
     * @param _y
     * @param _z
     */
    public void translateCorner(int _corner, double _x, double _y, double _z) {
        nwx += _x;
        nwy += _y;
        nwz += _z;
    }

    // Round Methods
    /**
     *
     * @param _a
     * @param _r
     * @param _x
     * @param _w
     * @return
     */
    static public double getX(
            double _a, double _r, int _x, int _w// a r z 0->1 a=0 straigt up r=0 center, z=0 closest
            ) {
        double radians = (Math.PI * 2) * _a;
        double rx = _x + Math.cos(radians) * ((_w / 2) * _r);
        return rx;
    }

    /**
     *
     * @param _a
     * @param _r
     * @param _y
     * @param _h
     * @return
     */
    static public double getY(
            double _a, double _r, int _y, int _h// a r z 0->1 a=0 straigt up r=0 center, z=0 closest
            ) {
        double radians = (Math.PI * 2) * _a;
        double ry = _y + Math.sin(radians) * ((_h / 2) * _r);
        return ry;
    }

    // Square Methods
    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public double getX(// return x
            double _x, double _y// x y 0->1 x=0 leftmost y=0 topmost
            ) {
        double wx = UDouble.linearInterpolation(nwx, swx, _y);
        double ex = UDouble.linearInterpolation(nex, sex, _y);

        return UDouble.linearInterpolation(wx, ex, _x);
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public double getY(// return y
            double _x, double _y// x y 0->1 x=0 leftmost y=0 topmost
            ) {
        double ny = UDouble.linearInterpolation(nwy, ney, _x);
        double sy = UDouble.linearInterpolation(swy, sey, _x);

        return UDouble.linearInterpolation(ny, sy, _y);
    }

    /**
     *
     * @param _x
     * @param _y
     * @return
     */
    public double getZ(// return y
            double _x, double _y// x y 0->1 x=0 leftmost y=0 topmost
            ) {
        double ez = UDouble.linearInterpolation(nex, sex, _y);
        double wz = UDouble.linearInterpolation(nwx, swx, _y);

        return UDouble.linearInterpolation(ez, wz, _x);
    }

    /**
     *
     * @param _x
     * @return
     */
    public double getH(// return h
            double _x// x 0->1 x=0 leftmost
            ) {
        double ed = UDouble.distance(nex, ney, sex, sey);
        double wd = UDouble.distance(nwx, nwy, swx, swy);

        double yd = UDouble.linearInterpolation(wd, ed, _x);
        return yd;
    }

    /**
     *
     * @param _y
     * @return
     */
    public double getW(// return w
            double _y//y 0->1 y=0 topmost
            ) {
        double nd = UDouble.distance(nwx, nwy, nex, ney);
        double sd = UDouble.distance(swx, swy, sex, sey);

        double xd = UDouble.linearInterpolation(nd, sd, _y);
        return xd;
    }

    // Draw Helpers
    /**
     *
     * @param _g
     * @param _fx
     * @param _fy
     * @param _tx
     * @param _ty
     */
    public void line(
            ICanvas _g,
            double _fx, double _fy,
            double _tx, double _ty) {
        _g.line((int) getX(_fx, _fy), (int) getY(_fx, _fy), (int) getX(_tx, _ty), (int) getY(_tx, _ty));
    }

    /**
     *
     * @param _g
     * @param _fill
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public void rect(
            ICanvas _g, boolean _fill,
            double _x, double _y, double _w, double _h) {
        int[] xps = new int[]{(int) getX(_x, _y), (int) getX(_x + _w, _y), (int) getX(_x + _w, _y + _h), (int) getX(_x, _y + _h)};
        int[] yps = new int[]{(int) getY(_x, _y), (int) getY(_x + _w, _y), (int) getY(_x + _w, _y + _h), (int) getY(_x, _y + _h)};
        _g.polygon(_fill, xps, yps, 4);
    }

    /**
     *
     * @param _g
     * @param _font
     * @param _string
     * @param _alignX
     * @param alignY
     * @param _x
     * @param _y
     */
    public void drawString(
            ICanvas _g, AFont _font,
            String _string, double _alignX, double alignY,
            double _x, double _y) {
        float fw = _font.getW(_string);
        float fh = _font.getH(_string);

        _g.setFont(_font);
        _g.drawString(_string, (int) (getX(_x, _y) - (fw / 2) + ((fw / 2) * _alignX)), (int) (getY(_x, _y) + (fh / 2) - ((fh / 2) * alignY)));
    }
    /*

    public void drawView(IView _view,double _alignX,double alignY,
    ICanvas _g,double _x,double _y,double _z
    ) {
    _view.layoutInterior();

    int vw = (int)_view.getW();
    int vh = (int)_view.getH();

    double w = getZW(vw,_z);
    double h = getZH(vh,_z);

    int x = (int)(tunnelX(_x,_z)-(vw/2)+((vw/2)*_alignX));
    int y = (int)(tunnelY(_y,_z)+(vh/2)-((vh/2)*alignY));

    Shape clip = _g.getClip();
    _g.translate(x,y);
    _view.paint(_view,_g,new Layer(0,0,0,0,(int)w,(int)h),cRepair);
    _g.translate(-(x),-(y));
    _g.setClip(clip);
    }*/

    /**
     *
     * @param _g
     * @param wave
     * @param _px
     * @param _py
     * @param _pw
     * @param _ph
     * @param _start
     * @param _end
     * @param _resolution
     */
    public void paintWaveform(
            ICanvas _g, double[] wave,
            double _px, double _py, double _pw, double _ph,
            double _start, double _end, int _resolution) {

        double i = Math.abs((_end - _start) / _resolution);

        for (int r = 0; r < _resolution; _start += i, r++) {
            if (_start < 0) {
                continue;
            }
            if (_start > 1) {
                continue;
            }

            double f = UDouble.linearInterpolation(wave, _start);
            double t = UDouble.linearInterpolation(wave, _start + i);

            if (f < 0 || t < 0) {
                continue;
            }

            f = 1 - f;//??
            t = 1 - t;//??


            // to do reverse direction

            double fr = (double) (r) / (double) (_resolution);
            double tr = (double) (r + 1) / (double) (_resolution);

            f = _py + (f * _ph);
            t = _py + (t * _ph);

            fr = _px + (fr * _pw);
            tr = _px + (tr * _pw);


            _g.line((int) getX(fr, f), (int) getY(fr, f), (int) getX(tr, t), (int) getY(tr, t));
        }
    }

    /**
     *
     * @param _g
     * @param wave
     * @param _px
     * @param _py
     * @param _pw
     * @param _ph
     * @param _start
     * @param _end
     * @param _resolution
     */
    public void paintVerticalWaveform(
            ICanvas _g, double[] wave,
            double _px, double _py, double _pw, double _ph,
            double _start, double _end, int _resolution) {

        double i = Math.abs((_end - _start) / _resolution);

        for (int r = 0; r < _resolution; _start += i, r++) {
            if (_start < 0) {
                continue;
            }
            if (_start > 1) {
                continue;
            }

            double f = UDouble.linearInterpolation(wave, _start);
            double t = UDouble.linearInterpolation(wave, _start + i);

            if (f < 0 || t < 0) {
                continue;
            }


            double fr = (double) (r) / (double) (_resolution);
            double tr = (double) (r + 1) / (double) (_resolution);

            double fx = _px + (f * _pw);
            double tx = _px + (t * _pw);

            double fy = _py + (fr * _ph);
            double ty = _py + (tr * _ph);

            line(_g, fx, fy, tx, ty);
        }
    }

    /**
     *
     * @param _g
     * @param wave
     * @param _px
     * @param _py
     * @param _pw
     * @param _ph
     * @param _start
     * @param _end
     * @param _resolution
     */
    public void paintHit(
            ICanvas _g, double[] wave,
            double _px, double _py, double _pw, double _ph,
            double _start, double _end, int _resolution) {

        double i = Math.abs((_end - _start) / _resolution);

        for (int r = 0; r < _resolution; _start += i, r++) {
            if (_start < 0) {
                continue;
            }
            if (_start > 1) {
                continue;
            }

            double f = UDouble.linearInterpolation(wave, _start);
            double t = UDouble.linearInterpolation(wave, _start + i);

            if (f < 0 || t < 0) {
                continue;
            }

            f = 1 - f;//??
            t = 1 - t;//??


            double fr = (double) (r) / (double) (_resolution);
            double tr = (double) (r + 1) / (double) (_resolution);

            f = _py + (f * _ph);
            t = _py + (t * _ph);

            fr = _px + (fr * _pw);
            tr = _px + (tr * _pw);

            _g.line((int) getX(fr, f), (int) getY(fr, f), (int) getX(fr, f), (int) getY(tr, t));
        }
    }

    /**
     *
     * @param _g
     * @param ft
     * @param tt
     */
    public void drawTimeTics(ICanvas _g, long ft, long tt) {

        long dt = tt - ft;

        double yh = 1d;
        yh -= 0.1d;
        //if (dt/(ty-fy) < 64) {
        boolean drewYear = false;
        for (long t = ft; t <= tt;) {
            long nt = UTime.yearLong(t, 0);
            if (nt > tt) {
                break;
            }
            double a = MinMaxLong.zeroToOne(ft, tt, nt);
            if (a >= 0 && a <= 1) {
                _g.setColor(AColor.darkGray);
                line(_g, a, 0, a, yh);
                _g.setColor(AColor.gray);


                drawString(_g, UV.fonts[UV.cSmall], UTime.year(nt), 0, 0, a, yh);
                drewYear = true;
            }
            t = UTime.yearLong(t, 1);
        }
        if (!drewYear) {
            _g.setColor(AColor.gray);
            drawString(_g, UV.fonts[UV.cSmall], UTime.year(ft + (dt / 2)), 0, 0, 0.5d, yh);
        }
        yh -= 0.1d;

        boolean drewMonth = false;
        //if (dt/(tm-fm) < 64) {
        for (long t = ft; t <= tt;) {
            long nt = UTime.monthLong(t, 0);
            if (nt > tt) {
                break;
            }
            double a = MinMaxLong.zeroToOne(ft, tt, nt);
            if (a >= 0 && a <= 1) {
                _g.setColor(AColor.blue);
                line(_g, a, 0, a, yh);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.monthName(nt), 0, 0, a, yh);
                drewMonth = true;
            }
            t = UTime.monthLong(t, 1);
            ;
        }
        if (!drewMonth) {
            _g.setColor(AColor.gray);
            drawString(_g, UV.fonts[UV.cSmall], UTime.monthName(ft + (dt / 2)), 0, 0, 0.5d, yh);
        }
        yh -= 0.1d;

        //if (dt/(td-fd) < 64) {
        for (long t = ft; t <= tt;) {
            long nt = UTime.dayLong(t, 0);
            if (nt > tt) {
                break;
            }
            double a = MinMaxLong.zeroToOne(ft, tt, nt);
            if (a >= 0 && a <= 1) {

                _g.setColor(AColor.salmon);
                line(_g, a, 0, a, yh);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.day(t), 0, 0, a, yh);
            }
            t = UTime.dayLong(t, 1);

        }
        //}
        yh -= 0.1d;

        long fh = UTime.hourLong(ft, 1);
        long th = UTime.hourLong(fh, 1);
        if (dt / (th - fh) < 48) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.hourLong(t, 0);
                if (nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                if (a >= 0 && a <= 1) {
                    _g.setColor(AColor.purple);
                    line(_g, a, 0, a, yh);
                    _g.setColor(AColor.gray);
                    drawString(_g, UV.fonts[UV.cSmall], UTime.hour(t), 0, 0, a, yh);
                }
                t = UTime.hourLong(t, 1);

            }
        }
        yh -= 0.1d;


        long fmin = UTime.minuteLong(ft, 1);
        long tmin = UTime.minuteLong(fmin, 1);
        if (dt / (tmin - fmin) < 120) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.minuteLong(t, 0);
                if (nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                if (a >= 0 && a <= 1) {

                    _g.setColor(AColor.yellow);
                    line(_g, a, 0, a, yh);
                    _g.setColor(AColor.gray);
                    drawString(_g, UV.fonts[UV.cSmall], UTime.minute(t), 0, 0, a, yh);
                }
                t = UTime.minuteLong(t, 1);

            }
        }
        yh -= 0.1d;

        long fsec = UTime.secondLong(ft, 1);
        long tsec = UTime.secondLong(fsec, 1);
        if (dt / (tsec - fsec) < 120) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.secondLong(t, 0);
                if (nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                if (a >= 0 && a <= 1) {
                    _g.setColor(AColor.brown);
                    line(_g, a, 0, a, yh);
                    _g.setColor(AColor.gray);
                    drawString(_g, UV.fonts[UV.cSmall], UTime.second(t), 0, 0, a, yh);
                }
                t = UTime.secondLong(t, 1);

            }
        }
        yh -= 0.1d;
    }

    /**
     *
     * @param _g
     * @param ft
     * @param tt
     */
    public void drawVerticalTimeTics(ICanvas _g, long ft, long tt) {
        long dt = tt - ft;

        double yh = 1d;
        yh -= 0.1d;
        long fy = UTime.yearLong(ft, 0);
        long ty = UTime.yearLong(fy, 1);
        if (dt / (ty - fy) < 64) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.yearLong(t, 0);
                if (nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.darkGray);
                line(_g, 0, a, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.year(nt), 0, 0, yh, a);
                t = UTime.yearLong(t, 1);
            }
        }
        yh -= 0.1d;

        long fm = UTime.monthLong(ft, 0);
        long tm = UTime.monthLong(fm, 1);
        if (dt / (tm - fm) < 64) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.monthLong(t, 0);
                if (nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.blue);
                line(_g, 0, a, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.monthName(nt), 0, 0, yh, a);
                t = UTime.monthLong(t, 1);
            }
        }
        yh -= 0.1d;

        long fd = UTime.dayLong(ft, 0);
        long td = UTime.dayLong(fd, 1);
        if (dt / (td - fd) < 64) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.dayLong(t, 0);
                if (nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.salmon);
                line(_g, 0, a, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.day(t), 0, 0, yh, a);
                t = UTime.dayLong(t, 1);
            }
        }
        yh -= 0.1d;

        long fh = UTime.hourLong(ft, 0);
        long th = UTime.hourLong(fh, 1);
        if (dt / (th - fh) < 48) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.hourLong(t, 0);
                if (nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.purple);
                line(_g, 0, a, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.hour(t), 0, 0, yh, a);
                t = UTime.hourLong(t, 1);
            }
        }
        yh -= 0.1d;


        long fmin = UTime.minuteLong(ft, 0);
        long tmin = UTime.minuteLong(fmin, 1);
        if (dt / (tmin - fmin) < 120) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.minuteLong(t, 0);
                if (nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.yellow);
                line(_g, 0, a, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.minute(t), 0, 0, yh, a);
                t = UTime.minuteLong(t, 1);
            }
        }
        yh -= 0.1d;

        long fsec = UTime.secondLong(ft, 0);
        long tsec = UTime.secondLong(fsec, 1);
        if (dt / (tsec - fsec) < 120) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.secondLong(t, 0);
                if (nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.brown);
                line(_g, 0, a, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.second(t), 0, 0, yh, a);
                t = UTime.secondLong(t, 1);
            }
        }
        yh -= 0.1d;
    }
}
