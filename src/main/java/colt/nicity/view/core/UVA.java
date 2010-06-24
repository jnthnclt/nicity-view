/*
 * UVA.java.java
 *
 * Created on 01-03-2010 01:29:42 PM
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

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 *
 * @author Administrator
 */
public class UVA { // Utils View Adaptor

    static private final Component component = new Component() {
    };

    /**
     *
     * @param _font
     * @return
     */
    static public FontMetrics fm(Font _font) {
        FontMetrics fm = component.getFontMetrics(_font);
        return fm;
    }

    /**
     *
     * @param _font
     * @param _string
     * @return
     */
    static public int stringWidth(AFont _font, String _string) {
        FontMetrics fm = component.getFontMetrics(_font.getFont());
        return fm.stringWidth(_string);
    }

    /**
     *
     * @param _font
     * @return
     */
    static public int fontAscent(AFont _font) {
        FontMetrics fm = component.getFontMetrics(_font.getFont());
        return fm.getAscent();
    }

    /**
     *
     * @param _font
     * @return
     */
    static public int fontDescent(AFont _font) {
        FontMetrics fm = component.getFontMetrics(_font.getFont());
        return fm.getDescent();
    }

    /**
     *
     * @param _font
     * @return
     */
    static public int fontHeight(AFont _font) {
        FontMetrics fm = component.getFontMetrics(_font.getFont());
        return fm.getHeight();
    }

    /**
     *
     * @param _font
     * @param _char
     * @return
     */
    static public int fontCharWidth(AFont _font, char _char) {
        FontMetrics fm = component.getFontMetrics(_font.getFont());
        return fm.charWidth(_char);
    }
}
