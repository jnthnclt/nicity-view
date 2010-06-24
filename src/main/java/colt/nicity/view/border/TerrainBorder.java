/*
 * TerrainBorder.java.java
 *
 * Created on 01-03-2010 01:31:37 PM
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
package colt.nicity.view.border;

import colt.nicity.view.core.AColor;
import colt.nicity.view.interfaces.ICanvas;

/**
 *
 * @author Administrator
 */
public class TerrainBorder extends AFlaggedBorder {

    private static float x = 2;
    private static float y = 2;
    private static float w = 2;
    private static float h = 2;
    int tw;
    int th;
    float[] hue;
    float[] sat;
    float[] bri;

    /**
     *
     * @param _hue
     * @param _sat
     * @param _bri
     * @param _w
     * @param _h
     */
    public TerrainBorder(float[] _hue, float[] _sat, float[] _bri, int _w, int _h) {
        hue = _hue;
        sat = _sat;
        bri = _bri;
        tw = _w;
        th = _h;
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    @Override
    public void paintBorder(ICanvas g, int x, int y, int w, int h) {
    }

    /**
     *
     * @param _g
     * @param x
     * @param y
     * @param _w
     * @param _h
     */
    @Override
    public void paintBackground(ICanvas _g, int x, int y, int _w, int _h) {


        _g.setColor(AColor.white);
        _g.rect(true, 0, 0, _w, _h);
        _g.setColor(AColor.lightGray);
        for (int yy = 0; yy < th; yy++) {
            for (int xx = 1; xx < tw; xx++) {

                //float _hue = hue[x+(y*tw)];
                float _f = hue[(xx - 1) + (yy * tw)];
                float _t = hue[xx + (yy * tw)];
                //float _bri = bri[x+(y*tw)];

                int _y = (yy) * (_h / th);

                _g.line(x + (xx - 1) * (_w / tw), y + (int) (_f * 32) + _y, x + (xx) * (_w / tw), y + (int) (_t * 32) + _y);

            }
        }

        for (int xx = 0; xx < tw; xx++) {
            for (int yy = 1; yy < th; yy++) {

                //float _hue = hue[x+(y*tw)];
                float _f = sat[xx + ((yy - 1) * tw)];
                float _t = sat[xx + ((yy) * tw)];
                //float _bri = bri[x+(y*tw)];

                int _x = (xx) * (_w / tw);

                _g.line(x + _x + (int) (_f * 32), y + (yy - 1) * (_h / th), x + _x + (int) (_t * 32), y + yy * (_h / th));

            }
        }


        /*
        for (int x=0;x<tw;x++) {
        for (int y=0;y<th;y++) {
        float _hue = hue[x+(y*tw)];
        float _sat = sat[x+(y*tw)];
        float _bri = bri[x+(y*tw)];
        if (_sat < 0.5f) _sat = 0.5f;
        if (_bri < 0.5f) _bri = 0.5f;


        _g.setColor(new AColor(_hue,_sat,_bri));
        _g.rect(true,x*(_w/tw),y*(_h/th),(_w/tw),(_h/th));

        }
        }*/
    }

    /**
     *
     * @return
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     *
     * @return
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     *
     * @return
     */
    @Override
    public float getW() {
        return w;
    }

    /**
     *
     * @return
     */
    @Override
    public float getH() {
        return h;
    }
}
