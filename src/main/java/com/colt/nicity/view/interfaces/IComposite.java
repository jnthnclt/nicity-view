/*
 * IComposite.java.java
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
package com.colt.nicity.view.interfaces;
//----------------------------------------------------------------------------
/**
 *
 * @author Administrator
 */
public interface IComposite  {
	//----------------------------------------------------------------------------	 
    /**
     *
     * @param as
     * @param rs
     * @param gs
     * @param bs
     * @param alpha
     * @param ad
     * @param rd
     * @param gd
     * @param bd
     * @return
     */
    public int composite(
		float as,float rs,float gs,float bs,
		float alpha,
		float ad,float rd,float gd,float bd
	);
	//----------------------------------------------------------------------------	 
    /**
     *
     * @param source
     * @param alpha
     * @param destination
     * @return
     */
    public int composite(
		int source, 
		float alpha,
		int destination
	);
	//----------------------------------------------------------------------------	 
        /**
         *
         * @param as
         * @param rs
         * @param gs
         * @param bs
         * @param alpha
         * @param ad
         * @param rd
         * @param gd
         * @param bd
         * @return
         */
        public int composite(
		int as,int rs,int gs,int bs,
		float alpha,
		int ad,int rd,int gd,int bd
	);
    //----------------------------------------------------------------------------	 
        /**
         *
         * @param as
         * @param rs
         * @param gs
         * @param bs
         * @param alpha
         * @param ad
         * @param rd
         * @param gd
         * @param bd
         * @return
         */
        public int composite(
		float as,float rs,float gs,float bs,
		float alpha,
		int ad,int rd,int gd,int bd
	);
    //----------------------------------------------------------------------------	 
        /**
         *
         * @param as
         * @param rs
         * @param gs
         * @param bs
         * @param alpha
         * @param ad
         * @param rd
         * @param gd
         * @param bd
         * @return
         */
        public int composite(
		int as,int rs,int gs,int bs,
		float alpha,
		float ad,float rd,float gd,float bd
	);
    //----------------------------------------------------------------------------	 
        /**
         *
         * @param as
         * @param rs
         * @param gs
         * @param bs
         * @param alpha
         * @param destination
         * @return
         */
        public int composite(
		int as,int rs,int gs,int bs,
		float alpha,
		int destination
	);
    //----------------------------------------------------------------------------	 
        /**
         *
         * @param source
         * @param alpha
         * @param ad
         * @param rd
         * @param gd
         * @param bd
         * @return
         */
        public int composite(
		int source,
		float alpha,
		int ad,int rd,int gd,int bd
	);
    //----------------------------------------------------------------------------	 
        /**
         *
         * @param source
         * @param alpha
         * @param ad
         * @param rd
         * @param gd
         * @param bd
         * @return
         */
        public int composite(
		int source,
		float alpha,
		float ad,float rd,float gd,float bd
	);
    //----------------------------------------------------------------------------	 
        /**
         *
         * @param as
         * @param rs
         * @param gs
         * @param bs
         * @param alpha
         * @param destination
         * @return
         */
        public int composite(
		float as,float rs,float gs,float bs,
		float alpha,
		int destination
	);
}
