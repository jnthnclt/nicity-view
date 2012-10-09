/*
 * VWait.java.java
 *
 * Created on 03-12-2010 06:42:51 PM
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
package colt.nicity.view.monitor;

import colt.nicity.view.border.BuldgeBorder;
import colt.nicity.view.border.ButtonBorder;
import colt.nicity.view.border.ItemBorder;
import colt.nicity.view.border.MenuItemBorder;
import colt.nicity.view.border.PopupBorder;
import colt.nicity.view.border.RecessBorder;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.event.FocusGained;
import colt.nicity.view.event.FocusLost;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.event.WindowActivated;
import colt.nicity.view.event.WindowClosed;
import colt.nicity.view.event.WindowDeactivated;
import colt.nicity.view.event.WindowDeiconified;
import colt.nicity.view.event.WindowIconified;
import colt.nicity.view.event.WindowOpened;
import colt.nicity.view.list.AItem;
import colt.nicity.view.list.VItem;
import colt.nicity.view.list.VList;
import colt.nicity.core.collection.CArray;
import colt.nicity.core.collection.CSet;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.lang.URandom;
import colt.nicity.core.lang.UThread;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.core.time.UTime;
import colt.nicity.core.value.LockValue;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AWindow;
import colt.nicity.view.core.NullView;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VFixed;
import colt.nicity.view.core.VPan;
import colt.nicity.view.core.VPopupButton;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IFocusEvents;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IVItem;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IWindowEvents;
import colt.nicity.view.rpp.IRPPViewable;

/**
 *
 * @author Administrator
 */
public class VOut extends Viewer implements IOut, IFocusEvents, IWindowEvents, IMouseEvents, IMouseMotionEvents, IRPPViewable {
    
    public static IView viewable(String[] args) {
        final VOut c = new VOut(300, "Example");
        new Thread() {

            @Override
            public void run() {
                while(!c.canceled()) {
                    c.out(URandom.rand(100), 100);
                    UThread.sleep(1000);
                }
            }
            
        }.start();
        return c;
    }

    private static CSet pulseSet = new CSet();

    static {
        Thread t = new Thread() {

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        if (pulseSet != null) {
                            Object[] all = pulseSet.getAll(Object.class);
                            for (int i = 0; i < all.length; i++) {
                                if (all[i] instanceof VOut) {
                                    ((VOut) all[i]).pulse();
                                }
                            }
                        }
                    } catch (Exception x) {
                        x.printStackTrace();
                    }
                }
            }
        };
        t.setPriority(2);
        t.start();
    }
    private ProgressView percent;
    private XY_I offset;
    AWindow showing;
    VChain buttonChain;
    CArray errors = new CArray();
    VPopupButton errorsPopup;
    CArray logs = new CArray();
    VPopupButton logsPopup;
    Thread waiter;
    boolean stopable = false;
    boolean suspendable = false;
    boolean stopped = false;
    int frame = 0;
    //IView pulseView;
    IView dialog;
    Object title;

    /**
     *
     */
    public VOut() {
        this(640);
    }

    /**
     *
     * @param _w
     */
    public VOut(int _w) {
        this(_w, "Please Wait");
    }

    /**
     *
     * @param _w
     * @param _title
     */
    public VOut(int _w, Object _title) {
        this(_w, _title, null);
    }

    /**
     *
     * @param _title
     */
    public VOut(Object _title) {
        this(640, _title, null);
    }

    /**
     *
     * @param _title
     * @param _menu
     */
    public VOut(Object _title, IView _menu) {
        this(640, _title, _menu);
    }
    long birth;

    /**
     *
     * @param _w
     * @param _title
     * @param _menu
     */
    public VOut(int _w, Object _title, IView _menu) {

        birth = System.currentTimeMillis();
        title = _title;
        dialog = new Viewer();
        VChain chain = new VChain(UV.cSN);


        VChain errorsChain = new VChain(UV.cSWNW);
        VList errorsList = new VList(errors, 1) {

            public IVItem vItem(Object _value) {
                VChain chain = new VChain(UV.cEW);
                chain.add(new VString("Exceptions:"));
                chain.add(new VString(_value));

                VItem item = new VItem(chain, _value) {

                    public void picked(IEvent _e) {
                        new VException((Throwable) value).toFront(this);
                    }
                };
                item.setBorder(new MenuItemBorder());
                item.spans(UV.cXEW);
                return item;
            }
        };
        errorsList.setComparator(null);
        errorsChain.add(new VPan(errorsList, 300, 300));
        errorsChain.setBorder(new PopupBorder());


        errorsPopup = new VPopupButton(
                new RigidBox(0, 0),
                errorsChain);
        errorsPopup.setBorder(new ItemBorder());


        VChain logsChain = new VChain(UV.cSWNW);
        VList logList = new VList(logs, 1) {

            @Override
            public IVItem vItem(Object _value) {
                VItem item = new VItem(new VString(_value), _value) {

                    @Override
                    public void picked(IEvent _e) {
                        new VException((Throwable) value).toFront(this);
                    }
                };
                item.spans(UV.cXEW);
                return item;
            }
        };
        logList.setComparator(null);
        logsChain.add(new VPan(logList, _w, 300));
        logsChain.setBorder(new PopupBorder());


        logsPopup = new VPopupButton(
                new RigidBox(0, 0),
                logsChain);
        logsPopup.setBorder(new ItemBorder());


        focusedProgress = new Progress(_w);
        progresses.insertLast(focusedProgress);

        VChain percentChain = new VChain(UV.cEW);
        percentChain.add(new RigidBox(5, 5));
        VList listProgresses = new VList(progresses, 1);
        listProgresses.setComparator(null);
        percentChain.add(listProgresses);
        percentChain.add(new RigidBox(5, 5));


        buttonChain = new VChain(UV.cEW);
        buttonChain.add(logsPopup);
        buttonChain.add(new RigidBox(5, 5));
        buttonChain.add(errorsPopup);

        chain.add(new RigidBox(5, 5));
        chain.add(buttonChain);
        chain.add(new RigidBox(5, 5));
        chain.add(percentChain);
        if (_menu != null) {
            chain.add(new RigidBox(5, 5));
            chain.add(_menu);
            chain.add(new RigidBox(5, 5));
        }
        chain.add(new RigidBox(5, 5));
        chain.add(dialog);
        chain.add(new RigidBox(5, 5));



        VChain title = new VChain(UV.cEW);
        title.add(new VFixed(new VString(this, ViewColor.cWindowThemeFont.bw()), _w, -1));
        title.spans(UV.cXEW);

        VChain cleds = new VChain(UV.cSN);
        cleds.add(new VList(leds, 1));
        cleds.spans(UV.cXEW);


        VChain main = new VChain(UV.cSN);
        main.add(title);
        main.add(cleds);
        main.add(chain);
        setPlacer(new Placer(main));
        setBorder(new BuldgeBorder());
    }

    @Override
    public String toString() {
        if (title == null) {
            return "Please Wait " + UTime.elapseHMS(System.currentTimeMillis() - birth);
        }
        return title.toString() + " " + UTime.elapseHMS(System.currentTimeMillis() - birth);
    }

    /**
     *
     * @param _view
     * @param _modal
     * @return
     */
    public Object dialog(IView _view, LockValue _modal) {
        if (_view == null) {
            _view = NullView.cNull;
        }
        dialog.setContent(_view);
        dialog.paint();
        paint();

        if (_modal != null) {
            Object v = _modal.getValue();
            dialog.setContent(NullView.cNull);
            dialog.paint();
            paint();
            return v;
        } else {
            return null;
        }
    }
    CSet leds = new CSet();

    /**
     *
     * @param _key
     * @param _value
     * @param _inc
     * @param _rateVolume
     */
    public void pulse(Object _key, long _value, boolean _inc, boolean _rateVolume) {
        VLED led = (VLED) leds.get(_key);
        if (led == null) {
            led = new VLED(_key, AColor.getHashSolid(_key), 640, 32, 64, _rateVolume);
            leds.add(led);
        }
        led.pulse(_value, _inc);
    }

    /**
     *
     */
    public void pulse() {
        long pulseTime = System.currentTimeMillis();
        if (pulseTime > showTime + UTime.ydhms(0, 0, 0, 0, 1)) {
            _show();
        }
    }
    private Object relativeTo = null;
    private boolean hide = false;
    private long showTime;

    /**
     *
     */
    public void show() {
        if (hide) {
            return;
        }
        pulseSet.add(this);
        showTime = System.currentTimeMillis();
    }

    /**
     *
     */
    public void showNow() {
        if (hide) {
            return;
        }
        pulseSet.add(this);
        _show();
    }

    private void _show() {
        if (hide) {
            pulseSet.remove(this);
            return;
        }
        _showWindow();
        if (hide) {
            _hide();
        }
    }

    private void _showWindow() {
        AWindow _showing = showing;
        if (_showing == null) {
            _showing = new AWindow(this);
            _showing.setTitle(this.toString());
            _showing.systemExitOnClose(false);
            if (relativeTo instanceof IView) {
                UV.centerWindowRelativeToView(_showing, (IView) relativeTo);
            } else {
                UV.centerWindow(_showing);
            }
            _showing.show();
            showing = _showing;
        }
    }

    /**
     *
     */
    public void hide() {
        hide = true;
        pulseSet.remove(this);
        if (errors.getCount() > 0 || logs.getCount() > 0) {
            _showWindow();

            VButton closeButton = new VButton(" Close ") {

                public void picked(IEvent _e) {
                    stopped = true;
                    if (hide) {
                        _hide();
                    }
                }
            };
            buttonChain.add(closeButton);
            buttonChain.paint();

        } else {
            _hide();
        }
        stopped = true;
        resume();
    }

    private void _hide() {
        final AWindow _showing = showing;
        if (_showing != null) {
            _showing.dispose();
            showing = null;
            errors.removeAll();
            logs.removeAll();
        }
    }

    /**
     *
     * @param _instance
     */
    public void add(Object _instance) {
        relativeTo = _instance;

    }

    /**
     *
     * @param _instance
     */
    public void remove(Object _instance) {
    }

    // IFocusEvents
    /**
     *
     * @param e
     */
    public void focusGained(FocusGained e) {
    }

    /**
     *
     * @param e
     */
    public void focusLost(FocusLost e) {
    }

    // IWindowEvents
    /**
     *
     * @param _e
     */
    public void windowOpened(WindowOpened _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowClosed(WindowClosed _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowActivated(WindowActivated _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowDeactivated(WindowDeactivated _e) {
        /*
        if (showing != null) {
        if (relativeTo instanceof IView) {
        UAWT.centerWindowRelativeToView(showing,(IView)relativeTo);
        }
        else UAWT.centerWindow(showing);
        showing.toFront();
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.beep();
        }*/
    }

    /**
     *
     * @param _e
     */
    public void windowIconified(WindowIconified _e) {
    }

    /**
     *
     * @param _e
     */
    public void windowDeiconified(WindowDeiconified _e) {
    }

    // IMouseEvents
    /**
     *
     * @param e
     */
    public void mouseEntered(MouseEntered e) {
        grabFocus(e.who());
    }

    /**
     *
     * @param e
     */
    public void mouseExited(MouseExited e) {
    }

    /**
     *
     * @param e
     */
    public void mousePressed(MousePressed e) {
        offset = e.getPoint();
    }

    /**
     *
     * @param e
     */
    public void mouseReleased(MouseReleased e) {
    }

    // IMouseMotionEvents
    /**
     *
     * @param e
     */
    public void mouseMoved(MouseMoved e) {
    }

    /**
     *
     * @param e
     */
    public void mouseDragged(MouseDragged e) {
        if (offset == null) {
            return;
        }
        XY_I p = e.getPoint();
        p.x -= offset.x;
        p.y -= offset.y;


        IView clientView = getRootView();
        if (clientView == null) {
            return;
        }
        XY_I screenPoint = clientView.getLocationOnScreen();
        clientView.setLocation(screenPoint.x + p.x, screenPoint.y + p.y);
    }
    Progress focusedProgress;
    CArray progresses = new CArray();

    class Progress extends AItem {

        ProgressView percent;
        String remainingString = "";
        String toString = "";

        Progress(int _w) {
            percent = new ProgressView(_w, 5);
            percent.setBorder(new RecessBorder());

            Object _remainingString = new Object() {

                public String toString() {
                    return remainingString;
                }
            };


            VChain c = new VChain(UV.cSWNW);
            c.add(new VString(this, UV.fonts[UV.cSmall]), UV.cFII);
            c.add(percent);
            c.add(new VString(_remainingString, UV.fonts[UV.cSmall]), UV.cFII);
            c.add(new RigidBox(_w, 1));
            setContent(new VFixed(c, _w, -1));
            setBorder(new ViewBorder());
        }

        public String toString() {
            return toString;
        }

        public void progress(double _count, double _outof) {
            if (hide) {
                return;
            }
            double p = (double) _count / (double) _outof;
            progress((float) p, "");
        }
        private long startTime = Long.MIN_VALUE;
        private float lastProgress = Float.MIN_VALUE;

        public void progress(float _progress, String _key) {
            if (hide) {
                return;
            }
            if (lastProgress == Float.MIN_VALUE) {
                lastProgress = _progress;
                startTime = System.currentTimeMillis();
            } else {
                if (_progress < lastProgress) {
                    lastProgress = Float.MIN_VALUE;
                }
            }
            if (lastProgress != Float.MIN_VALUE) {
                long time = System.currentTimeMillis();
                long timeDelta = time - startTime;
                float progressDelta = _progress;
                long remaining = (long) (((1 - progressDelta) * ((double) timeDelta / progressDelta)));
                remainingString = UTime.elapseYMWDHMS(remaining);
                lastProgress = _progress;
            }
            percent.setProgress(_progress);
        }

        public void out(Object _value) {
            if (hide) {
                return;
            }
            toString = (_value == null) ? "" : _value.toString();
            repaint();
        }

        public void out(Object _value, Object _key) {
            if (hide) {
                return;
            }
            toString = _value.toString();
            repaint();
        }
    }

    /**
     *
     */
    public void repaint() {
        paint();
    }

   
    /**
     *
     * @param _count
     * @param _outof
     */
    public void out(double _count, double _outof) {
        if (hide) {
            return;
        }
        if (focusedProgress != null) {
            focusedProgress.progress(_count, _outof);
        }
    }

    /**
     *
     * @param _value
     */
    public void out(Object... _value) {
        for(Object v:_value) {
            if (v instanceof Throwable) exception((Throwable)v);
            else out(v);
        }
    }

    /**
     *
     * @param _value
     */
    public void out(Object _value) {
        if (_value instanceof Throwable) {
            exception((Throwable)_value);
        }
        if (hide) {
            return;
        }
        if (focusedProgress != null) {
            focusedProgress.out(_value);
        }
    }

    /**
     *
     * @param _t
     */
    public void exception(Throwable _t) {
        //if (hide) return;
        if (errors.getCount() == 0) {
            Object errorsString = new Object() {

                public String toString() {
                    return " " + errors.getCount() + " ";
                }
            };
            errorsPopup.setView(
                    new VChain(UV.cEW, new VString("Errors:"), new VString(errorsString)));
            errorsPopup.setBorder(new ButtonBorder(AColor.red));
            if (percent != null) {
                percent.progressColor = AColor.red;
            }
        }
        errors.insertLast(_t);
        _showWindow();
    }

    /**
     *
     */
    public void pushProgress() {
        focusedProgress = new Progress(640);
        progresses.insertLast(focusedProgress);
    }

    /**
     *
     */
    public void popProgress() {
        progresses.removeLast();
        focusedProgress = (Progress) progresses.getLast();
    }

    /**
     *
     * @param _key
     */
    public void finished(String _key) {
    }

    /**
     *
     * @param _key
     */
    public void remove(String _key) {
    }

    


    /**
     *
     * @return
     */
    public boolean canceled() {
        if (!stopable) {
            stopable = true;
            VButton stopButton = new VButton(" Stop ") {

                public void picked(IEvent _e) {
                    if (suspend) {
                        suspend = false;
                        resume();
                    }
                    stopped = true;
                    out(" User requested stop. ");
                    if (hide) {
                        _hide();
                    }
                }
            };
            buttonChain.add(stopButton);
            buttonChain.paint();
        }
        return stopped;
    }

    /**
     *
     */
    public void reset() {
        stopped = false;
        errors.removeAll();
        logs.removeAll();
    }
    boolean suspend = false;
    Object wait = new Object();

    /**
     *
     */
    public void suspend() {
        if (!suspendable) {
            suspendable = true;
            VButton suspendButton = new VButton(" Suspend ") {

                public void picked(IEvent _e) {
                    if (suspend) {
                        out(" User requested Resume. ");
                        suspend = false;
                        resume();
                        setContent(new VString(" Suspend "));
                        paint();
                    } else {
                        out(" User requested Suspend. ");
                        suspend = true;
                        setContent(new VString(" Suspended "));
                        paint();
                    }
                }
            };
            buttonChain.add(suspendButton);
            buttonChain.paint();
        }
        if (!suspend) {
            return;
        }
        synchronized (wait) {
            try {
                wait.wait();
                suspend = false;
            } catch (Exception x) {
            }
        }
    }

    /**
     *
     */
    public void resume() {
        synchronized (wait) {
            wait.notifyAll();
        }
    }
}
