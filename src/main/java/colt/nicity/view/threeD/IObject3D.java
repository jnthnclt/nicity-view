/*
 * IObject3D.java.java
 *
 * Created on 01-03-2010 01:34:24 PM
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
package colt.nicity.view.threeD;
import colt.nicity.core.memory.struct.XYZ_D;
/**
 *
 * @author Administrator
 */
public interface IObject3D  {
	
    /**
     *
     * @param _center
     */
    public void setCenter(XYZ_D _center);
    /**
     *
     * @return
     */
    public XYZ_D getCenter();
	
        /**
         *
         * @param _pitch
         */
        public void setPitch(double _pitch);
        /**
         *
         * @return
         */
        public double getPitch();
        /**
         *
         * @param _yaw
         */
        public void setYaw(double _yaw);
        /**
         *
         * @return
         */
        public double getYaw();
        /**
         *
         * @param _roll
         */
        public void setRoll(double _roll);
        /**
         *
         * @return
         */
        public double getRoll();
	
        /**
         *
         * @param _points
         */
        public void setPoints(XYZ_D[] _points);
        /**
         *
         * @param _points
         */
        public void addPoints(XYZ_D[] _points);
        /**
         *
         * @return
         */
        public XYZ_D[] getPoints();
		
        /**
         *
         * @param _x
         * @param _y
         * @param _z
         * @param _rx
         * @param _ry
         * @param _rz
         */
        public void change(
		double _x, double _y, double _z,
		double _rx, double _ry, double _rz
	);
		
        /**
         *
         * @param _x
         * @param _y
         * @param _z
         * @param _rx
         * @param _ry
         * @param _rz
         */
        public void set(
		double _x, double _y, double _z,
		double _rx, double _ry, double _rz
	);
		
        /**
         *
         * @param _points
         */
        public void mapPoints(
		Object[] _points
	);
	
        /**
         *
         * @param _color
         */
        public void setColor(Object _color);
        /**
         *
         * @return
         */
        public Object getColor();
}

