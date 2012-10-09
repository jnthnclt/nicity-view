/*
 * UPaint.java.java
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

import colt.nicity.core.memory.struct.Poly_I;
import colt.nicity.core.collection.CArray;
import colt.nicity.core.collection.CSet;
import colt.nicity.core.lang.MinMaxDouble;
import colt.nicity.core.lang.UMath;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_D;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.adaptor.IPath;
import colt.nicity.view.adaptor.VS;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.Place;
import colt.nicity.view.core.ULAF;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VIcon;
import colt.nicity.view.image.ViewByteArrayImage;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IHaveColor;
import colt.nicity.view.interfaces.IView;
import java.awt.Polygon;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class UPaint {

    /**
     *
     */
    public static ViewByteArrayImage cheched = VIcon.image("Check");

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _forground
     * @param _background
     */
    public static void checked(ICanvas _g, int _x, int _y, int _w, int _h, AColor _forground, AColor _background) {
        _g.paintFlavor(ULAF.cRoundButtonBG, _x, _y, _w, _h, _background);
        cheched.paint(_g, null);
    }

    /**
     *
     * @param _g
     * @param x
     * @param y
     * @param w
     * @param h
     * @param forground
     * @param background
     */
    public static void accent(ICanvas _g, int x, int y, int w, int h, AColor forground, AColor background) {

        _g.setAlpha(0.5f, 0);
        _g.setColor(forground);
        IPath path = VS.path();
        path.moveTo(x, y);
        path.lineTo(x + w, y);
        path.lineTo(x, y + h);
        path.lineTo(x, y);
        path.closePath();
        _g.fill(path);
        _g.setAlpha(1f, 0);


    }

    /**
     *
     * @param _g
     * @param x
     * @param y
     * @param w
     * @param h
     * @param forground
     * @param background
     */
    public static void possibleAccent(ICanvas _g, int x, int y, int w, int h, AColor forground, AColor background) {
        _g.setColor(background);
        _g.rect(false, 1 + x, 1 + y, w, h);
        _g.setColor(forground);
        _g.rect(false, x, y, w, h);
    }
    // slope -1 -> 1

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _r
     */
    public static void drawCircle(ICanvas _g, int _x, int _y, int _r) {
        _g.oval(false, _x - _r, _y - _r, _r * 2, _r * 2);
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _r
     */
    public static void fillCircle(ICanvas _g, int _x, int _y, int _r) {
        _g.oval(true, _x - _r, _y - _r, _r * 2, _r * 2);
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _r
     * @param _sa
     * @param _da
     */
    public static void drawCircle(ICanvas _g, int _x, int _y, int _r, double _sa, double _da) {
        int sa = (int) (_sa * 360);
        int da = (int) (_da * 360);
        _g.arc(false, _x - _r, _y - _r, _r * 2, _r * 2, sa, -da);
    }

    /**
     *
     * @param _g
     * @param _fx
     * @param _fcy
     * @param _fh
     * @param _tx
     * @param _tcy
     * @param _th
     */
    public static void fillPoly(
            ICanvas _g,
            int _fx, int _fcy, int _fh, int _tx, int _tcy, int _th) {
        int[] xs = new int[5];
        int[] ys = new int[5];

        xs[0] = _fx;
        ys[0] = _fcy - (_fh / 2);

        xs[1] = _tx;
        ys[1] = _tcy - (_th / 2);

        xs[2] = _tx;
        ys[2] = _tcy + (_th / 2);

        xs[3] = _fx;
        ys[3] = _fcy + (_fh / 2);

        xs[4] = xs[0];
        ys[4] = xs[0];


        _g.polygon(true, xs, ys, 4);
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _sa
     * @param _ea
     * @param _sr
     * @param _er
     */
    public static void drawPieSlice(
            ICanvas _g,
            int _x, int _y, double _sa, double _ea, int _sr, int _er) {
        int[] xs = new int[4];
        int[] ys = new int[4];

        xs[0] = _x + (int) (_sr * Math.cos(_sa * (Math.PI * 2)));
        ys[0] = _y + (int) (_sr * Math.sin(_sa * (Math.PI * 2)));

        xs[1] = _x + (int) (_er * Math.cos(_sa * (Math.PI * 2)));
        ys[1] = _y + (int) (_er * Math.sin(_sa * (Math.PI * 2)));


        xs[2] = _x + (int) (_er * Math.cos(_ea * (Math.PI * 2)));
        ys[2] = _y + (int) (_er * Math.sin(_ea * (Math.PI * 2)));

        xs[3] = _x + (int) (_sr * Math.cos(_ea * (Math.PI * 2)));
        ys[3] = _y + (int) (_sr * Math.sin(_ea * (Math.PI * 2)));

        _g.polygon(true, xs, ys, 4);
    }
    // Both input polys should have the same number of points

    /**
     *
     * @param grow
     * @param shrink
     * @return
     */
    public static Poly_I join(Poly_I grow, Poly_I shrink) {
        int count = grow.npoints + 1 + shrink.npoints + 1;
        int[] xs = new int[count];
        int[] ys = new int[count];
        int i = 0;
        for (int a = 0; a < grow.npoints; a++) {
            xs[i] = grow.xpoints[a];
            ys[i] = grow.ypoints[a];
            i++;
        }
        xs[i] = grow.xpoints[0];
        ys[i] = grow.ypoints[0];
        i++;

        xs[i] = shrink.xpoints[0];
        ys[i] = shrink.ypoints[0];
        i++;
        for (int a = shrink.npoints - 1; a > -1; a--) {
            xs[i] = shrink.xpoints[a];
            ys[i] = shrink.ypoints[a];
            i++;
        }


        return new Poly_I(xs, ys, count);
    }

    // _a 0->1
    /**
     *
     * @param _x
     * @param _y
     * @param _a
     * @param _r
     * @return
     */
    public static XY_I radialPoint(
            int _x, int _y, double _a, double _r) {
        int x = (int) (_r * Math.cos(_a * (Math.PI * 2)));
        int y = (int) (_r * Math.sin(_a * (Math.PI * 2)));
        x += _x;
        y += _y;
        return new XY_I(x, y);
    }

    /**
     *
     * @param _x
     * @param _y
     * @param _count
     * @param _r
     * @return
     */
    public static XY_I[] radialPoints(
            int _x, int _y, int _count, double _r) {
        CArray points = new CArray(XY_I.class);
        for (double _a = 0; _a < 1; _a += (1d / (double) _count)) {
            points.insertLast(radialPoint(_x, _y, _a, _r));
        }
        return (XY_I[]) points.getAll();
    }

    /**
     *
     * @param _path
     * @param _x
     * @param _y
     * @param _a
     * @param _r
     */
    public static void moveToRadial(
            IPath _path,
            int _x, int _y, double _a, double _r) {
        int x = (int) (_r * Math.cos(_a * (Math.PI * 2)));
        int y = (int) (_r * Math.sin(_a * (Math.PI * 2)));
        x += _x;
        y += _y;
        _path.moveTo(x, y);
    }

    /**
     *
     * @param _path
     * @param _x
     * @param _y
     * @param _a1
     * @param _r1
     * @param _a2
     * @param _r2
     */
    public static void quadToRadial(
            IPath _path,
            int _x, int _y,
            double _a1, double _r1,
            double _a2, double _r2) {
        float x1 = (float) (_r1 * Math.cos(_a1 * (Math.PI * 2)));
        float y1 = (float) (_r1 * Math.sin(_a1 * (Math.PI * 2)));
        x1 += _x;
        y1 += _y;

        float x2 = (float) (_r2 * Math.cos(_a2 * (Math.PI * 2)));
        float y2 = (float) (_r2 * Math.sin(_a2 * (Math.PI * 2)));
        x2 += _x;
        y2 += _y;

        _path.quadTo(x1, y1, x2, y2);
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _a
     * @param _sr
     * @param _er
     */
    public static void drawRadial(
            ICanvas _g,
            int _x, int _y, double _a, int _sr, int _er) {
        int sx = (int) (_sr * Math.cos(_a * (Math.PI * 2)));
        int sy = (int) (_sr * Math.sin(_a * (Math.PI * 2)));
        sx += _x;
        sy += _y;

        int ex = (int) (_er * Math.cos(_a * (Math.PI * 2)));
        int ey = (int) (_er * Math.sin(_a * (Math.PI * 2)));
        ex += _x;
        ey += _y;

        _g.line(sx, sy, ex, ey);
    }

    /**
     *
     * @param _g
     * @param _font
     * @param _string
     * @param _x
     * @param _y
     * @param _a
     * @param _r
     */
    public static void drawRadialString(
            ICanvas _g,
            AFont _font, String _string,
            int _x, int _y, double _a, int _r) {
        int x = (int) (_r * Math.cos(_a * (Math.PI * 2)));
        int y = (int) (_r * Math.sin(_a * (Math.PI * 2)));
        x += _x;
        y += _y;

        XY_I p = _font.place(_string, x, y, UV.cCC);
        _g.drawString(_string, p.x, p.y);
    }

    /**
     *
     * @param _polygon
     * @param _iterations
     * @return
     */
    public static Polygon smooth(Polygon _polygon, int _iterations) {
        Vector polygon = new Vector(_polygon.npoints);
        for (int i = 0; i < _polygon.npoints - 1; i++) {
            polygon.add(new XY_D(_polygon.xpoints[i], _polygon.ypoints[i]));
        }
        for (int i = 0; i < _iterations; i++) {
            polygon = splitTweak(polygon);
        }
        int size = polygon.size();
        int[] xs = new int[size];
        int[] ys = new int[size];

        for (int i = 0; i < size; i++) {
            XY_D p = (XY_D) polygon.get(i);
            xs[i] = (int) p.x;
            ys[i] = (int) p.y;
        }
        return new Polygon(xs, ys, size);
    }

    /**
     *
     * @param _polygon
     * @param _amount
     * @return
     */
    public static Polygon shrink(Polygon _polygon, double _amount) {
        Vector polygon = new Vector(_polygon.npoints);
        for (int i = 0; i < _polygon.npoints - 1; i++) {
            polygon.add(new XY_D(_polygon.xpoints[i], _polygon.ypoints[i]));
        }

        polygon = shrink(polygon, _amount);

        int size = polygon.size();
        int[] xs = new int[size];
        int[] ys = new int[size];
        for (int i = 0; i < size; i++) {
            XY_D p = (XY_D) polygon.get(i);
            xs[i] = (int) p.x;
            ys[i] = (int) p.y;
        }
        return new Polygon(xs, ys, size);
    }

    /**
     *
     * @param orig
     * @return
     */
    static public XY_D center(Vector orig) {
        MinMaxDouble x = new MinMaxDouble();
        MinMaxDouble y = new MinMaxDouble();

        int size = orig.size();
        for (int i = 0; i < size; i++) {
            XY_D p = (XY_D) orig.get(i);
            x.value(p.x);
            y.value(p.y);
        }
        return new XY_D(x.middle(), y.middle());
    }

    /**
     *
     * @param orig
     * @param _amount
     * @return
     */
    static public Vector shrink(Vector orig, double _amount) {
        XY_D c = center(orig);
        Vector toReturn = new Vector();
        int size = orig.size();
        for (int i = 0; i < size; i++) {
            XY_D p = (XY_D) orig.get(i);
            double d = UMath.distance(p.x, p.y, c.x, c.y);
            double a = UMath.angle(p.x, p.y, c.x, c.y);
            double[] np = UMath.vector(a, d * _amount, new double[2]);
            toReturn.add(new XY_D(p.x - np[0], p.y - np[1]));
        }
        return toReturn;
    }

    /**
     *
     * @param orig
     * @return
     */
    static public Vector findMidPoints(Vector orig) {
        // Go through point pairs and add midpoints to a return Vector
        Vector toReturn = new Vector();
        int size = orig.size();
        XY_D firstPoint = (XY_D) (orig.firstElement());
        XY_D prevPoint = firstPoint;
        for (int i = 1; i < size; i++) {
            // Find midPoint between prev and next point			
            XY_D currPoint = (XY_D) (orig.elementAt(i));
            toReturn.add(midPoint(prevPoint, currPoint));
            prevPoint = currPoint;
        }
        toReturn.add(midPoint(prevPoint, firstPoint));
        return toReturn;
    }

    /**
     *
     * @param first
     * @param second
     * @return
     */
    static public XY_D midPoint(XY_D first, XY_D second) {
        return new XY_D((first.getX() + second.getX()) / 2, (first.getY() + second.getY()) / 2);
    }

    /**
     *
     * @param poly
     * @return
     */
    static public Vector splitTweak(Vector poly) {
        // Find the midpoints
        Vector mid = findMidPoints(poly);
        Vector targets = findMidPoints(mid);
        Vector tweaks = new Vector();

        // calculate tweaks: move poly points toward targets
        // start with last target for first point
        XY_D currTarget = (XY_D) (targets.elementAt(targets.size() - 1));
        XY_D currMidPoint = (XY_D) (poly.firstElement());

        tweaks.add(new XY_D((currTarget.getX() * .5 + currMidPoint.getX() * .5),
                (currTarget.getY() * .5 + currMidPoint.getY() * .5)));

        for (int i = 1; i < poly.size(); i++) {
            currTarget = (XY_D) (targets.elementAt(i - 1));
            currMidPoint = (XY_D) (poly.elementAt(i));
            tweaks.add(new XY_D(((currTarget.getX() + currMidPoint.getX()) / 2),
                    ((currTarget.getY() + currMidPoint.getY())) / 2));
        }

        // interleave midpoints and new tweaks
        return interleave(tweaks, mid);
    }

    /**
     *
     * @param poly
     * @return
     */
    static public Vector fourPoints(Vector poly) {
        Vector mid = findMidPoints(poly);

        XY_D currMidPoint, disp;
        Vector newMid = new Vector();
        XY_D left, right;

        for (int i = 0; i < mid.size(); i++) {
            currMidPoint = (XY_D) (mid.elementAt(i));

            // for the midpoint M: primary neighbors

            // allow for cyclic traversal of the polygon
            if (i == 0) {
                left = (XY_D) (mid.elementAt(mid.size() - 1));
            } else {
                left = (XY_D) (mid.elementAt(i - 1));
            }
            right = (XY_D) (mid.elementAt((i + 1) % mid.size()));

            // find the secondary midpoint M' --> midpoint of neighbors
            XY_D secM = midPoint(left, right);

            // add a point on the line from M' through M to 1/8 outward
            // --> subtract M from M', and we have a displacement
            disp = new XY_D((currMidPoint.getX() - secM.getX()),
                    (currMidPoint.getY() - secM.getY()));

            // --> add 1/4th of that displacement to current midpoint
            newMid.add(new XY_D((currMidPoint.getX() + 0.25 * disp.getX()),
                    (currMidPoint.getY() + 0.25 * disp.getY())));
        }

        // after subdividing, interleave new midpoints and vertices
        return interleave(poly, newMid);
    }

    /**
     *
     * @param lead
     * @param second
     * @return
     */
    static public Vector interleave(Vector lead, Vector second) {
        Vector toReturn = new Vector();

        for (int i = 0; i < lead.size() || i < second.size(); i++) {
            if (i < lead.size()) {
                toReturn.add(lead.elementAt(i));
            }
            if (i < second.size()) {
                toReturn.add(second.elementAt(i));
            }
        }

        return toReturn;
    }

    /**
     *
     * @param _g
     * @param _string
     * @param _font
     * @param _x
     * @param _y
     * @param _place
     * @return
     */
    public static XY_I string(
            ICanvas _g, String _string, AFont _font, int _x, int _y, Place _place) {
        XY_I p = _font.place(_string, _x, _y, _place);
        _g.drawString(_string, p.x, p.y);
        return p;
    }

    /**
     *
     * @param _g
     * @param _string
     * @param _font
     * @param _x
     * @param _y
     * @param _place
     * @param _degress
     * @return
     */
    public static XY_I string(
            ICanvas _g, String _string, AFont _font, int _x, int _y, Place _place, int _degress) {
        XY_I p = _font.place(_string, _x, _y, _place);
        _g.translate(p.x, p.y);
        _g.rotate(Math.toRadians(-_degress));
        _g.drawString(_string, 0, _font.getSize());
        _g.rotate(Math.toRadians(_degress));
        _g.translate(-p.x, -p.y);
        return p;
    }

    /**
     *
     * @param _g
     * @param _slope
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public static void paintSlope(
            ICanvas _g, double _slope, int _x, int _y, int _w, int _h) {
        _slope *= -1;
        int x = (int) (_w * Math.cos(_slope * (Math.PI / 2)));
        int y = (_h / 2) + (int) ((_h / 2) * Math.sin(_slope * (Math.PI / 2)));
        _g.line(_x, _y + (_h / 2), _x + x, _y + y);
    }

    /**
     *
     * @param _path
     * @param _from
     * @param _to
     * @param _offset
     * @param _inset
     * @param _deflect
     * @param _arrow
     * @param _reach
     */
    public static void perimeterLineTo(IPath _path, IView _from, IView _to, XY_I _offset, int _inset, double _deflect, boolean _arrow, double _reach) {
        if (_path == null || _from == null || _to == null) {
            return;

        }
        XY_I f = _from.getLocationOnScreen();
        XY_I t = _to.getLocationOnScreen();

        perimeterLineTo(_path, f, _from.getW(), _from.getH(), t, _to.getW(), _to.getH(), _offset, _inset, _deflect, _arrow, _reach);
    }

    /**
     *
     * @param _path
     * @param _from
     * @param _to
     * @param _offset
     * @param _inset
     * @param _deflect
     * @param _arrow
     * @param _reach
     */
    public static void perimeterLineTo(IPath _path, IView _from, XY_I _to, XY_I _offset, int _inset, double _deflect, boolean _arrow, double _reach) {
        if (_path == null || _from == null || _to == null) {
            return;

        }
        XY_I f = _from.getLocationOnScreen();

        perimeterLineTo(_path, f, _from.getW(), _from.getH(), _to, 1, 1, _offset, _inset, _deflect, _arrow, _reach);


    }

    /**
     *
     * @param _path
     * @param _from
     * @param _to
     * @param _offset
     * @param _inset
     * @param _deflect
     * @param _arrow
     * @param _reach
     */
    public static void perimeterLineTo(IPath _path, XY_I _from, IView _to, XY_I _offset, int _inset, double _deflect, boolean _arrow, double _reach) {
        if (_path == null || _from == null || _to == null) {
            return;

        }
        XY_I t = _to.getLocationOnScreen();

        perimeterLineTo(_path, _from, 1, 1, t, _to.getW(), _to.getH(), _offset, _inset, _deflect, _arrow, _reach);


    }

    /**
     *
     * @param _path
     * @param f
     * @param _fw
     * @param _fh
     * @param t
     * @param _tw
     * @param _th
     * @param _offset
     * @param _inset
     * @param _deflect
     * @param _arrow
     * @param _reach
     */
    public static void perimeterLineTo(
            IPath _path,
            XY_I f, float _fw, float _fh,
            XY_I t, float _tw, float _th,
            XY_I _offset, int _inset, double _deflect, boolean _arrow,
            double _reach) {

        //double[] reach = UMath.middle(f.x,f.y,t.x,t.y,_reach);
        //t = new XY_I((int)reach[0],(int)reach[1]);


        f.x -= _offset.x;
        f.y -= _offset.y;
        f.x += (int) (_fw / 2);
        f.y += (int) (_fh / 2);

        t.x -= _offset.x;
        t.y -= _offset.y;
        t.x += (int) (_tw / 2);
        t.y += (int) (_th / 2);

        //double[] reach = UMath.middle(f.x,f.y,t.x,t.y,_reach);
        //t = new XY_I((int)reach[0],(int)reach[1]);


        float radians = (float) (UMath.angle(f.x, f.y, t.x, t.y));
        double[] fxy = UMath.rectanglePerimeter(_fw - _inset, _fh - _inset, radians);
        float fox = (float) fxy[0];
        float foy = (float) fxy[1];

        radians = (float) (UMath.angle(t.x, t.y, f.x, f.y));
        double[] txy = UMath.rectanglePerimeter(_tw - _inset, _th - _inset, radians);
        float tox = (float) txy[0];
        float toy = (float) txy[1];

        XY_I _f = new XY_I((int) (f.x + fox), (int) (f.y + foy));
        XY_I _t = new XY_I((int) (t.x + tox), (int) (t.y + toy));


        quadTo(_path, _f, _t, _deflect);
        if (_arrow) {

            UPaint.arrowTo(_path, _f.x, _f.y, _t.x, _t.y, 10, (float) (Math.PI / 2));
        }
    }

    /**
     *
     * @param _path
     * @param _f
     * @param _t
     * @param _deflect
     */
    public static void quadTo(
            IPath _path,
            XY_I _f, XY_I _t,
            double _deflect) {

        double[] mxy = UMath.middle(_f.x, _f.y, _t.x, _t.y);
        double deflectX = (_f.y > _t.y) ? (Math.abs(_f.y - _t.y) * _deflect) : -(Math.abs(_f.y - _t.y) * _deflect);
        double deflectY = (_f.x > _t.x) ? -(Math.abs(_f.x - _t.x) * _deflect) : (Math.abs(_f.x - _t.x) * _deflect);
        mxy[0] += deflectX;
        mxy[1] += deflectY;

        _path.moveTo(_f.x, _f.y);
        _path.quadTo((float) mxy[0], (float) mxy[1], _t.x, _t.y);
    }

    /**
     *
     * @param _path
     * @param f
     * @param _fw
     * @param _fh
     * @param t
     * @param _tw
     * @param _th
     * @param _offset
     * @param _inset
     * @param _deflect
     * @param _arrow
     * @param _reach
     */
    public static void perimeterLineToHard(
            IPath _path,
            XY_I f, float _fw, float _fh,
            XY_I t, float _tw, float _th,
            XY_I _offset, int _inset, double _deflect, boolean _arrow,
            double _reach) {


        f.x -= _offset.x;
        f.y -= _offset.y;
        f.x += (int) (_fw / 2);
        f.y += (int) (_fh / 2);

        t.x -= _offset.x;
        t.y -= _offset.y;
        t.x += (int) (_tw / 2);
        t.y += (int) (_th / 2);


        float radians = (float) (UMath.angle(f.x, f.y, t.x, t.y));
        double[] fxy = UMath.rectanglePerimeter(_fw - _inset, _fh - _inset, radians);
        float fox = (float) fxy[0];
        float foy = (float) fxy[1];

        radians = (float) (UMath.angle(t.x, t.y, f.x, f.y));
        double[] txy = UMath.rectanglePerimeter(_tw - _inset, _th - _inset, radians);
        float tox = (float) txy[0];
        float toy = (float) txy[1];

        XY_I _f = new XY_I((int) (f.x + fox), (int) (f.y + foy));
        XY_I _t = new XY_I((int) (t.x + tox), (int) (t.y + toy));


        double[] mxy = UMath.middle(_f.x, _f.y, _t.x, _t.y);
        double deflectX = (_f.y > _t.y) ? (Math.abs(_f.y - _t.y) * _deflect) : -(Math.abs(_f.y - _t.y) * _deflect);
        double deflectY = (_f.x > _t.x) ? -(Math.abs(_f.x - _t.x) * _deflect) : (Math.abs(_f.x - _t.x) * _deflect);
        mxy[0] += deflectX;
        mxy[1] += deflectY;



        UPaint.drawCurveLink(_path, _f.x, _f.y, _t.x, _t.y);


        if (_arrow) {
            UPaint.arrowTo(_path, _f.x, _f.y, _t.x, _t.y, 10, (float) (Math.PI / 2));
        }
    }

    /**
     *
     * @param _p
     * @param _x
     * @param _y
     * @param _direction
     * @param _length
     * @param _angle
     */
    public static void arrowHead(IPath _p, float _x, float _y, float _direction, float _length, float _angle) {
        _p.moveTo(_x, _y);
        _direction -= (_angle / 2);
        _p.lineTo((float) (_x + (Math.sin(Math.toRadians(_direction)) * _length)), _y + (float) ((Math.sin(Math.toRadians(_direction + 90)) * _length)));

        _direction += _angle;
        _p.moveTo(_x, _y);
        _p.lineTo((float) (_x + (Math.sin(Math.toRadians(_direction)) * _length)), _y + (float) ((Math.sin(Math.toRadians(_direction + 90)) * _length)));
    }

    /**
     *
     * @param _p
     * @param _sx
     * @param _sy
     * @param _angle
     * @param _length
     */
    public static void vector(IPath _p, float _sx, float _sy, float _angle, float _length) {
        _p.moveTo(_sx, _sy);
        float radians = (float) Math.toRadians(_angle);
        float ex = (float) (_sx + (Math.sin(radians) * _length));
        float ey = (float) (_sy + (Math.cos(radians) * _length));
        _p.lineTo(ex, ey);
        arrowTo(_p, _sx, _sy, ex, ey, 10, (float) (Math.PI / 2));
    }

    /**
     *
     * @param _g
     * @param _sx
     * @param _sy
     * @param _angle
     * @param _length
     */
    public static void fastVector(ICanvas _g, float _sx, float _sy, float _angle, float _length) {
        float radians = (float) Math.toRadians(_angle);
        float ex = (float) (_sx + (Math.sin(radians) * _length));
        float ey = (float) (_sy + (Math.cos(radians) * _length));
        _g.line((int) _sx, (int) _sy, (int) ex, (int) ey);
        _g.oval(false, (int) (ex) - 1, (int) (ey) - 1, (int) 2, (int) 2);
    }

    /**
     *
     * @param _p
     * @param _sx
     * @param _sy
     * @param _ex
     * @param _ey
     * @param _length
     * @param _angle
     */
    public static void arrowTo(IPath _p, float _sx, float _sy, float _ex, float _ey, float _length, float _angle) {

        double radians = UMath.angle(_sx, _sy, _ex, _ey);

        radians -= (Math.toRadians(_angle) / 2);

        _p.moveTo(_ex, _ey);
        _p.lineTo((float) (_ex - (Math.sin(radians) * _length)), _ey + (float) ((Math.sin(radians + (Math.PI / 2)) * _length)));

        radians -= Math.toRadians(_angle);
        _p.moveTo(_ex, _ey);
        _p.lineTo((float) (_ex - (Math.sin(radians) * _length)), _ey + (float) ((Math.sin(radians + (Math.PI / 2)) * _length)));

    }

    /**
     *
     * @param _p
     * @param _sx
     * @param _sy
     * @param _ex
     * @param _ey
     * @param _length
     * @param _angle
     */
    public static void solidArrow(IPath _p, float _sx, float _sy, float _ex, float _ey, float _length, float _angle) {

        double radians = UMath.angle(_sx, _sy, _ex, _ey);

        radians -= (Math.toRadians(_angle) / 2);

        _p.moveTo(_ex, _ey);
        _p.lineTo((float) (_ex - (Math.sin(radians) * _length)), _ey + (float) ((Math.sin(radians + (Math.PI / 2)) * _length)));

        radians -= Math.toRadians(_angle);
        _p.lineTo((float) (_ex - (Math.sin(radians) * _length)), _ey + (float) ((Math.sin(radians + (Math.PI / 2)) * _length)));
        _p.lineTo(_ex, _ey);

    }

    /**
     *
     * @param _p
     * @param _sx
     * @param _sy
     * @param _ex
     * @param _ey
     * @param _length
     * @param _angle
     */
    public static void longArrow(IPath _p, float _sx, float _sy, float _ex, float _ey, float _length, float _angle) {

        double radians = UMath.angle(_sx, _sy, _ex, _ey);

        radians -= (_angle / 2);

        _p.moveTo(_ex, _ey);
        _p.lineTo((float) (_sx - (Math.sin(radians) * _length)), _sy + (float) ((Math.sin(radians + (Math.PI / 2)) * _length)));

        radians -= _angle;
        _p.lineTo((float) (_sx - (Math.sin(radians) * _length)), _sy + (float) ((Math.sin(radians + (Math.PI / 2)) * _length)));
        _p.lineTo(_ex, _ey);

    }

    /**
     *
     * @param _p
     * @param _fromX
     * @param _fromY
     * @param _toX
     * @param _toY
     * @param _tension
     */
    public static void drawCurveLink(IPath _p, float _fromX, float _fromY, float _toX, float _toY, float _tension) {

        float gapX = (Math.max(_fromX, _toX) - Math.min(_fromX, _toX));
        float gapY = (Math.max(_fromY, _toY) - Math.min(_fromY, _toY));

        float midX = Math.min(_fromX, _toX) + (gapX / 2);
        float midY = Math.min(_fromY, _toY) + (gapY / 2);

        _p.moveTo(_fromX, _fromY);
        if (_fromX >= _toX) {
            _tension = -(_fromX - _toX);
            _p.curveTo(midX - _tension, _fromY, midX - _tension, _fromY, midX, midY);
            _p.curveTo(midX + _tension, _toY, midX + _tension, _toY, _toX, _toY);
        } else {
            _tension = (midX - _fromX) / 2;
            _p.curveTo(midX - _tension, _fromY, midX - _tension, _fromY, midX, midY);
            _p.curveTo(midX + _tension, _toY, midX + _tension, _toY, _toX, _toY);
        }
    }

    /**
     *
     * @param _p
     * @param _fromX
     * @param _fromY
     * @param _toX
     * @param _toY
     */
    public static void drawCurveLink(IPath _p, float _fromX, float _fromY, float _toX, float _toY) {

        float gapX = (Math.max(_fromX, _toX) - Math.min(_fromX, _toX));
        float gapY = (Math.max(_fromY, _toY) - Math.min(_fromY, _toY));

        float midX = Math.min(_fromX, _toX) + (gapX / 2);
        float midY = Math.min(_fromY, _toY) + (gapY / 2);

        _p.moveTo(_fromX, _fromY);

        if (_fromY >= _toY) {
            float _tension = (midX - _fromX) / 2;
            _p.curveTo(midX - _tension, midY, midX - _tension, midY, midX, midY);
            _p.curveTo(midX + _tension, midY, midX + _tension, midY, _toX, _toY);
        } else {
            float _tension = (midY - _fromY) / 2;
            _p.curveTo(midX, midY - _tension, midX, midY - _tension, midX, midY);
            _p.curveTo(midX, midY + _tension, midX, midY + _tension, _toX, _toY);
        }

    }

    /**
     *
     * @param _p
     * @param _fromX
     * @param _fromY
     * @param _toX
     * @param _toY
     */
    public static void drawLine(IPath _p, float _fromX, float _fromY, float _toX, float _toY) {
        _p.moveTo(_fromX, _fromY);
        _p.lineTo(_toX, _toY);
    }

    /**
     *
     * @param _p
     * @param _fromX
     * @param _fromY
     * @param _toX
     * @param _toY
     * @param _tension
     */
    public static void drawLeftToLeftCurveLink(IPath _p, float _fromX, float _fromY, float _toX, float _toY, float _tension) {

        float gapX = (Math.max(_fromX, _toX) - Math.min(_fromX, _toX));
        float gapY = (Math.max(_fromY, _toY) - Math.min(_fromY, _toY));


        float midX = Math.min(_fromX, _toX) + (gapX / 2);
        float midY = Math.min(_fromY, _toY) + (gapY / 2);


        midX -= _tension;


        float min = Math.min(_fromX, _toX);
        float round = 0;
        if (gapY != 0) {
            round = (_tension / gapY) * gapY;
        }
        
        min -= round;


        _p.moveTo(_fromX, _fromY);

        _p.lineTo(min + round, _fromY);
        _p.moveTo(min + round, _fromY);
        _p.quadTo(min, _fromY, min, midY);
        //_p.lineTo(max,midY);


        _p.moveTo(_toX, _toY);
        _p.lineTo(min + round, _toY);
        _p.moveTo(min + round, _toY);
        _p.quadTo(min, _toY, min, midY);
        //_p.lineTo(max,midY);


    }

    /**
     *
     * @param _p
     * @param _fromX
     * @param _fromY
     * @param _toX
     * @param _toY
     * @param _tension
     */
    public static void drawRightToRightCurveLink(IPath _p, float _fromX, float _fromY, float _toX, float _toY, float _tension) {

        float gapX = (Math.max(_fromX, _toX) - Math.min(_fromX, _toX));
        float gapY = (Math.max(_fromY, _toY) - Math.min(_fromY, _toY));


        float midX = Math.min(_fromX, _toX) + (gapX / 2);
        float midY = Math.min(_fromY, _toY) + (gapY / 2);


        midX += _tension;


        float max = Math.max(_fromX, _toX);
        float round = 0;
        if (gapY != 0) {
            round = (_tension / gapY) * gapY;
        }
        
        max += round;


        _p.moveTo(_fromX, _fromY);

        _p.lineTo(max - round, _fromY);
        _p.moveTo(max - round, _fromY);
        _p.quadTo(max, _fromY, max, midY);
        //_p.lineTo(max,midY);


        _p.moveTo(_toX, _toY);
        _p.lineTo(max - round, _toY);
        _p.moveTo(max - round, _toY);
        _p.quadTo(max, _toY, max, midY);
        //_p.lineTo(max,midY);


    }

    /**
     *
     * @param _path
     * @param _f
     * @param _t
     * @param _deflect1
     * @param _deflect2
     */
    public static void moon(
            IPath _path,
            XY_I _f, XY_I _t,
            double _deflect1, double _deflect2) {

        double[] mxy1 = UMath.middle(_f.x, _f.y, _t.x, _t.y);
        double deflectX1 = (_f.y > _t.y) ? (Math.abs(_f.y - _t.y) * _deflect1) : -(Math.abs(_f.y - _t.y) * _deflect1);
        double deflectY1 = (_f.x > _t.x) ? -(Math.abs(_f.x - _t.x) * _deflect1) : (Math.abs(_f.x - _t.x) * _deflect1);
        mxy1[0] += deflectX1;
        mxy1[1] += deflectY1;

        double[] mxy2 = UMath.middle(_f.x, _f.y, _t.x, _t.y);
        double deflectX2 = (_f.y > _t.y) ? (Math.abs(_f.y - _t.y) * _deflect2) : -(Math.abs(_f.y - _t.y) * _deflect2);
        double deflectY2 = (_f.x > _t.x) ? -(Math.abs(_f.x - _t.x) * _deflect2) : (Math.abs(_f.x - _t.x) * _deflect2);
        mxy2[0] += deflectX2;
        mxy2[1] += deflectY2;

        _path.moveTo(_f.x, _f.y);
        _path.quadTo((float) mxy1[0], (float) mxy1[1], _t.x, _t.y);
        _path.moveTo(_t.x, _t.y);
        _path.quadTo((float) mxy2[0], (float) mxy2[1], _f.x, _f.y);

    }

    /**
     *
     * @param _path
     * @param _f
     * @param _t
     * @param _deflect
     */
    public static void deflectLine(
            IPath _path,
            XY_I _f, XY_I _t,
            double _deflect) {

        XY_I dm = deflectMiddle(_f, _t, _deflect);

        _path.moveTo(_f.x, _f.y);
        _path.quadTo(dm.x, dm.y, _t.x, _t.y);

    }

    /**
     *
     * @param _f
     * @param _t
     * @param _deflect1
     * @return
     */
    public static XY_I deflectMiddle(
            XY_I _f, XY_I _t,
            double _deflect1) {

        double[] mxy1 = UMath.middle(_f.x, _f.y, _t.x, _t.y);
        double deflectX1 = (_f.y > _t.y) ? (Math.abs(_f.y - _t.y) * _deflect1) : -(Math.abs(_f.y - _t.y) * _deflect1);
        double deflectY1 = (_f.x > _t.x) ? -(Math.abs(_f.x - _t.x) * _deflect1) : (Math.abs(_f.x - _t.x) * _deflect1);
        mxy1[0] += deflectX1;
        mxy1[1] += deflectY1;
        return new XY_I((int) mxy1[0], (int) mxy1[1]);

    }

    /**
     *
     * @param _path
     * @param _f
     * @param _t
     * @param _deflect1
     * @param _deflect2
     */
    public static void moonArrow(
            IPath _path,
            XY_I _f, XY_I _t,
            int _deflect1, int _deflect2) {

        double[] mxy0 = UMath.middle(_f.x, _f.y, _t.x, _t.y);


        double[] mxy1 = UMath.middle(_f.x, _f.y, mxy0[0], mxy0[1]);
        double deflectX1 = (_f.y > _t.y) ? (_deflect1) : -(_deflect1);
        double deflectY1 = (_f.x > _t.x) ? -(_deflect1) : (_deflect1);
        mxy1[0] += deflectX1;
        mxy1[1] += deflectY1;

        double[] mxy2 = UMath.middle(_f.x, _f.y, mxy0[0], mxy0[1]);
        double deflectX2 = (_f.y > _t.y) ? (_deflect2) : -(_deflect2);
        double deflectY2 = (_f.x > _t.x) ? -(_deflect2) : (_deflect2);
        mxy2[0] += deflectX2;
        mxy2[1] += deflectY2;

        _path.moveTo(_f.x, _f.y);
        _path.quadTo((float) mxy0[0], (float) mxy0[1], _t.x, _t.y);


        _path.moveTo(_f.x, _f.y);
        _path.quadTo((float) mxy2[0], (float) mxy2[1], (float) mxy0[0], (float) mxy0[1]);

        _path.moveTo(_f.x, _f.y);
        _path.quadTo((float) mxy2[0], (float) mxy2[1], (float) mxy0[0], (float) mxy0[1]);


    }

    /**
     *
     * @param _path
     * @param _f
     * @param _t
     * @param _deflect1
     * @param _deflect2
     */
    public static void moon(
            IPath _path,
            XY_I _f, XY_I _t,
            int _deflect1, int _deflect2) {

        double[] mxy1 = UMath.middle(_f.x, _f.y, _t.x, _t.y);
        double deflectX1 = (_f.y > _t.y) ? (_deflect1) : -(_deflect1);
        double deflectY1 = (_f.x > _t.x) ? -(_deflect1) : (_deflect1);
        mxy1[0] += deflectX1;
        mxy1[1] += deflectY1;

        double[] mxy2 = UMath.middle(_f.x, _f.y, _t.x, _t.y);
        double deflectX2 = (_f.y > _t.y) ? (_deflect2) : -(_deflect2);
        double deflectY2 = (_f.x > _t.x) ? -(_deflect2) : (_deflect2);
        mxy2[0] += deflectX2;
        mxy2[1] += deflectY2;

        _path.moveTo(_f.x, _f.y);
        _path.quadTo((float) mxy1[0], (float) mxy1[1], _t.x, _t.y);
        _path.moveTo(_t.x, _t.y);
        _path.quadTo((float) mxy2[0], (float) mxy2[1], _f.x, _f.y);

    }

    /**
     *
     * @param _p
     * @param _fromX
     * @param _fromY
     * @param _toX
     * @param _toY
     * @param _tension
     */
    public static void drawLineLink(IPath _p, float _fromX, float _fromY, float _toX, float _toY, float _tension) {
        float gapX = (Math.max(_fromX, _toX) - Math.min(_fromX, _toX));
        float gapY = (Math.max(_fromY, _toY) - Math.min(_fromY, _toY));


        float midX = Math.min(_fromX, _toX) + (gapX / 2);
        float midY = Math.min(_fromY, _toY) + (gapY / 2);

        _p.moveTo(_fromX, _fromY);
        if (_fromX >= _toX) {
            if (_fromY == _toY) {
                _p.lineTo(_fromX + _tension, _fromY);
                _p.moveTo(_fromX + _tension, _fromY);
                _p.lineTo(_fromX + _tension, midY + _tension);
                _p.moveTo(_fromX + _tension, midY + _tension);
                _p.lineTo(midX, midY);
                _p.moveTo(midX, midY);


                _p.lineTo(_toX - _tension, midY - _tension);
                _p.moveTo(_toX - _tension, midY - _tension);
                _p.lineTo(_toX - _tension, _toY);
                _p.moveTo(_toX - _tension, _toY);
                _p.lineTo(_toX, _toY);

            } else {

                _p.lineTo(_fromX + _tension, _fromY);
                _p.moveTo(_fromX + _tension, _fromY);
                _p.lineTo(_fromX + _tension, midY);
                _p.moveTo(_fromX + _tension, midY);
                _p.lineTo(midX, midY);
                _p.moveTo(midX, midY);


                _p.lineTo(_toX - _tension, midY);
                _p.moveTo(_toX - _tension, midY);
                _p.lineTo(_toX - _tension, _toY);
                _p.moveTo(_toX - _tension, _toY);
                _p.lineTo(_toX, _toY);

            }
        } else {
            _tension = (midX - _fromX) / 2;
            if (_fromY == _toY) {

                _p.lineTo(midX - _tension, _fromY);
                _p.moveTo(midX - _tension, _fromY);
                _p.lineTo(midX - _tension, _fromY);
                _p.moveTo(midX - _tension, _fromY);
                _p.lineTo(midX, midY);
                _p.moveTo(midX, midY);


                _p.lineTo(midX + _tension, _toY);
                _p.moveTo(midX + _tension, _toY);
                _p.lineTo(midX + _tension, _toY);
                _p.moveTo(midX + _tension, _toY);
                _p.lineTo(_toX, _toY);


            } else {
                _p.lineTo(midX - _tension, _fromY);
                _p.moveTo(midX - _tension, _fromY);
                _p.lineTo(midX - _tension, _fromY);
                _p.moveTo(midX - _tension, _fromY);
                _p.lineTo(midX, midY);
                _p.moveTo(midX, midY);


                _p.lineTo(midX + _tension, _toY);
                _p.moveTo(midX + _tension, _toY);
                _p.lineTo(midX + _tension, _toY);
                _p.moveTo(midX + _tension, _toY);
                _p.lineTo(_toX, _toY);

            }
        }
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _size
     * @param _black
     * @param _white
     */
    public static void drawCheckerBoard(ICanvas _g, int _x, int _y, int _w, int _h, int _size, AColor _black, AColor _white) {
        for (int x = 0; (x * _size) < _w; x++) {
            for (int y = 0; (y * _size) < _h; y++) {
                if (y % 2 == 0) {
                    if (x % 2 == 0) {
                        _g.setColor(_black);
                    } else {
                        _g.setColor(_white);
                    }
                } else {
                    if (x % 2 != 0) {
                        _g.setColor(_black);
                    } else {
                        _g.setColor(_white);
                    }
                }
                _g.rect(true, _x + (x * _size), _y + (y * _size), _size, _size);
            }
        }
    }

    /**
     *
     * @param _o
     * @param _sequence
     * @return
     */
    public static IPath line(XY_I _o, Object[] _sequence) {
        IPath path = VS.path();
        if (_sequence == null || _sequence.length == 0) {
            return path;
        }
        IView from = (IView) _sequence[0];
        for (int i = 1; i < _sequence.length; i++) {
            IView to = (IView) _sequence[i];
            double shift = 0.15d;
            XY_I fp = from.getLocationOnScreen();
            XY_I tp = to.getLocationOnScreen();

            XYWH_I fr = from.getEventBounds();//!!1-6-09 was getBounds
            XYWH_I tr = to.getEventBounds();//!!1-6-09 was getBounds

            UPaint.perimeterLineTo(path, fp, fr.w, fr.h, tp, tr.w, tr.h, _o, 1, shift, true, 1);

            from = to;
        }
        return path;
    }

    /**
     *
     * @param _o
     * @param _sequence
     * @return
     */
    public static IPath sequence(XY_I _o, Object[] _sequence) {
        return sequence(_o, _sequence, 0.15d);
    }

    /**
     *
     * @param _o
     * @param _sequence
     * @param _shift
     * @return
     */
    public static IPath sequence(XY_I _o, Object[] _sequence, double _shift) {
        IPath path = VS.path();
        if (_sequence == null || _sequence.length == 0) {
            return path;
        }
        IView from = (IView) _sequence[0];
        for (int i = 1; i < _sequence.length; i++) {
            IView to = (IView) _sequence[i];
            XY_I fp = from.getLocationOnScreen();
            XY_I tp = to.getLocationOnScreen();

            XYWH_I fr = from.getEventBounds();//!!1-6-09 was getBounds
            XYWH_I tr = to.getEventBounds();//!!1-6-09 was getBounds

            UPaint.perimeterLineTo(
                    path, fp, fr.w, fr.h, tp, tr.w, tr.h, _o, 1, _shift, true, 1);

            from = to;
        }
        return path;
    }

    /**
     *
     * @param _set
     * @param _domain
     * @return
     */
    public static Poly_I wrap(CSet _set, CSet _domain) {
        if (_set == null) {
            return null;
        }
        CArray bounds = new CArray(XYWH_I.class);
        Object[] all = _set.getAll(Object.class);
        int grow = all.length;
        if (_domain == null) {
            grow += all.length;
        }
        for (int a = 0; a < all.length; a++) {
            IView view = (IView) all[a];
            if (view == null) {
                continue;
            }
            bounds.insertLast(view.getEventBounds().growFromCenter(grow));//!!1-6-09 was getBounds
        }
        Poly_I poly = UMath.toPoly(UMath.wrapRectangles(bounds), true);
        if (_domain != null) {
            all = _domain.getAll(Object.class);
            CSet set = new CSet();
            for (int a = 0; a < all.length; a++) {
                IView view = (IView) all[a];
                if (_set.get(view) != null) {
                    continue;
                }
                XYWH_I xywh = view.getEventBounds();
                if (!poly.contains(xywh.x + xywh.w, xywh.y)) {
                    continue;
                }
                if (!poly.contains(xywh.x, xywh.y)) {
                    continue;
                }
                if (!poly.contains(xywh.x + xywh.w, xywh.y + xywh.h)) {
                    continue;
                }
                if (!poly.contains(xywh.x, xywh.y + xywh.h)) {
                    continue;
                }
                set.add(view);
            }
            Poly_I remove = wrap(set, null);
            return UMath.removeAFromB(remove, poly);
        }
        return poly;
    }

    /**
     *
     * @param _all
     * @return
     */
    public static Poly_I wrap(Object[] _all) {
        CArray bounds = new CArray(XYWH_I.class);
        int grow = _all.length;
        for (int a = 0; a < _all.length; a++) {
            IView view = (IView) _all[a];
            if (view == null) {
                continue;
            }
            bounds.insertLast(view.getEventBounds().growFromCenter(grow));//!!1-6-09 was getBounds
        }
        if (bounds.getCount() == 0) {
            return null;
        }
        Poly_I poly = UMath.toPoly(UMath.wrapRectangles(bounds), true);
        return poly;
    }

    /**
     *
     * @param _g
     * @param _colors
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _selected
     */
    public static void membershipByColor(
            ICanvas _g, Object[] _colors,
            int _x, int _y, int _w, int _h, boolean _selected) {
        if (_colors == null) {
            return;
        }
        int count = _colors.length;
        for (int i = 0; i < count; i++) {
            AColor color = null;
            if (_colors[i] instanceof AColor) {
                color = (AColor) _colors[i];
            } else if (_colors[i] instanceof IHaveColor) {
                color = ((IHaveColor) _colors[i]).getColor(null);
            }
            if (color == null) {
                continue;
            }
            if (_selected) {
                _g.setColor(color.saturate(0.25f));
            } else {
                _g.setColor(color);
            }
            int oxa = 0;
            if (i != 0) {
                oxa = 10;
            }
            int oxb = 10;
            if (i == count - 1) {
                oxb = 0;
            }
            int wc = (_w / count);

            int[] xs = new int[]{_x + (((i) * wc) + oxa), _x + (((i + 1) * wc) + oxb), _x + ((i + 1) * wc), _x + ((i) * wc)};
            int[] ys = new int[]{_y, _y, _h, _h};
            _g.polygon(true, xs, ys, 4);
        }
    }

    /**
     *
     * @param _g
     * @param _colors
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _selected
     */
    public static void underlineByColor(
            ICanvas _g, Object[] _colors,
            int _x, int _y, int _w, int _h, boolean _selected) {
        if (_colors == null) {
            return;
        }
        int count = _colors.length;
        double h = (double) _h / (double) count;
        double ry = _y;
        for (int i = 0; i < count; i++) {
            AColor color = null;
            if (_colors[i] instanceof AColor) {
                color = (AColor) _colors[i];
            } else if (_colors[i] instanceof IHaveColor) {
                color = ((IHaveColor) _colors[i]).getColor(null);
            }
            if (color == null) {
                continue;
            }
            if (_selected) {
                _g.setColor(color.saturate(0.25f));
            } else {
                _g.setColor(color);
            }
            int rh = (int) h;
            if (rh < 1) {
                rh = 1;
            }
            _g.rect(true, _x, (int) ry, _w, rh);
            ry += h;
        }
    }

    /**
     *
     * @param xs
     * @param ys
     * @param _tx
     * @param _ty
     * @param _sx
     * @param _sy
     * @return
     */
    public static IPath toCurvePath(double[] xs, double[] ys, double _tx, double _ty, double _sx, double _sy) {
        int count = xs.length;
        if (count == 0) {
            return null;
        }
        IPath path = VS.path();
        for (int k = 0; k < count; k += 2) {
            if (k == 0) {
                path.moveTo((float) (_tx + (xs[(k + 0)] * _sx)), (float) (_ty + (ys[(k + 0)] * _sy)));
            }
            if (count - k >= 3) {
                path.curveTo(
                        (float) (_tx + (xs[(k + 0)] * _sx)), (float) (_ty + (ys[(k + 0)] * _sy)),
                        (float) (_tx + (xs[(k + 1)] * _sx)), (float) (_ty + (ys[(k + 1)] * _sy)),
                        (float) (_tx + (xs[(k + 2)] * _sx)), (float) (_ty + (ys[(k + 2)] * _sy)));
            } else if (count - k == 2) {
                path.quadTo(
                        (float) (_tx + (xs[(k + 0)] * _sx)), (float) (_ty + (ys[(k + 0)] * _sy)),
                        (float) (_tx + (xs[(k + 1)] * _sx)), (float) (_ty + (ys[(k + 1)] * _sy)));
            } else if (count - k == 1) {
                path.lineTo((float) (_tx + (xs[(k + 0)] * _sx)), (float) (_ty + (ys[(k + 0)] * _sy)));
            }
        }
        return path;
    }

    /**
     *
     * @param xs
     * @param ys
     * @return
     */
    public static IPath toCurvedPath(float[] xs, float[] ys) {
        int count = xs.length;
        if (count == 0) {
            return null;
        }
        IPath path = VS.path();
        for (int k = 0; k < count; k += 2) {
            if (k == 0) {
                path.moveTo(xs[(k + 0)], ys[(k + 0)]);
            }
            if (count - k >= 3) {
                path.curveTo(
                        xs[(k + 0)], ys[(k + 0)],
                        xs[(k + 1)], ys[(k + 1)],
                        xs[(k + 2)], ys[(k + 2)]);
            } else if (count - k == 2) {
                path.quadTo(
                        xs[(k + 0)], ys[(k + 0)],
                        xs[(k + 1)], ys[(k + 1)]);
            } else if (count - k == 1) {
                path.lineTo(xs[(k + 0)], ys[(k + 0)]);
            }
        }
        return path;
    }

    /**
     *
     * @param xs
     * @param ys
     * @return
     */
    public static IPath toCurvedPath(int[] xs, int[] ys) {
        int count = xs.length;
        if (count == 0) {
            return null;
        }
        IPath path = VS.path();
        for (int k = 0; k < count; k += 2) {
            if (k == 0) {
                path.moveTo(
                        (float) xs[(k + 0)], (float) ys[(k + 0)]);
            }
            if (count - k >= 3) {
                path.curveTo(
                        (float) xs[(k + 0)], (float) ys[(k + 0)],
                        (float) xs[(k + 1)], (float) ys[(k + 1)],
                        (float) xs[(k + 2)], (float) ys[(k + 2)]);
            } else if (count - k == 2) {
                path.quadTo(
                        (float) xs[(k + 0)], (float) ys[(k + 0)],
                        (float) xs[(k + 1)], (float) ys[(k + 1)]);
            } else if (count - k == 1) {
                path.lineTo(
                        (float) xs[(k + 0)], (float) ys[(k + 0)]);
            }
        }
        return path;
    }

    /**
     *
     * @param ps
     * @return
     */
    public static IPath toCurvedPath(XY_I[] ps) {
        int count = ps.length;
        if (count == 0) {
            return null;
        }
        IPath path = VS.path();
        for (int k = 0; k < count; k += 2) {
            if (k == 0) {
                path.moveTo(
                        (float) ps[(k + 0)].x, (float) ps[(k + 0)].y);
            }
            if (count - k >= 3) {
                path.curveTo(
                        (float) ps[(k + 0)].x, (float) ps[(k + 0)].y,
                        (float) ps[(k + 1)].x, (float) ps[(k + 1)].y,
                        (float) ps[(k + 2)].x, (float) ps[(k + 2)].y);
            } else if (count - k == 2) {
                path.quadTo(
                        (float) ps[(k + 0)].x, (float) ps[(k + 0)].y,
                        (float) ps[(k + 1)].x, (float) ps[(k + 1)].y);
            } else if (count - k == 1) {
                path.lineTo(
                        (float) ps[(k + 0)].x, (float) ps[(k + 0)].y);
            }
        }
        return path;
    }

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _color
     */
    public static void sphere(ICanvas _g, int _x, int _y, int _w, int _h, AColor _color) {
        _g.setColor(_color);
        _g.oval(true, _x, _y, _w, _h);
        for (int i = 1; i < 5; i++) {
            float p = ((float) i / 5f);
            int w = _w / (i + i);
            int h = _h / (i + i);
            _g.setColor(_color.desaturate((float) (i / 5f)));
            _g.oval(true, _x + 2 + (int) ((_w / 4) * p), _y + 2 + (int) ((_h / 4) * p), w, h);
        }
    }
}
