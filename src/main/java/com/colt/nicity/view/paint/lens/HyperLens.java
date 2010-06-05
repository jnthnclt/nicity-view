/*
 * HyperLens.java.java
 *
 * Created on 01-24-2010 10:07:56 PM
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
package com.colt.nicity.view.paint.lens;

import com.colt.nicity.core.memory.struct.XY_D;

/**
 *
 * @author Administrator
 */
public class HyperLens extends ALens {

    private double hyperSB;// min max 0->108 ??

    /**
     *
     * @param p
     */
    @Override
    public void applyLens(XY_D p) {
        double hyperV = hyperSB;
        double dist = Math.sqrt(p.x * p.x + p.y * p.y);
        if (dist > 0) {
            p.x = p.x / dist * hyperDist(hyperV, dist);
            p.y = p.y / dist * hyperDist(hyperV, dist);
        } else {
            p.x = 0;
            p.y = 0;
        }
    }

    /**
     *
     * @param p
     */
    @Override
    public void undoLens(XY_D p) {
        double hyperV = hyperSB;
        updateInverseArray(hyperV);
        double dist = Math.sqrt(p.x * p.x + p.y * p.y);
        if (dist > 0) {
            p.x = p.x / dist * invHyperDist(dist);
            p.y = p.y / dist * invHyperDist(dist);
        } else {
            p.x = 0;
            p.y = 0;
        }
    }
    private double width = 1000;//!! hack
    private double inverseArray[] = new double[200]; //Helps calculate the inverse of the Hyperbolic function

    private void updateInverseArray(double hyperV) {
        double x;
        for (int i = 0; i < 200; i++) {
            x = width * i / 200; //Points within a radius of 'width' will have exact inverses.
            inverseArray[i] = hyperDist(hyperV, x);
        }
    }

    ;

    private double invHyperDist(double dist) { //The inverse of hyperDist
        if (dist == 0) {
            return 0;
        }
        int i;
        if (inverseArray[199] < dist) {
            i = 199;
        } else {
            i = findInd(0, 199, dist);
        }
        double x2 = inverseArray[i];
        double x1 = inverseArray[i - 1];
        double j = (dist - x1) / (x2 - x1);
        return (((double) i + j - 1) / 200.0 * width);
    }

    private int findInd(int min, int max, double dist) {
        int mid = (min + max) / 2;
        if (inverseArray[mid] < dist) {
            if (max - mid == 1) {
                return max;
            } else {
                return findInd(mid, max, dist);
            }
        } else if (mid - min == 1) {
            return mid;
        } else {
            return findInd(min, mid, dist);
        }
    }

    private double hyperDist(double hyperV, double dist) {
        //Points that are 250 away from the center stay fixed.
        double hyperD = rawHyperDist(hyperV, dist) / rawHyperDist(hyperV, 250) * 250;
        double fade = hyperV;
        double fadeAdjust = 100;
        hyperD = hyperD * fade / fadeAdjust + dist * (fadeAdjust - fade) / fadeAdjust;
        return hyperD;

    }

    private double rawHyperDist(double hyperV, double dist) {  //The hyperbolic transform
        if (hyperV == 0) {
            return dist;
        }
        return Math.log(dist / (Math.pow(1.5, (70 - hyperV) / 40) * 80) + 1);
    }
}
