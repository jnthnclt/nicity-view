/*
 * VFontPreview.java.java
 *
 * Created on 03-12-2010 06:39:43 PM
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

import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.event.AInputEvent;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.DragAndDrop;
import colt.nicity.view.core.PickupAndDrop;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.UDrop;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.ViewString;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IDrag;
import colt.nicity.view.interfaces.IDrop;
import colt.nicity.view.interfaces.IDropMode;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;

/**
 *
 * @author Administrator
 */
public class VFontPreview extends Viewer implements IDrag, IDrop, IMouseEvents, IMouseMotionEvents {
    /**
     *
     */
    protected ViewString fontString;
    /**
     *
     * @param _font
     * @param _title
     */
    public VFontPreview(AFont _font, String _title) {
        super();
        if (_font == null) {
            _font = new AFont(AFont.cPlain, 12);
        }
        if (_title == null) {
            _title = "null";
        }

        fontString = new ViewString(_title, _font);
        ViewString fontName = new ViewString(_font.getFont().getName());
        VChain preview = new VChain(UV.cEW, fontName, fontString);
        preview.spans(UV.cXE);

        setPlacer(new Placer(preview));
        setBorder(new ViewBorder());
        spans(UV.cXE);
    }
    /**
     *
     * @param _font
     */
    public void setFont(AFont _font) {
        if (_font == null) {
            return;
        }
        fontString.getFont().setFont(_font.getFont());
        repair();
        flush();
    }
    /**
     *
     * @return
     */
    public AFont getFont() {
        return fontString.getFont();
    }
    //----------------------------------------------------------------------------
    // IDrag
    //----------------------------------------------------------------------------
    /**
     *
     * @return
     */
    public Object getParcel() {
        return new VFontPreview(fontString.getFont(), fontString.getText());
    }
    //----------------------------------------------------------------------------
    // IDrop
    //----------------------------------------------------------------------------
    /**
     *
     * @param object
     * @param _e
     * @return
     */
    public IDropMode accepts(Object object, AInputEvent _e) {
        return UDrop.accepts(new Class[]{VFontPreview.class}, object);
    }
    /**
     *
     * @param object
     * @param mode
     */
    public void dropParcel(Object object, IDropMode mode) {
        if (object == this) {
            return;
        }
        if (object instanceof VFontPreview) {
            setFont(((VFontPreview) object).getFont());
        }
    }
    //----------------------------------------------------------------------------
    // IMouseEvents
    //----------------------------------------------------------------------------
    /**
     *
     * @param e
     */
    public void mouseEntered(MouseEntered e) {
        DragAndDrop.cDefault.mouseEntered(e);
    }
    /**
     *
     * @param e
     */
    public void mouseExited(MouseExited e) {
        DragAndDrop.cDefault.mouseExited(e);
    }
    /**
     *
     * @param e
     */
    public void mousePressed(MousePressed e) {
        DragAndDrop.cDefault.mousePressed(e);
    }
    /**
     *
     * @param e
     */
    public void mouseReleased(MouseReleased e) {
        if (PickupAndDrop.cDefault.event(e)) {
            return;
        }
        DragAndDrop.cDefault.mouseReleased(e);
        VFontBrowser fb = new VFontBrowser(new Value(getFont()), fontString.getText());
        UV.frame(fb, " FontBrowser ");
    }
    //----------------------------------------------------------------------------
    // IMouseMotionEvents
    //----------------------------------------------------------------------------
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
        DragAndDrop.cDefault.mouseDragged(e);
    }
}
