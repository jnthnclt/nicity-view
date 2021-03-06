/*
 * LinLogProgress.java.java
 *
 * Created on 01-02-2010 10:50:03 AM
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
package linloglayout;

/**
 * 
 * @author jonathan
 */
public interface LinLogProgress {

    /**
     * 
     * @param _count
     * @param _outof
     */
    public void out(double _count, double _outof);

    /**
     * 
     * @param _value
     */
    public void out(Object... _value);

    /**
     * 
     * @return
     */
    public boolean canceled();
}
