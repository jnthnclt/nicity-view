/*
 * VFontBrowser.java.java
 *
 * Created on 03-12-2010 06:39:47 PM
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
package colt.nicity.view.value;

import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.list.VItem;
import colt.nicity.view.list.VList;
import colt.nicity.core.collection.CArray;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.EditString;
import colt.nicity.view.core.MaintainViewer;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VPan;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.ViewString;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IVItem;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

/**
 *
 * @author Administrator
 */
public class VFontBrowser extends Viewer {

    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        ViewColor.onGray();
        UV.exitFrame(new VFontBrowser(new Value(UV.fonts[UV.cText])), "Font Browser");
    }
    /**
     *
     */
    protected static Font[] allFonts = null;
    /**
     *
     */
    protected static CArray viewAllStyles = new CArray(ViewString.class);
    /**
     *
     */
    protected static CArray viewAllSizes = new CArray(ViewString.class);
    /**
     *
     */
    protected static CArray viewAllFonts = null;

    static {
        for (int i = 6; i < 128; i += 2) {
            viewAllSizes.insertLast(new ViewString("" + i));
        }
        viewAllStyles.insertLast(new ViewString("Plain"));
        viewAllStyles.insertLast(new ViewString("Bold"));
        viewAllStyles.insertLast(new ViewString("Italic"));
        viewAllStyles.insertLast(new ViewString("Bold Italic"));
    }

    /**
     *
     * @param _style
     * @param _size
     * @return
     */
    static public CArray allFonts(int _style, int _size) {
        if (viewAllFonts != null) {
            return viewAllFonts;
        }
        if (allFonts == null) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            allFonts = ge.getAllFonts();
        }
        viewAllFonts = new CArray(ViewString.class);
        for (int i = 0; i < allFonts.length; i++) {
            viewAllFonts.insertLast(new ViewString(allFonts[i].getName()));
        }
        return viewAllFonts;
    }
    private Value<AFont> vfont;
    private ViewString font;
    private ViewString style;
    private EditString size;
    private VList fonts;
    private VList styles;
    private VList sizes;
    private MaintainViewer preview;
    private String text;

    /**
     *
     * @param _vfont
     */
    public VFontBrowser(Value<AFont> _vfont) {
        this(_vfont, " Quick Brown Fox Jumper over the Lazy Brown Dog ");
    }

    /**
     *
     * @param _vfont
     * @param _text
     */
    public VFontBrowser(Value<AFont> _vfont, String _text) {
        vfont = _vfont;
        text = _text;
        AFont f = vfont.getValue();
        font = new ViewString(f.getFont().getName());
        style = new ViewString("" + f.getFont().getStyle());
        size = new EditString("" + f.getFont().getSize());

        fonts = new VList(allFonts(AFont.cPlain, 16), 1) {

            @Override
            public IVItem vItem(Object _value) {
                return new VItem(new VString(_value), _value) {

                    @Override
                    public void picked(IEvent _e) {
                        setFont(getValue().toString(), style.getText(), size.getText());
                    }

                    @Override
                    public void selected(IEvent _e) {
                        setFont(getValue().toString(), style.getText(), size.getText());
                    }
                };
            }
        };
        styles = new VList(viewAllStyles, 1) {

            @Override
            public IVItem vItem(Object _value) {
                return new VItem(new VString(_value), _value) {

                    @Override
                    public void picked(IEvent _e) {
                        setFont(font.getText(), getValue().toString(), size.getText());
                    }

                    @Override
                    public void selected(IEvent _e) {
                        setFont(font.getText(), getValue().toString(), size.getText());
                    }
                };
            }
        };
        sizes = new VList(viewAllSizes, 1) {

            @Override
            public IVItem vItem(Object _value) {
                return new VItem(new VString(_value), _value) {

                    @Override
                    public void picked(IEvent _e) {
                        setFont(font.getText(), style.getText(), getValue().toString());
                    }

                    @Override
                    public void selected(IEvent _e) {
                        setFont(font.getText(), style.getText(), getValue().toString());
                    }
                };
            }
        };
        sizes.setComparator(null);

        VPan fontsList = new VPan(fonts, 200, 300);
        VPan stylesList = new VPan(styles, 200, 300);
        VPan sizesList = new VPan(sizes, 200, 300);

        VChain c = new VChain(UV.cNENW);
        c.add(UV.zone("Font Names", new VChain(UV.cSWNW, font, fontsList)));
        c.add(UV.zone("Font Style", new VChain(UV.cSWNW, style, stylesList)));
        c.add(UV.zone("Font Size", new VChain(UV.cSWNW, size, sizesList)));
        c.layoutInterior();

        ViewString sample = new ViewString(_text, f);
        preview = new MaintainViewer(c.getW(), 300, sample, false, new ViewBorder());


        VChain m = new VChain(UV.cSWNW);
        m.add(c);
        m.add(UV.zone("Preview", preview));
        setContent(m);
    }

    /**
     *
     * @param _name
     * @param _style
     * @param _size
     */
    public void setFont(String _name, String _style, String _size) {
        try {
            int size = Integer.parseInt(_size);
            int style = 0;
            if (_style.equals("Plain") || _style.equals("0")) {
                style |= AFont.cPlain;
            }
            if (_style.equals("Bold") || _style.equals("1")) {
                style |= Font.BOLD;
            }
            if (_style.equals("Italic") || _style.equals("2")) {
                style |= Font.ITALIC;
            }
            if (_style.equals("Bold Italic") || _style.equals("3")) {
                style |= (Font.BOLD | Font.ITALIC);
            }

            AFont font = new AFont(_name, style, size);
            this.font.setText(_name);
            this.style.setText("" + style);
            this.size.setText("" + size);
            preview.setContent(new ViewString(text, font));
            vfont.setValue(font);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
