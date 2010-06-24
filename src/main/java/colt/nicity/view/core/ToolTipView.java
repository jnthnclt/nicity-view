/*
 * ToolTipView.java.java
 *
 * Created on 01-03-2010 01:31:37 PM
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

import colt.nicity.view.interfaces.IPlacer;
import colt.nicity.view.interfaces.IToolTip;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class ToolTipView extends Viewer implements IToolTip {

    /**
     *
     */
    protected int dismiss = 5000;

    /**
     *
     */
    public ToolTipView() {
        super();
    }

    /**
     *
     * @param _placer
     * @param _dismiss
     */
    public ToolTipView(IPlacer _placer, int _dismiss) {
        super(_placer);
        dismiss = _dismiss;
    }

    /**
     *
     * @param _view
     * @param _dismiss
     */
    public ToolTipView(IView _view, int _dismiss) {
        super(_view);
        dismiss = _dismiss;
    }

    /**
     *
     * @param _view
     * @param _flex
     * @param _dismiss
     */
    public ToolTipView(IView _view, Flex _flex, int _dismiss) {
        super(_view, _flex);
        dismiss = _dismiss;
    }

    // IToolTip
    public void setDismissDelay(int _dismiss) {
        dismiss = _dismiss;
    }

    public int getDismissDelay() {
        return dismiss;
    }

    public void elaborate() {
    }
}
