/*
 * ViewColor.java.java
 *
 * Created on 03-12-2010 06:34:45 PM
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
package colt.nicity.view.core;

import colt.nicity.view.value.VEditHSBColor;
import colt.nicity.view.value.VPreviewColor;
import colt.nicity.core.value.Value;
import colt.nicity.view.interfaces.IView;

/**
 *
 * @author Administrator
 */
public class ViewColor {

    /**
     *
     */
    public static AColor cThemeAccept = AColor.green.desaturate(0.5f);
    /**
     *
     */
    public static AColor cThemeReject = AColor.red.desaturate(0.5f);
    /**
     *
     */
    public static AColor cThemeImportant = new AColor(0, 200, 200);
    /**
     *
     */
    public static AColor cThemeAccent = new AColor(0, 200, 230);
    /**
     *
     */
    public static AColor cItemTheme = new AColor(255, 255, 255);
    /**
     *
     */
    public static AColor cThemeEditFont = new AColor(0, 0, 0);
    /**
     *
     */
    public static AColor cThemeFont = new AColor(0, 0, 0);
    /**
     *
     */
    public static AColor cThemeHighlight = new AColor(235, 235, 235);
    /**
     *
     */
    public static AColor cTheme = new AColor(230, 230, 230);
    /**
     *
     */
    public static AColor cThemeShadow = new AColor(0.7f);
    /**
     *
     */
    public static AColor cThemeActive = new AColor(200, 200, 255);
    /**
     *
     */
    public static AColor cThemeSelected = new AColor(0, 150, 200);
    /**
     *
     */
    public static AColor cThemeScroll = new AColor(230, 230, 230);
    /**
     *
     */
    public static AColor cButtonFont = new AColor(0, 0, 0);
    /**
     *
     */
    public static AColor cButtonTheme = new AColor(220, 220, 220);
    /**
     *
     */
    public static AColor cButtonThemeHighlight = new AColor(210, 210, 210);
    /**
     *
     */
    public static AColor cButtonThemeShadow = new AColor(170, 170, 170);
    /**
     *
     */
    public static AColor cWindowTheme = new AColor(160, 160, 160);
    /**
     *
     */
    public static AColor cWindowThemeFont = new AColor(0, 0, 0);
    /**
     *
     */
    public static AColor cVisualizeTheme = new AColor(0, 0, 0);
    /**
     *
     */
    public static AColor cVisualizeThemeFont = new AColor(255, 255, 255);
    /**
     *
     */
    public static boolean inverted = false;

    /**
     *
     * @return
     */
    public static IView edit() {
        VChain c = new VChain(UV.cSWNW);
        c.add(vc("Accept", ViewColor.cThemeAccept));
        c.add(vc("Reject", ViewColor.cThemeReject));

        c.add(vc("Item", ViewColor.cItemTheme));

        c.add(vc("Accent", ViewColor.cThemeAccent));
        c.add(vc("Highlight", ViewColor.cThemeHighlight));
        c.add(vc("Background", ViewColor.cTheme));
        c.add(vc("Shadow", ViewColor.cThemeShadow));

        c.add(vc("Editing Font", ViewColor.cThemeEditFont));
        c.add(vc("Font", ViewColor.cThemeFont));
        c.add(vc("Active", ViewColor.cThemeActive));
        c.add(vc("Selected", ViewColor.cThemeSelected));
        c.add(vc("Scroll", ViewColor.cThemeScroll));

        c.add(vc("Button Font", ViewColor.cButtonFont));
        c.add(vc("Button", ViewColor.cButtonTheme));
        c.add(vc("Button Highlight", ViewColor.cButtonThemeHighlight));
        c.add(vc("Button Shadow", ViewColor.cButtonThemeShadow));

        c.add(vc("Window", ViewColor.cWindowTheme));
        c.add(vc("Window Font", ViewColor.cWindowThemeFont));

        c.add(vc("Visualize", ViewColor.cVisualizeTheme));
        c.add(vc("Visualize Font", ViewColor.cVisualizeThemeFont));
        c.add(vc("Important", ViewColor.cThemeImportant));
        return c;
    }

    private static IView vc(final String _name, final AColor _color) {
        Value v = new Value(_color) {
            @Override
            public void setValue(Object _value) {
                _color.set8BitColor(((AColor) _value).get8BitRGBA());
            }
        };
        VChain c = new VChain(UV.cSWNW);
        c.add(new VPreviewColor(new Value(_name), v, 300, 30));
        c.add(new VEditHSBColor("", v, 300, 60));
        return c;
    }

    /**
     *
     */
    public static void onWhite() {
        inverted = false;
        ViewColor.cThemeImportant.set8BitColor(new AColor(255, 227, 0).get8BitRGBA());
        ViewColor.cThemeShadow.set8BitColor(new AColor(0.7f).get8BitRGBA());
        ViewColor.cTheme.set8BitColor(new AColor(250, 250, 250).get8BitRGBA());
        ViewColor.cThemeFont.set8BitColor(AColor.black.get8BitRGBA());
        ViewColor.cThemeEditFont.set8BitColor(AColor.black.get8BitRGBA());
        ViewColor.cThemeActive.set8BitColor(new AColor(200, 200, 200).get8BitRGBA());
        ViewColor.cThemeSelected.set8BitColor(new AColor(170, 170, 170).get8BitRGBA());
        ViewColor.cButtonTheme.set8BitColor(new AColor(230, 230, 230).get8BitRGBA());
        ViewColor.cButtonFont.set8BitColor(AColor.black.get8BitRGBA());
        ViewColor.cWindowTheme.set8BitColor(new AColor(240, 240, 240).get8BitRGBA());
        ViewColor.cWindowThemeFont.set8BitColor(AColor.black.get8BitRGBA());
        ViewColor.cVisualizeTheme.set8BitColor(new AColor(255, 255, 255).get8BitRGBA());
        ViewColor.cVisualizeThemeFont.set8BitColor(new AColor(20, 20, 20).get8BitRGBA());
        ViewColor.cItemTheme.set8BitColor(new AColor(245, 245, 245).get8BitRGBA());
        ViewColor.cThemeScroll.set8BitColor(new AColor(130, 130, 130).get8BitRGBA());
    }
    
    /**
     *
     */
    public static void onGray() {
        inverted = false;
        ViewColor.cThemeImportant.set8BitColor(new AColor(170, 170, 200).get8BitRGBA());
        ViewColor.cThemeShadow.set8BitColor(new AColor(0.7f).get8BitRGBA());
        ViewColor.cTheme.set8BitColor(new AColor(220, 220, 220).get8BitRGBA());
        ViewColor.cThemeFont.set8BitColor(AColor.black.get8BitRGBA());
        ViewColor.cThemeEditFont.set8BitColor(AColor.black.get8BitRGBA());
        ViewColor.cButtonTheme.set8BitColor(new AColor(160, 200, 240).get8BitRGBA());
        ViewColor.cButtonFont.set8BitColor(AColor.black.get8BitRGBA());
        ViewColor.cWindowTheme.set8BitColor(new AColor(102,153,255).get8BitRGBA());
        ViewColor.cWindowThemeFont.set8BitColor(AColor.black.get8BitRGBA());
        ViewColor.cVisualizeTheme.set8BitColor(new AColor(245, 245, 245).get8BitRGBA());
        ViewColor.cVisualizeThemeFont.set8BitColor(new AColor(20, 20, 20).get8BitRGBA());
        ViewColor.cItemTheme.set8BitColor(new AColor(245, 245, 245).get8BitRGBA());
        ViewColor.cThemeAccent.set8BitColor(new AColor(122,173,255).get8BitRGBA());
        
        ViewColor.cThemeActive.set8BitColor(new AColor(112,163,255).get8BitRGBA());
        ViewColor.cThemeSelected.set8BitColor(new AColor(102,153,255).get8BitRGBA());
        ViewColor.cThemeScroll.set8BitColor(new AColor(102,153,255).get8BitRGBA());
    }


    /**
     *
     */
    public static void onBlack() {
        inverted = true;
        ViewColor.cThemeImportant.set8BitColor(new AColor(128, 0, 200).get8BitRGBA());
        ViewColor.cThemeShadow.set8BitColor(new AColor(0.3f).get8BitRGBA());
        ViewColor.cTheme.set8BitColor(new AColor(40, 40, 40).get8BitRGBA());
        ViewColor.cThemeFont.set8BitColor(new AColor(255, 255, 255).get8BitRGBA());
        ViewColor.cThemeEditFont.set8BitColor(new AColor(255, 255, 255).get8BitRGBA());
        ViewColor.cThemeActive.set8BitColor(new AColor(102, 102, 112).get8BitRGBA());
        ViewColor.cThemeSelected.set8BitColor(new AColor(0, 150, 225).get8BitRGBA());
        ViewColor.cButtonTheme.set8BitColor(new AColor(50, 50, 60).get8BitRGBA());
        ViewColor.cButtonFont.set8BitColor(new AColor(240, 240, 240).get8BitRGBA());
        ViewColor.cWindowTheme.set8BitColor(new AColor(50, 50, 60).get8BitRGBA());
        ViewColor.cWindowThemeFont.set8BitColor(new AColor(240, 240, 240).get8BitRGBA());
        ViewColor.cVisualizeTheme.set8BitColor(new AColor(0, 0, 0).get8BitRGBA());
        ViewColor.cVisualizeThemeFont.set8BitColor(new AColor(255, 255, 255).get8BitRGBA());
        ViewColor.cItemTheme.set8BitColor(new AColor(15, 15, 15).get8BitRGBA());
        ViewColor.cThemeScroll.set8BitColor(new AColor(90, 90, 90).get8BitRGBA());
    }
}
