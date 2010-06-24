/*
 * Tunnel.java.java
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

import colt.nicity.view.ngraph.LinkDrawer;
import colt.nicity.core.lang.MinMaxLong;
import colt.nicity.core.lang.UDouble;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.core.time.UTime;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.Layer;
import colt.nicity.view.core.UV;
import colt.nicity.view.image.IImage;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IPaintable;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class Tunnel {

    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public double nx, ny, nw, nh;//read only please
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    /**
     *
     */
    public double fx, fy, fw, fh;//read only please
    private double rw = 1, rh = 1;//near far ratios
    private Flip flip = new Flip();

    /**
     *
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _ratio
     */
    public Tunnel(
            double _x, double _y, double _w, double _h,
            double _ratio) {
        this(
                _x, _y,
                _w, _h,
                _x + ((_w / 2) - ((_w * _ratio) / 2)), _y + ((_h / 2) - ((_h * _ratio) / 2)),
                (_w * _ratio), (_h * _ratio));
    }

    /**
     *
     * @param _nx
     * @param _ny
     * @param _nw
     * @param _nh
     * @param _fx
     * @param _fy
     * @param _fw
     * @param _fh
     */
    public Tunnel(
            double _nx, double _ny, double _nw, double _nh,
            double _fx, double _fy, double _fw, double _fh) {
        nx = _nx;
        ny = _ny;
        nw = _nw;
        nh = _nh;
        fx = _fx;
        fy = _fy;
        fw = _fw;
        fh = _fh;
        computeRatio();
    }

    /**
     *
     * @return
     */
    public Flip getFlip() {
        return flip;
    }

    /**
     *
     * @param _flip
     */
    public void setFlip(Flip _flip) {
        flip = _flip;
    }

    private void computeRatio() {
        rw = fw / nw;
        rh = fh / nh;
    }

    private void maintainRatio() {
        fw = nw * rw;
        fh = nh * rh;
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public void setWH(double _w, double _h) {
        nw = _w;
        nh = _h;
        maintainRatio();
    }

    /**
     *
     * @param _nx
     * @param _ny
     */
    public void shiftNearXY(double _nx, double _ny) {
        nx += _nx;
        ny += _ny;
    }

    /**
     *
     * @param _fx
     * @param _fy
     */
    public void shiftFarXY(double _fx, double _fy) {
        fx += _fx;
        fy += _fy;
    }

    /**
     *
     * @param _nx
     * @param _ny
     */
    public void setNearXY(double _nx, double _ny) {
        nx = _nx;
        ny = _ny;
    }

    /**
     *
     * @param _nw
     * @param _nh
     */
    public void setNearWH(double _nw, double _nh) {
        nw = _nw;
        nh = _nh;
        computeRatio();
    }

    /**
     *
     * @param _fx
     * @param _fy
     */
    public void setFarXY(double _fx, double _fy) {
        fx = _fx;
        fy = _fy;
    }

    /**
     *
     * @param _fw
     * @param _fh
     */
    public void setFarWH(double _fw, double _fh) {
        fw = _fw;
        fh = _fh;
        computeRatio();
    }

    // Round Methods
    /**
     *
     * @param _a
     * @param _r
     * @param _z
     * @return
     */
    public double getX(
            double _a, double _r, double _z// a r z 0->1 a=0 straigt up r=0 center, z=0 closest
            ) {
        _z = flip.flipZ(_z);

        double h = getZH(0.5d, _z);
        double w = getZW(0.5d, _z);
        double cx = getX(0.5d, _z);
        double cy = getY(0.5d, _z);

        double radians = (Math.PI * 2) * _a;

        double rx = cx + Math.cos(radians) * ((w / 2) * _r);
        double ry = cy + Math.sin(radians) * ((h / 2) * _r);

        return rx;
    }

    /**
     *
     * @param _a
     * @param _r
     * @param _z
     * @return
     */
    public double getY(
            double _a, double _r, double _z// a r z 0->1 a=0 straigt up r=0 center, z=0 closest
            ) {
        _z = flip.flipZ(_z);

        double h = getZH(0.5d, _z);
        double w = getZW(0.5d, _z);
        double cx = getX(0.5d, _z);
        double cy = getY(0.5d, _z);

        double radians = (Math.PI * 2) * _a;

        double rx = cx + Math.cos(radians) * ((w / 2) * _r);
        double ry = cy + Math.sin(radians) * ((h / 2) * _r);

        return ry;
    }

    /**
     *
     * @param _a
     * @param _r
     * @param _z
     * @return
     */
    public double getR(// returns radius
            double _a, double _r, double _z// a r z 0->1 a=0 straigt up r=0 center, z=0 closest
            ) {
        return 0;
    }

    // Square Methods
    /**
     *
     * @param _x
     * @param _z
     * @return
     */
    public double getX(// return x,y
            double _x, double _z// x y z 0->1 x=0 leftmost y=0 bottommost, z=0 closest
            ) {
        _x = flip.flipX(_x);
        _z = flip.flipZ(_z);

        return UDouble.linearInterpolation((nx + (nw * _x)), (fx + (fw * _x)), _z);
    }

    /**
     *
     * @param _y
     * @param _z
     * @return
     */
    public double getY(// return x,y
            double _y, double _z// x y z 0->1 x=0 leftmost y=0 bottommost, z=0 closest
            ) {
        _y = flip.flipY(_y);
        _z = flip.flipZ(_z);

        _y = 1 - _y;//??
        return UDouble.linearInterpolation((ny + (nh * _y)), (fy + (fh * _y)), _z);
    }

    /**
     *
     * @param _x
     * @param _z
     * @return
     */
    public double getZH(// return x,y
            double _x, double _z// x y z 0->1 x=0 leftmost y=0 bottommost, z=0 closest
            ) {
        _x = flip.flipX(_x);
        _z = flip.flipZ(_z);
        return UDouble.distance(getX(_x, _z), getY(0, _z), getX(_x, _z), getY(1, _z));
    }

    /**
     *
     * @param _y
     * @param _z
     * @return
     */
    public double getZW(// return x,y
            double _y, double _z// x y z 0->1 x=0 leftmost y=0 bottommost, z=0 closest
            ) {
        return UDouble.distance(getX(0, _z), getY(_y, _z), getX(1, _z), getY(_y, _z));
    }

    /**
     *
     * @param _g
     * @param _fill
     * @param _x
     * @param _y
     * @param _z
     * @param _w
     * @param _h
     */
    public void oval(
            ICanvas _g, boolean _fill,
            double _x, double _y, double _z,
            double _w, double _h) {
        _w *= 1 - _z;
        _h *= 1 - _z;
        _g.oval(_fill, (int) (getX(_x, _z) - (_w / 2)), (int) (getY(_y, _z) - (_h / 2)), (int) _w, (int) _h);
    }

    /**
     *
     * @param _g
     * @param _fx
     * @param _fy
     * @param _fz
     * @param _tx
     * @param _ty
     * @param _tz
     */
    public void line(
            ICanvas _g,
            double _fx, double _fy, double _fz,
            double _tx, double _ty, double _tz) {
        _g.line((int) getX(_fx, _fz), (int) getY(_fy, _fz), (int) getX(_tx, _tz), (int) getY(_ty, _tz));
    }

    /**
     *
     * @param _g
     * @param _linkDrawer
     * @param _rank
     * @param _size
     * @param _fx
     * @param _fy
     * @param _fz
     * @param _tx
     * @param _ty
     * @param _tz
     */
    public void drawLink(
            ICanvas _g, LinkDrawer _linkDrawer, double _rank, int _size,
            double _fx, double _fy, double _fz,
            double _tx, double _ty, double _tz) {
        _linkDrawer.draw(_g, new XY_I((int) getX(_fx, _fz), (int) getY(_fy, _fz)), new XY_I((int) getX(_tx, _tz), (int) getY(_ty, _tz)), _rank, _size);
    }

    /**
     *
     * @param _g
     * @param _fill
     * @param _fx
     * @param _fy
     * @param _tx
     * @param _ty
     * @param _z
     */
    public void rect(
            ICanvas _g, boolean _fill,
            double _fx, double _fy,
            double _tx, double _ty,
            double _z) {
        rect(_g, _fill,
                _fx, _fy, _z,
                _tx, _fy, _z,
                _tx, _ty, _z,
                _fx, _ty, _z);
    }

    /**
     *
     * @param _g
     * @param _fill
     * @param _ax
     * @param _ay
     * @param _az
     * @param _bx
     * @param _by
     * @param _bz
     * @param _cx
     * @param _cy
     * @param _cz
     * @param _dx
     * @param _dy
     * @param _dz
     */
    public void rect(
            ICanvas _g, boolean _fill,
            double _ax, double _ay, double _az,
            double _bx, double _by, double _bz,
            double _cx, double _cy, double _cz,
            double _dx, double _dy, double _dz) {
        int[] xps = new int[]{(int) getX(_ax, _az), (int) getX(_bx, _bz), (int) getX(_cx, _cz), (int) getX(_dx, _dz)};
        int[] yps = new int[]{(int) getY(_ay, _az), (int) getY(_by, _bz), (int) getY(_cy, _cz), (int) getY(_dy, _dz)};
        _g.polygon(_fill, xps, yps, 4);
    }

    /**
     *
     * @param _g
     * @param _fill
     * @param _ax
     * @param _ay
     * @param _az
     * @param _bx
     * @param _by
     * @param _bz
     */
    public void drawCube(
            ICanvas _g, boolean _fill,
            double _ax, double _ay, double _az,//front upper left corner
            double _bx, double _by, double _bz//back lower right corner
            ) {
        rect(_g, _fill, _ax, _ay, _bz, _bx, _ay, _bz, _bx, _by, _bz, _ax, _by, _bz);

        rect(_g, _fill, _ax, _ay, _az, _ax, _ay, _bz, _bx, _ay, _bz, _bx, _ay, _az);
        rect(_g, _fill, _bx, _ay, _az, _bx, _ay, _bz, _bx, _by, _bz, _bx, _by, _az);
        rect(_g, _fill, _bx, _by, _az, _bx, _by, _bz, _ax, _by, _bz, _ax, _by, _az);
        rect(_g, _fill, _ax, _by, _az, _ax, _by, _bz, _ax, _ay, _bz, _ax, _ay, _az);

        rect(_g, _fill, _ax, _ay, _az, _bx, _ay, _az, _bx, _by, _az, _ax, _by, _az);

    }

    /**
     *
     * @param _g
     * @param _fill
     * @param _ax
     * @param _ay
     * @param _az
     * @param _bx
     * @param _by
     * @param _bz
     */
    public void drawOpenBox(
            ICanvas _g, boolean _fill,
            double _ax, double _ay, double _az,//front upper left corner
            double _bx, double _by, double _bz//back lower right corner
            ) {
        //rect(_g,_fill,_ax,_ay,_bz,_bx,_ay,_bz,_bx,_by,_bz,_ax,_by,_bz);

        rect(_g, _fill, _ax, _ay, _az, _ax, _ay, _bz, _bx, _ay, _bz, _bx, _ay, _az);
        rect(_g, _fill, _bx, _ay, _az, _bx, _ay, _bz, _bx, _by, _bz, _bx, _by, _az);
        rect(_g, _fill, _bx, _by, _az, _bx, _by, _bz, _ax, _by, _bz, _ax, _by, _az);
        rect(_g, _fill, _ax, _by, _az, _ax, _by, _bz, _ax, _ay, _bz, _ax, _ay, _az);

        //rect(_g,_fill,_ax,_ay,_az,_bx,_ay,_az,_bx,_by,_az,_ax,_by,_az);

    }

    /**
     *
     * @param _g
     * @param _fill
     * @param _ax
     * @param _ay
     * @param _az
     * @param _bx
     * @param _by
     * @param _bz
     */
    public void drawWindow(
            ICanvas _g, boolean _fill,
            double _ax, double _ay, double _az,//front upper left corner
            double _bx, double _by, double _bz//back lower right corner
            ) {
        //rect(_g,_fill,_ax,_ay,_bz,_bx,_ay,_bz,_bx,_by,_bz,_ax,_by,_bz);

        rect(_g, _fill, 0, _ay, _az, _ax, _ay, _az, _ax, _by, _az, 0, _by, _az);
        rect(_g, _fill, _bx, _ay, _az, 1, _ay, _az, 1, _by, _az, _bx, _by, _az);


        rect(_g, _fill, 0, 0, _az, 1, 0, _az, 1, _ay, _az, 0, _ay, _az);

        rect(_g, _fill, 0, _by, _az, 1, _by, _az, 1, 1, _az, 0, 1, _az);


        rect(_g, _fill, _ax, _ay, _az, _ax, _ay, _bz, _bx, _ay, _bz, _bx, _ay, _az);
        rect(_g, _fill, _bx, _ay, _az, _bx, _ay, _bz, _bx, _by, _bz, _bx, _by, _az);
        rect(_g, _fill, _bx, _by, _az, _bx, _by, _bz, _ax, _by, _bz, _ax, _by, _az);
        rect(_g, _fill, _ax, _by, _az, _ax, _by, _bz, _ax, _ay, _bz, _ax, _ay, _az);

        //rect(_g,_fill,_ax,_ay,_az,_bx,_ay,_az,_bx,_by,_az,_ax,_by,_az);

    }

    /**
     *
     * @param _g
     * @param _font
     * @param _string
     * @param _alignX
     * @param _alignY
     * @param _x
     * @param _y
     * @param _z
     */
    public void drawString(
            ICanvas _g, AFont _font,
            String _string, double _alignX, double _alignY,
            double _x, double _y, double _z) {
        _g.setFont(_font);
        float _fw = _font.getW(_string);
        float _fh = _font.getH(_string);
        int x = (int) flip.subtractX(getX(_x, _z), (_fw * _alignX));
        int y = (int) flip.addY(getY(_y, _z), (_fh * _alignY));
        _g.drawString(_string, x, y);
    }

    /**
     *
     * @param _g
     */
    public void drawTunnel(ICanvas _g) {
        _g.line((int) nx, (int) ny, (int) (fx), (int) (fy));
        _g.line((int) (nx + nw), (int) ny, (int) (fx + fw), (int) (fy));
        _g.line((int) nx, (int) (ny + nh), (int) (fx), (int) (fy + fh));
        _g.line((int) (nx + nw), (int) (ny + nh), (int) (fx + fw), (int) (fy + fh));

        _g.line((int) nx, (int) ny, (int) (nx), (int) (ny + nh));
        _g.line((int) nx, (int) ny, (int) (nx + nw), (int) (ny));
        _g.line((int) (nx + nw), (int) (ny + nh), (int) (nx + nw), (int) (ny));
        _g.line((int) (nx + nw), (int) (ny + nh), (int) (nx), (int) (ny + nh));

        _g.line((int) fx, (int) fy, (int) (fx), (int) (fy + fh));
        _g.line((int) fx, (int) fy, (int) (fx + fw), (int) (fy));
        _g.line((int) (fx + fw), (int) (fy + fh), (int) (fx + fw), (int) (fy));
        _g.line((int) (fx + fw), (int) (fy + fh), (int) (fx), (int) (fy + fh));

    }

    /**
     *
     * @param _g
     * @param _segments
     */
    public void drawTube(ICanvas _g, int _segments) {
        double step = 1d / _segments;
        for (double i = 0; i <= 1; i += step) {
            _g.line((int) getX(i, 1, 0), (int) getY(i, 1, 0), (int) getX(i, 1, 1), (int) getY(i, 1, 1));
            _g.line((int) getX(i, 1, 0), (int) getY(i, 1, 0), (int) getX(i + step, 1, 0), (int) getY(i + step, 1, 0));
            _g.line((int) getX(i, 1, 1), (int) getY(i, 1, 1), (int) getX(i + step, 1, 1), (int) getY(i + step, 1, 1));
        }
    }

    /**
     *
     * @param _view
     * @param _alignX
     * @param alignY
     * @param _g
     * @param _x
     * @param _y
     * @param _z
     */
    public void drawView(IView _view, double _alignX, double alignY,
            ICanvas _g, double _x, double _y, double _z) {
        _view.layoutInterior();

        int vw = (int) _view.getW();
        int vh = (int) _view.getH();

        float w = (float) getZW(vw, _z);
        float h = (float) getZH(vh, _z);

        int x = (int) flip.subtractX(getX(_x, _z), (vw / 2) + ((vw / 2) * _alignX));
        int y = (int) flip.addY(getY(_y, _z), (vh / 2) - ((vh / 2) * alignY));


        Object clip = _g.getClip();
        _g.translate(x, y);
        _view.paint(
                _view, _g,
                new Layer(null, 0f, 0f, 0f, 0f, w, h),
                UV.cRepair,
                new XYWH_I(0, 0, 0, 0));
        _g.translate(-(x), -(y));
        _g.setClip(clip);
    }

    /**
     *
     * @param _view
     * @param _g
     * @param _x
     * @param _y
     * @param _z
     */
    public void drawView(IView _view,
            ICanvas _g, double _x, double _y, double _z) {
        _view.layoutInterior();

        int vw = (int) _view.getW();
        int vh = (int) _view.getH();

        float w = (float) getZW(vw, _z);
        float h = (float) getZH(vh, _z);

        int x = (int) getX(_x, _z);
        int y = (int) getY(_y, _z);

        Object clip = _g.getClip();
        _g.translate(x, y);
        _view.paint(
                _view, _g,
                new Layer(null, 0f, 0f, 0f, 0f, w, h),
                UV.cRepair,
                new XYWH_I(0, 0, 0, 0));
        _g.translate(-(x), -(y));
        _g.setClip(clip);
    }

    /**
     *
     * @param _image
     * @param _alignX
     * @param _alignY
     * @param _g
     * @param _x
     * @param _y
     * @param _z
     */
    public void drawImage(
            IImage _image, double _alignX, double _alignY,
            ICanvas _g, double _x, double _y, double _z) {
        int iw = _image.getWidth();
        int ih = _image.getHeight();

        double h = getZH(ih, _z);
        double w = getZW(iw, _z);

        int x = (int) (getX(_x, _z) - (w / 2) + ((w / 2) * _alignX));
        int y = (int) (getY(_y, _z) + (h / 2) - ((h / 2) * _alignY));

        _g.drawImage(_image, (int) (x - (w / 2)), (int) (y - (h / 2)), (int) w, (int) h, null);
    }

    /**
     *
     * @param _paintable
     * @param _alignX
     * @param _alignY
     * @param _g
     * @param _x
     * @param _y
     * @param _z
     */
    public void drawImage(
            IPaintable _paintable, double _alignX, double _alignY,
            ICanvas _g, double _x, double _y, double _z) {
        int iw = (int) _paintable.getW(null, null);
        int ih = (int) _paintable.getH(null, null);

        double h = iw;//getZH(ih,_z);
        double w = ih;//getZW(iw,_z);

        int x = (int) (getX(_x, _z) - (w / 2) + ((w / 2) * _alignX));
        int y = (int) (getY(_y, _z) + (h / 2) - ((h / 2) * _alignY));


        _paintable.paint(_g, null);
    }

    /**
     *
     * @param _g
     * @param _w
     * @param _h
     * @param _z
     */
    public void drawEllipse(ICanvas _g, double _w, double _h, double _z) {
        double w = getZW(0.5, _z) * _w;
        double h = getZH(0.5, _z) * _h;
        double x = getX(0.5, _z);
        double y = getY(0.5, _z);

        double resolution = ((Math.PI * 2) / 50);
        for (double r = 0; r < Math.PI * 2; r += resolution) {
            int fx = (int) ((Math.cos(r) * w) + x);
            int fy = (int) ((Math.sin(r) * h) + y);

            int tx = (int) ((Math.cos(r + resolution) * w) + x);
            int ty = (int) ((Math.sin(r + resolution) * h) + y);

            _g.line(fx, fy, tx, ty);
        }
    }

    /**
     *
     * @param _title
     * @param _color
     * @param wave
     * @param _alpha
     * @param _px
     * @param _pw
     * @param _py
     * @param _ph
     * @param _start
     * @param _end
     * @param _resolution
     * @param _g
     * @param _w
     * @param _h
     */
    public void paintWaveform(
            IView _title, AColor _color, double[] wave, float _alpha,
            double _px, double _pw, double _py, double _ph,
            double _start, double _end, int _resolution,
            ICanvas _g, int _w, int _h) {

        double i = Math.abs((_end - _start) / _resolution);

        int h = _h;

        _g.setAlpha(_alpha, 0);

        boolean title = false;
        boolean titled = false;
        double l = 0;
        for (int z = 0; z < _resolution; _start += i, z++) {
            if (_start < 0) {
                continue;
            }
            if (_start > 1) {
                continue;
            }
            double f = UDouble.linearInterpolation(wave, _start, -1);
            double t = UDouble.linearInterpolation(wave, _start + i, -1);

            if (l < f && f > t) {
                title = true;
            } else {
                title = false;
            }
            l = f;

            double fv = _py + (f * _ph);
            double tv = _py + (t * _ph);


            double fpz = (double) z / (double) _resolution;
            double tpz = (double) (z + 1) / (double) _resolution;

            double ps = _px;
            double pe = ps + _pw;

            _g.setColor(_color.desaturate((float) flip.flipZ(fpz) * 0.25f));
            rect(_g, true, ps, fv, fpz, pe, fv, fpz, pe, tv, tpz, ps, tv, tpz);

            _g.setColor(_color);
            line(_g, ps, fv, fpz, ps, tv, tpz);
            line(_g, pe, fv, fpz, pe, tv, tpz);

            if (!titled && title && _title != null) {
                titled = true;
                AFont font = new AFont(AFont.cPlain, 10 + (int) ((1 - fpz) * 22));
                _g.setColor(AColor.black);
                _g.setAlpha(1 - (float) fpz, 0);

                double cl = ps + ((pe - ps) / 2);
                line(_g, ps, fv, fpz, pe, fv, fpz);
                drawString(_g, font, _title.toString(), 0, 0, ps, fv, fpz);
                //drawView(_title,_g,ps,fv,fpz);
                title = false;
                _g.setAlpha(_alpha, 0);
            }
        }

        _g.setAlpha(1f, 0);

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
        long fy = UTime.yearLong(ft, 1);
        long ty = UTime.yearLong(fy, 1);
        if (dt / (ty - fy) < 64) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.yearLong(t, 1);
                if (nt == t || nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.darkGray);
                line(_g, 0, 0, a, 0, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.year(nt), 0, 0, 0, yh, a);
                t = nt;
            }
        }
        yh -= 0.1d;

        long fm = UTime.monthLong(ft, 1);
        long tm = UTime.monthLong(fm, 1);
        if (dt / (tm - fm) < 64) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.monthLong(t, 1);
                if (nt == t || nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.blue);
                line(_g, 0, 0, a, 0, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.monthName(nt), 0, 0, 0, yh, a);
                t = nt;
            }
        }
        yh -= 0.1d;

        long fd = UTime.dayLong(ft, 1);
        long td = UTime.dayLong(fd, 1);
        if (dt / (td - fd) < 64) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.dayLong(t, 1);
                if (nt == t || nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.salmon);
                line(_g, 0, 0, a, 0, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.day(t), 0, 0, 0, yh, a);
                t = nt;
            }
        }
        yh -= 0.1d;

        long fh = UTime.hourLong(ft, 1);
        long th = UTime.hourLong(fh, 1);
        if (dt / (th - fh) < 48) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.hourLong(t, 1);
                if (nt == t || nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.purple);
                line(_g, 0, 0, a, 0, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.hour(t), 0, 0, 0, yh, a);
                t = nt;
            }
        }
        yh -= 0.1d;


        long fmin = UTime.minuteLong(ft, 1);
        long tmin = UTime.minuteLong(fmin, 1);
        if (dt / (tmin - fmin) < 120) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.minuteLong(t, 1);
                if (nt == t || nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.yellow);
                line(_g, 0, 0, a, 0, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.minute(t), 0, 0, 0, yh, a);
                t = nt;
            }
        }
        yh -= 0.1d;

        long fsec = UTime.secondLong(ft, 1);
        long tsec = UTime.secondLong(fsec, 1);
        if (dt / (tsec - fsec) < 120) {
            for (long t = ft; t <= tt;) {
                long nt = UTime.secondLong(t, 1);
                if (nt == t || nt > tt) {
                    break;
                }
                double a = MinMaxLong.zeroToOne(ft, tt, nt);
                _g.setColor(AColor.brown);
                line(_g, 0, 0, a, 0, yh, a);
                _g.setColor(AColor.gray);
                drawString(_g, UV.fonts[UV.cSmall], UTime.second(t), 0, 0, 0, yh, a);
                t = nt;
            }
        }
        yh -= 0.1d;
    }
}
