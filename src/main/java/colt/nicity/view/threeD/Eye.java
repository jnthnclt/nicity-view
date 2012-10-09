/*
 * Eye.java.java
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
import colt.nicity.core.memory.struct.XYZ_D;
import colt.nicity.core.time.UTime;

/**
 *
 * @author Administrator
 */
public class Eye {

    /**
     *
     */
    public double tx = 0;
    /**
     *
     */
    public double ty = 0;
    /**
     *
     */
    public double tz = 0;
    /**
     *
     */
    public double x = 0;
    /**
     *
     */
    public double y = 0;
    /**
     *
     */
    public double z = -1000;
    /**
     *
     */
    public double rx = 0;
    /**
     *
     */
    public double ry = 0;
    /**
     *
     */
    public double rz = 0;
    private double focus = -1000;
    boolean eyeCentric = true;
    long time = System.currentTimeMillis();

    /**
     *
     */
    public Eye() {
        time = System.currentTimeMillis();
    }

    /**
     *
     * @param _x
     * @param _y
     * @param _z
     * @param _rx
     * @param _ry
     * @param _rz
     * @param _focus
     */
    public Eye(
            double _x, double _y, double _z,
            double _rx, double _ry, double _rz, double _focus) {
        time = System.currentTimeMillis();
        x = _x;
        y = _y;
        z = _z;
        rx = _rx;
        ry = _ry;
        rz = _rz;
        focus = _focus;
    }

    /**
     *
     * @param _x
     * @param _y
     * @param _z
     */
    public void move(double _x, double _y, double _z) {
        if (eyeCentric) {
            x += _x;
            y += _y;
            z += _z;
        } else {
            ;
        }
    }

    /**
     *
     * @param _rx
     * @param _ry
     * @param _rz
     */
    public void look(double _rx, double _ry, double _rz) {
        if (eyeCentric) {
            rx += _rx;
            ry += _ry;
            rz += _rz;
        } else {
            ;
        }
    }

    /**
     *
     * @param _eyeCentric
     */
    public void setEyeCentric(boolean _eyeCentric) {
        eyeCentric = _eyeCentric;
    }

    /**
     *
     * @param _focus
     */
    public void setPerspective(double _focus) {
        focus = _focus;
    }

    /**
     *
     * @return
     */
    public double getPerspective() {
        return focus;
    }

    /**
     *
     * @param _xyzs
     * @return
     */
    public XYZ_D[] sceneToPOV(XYZ_D[] _xyzs) {
        if (eyeCentric) {
            XYZ_D[] xyzs = UXYZ.sceneToPOV(_xyzs, rx, ry, rz, x, y, z, focus);
            return xyzs;
        } else {
            XYZ_D target = getTarget();
            XYZ_D[] xyzs = UXYZ.sceneToPOV(_xyzs, rx, ry, rz, target.x, target.y, target.z, focus);
            return xyzs;
        }
    }

    /**
     *
     * @param _xyzs
     * @return
     */
    public XYZ_D[] povToScene(XYZ_D[] _xyzs) {
        if (eyeCentric) {
            UXYZ.translate(_xyzs, x, y, z);
            XYZ_D[] xyzs = UXYZ.povToScene(_xyzs, rx, ry, rz, x, y, z, focus);
            return xyzs;
        } else {
            XYZ_D target = getTarget();
            UXYZ.translate(_xyzs, x, y, z);
            XYZ_D[] xyzs = UXYZ.povToScene(_xyzs, rx, ry, rz, target.x, target.y, target.z, focus);
            return xyzs;
        }
    }

    /**
     *
     * @param _xyzs
     */
    public void sceneToPOV(Object[] _xyzs) {
        if (eyeCentric) {
            UXYZ.sceneToPOV(_xyzs, rx, ry, rz, x, y, z, focus);
        } else {
            XYZ_D target = getTarget();
            UXYZ.sceneToPOV(_xyzs, rx, ry, rz, target.x, target.y, target.z, focus);
        }
    }

    /**
     *
     * @param _xyzs
     */
    public void povToScene(Object[] _xyzs) {
        if (eyeCentric) {
            UXYZ.translate(_xyzs, -x, -y, -z);
            UXYZ.povToScene(_xyzs, rx, ry, rz, x, y, z, focus);
        } else {
            XYZ_D target = getTarget();
            UXYZ.translate(_xyzs, -x, -y, -z);
            UXYZ.povToScene(_xyzs, rx, ry, rz, target.x, target.y, target.z, focus);
        }
    }

    /**
     *
     * @param _time
     */
    public void setTime(long _time) {
        time = _time;
    }

    /**
     *
     * @return
     */
    public long getTime() {
        return time;
    }

    /**
     *
     * @return
     */
    public XYZ_D getEye() {
        return new XYZ_D(x, y, z);
    }

    /**
     *
     * @return
     */
    public XYZ_D getTarget() {
        XYZ_D target = getEye();
        UXYZ.translate(target, 0, 0, 0);
        return target;
    }

    /**
     *
     * @param _target
     */
    public void setTarget(XYZ_D _target) {
        UXYZ.setLength(_target, focus);
        UXYZ.rotate(_target, -rx, -ry, -rz);
        x = _target.x;
        y = _target.y;
        z = _target.z;
    }

    /**
     *
     */
    public void ortho() {
        focus = 0;
    }

    /**
     *
     */
    public void reset() {
        x = 0;
        y = 0;
        z = 0;
        rx = 0;
        ry = 0;
        rz = 0;
        focus = 0;
    }

    @Override
    public String toString() {
        XYZ_D target = getTarget();
        return "       p:" + UDouble.trimPrecision(Math.toDegrees(getEye().getAngle(XYZ_D.cY)), 2)
                + " y:" + UDouble.trimPrecision(Math.toDegrees(getEye().getAngle(XYZ_D.cX)), 2)
                + " r:" + UDouble.trimPrecision(Math.toDegrees(getEye().getAngle(XYZ_D.cZ)), 2)
                + " " + UTime.fixedWidthDate(time)
                + "       sx:" + UDouble.trimPrecision(x, 2)
                + " sy:" + UDouble.trimPrecision(y, 2)
                + " sz:" + UDouble.trimPrecision(z, 2)
                + "       ex:" + UDouble.trimPrecision(target.x, 2)
                + " ey:" + UDouble.trimPrecision(target.y, 2)
                + " ez:" + UDouble.trimPrecision(target.z, 2)
                + "       rx:" + Math.toDegrees(UDouble.trimPrecision(rx, 2))
                + " ry:" + Math.toDegrees(UDouble.trimPrecision(ry, 2))
                + " rz:" + Math.toDegrees(UDouble.trimPrecision(rz, 2))
                + "       angle:" + focus
                + "";
    }

    /**
     *
     */
    public void iso() {
        rx = Math.toRadians(45);
        ry = Math.toRadians(45);
        rz = 0;
    }

    /**
     *
     */
    public void front() {
        rx = 0;
        ry = 0;
        rz = 0;
    }

    /**
     *
     */
    public void back() {
        rx = 0;
        ry = Math.toRadians(180);
        rz = 0;
    }

    /**
     *
     */
    public void top() {
        rx = Math.toRadians(90);
        ry = 0;
        rz = 0;
    }

    /**
     *
     */
    public void bottom() {
        rx = Math.toRadians(-90);
        ry = 0;
        rz = 0;
    }

    /**
     *
     */
    public void right() {
        rx = 0;
        ry = Math.toRadians(90);
        rz = 0;
    }

    /**
     *
     */
    public void left() {
        rx = 0;
        ry = Math.toRadians(-90);
        rz = 0;
    }
}
