/*
 * VButton.java.java
 *
 * Created on 01-03-2010 01:32:11 PM
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
package com.colt.nicity.view.core;

import com.colt.nicity.view.border.ButtonBorder;
import com.colt.nicity.view.event.AInputEvent;
import com.colt.nicity.view.event.KeyPressed;
import com.colt.nicity.view.event.MouseEntered;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MousePressed;
import com.colt.nicity.view.event.MouseReleased;
import com.colt.nicity.view.interfaces.IBorder;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IDropMode;
import com.colt.nicity.view.interfaces.IToolTip;
import com.colt.nicity.view.interfaces.IView;
import com.colt.nicity.view.list.AItem;

/**
 *
 * @author Administrator
 */
public class VButton extends AItem {

    /**
     *
     */
    protected IToolTip toolTip;
    private boolean isDisabled;

    /**
     *
     */
    public VButton() {
        super();
        setBorder(new ButtonBorder());
    }

    /**
     *
     * @param _name
     */
    public VButton(Object _name) {
        this(new VString(_name, ViewColor.cButtonFont));
    }

    /**
     *
     * @param _name
     * @param _font
     */
    public VButton(Object _name, AFont _font) {
        this((IView) new VString(_name, _font, ViewColor.cButtonFont));
    }

    /**
     *
     * @param _iconName
     * @param _name
     * @param _place
     */
    public VButton(String _iconName, String _name, Place _place) {
        this(new VChain(_place, VIcon.icon(_iconName, 16), new ViewString(_name, ViewColor.cButtonFont)));
    }

    /**
     *
     * @param _iconName
     * @param _name
     * @param _place
     * @param _vertical
     */
    public VButton(String _iconName, String _name, Place _place, boolean _vertical) {
        this(new VChain(_place, VIcon.icon(_iconName, 16), new ViewString(_name, ViewColor.cButtonFont)));
    }

    /**
     *
     * @param _view
     */
    public VButton(IView _view) {
        setPlacer(new Placer(_view, UV.cOrigin));
        setBorder(new ButtonBorder());
    }

    /**
     *
     * @param _view
     * @param _borderColor
     */
    public VButton(IView _view, AColor _borderColor) {
        setPlacer(new Placer(_view, UV.cOrigin));
        setBorder(new ButtonBorder(_borderColor));
    }

    /**
     *
     * @param _name
     * @param _border
     * @param _spans
     */
    public VButton(Object _name, IBorder _border, int _spans) {
        this(new VString(_name, ViewColor.cButtonFont), _border, _spans);
    }

    /**
     *
     * @param _view
     * @param _border
     * @param _spans
     */
    public VButton(IView _view, IBorder _border, int _spans) {
        setPlacer(new Placer(_view, UV.cOrigin));
        setBorder(_border);
        spans(_spans);
    }

    /**
     *
     * @param _iconName
     * @param _name
     * @param _place
     */
    public void setView(String _iconName, String _name, Place _place) {
        VChain c = new VChain(_place, VIcon.icon(_iconName, 16), new ViewString(_name, ViewColor.cButtonFont));
        setPlacer(new Placer(c, UV.cOrigin));
        layoutInterior();
        flush();
    }

    /**
     *
     * @param _toolTip
     */
    public void setToolTip(String _toolTip) {
        setToolTip(ToolTipFactory.cDefault.createToolTip(_toolTip));
    }

    /**
     *
     * @param _toolTip
     */
    public void setToolTip(String[] _toolTip) {
        setToolTip(ToolTipFactory.cDefault.createToolTip(_toolTip));
    }

    @Override
    public void setToolTip(IToolTip _toolTip) {
        toolTip = _toolTip;
    }

    @Override
    public IToolTip getToolTip() {
        return toolTip;
    }

    @Override
    public void paintBorder(ICanvas g, int _x, int _y, int _w, int _h) {
        super.paintBorder(g, _x, _y, _w, _h);
        if (isDisabled) {
            g.setAlpha(0.5f, 0);
            IBorder _border = getBorder();
            if (_border != null) {
                _border.paintBackground(g, _x, _y, _w, _h);
            }
            g.setAlpha(1f, 0);
        }
    }

    /**
     *
     * @return
     */
    public boolean isDisabled() {
        return isDisabled;
    }

    /**
     *
     * @param _isDisabled
     */
    public void setDisabled(boolean _isDisabled) {
        isDisabled = _isDisabled;
    }

    @Override
    public Object getValue() {
        return this;
    }

    @Override
    public String toString() {
        return getContent().toString();
    }

    // IMouseEvents
    @Override
    public void mouseEntered(MouseEntered _e) {
        if (!isDisabled) {
            super.mouseEntered(_e);
            activateBorder();
        }
    }

    @Override
    public void mouseExited(MouseExited _e) {
        if (!isDisabled) {
            super.mouseExited(_e);
            deactivateBorder();
        }
    }

    @Override
    public void mousePressed(MousePressed _e) {
        if (!isDisabled) {
            super.mousePressed(_e);
            selectBorder();
        }
    }

    @Override
    public void mouseReleased(MouseReleased _e) {
        if (!isDisabled) {
            super.mouseReleased(_e);
            deactivateBorder();
            deselectBorder();
        }
    }

    // IKeyEvents
    @Override
    public void keyPressed(KeyPressed _e) {
        if (!isDisabled) {
            super.keyPressed(_e);
        }
    }

    // IDrag
    @Override
    public Object getParcel() {
        return null;
    }

    // IDrop
    @Override
    public IDropMode accepts(Object value, AInputEvent _e) {
        return null;
    }

    @Override
    public void dropParcel(Object value, IDropMode mode) {
    }
}
