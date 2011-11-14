/*
 * EditText.java.java
 *
 * Created on 01-03-2010 01:31:36 PM
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

import colt.nicity.view.border.NullBorder;
import colt.nicity.view.event.AKeyEvent;
import colt.nicity.view.event.AMouseEvent;
import colt.nicity.view.event.AViewEvent;
import colt.nicity.view.event.FocusGained;
import colt.nicity.view.event.FocusLost;
import colt.nicity.view.event.KeyPressed;
import colt.nicity.view.event.KeyReleased;
import colt.nicity.view.event.KeyTyped;
import colt.nicity.view.event.MouseDragged;
import colt.nicity.view.event.MouseEntered;
import colt.nicity.view.event.MouseExited;
import colt.nicity.view.event.MouseMoved;
import colt.nicity.view.event.MousePressed;
import colt.nicity.view.event.MouseReleased;
import colt.nicity.view.event.UEvent;
import colt.nicity.core.collection.CArray;
import colt.nicity.core.lang.UArray;
import colt.nicity.core.lang.UString;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.adaptor.VS;
import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IFocusEvents;
import colt.nicity.view.interfaces.IKeyEvents;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IView;
import java.awt.event.KeyEvent;

/**
 *
 * @author Administrator
 */
public class EditText extends ViewText implements IFocusEvents, IKeyEvents, IMouseEvents, IMouseMotionEvents {

    // Convienient overloadable methods
    /**
     *
     * @param _value
     */
    public void stringChanged(String[] _value) {
    }

    /**
     *
     * @param _value
     */
    public void stringSet(String[] _value) {
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
    protected IView parent = NullView.cNull;
    /**
     *
     */
    protected IBorder border = NullBorder.cNull;
    private RowColum selectionBegin = null;
    private RowColum selectionStart = null;
    private RowColum selectionEnd = null;
    private RowColum caret = null;
    /**
     *
     */
    protected AColor highlight = ViewColor.cThemeSelected;
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
     * @param _text
     */
    public EditText(String[] _text) {
        super(_text);
    }

    /**
     *
     * @param _text
     * @param _font
     */
    public EditText(String[] _text, AFont _font) {
        super(_text, _font);
    }

    /**
     *
     * @param _text
     * @param _color
     */
    public EditText(String[] _text, AColor _color) {
        super(_text, _color);
    }

    /**
     *
     * @param _text
     * @param _font
     * @param _color
     */
    public EditText(String[] _text, AFont _font, AColor _color) {
        super(_text, _font, _color);
    }

    /**
     *
     * @param _text
     */
    public void appendText(String[] _text) {
        if (_text == null) {
            return;
        }
        setText(UArray.join(text, _text));
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

    /**
     *
     * @return
     */
    public boolean isEnabled() {
        return true;
    }

    /**
     *
     * @param _boolean
     */
    public void setEnabled(boolean _boolean) {
    }

    private void update() {
        parent.layoutInterior();
        repair();
        flush();
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
    public void paintBody(ICanvas g, Layer _layer, int mode, XYWH_I _painted) {
        super.paintBody(g, _layer, mode, _painted);
        g.setFont(font);

        int size = font.getSize();

        int start = (_layer.ty > 0) ? 0 : (int) (Math.abs(_layer.ty) / size);
        int end = (int) (1 + (start) + ((_layer.y() + _layer.h()) / size));
        if (end > text.length) {
            end = text.length;
        }
        int y = (start) * size;


        for (int i = start; i < end; i++, y += size) {
            if (selectionStart != null && selectionEnd != null) {
                if (i >= selectionStart.row && i <= selectionEnd.row) {
                    g.setColor(highlight);
                    if (selectionStart.row == selectionEnd.row) {
                        paintSelected(g, y, font, text[i], selectionStart.colum, selectionEnd.colum);
                    } else if (i == selectionStart.row) {
                        paintSelected(g, y, font, text[i], selectionStart.colum, text[i].length());
                    } else if (i == selectionEnd.row) {
                        paintSelected(g, y, font, text[i], 0, selectionEnd.colum);
                    } else {
                        paintSelected(g, y, font, text[i], 0, text[i].length());
                    }
                }
            }


            g.setColor(fontColor);
            g.drawString(text[i], 0, y + size);


            if (caret != null) {
                if (i == caret.row) {
                    g.setColor(ViewColor.cThemeFont);
                    int tl = text[i].length();
                    if (caret.colum > tl) {
                        caret.colum = tl;
                    }
                    int x = UVA.stringWidth(font, text[i].substring(0, caret.colum));
                    g.line(x, y, x, (y + size));
                }
            }
        }
    }

    private void paintSelected(ICanvas g, int y, AFont font, String line, int start, int end) {
        int s = UVA.stringWidth(font, line.substring(0, start));
        int e = UVA.stringWidth(font, line.substring(0, end));
        g.rect(true, s, y, e - s, font.getSize());
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
        getRootView().setFocusedView(_who, this);
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
    public float getW() {
        return w + font.getSize() + border.getW();
    }// 5 so empty canbe clicked in

    @Override
    public float getH() {
        return h + font.getSize() + border.getH();
    }

    // IKeyEvents
    public void keyPressed(KeyPressed e) {
        int code = e.getKeyCode();
        if (e.isControlDown()) {
            switch (code) {
                case KeyEvent.VK_A: {
                    selectionStart = new RowColum(0, 0);
                    int end = text.length - 1;
                    selectionEnd = new RowColum(end, text[end].length());
                    break;
                }
                case KeyEvent.VK_X: {
                    if (selectionStart != null && selectionEnd != null) {
                        String cut = selectionStart.selected(text, selectionEnd);
                        VS.setClipboard(cut);
                        setText(selectionStart.remove(text, selectionEnd));
                        caret = selectionStart;
                        selectionStart = null;
                        selectionEnd = null;
                        stringChanged(text);
                        update();
                    }
                    break;
                }
                case KeyEvent.VK_C: {
                    if (selectionStart != null && selectionEnd != null) {
                        String copy = selectionStart.selected(text, selectionEnd);
                        VS.setClipboard(copy);
                    }
                    break;
                }
                case KeyEvent.VK_V: {
                    String paste = "";
                    try {
                        paste = VS.getClipboardIfString(this);
                        appendText(UString.toStringArray(paste, "\n"));
                        update();
                        stringChanged(text);
                        break;
                    } catch (Exception x) {
                        paste = x.toString();
                    }
                    if (selectionStart == null || selectionEnd == null || selectionStart.equals(selectionEnd)) {
                        setText(caret.replace(paste, text, caret));
                        selectionStart = null;
                        selectionEnd = null;
                    } else {
                        setText(selectionStart.replace(paste, text, selectionEnd));
                        selectionStart = null;
                        selectionEnd = null;
                    }
                    update();
                    stringChanged(text);
                    break;
                }
            }
            return;
        } else if (code == KeyEvent.VK_SHIFT) {
        } else if (code == KeyEvent.VK_CONTROL) {
        } else if (code == KeyEvent.VK_ALT) {
        } else if (code == KeyEvent.VK_RIGHT) {
            if (e.isShiftDown()) {
                orderStartEnd(caret);
            }
            caret = caret.nextColum(text);
            if (e.isShiftDown()) {
                orderStartEnd(caret);
            }
            update();
        } else if (code == KeyEvent.VK_LEFT) {
            if (e.isShiftDown()) {
                orderStartEnd(caret);
            }
            caret = caret.prevColum(text);
            if (e.isShiftDown()) {
                orderStartEnd(caret);
            }
            update();
        } else if (code == KeyEvent.VK_UP) {
            if (e.isShiftDown()) {
                orderStartEnd(caret);
            }
            caret = caret.prevRow(text);
            if (e.isShiftDown()) {
                orderStartEnd(caret);
            }
            update();
        } else if (code == KeyEvent.VK_DOWN) {
            if (e.isShiftDown()) {
                orderStartEnd(caret);
            }
            caret = caret.nextRow(text);
            if (e.isShiftDown()) {
                orderStartEnd(caret);
            }
            update();
        } else if (code == KeyEvent.VK_BACK_SPACE) {
            if (selectionStart == null || selectionEnd == null || selectionStart.equals(selectionEnd)) {
                selectionStart = caret.prevColum(text);
                selectionEnd = caret;
                setText(selectionStart.remove(text, selectionEnd));
                caret = selectionStart;
                selectionStart = null;
                selectionEnd = null;
            } else {
                setText(selectionStart.remove(text, selectionEnd));
                caret = selectionStart;
                selectionStart = null;
                selectionEnd = null;
            }
            stringChanged(text);
            update();
        } else if (code == KeyEvent.VK_DELETE) {
            if (selectionStart == null || selectionEnd == null || selectionStart.equals(selectionEnd)) {
                selectionStart = caret;
                selectionEnd = caret.nextColum(text);
                setText(selectionStart.remove(text, selectionEnd));
                caret = selectionStart;
                selectionStart = null;
                selectionEnd = null;
            } else {
                setText(selectionStart.remove(text, selectionEnd));
                caret = selectionStart;
                selectionStart = null;
                selectionEnd = null;
            }
            stringChanged(text);
            update();
        } else if (code == KeyEvent.VK_HOME) {
            caret = new RowColum(caret.getRow(), 0);
            update();
        } else if (code == KeyEvent.VK_END) {
            int row = caret.getRow();
            caret = new RowColum(caret.getRow(), text[row].length());
            update();
        } else if (code == KeyEvent.VK_ENTER) {
            selectionStart = null;
            selectionEnd = null;


            int row = caret.getRow();
            int colum = caret.getColum();
            String line = text[row];
            text[row] = line.substring(0, colum);
            setText(UArray.add(text, row + 1, new String[]{line.substring(colum, line.length())}, row + 1));
            caret = new RowColum(row + 1, 0);
            stringChanged(text);
            update();
        } else {
            String replace = String.valueOf(new char[]{e.getKeyChar()});
            if (selectionStart == null || selectionEnd == null || selectionStart.equals(selectionEnd)) {
                setText(caret.replace(replace, text, caret));
                selectionStart = null;
                selectionEnd = null;
            } else {
                setText(selectionStart.replace(replace, text, selectionEnd));
                selectionStart = null;
                selectionEnd = null;
            }
            stringChanged(text);
            update();
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
        DragAndDrop.cDefault.mouseDragged(_e);
        caret = mouseAt(text, font, _e, w);
        orderStartEnd(caret);
        update();
    }

    private void orderStartEnd(RowColum end) {
        if (selectionBegin == null) {
            selectionBegin = end;
            return;
        }
        int compare = selectionBegin.compare(end);
        switch (compare) {
            case -1:
                selectionStart = selectionBegin;
                selectionEnd = end;
                break;
            case 0:
                selectionStart = null;
                selectionEnd = null;
                break;
            case 1:
                selectionStart = end;
                selectionEnd = selectionBegin;
                break;
        }
    }

    // IMouseEvents
    public void mouseEntered(MouseEntered _e) {
        grabFocus(_e.who());
    }

    public void mouseExited(MouseExited _e) {
    }

    public void mousePressed(MousePressed _e) {
        grabHardFocus(_e.who());
        selectionEnd = null;
        selectionStart = mouseAt(text, font, _e, w);
        caret = selectionStart;
        selectionBegin = caret;
        update();
    }

    public void mouseReleased(MouseReleased _e) {
        selectionBegin = null;
        caret = mouseAt(text, font, _e, w);
        if (_e.isSingleClick()) {
            orderStartEnd(caret);
        } else if (_e.isDoubleClick()) {
            int colum = caret.getColum();
            int row = caret.getRow();
            String line = text[caret.getRow()];
            char[] chars = line.toCharArray();
            if (chars.length != 0) {
                int start;
                int end;
                boolean seperator = (colum < line.length()) ? seperator(chars[colum]) : seperator(chars[colum - 1]);
                for (start = colum; (start - 1) >= 0; start--) {
                    if (seperator) {
                        if (!seperator(chars[start - 1])) {
                            break;
                        }
                    } else if (seperator(chars[start - 1])) {
                        break;
                    }
                }
                for (end = colum; end < line.length(); end++) {
                    if (seperator) {
                        if (!seperator(chars[end])) {
                            break;
                        }
                    } else if (seperator(chars[end])) {
                        break;
                    }
                }
                selectionStart = new RowColum(row, start);
                selectionEnd = new RowColum(row, end);
            } else {
                selectionStart.startEnd(selectionEnd);
            }
        } else if (_e.isTripleClick()) {
            int row = caret.getRow();
            selectionStart = new RowColum(row, 0);
            selectionEnd = new RowColum(row, text[row].length());
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

    private final RowColum mouseAt(String[] _text, AFont _font, AMouseEvent _e, float _w) {


        int row = 0;
        int colum = 0;

        int size = _font.getSize();

        XY_I p = _e.getPoint();
        row = p.y / size;

        if (row < 0) {
            row = 0;
        } else if (row >= _text.length) {
            row = text.length - 1;
        }

        String line = _text[row];
        int lineLength = line.length();

        int s = 0;
        int e = 1;
        if (p.x > _w) {
            colum = lineLength;
        } else if (p.x < 0) {
            colum = 0;
        } else {
            for (; s >= 0 && e <= lineLength;) {
                int sx = UVA.stringWidth(_font, line.substring(0, s));
                int ex = UVA.stringWidth(_font, line.substring(0, e));
                if (p.x < sx) {
                    s--;
                    e--;
                } else if (p.x > ex) {
                    s++;
                    e++;
                } else {
                    if (p.x - sx > ex - p.x) {
                        colum = e;
                    } else {
                        colum = s;
                    }
                    break;
                }
            }
        }
        return new RowColum(row, colum);
    }

    // IFocusEvents
    public void focusGained(FocusGained e) {
    }

    public void focusLost(FocusLost e) {
    }

    class RowColum {

        int row = -1;
        int colum = -1;

        RowColum(int _row, int _colum) {
            row = _row;
            colum = _colum;
        }

        void startEnd(RowColum _end) {
            if (_end == null) {
                return;
            }
            if (row == _end.row) {
                if (colum > _end.colum) {
                    int swap = colum;
                    colum = _end.colum;
                    _end.colum = swap;
                }
            } else if (row > _end.row) {
                int swap = row;
                row = _end.row;
                _end.row = swap;

                swap = colum;
                colum = _end.colum;
                _end.colum = swap;
            }
        }

        public String selected(String[] _text, RowColum _end) {
            String selection = "";
            if (_text == null || _end == null || _end == this) {
                return selection;
            }
            startEnd(_end);

            String rowOfText = _text[row];
            if (row == _end.row) {
                selection += rowOfText.substring(colum, _end.colum);
            } else {
                selection = rowOfText.substring(colum, rowOfText.length()) + "\n\r";//??
                for (; row < _end.row; row++) {
                    selection += _text[row] + "\n\r";//??
                }
                rowOfText = _text[_end.row];
                selection += rowOfText.substring(0, colum);
            }
            return selection;
        }

        String[] remove(String[] _text, RowColum _end) {
            if (_text == null || _end == null || _end == this) {
                return _text;
            }
            startEnd(_end);


            //!! need alot of improvement this is just a fast impl.
            CArray buffer = new CArray(String.class);

            for (int i = 0; i < row; i++) {
                buffer.insertLast(_text[i]);
            }

            String rowOfText = _text[row];
            if (row == _end.row) {
                String line = rowOfText.substring(0, colum);
                line += rowOfText.substring(_end.colum, rowOfText.length());
                buffer.insertLast(line);
            } else {
                String line = rowOfText.substring(0, colum);
                line += _text[_end.row].substring(_end.colum, _text[_end.row].length());
                buffer.insertLast(line);
            }

            for (int i = _end.row + 1; i < _text.length; i++) {
                buffer.insertLast(_text[i]);
            }

            String[] lines = (String[]) buffer.getAll();
            return lines;
        }

        String[] replace(String _replace, String[] _text, RowColum _end) {
            if (_replace == null || _text == null || _end == null) {
                return _text;
            }
            startEnd(_end);


            //!! need alot of improvement this is just a fast impl.
            CArray buffer = new CArray(String.class);

            for (int i = 0; i < row; i++) {
                buffer.insertLast(_text[i]);
            }

            String rowOfText = _text[row];
            if (row == _end.row) {
                String line = rowOfText.substring(0, colum);
                line += _replace;
                line += rowOfText.substring(_end.colum, rowOfText.length());
                buffer.insertLast(line);
            } else {
                String line = rowOfText.substring(0, colum);
                line += _replace;
                line += _text[_end.row].substring(_end.colum, _text[_end.row].length());
                buffer.insertLast(line);
            }

            for (int i = _end.row + 1; i < _text.length; i++) {
                buffer.insertLast(_text[i]);
            }

            colum += _replace.length();
            return (String[]) buffer.getAll();
        }

        public RowColum prevColum(String[] text) {
            if (colum - 1 > -1) {
                return new RowColum(row, colum - 1);
            } else {
                if (row - 1 > -1) {
                    return new RowColum(row - 1, text[row - 1].length());
                } else {
                    return this;
                }
            }

        }

        public RowColum nextColum(String[] text) {
            String line = text[row];
            if (colum + 1 <= line.length()) {
                return new RowColum(row, colum + 1);
            } else {
                if (row + 1 < text.length) {
                    return new RowColum(row + 1, 0);
                } else {
                    return this;
                }
            }
        }

        public RowColum prevRow(String[] text) {
            if (row - 1 > -1) {
                String line = text[row - 1];
                if (colum > line.length()) {
                    colum = line.length();
                }
                return new RowColum(row - 1, colum);
            } else {
                return this;
            }
        }

        public RowColum nextRow(String[] text) {
            if (row + 1 < text.length) {
                String line = text[row + 1];
                if (colum > line.length()) {
                    colum = line.length();
                }
                return new RowColum(row + 1, colum);
            } else {
                return this;
            }
        }

        public int getRow() {
            return row;
        }

        public int getColum() {
            return colum;
        }

        @Override
        public int hashCode() {
            return colum + row;
        }

        @Override
        public boolean equals(Object instance) {
            if (instance == this) {
                return true;
            }
            if (instance instanceof RowColum) {
                RowColum i = (RowColum) instance;
                return (i.colum == colum && i.row == row);
            }
            return false;
        }

        public int compare(Object instance) {
            if (instance == this) {
                return 0;
            }
            RowColum i = (RowColum) instance;
            if (row > i.row) {
                return 1;
            } else if (row < i.row) {
                return -1;
            } else {
                if (colum > i.colum) {
                    return 1;
                } else if (colum < i.colum) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }
}
