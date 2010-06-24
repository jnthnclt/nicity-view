/*
 * UXYZ.java.java
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

import colt.nicity.core.lang.UDouble;
import colt.nicity.core.lang.UVector;
import colt.nicity.core.memory.struct.IXYZ;
import colt.nicity.core.memory.struct.XYZ_D;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class UXYZ {

    /**
     *
     * @param _xyzs
     * @param x
     * @param y
     * @param z
     * @param distance
     */
    public static void perspective(XYZ_D[] _xyzs,
            double x, double y, double z,
            double distance) {
        if (distance == 0) {
            return;
        }
        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];
            xyz.x = (xyz.x * -distance / xyz.z);
            xyz.y = -(xyz.y * distance / xyz.z);
        }
    }

    /**
     *
     * @param _xyzs
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @param distance
     */
    public static void undoPerspective(XYZ_D[] _xyzs,
            double eyeX, double eyeY, double eyeZ,
            double distance) {
        if (distance == 0) {
            return;
        }
        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];
            xyz.x = (xyz.x * xyz.z / -distance);
            xyz.y = -(xyz.y * xyz.z / distance);
        }
    }

    /**
     *
     * @param _xyz
     * @param _x
     * @param _y
     * @param _z
     */
    public static void translate(XYZ_D _xyz, double _x, double _y, double _z) { // radians; rotate around x,y,z axis
        translate(new XYZ_D[]{_xyz}, _x, _y, _z);
    }


     /**
      *
      * @param _xyzs
      * @param _x
      * @param _y
      * @param _z
      */
     public static void translate(XYZ_D[] _xyzs, double _x, double _y, double _z) {
        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];
            xyz.x += _x;
            xyz.y += _y;
            xyz.z += _z;
        }
    }


    /**
     *
     * @param _xyz
     * @param _rx
     * @param _ry
     * @param _rz
     * @param _x
     * @param _y
     * @param _z
     */
    public static void move(XYZ_D _xyz, double _rx, double _ry, double _rz, double _x, double _y, double _z) {
        move(new XYZ_D[]{_xyz}, _rx, _ry, _rz, _x, _y, _z);
    }


    /**
     *
     * @param _xyzs
     * @param _rx
     * @param _ry
     * @param _rz
     * @param _x
     * @param _y
     * @param _z
     */
    public static void move(XYZ_D[] _xyzs, double _rx, double _ry, double _rz, double _x, double _y, double _z) {// object's change in position is a function of object's rx,ry,rz
        XYZ_D delta = new XYZ_D(_x, _y, _z);
        XYZ_D.rotate(delta, _rx, _ry, _rz);
        XYZ_D.translate(_xyzs, delta.x, delta.y, delta.z);
    }


    /**
     *
     * @param _xyz
     * @param _x
     * @param _y
     * @param _z
     */
    public static void scale(XYZ_D _xyz, double _x, double _y, double _z) {
        scale(new XYZ_D[]{_xyz}, _x, _y, _z);
    }

    /**
     *
     * @param _xyzs
     * @param _x
     * @param _y
     * @param _z
     */
    public static void scale(XYZ_D[] _xyzs, double _x, double _y, double _z) {
        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];
            xyz.x *= _x;
            xyz.y *= _y;
            xyz.z *= _z;
        }
    }

    /**
     *
     * @param _xyz
     * @return
     */
    public static double getLength(XYZ_D _xyz) {
        return Math.sqrt((_xyz.x * _xyz.x) + (_xyz.y * _xyz.y) + (_xyz.z * _xyz.z));
    }

    /**
     *
     * @param _xyz
     * @param _length
     */
    public static void setLength(XYZ_D _xyz, double _length) {
        double d = UDouble.check(
                _length / Math.sqrt((_xyz.x * _xyz.x) + (_xyz.y * _xyz.y) + (_xyz.z * _xyz.z)),
                _length);
        _xyz.x *= d;
        _xyz.y *= d;
        _xyz.z *= d;
    }

    /**
     *
     * @param _xyzs
     * @param eyeRX
     * @param eyeRY
     * @param eyeRZ
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @param distance
     * @return
     */
    public static XYZ_D[] sceneToPOV(XYZ_D[] _xyzs,
            double eyeRX, double eyeRY, double eyeRZ,
            double eyeX, double eyeY, double eyeZ,
            double distance) {
      
        XYZ_D[] xyzs = getClone(_xyzs); // generate pov without modifying the _xyzs scene
        
        //!!!!! THIS WORKS! need to pass in additional desired rotation point arg
        translate(xyzs, -0, -0, -0);//!! eye's desired rotation point
        rotate(xyzs, eyeRX, eyeRY, eyeRZ);
        translate(xyzs, -eyeX, -eyeY, -eyeZ);
        //doPerspective(xyzs, eyeX, eyeY, eyeZ, distance);
        return xyzs;
    }
    

    /**
     *
     * @param _xyzs
     * @param eyeRX
     * @param eyeRY
     * @param eyeRZ
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @param distance
     * @return
     */
    public static XYZ_D[] povToScene(XYZ_D[] _xyzs,
            double eyeRX, double eyeRY, double eyeRZ,
            double eyeX, double eyeY, double eyeZ,
            double distance) {
        
        XYZ_D[] xyzs = getClone(_xyzs); // generate scene without modifying the pov
        
        //!!!!! THIS WORKS! need to pass in additional desired rotation point arg
        //undoPerspective(xyzs, eyeX, eyeY, eyeZ, distance);
        translate(xyzs, eyeX, eyeY, eyeZ);
        rotate(xyzs, -eyeRX, -eyeRY, -eyeRZ);
        translate(xyzs, 0, 0, 0);//!! eye's desired rotation point
        return xyzs;
    }
    
    // Given:
    // _lineOfSight = [0,0,-1]
    // _orientation = [0,-1,0]
    // _vector = [1,1,1]
    // return = [1,1,1]
    

    /**
     *
     * @param _lineOfSight
     * @param _orientation
     * @param _vector
     * @return
     */
    public static XYZ_D get3D(XYZ_D _lineOfSight, XYZ_D _orientation, XYZ_D _vector) {
        double vectorLength = UDouble.check(_vector.getLength(), 0);
        
        //_lineOfSight = _lineOfSight.getUnitVector();
        //_orientation = _orientation.getUnitVector();
        //_vector = _vector.getUnitVector();
        
        XYZ_D sightNormal = getPlaneNormal(_lineOfSight, _vector);
        XYZ_D orientationNormal = getPlaneNormal(_lineOfSight, _orientation);
        
        double steer = orientationNormal.getAngle(_vector);
        double role = sightNormal.getAngle(orientationNormal);
        double rotation = _lineOfSight.getAngle(_vector);
        
        double hypotenuse = UDouble.check((1 / Math.sin(rotation)), 0);
        double x = UDouble.check((Math.sin(role) / hypotenuse), 0);
        double y = -UDouble.check((Math.cos(role) / hypotenuse), 0);
        double z = -UDouble.check(Math.cos(rotation), 0);
        if (steer < UVector.cHalfPI) {
            x = -x;
        }
        
        XYZ_D xyz = new XYZ_D(x, y, z);
        xyz.setLength(vectorLength);
        return xyz;
    }

    /**
     *
     * @param _v
     * @param _v1
     * @return
     */
    public static XYZ_D getPlaneNormal(XYZ_D _v, XYZ_D _v1) {
        double x = _v.y * _v1.z - _v.z * _v1.y;
        double y = _v1.x * _v.z - _v1.z * _v.x;
        double z = _v.x * _v1.y - _v.y * _v1.x;
        return new XYZ_D(x, y, z);
    }

    /**
     *
     * @param _xyzs
     * @return
     */
    public static XYZ_D[] getClone(XYZ_D[] _xyzs) {
        XYZ_D[] results = new XYZ_D[_xyzs.length];
        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];
            results[i] = new XYZ_D(xyz.x, xyz.y, xyz.z);
        }
        return results;
    }

    /**
     *
     * @param _v
     * @return
     */
    public static int toInt(double _v) {
        return _v < 0 ? (int) (_v - .5) : (int) (_v + .5);
    }

    /**
     *
     * @param _xyz
     * @param alpha
     * @param beta
     * @param theta
     */
    public static void rotate(XYZ_D _xyz, double alpha, double beta, double theta) { // radians; rotate around x,y,z axis
        rotate(new XYZ_D[]{_xyz}, alpha, beta, theta);
    }

    // All transforms are static and optimized to manipulate arrays of XYZs
    /**
     *
     * @param _xyzs
     * @param pitch
     * @param yaw
     * @param roll
     */
    public static void rotate(Object[] _xyzs, double pitch, double yaw, double roll) { // radians; rotate around x,y,z axis

        double cosPitch = Math.cos(pitch);
        double sinPitch = Math.sin(pitch);
        double cosYaw = Math.cos(yaw);
        double sinYaw = Math.sin(yaw);
        double cosRoll = Math.cos(roll);
        double sinRoll = Math.sin(roll);

        // rotate using optimized equilavent of traditional 3-D matrix operations

        for (int i = 0; i < _xyzs.length; i++) {
            IXYZ xyz = (IXYZ) _xyzs[i];

            double _x = xyz.x();
            double _y = xyz.y();
            double _z = xyz.z();

            double xx = (cosYaw * cosRoll);
            double xy = (sinPitch * sinYaw * cosRoll) - (cosPitch * sinRoll);
            double xz = (cosPitch * sinYaw * cosRoll) + (sinPitch * sinRoll);

            double yx = (cosYaw * sinRoll);
            double yy = (sinPitch * sinYaw * sinRoll) + (cosPitch * cosRoll);
            double yz = (cosPitch * sinYaw * sinRoll) - (sinPitch * cosRoll);

            double zx = (-sinYaw);
            double zy = (sinPitch * cosYaw);
            double zz = (cosPitch * cosYaw);

            xyz.x((_x * xx) + (_y * xy) + (_z * xz));
            xyz.y((_x * yx) + (_y * yy) + (_z * yz));
            xyz.z((_x * zx) + (_y * zy) + (_z * zz));

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

    /**
     *
     * @param _xyzs
     * @param _x
     * @param _y
     * @param _z
     */
    public static void scale(Object[] _xyzs, double _x, double _y, double _z) {
        for (int i = 0; i < _xyzs.length; i++) {
            IXYZ xyz = (IXYZ) _xyzs[i];
            xyz.x(xyz.x() * _x);
            xyz.y(xyz.y() * _y);
            xyz.z(xyz.z() * _z);
        }
    }

    /**
     *
     * @param _xyzs
     */
    public static void sort(Object[] _xyzs) {
        Arrays.sort(_xyzs, new ZComparator());
    }

    /**
     *
     * @param _xyzs
     * @param eyePitch
     * @param eyeYaw
     * @param eyeRoll
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @param perspective
     */
    public static void sceneToPOV(Object[] _xyzs,
            double eyePitch, double eyeYaw, double eyeRoll,
            double eyeX, double eyeY, double eyeZ,
            double perspective) {

        for (int i = 0; i < _xyzs.length; i++) {
            IXYZScene xyz = (IXYZScene) _xyzs[i];
            xyz.x(xyz.getSceneX());
            xyz.y(xyz.getSceneY());
            xyz.z(xyz.getSceneZ());
        }

        translate(_xyzs, -eyeX, -eyeY, -eyeZ);// translation relative to eyes means subtraction
        rotate(_xyzs, eyePitch, eyeYaw, eyeRoll);
        doPerspective(_xyzs, eyeX, eyeY, eyeZ, perspective);
    }

    /**
     *
     * @param _xyzs
     * @param eyePitch
     * @param eyeYaw
     * @param eyeRoll
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @param perspective
     */
    public static void povToScene(Object[] _xyzs,
            double eyePitch, double eyeYaw, double eyeRoll,
            double eyeX, double eyeY, double eyeZ,
            double perspective) {
        undoPerspective(_xyzs, eyeX, eyeY, eyeZ, perspective);
        rotate(_xyzs, -eyePitch, -eyeYaw, -eyeRoll);
        translate(_xyzs, eyeX, eyeY, eyeZ); // translation relative to eyes means subtraction

    }

    /**
     *
     * @param _xyzs
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @param perspective
     */
    public static void doPerspective(Object[] _xyzs,
            double eyeX, double eyeY, double eyeZ,
            double perspective) {
        if (perspective == 0) {
            return;
        }
        for (int i = 0; i < _xyzs.length; i++) {
            IXYZ xyz = (IXYZ) _xyzs[i];
            xyz.x(xyz.x() * -perspective / xyz.z());
            xyz.y(-(xyz.y() * perspective / xyz.z()));
        }
    }

    /**
     *
     * @param _xyzs
     * @param eyeX
     * @param eyeY
     * @param eyeZ
     * @param perspective
     */
    public static void undoPerspective(Object[] _xyzs,
            double eyeX, double eyeY, double eyeZ,
            double perspective) {
        if (perspective == 0) {
            return;
        }
        for (int i = 0; i < _xyzs.length; i++) {
            IXYZ xyz = (IXYZ) _xyzs[i];
            xyz.x(xyz.x() * xyz.z() / -perspective);
            xyz.y(-(xyz.y() * xyz.z() / perspective));
        }
    }
}


