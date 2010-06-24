/*
 * AListController.java.java
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
package colt.nicity.view.list;

import colt.nicity.core.collection.IBackcall;
import colt.nicity.core.collection.NullBackcall;
import colt.nicity.core.lang.ASetObject;
import colt.nicity.core.lang.IOut;
import colt.nicity.core.observer.AObserver;
import colt.nicity.core.observer.Change;
import colt.nicity.core.observer.IObservable;
import colt.nicity.core.observer.IObserver;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IListController;

/**
 *
 * @author Administrator
 */
abstract public class AListController extends ASetObject implements IListController {

    /**
     *
     */
    protected AVList list;
    /**
     *
     */
    protected IBackcall backcall = NullBackcall.cNull;
    /**
     *
     */
    protected IObserver observer;
    /**
     *
     */
    protected long autoUpdateElapse = 600;

    /**
     *
     */
    public AListController() {
    }

    /**
     *
     * @param _backcall
     */
    public AListController(IBackcall _backcall) {
        this(_backcall, 600);
    }

    /**
     *
     * @param _backcall
     * @param _autoUpdateElapse
     */
    public AListController(IBackcall _backcall, long _autoUpdateElapse) {
        autoUpdateElapse = _autoUpdateElapse;
        if (_backcall == null) {
            _backcall = NullBackcall.cNull;
        }
        backcall = _backcall;
        if (backcall instanceof IObservable) {
            attachObserver((IObservable) backcall);
        }
    }

    /**
     *
     * @return
     */
    public Object hashObject() {
        return this;
    }

    /**
     *
     * @param _backcall
     */
    protected void attachObserver(IObservable _backcall) {
        if (observer == null) {
            observer = new AObserver() {

                @Override
                public void bound(IObservable _observable) {
                    //listModified(null);
                }

                @Override
                public void change(Change _change) {
                    listModified(null);
                }

                @Override
                public void released(IObservable _observable) {
                    //listModified(null);
                }
            };
        }
        ((IObservable) backcall).bind(observer);
    }

    /**
     *
     * @param _list
     */
    public void setVList(AVList _list) {
        list = _list;
    }

    /**
     *
     * @return
     */
    public AVList getVList() {
        return list;
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        return list;
    }

    /**
     *
     * @return
     */
    public IBackcall getBackcall() {
        return backcall;
    }

    /**
     *
     * @param _backcall
     */
    public void setBackcall(IBackcall _backcall) {
        if (_backcall == null) {
            backcall = NullBackcall.cNull;
        } else {
            backcall = _backcall;
            if (backcall instanceof IObservable) {
                attachObserver((IObservable) backcall);
            }
        }
        if (list == null) {
            return;
        }
        list.layoutInterior();
        list.paint();
    }

    /**
     *
     * @param _list
     * @param _task
     * @return
     */
    public IEvent processEvent(AVList _list, IEvent _task) {
        return _task;
    }

    /**
     *
     * @param _
     */
    public void listModified(IOut _) {
        if (list == null) {
            return;
        }
        synchronized (this) {
            list.layoutInterior();
            list.paint();
        }
    }

    /**
     *
     * @param _
     */
    public void filterModified(IOut _) {
        if (list == null) {
            return;
        }
        synchronized (this) {
            list.layoutInterior();
            list.paint();
        }
    }

    /**
     *
     * @return
     */
    public String getFilter() {
        return "";
    }
}
