/*
 * Object3D.java.java
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
package com.colt.nicity.view.threeD;

import com.colt.nicity.core.memory.struct.IXYZ;
import com.colt.nicity.core.memory.struct.XYZ_D;
//import adk.view.core.AColor;
/**
 *
 * @author Administrator
 */
public class Object3D implements IObject3D, Comparable {

    // An Object3D is a center point, three angles, and a collection of XYZ originalPoints that move together as one object.
    // The originalPoints are fixed relative to the moveable center of gravity, and relative to the three changable angles
    // of orientation: pitch, yaw, roll, as in an airplane. They correspond to rotation of the x,y,z axis.
    //private static final AColor defaultColor = new AColor(0,0,0);
    // read-only access to radians is allowed for efficient transforms; for degrees, use get and set
    double rx = 0;	// pitch
    double ry = 0;	// yaw
    double rz = 0;	// roll
    XYZ_D center;
    double focus = 0;
    Object color;
    private XYZ_D[] originalPoints; // constructed with read-only points relative to center and angles
    private XYZ_D[] orientedPoints; // all requests for points returns a clone of orientedPoints
    private boolean needsTranslation;
    private boolean needsRotation;

    /**
     *
     * @param _center
     */
    public Object3D(
            XYZ_D _center) {
        this(_center, 0, 0, 0, null);
    }

    /**
     *
     * @param _center
     * @param _pitch
     * @param _yaw
     * @param _roll
     * @param _points
     */
    public Object3D(
            XYZ_D _center,
            double _pitch, double _yaw, double _roll,
            XYZ_D[] _points) {
        rx = Math.toRadians(_pitch % 360);
        ry = Math.toRadians(_yaw % 360);
        rz = Math.toRadians(_roll % 360);
        center = _center;
        if (center == null) {
            center = new XYZ_D(0, 0, 0);
        }
        originalPoints = _points;
        if (originalPoints == null) {
            originalPoints = new XYZ_D[0];
        }
        //color = defaultColor;
        needsTranslation = true;
        needsRotation = true;
    }

    /**
     *
     * @return
     */
    public int getCount() {
        return originalPoints.length;
    }

    private void orientPoints() {
        XYZ_D[] points = UXYZ.getClone(originalPoints);

        UXYZ.rotate(points, rx, ry, rz); // rel to object's orientation
        UXYZ.translate(points, center.x, center.y, center.z); // return rel to center

//System.out.println(points[0]+"  "+points[1]+" c="+center);		

        needsTranslation = false;
        needsRotation = false;
        orientedPoints = points;
    }

    // IObject interface
    /**
     *
     * @param _x
     * @param _y
     * @param _z
     * @param _rx
     * @param _ry
     * @param _rz
     */
    public void change(double _x, double _y, double _z, double _rx, double _ry, double _rz) {
//System.out.println(_x+" "+_y+" "+_z+" "+_rx+","+_ry+","+_rz);
        //!! look where you are going => change angles first, then change coordinates
        rx += _rx;
        ry += _ry;
        rz += _rz;
        XYZ_D delta = new XYZ_D(_x, _y, _z);
        XYZ_D.rotate(delta, rx, ry, rz);
        center.x += delta.x;
        center.y += delta.y;
        center.z += delta.z;
    }

    /**
     *
     * @param _x
     * @param _y
     * @param _z
     * @param _rx
     * @param _ry
     * @param _rz
     */
    public void set(double _x, double _y, double _z, double _rx, double _ry, double _rz) {
        rx = _rx;
        ry = _ry;
        rz = _rz;
        center.x = _x;
        center.y = _y;
        center.z = _z;
    }

    /**
     *
     * @param _center
     */
    public void setCenter(XYZ_D _center) {
        center = _center;
        needsTranslation = true;
    }

    /**
     *
     * @return
     */
    public XYZ_D getCenter() {
        if (center == null) {
            center = new XYZ_D(0, 0, 0);
        }
        return center;
    }

    /**
     *
     * @param _pitch
     */
    public void setPitch(double _pitch) {
        rx = Math.toRadians(_pitch % 360);
        needsRotation = true;
    }

    /**
     *
     * @return
     */
    public double getPitch() {
        return Math.toDegrees(rx) % 360;
    }

    /**
     *
     * @param _yaw
     */
    public void setYaw(double _yaw) {
        ry = Math.toRadians(_yaw % 360);
        needsRotation = true;
    }

    /**
     *
     * @return
     */
    public double getYaw() {
        return Math.toDegrees(ry) % 360;
    }

    /**
     *
     * @param _roll
     */
    public void setRoll(double _roll) {
        rz = Math.toRadians(_roll % 360);
        needsRotation = true;
    }

    /**
     *
     * @return
     */
    public double getRoll() {
        return Math.toDegrees(rz) % 360;
    }

    /**
     *
     * @param _points
     */
    public void setPoints(XYZ_D[] _points) {
        originalPoints = _points;
        needsRotation = true;
        needsTranslation = true;
    }

    /**
     *
     * @return
     */
    public XYZ_D[] getPoints() {
        //if (needsTranslation || needsRotation) {
        if (true) {//!! optimize after debugged
            orientPoints();
        }
        return UXYZ.getClone(orientedPoints);
    }

    /**
     *
     * @param _pov
     * @return
     */
    public XYZ_D[] getPointsRelativeTo(Object3D _pov) {
        XYZ_D[] points = UXYZ.getClone(originalPoints);

        XYZ_D povCenter = _pov.getCenter();
        XYZ_D myCenter = new XYZ_D(center.x, center.y, center.z);

        UXYZ.rotate(points, rx, ry, rz);
        UXYZ.translate(points, myCenter.x - povCenter.x, myCenter.y - povCenter.y, myCenter.z - povCenter.z);
        UXYZ.perspective(points, _pov.center.x, _pov.center.y, _pov.center.z, _pov.focus);
        UXYZ.rotate(points, -_pov.rx, -_pov.ry, -_pov.rz);

        return points;
    }

    /**
     *
     * @param _map
     */
    public void mapPoints(Object[] _map) {
        if (true) {//!! optimize after debugged
            orientPoints();
        }
        for (int i = 0; i < orientedPoints.length; i++) {
            ((IXYZ) _map[i]).x(orientedPoints[i].x);
            ((IXYZ) _map[i]).y(orientedPoints[i].y);
            ((IXYZ) _map[i]).z(orientedPoints[i].z);
        }
    }

    /**
     *
     * @param _points
     */
    public void addPoints(XYZ_D[] _points) {
        XYZ_D[] newPoints = new XYZ_D[originalPoints.length + _points.length];
        System.arraycopy(originalPoints, 0, newPoints, 0, originalPoints.length);
        System.arraycopy(_points, 0, newPoints, originalPoints.length, _points.length);
        needsRotation = true;
        needsTranslation = true;
        originalPoints = newPoints;
    }

    /**
     *
     * @param _color
     */
    public void setColor(Object _color) {
        color = _color;
    }

    /**
     *
     * @return
     */
    public Object getColor() {
        return color;
    }

    /**
     *
     */
    public void debug() {
        System.out.println("----- " + toString());
        for (int i = 0; i < orientedPoints.length; i++) {
            System.out.println(i + " = " + orientedPoints[i].toString());
        }
        System.out.println("-----");
    }

    public String toString() {
        return "at:" + center.getIntX() + "," + center.getIntY() + "," + center.getIntZ()
                + "; pyr:" + (int) getPitch() + "," + (int) getYaw() + "," + (int) getRoll();
    }

    // Comparable
    public int compareTo(Object otherObject3D) {
        double thisZ = this.center.z();
        double otherZ = ((Object3D) otherObject3D).center.z();
        return (otherZ < thisZ ? -1 : (otherZ == thisZ ? 0 : 1));
    }
}
