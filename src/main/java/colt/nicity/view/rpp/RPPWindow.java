package colt.nicity.view.rpp;

import colt.nicity.core.lang.IOut;
import colt.nicity.core.memory.struct.UXYWH_I;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.awt.NullPeerView;
import colt.nicity.view.core.ADisplay;
import colt.nicity.view.core.AInput;
import colt.nicity.view.core.AViewer;
import colt.nicity.view.core.NullPlacer;
import colt.nicity.view.core.NullRootView;
import colt.nicity.view.core.PrimativeEvent;
import colt.nicity.view.core.VPopupViewer;
import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IPeerView;
import colt.nicity.view.interfaces.IRootView;
import colt.nicity.view.interfaces.IView;
import java.awt.image.BufferedImage;

/**
 * 
 * @author jonathan
 */
public class RPPWindow extends AViewer implements IRootView {

    /**
     * 
     */
    public RPPDisplay display;
    /**
     * 
     */
    public AInput input;

    /**
     * 
     * @param _view
     */
    public RPPWindow(IView _view) {
        super(new VPopupViewer(_view));
        init();
        show();
    }

    /**
     * 
     */
    protected void init() {
        display = new RPPDisplay(this) {

            @Override
            public void paintWhos(ICanvas _g) {
                AInput _input = input;
                if (_input != null) {
                    _input.paintWhos(_g);
                }
            }
        };
        input = new AInput(display) {

            @Override
            public String toString() {
                return "Portal Input Handler " + super.toString();
            }
        };
        super.setParentView(NullRootView.cNull);
    }

    /**
     * 
     * @return
     */
    public IPeerView getPeer() {
        setBorder(null);
        return NullPeerView.cNull;
    }

    @Override
    public void setBorder(IBorder border) {
        super.setBorder(null);
    }

    @Override
    public void setParentView(IView parent) {
    }

    /**
     * 
     * @param _boolean
     */
    public void systemExitOnClose(boolean _boolean) {
    }

    /**
     * 
     * @return
     */
    public boolean systemExitOnClose() {
        return false;
    }

    @Override
    public IPeerView getPeerView() {
        return getPeer();
    }
    private boolean paintable = false;
    private boolean layedOut = false;

    /**
     * 
     */
    public void show() {
        super.setParentView(NullRootView.cNull);
        layoutInterior();
        paintable = true;
        flush();
    }

    @Override
    public void layoutInterior() {
        super.layoutInterior();
        layedOut = true;
        flush();
    }

    /**
     * 
     */
    public void iconify() {
    }

    /**
     * 
     */
    public void deiconify() {
    }

    /**
     * 
     */
    public void maximize() {
    }

    @Override
    public void toFront() {
    }

    /**
     * 
     */
    public void toBack() {
    }

    /**
     * 
     * @return
     */
    public boolean closed() {
        return false;
    }

    @Override
    public boolean isValid() {
        return !requestedDispose;
    }
    private boolean requestedDispose = false;

    @Override
    public void dispose() {
        ADisplay _display = display;
        if (_display != null) {
            _display.dispose();
        }
        display = null;
        AInput _input = input;
        if (_input != null) {
            _input.dispose();
        }
        input = null;
        if (ADisplay.topDisplay == display) {
            ADisplay.topDisplay = null;
        }
        paintable = false;
        placer = NullPlacer.cNull;
        super.setParentView(null);

        IView content = getContent();
        if (content != null) {
            content.setParentView(null);
        }
    }

    //----------------------------------------------------------------------------
    // This is intentionally not synchronized
    //----------------------------------------------------------------------------
    @Override
    public void addToRepaint(IView _view) {
        ADisplay _display = display;
        if (_display != null) {
            _display.addToRepaint(_view);
        }
    }

    @Override
    public void flush() {
        ADisplay _display = display;
        if (_display != null) {
            _display.repaint();
        }
    }

    /**
     * 
     */
    public void fullscreen() {
        getPeer().fullscreen();
    }

    //----------------------------------------------------------------------------
    // IView overloading
    //----------------------------------------------------------------------------
    @Override
    public boolean isVisible(int _x, int _y, int _w, int _h) {
        XYWH_I r = UXYWH_I.intersection(_x, _y, _w, _h, 0, 0, (int) getW(), (int) getH());
        if (r == null) {
            return false;
        }
        return true;
    }

    @Override
    public IRootView getRootView() {
        return this;
    }

    @Override
    public void enableEvents(long tasksToEnable) {
        super.enableEvents(tasksToEnable);
        getPeer().enablePeerEvents(tasksToEnable);
    }

    @Override
    public void disableEvents(long tasksToDisable) {
        super.disableEvents(tasksToDisable);
        getPeer().disablePeerEvents(tasksToDisable);
    }

    @Override
    public void setLocation(float x, float y) {
        getPeer().setCorner((int) x, (int) y);
        super.setLocation(0, 0);
    }

    @Override
    public XY_I getLocationOnScreen() {
        return getPeer().getCorner();
    }

    @Override
    public XY_I getLocationInWindow() {
        return new XY_I(0, 0);
    }

    /**
     * 
     * @return
     */
    public String getTitle() {
        return getPeer().getTitle();
    }

    /**
     * 
     * @param _title
     */
    public void setTitle(String _title) {
        getPeer().setTitle(_title);
    }

    @Override
    public IView getFocusedView(long _who) {
        AInput _input = input;
        if (_input != null) return _input.getFocusedView(_who);
        else return null;
    }

    @Override
    public void setFocusedView(long _who, IView view) {
        AInput _input = input;
        if (_input != null) {
            _input.setFocusedView(_who, view);
        }
    }

    @Override
    public IView getHardFocusedView(long _who) {
        AInput _input = input;
        if (_input != null) return _input.getHardFocusedView(_who);
        else return null;
    }

    @Override
    public void setHardFocusedView(long _who, IView view) {
        AInput _input = input;
        if (_input != null) {
            _input.setHardFocusedView(_who, view);
        }
    }

    @Override
    public void setMouseWheelFocus(long _who, IView _mouseWheelFocus) {
        AInput _input = input;
        if (_input != null) {
            _input.setMouseWheelFocus(_who, _mouseWheelFocus);
        }
    }

    /**
     * 
     * @param _buffer
     * @param _bufferRegion
     * @param _screenRegion
     */
    public void directPaint(BufferedImage _buffer, XYWH_I _bufferRegion, XYWH_I _screenRegion) {
    }

    @Override
    public void processEvent(IOut _, PrimativeEvent event) {
        AInput _input = input;
        if (_input != null) {
            _input.handleEvent(_, event);
        }
    }
}
