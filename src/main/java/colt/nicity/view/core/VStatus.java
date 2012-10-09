/*
 * VStatus.java.java
 *
 * Created on 03-12-2010 06:34:33 PM
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

import colt.nicity.view.paint.UPaint;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.value.Value;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IView;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Administrator
 */
public class VStatus extends Viewer implements IOut {
    
    static ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);
    /**
     *
     */
    public Value<String> status = new Value<String>("");

    /**
     *
     */
    public VStatus() {
        super();
    }

    /**
     *
     * @param _view
     */
    public VStatus(IView _view) {
        super(_view);
    }

    /**
     *
     * @param _view
     * @param _flex
     */
    public VStatus(IView _view, Flex _flex) {
        super(_view, _flex);
    }

    @Override
    public void paintBorder(ICanvas g, int _x, int _y, int _w, int _h) {
        super.paintBorder(g, _x, _y, _w, _h);
        String s = status.toString();
        if (s.length() > 0) {
            AFont f = UV.fonts[UV.cLarge];
            g.setFont(f);
            int fw = (int) f.getW(s);
            int fh = (int) f.getH(s);
            g.setColor(ViewColor.cThemeActive);
            g.setAlpha(0.5f, 0);
            g.roundRect(true, _x + (_w / 2) - (fw / 2) - 4, _y + (_h / 2) - (fh / 2) - 4, fw + 8, fh + 8, 4, 4);
            g.setAlpha(1f, 0);
            g.setColor(ViewColor.cThemeFont);
            UPaint.string(g, status.toString(), UV.fonts[UV.cLarge], _x + (_w / 2), _y + (_h / 2), UV.cCC);
        }
    }

    @Override
    public void mend() {
        if (status.toString().length() > 0) {
            enableFlag(UV.cRepair);
        }
        super.mend();
    }

    /**
     *
     * @param _string
     */
    public void setStatus(String _string) {
        if (_string != null && _string.equals(status.toString())) {
            return;
        }
        status.setValue(_string);
        paint();
    }
    /**
     *
     * @param _count
     * @param _outof
     */
    @Override
    public void out(double _count, double _outof) {
        setStatus((int) ((_count / _outof) * 100) + "%");
    }

    /**
     *
     * @param _value
     */
    @Override
    public void out(Object... _value) {
        if (_value != null) {
            for(Object _v:_value) {
                setStatus(_v.toString());
            }
        }
    }
    
    private static final String done = "done";
    public void done() {
        setStatus(done);
        executor.schedule(new Runnable() {

            @Override
            public void run() {
                String current = status.getValue();
                if (current == done) {
                    setStatus("");
                }
            }
        }, 1200, TimeUnit.MILLISECONDS);
    }

    /**
     *
     * @return
     */
    @Override
    public boolean canceled() {
        return false;
    }
}
