/*
 * VIconSwitch.java.java
 *
 * Created on 01-03-2010 01:34:45 PM
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
package colt.nicity.view.value;

import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.list.AItem;
import colt.nicity.core.lang.ICallback;
import colt.nicity.view.core.AColor;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IPaintable;

/**
 *
 * @author Administrator
 */
public class VIconSwitch extends AItem implements IMouseMotionEvents {

    IPaintable[] icons;			// index[i] displays icons[i]
    int index;				// purpose of GUI is to set index
    int dynamic;			// used as the index while unlocked and mouse dragging
    int hysteresis = 20;	// drag up/down this length before index changes
    int margin = 2;			// border around icon
    int start = 0;			// mouse down makes start = y
    boolean locked = true;	// mouse down unlocks; mouse up locks
    ICallback callback;		// who to tell when value changes

    /**
     *
     * @param _icons
     * @param _w
     * @param _h
     */
    public VIconSwitch(IPaintable[] _icons, float _w, float _h) {
        h = _h;
        w = _w;
        icons = _icons;
    }

    /**
     *
     * @return
     */
    public int getCase() {
        return index;
    }

    ;

    /**
     *
     * @param _index
     */
    public void setCase(int _index) {
        index = _index % icons.length;
        if (callback != null) {
            callback.callback(new Integer(index));
        }
    }

    ;

    /**
     *
     * @param _callback
     */
    public void setCallback(ICallback _callback) {
        callback = _callback;
    }

    ;

    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     */
    public void paintBackground(ICanvas _g, int _x, int _y, int _w, int _h) {
        super.paintBackground(_g, _x, _y, _w, _h);
        if (locked) {
            _g.setColor(AColor.gray);
            _g.rect(true, _x, _y, _w, _h);
            if (icons != null) {
                icons[index].paint(_g, null);
            }
        } else {
            _g.setColor(AColor.cyan);
            _g.rect(true, _x, _y, _w, _h);
            if (icons != null) {
                icons[dynamic].paint(_g, null);
            }
        }
    }

    // IMouseEvents
    /**
     *
     * @param _e
     */
    public void mouseEntered(MouseEntered _e) {
    }

    /**
     *
     * @param _e
     */
    public void mouseExited(MouseExited _e) {
    }

    /**
     *
     * @param _e
     */
    public void mousePressed(MousePressed _e) {
        start = (int) _e.getPoint().y;
        locked = false;
        ;
        paint();
    }

    /**
     *
     * @param _e
     */
    public void mouseReleased(MouseReleased _e) {
        locked = true;
        setCase(dynamic);
        paint();
    }

    // IMouseMotionEvents
    /**
     *
     * @param _e
     */
    public void mouseMoved(MouseMoved _e) {
    }

    /**
     *
     * @param _e
     */
    public void mouseDragged(MouseDragged _e) {
        int delta = start - _e.getPoint().y;
        dynamic = (Math.abs(index + icons.length + delta / hysteresis)) % icons.length;
        paint();
    }
}
