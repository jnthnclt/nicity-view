/*
 * AItem.java.java
 *
 * Created on 01-03-2010 01:32:32 PM
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

import colt.nicity.view.adaptor.IKeyEventConstants;
import colt.nicity.view.border.ItemBorder;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.DragAndDrop;
import colt.nicity.view.core.NullPopup;
import colt.nicity.view.core.PickupAndDrop;
import colt.nicity.view.core.ULAF;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.event.AInputEvent;
import colt.nicity.view.event.FocusGained;
import colt.nicity.view.event.FocusLost;
import colt.nicity.view.event.KeyPressed;
import colt.nicity.view.event.KeyReleased;
import colt.nicity.view.event.KeyTyped;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.event.UKey;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IDrag;
import colt.nicity.view.interfaces.IDrop;
import colt.nicity.view.interfaces.IDropMode;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IFocusEvents;
import colt.nicity.view.interfaces.IFocusable;
import colt.nicity.view.interfaces.IItemIntercepter;
import colt.nicity.view.interfaces.IKeyEvents;
import colt.nicity.view.interfaces.IListController;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IPopup;
import colt.nicity.view.interfaces.IVItem;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.list.event.ItemPicked;
import colt.nicity.view.list.event.ItemSelected;

/**
 *
 * @author Administrator
 */
public abstract class AItem extends Viewer implements IVItem, IKeyEvents, IMouseEvents, IFocusEvents, IMouseMotionEvents, IDrop, IDrag {

    private static IItemIntercepter intercepter;

    /**
     *
     * @param _intercepter
     */
    public static void setItemIntercepter(IItemIntercepter _intercepter) {
        if (intercepter == _intercepter) {
            intercepter.interceptionOff(_intercepter);
            intercepter = null;
        } else if (intercepter != null) {
            intercepter.interceptionOff(_intercepter);
            intercepter = _intercepter;
            if (intercepter != null) {
                intercepter.interceptionOn();
            }
        } else if (intercepter == null) {
            intercepter = _intercepter;
            intercepter.interceptionOn();
        }
    }

    /**
     *
     * @param _item
     * @param _e
     * @return
     */
    public static boolean intercept(IVItem _item, IEvent _e) {
        if (_item == null) {
            return false;
        }
        if (intercepter == null) {
            return false;
        }
        return intercepter.intercepted(_item, _e);
    }

    // Convienient overloadable methods
    /**
     *
     * @param _e
     */
    public void picked(IEvent _e) {
    }

    /**
     *
     * @param _e
     */
    public void selected(IEvent _e) {
    }

    /**
     *
     * @param _e
     */
    public void removed(IEvent _e) {
    }

    /**
     *
     */
    public AItem() {
        setBorder(new ItemBorder());
    }

    /**
     *
     * @return
     */
    @Override
    public IPopup getPopup() {
        return NullPopup.cNull;
    }

    /**
     *
     * @param _who
     * @param direction
     * @return
     */
    @Override
    public IView transferFocusToNearestNeighbor(long _who, int direction) {
        IView p = getParentView().getView();
        if (p instanceof AVList) {
            return ((AVList) p).transferFocusToNearestNeighbor(this, direction);
        } else {
            p = UV.findParent(this, IFocusable.class);
            p.grabFocus(_who);
            return p;
        }
    }

    @Override
    public String toString() {
        Object value = getValue();
        if (value != null && value != this) {
            return value.toString();
        } else {
            return super.toString();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Object hashObject() {
        return getValue();
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
        if (ULAF.cDropping != null && DragAndDrop.cDefault.isDropOn(this)) {
            g.setAlpha(0.5f, 0);
            g.paintFlavor(ULAF.cDropping, _x, _y, _w, _h, AColor.green);
            g.setColor(AColor.black);
            g.drawString("Drop", _x + 5, _y + _h - 5);
            g.setAlpha(1f, 0);

        }
        if (ULAF.cDragging != null && DragAndDrop.cDefault.isDragFrom(this)) {
            g.setAlpha(0.5f, 0);
            g.paintFlavor(ULAF.cDragging, _x, _y, _w, _h, AColor.orange);
            g.setColor(AColor.black);
            g.drawString("Dragging", _x + 5, _y + _h - 5);
            g.setAlpha(1f, 0);
        }
    }

    // IDrag
    /**
     *
     * @return
     */
    @Override
    public Object getParcel() {
        return getValue();
    }

    // IDrop
    /**
     *
     * @param object
     * @param _e
     * @return
     */
    @Override
    public IDropMode accepts(Object object, AInputEvent _e) {
        return null;
    }

    /**
     *
     * @param object
     * @param mode
     */
    @Override
    public void dropParcel(Object object, IDropMode mode) {
    }

    /**
     *
     * @return
     */
    @Override
    public Object getValue() {
        return this;
    }

    // IKeyEvents
    /**
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyPressed e) {
        UKey.arrowKeys(e, this);
        int code = e.getKeyCode();
        if (e.isControlDown()) {
            if (code == IKeyEventConstants.cA) {
                IListController controller = getListController();
                controller.selectAllItems();
            } else if (code == IKeyEventConstants.cD) {
                IListController controller = getListController();
                controller.deselectAllItems();
            }
        } else if (code == IKeyEventConstants.cDelete) {
            if (isSelected()) {
                removed(e);
            }
        } else if (code == IKeyEventConstants.cEnter) {
            if (e.isShiftDown()) {
                selected(e);
                promoteEvent(ItemSelected.newInstance(this, e));
            } else {
                if (!intercept(this, e)) {
                    picked(e);
                    promoteEvent(ItemPicked.newInstance(this, e));
                }
            }
        } else {
            promoteEvent(e);
        }
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyReleased e) {
        promoteEvent(e);
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyTyped e) {
        promoteEvent(e);
    }

    // IMouseEvents
    /**
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEntered e) {
        DragAndDrop.cDefault.mouseEntered(e);
        grabFocus(e.who());
    }

    /**
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseExited e) {
        DragAndDrop.cDefault.mouseExited(e);
    }

    /**
     *
     * @param e
     */
    @Override
    public void mousePressed(MousePressed e) {
        DragAndDrop.cDefault.mousePressed(e);
        IListController controller = getListController();
        if (e.isControlDown()) {
            //controller.toggleItems(new IVItem[]{this});
        } else if (e.isShiftDown()) {
            //controller.selectFromSelectedItemTo(this);
        } else {
            if (this.isSelected() && controller.hasMultiSelect()); else {
                controller.selectOneItem(this);
            }
        }
    }

    /**
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseReleased e) {
        DragAndDrop.cDefault.mouseReleased(e);
        if (PickupAndDrop.cDefault.event(e)) {
            return;
        }
        MouseDragged lastDrag = e.getLastDrag();
        if (lastDrag != null) {
            if (lastDrag.getSumDeltaX() > 5 || lastDrag.getSumDeltaX() < -5) {
                return;
            }
            if (lastDrag.getSumDeltaY() > 5 || lastDrag.getSumDeltaY() < -5) {
                return;
            }
        }
        IListController controller = getListController();
        if (e.isLeftClick() || e.isRightClick()) {
            if (e.isSingleClick()) {
                if (e.isControlDown()) {
                    controller.toggleItems(new IVItem[]{this});
                } else if (e.isShiftDown()) {
                    controller.selectFromSelectedItemTo(this);
                } else {
                    controller.selectOneItem(this);
                }
                if (!intercept(this, e)) {
                    picked(e);
                    promoteEvent(ItemPicked.newInstance(this, controller.getSelectedItems(), e));
                }
            } else if (e.isDoubleClick()) {
                if (e.isControlDown()) {
                    //controller.toggleItems(new IVItem[]{this});
                } else if (e.isShiftDown()) {
                    //controller.selectFromSelectedItemTo(this);
                } else {
                    if (isSelected()) {
                        controller.selectItem(this);
                    } else {
                        controller.selectOneItem(this);
                    }
                }
                selected(e);
                promoteEvent(ItemSelected.newInstance(this, controller.getSelectedItems(), e));
            }
        }
    }

    // IMouseMotionEvents
    /**
     *
     * @param _e
     */
    @Override
    public void mouseMoved(MouseMoved _e) {
    }

    /**
     *
     * @param _e
     */
    @Override
    public void mouseDragged(MouseDragged _e) {
        DragAndDrop.cDefault.mouseDragged(_e);
    }

    // IFocusEvents
    /**
     *
     * @param e
     */
    @Override
    public void focusGained(FocusGained e) {
        activateBorder();
    }

    /**
     *
     * @param e
     */
    @Override
    public void focusLost(FocusLost e) {
        deactivateBorder((IView)e.getSource());
    }

    private IListController getListController() {
        IView foundParent = UV.findParent(this, AVList.class);//??
        if (!(foundParent instanceof AVList)) {
            return NullListController.cNull;
        }
        AVList list = (AVList) foundParent;
        IListController lc = list.getListController();
        return lc;
    }
}
