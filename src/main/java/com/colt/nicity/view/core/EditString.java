/*
 * EditString.java.java
 *
 * Created on 03-12-2010 06:32:26 PM
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
package com.colt.nicity.view.core;

import com.colt.nicity.view.border.NullBorder;
import com.colt.nicity.view.event.AKeyEvent;
import com.colt.nicity.view.event.AMouseEvent;
import com.colt.nicity.view.event.AViewEvent;
import com.colt.nicity.view.event.FocusGained;
import com.colt.nicity.view.event.FocusLost;
import com.colt.nicity.view.event.KeyPressed;
import com.colt.nicity.view.event.KeyReleased;
import com.colt.nicity.view.event.KeyTyped;
import com.colt.nicity.view.event.MouseDragged;
import com.colt.nicity.view.event.MouseEntered;
import com.colt.nicity.view.event.MouseExited;
import com.colt.nicity.view.event.MouseMoved;
import com.colt.nicity.view.event.MousePressed;
import com.colt.nicity.view.event.MouseReleased;
import com.colt.nicity.view.event.UEvent;
import com.colt.nicity.core.lang.UArray;
import com.colt.nicity.core.lang.UFloat;
import com.colt.nicity.core.memory.struct.XYWH_I;
import com.colt.nicity.core.memory.struct.XY_I;
import com.colt.nicity.core.value.IValue;
import com.colt.nicity.view.interfaces.IBorder;
import com.colt.nicity.view.interfaces.ICanvas;
import com.colt.nicity.view.interfaces.IEvent;
import com.colt.nicity.view.interfaces.IFocusEvents;
import com.colt.nicity.view.interfaces.IKeyEvents;
import com.colt.nicity.view.interfaces.IMouseEvents;
import com.colt.nicity.view.interfaces.IMouseMotionEvents;
import com.colt.nicity.view.interfaces.IView;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

/**
 *
 * @author Administrator
 */
public class EditString extends AViewableWH implements IValue, IFocusEvents, IKeyEvents, IMouseEvents, IMouseMotionEvents, ClipboardOwner {

    // Convienient overloadable methods
    /**
     *
     * @param _value
     */
    public void stringChanged(String _value) {
    }

    /**
     *
     * @param _value
     */
    public void stringSet(String _value) {
    }

    /**
     *
     */
    public void canceled() {
    }

    /**
     *
     * @param _event
     */
    public void bondary(AKeyEvent _event) {
    }


    /**
     *
     */
    public Object text = "";//dg public because IVPowSlider needed to set text without setText() side effects
    /**
     *
     */
    protected AFont font;// = UV.fonts[UV.cText];
    /**
     *
     */
    protected AColor fontColor;
    /**
     *
     */
    protected IView parent = NullView.cNull;
    /**
     *
     */
    public IView options;
    /**
     *
     */
    protected AColor highlight = ViewColor.cThemeActive;
    /**
     *
     */
    protected IBorder border = NullBorder.cNull;
    /**
     *
     */
    protected int start = -1;
    /**
     *
     */
    protected int at = -1;
    /**
     *
     */
    protected int end = -1;
    /**
     *
     */
    protected float minW = -1;
    /**
     *
     */
    protected float maxW = -1;
    /**
     *
     */
    public boolean password = false;

    /**
     *
     */
    public EditString() {
    }

    /**
     *
     * @param _text
     */
    public EditString(Object _text) {
        this(_text, null, ViewColor.cThemeEditFont, NullBorder.cNull);
    }

    /**
     *
     * @param _text
     * @param _minW
     * @param _maxW
     */
    public EditString(Object _text, float _minW, float _maxW) {
        this(_text, null, _minW, _maxW);
    }

    /**
     *
     * @param _text
     * @param _font
     * @param _minW
     * @param _maxW
     */
    public EditString(Object _text, AFont _font, float _minW, float _maxW) {
        this(_text, _font, ViewColor.cThemeEditFont, NullBorder.cNull);
        minW = _minW;
        maxW = _maxW;
        _setText(_text);
    }

    /**
     *
     * @param _text
     * @param color
     */
    public EditString(Object _text, AColor color) {
        this(_text, null, color, NullBorder.cNull);
    }

    /**
     *
     * @param _text
     * @param font
     */
    public EditString(Object _text, AFont font) {
        this(_text, font, ViewColor.cThemeEditFont, NullBorder.cNull);
    }

    /**
     *
     * @param _text
     * @param font
     * @param fontColor
     */
    public EditString(Object _text, AFont font, AColor fontColor) {
        this(_text, font, fontColor, NullBorder.cNull);
    }

    /**
     *
     * @param _text
     * @param _font
     * @param _fontColor
     * @param _border
     */
    public EditString(Object _text, AFont _font, AColor _fontColor, IBorder _border) {
        setColor(_fontColor);
        setFont(_font);

        w = (float) UVA.stringWidth(font, _text.toString());
        if (w < 10) {
            w = 10;//!!
        }
        h = (float) UVA.fontHeight(font);
        _setText(_text);
        border = _border;
    }

    @Override
    public void mend() {
        enableFlag(UV.cRepair);
        super.mend();
    }

    /**
     *
     * @param _at
     */
    public void setCaret(int _at) {
        int l = getText().length();
        if (_at == -1) {
            at = l;
        } else if (l > -1 && _at <= l) {
            at = _at;
        }
    }

    /**
     *
     * @return
     */
    public int getCaret() {
        return at;
    }

    /**
     *
     * @return
     */
    public String getText() {
        return text.toString();
    }

    /**
     *
     * @param _text
     */
    public void setText(Object _text) {
        _setText(_text);
        stringChanged(text.toString());
    }

    /**
     *
     * @param _text
     */
    public void _setText(Object _text) {
        if (_text == null) {
            _text = "";
        }
        text = _text;
        String ts = text.toString();
        float _w = (float) UVA.stringWidth(font, ts);
        if (minW != -1 && _w < minW) {
            w = minW;
        } else if (maxW != -1 && _w > maxW) {
            w = maxW;
        } else {
            w = _w;
        }
        h = (float) UVA.fontHeight(font);
        repair();
        flush();
    }

    /**
     *
     * @return
     */
    public AFont getFont() {
        return font;
    }

    /**
     *
     * @param _font
     */
    public void setFont(AFont _font) {
        if (_font == null) {
            _font = UV.fonts[UV.cText];
        }
        font = _font;
        w = (float) UVA.stringWidth(font, text.toString());
        //if (w < 10) w = 10;//!!
        h = (float) UVA.fontHeight(font);
    }

    /**
     *
     * @return
     */
    public AColor getColor() {
        return fontColor;
    }

    /**
     *
     * @param fontColor
     */
    public void setColor(AColor fontColor) {
        if (fontColor == null) {
            fontColor = ViewColor.cThemeEditFont;
        }
        this.fontColor = fontColor;
    }

    /**
     *
     * @return
     */
    public boolean isEnabled() {
        return (at > -2);
    }

    /**
     *
     * @param _boolean
     */
    public void setEnabled(boolean _boolean) {
        if (_boolean) {
            if (at == -2) {
                at = -1;
            }
        } else {
            at = -2;
            start = -1;
            end = -1;
            update();
        }
    }

    /**
     *
     * @param _highlight
     */
    public void setHighlight(AColor _highlight) {
        highlight = (_highlight == null) ? AColor.yellow : _highlight;
    }

    /**
     *
     * @param _highlight
     * @return
     */
    public AColor getHighlight(AColor _highlight) {
        return highlight;
    }

    // AViewable
    @Override
    public Object processEvent(IEvent task) {
        if (isEnabled()) {
            return UEvent.processEvent(this, task);
        }
        promoteEvent(task);
        return null;
    }

    @Override
    public IView disbatchEvent(IView parent, AViewEvent event) {
        if (isEnabled()) {
            return super.disbatchEvent(parent, event);
        }
        return NullView.cNull;
    }

    @Override
    public IBorder getBorder() {
        return border;
    }

    @Override
    public void setBorder(IBorder _border) {
        if (_border == null) {
            border = NullBorder.cNull;
        } else {
            border = _border;
        }
    }

    @Override
    public void paintBorder(ICanvas g, int _x, int _y, int _w, int _h) {
        border.paintBorder(g, _x, _y, _w, _h);
    }

    @Override
    public void paintBackground(ICanvas g, int _x, int _y, int _w, int _h) {
        border.paintBackground(g, _x, _y, _w, _h);
    }

    @Override
    public void paint(IView _parent, ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        enableFlag(UV.cRepair);
        enableFlag(UV.cMend);
        super.paint(_parent, g, _layer, mode, _painted);
        enableFlag(UV.cRepair);
        enableFlag(UV.cMend);
    }

    @Override
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        super.paintBody(g, _layer, mode, _painted);

        try {
            String string = text.toString();

            if (start != -1 && end != -1) {
                g.setColor(highlight);
                int s = UVA.stringWidth(font, string.substring(0, start));
                int e = UVA.stringWidth(font, string.substring(0, end));
                g.rect(true, s, 0, e - s, (int) h);
            }
            int x = 0;
            if (at > -1 && at <= string.length()) {
                x = UVA.stringWidth(font, string.substring(0, at));
                if (at == string.length()) {
                    x -= 1;
                }

            }
            int w = (int) getW();
            int sx = 0;
            if (x > w) {
                sx = -(x - w);
            }

            g.setColor(fontColor);
            g.setFont(this.font);
            if (password) {
                int s = (int) (font.getW(string) / string.length());
                for (int i = 0; i < string.length(); i++) {
                    g.oval(true, i * s, (int) ((h / 2) - (s / 2)), s, s);
                }
            } else {
                g.drawString(string, sx, font.getSize());
            }

            g.setColor(ViewColor.cThemeEditFont);
            g.line(x, 0, x, (int) h - 1);

        } catch (Exception x) {
        }
    }

    @Override
    public IView getParentView() {
        return parent;
    }

    @Override
    public void setParentView(IView _parent) {
        if (_parent == this || parent == _parent) {
            return;
        }
        if (_parent == null) {
            parent = NullView.cNull;
        } else {
            parent = _parent;
        }
    }

    @Override
    public void grabFocus(long _who) {
        if (at != -2) {
            at = 0;
            getRootView().setFocusedView(_who, this);
        }
    }

    @Override
    public void grabHardFocus(long _who) {
        getRootView().setHardFocusedView(_who, this);
    }

    @Override
    public void releaseHardFocus(long _who) {
        getRootView().setHardFocusedView(_who, null);
    }

    @Override
    public String toString() {
        return text.toString();
    }

    private void update() {
        paint();
    }

    @Override
    public float getW() {
        return w + border.getW();
    }

    @Override
    public float getH() {
        return h + border.getH();
    }

    // ClipboardOwner
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }

    // IValue
    /**
     *
     * @return
     */
    public Object getValue() {
        return text;
    }

    /**
     *
     * @param _value
     */
    public void setValue(Object _value) {
        setText((_value == null) ? "" : _value.toString());
    }

    // IKeyEvents
    public void keyPressed(KeyPressed e) {
        int code = e.getKeyCode();
        if (e.isControlDown()) {
            switch (code) {
                case KeyEvent.VK_ESCAPE: {
                    canceled();
                    break;
                }
                case KeyEvent.VK_X: {
                    String cut = "";
                    if (start != -1 && end != -1) {
                        cut = text.toString().substring(start, end);
                        setText(new String(UArray.remove(text.toString().toCharArray(), start, end)));
                        at = start;
                        start = -1;
                        end = -1;
                    } else {
                        setText(new String(UArray.remove(text.toString().toCharArray(), at, at + 1)));
                    }
                    if (at < 0) {
                        at = 0;
                    }
                    if (at > text.toString().length()) {
                        at = text.toString().length();
                    }
                    parent.layoutInterior();
                    update();
                    Toolkit tk = Toolkit.getDefaultToolkit();
                    Clipboard cb = tk.getSystemClipboard();
                    cb.setContents(new StringSelection(cut), this);
                    break;
                }
                case KeyEvent.VK_C: {
                    String copy = "";
                    if (start != -1 && end != -1) {
                        copy = text.toString().substring(start, end);
                    }
                    Toolkit tk = Toolkit.getDefaultToolkit();
                    Clipboard cb = tk.getSystemClipboard();
                    cb.setContents(new StringSelection(copy), this);
                    break;
                }
                case KeyEvent.VK_V: {
                    Toolkit tk = Toolkit.getDefaultToolkit();
                    Clipboard cb = tk.getSystemClipboard();
                    Transferable t = cb.getContents(this);
                    try {
                        String paste = (String) t.getTransferData(DataFlavor.stringFlavor);
                        if (start != -1 && end != -1) {
                            setText(new String(UArray.add(text.toString().toCharArray(), start, paste.toCharArray(), end)));
                            at = start;
                            at += paste.length();
                            start = -1;
                            end = -1;
                        } else {
                            setText(new String(UArray.add(text.toString().toCharArray(), at, paste.toCharArray(), at)));
                            at += paste.length();
                        }
                        parent.layoutInterior();
                        update();
                    } catch (Exception x) {
                    }
                    break;
                }
                case KeyEvent.VK_A: {
                    start = 0;
                    end = text.toString().length();
                    update();
                    break;
                }
            }
            return;
        }
        if (code == KeyEvent.VK_RIGHT) {
            at++;
            if (at > text.toString().length()) {
                at = text.toString().length();
                bondary(e);
            }
            if (e.isShiftDown()) {
                if (start == -1) {
                    start = at - 1;
                    end = at;
                } else {
                    if (end < at) {
                        end = at;
                    } else if (start < at) {
                        start = at;
                    }
                    //if (start == end) { start = at-1; end = at; }
                }
            } else {
                start = -1;
                end = -1;
            }
            update();
        } else if (code == KeyEvent.VK_LEFT) {
            at--;
            if (at < 0) {
                at = 0;
                bondary(e);
            }
            if (e.isShiftDown()) {
                if (start == -1) {
                    start = at;
                    end = at + 1;
                } else {
                    if (start > at) {
                        start = at;
                    } else if (end > at) {
                        end = at;
                    }
                    //if (start == end) { start = at-1; end = at; }
                }

            } else {
                start = -1;
                end = -1;
            }
            update();
        } else if (code == KeyEvent.VK_UP) {
            bondary(e);
        } else if (code == KeyEvent.VK_DOWN) {
            bondary(e);
        } else if (code == KeyEvent.VK_BACK_SPACE) {
            if (start != -1 && end != -1) {
                setText(new String(UArray.remove(text.toString().toCharArray(), start, end)));
                at = start;
                start = -1;
                end = -1;
            } else {
                setText(new String(UArray.remove(text.toString().toCharArray(), at - 1, at)));
                at--;
            }
            if (at < 0) {
                at = 0;
                bondary(e);
            }
            if (at > text.toString().length()) {
                at = text.toString().length();
            }
            parent.layoutInterior();
            update();
        } else if (code == KeyEvent.VK_DELETE) {
            if (start != -1 && end != -1) {
                int _at = text.toString().length() - Math.max(start, end);
                setText(new String(UArray.remove(text.toString().toCharArray(), start, end)));
                at = text.toString().length() - _at;
                start = -1;
                end = -1;
            } else {
                setText(new String(UArray.remove(text.toString().toCharArray(), at, at + 1)));
            }
            if (at < 0) {
                at = 0;
            }
            if (at > text.toString().length()) {
                at = text.toString().length();
            }
            parent.layoutInterior();
            update();
        } else if (code == KeyEvent.VK_HOME) {
            at = 0;
            update();
        } else if (code == KeyEvent.VK_END) {
            at = text.toString().length();
            update();
        } else if (code == KeyEvent.VK_ENTER) {
            stringSet(text.toString());
        } else if (code == KeyEvent.VK_ESCAPE) {
            return;
        } else {
            char k = e.getKeyChar();
            if (Character.isDefined(k)) {
                if (start != -1 && end != -1) {
                    setText(new String(UArray.add(text.toString().toCharArray(), start, new char[]{k}, end)));
                    at = end - (end - start);
                    start = -1;
                    end = -1;
                } else {
                    if (at == -1) {
                        setText(new String(new char[]{k}));
                        at = 0;
                    } else {
                        char[] chars = text.toString().toCharArray();
                        if (at > chars.length) {
                            at = chars.length - 1;//??
                        }
                        if (at == -1) {
                            setText(new String(new char[]{k}));
                            at = 0;
                        } else {
                            setText(new String(UArray.add(chars, at, new char[]{k}, at)));
                        }
                    }
                }
                at++;
                parent.layoutInterior();
                update();
            }
        }
    }

    public void keyReleased(KeyReleased _e) {
    }

    public void keyTyped(KeyTyped _e) {
    }

    // IMouseMotionEvents
    public void mouseMoved(MouseMoved _e) {
    }

    public void mouseDragged(MouseDragged _e) {
        XY_I p = _e.getPoint();
        if (p.x < 0 || p.y < 0 || p.x > w || p.y > h) {
            return;
        }
        if (at == -2) {
            return;
        }
        int i = at(text.toString(), font, _e, w);
        if (i > at) {
            start = at;
            end = i;
        } else if (i < at) {
            start = i;
            end = at;
        } else {
            start = 0;
            end = 0;
        }
        update();
    }

    // IMouseEvents
    public void mouseEntered(MouseEntered _e) {
        if (at == -2) {
            return;
        }
        if (at == -1) {
            at = at(text.toString(), font, _e, w);
        }
        update();
    }

    public void mouseExited(MouseExited _e) {
        //if (at == -2) return;
        //at = -1;
        update();
    }

    public void mousePressed(MousePressed _e) {
        grabHardFocus(_e.who());
        if (at == -2) {
            return;
        }
        at = at(text.toString(), font, _e, w);
        update();
    }

    public void mouseReleased(MouseReleased _e) {
        if (at == -2) {
            return;
        }
        at = at(text.toString(), font, _e, w);
        if (_e.isDoubleClick()) {
            at = at(text.toString(), font, _e, w);
            char[] chars = text.toString().toCharArray();
            if (chars.length != 0) {
                boolean seperator = (at < text.toString().length()) ? seperator(chars[at]) : seperator(chars[at - 1]);
                for (start = at; (start - 1) >= 0; start--) {
                    if (seperator) {
                        if (!seperator(chars[start - 1])) {
                            break;
                        }
                    } else if (seperator(chars[start - 1])) {
                        break;
                    }
                }
                for (end = at; end < text.toString().length(); end++) {
                    if (seperator) {
                        if (!seperator(chars[end])) {
                            break;
                        }
                    } else if (seperator(chars[end])) {
                        break;
                    }
                }
            }
        }
        if (_e.isTripleClick()) {
            at = at(text.toString(), font, _e, w);
            start = 0;
            end = text.toString().length();
        }
        if (_e.isLeftClick()) {
            //if (options != null && text.toString().length() == 0) UV.popup(this,UV.cSWNW,options,false);
        }
        update();
    }

    private static final boolean seperator(char c) {
        switch (Character.getType(c)) {
            case Character.UNASSIGNED:
                break;
            case Character.UPPERCASE_LETTER:
                break;
            case Character.LOWERCASE_LETTER:
                break;
            case Character.TITLECASE_LETTER:
                break;
            case Character.MODIFIER_LETTER:
                break;
            case Character.OTHER_LETTER:
                break;
            case Character.NON_SPACING_MARK:
                break;
            case Character.ENCLOSING_MARK:
                return true;
            case Character.COMBINING_SPACING_MARK:
                return true;
            case Character.DECIMAL_DIGIT_NUMBER:
                break;
            case Character.LETTER_NUMBER:
                break;
            case Character.OTHER_NUMBER:
                break;
            case Character.SPACE_SEPARATOR:
                return true;
            case Character.LINE_SEPARATOR:
                return true;
            case Character.PARAGRAPH_SEPARATOR:
                return true;
            case Character.CONTROL:
                break;
            case Character.FORMAT:
                break;
            case Character.PRIVATE_USE:
                break;
            case Character.SURROGATE:
                break;
            case Character.DASH_PUNCTUATION:
                return true;
            case Character.START_PUNCTUATION:
                return true;
            case Character.END_PUNCTUATION:
                return true;
            case Character.CONNECTOR_PUNCTUATION:
                return true;
            case Character.OTHER_PUNCTUATION:
                return true;
            case Character.MATH_SYMBOL:
                break;
            case Character.CURRENCY_SYMBOL:
                break;
            case Character.MODIFIER_SYMBOL:
                break;
            case Character.OTHER_SYMBOL:
                break;
        }
        return false;
    }

    private final int at(String _text, AFont _font, AMouseEvent _e, float _w) {
        int at = 0;
        int tl = _text.length();
        if (tl == 0) {
            return at;
        }

        XY_I ep = _e.getPoint();
        XY_I p = new XY_I((int) (ep.x + border.getX()), (int) (ep.y + border.getY()));
        float x = p.x / _w;
        x = UFloat.checkFloat(x, 0.0f);

        int s = 0;
        if (_w != 0.0f) {
            s = (int) (tl * (p.x / _w));
        }
        if (s > tl) {
            s = tl;
        }
        if (s < 0) {
            s = 0;
        }

        int e = 0;
        if (s == 0) {
            e = 1;
        } else if (s == tl) {
            s = tl - 1;
            e = tl;
        } else {
            e = s + 1;
        }

        if (p.x > _w) {
            return tl;
        }
        if (p.x < 0) {
            return 0;
        }
        for (; s >= 0 && e <= tl;) {
            int sx = UVA.stringWidth(font, _text.substring(0, s));
            int ex = UVA.stringWidth(font, _text.substring(0, e));
            if (p.x < sx) {
                s--;
                e--;
            } else if (p.x > ex) {
                s++;
                e++;
            } else {
                if (p.x - sx > ex - p.x) {
                    at = e;
                } else {
                    at = s;
                }
                return at;
            }
        }
        return tl;
    }

    // IFocusEvents
    public void focusGained(FocusGained e) {
        super.promoteEvent(e);
    }

    public void focusLost(FocusLost e) {
        super.promoteEvent(e);
    }
}
