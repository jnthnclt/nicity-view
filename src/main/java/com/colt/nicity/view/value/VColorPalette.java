/*
 * VColorPalette.java.java
 *
 * Created on 03-12-2010 06:41:14 PM
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
package com.colt.nicity.view.value;

import com.colt.nicity.view.border.ItemBorder;
import com.colt.nicity.view.list.AItem;
import com.colt.nicity.view.list.VGrid;
import com.colt.nicity.core.collection.CSet;
import com.colt.nicity.core.collection.IHaveCount;
import com.colt.nicity.core.comparator.AValueComparator;
import com.colt.nicity.core.comparator.UValueComparator;
import com.colt.nicity.core.lang.IOut;
import com.colt.nicity.core.lang.MinMaxDouble;
import com.colt.nicity.core.value.IValue;
import com.colt.nicity.view.core.AColor;
import com.colt.nicity.view.core.RigidBox;
import com.colt.nicity.view.core.UV;
import com.colt.nicity.view.core.VChain;
import com.colt.nicity.view.core.VPan;
import com.colt.nicity.view.core.VString;
import com.colt.nicity.view.interfaces.IView;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class VColorPalette extends AItem {

    IValue value;
    Object title;
    float jw;
    float jh;
    CSet colors = new CSet();
    int hResolution = 16;
    int sResolution = 16;
    int bResolution = 16;

    /**
     *
     * @param _value
     * @param _title
     * @param _w
     * @param _h
     * @param _hResolution
     * @param _sResolution
     * @param _bResolution
     * @param _init
     */
    public VColorPalette(
            IValue _value, Object _title, float _w, float _h,
            int _hResolution, int _sResolution, int _bResolution,
            boolean _init) {
        value = _value;
        title = _title;
        jw = _w;
        jh = _h;
        hResolution = _hResolution;
        sResolution = _sResolution;
        bResolution = _bResolution;
        if (_init) {
            init();
        }
    }

    /**
     *
     * @param _colors
     */
    public void setColors(Object[] _colors) {
        colors.add(_colors);
    }

    /**
     *
     */
    public void reset() {
        total = 0;
        colors.removeAll();
    }
    int total = 0;

    /**
     *
     * @param _rgba
     */
    synchronized public void addColor(int _rgba) {
        total++;
        AColor color = color(_rgba, hResolution, sResolution, bResolution);
        PaletteColor pcolor = (PaletteColor) colors.get(color);
        if (pcolor == null) {
            pcolor = new PaletteColor(color);
            colors.add(pcolor);
        }
        pcolor.inc();
    }

    /**
     *
     * @return
     */
    public PaletteColor[] palette() {
        return (PaletteColor[]) colors.toArray(PaletteColor.class);
    }

    /**
     *
     * @param _rgba
     * @param _hResolution
     * @param _sResolution
     * @param _bResolution
     * @return
     */
    public static AColor color(int _rgba, int _hResolution, int _sResolution, int _bResolution) {

        AColor source = new AColor(_rgba);

        // the number of _hue distinctions should deminish as the colors brightness goes down
        // we will be linear for now
        // the number of _hue distinctions should also deminish as the saturation drops
        // we also be linear for now
        // hueResolution will be full when sat + bri == 2;


        float hue = source.getHue();
        float sat = source.getSaturation();
        float bri = source.getBrightness();

        _hResolution = (int) (((float) _hResolution) * ((sat + bri) / 2));
        if (_hResolution == 0) {
            _hResolution = 1;
        }

        hue = (float) ((int) (hue * _hResolution)) / (float) _hResolution;

        sat = (float) ((int) (sat * _sResolution)) / (float) _sResolution;

        bri = (float) ((int) (bri * _bResolution)) / (float) _bResolution;


        AColor color = new AColor(hue, sat, bri);
        return color;
    }

    /**
     *
     * @param _
     * @param _count
     */
    public void condense(IOut _, int _count) {
        MinMaxDouble mmd = new MinMaxDouble();
        float[] ahsb = new float[3];
        float[] bhsb = new float[3];

        while (colors.getCount() > _count) {
            mmd.reset();
            _.out(colors.getCount() + "->" + _count);
            _.out(_count, colors.getCount());

            Object[] all = colors.getAll(Object.class);
            java.util.Arrays.sort(all, UValueComparator.haveCount(AValueComparator.cAscending));

            for (int i = 1; i < all.length; i++) {
                PaletteColor a = (PaletteColor) all[i - 1];
                PaletteColor b = (PaletteColor) all[i];

                double c = AColor.correlateHSB(a.color.intValue(), b.color.intValue(), ahsb, bhsb);
                c *= 1d - Math.pow(((double) (a.count + b.count) / (double) total), 0.125d);
                mmd.value(c);
            }
            PaletteColor a = (PaletteColor) all[mmd.maxIndex];
            PaletteColor b = (PaletteColor) all[mmd.maxIndex + 1];
            b.count += a.count;
            colors.remove(a);
        }
    }

    /**
     *
     * @param _
     * @param _minNumberOfOccurance
     * @param _paletteSize
     * @return
     */
    public VColorPalette.PaletteColor[] clean(IOut _, int _minNumberOfOccurance, int _paletteSize) {

        this.clean(_, 0d, _minNumberOfOccurance);
        VColorPalette.PaletteColor[] colors = this.palette();
        Arrays.sort(colors, new AValueComparator(false) {
            public Object value(Object _value) {
                VColorPalette.PaletteColor paletteColor = (VColorPalette.PaletteColor) _value;
                return new Long(paletteColor.getCount());
            }
        });

        CSet keep = new CSet();
        CSet join = new CSet();
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].popularity() > 0.01d) {
                keep.add(colors[i]);
            } else {
                join.add(colors[i]);
            }
        }
        this.condense(keep.getAll(Object.class), join.getAll(Object.class));
        colors = (VColorPalette.PaletteColor[]) keep.toArray(VColorPalette.PaletteColor.class);
        Arrays.sort(colors, new AValueComparator(false) {

            public Object value(Object _value) {
                VColorPalette.PaletteColor paletteColor = (VColorPalette.PaletteColor) _value;
                return new Long(paletteColor.getCount());
            }
        });

        keep = new CSet();
        join = new CSet();
        for (int i = 0; i < colors.length; i++) {
            if (keep.getCount() < _paletteSize) {
                keep.add(colors[i]);
            } else {
                join.add(colors[i]);
            }
        }
        this.condense(keep.getAll(Object.class), join.getAll(Object.class));
        colors = (VColorPalette.PaletteColor[]) keep.toArray(VColorPalette.PaletteColor.class);
        return colors;
    }

    /**
     *
     * @param _
     * @param _cutoff
     * @param _count
     */
    public void clean(IOut _, double _cutoff, int _count) {
        double maxPopularity = 0;
        Object[] all = colors.getAll(Object.class);
        for (Object a : all) {
            PaletteColor pc = (PaletteColor) a;
            if (pc.popularity() > maxPopularity) {
                maxPopularity = pc.popularity();
            }
        }
        for (Object a : all) {
            PaletteColor pc = (PaletteColor) a;
            pc.init(maxPopularity);
            if (pc.popularity() < _cutoff
                    || pc.count < _count) {
                colors.remove(pc);
                continue;
            }
        }
    }

    /**
     *
     * @param _rgba
     * @return
     */
    public int lualpha(int _rgba) {
        AColor color = color(_rgba, hResolution, sResolution, bResolution);
        PaletteColor pcolor = (PaletteColor) colors.get(color);
        if (pcolor == null) {
            return AColor.rgbaToInt(0, 0, 0, 0);
        }
        return _rgba;
    }

    /**
     *
     * @param _rgba
     * @return
     */
    public int lucolor(int _rgba) {
        AColor color = color(_rgba, hResolution, sResolution, bResolution);
        PaletteColor pcolor = (PaletteColor) colors.get(color);
        if (pcolor == null) {
            return bestMatch(_rgba, colors.getAll(Object.class)).color.intValue();
        }
        return pcolor.color.intValue();
    }

    /**
     *
     * @param _rgba
     * @param all
     * @return
     */
    public PaletteColor bestMatch(int _rgba, Object[] all) {
        MinMaxDouble mmd = new MinMaxDouble();
        for (int i = 0; i < all.length; i++) {
            PaletteColor b = (PaletteColor) all[i];
            if (b.color.intValue() == _rgba) {
                continue;
            }
            mmd.value(AColor.correlateHSB(_rgba, b.color.intValue()));
        }
        int index = mmd.maxIndex;
        if (index > -1 && index < all.length) {
            return (PaletteColor) all[index];
        }
        return null;
    }

    /**
     *
     * @param _keep
     * @param _merge
     */
    public void condense(Object[] _keep, Object[] _merge) {
        for (int i = 0; i < _merge.length; i++) {
            PaletteColor merge = (PaletteColor) _merge[i];
            PaletteColor keep = bestMatch(merge.color.toInt(), _keep);
            if (keep != null) {
                keep.count += merge.count;
            }
        }

        for (Object a : _keep) {
            ((PaletteColor) a).maxPopularity = 0;
        }
        double maxPopularity = 0;
        for (Object a : _keep) {
            PaletteColor pc = (PaletteColor) a;
            if (pc.popularity() > maxPopularity) {
                maxPopularity = pc.popularity();
            }
        }
        for (Object a : _keep) {
            ((PaletteColor) a).maxPopularity = maxPopularity;
        }

        java.util.Arrays.sort(_keep, UValueComparator.haveCount(AValueComparator.cAscending));

    }

    @Override
    public String toString() {
        return title.toString();
    }

    /**
     *
     */
    public void init() {
        VChain c = new VChain(UV.cSN);
        VGrid colorsList = new VGrid(colors, -1);

        c.add(new VPan(colorsList, jw, jh));
        setContent(c);
    }

    /**
     *
     */
    public class PaletteColor extends AItem implements IHaveCount {

        /**
         *
         */
        public AColor color;
        /**
         *
         */
        public int count;
        /**
         *
         */
        public double maxPopularity = 0;

        /**
         *
         * @param _color
         */
        public PaletteColor(AColor _color) {
            color = _color;
        }

        /**
         *
         * @param _maxPopularity
         */
        public void init(double _maxPopularity) {
            maxPopularity = _maxPopularity;
            IView vColor = new VChain(UV.cCC, new VString(this), new RigidBox((int) (400 * (popularity())), 20));
            vColor.setBorder(new ItemBorder(color));
            setContent(vColor);
            layoutInterior();
            paint();

        }

        /**
         *
         * @return
         */
        public Object getValue() {
            return color;
        }

        // IHaveCount
        public long getCount() {
            return count;
        }

        /**
         *
         * @return
         */
        public double popularity() {
            if (maxPopularity == 0) {
                return ((double) count / (double) total);
            } else {
                return ((double) count / (double) total) / maxPopularity;
            }
        }

        public String toString() {
            return "" + (int) (popularity() * 100) + "%";
        }

        /**
         *
         */
        public void inc() {
            count++;
        }
    }
}
