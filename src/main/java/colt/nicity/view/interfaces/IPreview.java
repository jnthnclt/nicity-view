/*
 * IPreview.java.java
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
package colt.nicity.view.interfaces;


/**
 *
 * @author Administrator
 */
public interface IPreview {
    /**
     *
     * @param _g
     * @param _x
     * @param _y
     * @param _w
     * @param _h
     * @param _f
     * @param _a
     * @param _t
     * @param _scale
     */
    public void preview(ICanvas _g,int _x,int _y,int _w,int _h,double _f,double _a,double _t,double _scale);
        /**
         *
         * @return
         */
        public double previewTime();
        /**
         *
         * @param _scale
         * @return
         */
        public int preferedW(double _scale);
    /**
     *
     * @param _scale
     * @return
     */
    public int preferedH(double _scale);

    
    //public long numberOfSamples();
    // duration should return a double value that is a % of the _numberOfFrames
    //public void duration(long _totalNumberOfFrames,double _frameRatePerSecond,IAsync _normalizedDuration);
}
	 
