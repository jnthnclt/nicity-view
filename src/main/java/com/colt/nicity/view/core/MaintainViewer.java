/*
 * MaintainViewer.java.java
 *
 * Created on 01-03-2010 01:31:33 PM
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

import com.colt.nicity.view.interfaces.IBorder;
import com.colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class MaintainViewer extends Viewer {

    /**
     *
     */
    protected IView content = null;
    /**
     *
     */
    protected float restoreW;
    /**
     *
     */
    protected float restoreH;
    /**
     *
     */
    protected boolean isVertical = false;
    /**
     *
     */
    protected boolean asNeeded = true;

    /**
     *
     */
    public MaintainViewer() {
        super();
    }

    /**
     *
     * @param _w
     * @param _h
     * @param _content
     * @param _isVertical
     * @param _border
     */
    public MaintainViewer(float _w, float _h, IView _content, boolean _isVertical, IBorder _border) {
        this(_w, _h, _content, _isVertical, true, _border);
    }

    /**
     *
     * @param _w
     * @param _h
     * @param _content
     * @param _isVertical
     * @param _asNeeded
     * @param _border
     */
    public MaintainViewer(float _w, float _h, IView _content, boolean _isVertical, boolean _asNeeded, IBorder _border) {
        super();
        setBorder(_border);
        asNeeded = _asNeeded;
        isVertical = _isVertical;
        setContent(_w, _h, _content);
    }

    /**
     *
     * @param _isVertical
     */
    public void setVertical(boolean _isVertical) {
        isVertical = _isVertical;
    }

    /**
     *
     * @return
     */
    public boolean isVertical() {
        return isVertical;
    }

    /**
     *
     * @param _restore
     */
    public void setRestoreSize(float _restore) {
        if (isVertical()) {
            restoreH = _restore;
        } else {
            restoreW = _restore;
        }
    }

    @Override
    public IView getContent() {
        return content;
    }

    @Override
    public void setContent(IView _content) {
        if (content == NullView.cNull) {
            if (isVertical()) {
                setContent(restoreW, h, _content);
            } else {
                setContent(w, restoreH, _content);
            }
        } else {
            setContent(w, h, _content);
        }
    }

    /**
     *
     * @param _w
     * @param _h
     * @param _content
     */
    public void setContent(float _w, float _h, IView _content) {
        if (_content == null || _content == NullView.cNull) {
            content = NullView.cNull;
            if (isVertical()) {
                placer = new Placer(new VPan(content, 1, _h));//new VBox(1,_h,AColor.white));
            } else {
                placer = new Placer(new VPan(content, _w, 1));//new VBox(_w,1,AColor.white));
            }
            layoutInterior();
            return;
        }

        content = _content;

        content.setParentView(NullView.cNull);
        content.layoutInterior();

        flex(_w, _h, _content);

        float cw = content.getW();
        float ch = content.getH();

        if (asNeeded) {
            if ((int) (cw) != (int) (_w) && (int) (ch) != (int) (_h)) { // wrapper
                Viewer wrapper = new VPan(content, _w, _h);
                wrapper.setBorder(getBorder());
                placer = new Placer(wrapper);
            } else if ((int) (cw) == (int) (_w) && (int) (ch) != (int) (_h)) { // wrapper
                Viewer wrapper = new VPan(content, -1, _h);
                wrapper.setBorder(getBorder());
                placer = new Placer(wrapper);
            } else if ((int) (cw) != (int) (_w) && (int) (ch) == (int) (_h)) { // wrapper
                Viewer wrapper = new VPan(content, _w, -1);
                wrapper.setBorder(getBorder());
                placer = new Placer(wrapper);
            } else {
                placer = new Placer(content);
            }
        } else {
            if ((int) (cw) != (int) (_w) || (int) (ch) != (int) (_h)) { // wrapper
                Viewer wrapper = new VPan(content, _w, _h);
                wrapper.setBorder(getBorder());
                placer = new Placer(wrapper);
            } else {
                placer = new Placer(content);
            }
        }

        layoutInterior();
        restoreW = cw;
        restoreH = ch;
    }

    static private void flex(float _w, float _h, IView _content) {

        float cw = _content.getW();
        float ch = _content.getH();

        if ((int) (cw) != (int) (_w) || (int) (ch) != (int) (_h)) {
            int flexDeltaX = 0;
            int flexDeltaY = 0;
            int testFlexX = 0;
            int testFlexY = 0;
            if ((int) cw == 0 || (int) cw == 1 || (int) (cw) != (int) (_w)) {
                flexDeltaX = ((int) _w) - ((int) cw);
                testFlexX = (flexDeltaX > 0) ? 1 : -1;
            }
            if ((int) ch == 0 || (int) ch == 1 || (int) (ch) != (int) (_h)) {
                flexDeltaY = ((int) _h) - ((int) ch);
                testFlexY = (flexDeltaY > 0) ? 1 : -1;
            }

            // test flex
            _content.setParentView(NullView.cNull);
            _content.disableFlag(UV.cInterior);
            _content.layoutInterior(_content, new Flex(_content, testFlexX, testFlexY));

            if (testFlexX > 0 && (int) _content.getW() < cw) {
                flexDeltaX = -(flexDeltaX + testFlexX);
            } else if (testFlexX < 0 && (int) _content.getW() > cw) {
                flexDeltaX = -(flexDeltaX - testFlexX);
            }

            if (testFlexY > 0 && (int) _content.getH() < ch) {
                flexDeltaY = -(flexDeltaY + testFlexY);
            } else if (testFlexY < 0 && (int) _content.getH() > ch) {
                flexDeltaY = -(flexDeltaY - testFlexY);
            }

            _content.setParentView(NullView.cNull);
            _content.disableFlag(UV.cInterior);
            _content.layoutInterior(_content, new Flex(_content, flexDeltaX, flexDeltaY));
            flexDeltaX = ((int) _w) - ((int) _content.getW());
            flexDeltaY = ((int) _h) - ((int) _content.getH());
            if (flexDeltaX != 0 || flexDeltaY != 0) {
                _content.setParentView(NullView.cNull);
                _content.disableFlag(UV.cInterior);
                _content.layoutInterior(_content, new Flex(_content, flexDeltaX, flexDeltaY));
            }

        }
    }
}
