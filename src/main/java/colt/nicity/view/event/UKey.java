/*
 * UKey.java.java
 *
 * Created on 01-03-2010 01:31:38 PM
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
package colt.nicity.view.event;

import colt.nicity.view.adaptor.IKeyEventConstants;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class UKey {

    /**
     *
     * @param e
     * @param view
     */
    public static void arrowKeys(AKeyEvent e, IView view) {

        int code = e.getKeyCode();
        if (code == IKeyEventConstants.cRight) {
            autoScroll(view.transferFocusToNearestNeighbor(e.who(), IKeyEventConstants.cRight));
            if (e.isShiftDown()) {
                view.selectBorder();
            }
        } else if (code == IKeyEventConstants.cLeft) {
            autoScroll(view.transferFocusToNearestNeighbor(e.who(), IKeyEventConstants.cLeft));
            if (e.isShiftDown()) {
                view.selectBorder();
            }
        } else if (code == IKeyEventConstants.cUp) {
            autoScroll(view.transferFocusToNearestNeighbor(e.who(), IKeyEventConstants.cUp));
            if (e.isShiftDown()) {
                view.selectBorder();
            }
        } else if (code == IKeyEventConstants.cDown) {
            autoScroll(view.transferFocusToNearestNeighbor(e.who(), IKeyEventConstants.cDown));
            if (e.isShiftDown()) {
                view.selectBorder();
            }
        }
    }

    private static void autoScroll(IView view) {
        if (!view.isVisible()) {
            view.scrollTo(0, 0, view.getW(), view.getH());
        }
    }
}
