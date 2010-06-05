/*
 * ULinkDrawer.java.java
 *
 * Created on 01-03-2010 01:33:51 PM
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
package com.colt.nicity.view.ngraph;

import com.colt.nicity.view.paint.UPaint;
import com.colt.nicity.core.lang.UDouble;
import com.colt.nicity.core.lang.UMath;
import com.colt.nicity.core.lang.URandom;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.interfaces.ICanvas;
import java.awt.geom.GeneralPath;

/**
 *
 * @author Administrator
 */
public class ULinkDrawer {

    /**
     *
     */
    public static LinkDrawer cFromTo = new LinkDrawer("FromTo") {

        @Override
        public void draw(ICanvas _g, XY_I fp, XY_I tp, double _rank, int _size) {
            double percent = UDouble.percent(_rank, 0, 1);
            percent = 1d - UDouble.constrain(percent, 0, 1 - Double.MIN_VALUE);
            AColor color = AColor.getWarmToCool(percent);
            _g.setColor(color);
            if (fp == tp) {
                GeneralPath p = new GeneralPath();
                int size = 4 + (int) ((1d - percent) * _size);
                p = new GeneralPath();
                UPaint.solidArrow(p, fp.x + (size * 2), fp.y - size, fp.x + (size * 2), fp.y, size, (float) (Math.PI / 2));
                _g.fill(p);
                _g.oval(false, fp.x, fp.y - size, size * 2, size * 2);
            } else if (fp != null && tp != null) {
                GeneralPath p = new GeneralPath();
                UPaint.drawCurveLink(p, fp.x, fp.y, tp.x, tp.y, 0.01f);
                _g.draw(p);
            }
        }
    };
    /**
     *
     */
    public static LinkDrawer cDefault = new LinkDrawer("Link") {

        public void draw(ICanvas _g, XY_I fp, XY_I tp, double _rank, int _size) {
            double percent = UDouble.percent(_rank, 0, 1);
            percent = 1d - UDouble.constrain(percent, 0, 1 - Double.MIN_VALUE);
            AColor color = AColor.getWarmToCool(percent);
            _g.setColor(color);
            if (fp == tp) {
                GeneralPath p = new GeneralPath();
                int size = 4 + (int) ((1d - percent) * _size);
                p = new GeneralPath();
                UPaint.solidArrow(p, fp.x + (size * 2), fp.y - size, fp.x + (size * 2), fp.y, size, (float) (Math.PI / 2));
                _g.fill(p);
                _g.oval(false, fp.x, fp.y - size, size * 2, size * 2);
            } else {
                double deflect = 0.005d + (0.005d * percent);
                GeneralPath p = new GeneralPath();
                UPaint.deflectLine(p, tp, fp, deflect);
                _g.draw(p);
                XY_I dm = UPaint.deflectMiddle(tp, fp, deflect);
                int size = 4 + (int) ((1d - percent) * _size);
                p = new GeneralPath();
                UPaint.solidArrow(p, fp.x, fp.y, dm.x, dm.y, size, (float) (Math.PI / 2));
                _g.fill(p);
            }
        }
    };

    /**
     *
     * @param _key
     * @param _color
     * @param _fill
     * @return
     */
    public static LinkDrawer longArrow(Object _key, final AColor _color, final boolean _fill) {
        LinkDrawer cLongArrow = new LinkDrawer(_key) {

            public void draw(ICanvas _g, XY_I f, XY_I t, double _rank, int _size) {
                double distance = UMath.distance(f.x, f.y, t.x, t.y);
                double percent = UDouble.percent(_rank, 0, 1);
                percent = 1d - UDouble.constrain(percent, 0, 1 - Double.MIN_VALUE);
                if (_color != null) {
                    _g.setColor(_color);
                }
                int size = 4 + (int) ((1d - percent) * _size);
                if (size > distance / 4) {
                    size = (int) distance / 4;
                }
                GeneralPath path = new GeneralPath();
                UPaint.longArrow(path, f.x, f.y, t.x, t.y, size, (float) (Math.PI / 2));
                if (_fill) {
                    _g.fill(path);
                } else {
                    _g.draw(path);
                }
            }
        };
        return cLongArrow;
    }

    /**
     *
     * @param _key
     * @param _color
     * @param _fill
     * @param _invert
     * @return
     */
    public static LinkDrawer diamond(Object _key, final AColor _color, final boolean _fill, final boolean _invert) {
        LinkDrawer cDiamond = new LinkDrawer(_key) {

            public void draw(ICanvas _g, XY_I f, XY_I t, double _rank, int _size) {
                double distance = UMath.distance(f.x, f.y, t.x, t.y);
                double percent = UDouble.percent(_rank, 0, 1);
                percent = 1d - UDouble.constrain(percent, 0, 1 - Double.MIN_VALUE);
                if (_color != null) {
                    _g.setColor(_color);
                }
                int size = 4 + (int) ((1d - percent) * _size);
                if (size > distance / 4) {
                    size = (int) distance / 4;
                }
                int mx = (int) UMath.middle(f.x, t.x, 0.5d);
                int my = (int) UMath.middle(f.y, t.y, 0.5d);
                GeneralPath path = new GeneralPath();
                if (_invert) {
                    UPaint.longArrow(path, f.x, f.y, mx, my, size, (float) (Math.PI / 2));
                    UPaint.longArrow(path, t.x, t.y, mx, my, size, (float) (Math.PI / 2));
                } else {
                    UPaint.longArrow(path, mx, my, f.x, f.y, size, (float) (Math.PI / 2));
                    UPaint.longArrow(path, mx, my, t.x, t.y, size, (float) (Math.PI / 2));
                }
                if (_fill) {
                    _g.fill(path);
                } else {
                    _g.draw(path);
                }
            }
        };
        return cDiamond;
    }

    /**
     *
     * @param _key
     * @param _color
     * @param _fill
     * @param _line
     * @param _shrink
     * @param _expand
     * @return
     */
    public static LinkDrawer dots(
            Object _key, final AColor _color, final boolean _fill,
            final boolean _line, final boolean _shrink, final boolean _expand) {
        LinkDrawer cDots = new LinkDrawer(_key) {

            public void draw(ICanvas _g, XY_I f, XY_I t, double _rank, int _size) {
                double percent = UDouble.percent(_rank, 0, 1);
                percent = 1d - UDouble.constrain(percent, 0, 1 - Double.MIN_VALUE);
                if (_color != null) {
                    _g.setColor(_color);
                }
                if (_line) {
                    _g.line(f.x, f.y, t.x, t.y);
                }
                int size = 4 + (int) ((1d - percent) * _size);
                double distance = UMath.distance(f.x, f.y, t.x, t.y);
                GeneralPath path = new GeneralPath();
                int count = (int) (distance / (size / 2));
                for (int i = 0; i < count; i++) {
                    double p = (double) (i + 1) / (double) count;
                    int _s = size;
                    if (_shrink || _expand) {
                        _s = 0;
                        if (_shrink) {
                            _s += (int) (size * p);
                        }
                        if (_expand) {
                            _s += (int) (size * (1d - p));
                        }
                        if (_shrink && _expand) {
                            _s /= 2;
                        }
                    }
                    int hs = _s / 2;
                    if (_fill) {
                        _g.oval(true, (int) UMath.middle(f.x, t.x, p) - hs, (int) UMath.middle(f.y, t.y, p) - hs, _s, _s);
                    } else {
                        _g.oval(false, (int) UMath.middle(f.x, t.x, p) - hs, (int) UMath.middle(f.y, t.y, p) - hs, _s, _s);
                    }
                }
                _g.fill(path);
            }
        };
        return cDots;
    }

    /**
     *
     * @param _key
     * @param _color
     * @param _line
     * @param _shrink
     * @param _expand
     * @return
     */
    public static LinkDrawer arrows(
            Object _key, final AColor _color,
            final boolean _line, final boolean _shrink, final boolean _expand) {
        LinkDrawer cArrows = new LinkDrawer("Arrows") {

            public void draw(ICanvas _g, XY_I f, XY_I t, double _rank, int _size) {
                double percent = UDouble.percent(_rank, 0, 1);
                percent = 1d - UDouble.constrain(percent, 0, 1 - Double.MIN_VALUE);
                if (_color != null) {
                    _g.setColor(_color);
                }
                if (_line) {
                    _g.line(f.x, f.y, t.x, t.y);
                }
                int size = 4 + (int) ((1d - percent) * _size);
                double distance = UMath.distance(f.x, f.y, t.x, t.y);
                GeneralPath path = new GeneralPath();
                int count = (int) (distance / (size / 2));
                for (int i = 0; i < count; i++) {
                    double p = (double) (i + 1) / (double) count;
                    int _s = size;
                    if (_shrink || _expand) {
                        _s = 0;
                        if (_shrink) {
                            _s += (int) (size * p);
                        }
                        if (_expand) {
                            _s += (int) (size * (1d - p));
                        }
                        _s /= 2;
                    }
                    UPaint.solidArrow(path, f.x, f.y, (int) UMath.middle(f.x, t.x, p), (int) UMath.middle(f.y, t.y, p), _s, (float) (Math.PI / 2));
                }
                _g.fill(path);
            }
        };
        return cArrows;
    }

    /**
     *
     * @param _key
     * @param _color
     * @param _fill
     * @return
     */
    public static LinkDrawer moons(
            Object _key, final AColor _color,
            final boolean _fill) {
        LinkDrawer cMoon = new LinkDrawer("Moon") {

            public void draw(ICanvas _g, XY_I f, XY_I t, double _rank, int _size) {
                double percent = UDouble.percent(_rank, 0, 1);
                percent = 1d - UDouble.constrain(percent, 0, 1 - Double.MIN_VALUE);
                if (_color != null) {
                    _g.setColor(_color);
                }
                int size = 4 + (int) ((1d - percent) * _size);
                GeneralPath path = new GeneralPath();
                UPaint.moonArrow(path, f, t, -size, size);
                if (_fill) {
                    _g.fill(path);
                } else {
                    _g.draw(path);
                }
            }
        };
        return cMoon;
    }

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer outUp(
            Object _key, final AColor _color) {
        LinkDrawer cOutUp = new LinkDrawer("OutUp") {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                _g.oval(true, _f.x - 4, _f.y - 4, _size, _size);
                GeneralPath path = new GeneralPath();
                path.moveTo(_f.x, _f.y);
                path.curveTo(_t.x, _f.y, _t.x, _f.y, _t.x, _t.y);
                UPaint.arrowHead(path, _t.x, _t.y, 0, 16, 45);
                _g.draw(path);
            }
        };
        return cOutUp;
    }

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer downOut(
            Object _key, final AColor _color) {
        LinkDrawer c = new LinkDrawer("DownOut") {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                _g.oval(true, _f.x - 4, _f.y - 4, _size, _size);
                GeneralPath path = new GeneralPath();
                path.moveTo(_f.x, _f.y);
                path.curveTo(_f.x, _t.y, _f.x, _t.y, _t.x, _t.y);
                UPaint.arrowHead(path, _t.x, _t.y, 270, 16, 45);
                _g.draw(path);
            }
        };
        return c;
    }

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer inout(
            Object _key, final AColor _color) {
        LinkDrawer cInOut = new LinkDrawer("InOut") {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                _g.oval(true, _f.x - 4, _f.y - 4, _size, _size);
                GeneralPath path = new GeneralPath();
                UPaint.drawCurveLink(path, _f.x, _f.y, _t.x, _t.y, 0.5f);
                UPaint.arrowHead(path, _t.x, _t.y, 270, 16, 45);
                _g.draw(path);
            }
        };
        return cInOut;
    }

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer outin(
            Object _key, final AColor _color) {
        LinkDrawer cOutIn = new LinkDrawer("OutIn") {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                _g.oval(true, _f.x - 4, _f.y - 4, _size, _size);
                GeneralPath path = new GeneralPath();
                UPaint.drawCurveLink(path, _f.x, _f.y, _t.x, _t.y, 0.5f);
                UPaint.arrowHead(path, _f.x, _f.y, 90, 16, 45);
                _g.draw(path);
            }
        };
        return cOutIn;
    }
    /**
     *
     */
    public static LinkDrawer cLine = line("Line", AColor.gray);

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer line(
            Object _key, final AColor _color) {
        LinkDrawer cLine = new LinkDrawer("Line") {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                _g.line(_f.x, _f.y, _t.x, _t.y);
            }
        };
        return cLine;
    }

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer directedLine(
            Object _key, final AColor _color) {
        LinkDrawer cLine = new LinkDrawer("Directed Line") {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                _g.line(_f.x, _f.y, _t.x, _t.y);
                GeneralPath path = new GeneralPath();
                UPaint.solidArrow(path, _f.x, _f.y, _t.x, _t.y, 8, 90);
                _g.fill(path);
            }
        };
        return cLine;
    }

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer elbow(
            Object _key, final AColor _color) {
        LinkDrawer cLine = new LinkDrawer("Elbow") {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                GeneralPath path = new GeneralPath();
                path.moveTo(_f.x, _f.y);
                path.curveTo(_f.x, _f.y, _t.x, _f.y, _t.x, _t.y);
                _g.draw(path);
            }
        };
        return cLine;
    }

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer sideToSide(Object _key, final AColor _color) {
        LinkDrawer dld = new LinkDrawer(_key) {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                int mx = (int) UMath.middle(_f.x, _t.x);
                int my = (int) UMath.middle(_f.y, _t.y);

                GeneralPath path = UPaint.toCurvedPath(new int[]{_f.x, mx, mx, mx, _t.x}, new int[]{_f.y, _f.y, my, _t.y, _t.y});
                _g.draw(path);
            }
        };
        return dld;
    }

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer updown(
            Object _key, final AColor _color) {
        LinkDrawer cUpDown = new LinkDrawer(_key) {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                int mx = (int) UMath.middle(_f.x, _t.x);
                int my = (int) UMath.middle(_f.y, _t.y);

                GeneralPath path = UPaint.toCurvedPath(new int[]{_f.x, _f.x, mx, _t.x, _t.x}, new int[]{_f.y, my, my, my, _t.y});
                _g.draw(path);
            }
        };
        return cUpDown;
    }

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer wire(
            Object _key, final AColor _color) {
        LinkDrawer cWire = new LinkDrawer(_key) {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                int mx = (int) UMath.middle(_f.x, _t.x);
                int my = (int) UMath.middle(_f.y, _t.y);
                if (_f.y < _t.y) {
                    GeneralPath path = UPaint.toCurvedPath(new int[]{_f.x, _f.x, mx, _t.x, _t.x}, new int[]{_f.y, my, my, my, _t.y});
                    _g.draw(path);
                } else if (_f.y > _t.y) {
                    GeneralPath path = UPaint.toCurvedPath(new int[]{_f.x, _f.x, mx, mx, mx, _t.x, _t.x}, new int[]{_f.y, _f.y + 32, _f.y + 32, my, _t.y - 32, _t.y - 32, _t.y});
                    _g.draw(path);
                } else {
                    GeneralPath path = UPaint.toCurvedPath(new int[]{_f.x, _f.x, mx, _t.x, _t.x}, new int[]{_f.y, my, my, my, _t.y});
                    _g.draw(path);
                }
            }
        };
        return cWire;
    }

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer wireLeft(
            Object _key, final AColor _color) {
        LinkDrawer cWire = new LinkDrawer(_key) {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                int mx = (int) UMath.middle(_f.x, _t.x);
                int my = (int) UMath.middle(_f.y, _t.y);
                if (_f.y < _t.y) {
                    GeneralPath path = UPaint.toCurvedPath(new int[]{_f.x, _f.x, mx, _t.x, _t.x}, new int[]{_f.y, my, my, my, _t.y});
                    _g.draw(path);
                } else if (_f.y > _t.y) {
                    mx = (int) Math.min(_f.x, _t.x) - 32;
                    GeneralPath path = UPaint.toCurvedPath(new int[]{_f.x, _f.x, mx, mx, mx, _t.x, _t.x}, new int[]{_f.y, _f.y + 32, _f.y + 32, my, _t.y - 32, _t.y - 32, _t.y});
                    _g.draw(path);
                } else {
                    GeneralPath path = UPaint.toCurvedPath(new int[]{_f.x, _f.x, mx, _t.x, _t.x}, new int[]{_f.y, my, my, my, _t.y});
                    _g.draw(path);
                }
            }
        };
        return cWire;
    }

    /**
     *
     * @param _key
     * @param _color
     * @return
     */
    public static LinkDrawer wireRight(
            Object _key, final AColor _color) {
        LinkDrawer cWire = new LinkDrawer(_key) {

            public void draw(ICanvas _g, XY_I _f, XY_I _t, double _rank, int _size) {
                if (_f == null || _t == null) {
                    return;
                }
                if (_color != null) {
                    _g.setColor(_color);
                }
                int mx = (int) UMath.middle(_f.x, _t.x);
                int my = (int) UMath.middle(_f.y, _t.y);
                if (_f.y < _t.y) {
                    GeneralPath path = UPaint.toCurvedPath(new int[]{_f.x, _f.x, mx, _t.x, _t.x}, new int[]{_f.y, my, my, my, _t.y});
                    _g.draw(path);
                } else if (_f.y > _t.y) {
                    mx = (int) Math.max(_f.x, _t.x) + 32;
                    GeneralPath path = UPaint.toCurvedPath(new int[]{_f.x, _f.x, mx, mx, mx, _t.x, _t.x}, new int[]{_f.y, _f.y + 32, _f.y + 32, my, _t.y - 32, _t.y - 32, _t.y});
                    _g.draw(path);
                } else {
                    GeneralPath path = UPaint.toCurvedPath(new int[]{_f.x, _f.x, mx, _t.x, _t.x}, new int[]{_f.y, my, my, my, _t.y});
                    _g.draw(path);
                }
            }
        };
        return cWire;
    }

    /**
     *
     * @param _key
     * @return
     */
    public static LinkDrawer randomDrawer(Object _key) {

        final AColor color = AColor.randomSolid(127, 127);
        final boolean fill = (URandom.rand(100) < 50) ? true : false;
        if (URandom.rand(100) < 25) {
            return longArrow(_key, color, fill);
        }
        if (URandom.rand(100) < 25) {
            final boolean invert = (URandom.rand(100) < 50) ? true : false;
            return diamond(_key, color, fill, invert);
        }
        if (URandom.rand(100) < 50) {
            final boolean line = (URandom.rand(100) < 50) ? true : false;
            final boolean shrink = (URandom.rand(100) < 50) ? true : false;
            final boolean expand = (URandom.rand(100) < 50) ? true : false;
            return dots(_key, color, fill, line, shrink, expand);
        } else {
            final boolean line = (URandom.rand(100) < 50) ? true : false;
            final boolean shrink = (URandom.rand(100) < 50) ? true : false;
            final boolean expand = (URandom.rand(100) < 50) ? true : false;

            return arrows(_key, color, line, shrink, expand);
        }

    }
}
