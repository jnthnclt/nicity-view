/*
 * NGEnv.java.java
 *
 * Created on 03-12-2010 06:42:35 PM
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

import com.colt.nicity.view.value.VEditValue;
import com.colt.nicity.view.border.SolidBorder;
import com.colt.nicity.view.event.AInputEvent;
import com.colt.nicity.view.event.MouseDragged;
import com.colt.nicity.view.event.MouseEntered;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MouseMoved;
import com.colt.nicity.view.event.MouseReleased;
import com.colt.nicity.view.list.AItem;
import com.colt.nicity.view.list.ListController;
import com.colt.nicity.view.list.VGrid;
import com.colt.nicity.view.list.VItem;
import com.colt.nicity.asynchro.ElapseCall;
import com.colt.nicity.core.process.ICall;
import com.colt.nicity.core.collection.CArray;
import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.lang.MinMaxDouble;
import com.colt.nicity.core.lang.MinMaxLong;
import com.colt.nicity.core.lang.NullOut;
import com.colt.nicity.core.lang.UDouble;
import com.colt.nicity.core.lang.URandom;
import com.colt.nicity.core.memory.struct.IXYZ;
import com.colt.nicity.core.memory.struct.XYZ_D;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.AFont;
import com.colt.nicity.view.core.PaintCompression;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.VPan;
import com.colt.nicity.view.core.VString;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IDropMode;
import com.colt.nicity.view.interfaces.IEnteredOrExited;
import com.colt.nicity.view.interfaces.IEvent;
import com.colt.nicity.view.interfaces.IMouseEvents;
import com.colt.nicity.view.interfaces.IMouseMotionEvents;
import com.colt.nicity.view.interfaces.IToolTip;
import com.colt.nicity.view.interfaces.IVItem;
import com.colt.nicity.view.interfaces.IView;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import linloglayout.LinLogLayout;
import linloglayout.LinLogProgress;

/**
 *
 * @author Administrator
 */
public class NGEnv extends AItem {

    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        ViewColor.onBlack();
        NG ng = new NG();
        for (int i = 0; i < 40; i++) {
            ng.order(URandom.randomLowerCaseAlphaString(1), URandom.randomLowerCaseAlphaString(1), ULinkDrawer.inout("", AColor.blue));
        }
        NGEnv g = new NGEnv(800, 600);
        g.set(NullOut.cNull, ng);
        UV.exitFrame(new Viewer(g), "");
    }

    Object[] links = new Object[0];
    long maxNodeCount = 0;
    double minNode = 0;
    double maxNode = 1;
    long maxLinkCount = 0;
    double minLink = 0;
    double maxLink = 1;
    double minTime = 0;
    double maxTime = 1;
    CSet hashNodes = new CSet();
    CSet selected = new CSet();
    CArray orderedSelection = new CArray(IVNode.class);
    /**
     *
     */
    public VGrid grid;
    CSet nodes = new CSet();
    /**
     *
     */
    public Value attraction = new Value(1d);
    /**
     *
     */
    public Value repulsion = new Value(-2.5d);
    /**
     *
     */
    public Value iterations = new Value(new Integer(100));
    PaintCompression compression;
    ElapseCall refresher;

    /**
     *
     * @param _w
     * @param _h
     */
    public NGEnv(int _w, int _h) {

        compression = new PaintCompression();

        ListController controller = new ListController(nodes);
        grid = new VGrid(controller, -1) {

            public void mouseReleased(final MouseReleased _e) {
                super.mouseReleased(_e);
                if (_e.isRightClick()) {
                    UV.popup(this, _e, UV.zone(NGEnv.this.popupView()), true);
                }

            }

            public void paintBackground(ICanvas _g, int _x, int _y, int _w, int _h) {
                super.paintBackground(_g, _x, _y, _w, _h);




                NGEnv.this.paintBackground(_g, _x, _y, _w, _h);


                _g.setColor(ViewColor.cVisualizeTheme);
                if (threeD) {
                    tunnel.drawTunnel(_g);
                }


                _g.setAlpha(0.1f, 0);
                for (IVItem i : getItems()) {
                    _g.setColor(ViewColor.cVisualizeThemeFont);
                    int cx = (int) (i.getX() + (i.getW() / 2));
                    int cy = (int) (i.getY() + (i.getH() / 2));
                    int max = (int) Math.max(i.getW(), i.getH());
                    int steps = max / 3;
                    for (int m = 0; m < steps; m++) {
                        _g.oval(true, _x + cx - (max / 2), _y + cy - (max / 2), max, max);
                        max -= steps;
                        steps += steps;
                        if (max / 2 < 1) {
                            break;
                        }
                    }
                }
                _g.setAlpha(1f, 0);

                for (int i = 0; i < links.length; i++) {
                    //if (refreshing)
                    // {
                    //    break;
                    //}
                    if (links[i] == null) {
                        continue;
                    }
                    NGLink link = (NGLink) links[i];
                    IView from = (IView) nodes.get(link.key(0));
                    IView to = (IView) nodes.get(link.key(1));
                    if (from == null) {
                        continue;
                    }
                    if (to == null) {
                        continue;
                    }
                    double rank = (double) link.count / (double) maxLinkCount;

                    XY_I _fp = UV.toPoint(from, UV.cCC);
                    XY_I _tp = UV.toPoint(to, UV.cCC);

                    XY_I fp = UV.toPerimeterPoint(from, _fp, _tp);
                    XY_I tp = UV.toPerimeterPoint(to, _tp, _fp);

                    LinkDrawer linkDrawer = (LinkDrawer) link.key(2);
                    linkDrawer.draw(_g, fp, tp, rank, 8);
                }

                //PaintCompression.paintBackground(compression, _g, 0, 0, _w, _h, 0, 16);
                //PaintCompression.paintBorder(compression, _g, 0, 0, _w, _h, 0, 16, true);

            }
        };
        grid.set3D(true, Math.min(_w, _h), _w, _h);
        grid.setPerspective(true);

        grid.spans(UV.cXNSEW);
        VPan pan = new VPan(grid, _w, _h) {

            public void paintBackground(ICanvas _g, int _x, int _y, int _w, int _h) {
                if (grid.is3D()) {
                    grid.set3D(true, Math.min(_w, _h), _w, _h);
                }
                super.paintBackground(_g, _x, _y, _w, _h);
            }

            // IDrop
            public IDropMode accepts(Object value, AInputEvent _e) {
                return NGEnv.this.accepts(value, _e);
            }

            public void dropParcel(final Object value, final IDropMode mode) {
                NGEnv.this.dropParcel(value, mode);
            }
        };
        pan.setBorder(new SolidBorder(ViewColor.cVisualizeTheme));
        setContent(pan);
        setBorder(new SolidBorder(ViewColor.cVisualizeTheme));

        refresher = new ElapseCall(NullOut.cNull, 500, new ICall() {

            public void invoke(IOut _) {
                _refresh(_);
            }
        });
    }

    /**
     *
     * @param _
     * @param _dos
     */
    public void set(IOut _, NG _dos) {
        set(_, _dos.nodes.getAll(Object.class), _dos.maxNodeCount, _dos.links.getAll(Object.class), _dos.maxLinkCount);
    }

    // _nodes instanceof IVNode
    // _links instanceof DoLink
    /**
     *
     * @param _
     * @param _nodes
     * @param _maxNodeCount
     * @param _links
     * @param _maxLinkCount
     */
    public void set(IOut _, Object[] _nodes, long _maxNodeCount, Object[] _links, long _maxLinkCount) {
        maxNodeCount = _maxNodeCount;
        maxLinkCount = _maxLinkCount;
        setNodes(_nodes);
        setLinks(_links);
        refresher.signal();
    }

    /**
     *
     */
    public void deselectAll() {
        orderedSelection.removeAll();
        selected.removeAll();
    }

    /**
     *
     * @param _location
     */
    public void setPicked(IVNode _location) {
        if (selected.get(_location) == null) {
            orderedSelection.insertLast(_location);
            selected.add(_location);
        } else {
            selected.xor(_location);
            orderedSelection.removeAt(_location);
        }
    }

    /**
     *
     * @param _location
     */
    public void setSelected(IVNode _location) {
    }

    /**
     *
     * @param _nodes
     */
    public void setNodes(Object[] _nodes) {
        hashNodes.removeAll();
        hashNodes.add(_nodes);
    }

    /**
     *
     * @param _links
     */
    public void setLinks(Object[] _links) {
        links = _links;
    }

    /**
     *
     */
    public void clearNodes() {
        hashNodes.removeAll();
    }

    /**
     *
     */
    public void twoD() {
        manualLayout = false;
        processLayout = false;
        verticalProcess = false;
        threeD = false;
        grid.set3D(true);
        grid.setPerspective(0.9d);
        grid.layoutInterior();
        refresher.signal();
    }

    /**
     *
     */
    public void threeD() {
        manualLayout = false;
        processLayout = false;
        verticalProcess = false;
        threeD = true;
        grid.setPerspective(true);
        grid.set3D(true);
        grid.layoutInterior();
        refresher.signal();
    }

    /**
     *
     */
    public void manualD() {
        manualLayout = true;
        processLayout = false;
        verticalProcess = false;
        threeD = false;
        grid.setPerspective(true);
        grid.set3D(true);
        grid.layoutInterior();
        refresher.signal();
    }

    /**
     * 
     */
    public void horizontalProcess() {
        manualLayout = false;
        threeD = false;
        processLayout = true;
        verticalProcess = false;
        grid.setRadial(false);
        grid.set3D(true);
        grid.layoutInterior();
        refresher.signal();
    }

    /**
     *
     */
    public void verticalProcess() {
        manualLayout = false;
        threeD = false;
        processLayout = false;
        verticalProcess = true;
        grid.setRadial(false);
        grid.set3D(true);
        grid.layoutInterior();
        refresher.signal();
    }
    boolean threeD = true;
    boolean manualLayout = false;
    boolean processLayout = false;
    boolean verticalProcess = false;

    /**
     *
     * @return
     */
    public IView popupView() {
        VChain c = new VChain(UV.cSWNW);

        VItem b = new VItem("2D") {

            public void picked(IEvent _e) {
                getRootView().dispose();
                twoD();
            }
        };
        c.add(b);

        b = new VItem("Manual") {

            public void picked(IEvent _e) {
                getRootView().dispose();
                manualD();
            }
        };
        c.add(b);

        b = new VItem("3D") {

            public void picked(IEvent _e) {
                getRootView().dispose();
                threeD();
            }
        };
        c.add(b);


        c.add(new VEditValue("Attraction:", attraction, "", false));
        c.add(new VEditValue("Repulsion:", repulsion, "", false));
        c.add(new VEditValue("Iterations:", iterations, "", false));


        b = new VItem("Horizotal Process") {

            public void picked(IEvent _e) {
                getRootView().dispose();
                horizontalProcess();
            }
        };
        c.add(b);

        b = new VItem("Vertical Process") {

            public void picked(IEvent _e) {
                getRootView().dispose();
                verticalProcess();
            }
        };
        c.add(b);

        b = new VItem("List") {

            public void picked(IEvent _e) {
                manualLayout = false;
                grid.setRadial(false);
                grid.set3D(false);
                grid.setRowsColums(-1);
                grid.layoutInterior();
                paint();
                getRootView().dispose();

            }
        };
        c.add(b);

        b = new VItem("Radial") {

            public void picked(IEvent _e) {
                manualLayout = false;
                grid.set3D(false);
                grid.setRadial(true);
                grid.setRowsColums(1);
                grid.layoutInterior();
                paint();
                getRootView().dispose();

            }
        };
        c.add(b);

        b = new VItem("Grid") {

            public void picked(IEvent _e) {
                manualLayout = false;
                grid.setRadial(false);
                grid.set3D(false);
                grid.setRowsColums((int) Math.sqrt(hashNodes.getCount()));
                grid.layoutInterior();
                paint();
                getRootView().dispose();

            }
        };
        c.add(b);

        b = new VItem("Radial Grid") {

            public void picked(IEvent _e) {
                manualLayout = false;
                grid.setRadial(true);
                grid.set3D(false);
                grid.setRowsColums((int) Math.sqrt(hashNodes.getCount()));
                grid.layoutInterior();
                paint();
                getRootView().dispose();
            }
        };
        c.add(b);
        return c;
    }

    /**
     *
     * @param _async
     */
    public void refresh(boolean _async) {
        refresher.signal();
    }

    private void _refresh(final IOut _) {

        CSet toPaint = new CSet();
        if (manualLayout) {
            Object[] all = hashNodes.getAll(Object.class);
            for (int a = 0; a < all.length; a++) {
                IVNode node = (IVNode) all[a];
                DoLocation l = new DoLocation((IVNode) node, 16);
                toPaint.add(l);
            }
        } else if (processLayout || verticalProcess) {
            double factor = Math.sqrt((1d * 1d) / 2d);

            Object[] all = hashNodes.getAll(Object.class);
            CSet done = new CSet();
            CArray bands = new CArray(HashSet.class);
            CSet band = new CSet();
            bands.insertLast(band);
            for (int i = 0; i < all.length; i++) {
                IVNode node = (IVNode) all[i];
                if (node.linkFromCount() == 0) {
                    if (done.get(node) != null) {
                        continue;
                    }
                    done.add(node);
                    band.add(node);
                }
            }
            while (band.getCount() > 0) {
                all = band.getAll(Object.class);
                band = new CSet();
                for (int i = 0; i < all.length; i++) {
                    IVNode node = (IVNode) all[i];
                    Object[] tos = node.linkTos();
                    for (int t = 0; t < tos.length; t++) {
                        IVNode toNode = (IVNode) ((NGLink) tos[t]).key(1);
                        if (done.get(toNode) != null) {
                            continue;
                        }
                        done.add(toNode);
                        band.add(toNode);
                    }
                }
                if (band.getCount() > 0) {
                    bands.insertLast(band);
                }
            }
            CSet lost = new CSet();
            lost.add(hashNodes);
            lost.subtract(done);
            if (lost.getCount() > 0) {
                bands.insertLast(lost);
            }
            done.removeAll();

            MinMaxDouble x = new MinMaxDouble();
            x.value(0);
            x.value(bands.getCount() + 1);
            all = bands.getAll();
            for (int i = 0; i < all.length; i++) {
                band = (CSet) all[i];
                Object[] steps = band.getAll(Object.class);
                MinMaxDouble y = new MinMaxDouble();
                y.value(0);
                y.value(steps.length + 1);

                for (int s = 0; s < steps.length; s++) {
                    IVNode node = (IVNode) steps[s];
                    DoLocation l = new DoLocation((IVNode) node, 16);
                    double rank = (double) (((IVNode) node).getCount()) / (double) maxNodeCount;
                    double percent = UDouble.percent(rank, 0, 1);
                    percent = 1d - UDouble.constrain(percent, 0, 1 - Double.MIN_VALUE);
                    AColor color = AColor.getWarmToCool(percent);
                    l.color = color;
                    toPaint.add(l);

                    if (verticalProcess) {
                        l.x(((-x.zeroToOne(i + 1)) + 0.5d) * 2);
                        l.y((y.zeroToOne(s + 1) - 0.5d) * 2);
                        l.z(0);
                    } else {
                        l.x((x.zeroToOne(i + 1) - 0.5d) * 2);
                        l.y((y.zeroToOne(s + 1) - 0.5d) * 2);
                        l.z(0);
                    }
                }
            }

        } else {

            //Layout

            MinMaxLong mmcount = new MinMaxLong();
            mmcount.value(0);
            for (int i = 0; i < links.length; i++) {
                mmcount.value(((NGLink) links[i]).count);
            }
            Map<Object, Map<Object, Float>> graph = new HashMap<Object, Map<Object, Float>>();
            for (int i = 0; i < links.length; i++) {
                NGLink link = (NGLink) links[i];
                Object source = link.key(0);
                Object target = link.key(1);
                float weight = (float) mmcount.zeroToOne(link.count);
                if (graph.get(source) == null) {
                    graph.put(source, new HashMap<Object, Float>());
                }
                graph.get(source).put(target, weight);
            }

            Map<Object, float[]> nodePosition = new HashMap<Object, float[]>();
            Map<Object, Float> nodeDiameter = new HashMap<Object, Float>();
            LinLogProgress llp = new LinLogProgress() {

                public void out(double _count, double _outof) {
                    _.out(_count, _outof);
                }

                public void out(Object... _value) {
                    _.out(_value);
                }

                public boolean canceled() {
                    return _.canceled();
                }
            };

            LinLogLayout.layout(llp,
                    graph, nodePosition, nodeDiameter,
                    attraction.doubleValue(),
                    repulsion.doubleValue(),
                    iterations.intValue(),
                    threeD);

            MinMaxDouble xp = new MinMaxDouble();
            MinMaxDouble yp = new MinMaxDouble();
            MinMaxDouble zp = new MinMaxDouble();
            MinMaxDouble mmd = new MinMaxDouble();
            for (Object node : nodePosition.keySet()) {
                float[] position = nodePosition.get(node);
                float diameter = nodeDiameter.get(node);
                mmd.value(diameter);
                xp.value(position[0]);
                yp.value(position[1]);
                zp.value(position[2]);
            }

            double factor = Math.sqrt((1d * 1d) / 2d);
            for (Object node : nodePosition.keySet()) {
                float[] position = nodePosition.get(node);
                double cx = xp.zeroToOne(position[0]) - 0.5d;
                double cy = yp.zeroToOne(position[1]) - 0.5d;
                double cz = zp.zeroToOne(position[2]) - 0.5d;
                float diameter = nodeDiameter.get(node);
                diameter = (float) mmd.zeroToOne(diameter);


                int fontSize = 14;//+(int)(diameter*16);
                DoLocation l = new DoLocation((IVNode) node, fontSize);
                double rank = (double) (((IVNode) node).getCount()) / (double) maxNodeCount;
                double percent = UDouble.percent(rank, 0, 1);
                percent = 1d - UDouble.constrain(percent, 0, 1 - Double.MIN_VALUE);
                AColor color = AColor.getWarmToCool(percent);
                l.color = color;
                toPaint.add(l);

                l.x((cx * 2) * factor);
                l.y((cy * 2) * factor);
                l.z((cz * 2) * factor);

                if (Double.isNaN(l.z())) {
                    l.z(0);
                }
            }
        }

        _.out("");
        nodes.removeAll();
        nodes.add(toPaint.getAll(Object.class));
        grid.listModified(_);
    }

    class DoLocation extends AItem implements IXYZ, Comparable, IMouseEvents, IMouseMotionEvents {

        IVNode key;
        AColor color = null;
        IXYZ xyz;

        DoLocation(IVNode _key, int _fontSize) {
            key = _key;
            if (key.value() instanceof IXYZ) {
                xyz = (IXYZ) key.value();
            } else {
                xyz = new XYZ_D(0, 0, 0);
            }
            VChain c = new VChain(UV.cEW);
            if (key.value() instanceof IView) {
                c.add((IView) key.value());
            } else if (key.value() instanceof IEnteredOrExited) {
                c.add(((IEnteredOrExited) key.value()).exitedView());
            } else {
                VString s = new VString(key, new AFont(AFont.cPlain, _fontSize), ViewColor.cVisualizeThemeFont);
                c.add(s);
            }
            setContent(c);
            setBorder(null);//new ItemBorder(ViewColor.cVisualizeTheme));
        }

        @Override
        public void mouseEntered(MouseEntered e) {
            super.mouseEntered(e);

            if (!UV.isChild(this, (IView) e.getExited())) {
                if (key.value() instanceof IEnteredOrExited) {
                    setView(((IEnteredOrExited) key.value()).enteredView());
                }
            }
        }

        @Override
        public void mouseExited(MouseExited e) {
            super.mouseExited(e);
            if (!UV.isChild(this, (IView) e.getEntered())) {
                if (key.value() instanceof IEnteredOrExited) {
                    setView(((IEnteredOrExited) key.value()).exitedView());
                }
            }
        }

        public IToolTip getToolTip() {
            if (key.value() instanceof IView) {
                return ((IView) key.value()).getToolTip();
            }
            return super.getToolTip();
        }

        public String toString() {
            return key.toString();
        }

        public void picked(IEvent _e) {
            setPicked(key);
        }

        public void selected(IEvent _e) {
            setSelected(key);
        }

        public Object getValue() {
            return key;
        }

        public void mouseReleased(final MouseReleased _e) {
            super.mouseReleased(_e);
            if (_e.isRightClick() && _e.isSingleClick()) {
                UV.popup(this, _e,UV.zone(NGEnv.this.popupView()), true);
            }
        }
        // IXYZ

        public double x() {
            return (compression.xFalloff(((xyz.x() + 1d) / 2d)) - 0.5d) * 2d;
        }

        public double y() {
            return (compression.yFalloff(((xyz.y() + 1d) / 2d)) - 0.5d) * 2d;
        }

        public double z() {
            return xyz.z();
        }

        public void x(double _px) {
            xyz.x(_px);
        }

        public void y(double _py) {
            xyz.y(_py);
        }

        public void z(double _pz) {
            xyz.z(_pz);
        }

        // Comparable
        public int compareTo(Object other) {
            double thisVal = z();
            double otherVal = ((IXYZ) other).z();
            return (otherVal < thisVal ? -1 : (otherVal == thisVal ? 0 : 1));
        }

        // IMouseMotionEvents
        public void mouseMoved(MouseMoved e) {
        }

        public void mouseDragged(MouseDragged e) {
            XY_I at = UV.getLocationInView(this.getParentView(), this);
            at.x += e.getDeltaX();
            at.y += e.getDeltaY();

            setX(at.x);
            setY(at.y);
            paint();
        }
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

