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

import colt.nicity.core.collection.CArray;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IView;

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
    protected volatile CArray toRepaint = new CArray();
    final private Object painterLock = new Object();
    private Painter painter;
    private IView displaying;
    private boolean antialias = true;
    private boolean isAutoFocusable = true;

    /**
     *
     * @param _displaying
     */
    public ADisplay(IView _displaying) {
        displaying = _displaying;
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
        synchronized (painterLock) {
            if (painter == null) {
                painter = new Painter();
            }
        }
        toRepaint.insertLast(_view);
    }

    /**
     *
     */
    public void repaint() {
        if (painter == null) {
            return;
        }
        if (toRepaint.getCount() == 0) {
            return;
        }
        synchronized (painterLock) {
            painterLock.notifyAll();
        }
    }

    /**
     *
     */
    public void dispose() {
        if (painter != null) {
            painter.stop = true;
        }
        synchronized (painterLock) {
            painterLock.notifyAll();
        }
    }

    class Painter extends Thread {

        boolean stop = false;

        Painter() {
            start();
        }

        public void run() {
            while (!stop) {
                if (toRepaint.getCount() == 0) {
                    synchronized (painterLock) {
                        try {
                            painterLock.wait();
                        } catch (Exception x) {
                        }
                    }
                } else {
                    Object[] repaint = toRepaint.removeAll();
                    //Ensures mend paths are enables
                    for (int i = 0; i < repaint.length; i++) {
                        IView repair = (IView) repaint[i];
                        if (repair == null) {
                            continue;
                        }
                        repair.enableFlag(UV.cRepair);
                        repair.mend();
                    }

                    try {
                        float w = displaying.getW();
                        float h = displaying.getH();
                        ICanvas g = display(0, w, h);//who
                        if (g != null) {
                            XYWH_I _region = new XYWH_I(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
                            Layer layer = new Layer(null, 0f, 0f, 0f, 0f, w, h);
                            displaying.paint(displaying, g, layer, UV.cMend, _region);
                            paintWhos(g);
                            displayable(g, _region);
                        }

                    } catch (Exception x) {
                        x.printStackTrace();
                    }
                }
            }
        }
    }
}
