/*
 * PreviewColor.java.java
 *
 * Created on 01-03-2010 01:31:34 PM
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

import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.event.AInputEvent;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.interfaces.IDrag;
import colt.nicity.view.interfaces.IDrop;
import colt.nicity.view.interfaces.IDropMode;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;

/**
 *
 * @author Administrator
 */
public class PreviewColor extends Viewer implements IDrag, IDrop, IMouseEvents, IMouseMotionEvents {

    /**
     *
     */
    protected VBox colorBox;
    /**
     *
     */
    protected String title;

    /**
     *
     * @param _color
     * @param _title
     */
    public PreviewColor(AColor _color, String _title) {
        this(_color, _title, 30);
    }

    /**
     *
     * @param _color
     * @param _title
     * @param _size
     */
    public PreviewColor(AColor _color, String _title, int _size) {
        super();
        if (_color == null) {
            _color = new AColor(0, 0, 0);
        }
        title = _title;

        colorBox = new VBox(_size, _size, _color);
        VChain preview = new VChain(UV.cEW);
        if (title != null) {
            preview.add(new ViewString(title));
        }
        preview.add(colorBox);
        preview.spans(UV.cXE);

        setPlacer(new Placer(preview));
        setBorder(new ViewBorder());
        spans(UV.cXE);
    }

    /**
     *
     * @param _color
     */
    public void setColor(AColor _color) {
        if (_color == null) {
            return;
        }
        colorBox.getColor().setRGB(_color.getR(), _color.getG(), _color.getB());
        repair();
        flush();
    }

    /**
     *
     * @return
     */
    public AColor getColor() {
        return colorBox.getColor();
    }

    @Override
    public String toString() {
        if (title == null) {
            return "";
        }
        return title;
    }

    // IDrag
    public Object getParcel() {
        return new VBox(32, 32, getColor());
    }

    // IDrop
    public IDropMode accepts(Object object, AInputEvent _e) {
        return UDrop.accepts(new Class[]{VBox.class}, object);
    }

    public void dropParcel(Object object, IDropMode mode) {
        if (object == this) {
            return;
        }
        if (object instanceof VBox) {
            setColor(((VBox) object).getColor());
        }
    }

    // IMouseEvents
    public void mouseEntered(MouseEntered e) {
        DragAndDrop.cDefault.mouseEntered(e);
    }

    public void mouseExited(MouseExited e) {
        DragAndDrop.cDefault.mouseExited(e);
    }

    public void mousePressed(MousePressed e) {
        DragAndDrop.cDefault.mousePressed(e);
    }

    public void mouseReleased(MouseReleased e) {
        if (PickupAndDrop.cDefault.event(e)) {
            return;
        }
        DragAndDrop.cDefault.mouseReleased(e);
    }

    // IMouseMotionEvents
    public void mouseMoved(MouseMoved e) {
    }

    public void mouseDragged(MouseDragged e) {
        DragAndDrop.cDefault.mouseDragged(e);
    }
}
