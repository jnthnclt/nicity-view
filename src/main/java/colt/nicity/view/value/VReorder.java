/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colt.nicity.view.value;

import colt.nicity.view.border.ItemBorder;
import colt.nicity.view.event.AInputEvent;
import colt.nicity.view.list.AItem;
import colt.nicity.view.list.VList;
import colt.nicity.core.collection.CArray;
import colt.nicity.view.core.UDrop;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VIcon;
import colt.nicity.view.core.VPan;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IDropMode;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
abstract public class VReorder extends Viewer {
    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        ViewColor.onBlack();
        VReorder ro = new VReorder() {
            Object[] order = new Object[]{"five","two","seven","nine","one"};
            @Override
            public void ordered(Object[] _newOrder) {
                order = _newOrder;
            }

            @Override
            public Object[] order() {
                return order;
            }
        };
        ro.refresh();
        UV.exitFrame(new Viewer(ro), "");
    }

    /**
     *
     * @param _newOrder
     */
    abstract public void ordered(Object[] _newOrder);
    /**
     *
     * @return
     */
    abstract public Object[] order();

    /**
     *
     * @param _view
     * @return
     */
    public IView view(Object _view) {
        return new VString(_view);
    }
    private CArray<VPosition> order = new CArray<VPosition>(VPosition.class);

    /**
     *
     */
    public VReorder() {
        VList list = new VList(order, 1);
        list.setComparator(null);
        VChain c = new VChain(UV.cSN);
        c.add(new VPan(list, 400, 600));
        c.add(new VButton("Done") {

            @Override
            public void picked(IEvent _e) {
                Object[] done = new Object[order.getCount()];
                VPosition[] all = order.getAll();
                for (int i = 0; i < all.length; i++) {
                    done[i] = all[i].getValue();
                }
                ordered(done);
            }
        });
        setContent(c);
    }

    /**
     *
     */
    public void refresh() {
        order.removeAll();
        Object[] all = order();
        VPosition[] positions = new VPosition[all.length];
        for (int i = 0; i < all.length; i++) {
            positions[i] = new VPosition(all[i]);
        }
        order.insertLast(positions);
    }

    class VPosition extends AItem {

        Object value;

        VPosition(Object _value) {
            value = _value;
            final VPosition _this = this;
            VChain c = new VChain(UV.cEW);
            c.add(VIcon.icon("Int"));// drag spot
            c.add(UV.border(new VButton(VIcon.icon("up20x20")) {

                @Override
                public void picked(IEvent _e) {
                    int i = order.getIndex(_this);
                    if (i != -1) {
                        order.removeAt(i);
                        if (i - 1 >= 0) {
                            order.insertAt(_this, i - 1);
                        }
                    }
                }

                @Override
                public Object getParcel() {
                    return null;
                }

                @Override
                public IDropMode accepts(Object value, AInputEvent _e) {
                    IDropMode mode = UDrop.accepts(new Class[]{
                                VPosition.class, VPosition[].class
                            }, value);
                    return mode;
                }

                @Override
                public void dropParcel(Object dropped, IDropMode mode) {
                    if (dropped instanceof VPosition) {
                        if (dropped == _this) {
                            return;
                        }
                        int fi = order.getIndex(dropped);
                        order.removeAt(fi);
                        int ai = order.getIndex(_this);
                        if (ai - 1 >= 0) {
                            order.insertAt((VPosition)dropped, ai);
                        } else {
                            order.insertFirst((VPosition)dropped);
                        }
                    }
                }
            }, new ItemBorder()));
            c.add(UV.border(new VButton(VIcon.icon("down20x20")) {

                @Override
                public void picked(IEvent _e) {
                    int i = order.getIndex(_this);
                    if (i != -1) {
                        order.removeAt(i);
                        order.insertAt(_this, i + 1);
                    }
                }

                @Override
                public Object getParcel() {
                    return null;
                }

                @Override
                public IDropMode accepts(Object value, AInputEvent _e) {
                    IDropMode mode = UDrop.accepts(new Class[]{
                                VPosition.class, VPosition[].class
                            }, value);
                    return mode;
                }

                @Override
                public void dropParcel(Object dropped, IDropMode mode) {
                    if (dropped instanceof VPosition) {
                        if (dropped == _this) {
                            return;
                        }
                        int fi = order.getIndex(dropped);
                        order.removeAt(fi);
                        int ai = order.getIndex(_this);
                        order.insertAt((VPosition)dropped, ai + 1);
                    }
                }
            }, new ItemBorder()));
            c.add(view(value));
            setContent(c);
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public Object getParcel() {
            return this;
        }
    }
}
