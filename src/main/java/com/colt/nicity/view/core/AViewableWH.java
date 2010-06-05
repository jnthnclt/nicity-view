/*
 * AViewableWH.java.java
 *
 * Created on 01-03-2010 01:29:41 PM
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

/**
 *
 * @author Administrator
 */
public abstract class AViewableWH extends AViewable {

    /**
     *
     */
    public AViewableWH() {
        super();
    }
    /**
     *
     */
    protected float w;
    /**
     *
     */
    protected float h;

    @Override
    public float getW() {
        return w;
    }

    @Override
    public float getH() {
        return h;
    }
}
