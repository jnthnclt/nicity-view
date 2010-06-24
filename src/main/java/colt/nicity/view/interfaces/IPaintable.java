/*
 * IPaintable.java.java
 *
 * Created on 01-30-2010 09:51:01 AM
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
package colt.nicity.view.interfaces;

import colt.nicity.core.memory.struct.XYWH_I;

/**
 *
 * @author Administrator
 */
public interface IPaintable {

    /**
     *
     * @param _g
     * @param _xywh
     */
    public void paint(ICanvas _g, XYWH_I _xywh);

    /**
     *
     * @param _under
     * @param _over
     * @return
     */
    public float getW(IPaintable _under, IPaintable _over);

    /**
     *
     * @param _under
     * @param _over
     * @return
     */
    public float getH(IPaintable _under, IPaintable _over);
}
