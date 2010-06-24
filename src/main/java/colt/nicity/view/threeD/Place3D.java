/*
 * Place3D.java.java
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

import colt.nicity.core.lang.ASetObject;
import colt.nicity.core.lang.UDouble;
import colt.nicity.core.lang.UVector;
import colt.nicity.core.memory.struct.XYZ_D;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class Place3D extends ASetObject implements Comparable {

    private XYZ_D location;//absolute
    private XYZ_D xAxis;
    private XYZ_D yAxis;
    private XYZ_D zAxis;

    /**
     *
     */
    public Place3D() {
        this(new XYZ_D(0, 0, 0), new XYZ_D(1, 0, 0), new XYZ_D(0, 1, 0), new XYZ_D(0, 0, 1));
    }

    private Place3D(XYZ_D _location, XYZ_D _xAxis, XYZ_D _yAxis, XYZ_D _zAxis) {
        location = _location;
        xAxis = _xAxis;
        yAxis = _yAxis;
        zAxis = _zAxis;
    }

    /**
     *
     * @return
     */
    public Object hashObject() {
        return this;
    }

    @Override
    public int hashCode() {
        return (int) ((location.hashCode()
                + xAxis.hashCode()
                + yAxis.hashCode()
                + zAxis.hashCode()) * Integer.MAX_VALUE);
    }

    public boolean equals(Object _instance) {
        if (_instance instanceof Place3D) {
            Place3D place3D = (Place3D) _instance;
            return (location.equals(place3D.location)
                    && xAxis.equals(place3D.xAxis)
                    && yAxis.equals(place3D.yAxis)
                    && zAxis.equals(place3D.zAxis));
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Place3D getClone() {
        return new Place3D(location.getClone(), xAxis.getClone(), yAxis.getClone(), zAxis.getClone());
    }

    public String toString() {
        return "XYZ=" + location + "," + xAxis + "," + yAxis + "," + zAxis;
    }

    // Comparable
    public int compareTo(Object otherPlace3D) {
        double thisVal = location.z;
        double otherVal = ((Place3D) otherPlace3D).location.z;
        return (otherVal < thisVal ? -1 : (otherVal == thisVal ? 0 : 1));
    }

    /**
     *
     * @param _angle
     */
    public void pitch(double _angle) { //X in radians
        ;
    }

    /**
     *
     * @param _angle
     */
    public void role(double _angle) { //Z in radians
        ;
    }

    /**
     *
     * @param _angle
     */
    public void yaw(double _angle) { //Y in radians
        ;
    }

    /**
     *
     */
    public void transform() {
        ;
    }

    // All transforms are static and optimized to manipulate arrays of XYZs
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

    /**
     *
     * @param _xyzs
     * @param alpha
     * @param beta
     * @param theta
     */
    public static void rotate(XYZ_D[] _xyzs, double alpha, double beta, double theta) { // radians; rotate around x,y,z axis

        double cosAlpha = Math.cos(alpha);
        double sinAlpha = Math.sin(alpha);
        double cosBeta = Math.cos(beta);
        double sinBeta = Math.sin(beta);
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        // rotate using optimized equilavent of traditional 3-D matrix operations

        for (int i = 0; i < _xyzs.length; i++) {
            XYZ_D xyz = _xyzs[i];

            double _x = xyz.x;
            double _y = xyz.y;
            double _z = xyz.z;

            double xx = (cosBeta * cosTheta);
            double xy = (sinAlpha * sinBeta * cosTheta) - (cosAlpha * sinTheta);
            double xz = (cosAlpha * sinBeta * cosTheta) + (sinAlpha * sinTheta);

            double yx = (cosBeta * sinTheta);
            double yy = (sinAlpha * sinBeta * sinTheta) + (cosAlpha * cosTheta);
            double yz = (cosAlpha * sinBeta * sinTheta) - (sinAlpha * cosTheta);

            double zx = (-sinBeta);
            double zy = (sinAlpha * cosBeta);
            double zz = (cosAlpha * cosBeta);

            xyz.x = (_x * xx) + (_y * xy) + (_z * xz);
            xyz.y = (_x * yx) + (_y * yy) + (_z * yz);
            xyz.z = (_x * zx) + (_y * zy) + (_z * zz);

        }
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
     */
    public static void sort(XYZ_D[] _xyzs) {
        Arrays.sort(_xyzs);
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

        XYZ_D[] xyzs = UXYZ.getClone(_xyzs); // generate pov without modifying the _xyzs scene

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

        XYZ_D[] xyzs = UXYZ.getClone(_xyzs); // generate scene without modifying the pov

        //!!!!! THIS WORKS! need to pass in additional desired rotation point arg
        //undoPerspective(xyzs, eyeX, eyeY, eyeZ, distance);
        translate(xyzs, eyeX, eyeY, eyeZ);
        rotate(xyzs, -eyeRX, -eyeRY, -eyeRZ);
        translate(xyzs, 0, 0, 0);//!! eye's desired rotation point
        return xyzs;
    }

    //  Static Methods
    // gets cross product
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
     * @param _v
     * @param _v1
     * @return
     */
    public static XYZ_D getBisector(XYZ_D _v, XYZ_D _v1) {
        double x = _v.x + _v1.x;
        double y = _v.y + _v1.y;
        double z = _v.z + _v1.z;
        return new XYZ_D(x, y, z);
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
    public static XYZ_D getXYZ(XYZ_D _lineOfSight, XYZ_D _orientation, XYZ_D _vector) {
        double vectorLength = UDouble.check(_vector.getLength(), 0);

        _lineOfSight = _lineOfSight.getUnitVector();
        _orientation = _orientation.getUnitVector();
        _vector = _vector.getUnitVector();

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
}

