/*
 * U3D.java.java
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

import colt.nicity.core.lang.URandom;
import colt.nicity.core.memory.struct.IXYZ;
import colt.nicity.core.memory.struct.XYZ_D;

/**
 *
 * @author Administrator
 */
public class U3D {
  
    /**
     *
     * @param _center
     * @param _points
     * @param _radius
     */
    public static void sphere(
            Object3D _center, XYZ_D[] _points, double _radius) {
        double r2 = _radius * _radius;
        for (int i = 0; i < _points.length; i++) {
            XYZ_D p = _points[i];
            p.x = URandom.signedRand(_radius);
            double max = Math.sqrt(r2 - p.x * p.x);
            p.y = URandom.signedRand(max);
            p.z = Math.sqrt(Math.abs(r2 - p.x * p.x - p.y * p.y));
            if (i % 2 == 0) {
                p.z *= -1; // alternate
            }
        }
        _center.setPoints(_points);
    }
    
    /**
     *
     * @param _eye
     * @param _points
     * @param _depthOfField
     */
    public static void perspective(Object3D _eye, Object[] _points, double _depthOfField) {
        for (int k = 0; k < _points.length; k++) {
            IXYZ p = (IXYZ) _points[k];
            double lenX = p.x() - _eye.center.x;
            double lenY = p.y() - _eye.center.y;
            double lenZ = p.z() - _eye.center.z;
            if (lenZ <= 0) {
                continue; //!! kiss
            }
            double len = Math.sqrt((lenX * lenX) + (lenY * lenY) + (lenZ * lenZ));
            p.x(p.x() * (_depthOfField / len));
            p.y(p.y() * (_depthOfField / len));
            p.z(p.z() * (_depthOfField / len));//??
        }
    }
    
    /**
     *
     * @param _xyzs
     * @param _x
     * @param _y
     * @param _z
     */
    public static void translate(Object[] _xyzs, double _x, double _y, double _z) {
        for (int i = 0; i < _xyzs.length; i++) {
            IXYZ xyz = (IXYZ) _xyzs[i];
            xyz.x(xyz.x() + _x);
            xyz.y(xyz.y() + _y);
            xyz.z(xyz.z() + _z);
        }
    }
}
