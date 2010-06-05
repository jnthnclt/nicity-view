/*
 * AWindowPopup.java.java
 *
 * Created on 01-03-2010 01:31:40 PM
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

import com.colt.nicity.view.event.ADK;
import com.colt.nicity.view.awt.UAWT;
import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.view.interfaces.IPeerView;
import com.colt.nicity.view.interfaces.IPopup;
import com.colt.nicity.view.interfaces.IRootView;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class AWindowPopup extends AWindow implements IPopup, IRootView, IView {

    /**
     *
     */
    public boolean hideOnExit = false;
    /**
     *
     */
    public boolean hideOnLost = true;
    IView reference;

    /**
     *
     */
    public AWindowPopup() {
    }

    /**
     *
     * @param _reference
     * @param _view
     */
    public AWindowPopup(IView _reference, IView _view) {
        reference = _reference;
        setContent(_view);
        layoutInterior();
    }

    /**
     *
     * @return
     */
    public IPeerView referencePeer() {
        if (reference == null) {
            return null;
        }
        return reference.getRootView().getPeerView();
    }

    public void showPopup(XY_I p, IView popupParent) {
        if (p == null) {
            p = new XY_I((int) ((UAWT.getScreenWidth() / 2) - (getW() / 2)), (int) ((UAWT.getScreenHeight() / 2) - (getH() / 2)));
        }
        if (p.x < 0) {
            p.x = 0;
        }
        if (p.y < 0) {
            p.y = 0;
        }
        setLocation(p.x, p.y);
        show();
        toFront();
    }

    public void hidePopup() {
        dispose();
    }

    @Override
    public void processEvent(IOut _,PrimativeEvent event) {
        super.processEvent(_,event);
        if (event.family == ADK.cComponent) {
            if (event.id == ADK.cFocusLost) {
                if (hideOnLost) {
                    hidePopup();
                }
            }
        } else if (event.family == ADK.cComponent) {
            if (event.id == ADK.cWindowLostFocus) {
                if (hideOnLost) {
                    hidePopup();
                }
            }
        } else if (event.family == ADK.cMouse) {
            if (event.id == ADK.cMouseExited) {
                if (hideOnExit) {
                    hidePopup();
                }
            }
        }

    }
}
