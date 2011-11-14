/*
 * VClip.java.java
 *
 * Created on 01-03-2010 01:31:33 PM
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
package colt.nicity.view.core;

import colt.nicity.view.event.AMouseEvent;
import colt.nicity.view.event.AViewEvent;
import colt.nicity.core.lang.UFloat;
import colt.nicity.core.memory.struct.UXYWH_I;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.interfaces.ISizeable;
import colt.nicity.view.interfaces.ISupportSizeDependecy;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VClip extends Viewer implements ISizeable, ISupportSizeDependecy {

    /**
     *
     */
    protected float alignX = -1.0f;
    /**
     *
     */
    protected float alignY = -1.0f;
    /**
     *
     */
    protected float offsetX = 0.0f;
    /**
     *
     */
    protected float offsetY = 0.0f;
    /**
     *
     */
    protected float wSlack = 0;
    /**
     *
     */
    protected float hSlack = 0;
    /**
     *
     */
    protected IView view = NullView.cNull;
    /**
     *
     */
    protected boolean autoCenterX = false;
    /**
     *
     */
    protected boolean autoCenterY = false;
    /**
     *
     */
    public int overScroll = 32;

    /**
     *
     * @param _view
     * @param _w
     * @param _h
     */
    public VClip(IView _view, float _w, float _h) {
        super();
        view = _view;
        //view.spans(UV.cXNSEW);//??

        placer = new Placer(view, new Place(UV.cOrigin, 2, 2, UV.cFGG));
        w = _w;
        h = _h;
        if (_w > 0) {
            alignX = 0;
        }
        if (_h > 0) {
            alignY = 0;
        }
    }
    /**
     *
     */
    public XY_I hDependant;//!! hacky
    /**
     *
     */
    public XY_I wDependant;//!! hacky

    @Override
    public void setWDependency(XY_I _d) {
        wDependant = _d;
        paint();
    }

    @Override
    public void setHDependency(XY_I _d) {
        hDependant = _d;
        paint();
    }

    @Override
    public float getH() {
        float ch = super.getH();
        if (hDependant != null) {
            float _h = hDependant.getY();
            if (ch != _h) {
                h = _h;
                getParentView().layoutInterior();
            }
            return _h;
        } else {
            return ch;
        }
    }

    @Override
    public float getW() {
        float cw = super.getW();
        if (wDependant != null) {
            float _w = wDependant.getX();
            if (cw != _w) {
                w = _w;
                getParentView().layoutInterior();
            }
            return _w;
        } else {
            return cw;
        }
    }

    // ISizeable
    @Override
    public void setSize(int _w, int _h) {
        w = _w;
        h = _h;
    }

    @Override
    public float getOriginX() {
        return offsetX;
    }

    @Override
    public float getOriginY() {
        return offsetY;
    }

    private static float adjustAlignmentForFlex(float viewSize, float clipSize, float alignment, float flex) {
        float offset = -(alignment * ((viewSize - flex) - clipSize));
        float newAlignment = (-offset) / (viewSize - clipSize);
        newAlignment = UFloat.checkFloat(newAlignment, 0.0f);
        if (newAlignment < 0.0f) {
            newAlignment = 0.0f;
        } else if (newAlignment > 1.0f) {
            newAlignment = 1.0f;
        }
        return newAlignment;
    }

    /**
     *
     * @return
     */
    public IView getContextView() {
        return placer.getView();
    }

    @Override
    synchronized public void layoutInterior(IView _parent, Flex _flex) {
        IView _view = placer.getView();
        IView isChild = UV.getChild(_parent, _flex.getCreator());
        if (isChild != null || _flex.getCreator() == this) {
            // A view inside this VClip is flexing so only take the flex into account
            // if the flex won't be sucked up by scroll bars
            XY_I p = getLocationOnScreen();
            if (alignX < 0.0f) {
                w = _flex.flexW(getW());
            } else {
                alignX = adjustAlignmentForFlex(_view.getW(), w, alignX, _flex.x);
            }
            if (alignY < 0.0f) {
                h = _flex.flexH(getH());
            } else {
                alignY = adjustAlignmentForFlex(_view.getH(), h, alignY, _flex.y);
            }
        } else {
            // A  parent view is telling this clipCell to flex so take the flex seriously
            Flex clone = (Flex) _flex.clone();
            clone.setCreator(_view);
            w = (alignX < 0.0f) ? _flex.flexW(w) : clone.flexW(w);
            h = (alignY < 0.0f) ? _flex.flexH(h) : clone.flexH(h);
            _flex = clone;
        }

        _view.setParentView(_parent);
        _view.disableFlag(UV.cInterior);
        _view.layoutInterior(_flex);
        float vw = _view.getW();
        float vh = _view.getH();

        if (alignX < 0.0f) {
            w = vw;
        }
        if (alignY < 0.0f) {
            h = vh;
        }

        wSlack = vw - w;
        if (wSlack > 0) {
            offsetX = -(int) (alignX * (wSlack));
        } else if (autoCenterX) {
            offsetX = -(wSlack / 2);
        }

        hSlack = vh - h;
        if (hSlack > 0) {
            offsetY = -(int) (alignY * (hSlack));
        } else if (autoCenterY) {
            offsetY = -(hSlack / 2);
        }
    }

    /**
     *
     * @return
     */
    public float getAlignX() {
        return alignX;
    }

    /**
     *
     * @return
     */
    public float getAlignY() {
        return alignY;
    }

    /**
     *
     * @param _x
     * @param _caller
     */
    public void setAlignX(float _x, Object _caller) {
        alignX = _x;
        IView _view = placer.getView();
        if (alignX < 0.0f) {
            w = _view.getW();
        }
        float _wSlack = (_view.getW() + overScroll) - w;
        offsetX = (_wSlack > 0) ? -(int) (alignX * (_wSlack)) : 0;
    }

    /**
     *
     * @param _y
     * @param _caller
     */
    public void setAlignY(float _y, Object _caller) {
        alignY = _y;

        IView _view = placer.getView();
        if (alignY < 0.0f) {
            h = _view.getH();
        }
        float _hSlack = (_view.getH() + overScroll) - h;
        offsetY = (_hSlack > 0) ? -(int) (alignY * (_hSlack)) : 0;
    }

    /**
     *
     * @param _x
     */
    public void setOffsetX(float _x) {
        offsetX = _x;
        IView _view = placer.getView();
        float _wSlack = _view.getW() - w;
        alignX = -(offsetX / _wSlack);
    }

    /**
     *
     * @param _y
     */
    public void setOffsetY(float _y) {
        offsetY = _y;
    }

    @Override
    public boolean isVisible(int _x, int _y, int _w, int _h) {

        int ox = (int) (_x + offsetX);
        int oy = (int) (_y + offsetY);

        XYWH_I b = getEventBounds();
        XYWH_I r = UXYWH_I.intersection(ox, oy, _w, _h, 0, 0, (int) b.w, (int) b.h);
        if (r == null) {
            return false;
        }

        return getParentView().isVisible(r.x + (int) b.x, r.y + (int) b.y, r.w, r.h);
    }

    /**
     *
     * @return
     */
    @Override
    public float ox() {
        return offsetX;
    }

    /**
     *
     * @return
     */
    @Override
    public float oy() {
        return offsetY;
    }

    @Override
    public IView disbatchEvent(IView parent, AViewEvent event) {
        // translate events location based on clip
        if (event instanceof AMouseEvent) {
            AMouseEvent me = (AMouseEvent) event;
            me.shiftXY(-(offsetX + border.getX()), -(offsetY + border.getY()));
            me.shiftTXY(offsetX + border.getX(), offsetY + border.getY());
        }
        return super.disbatchEvent(parent, event);
    }

    @Override
    public void scrollTo(float _x, float _y, float _w, float _h) {
        IView _view = placer.getView();
        float vw = _view.getW();
        float vh = _view.getH();

        float oldAlignX = alignX;
        float oldAlignY = alignY;

        if (_y > -offsetY && _y + _h < -offsetY + vh) {
            return;//??
            //if (_y > -offsetY && _y+_h < -offsetY+vh) return;//??

// when _y < 0 decrease alignY so that _y == 0
// when _y+h >h increase alignY so that _y+h ==h
        }
        if (alignY != -1.0f) {
            if ((_y - _h) < 0) {
                float newPosition = (_y - _h) - _view.getY();
                alignY = (newPosition) / (vh - h);
                alignY = UFloat.checkFloat(alignY, 0.0f);
                if (alignY > 1.0f) {
                    alignY = 1.0f;
                } else if (alignY < 0.0f) {
                    alignY = 0.0f;
                }
            } else if (_y + (_h * 2) > h) {
                float newPosition = (-_view.getY()) + ((_y + (_h * 2)) - h);
                alignY = (newPosition) / (vh - h);
                alignY = UFloat.checkFloat(alignY, 0.0f);
                if (alignY > 1.0f) {
                    alignY = 1.0f;
                } else if (alignY < 0.0f) {
                    alignY = 0.0f;
                }
            }
        }

// when _x < 0 decrease alignX so that _x == 0
// when _x+w >w increase alignX so that _x+w ==w
        if (alignX != -1.0f) {
            if ((_x - _w) < 0) {
                float newPosition = (_x - _w) - _view.getX();
                alignX = (newPosition) / (vw - w);
                alignX = UFloat.checkFloat(alignX, 0.0f);
                if (alignX > 1.0f) {
                    alignX = 1.0f;
                } else if (alignX < 0.0f) {
                    alignX = 0.0f;
                }
            } else if (_x + (_w * 2) > w) {
                float newPosition = (-_view.getX()) + ((_x + (_w * 2)) - w);
                alignX = (newPosition) / (vw - w);
                alignX = UFloat.checkFloat(alignX, 0.0f);
                if (alignX > 1.0f) {
                    alignX = 1.0f;
                } else if (alignX < 0.0f) {
                    alignX = 0.0f;
                }
            }
        }

        if (alignX != oldAlignX || alignY != oldAlignY) {
            layoutInterior();
        }
    }
}
