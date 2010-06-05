/*
 * IMouseEvents.java.java
 *
 * Created on 01-24-2010 09:54:32 PM
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
package com.colt.nicity.view.interfaces;

import com.colt.nicity.view.event.MouseEntered;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MousePressed;
import com.colt.nicity.view.event.MouseReleased;

/**
 *
 * @author Administrator
 */
public interface IMouseEvents {

    /**
     *
     * @param e
     */
    public void mouseEntered(MouseEntered e);

    /**
     *
     * @param e
     */
    public void mouseExited(MouseExited e);

    /**
     *
     * @param e
     */
    public void mousePressed(MousePressed e);

    /**
     *
     * @param e
     */
    public void mouseReleased(MouseReleased e);
}
