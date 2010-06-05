/*
 * LinkViewer.java.java
 *
 * Created on 01-03-2010 01:32:53 PM
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

import com.colt.nicity.view.event.MouseEntered;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MousePressed;
import com.colt.nicity.view.event.MouseReleased;
import com.colt.nicity.view.monitor.VException;
import com.colt.nicity.core.lang.UArray;
import com.colt.nicity.core.lang.UString;
import com.colt.nicity.view.interfaces.IMouseEvents;
import com.colt.nicity.view.interfaces.IToolTip;
import com.colt.nicity.view.interfaces.IView;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Administrator
 */
public class LinkViewer extends Viewer implements IMouseEvents {

    /**
     *
     */
    protected IToolTip toolTip;
    /**
     *
     */
    protected String link;

    /**
     *
     */
    public LinkViewer() {
        super();
    }

    /**
     *
     * @param _view
     * @param _link
     */
    public LinkViewer(IView _view, String _link) {
        super(_view);
        link = _link;
    }

    public void setToolTip(IToolTip _toolTip) {
        toolTip = _toolTip;
    }

    public IToolTip getToolTip() {
        return toolTip;
    }

    // IMouseEvents
    public void mouseEntered(MouseEntered _e) {
        activateBorder();
    }

    public void mouseExited(MouseExited _e) {
        deactivateBorder();
    }

    public void mousePressed(MousePressed _e) {
        selectBorder();
    }

    public void mouseReleased(MouseReleased _e) {
        try {
            Object content = new URL(link).getContent();
            if (content instanceof InputStream) {
                InputStream in = (InputStream) content;

                try {
                    int count = 0;
                    int len = 1024;
                    char[] chars = new char[len];
                    int c = 0;
                    while ((c = in.read()) != -1) {
                        if (count >= len) {
                            chars = UArray.grow(chars, len);
                            len = chars.length;
                        }
                        chars[count++] = (char) c;
                    }
                    chars = UArray.trim(chars, new char[count]);
                    content = new String(chars);
                } catch (Exception x) {
                    content = "LoadError";
                }
            }

            if (content == null) {
                content = "null";
            }
            setPlacer(new Placer(new ViewText(UString.toStringArray(content.toString(), "\r"))));

        } catch (Exception x) {
            setPlacer(new Placer(new ViewString(x.toString())));
            new VException(x, this.getClass() + " exception ").toFront(null);

        }
        layoutInterior();
        flush();
        deselectBorder();
    }
}
