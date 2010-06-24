/*
 * OriginLens.java.java
 *
 * Created on 01-03-2010 01:34:08 PM
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
package colt.nicity.view.paint.lens;

import colt.nicity.core.memory.struct.XY_D;

/**
 *
 * @author Administrator
 */
public class OriginLens extends ALens {

    /**
     *
     */
    public double width = 1;
    /**
     *
     */
    public double height = 1;

    /**
     *
     * @param p
     */
    @Override
    public void applyLens(XY_D p) {
        p.x = p.x + width / 2;
        p.y = p.y + height / 2;
    }

    /**
     *
     * @param p
     */
    @Override
    public void undoLens(XY_D p) {
        p.x = p.x - width / 2;
        p.y = p.y - height / 2;
    }
}
