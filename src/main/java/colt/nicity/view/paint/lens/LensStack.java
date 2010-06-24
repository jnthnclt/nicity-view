/*
 * LensStack.java.java
 *
 * Created on 01-24-2010 09:44:08 PM
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

/*
 * Example:
 * LensStack lens = new LensStack();
 * lens.addLens(new OffsetLens());
 * lens.addLens(new ZoomLens());
 * lens.addLens(new RotateLens());
 * lens.addLens(new OriginLens());
 */
import colt.nicity.core.collection.CArray;


/*
 * Example:
 * LensStack lens = new LensStack();
 * lens.addLens(new OffsetLens());
 * lens.addLens(new ZoomLens());
 * lens.addLens(new RotateLens());
 * lens.addLens(new OriginLens());
 */
import colt.nicity.core.memory.struct.XY_D;

/**
 *
 * @author Administrator
 */
public class LensStack extends ALens {

    CArray<ALens> lenses = new CArray<ALens>(ALens.class);

    /**
     *
     * @param l
     */
    public void addLens(ALens l) {
        lenses.insertLast(l);
    }

    /**
     *
     * @param p
     */
    @Override
    public void applyLens(XY_D p) {
        if (lenses.getCount() == 0) {
            return;
        }
        for (int i = 0; i < lenses.getCount(); i++) {
            lenses.getAt(i).applyLens(p);
        }
    }

    /**
     *
     * @param p
     */
    @Override
    public void undoLens(XY_D p) {
        if (lenses.getCount() == 0) {
            return;
        }
        for (int i = 0; i < lenses.getCount(); i++) {
            lenses.getAt(i).undoLens(p);
        }
    }
}

