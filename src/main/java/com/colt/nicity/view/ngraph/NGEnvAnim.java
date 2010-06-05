/*
 * NGEnvAnim.java.java
 *
 * Created on 03-12-2010 06:42:30 PM
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

import com.colt.nicity.view.border.SolidBorder;
import com.colt.nicity.view.event.MouseDragged;
import com.colt.nicity.view.event.MouseEntered;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MouseMoved;
import com.colt.nicity.view.event.MouseReleased;
import com.colt.nicity.view.list.AItem;
import com.colt.nicity.view.list.ListController;
import com.colt.nicity.view.list.VItem;
import com.colt.nicity.view.list.VLoose;
import com.colt.nicity.view.value.VEditValue;
import com.colt.nicity.asynchro.ElapseCall;
import com.colt.nicity.core.process.ICall;
import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.collection.keyed.KeyedValue;
import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.lang.MinMaxDouble;
import com.colt.nicity.core.lang.MinMaxLong;
import com.colt.nicity.core.lang.NullOut;
import com.colt.nicity.core.lang.UFloat;
import com.colt.nicity.core.lang.URandom;
import com.colt.nicity.core.memory.struct.IXYZ;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.core.value.Value;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.AFont;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.VPan;
import com.colt.nicity.view.core.VStatus;
import com.colt.nicity.view.core.VString;
import com.colt.nicity.view.core.ViewColor;
import com.colt.nicity.view.core.Viewer;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IEnteredOrExited;
import com.colt.nicity.view.interfaces.IEvent;
import com.colt.nicity.view.interfaces.IMouseEvents;
import com.colt.nicity.view.interfaces.IMouseMotionEvents;
import com.colt.nicity.view.interfaces.IToolTip;
import com.colt.nicity.view.interfaces.IVItem;
import com.colt.nicity.view.interfaces.IView;
import java.util.HashMap;
import java.util.Map;
import linloglayout.LinLogLayout;
import linloglayout.LinLogProgress;
import linloglayout.MinimizerBarnesHut;

/**
 *
 * @author Administrator
 */
public class NGEnvAnim extends Viewer {

    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        ViewColor.onBlack();
        NG ng = new NG();
        for(int i=0;i<40;i++) {
            ng.order(URandom.randomLowerCaseAlphaString(1), URandom.randomLowerCaseAlphaString(1),ULinkDrawer.inout("",AColor.blue));
        }
       NGEnvAnim g = new NGEnvAnim(800,600);
       g.set(NullOut.cNull, ng);
        UV.exitFrame(new Viewer(g), "");
    }

    
    /**
     *
     * @param _location
     */
    public void setPicked(IVNode _location) {
    }
    
    /**
     *
     * @param _location
     */
    public void setSelected(IVNode _location) {
    }
   
    long maxNodeCount = 0;
    double minNode = 0;
    double maxNode = 1;
    
    long maxLinkCount = 0;
    double minLink = 0;
    double maxLink = 1;
    
    CSet hashNodes = new CSet();
    
    CSet nodes = new CSet();
    Object[] links = new Object[0];
    /**
     *
     */
    public VLoose loose;
    
    /**
     *
     */
    public boolean inoutLinks = false;
    /**
     *
     */
    public Value<Double> attraction = new Value<Double>(1d);
    /**
     *
     */
    public Value<Double> repulsion = new Value<Double>(-2.5d);
    /**
     *
     */
    public Value<Double> gravityFactor = new Value<Double>(0.01d);
    /**
     *
     */
    public Value<Integer> iterations = new Value<Integer>(new Integer(60));
    ElapseCall refresher;
    int cw = 800;
    int ch = 800;
    VPan panGraph;
    
    VStatus status;
    CSet pinned = new CSet();
    
    /**
     *
     * @param _w
     * @param _h
     */
    public NGEnvAnim(int _w, int _h) {
        cw = _w;
        ch = _h;

        loose = new VLoose(new ListController(nodes)) {
            @Override
            public void mend() {
                enableFlag(UV.cRepair);//??
                super.mend();
            }
            
            @Override
            public void mouseReleased(final MouseReleased _e) {
                super.mouseReleased(_e);
                if (_e.isRightClick()) {
                    UV.popup(this, _e, NGEnvAnim.this.popupView(), true);
                }
            }
            
            @Override
            public void paintBackground(ICanvas _g,int _x,int _y, int _w, int _h) {
                super.paintBackground(_g,_x,_y, _w, _h);
                //_g.setColor(ViewColor.cVisualizeTheme);

                
                _g.setAlpha(0.1f, 0);
                for (IVItem i : getItems()) {
                    _g.setColor(ViewColor.cVisualizeThemeFont);
                    int cx = (int) (i.getX() + (i.getW() / 2));
                    int cy = (int) (i.getY() + (i.getH() / 2));
                    int max = (int) Math.max(i.getW(), i.getH());
                    int steps = max / 3;
                    for (int m = 0; m < steps; m++) {
                        _g.oval(true,cx - (max / 2), cy - (max / 2), max, max);
                        max -= steps;
                        steps += steps;
                        if (max / 2 < 1) {
                            break;
                        }
                    }
                }
                _g.setAlpha(1f, 0);

                for (int i = 0; i < links.length; i++) {
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

                    if (inoutLinks) {
                        XY_I fp = UV.toPoint(from, UV.cEE);
                        XY_I tp = UV.toPoint(to, UV.cWW);
                        LinkDrawer linkDrawer = (LinkDrawer) link.key(2);
                        linkDrawer.draw(_g, fp, tp, rank, 8);
                    } else {
                        XY_I _fp = UV.toPoint(from, UV.cCC);
                        XY_I _tp = UV.toPoint(to, UV.cCC);

                        XY_I fp = UV.toPerimeterPoint(from, _fp, _tp);
                        XY_I tp = UV.toPerimeterPoint(to, _tp, _fp);

                        LinkDrawer linkDrawer = (LinkDrawer) link.key(2);
                        linkDrawer.draw(_g, fp, tp, rank, 8);
                    }
                }

            }
        };
        loose.spans(UV.cXNSEW);
        loose.setBorder(new SolidBorder(ViewColor.cVisualizeTheme));

        panGraph = new VPan(loose, _w, _h) {
            @Override
            public void paintBackground(ICanvas _g,int _x,int _y, int _w, int _h) {
                super.paintBackground(_g,_x,_y, _w, _h);
                if (cw != _w || ch != _h) {
                    cw = _w;
                    ch = _h;
                    refresher.signal();
                }
            }
        };
        status = new VStatus(panGraph);
        setContent(status);
        setBorder(new SolidBorder(ViewColor.cVisualizeTheme));

        refresher = new ElapseCall(NullOut.cNull,500,new ICall() {
            public void invoke(IOut _) {
                _refresh(_);
            }
        });
    }

    /**
     *
     * @param _w
     * @param _h
     */
    public  void setSize(int _w,int _h) {
        panGraph.setSize(_w, _h);
        panGraph.layoutInterior();
    }

    private boolean editable = true;
    /**
     *
     * @param b
     */
    public void setEditable(boolean b) {
        editable = b;
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
    Map<Object, Map<Object, Float>> graph;
    Map<Object, Integer> nodeToId;
    float[][] positions;
    int[][] attractionIndexes;
    float[][] attrationWeights;
    float[] repulsionWeights;
    MinMaxDouble xp = new MinMaxDouble();
    MinMaxDouble yp = new MinMaxDouble();
    MinMaxDouble zp = new MinMaxDouble();
    MinimizerBarnesHut minimizer;
    boolean[] pinnedPositions;

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


        MinMaxLong mmcount = new MinMaxLong();
        mmcount.value(0);
        for (int i = 0; i < links.length; i++) {
            mmcount.value(((NGLink) links[i]).count);
        }
        graph = new HashMap<Object, Map<Object, Float>>();
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

        graph = LinLogLayout.makeSymmetricGraph(graph);
        nodeToId = LinLogLayout.makeIds(graph);
        positions = LinLogLayout.makeInitialPositions(graph, false);
        pinnedPositions = new boolean[positions.length];

        attractionIndexes = LinLogLayout.makeAttrIndexes(graph, nodeToId);
        attrationWeights = LinLogLayout.makeAttrWeights(graph, nodeToId);
        repulsionWeights = LinLogLayout.makeRepuWeights(graph, nodeToId);

        CSet toPaint = new CSet();
        for (Object node : nodeToId.keySet()) {
            int pi = nodeToId.get(node);
            toPaint.add(new DoLocation((IVNode) node, pi));
            float[] xyz = (float[])KeyedValue.get(pinned, node);
            if (xyz != null) {
                positions[pi][0] = xyz[0];
                positions[pi][1] = xyz[1];
                positions[pi][2] = xyz[2];
                pinnedPositions[pi] = true;
            }
        }

        minimizer = new MinimizerBarnesHut(
                attractionIndexes, attrationWeights, repulsionWeights,
                (float) attraction.doubleValue(), (float) repulsion.doubleValue(), 0.01f, positions, pinnedPositions);

        nodes.removeAll();
        nodes.add(toPaint.getAll(Object.class));
        loose.listModified(_);
        _refresh(_);
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
     * @return
     */
    public IView popupView() {
        VChain c = new VChain(UV.cSWNW);
        c.add(new VItem("Unpin all") { public void picked(IEvent _e) {
            pinned.removeAll();
            for(int i=0;i<pinnedPositions.length;i++) {
                getRootView().dispose();
                pinnedPositions[i] = false;
                refresh();
            }
        }});
        c.add(new VEditValue("Attraction:", attraction, "", false));
        c.add(new VEditValue("Repulsion:", repulsion, "", false));
        c.add(new VEditValue("Iterations:", iterations, "", false));
        VItem b = new VItem("Refresh") {
            public void picked(IEvent _e) {
                getRootView().dispose();
                refresh();
            }
        };
        c.add(b);
        return c;
    }
    
    /**
     *
     */
    public void refresh() {
        refresher.signal();
    }
    
    private void _refresh(final IOut _) {

        
        LinLogProgress llp = new LinLogProgress() {
            public void out(double _count, double _outof) {
                _.out(_count, _outof);
                //status.out((_count / _outof) + "%");

                xp.reset();
                yp.reset();
                zp.reset();
                for (int i = 0; i < positions.length; i++) {
                    float[] position = positions[i];
                    xp.value(position[0]);
                    yp.value(position[1]);
                    zp.value(position[2]);
                }

                loose.listModified(_);
            }
            public void out(Object... _value) {
                _.out(_value);
            }
            public boolean canceled() {
                return _.canceled();
            }
        };

        minimizer.attrExponent((float) attraction.doubleValue());
        minimizer.repuExponent((float) repulsion.doubleValue());
        minimizer.minimizeEnergy(llp, iterations.intValue());

        status.out("");
    }
    

    class DoLocation extends AItem implements IXYZ, Comparable, IMouseEvents, IMouseMotionEvents {
        IVNode key;
        
        AColor color = null;
        int pi;
        
        DoLocation(IVNode _key, int _pi) {
            key = _key;
            pi = _pi;
            VChain c = new VChain(UV.cEW);
            if (key.value() instanceof IView) {
                c.add((IView) key.value());
            } else if (key.value() instanceof IEnteredOrExited) {
                c.add(((IEnteredOrExited) key.value()).exitedView());
            } else {
                VString s = new VString(key, new AFont(AFont.cPlain, 14), ViewColor.cVisualizeThemeFont);
                c.add(s);
            }
            setContent(c);
            setBorder(null);
        }
        public float[] xyz() {
            return positions[pi];
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
        @Override
        public void paintBorder(ICanvas g,int _x,int _y, int _w, int _h) {
            super.paintBorder(g,_x,_y, _w, _h);
            if (pinnedPositions[pi]) {
                g.setColor(AColor.red);
                g.oval(false,_x,_y,6,6);
            }
        }


        
        public float getX() {
            return UFloat.clamp((float) (cw * x()) - (getW() / 2), 16, cw - getW() - 16);
        }
        
        public float getY() {
            return UFloat.clamp((float) (ch * y()) - (getH() / 2), 16, ch - getH() - 16);
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
            if (editable) {
                pinnedPositions[pi] = true;
                KeyedValue.add(pinned, xyz(),key);
                refresh();
            }
        }
        
        // IXYZ
        public double x() {
            return xp.zeroToOne(xyz()[0]);
        }
        public double y() {
            return yp.zeroToOne(xyz()[1]);
        }
        public double z() {
            return zp.zeroToOne(xyz()[2]);
        }
        public void x(double _px) {
            xyz()[0] = (float) xp.unzeroToOne(_px);
        }
        public void y(double _py) {
            xyz()[1] = (float) yp.unzeroToOne(_py);
        }
        public void z(double _pz) {
            xyz()[2] = (float) zp.unzeroToOne(_pz);
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
            int dx = e.getDeltaX();
            int dy = e.getDeltaY();

            double xp = (double) dx / (double) cw;
            double yp = (double) dy / (double) ch;

            x(x() + xp);
            y(y() + yp);
            paint();


        }
    }
}
