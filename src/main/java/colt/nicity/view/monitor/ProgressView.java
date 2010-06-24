/*
 * ProgressView.java.java
 *
 * Created on 03-12-2010 06:43:02 PM
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

import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.flavor.ButtonFlavor;
import colt.nicity.view.list.AItem;
import colt.nicity.view.list.VMenu;
import colt.nicity.core.collection.CArray;
import colt.nicity.core.value.IValue;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IEvent;

/**
 *
 * @author Administrator
 */
public class ProgressView extends AItem implements IValue {
    
    /**
     *
     */
    protected float progress = 0.0f;
    /**
     *
     */
    protected String progressText = "";
    
    /**
     *
     */
    protected long startTime = 0;
    /**
     *
     */
    protected long elapseTime = 0;
    
    /**
     *
     */
    protected int alive = 0;
    
    CArray messages = new CArray(String.class);
    /**
     *
     */
    protected AFont progressFont = UV.fonts[UV.cSmall];
    /**
     *
     */
    public AColor progressColor = ViewColor.cThemeActive;
    
    /**
     *
     * @param _w
     * @param _h
     */
    public ProgressView(float _w, float _h) {
        progressFont = new AFont(AFont.cPlain, (int) (_h / 1.75f));
        setPlacer(new Placer(new RigidBox(_w, _h)));
        setBorder(new ViewBorder());
    }
    
    /**
     *
     * @return
     */
    public Object getValue() {
        return new Double(progress);
    }
    /**
     *
     * @param _value
     */
    public void setValue(Object _value) {
        setProgress(((Double) _value).floatValue());
    }
    
    /**
     *
     * @param _e
     */
    public void picked(IEvent _e) {
        VMenu messagesPopup = new VMenu(this, messages);
        messagesPopup.showPopup(0, getLocationOnScreen());
    }
    
    private long last = 0;
    
    /**
     *
     */
    public void update() {
        alive++;
        if (alive > getW()) {
            alive = 0;
        }
        super.repair();
        super.flush();
    }
    
    /**
     *
     */
    public void finished() {
        progressText = "";
        startTime = 0;
        progress = 0.0f;
        update();
    }
    
    /**
     *
     * @param _t
     */
    public void exception(Throwable _t) {
    }
    /**
     *
     * @return
     */
    public boolean stop() {
        return false;
    }
    
    /**
     *
     * @param _progress
     * @param _key
     */
    public void progress(float _progress, String _key) {
        setProgress(_progress);
    }
    /**
     *
     * @param _progress
     */
    public void setProgress(float _progress) {
        progress = _progress;
        if (progress > 1) {
            progress = 1;
        } else if (progress < 0) {
            progress = 0;
        }
        
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        } else {
            elapseTime = System.currentTimeMillis() - startTime;
        }
        
        update();
    }
    
    /**
     *
     * @return
     */
    public float getProgress() {
        return progress;
    }
    
    /**
     *
     * @param _out
     * @param _key
     */
    public void out(String _out, String _key) {
        setProgressText(_out);
    }
    /**
     *
     * @param _progressText
     */
    public void setProgressText(String _progressText) {
        if (_progressText == null) {
            _progressText = "null";
        }
        progressText = _progressText;
        messages.insertLast(progressText);
        if (messages.getCount() > 50) {
            messages.removeFirst();
        }
        update();
    }
    
    /**
     *
     * @param _key
     */
    public void remove(String _key) {
    }
    /**
     *
     * @param _key
     */
    public void finished(String _key) {
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
        int extent = (int) (_w * progress);
        g.setColor(progressColor);
        g.rect(true, _x + 0, _y + 2, extent, _h - 4);
        g.setFont(progressFont);
        g.setColor(ViewColor.cThemeFont);
        g.drawString(progressText, _x + 0, _y + _h - 5);
    }
    static ButtonFlavor buttonFlavor = new ButtonFlavor();
}
