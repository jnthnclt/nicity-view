/*
 * AVList.java.java
 *
 * Created on 01-03-2010 01:32:11 PM
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

import colt.nicity.view.event.AInputEvent;
import colt.nicity.view.event.AViewEvent;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.event.UEvent;
import colt.nicity.core.collection.IBackcall;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.memory.struct.UXYWH_I;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.core.Layer;
import colt.nicity.view.core.NullView;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IDropMode;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IListController;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IVItem;
import colt.nicity.view.interfaces.IVList;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IViewable;
import java.util.Comparator;

/**
 *
 * @author Administrator
 */
public abstract class AVList extends AItem implements IVList, IMouseEvents, IMouseMotionEvents {

    /**
     *
     */
    public static final int cHorizontal = -1;
    /**
     *
     */
    public static final int cVertical = 1;

    /**
     *
     */
    public AVList() {
    }

    /**
     *
     * @param _value
     * @return
     */
    public IVItem vItem(Object _value) {
        if (_value == null) {
            return null;
        }
        if (_value instanceof IVItem) {
            return (IVItem) _value;
        }
        if (_value instanceof IViewable) {
            IView view = ((IViewable) _value).getView();
            if (view instanceof IVItem) {
                return (IVItem) view;
            }
            return new VItem(view, _value);
        }
        return new VItem(new VString(_value), _value);
    }

    /**
     *
     * @param _backcall
     */
    public void setBackcall(IBackcall _backcall) {
        getListController().setBackcall(_backcall);
    }

    /**
     *
     * @return
     */
    public IVItem getSelectedItem() {
        return getListController().getSelectedItem();
    }

    /**
     *
     * @return
     */
    public IVItem[] getSelectedItems() {
        return getListController().getSelectedItems();
    }

    /**
     *
     */
    public void selectAllItems() {
        getListController().selectAllItems();
    }

    /**
     *
     */
    public void deselectAllItems() {
        getListController().deselectAllItems();
    }

    /**
     *
     * @param _
     */
    public void listModified(IOut _) {
        getListController().listModified(_);
    }

    /**
     *
     * @param _
     */
    public void filterModified(IOut _) {
        getListController().filterModified(_);
    }

    /**
     *
     * @param _empty
     */
    public void setEmptyView(IView _empty) {
    }

    /**
     *
     * @return
     */
    public IView getEmptyView() {
        return NullView.cNull;
    }

    /**
     *
     * @return
     */
    abstract public IListController getListController();

    /**
     *
     * @param _controller
     */
    abstract public void setListController(IListController _controller);

    /**
     *
     * @return
     */
    public int getRowsColums() {
        return 0;
    }

    /**
     *
     * @param _rows_colums
     */
    public void setRowsColums(int _rows_colums) {
    }

    /**
     *
     * @return
     */
    public IVItem[] getItems() {
        IListController controller = getListController();
        if (controller == NullListController.cNull) {
            return null;
        }
        return controller.getItems();
    }

    /**
     *
     * @param _comparator
     */
    public void setComparator(Comparator _comparator) {
        IListController controller = getListController();
        controller.setComparator(_comparator);
    }

    /**
     *
     * @return
     */
    public boolean isVertical() {
        return getRowsColums() > 0;
    }

    /**
     *
     * @return
     */
    public boolean isHorizontal() {
        return getRowsColums() < 0;
    }

    /**
     *
     * @param _parent
     * @param _event
     * @return
     */
    public IView disbatchEvent(IView _parent, AViewEvent _event) {
        IVItem[] items = getItems();
        if (items == null || items.length == 0) {
            getEmptyView().setParentView(this);
            IView v = _event.disbatchEvent(this, getEmptyView());
            if (v != NullView.cNull) {
                return v;
            }
        } else {
            for (int i = 0; i < items.length; i++) {
                IView view = items[i];
                if (view == null) {
                    continue;
                }
                view.setParentView(this);
                IView v = _event.disbatchEvent(this, view);
                if (v != NullView.cNull) {
                    return v;
                }
            }
        }
        return super.disbatchEvent(_parent, _event);
    }

    /**
     *
     * @param item
     * @param direction
     * @return
     */
    public IView transferFocusToNearestNeighbor(IVItem item, int direction) {
        return NullView.cNull;
    }

    /**
     *
     * @param _who
     * @return
     */
    public IView transferFocusToChild(long _who) {
        IVItem[] items = getItems();
        if (items == null || items.length == 0) {
            return getEmptyView().transferFocusToChild(_who);
        }
        if (items.length > 0) {
            IView view = items[0];
            if (view != null) {
                view.grabFocus(_who);
                return view;
            }
        }
        return NullView.cNull;
    }

    /**
     *
     * @param g
     * @param _layer
     * @param _mode
     * @param _painted
     */
    public void paintBody(ICanvas g, Layer _layer, int _mode, XYWH_I _painted) {
        IVItem[] items = getItems();
        if (items == null || items.length == 0) {
            getEmptyView().paint(
                    this, g,
                    _layer,
                    _mode,
                    _painted);
            getEmptyView().getPlacers().paintPlacers(this, g, _layer, _mode, _painted);
            super.paintBody(g, _layer, _mode, _painted);
            return;
        }

        for (int i = items.length - 1; i > -1; i--) {
            IView view = items[i];
            if (view == null) {
                continue;
            }

            float _y = view.getY();
            float _h = view.getH();

            if (_y < (_layer.y() - _layer.ty) && (_y + _h) < (_layer.y() - _layer.ty)) {
                continue;
            }
            view.paint(this, g, _layer, _mode, _painted);
            view.getPlacers().paintPlacers(this, g, _layer, _mode, _painted);
        }
        super.paintBody(g, _layer, _mode, _painted);

    }

    /**
     *
     * @param _task
     */
    public void promoteEvent(IEvent _task) {
        if (_task instanceof AViewEvent) {
            UEvent.processEvent(getListController(), _task);
        }
    }
    // Bounding
    XY_I pressed;
    XY_I dragging;

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public void paintBorder(ICanvas _g, int _x, int _y, int _w, int _h) {
        super.paintBorder(_g, _x, _y, _w, _h);
        if (pressed != null && dragging != null) {
            _g.setColor(ViewColor.cThemeSelected);
            _g.setAlpha(0.3f, 0);
            XYWH_I r = UXYWH_I.rect(pressed.x, pressed.y, dragging.x, dragging.y);
            _g.rect(true, _x + r.x, _y + r.y, r.w, r.h);
            _g.setAlpha(1f, 0);
            _g.rect(false, _x + r.x, _y + r.y, r.w, r.h);
        }
        IListController controller = getListController();
        IVItem[] items = controller.getItems();
        _g.setColor(ViewColor.cThemeFont);
        _g.setFont(UV.fonts[UV.cMicro]);
        _g.drawString("" + items.length, 2, _h - 3);
    }

    // IMouseEvents
    /**
     *
     * @param e
     */
    public void mouseEntered(MouseEntered e) {
        super.mouseEntered(e);
    }

    /**
     *
     * @param e
     */
    public void mouseExited(MouseExited e) {
        super.mouseExited(e);
    }

    /**
     *
     * @param e
     */
    public void mousePressed(MousePressed e) {
        super.mousePressed(e);
        pressed = e.getPoint();
    }

    /**
     *
     * @param e
     */
    public void mouseReleased(MouseReleased e) {
        super.mouseReleased(e);
        XY_I _dragging = dragging;
        XY_I _pressed = pressed;
        if (_dragging != null && _pressed != null) {
            XYWH_I r = UXYWH_I.rect(_pressed.x, _pressed.y, _dragging.x, _dragging.y);
            IVItem[] items = getItems();
            for (int i = 0; i < items.length; i++) {
                XYWH_I ir = items[i].getEventBounds();//!!1-6-09 was getBounds
                if (r.intersects(ir)) {
                    if (items[i].isSelected()) {
                        items[i].deselectBorder();
                    } else {
                        items[i].selectBorder();
                    }
                }
            }
        }
        pressed = null;
        dragging = null;
    }

    // IMouseMotionEvents
    /**
     *
     * @param _e
     */
    public void mouseMoved(MouseMoved _e) {
        super.mouseMoved(_e);
    }

    /**
     *
     * @param _e
     */
    public void mouseDragged(MouseDragged _e) {
        super.mouseDragged(_e);
        dragging = _e.getPoint();
        paint();
    }

    // IDrop
    /**
     *
     * @param value
     * @param _e
     * @return
     */
    public IDropMode accepts(Object value, AInputEvent _e) {
        return null;
    }

    /**
     *
     * @param value
     * @param mode
     */
    public void dropParcel(final Object value, final IDropMode mode) {
    }
}

