/*
 * ADisplay.java.java
 *
 * Created on 01-03-2010 01:31:39 PM
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

import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IView;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
abstract public class ADisplay {
    //overrideables

    /**
     *
     * @param _who
     * @param _w
     * @param _h
     * @return
     */
    abstract public ICanvas display(long _who, float _w, float _h);

    /**
     *
     * @param _g
     * @param _region
     */
    public void displayable(ICanvas _g, XYWH_I _region) {
    }

    /**
     *
     * @param _g
     */
    public void paintWhos(ICanvas _g) {
    }

    /**
     *
     */
    public void toFront() {
    }

    /**
     *
     * @return
     */
    public float ow() {
        return w();
    }

    /**
     *
     * @return
     */
    public float oh() {
        return h();
    }
    /**
     *
     */
    static public ADisplay topDisplay;
    /**
     *
     */
    static public ADisplay activeDisplay;
    /**
     *
     */
    private final Painter painter;
    private Thread painterThread;
    private final ExecutorService executor;
    private final BlockingQueue<IView> blockingQueue;
    private final IView displaying;
    private boolean antialias = true;
    private boolean isAutoFocusable = true;

    /**
     *
     * @param view
     */
    public ADisplay(IView view) {
        displaying = view;
        blockingQueue = new LinkedBlockingQueue<IView>();
        painter = new Painter();
        executor = Executors.newSingleThreadExecutor();
        executor.submit(painter);
    }

    /**
     *
     * @return
     */
    public IView displaying() {
        return displaying;
    }

    /**
     *
     * @return
     */
    public float w() {
        return displaying.getW();
    }

    /**
     *
     * @return
     */
    public float h() {
        return displaying.getH();
    }

    /**
     *
     * @param _boolean
     */
    public void setAutoFocusable(boolean _boolean) {
        isAutoFocusable = _boolean;
    }

    /**
     *
     * @return
     */
    public boolean isAutoFocusable() {
        return isAutoFocusable;
    }

    /**
     *
     * @param _boolean
     */
    public void setAntialiasing(boolean _boolean) {
        antialias = _boolean;
    }

    /**
     *
     * @return
     */
    public boolean isAntialiased() {
        return antialias;
    }

    /**
     *
     * @param _view
     */
    public void addToRepaint(IView _view) {
        if (painterThread == Thread.currentThread()) {
            // prevents painter thread from creating an infinite loop
            return;
        }
        blockingQueue.add(_view);
    }

    /**
     *
     */
    public void repaint() {
        addToRepaint(displaying);
    }

    /**
     *
     */
    public void dispose() {
        painter.stop = true;
        blockingQueue.clear();
    }

    class Painter implements Runnable {

        boolean stop = false;

        @Override
        public void run() {
            
            painterThread = Thread.currentThread();
            while (!stop) {
                LinkedList<IView> list = new LinkedList<IView>();
                try {
                    IView take = blockingQueue.take();
                    list.add(take);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ADisplay.class.getName()).log(Level.SEVERE, null, ex);
                }
                blockingQueue.drainTo(list);
                for (IView l : list) {
                    if (l.hasFlag(UV.cMend)) {
                        continue;//??
                    }
                    l.layoutInterior();
                    l.enableFlag(UV.cRepair);
                    l.mend();
                }
                try {
                    float w = displaying.getW();
                    float h = displaying.getH();
                    if (w == 0 && h == 0) {
                        displaying.mend();
                        repaint();
                        continue;
                    }
                    ICanvas g = display(0, w, h);//who
                    if (g == null) {
                        displaying.mend();
                        repaint();
                        continue;
                    }
                    XYWH_I region = new XYWH_I(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
                    Layer layer = new Layer(null, 0f, 0f, 0f, 0f, w, h);
                    displaying.paint(displaying, g, layer, UV.cMend, region);
                    paintWhos(g);
                    displayable(g, region);
                    
                    float nw = displaying.getW();
                    float nh = displaying.getH();
                    if (nw == w && nh == h) {
                        displaying.mend();
                        repaint();
                        continue;
                    }

                } catch (Exception x) {
                    x.printStackTrace();
                }

            }
        }
    }
}
