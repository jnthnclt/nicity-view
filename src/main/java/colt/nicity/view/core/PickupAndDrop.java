/*
 * PickupAndDrop.java.java
 *
 * Created on 01-03-2010 01:34:44 PM
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

import colt.nicity.view.event.AMouseEvent;
import colt.nicity.view.value.AcceptDecline;
import colt.nicity.view.value.AcceptRejectDecline;
import colt.nicity.core.collection.CArray;
import colt.nicity.view.interfaces.IDrag;
import colt.nicity.view.interfaces.IDrop;
import colt.nicity.view.interfaces.IDropMode;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Jonathan Colt
 * Feb 29, 2008
 */
public class PickupAndDrop {

    /**
     *
     */
    public static PickupAndDrop cDefault = new PickupAndDrop();
    CArray pickedUp = new CArray();

    /**
     *
     */
    public PickupAndDrop() {
    }

    /**
     *
     * @param _e
     * @return
     */
    public boolean isPickEvent(AMouseEvent _e) {
        return _e.isAltDown() && _e.isLeftClick() && _e.isSingleClick();
    }

    /**
     *
     * @param _e
     * @return
     */
    public boolean event(final AMouseEvent _e) {
        if (isPickEvent(_e)) {
            final Object source = _e.getSource();
            boolean canDrag = (source instanceof IDrag);
            boolean canDrop = (source instanceof IDrop);
            System.out.println("PickupAndDrop" + canDrag + " " + canDrop + " " + source);
            if (canDrag && canDrop) {
                String[] _explain = new String[]{
                    "This item can get drag to and dropped on.",
                    "What would you like to do?"
                };
                new AcceptRejectDecline(_explain, "Pickup", "Dropoff", "Clear") {

                    @Override
                    public void accept() {
                        pickup((IDrag) source);
                    }

                    @Override
                    public void reject() {
                        dropoff((IDrop) source, _e);
                    }

                    @Override
                    public void decline() {
                        pickedUp.removeAll();
                    }
                }.toFront((IView) source);
                return true;
            } else if (canDrag) {
                String[] _explain = new String[0];
                new AcceptDecline(_explain, "Pickup", "Clear") {

                    @Override
                    public void accept() {
                        pickup((IDrag) source);
                    }

                    @Override
                    public void decline() {
                        pickedUp.removeAll();
                    }
                }.toFront((IView) source);
                return true;
            } else if (canDrop) {
                dropoff((IDrop) source, _e);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param _drag
     */
    public void pickup(IDrag _drag) {
        pickedUp.insertLast((Object) ((IDrag) _drag).getParcel());
    }

    /**
     *
     * @param _drop
     * @param _e
     */
    public void dropoff(IDrop _drop, AMouseEvent _e) {
        Object[] all = pickedUp.removeAll();
        for (Object a : all) {
            IDropMode mode = _drop.accepts(a, _e);
            if (mode != null) {
                _drop.dropParcel(a, mode);
            }
        }
    }
}
