/*
 * VCallback.java.java
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
package colt.nicity.view.core;

import colt.nicity.core.lang.ICallback;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class VCallback implements ICallback {

    IView view;

    /**
     *
     * @param _view
     */
    public VCallback(IView _view) {
        view = _view;
    }

    //  ICallback
    /**
     *
     * @param _value
     * @return
     */
    public Object callback(Object _value) {
        return view;
    }
}
