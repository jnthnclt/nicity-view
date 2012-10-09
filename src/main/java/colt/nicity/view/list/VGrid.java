/*
 * VGrid.java.java
 *
 * Created on 01-03-2010 01:34:24 PM
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
package colt.nicity.view.list;

import colt.nicity.view.paint.Tunnel;
import colt.nicity.view.awt.UAWT;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.threeD.Object3D;
import colt.nicity.view.threeD.U3D;
import colt.nicity.core.collection.IBackcall;
import colt.nicity.core.memory.struct.IXYZ;
import colt.nicity.core.memory.struct.WH_F;
import colt.nicity.core.memory.struct.XYZ_D;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.Flex;
import colt.nicity.view.core.NullView;
import colt.nicity.view.interfaces.IAlignableItem;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IListController;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IVItem;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VGrid extends VList implements IMouseMotionEvents {

    /**
     *
     */
    protected float[] maxW;
    /**
     *
     */
    protected float[] maxH;
    /**
     *
     */
    protected float[] rings;
    /**
     *
     */
    protected float xspacing = 0.0f;
    /**
     *
     */
    protected float yspacing = 0.0f;
    /**
     *
     */
    protected AColor lineColor;
    /**
     *
     */
    protected AColor midLineColor;
    /**
     *
     */
    protected boolean radial = false;

    /**
     *
     */
    public VGrid() {
    }

    /**
     *
     * @param _backcall
     */
    public VGrid(IBackcall _backcall) {
        super(_backcall, -1);
    }

    /**
     *
     * @param _backcall
     * @param _rows_colums
     */
    public VGrid(IBackcall _backcall, int _rows_colums) {
        super(_backcall, _rows_colums);
    }

    /**
     *
     * @param _controller
     * @param _rows_colums
     */
    public VGrid(IListController _controller, int _rows_colums) {
        super(_controller, _rows_colums);
    }

    /**
     *
     * @param _spacing
     */
    public void setSpacing(float _spacing) {
        xspacing = _spacing;
        yspacing = _spacing;
    }

    /**
     *
     * @param _spacing
     */
    public void setXSpacing(float _spacing) {
        xspacing = _spacing;
    }

    /**
     *
     * @param _spacing
     */
    public void setYSpacing(float _spacing) {
        yspacing = _spacing;
    }

    /**
     *
     * @param _lineColor
     */
    public void setLineColor(AColor _lineColor) {
        lineColor = _lineColor;
    }

    /**
     *
     * @param _midLineColor
     */
    public void setMidLineColor(AColor _midLineColor) {
        midLineColor = _midLineColor;
    }

    /**
     *
     * @param _radial
     */
    public void setRadial(boolean _radial) {
        radial = _radial;
    }
    boolean perspective = false;

    /**
     *
     * @param _perspective
     */
    public void setPerspective(boolean _perspective) {
        perspective = _perspective;
    }

    /**
     *
     * @param _amount
     */
    public void setPerspective(double _amount) {
        perspective = true;
        tunnel = new Tunnel(0, 0, eyeDistance, eyeDistance, _amount);
    }
    boolean threeD = false;
    boolean spherical = false;
    Object3D eye = new Object3D(new XYZ_D(0, 0, -1000));
    Object3D sphere = new Object3D(new XYZ_D(0, 0, 0));

    /**
     *
     * @param _3d
     */
    public void set3D(boolean _3d) {
        threeD = _3d;
    }

    /**
     *
     * @return
     */
    public boolean is3D() {
        return threeD;
    }
    int eyeDistance = 1000;
    /**
     *
     */
    public Tunnel tunnel = new Tunnel(0, 0, eyeDistance, eyeDistance, 0.75d);

    /**
     *
     * @param _3d
     * @param _eye
     * @param _w
     * @param _h
     */
    public void set3D(boolean _3d, int _eye, int _w, int _h) {
        threeD = _3d;
        eyeDistance = _eye;
        tunnel = new Tunnel(
                0, 0, _w, _h,
                (_w * 0.5) / 2d, (_h * Math.pow(0.5, 1.5)) / 2d, _w * 0.5, _h * 0.5);
        eye.setCenter(new XYZ_D(0, 0, -_eye));
    }
    int sphereSize = 1000;

    /**
     *
     * @param _spherical
     * @param _sphereSize
     */
    public void setSphere(boolean _spherical, int _sphereSize) {
        spherical = _spherical;
        threeD = spherical;
        sphereSize = _sphereSize;
        eye.setCenter(new XYZ_D(0, 0, -1000));
        sphere.setPoints(new XYZ_D[0]);
    }

    /**
     *
     * @param _e
     */
    @Override
    public void mouseDragged(MouseDragged _e) {
        if (threeD || spherical) {
            //sphere.change(0,0,0,Math.toRadians(_e.getDeltaY()),Math.toRadians(_e.getDeltaX()),0);
            if (_e.isShiftDown()) {
                sphere.change(0, 0, 0, Math.toRadians(-_e.getDeltaY()), 0, 0);
            } else {
                sphere.change(0, 0, 0, 0, Math.toRadians(-_e.getDeltaX()), 0);
            }
            paint();
        } else {
            super.mouseDragged(_e);
        }
    }
    /*
     *
     * public void picked(IEvent _e) { if (!spherical) spherical = true; else
     * spherical = false; layoutInterior(); flush();
    }
     */
    /**
     *
     */
    protected int dia = 0;
    /**
     *
     */
    protected int pad = 0;

    /**
     *
     * @param _dia
     * @param _pad
     */
    public void setDiameter(int _dia, int _pad) {
        dia = _dia;
        pad = _pad;
    }

    /**
     *
     * @param _who
     * @param item
     * @param direction
     * @return
     */
    @Override
    public IView transferFocusToNearestNeighbor(long _who, IVItem item, int direction) {
        if (controller == NullListController.cNull) {
            return NullView.cNull;
        }
        IVItem[] items = controller.getItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i] == item) {
                if (rows_colums > 0) {
                    if (direction == UAWT.cUp) {
                        if (i - 1 < 0) {
                            return NullView.cNull;
                        }
                        items[i - 1].grabFocus(_who);
                        return items[i - 1];
                    }
                    if (direction == UAWT.cDown) {
                        if (i + 1 == items.length) {
                            return NullView.cNull;
                        }
                        items[i + 1].grabFocus(_who);
                        return items[i + 1];
                    }
                    if (direction == UAWT.cLeft) {
                        if (i - Math.abs(rows_colums) < 0) {
                            return NullView.cNull;
                        }
                        items[i - Math.abs(rows_colums)].grabFocus(_who);
                        return items[i - Math.abs(rows_colums)];
                    }
                    if (direction == UAWT.cRight) {
                        if (i + Math.abs(rows_colums) == items.length) {
                            return NullView.cNull;
                        }
                        items[i + Math.abs(rows_colums)].grabFocus(_who);
                        return items[i + Math.abs(rows_colums)];
                    }
                } else {
                    if (direction == UAWT.cLeft) {
                        if (i - 1 < 0) {
                            return NullView.cNull;
                        }
                        items[i - 1].grabFocus(_who);
                        return items[i - 1];
                    }
                    if (direction == UAWT.cRight) {
                        if (i + 1 == items.length) {
                            return NullView.cNull;
                        }
                        items[i + 1].grabFocus(_who);
                        return items[i + 1];
                    }
                    if (direction == UAWT.cUp) {
                        if (i - Math.abs(rows_colums) < 0) {
                            return NullView.cNull;
                        }
                        items[i - Math.abs(rows_colums)].grabFocus(_who);
                        return items[i - Math.abs(rows_colums)];
                    }
                    if (direction == UAWT.cDown) {
                        if (i + Math.abs(rows_colums) == items.length) {
                            return NullView.cNull;
                        }
                        items[i + Math.abs(rows_colums)].grabFocus(_who);
                        return items[i + Math.abs(rows_colums)];
                    }
                }
            }
        }
        return NullView.cNull;
    }

    // Layout
    /**
     *
     * @param _parent
     * @param _flex
     */
    @Override
    public void layoutInterior(IView _parent, Flex _flex) {
        if (controller == NullListController.cNull) {
            w = _parent.getBorder().getW();
            h = _parent.getBorder().getH();
            return;
        }

        int x = (int) getBorder().getX();
        int y = (int) getBorder().getY();

        WH_F size = new WH_F(0, 0);

        if (threeD) {

            IVItem[] items = getItems();
            if (items == null) {
                w = _parent.getBorder().getW();
                h = _parent.getBorder().getH();
                return;
            }
            if (sphere.getCount() != items.length) {
                XYZ_D[] xyzs = new XYZ_D[items.length];
                for (int i = 0; i < items.length; i++) {
                    XYZ_D xyz = null;
                    if (items[i] instanceof IXYZ) {
                        IXYZ ixyz = (IXYZ) items[i];
                        xyz = new XYZ_D(ixyz.x(), ixyz.y(), ixyz.z());
                    } else {
                        xyz = new XYZ_D(0, 0, 0);
                    }
                    xyzs[i] = xyz;
                }
                if (spherical) {
                    U3D.sphere(sphere, xyzs, sphereSize);//UTessellate
                } else {
                    sphere.setPoints(xyzs);
                }
            }
            sphere.mapPoints(items);
            XYZ_D centerEye = eye.getCenter();
            XYZ_D centerObj = sphere.getCenter();


            //UXYZ.sort(items);

            int len = (items == null) ? 0 : items.length;
            for (int i = 0; i < len; i++) {
                IView view = items[i];
                if (view == null) {
                    continue;
                }
                if (view instanceof IHideable) {
                    if (((IHideable) view).isHidden()) {
                        continue;
                    }
                }
                IXYZ xyz = (IXYZ) items[i];

                view.setParentView(this);//??
                view.layoutInterior(_flex);
                float _x = (float) xyz.x();
                float _y = (float) xyz.y();
                float _z = (float) xyz.z();

                double tx = _x, ty = _y;
                if (perspective) {
                    tx = tunnel.getX(((_x + 1d) / 2d), ((_z + 1d) / 2d));
                    ty = tunnel.getY(((_y + 1d) / 2d), ((_z + 1d) / 2d));
                } else {
                    tx = tunnel.getX(((_x + 1d) / 2d), -1);
                    ty = tunnel.getY(((_y + 1d) / 2d), -1);
                }

                _x = (float) tx;
                _y = (float) ty;


                float vx = view.getX();
                float vy = view.getY();
                float vw = view.getW();
                float vh = view.getH();

                view.setLocation(_x, _y);
                size.max(vx + vw, +vy + vh);

            }

            w = size.getW() - getBorder().getX();//!!??
            h = size.getH() - getBorder().getY();//!!?? this offset is required do to placer

            if (spherical) {
                if (w < 400) {
                    w = 400 - getBorder().getX();
                }
                if (h < 400) {
                    h = 400 - getBorder().getY();
                }
            }
            return;

        }


        if (rows_colums != 0) {
            IVItem[] items = controller.getItems();
            int len = (items == null) ? 0 : items.length;

            if (rows_colums > 0) {
                int columCount = len / rows_colums;
                int rowCount = rows_colums;

                maxW = new float[columCount + 1];
                maxH = new float[rowCount + 1];
                rings = new float[rowCount + 1];

                int r = 0;
                int c = 0;
                for (int i = 0; i < len; i++, r++) {
                    if (r == rowCount) {
                        r = 0;
                        c++;
                    }

                    IView view = items[i];
                    if (view == null) {
                        continue;
                    }

                    view.setParentView(this);
                    view.layoutInterior(_flex);

                    maxW[c] = Math.max(maxW[c], view.getW());
                    maxH[r] = Math.max(maxH[r], view.getH());
                    rings[r] = (float) Math.max(rings[r], Math.max(view.getW(), view.getH()));
                    //rings[r] = (float)Math.max(rings[r],Math.sqrt(Math.pow(view.getW(),2)+Math.pow(view.getH(),2)));
                }

                if (radial) {
                    float origin = (dia > 0) ? dia : rings[0];

                    for (int i = 0; i < rings.length; i++) {
                        origin += (dia > 0) ? dia : rings[i];
                    }

                    origin += pad;

                    r = 0;
                    c = 0;
                    double _length = origin - pad;
                    float _direction = 0;
                    for (int i = 0; i < len; i++, r++) {
                        if (r == rowCount) {
                            _length = origin - pad;
                            r = 0;
                            c++;
                            _direction = ((float) c / (float) columCount) * 360;

                        }
                        float rr = (dia > 0) ? dia : rings[r];
                        _length -= rr;

                        IView view = items[i];
                        if (view == null) {
                            continue;
                        }

                        x = (int) origin + (int) (Math.sin(Math.toRadians(_direction + 90)) * (_length + (rr / 2)));
                        y = (int) origin + (int) (Math.sin(Math.toRadians(_direction)) * (_length + (rr / 2)));

                        x -= (rr / 2);
                        y -= (rr / 2);

                        if (view instanceof IAlignableItem) {
                            ((IAlignableItem) view).justify(x, y, rr, rr);
                        } else {
                            view.setLocation(x, y);
                        }

                        size.max(view.getX() + view.getW(), view.getY() + view.getH());

                    }
                    size.max((float) (origin * 2), (float) (origin * 2));


                } else {
                    r = 0;
                    c = 0;
                    for (int i = 0; i < len; i++, r++) {
                        if (r == rowCount) {
                            r = 0;
                            y = (int) getBorder().getY();
                            x += maxW[c] + xspacing;
                            c++;
                        }

                        IView view = items[i];
                        if (view == null) {
                            continue;
                        }

                        if (view instanceof IAlignableItem) {
                            ((IAlignableItem) view).justify(x, y, maxW[c], maxH[r]);
                        } else {
                            view.setLocation(x, y);
                        }

                        size.max(x + maxW[c], y + maxH[r]);
                        y += maxH[r] + yspacing;
                    }
                }

            } else {
                int columCount = -(rows_colums);
                int rowCount = len / columCount;

                maxW = new float[columCount + 1];
                maxH = new float[rowCount + 1];
                rings = new float[rowCount + 1];

                int r = 0;
                int c = 0;
                for (int i = 0; i < len; i++, c++) {
                    if (c == columCount) {
                        c = 0;
                        r++;
                    }

                    IView view = items[i];
                    if (view == null) {
                        continue;
                    }

                    view.setParentView(this);
                    view.layoutInterior(_flex);

                    maxW[c] = Math.max(maxW[c], view.getW());
                    maxH[r] = Math.max(maxH[r], view.getH());
                    rings[r] = (float) Math.max(rings[r], Math.max(view.getW(), view.getH()));
                    //rings[r] = (float)Math.max(rings[r],Math.sqrt(Math.pow(view.getW(),2)+Math.pow(view.getH(),2)));
                }

                if (radial) {

                    float origin = 0;
                    for (int i = 0; i < rings.length; i++) {
                        origin += (dia > 0) ? dia : rings[i];
                    }
                    origin += pad;

                    r = 0;
                    c = 0;
                    double _length = (dia > 0) ? dia : rings[0];
                    for (int i = 0; i < len; i++, r++) {
                        if (r == rowCount) {
                            r = 0;
                            c++;
                            _length += (dia > 0) ? dia : rings[c];
                        }

                        IView view = items[i];
                        if (view == null) {
                            continue;
                        }

                        float _direction = ((float) r / (float) rowCount) * 360;
                        x = (int) origin + (int) (Math.sin(Math.toRadians(_direction + 90)) * _length);
                        y = (int) origin + (int) (Math.sin(Math.toRadians(_direction)) * _length);

                        x -= (maxW[c] / 2);
                        y -= (maxH[r] / 2);

                        if (view instanceof IAlignableItem) {
                            ((IAlignableItem) view).justify(x, y, maxW[c], maxH[r]);
                        } else {
                            view.setLocation(x, y);
                        }
                        size.max(view.getX() + view.getW(), view.getY() + view.getH());


                    }
                    size.max((float) (origin * 2), (float) (origin * 2));

                } else {

                    r = 0;
                    c = 0;
                    for (int i = 0; i < len; i++, c++) {
                        if (c == columCount) {
                            c = 0;
                            x = (int) getBorder().getX();
                            y += maxH[r] + yspacing;
                            r++;
                        }

                        IView view = items[i];
                        if (view == null) {
                            continue;
                        }

                        if (view instanceof IAlignableItem) {
                            ((IAlignableItem) view).justify(x, y, maxW[c], maxH[r]);
                        } else {
                            view.setLocation(x, y);
                        }

                        size.max(x + maxW[c], y + maxH[r]);
                        x += maxW[c] + xspacing;
                    }
                }
            }
        }

        w = size.getW() - getBorder().getX();//!!??
        h = size.getH() - getBorder().getY();//!!?? this offset is required do to placer
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
    public void paintBorder(ICanvas g, int _x, int _y, int _w, int _h) {
        super.paintBorder(g, _x, _y, _w, _h);
        if (lineColor != null) {

            if (radial) {

                float origin = (dia > 0) ? dia : rings[0];
                for (int i = 0; i < rings.length; i++) {
                    origin += (dia > 0) ? dia : rings[i];
                }
                origin += pad;

                g.setColor(lineColor);
                double _length = origin - pad;
                for (int i = 0; i < rings.length; i++) {
                    g.oval(false, _x + (int) (origin - _length), _y + (int) (origin - _length), (int) (_length * 2), (int) (_length * 2));
                    _length -= (dia > 0) ? dia : rings[i];
                }

            } else {
                IView _parent = parent.get();
                float x = (int) _parent.getBorder().getX();
                float y = (int) _parent.getBorder().getY();

                g.setColor(lineColor);
                if (maxW != null) {
                    for (int i = 0; i < maxW.length; i++) {
                        x += maxW[i] + xspacing;
                        g.line(_x + (int) x - 1, _y + 0, _x + (int) x - 1, _y + (int) h);

                    }
                }
                if (maxH != null) {
                    for (int i = 0; i < maxH.length; i++) {
                        y += maxH[i] + yspacing;
                        g.line(_x + 0, _y + (int) y - 1, _x + (int) w, _y + (int) y - 1);
                    }
                }
            }
        }

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

        _x += getBorder().getX();
        _y += getBorder().getY();

        if (midLineColor != null) {

            if (radial) {

                float origin = (dia > 0) ? dia : rings[0];
                for (int i = 0; i < rings.length; i++) {
                    origin += (dia > 0) ? dia : rings[i];
                }
                origin += pad;

                g.setColor(midLineColor);
                double _length = origin - pad;
                for (int i = 0; i < rings.length - 1; i++) {
                    _length -= ((dia > 0) ? dia : rings[i]) / 2;
                    g.oval(false, _x + (int) (origin - _length), _y + (int) (origin - _length), (int) (_length * 2), (int) (_length * 2));
                    _length -= ((dia > 0) ? dia : rings[i]) / 2;
                }

            } else {
                IView _parent = parent.get();
                float x = (int) _parent.getBorder().getX();
                float y = (int) _parent.getBorder().getY();

                g.setColor(midLineColor);
                if (maxW != null) {
                    for (int i = 0; i < maxW.length; i++) {
                        x += (maxW[i] + xspacing) / 2;
                        g.line(_x + (int) x - 1, _y + 0, _x + (int) x - 1, _y + (int) h);
                        x += (maxW[i] + xspacing) / 2;
                    }
                }
                if (maxH != null) {
                    for (int i = 0; i < maxH.length; i++) {
                        y += (maxH[i] + yspacing) / 2;
                        g.line(_x + 0, _y + (int) y - 1, _x + (int) w, _y + (int) y - 1);
                        y += (maxH[i] + yspacing) / 2;
                    }
                }
            }
        }
    }
}
