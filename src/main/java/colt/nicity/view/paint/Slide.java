/*
 * Slide.java.java
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
import colt.nicity.view.image.IImage;
import colt.nicity.core.lang.MinMaxDouble;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.Layer;
import colt.nicity.view.core.UV;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IPaintable;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class Slide {

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
    private Flip flip = new Flip();
    private double fromX = 0, toX = 1;
    private double fromY = 0, toY = 1;
    MinMaxDouble xSlide = new MinMaxDouble();
    MinMaxDouble ySlide = new MinMaxDouble();

    /**
     *
     * @param _nx
     * @param _ny
     * @param _nw
     * @param _nh
     * @param _fromX
     * @param _toX
     * @param _fromY
     * @param _toY
     */
    public Slide(
            double _nx, double _ny, double _nw, double _nh,
            double _fromX, double _toX, double _fromY, double _toY) {
        nx = _nx;
        ny = _ny;
        nw = _nw;
        nh = _nh;
        fromX = _fromX;
        toX = _toX;
        fromY = _fromY;
        toY = _toY;
        xSlide.value(fromX);
        xSlide.value(toX);
        ySlide.value(fromY);
        ySlide.value(toY);
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

    /**
     *
     * @param _nx
     * @param _ny
     */
    public void shiftXY(double _nx, double _ny) {
        nx += _nx;
        ny += _ny;
    }

    /**
     *
     * @param _nx
     * @param _ny
     */
    public void setXY(double _nx, double _ny) {
        nx = _nx;
        ny = _ny;
    }

    /**
     *
     * @param _nw
     * @param _nh
     */
    public void setWH(double _nw, double _nh) {
        nw = _nw;
        nh = _nh;
    }

    // Square Methods
    /**
     *
     * @param _x
     * @return
     */
    public double getX(// return x,y
            double _x// x y z 0->1 x=0 leftmost y=0 bottommost, z=0 closest
            ) {
        _x = flip.flipX(_x);
        return nx + (xSlide.zeroToOne(_x) * nw);
    }

    /**
     *
     * @param _y
     * @return
     */
    public double getY(// return x,y
            double _y// x y z 0->1 x=0 leftmost y=0 bottommost, z=0 closest
            ) {
        _y = flip.flipY(_y);
        _y = 1 - _y;//??
        return ny + (ySlide.zeroToOne(_y) * nh);
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
    public void oval(
            ICanvas _g, boolean _fill,
            double _x, double _y,
            double _w, double _h) {
        _g.oval(_fill, (int) (getX(_x) - (_w / 2)), (int) (getY(_y) - (_h / 2)), (int) _w, (int) _h);
    }

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
        _g.line((int) getX(_fx), (int) getY(_fy), (int) getX(_tx), (int) getY(_ty));
    }

    /**
     *
     * @param _g
     * @param _linkDrawer
     * @param _rank
     * @param _size
     * @param _fx
     * @param _fy
     * @param _tx
     * @param _ty
     */
    public void drawLink(
            ICanvas _g, LinkDrawer _linkDrawer, double _rank, int _size,
            double _fx, double _fy,
            double _tx, double _ty) {
        _linkDrawer.draw(_g, new XY_I((int) getX(_fx), (int) getY(_fy)), new XY_I((int) getX(_tx), (int) getY(_ty)), _rank, _size);
    }

    /**
     *
     * @param _g
     * @param _fill
     * @param _fx
     * @param _fy
     * @param _tx
     * @param _ty
     */
    public void rect(
            ICanvas _g, boolean _fill,
            double _fx, double _fy,
            double _tx, double _ty) {
        rect(_g, _fill,
                _fx, _fy,
                _tx, _fy,
                _tx, _ty,
                _fx, _ty);
    }

    /**
     *
     * @param _g
     * @param _fill
     * @param _ax
     * @param _ay
     * @param _bx
     * @param _by
     * @param _cx
     * @param _cy
     * @param _dx
     * @param _dy
     */
    public void rect(
            ICanvas _g, boolean _fill,
            double _ax, double _ay,
            double _bx, double _by,
            double _cx, double _cy,
            double _dx, double _dy) {
        int[] xps = new int[]{(int) getX(_ax), (int) getX(_bx), (int) getX(_cx), (int) getX(_dx)};
        int[] yps = new int[]{(int) getY(_ay), (int) getY(_by), (int) getY(_cy), (int) getY(_dy)};
        _g.polygon(_fill, xps, yps, 4);
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
     */
    public void drawString(
            ICanvas _g, AFont _font,
            String _string, double _alignX, double _alignY,
            double _x, double _y) {
        _g.setFont(_font);
        float _fw = _font.getW(_string);
        float _fh = _font.getH(_string);
        int x = (int) flip.subtractX(getX(_x), (_fw * _alignX));
        int y = (int) flip.addY(getY(_y), (_fh * _alignY));
        _g.drawString(_string, x, y);
    }

    /**
     *
     * @param _view
     * @param _alignX
     * @param alignY
     * @param _g
     * @param _x
     * @param _y
     */
    public void drawView(IView _view, double _alignX, double alignY,
            ICanvas _g, double _x, double _y) {
        _view.layoutInterior();

        int vw = (int) _view.getW();
        int vh = (int) _view.getH();

        float w = vw;
        float h = vh;

        int x = (int) flip.subtractX(getX(_x), (vw / 2) + ((vw / 2) * _alignX));
        int y = (int) flip.addY(getY(_y), (vh / 2) - ((vh / 2) * alignY));


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
     */
    public void drawView(IView _view,
            ICanvas _g, double _x, double _y) {
        _view.layoutInterior();

        int vw = (int) _view.getW();
        int vh = (int) _view.getH();

        float w = vw;
        float h = vw;

        int x = (int) getX(_x);
        int y = (int) getY(_y);

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
     */
    public void drawImage(
            IImage _image, double _alignX, double _alignY,
            ICanvas _g, double _x, double _y) {
        int iw = _image.getWidth();
        int ih = _image.getHeight();

        double h = iw;
        double w = ih;

        int x = (int) (getX(_x) - (w / 2) + ((w / 2) * _alignX));
        int y = (int) (getY(_y) + (h / 2) - ((h / 2) * _alignY));

        _g.drawImage(_image, (int) (x - (w / 2)), (int) (y - (h / 2)), (int) w, (int) h, null);
    }

    /**
     *
     * @param _image
     * @param _alignX
     * @param _alignY
     * @param _g
     * @param _x
     * @param _y
     */
    public void drawImage(
            IPaintable _image, double _alignX, double _alignY,
            ICanvas _g, double _x, double _y) {
        int iw = (int) _image.getW(null, null);
        int ih = (int) _image.getH(null, null);

        double h = iw;//getZH(ih,_z);
        double w = ih;//getZW(iw,_z);

        int x = (int) (getX(_x) - (w / 2) + ((w / 2) * _alignX));
        int y = (int) (getY(_y) + (h / 2) - ((h / 2) * _alignY));


        _image.paint(_g, null);
    }
}
