package colt.nicity.view.concurrent;

import colt.nicity.core.collection.CSet;
import colt.nicity.core.collection.keyed.KeyedValue;
import colt.nicity.core.lang.UArray;
import colt.nicity.core.lang.UString;
import colt.nicity.core.memory.struct.XYWH_I;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.view.border.NullBorder;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.DragAndDrop;
import colt.nicity.view.core.Layer;
import colt.nicity.view.core.NullView;
import colt.nicity.view.core.UVA;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.ViewText;
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
import colt.nicity.view.interfaces.IBorder;
import colt.nicity.view.interfaces.ICanvas;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IFocusEvents;
import colt.nicity.view.interfaces.IKeyEvents;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IMouseMotionEvents;
import colt.nicity.view.interfaces.IView;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

/**
 * 
 * @author jonathan
 */
public class VCText extends ViewText implements IFocusEvents, IKeyEvents, IMouseEvents, IMouseMotionEvents, ClipboardOwner {

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
    final private CSet<KeyedValue<Long, RowColum>> selectionBegin = new CSet<KeyedValue<Long, RowColum>>();
    final private CSet<KeyedValue<Long, RowColum>> selectionStart = new CSet<KeyedValue<Long, RowColum>>();
    final private CSet<KeyedValue<Long, RowColum>> selectionEnd = new CSet<KeyedValue<Long, RowColum>>();
    final private CSet<KeyedValue<Long, RowColum>> carets = new CSet<KeyedValue<Long, RowColum>>();

    private RowColum caret(long _who, CSet<KeyedValue<Long, RowColum>> _table) {
        synchronized (_table) {
            return KeyedValue.get(_table, _who);
        }
    }

    private void caret(long _who, RowColum _caret, CSet<KeyedValue<Long, RowColum>> _table) {
        synchronized (_table) {
            KeyedValue.remove(_table, _who);
            if (_caret != null) {
                KeyedValue.add(_table, _who, _caret);
            }
        }
    }
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
    public VCText(String[] _text) {
        super(_text);
    }

    /**
     * 
     * @param _text
     * @param _font
     */
    public VCText(String[] _text, AFont _font) {
        super(_text, _font);
    }

    /**
     * 
     * @param _text
     * @param _color
     */
    public VCText(String[] _text, AColor _color) {
        super(_text, _color);
    }

    /**
     * 
     * @param _text
     * @param _font
     * @param _color
     */
    public VCText(String[] _text, AFont _font, AColor _color) {
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
            KeyedValue[] all = carets.getAll(KeyedValue.class);
            for (KeyedValue a : all) {
                RowColum rc = (RowColum) a.value();
                RowColum ss = caret(rc.who, selectionStart);
                RowColum se = caret(rc.who, selectionEnd);

                if (ss != null && se != null) {
                    if (i >= ss.row && i <= se.row) {
                        g.setAlpha(0.5f, 0);
                        g.setColor(rc.color());
                        if (ss.row == se.row) {
                            paintSelected(g, y, font, text[i], ss.colum, se.colum);
                        } else if (i == ss.row) {
                            paintSelected(g, y, font, text[i], ss.colum, text[i].length());
                        } else if (i == se.row) {
                            paintSelected(g, y, font, text[i], 0, se.colum);
                        } else {
                            paintSelected(g, y, font, text[i], 0, text[i].length());
                        }
                    }
                }
            }


            g.setColor(fontColor);
            g.drawString(text[i], 0, y + size);

            for (KeyedValue a : all) {
                RowColum rc = (RowColum) a.value();
                if (rc != null) {
                    if (i == rc.row) {
                        g.setColor(rc.color());
                        int tl = text[i].length();
                        if (rc.colum > tl) {
                            rc.colum = tl;
                        }
                        int x = UVA.stringWidth(font, text[i].substring(0, rc.colum));
                        g.line(x, y, x, (y + size));
                        g.line(x - 1, y, x - 1, (y + size));

                    }
                }
            }
        }
    }

    private void paintSelected(ICanvas g, int y, AFont font, String line, int start, int end) {
        int s = UVA.stringWidth(font, line.substring(0, start));
        int e = UVA.stringWidth(font, line.substring(0, end));
        g.rect(true, s, y, e - s, font.getSize() + 5);
        g.setAlpha(1f, 0);
        g.rect(false, s, y, e - s, font.getSize() + 4);
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

    // ClipboardOwner
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        //System.out.println("lostOwnership "+clipboard+" "+contents);
    }

    // IKeyEvents
    @Override
    public void keyPressed(KeyPressed e) {
        int code = e.getKeyCode();
        if (e.isControlDown()) {
            switch (code) {
                case KeyEvent.VK_A: {
                    caret(e.who(), new RowColum(e.who(), 0, 0), selectionStart);
                    int end = text.length - 1;
                    caret(e.who(), new RowColum(e.who(), end, text[end].length()), selectionEnd);
                    update();
                    break;
                }
                case KeyEvent.VK_X: {
                    RowColum ss = caret(e.who(), selectionStart);
                    RowColum se = caret(e.who(), selectionEnd);
                    if (ss != null && se != null) {
                        String cut = ss.selected(text, se);
                        Toolkit tk = Toolkit.getDefaultToolkit();
                        Clipboard cb = tk.getSystemClipboard();
                        cb.setContents(new StringSelection(cut), this);

                        setText(ss.remove(text, se));
                        caret(e.who(), ss, carets);
                        caret(e.who(), null, selectionStart);
                        caret(e.who(), null, selectionEnd);
                        stringChanged(text);
                        update();
                    }
                    break;
                }
                case KeyEvent.VK_C: {
                    RowColum ss = caret(e.who(), selectionStart);
                    RowColum se = caret(e.who(), selectionEnd);
                    if (ss != null && se != null) {
                        String copy = ss.selected(text, se);
                        Toolkit tk = Toolkit.getDefaultToolkit();
                        Clipboard cb = tk.getSystemClipboard();
                        cb.setContents(new StringSelection(copy), this);
                    }
                    break;
                }
                case KeyEvent.VK_V: {
                    Toolkit tk = Toolkit.getDefaultToolkit();
                    Clipboard cb = tk.getSystemClipboard();
                    Transferable t = cb.getContents(this);
                    String paste = "";
                    try {
                        paste = (String) t.getTransferData(DataFlavor.stringFlavor);
                        appendText(UString.toStringArray(paste, "\n"));
                        update();
                        stringChanged(text);
                        break;
                    } catch (Exception x) {
                        paste = x.toString();
                    }
                    RowColum ss = caret(e.who(), selectionStart);
                    RowColum se = caret(e.who(), selectionEnd);

                    if (ss == null || se == null || ss.equals(se)) {

                        RowColum rc = caret(e.who(), carets);
                        setText(rc.replace(paste, text, rc));
                        caret(e.who(), null, selectionStart);
                        caret(e.who(), null, selectionEnd);
                    } else {
                        setText(ss.replace(paste, text, se));
                        caret(e.who(), null, selectionStart);
                        caret(e.who(), null, selectionEnd);
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
            RowColum rc = caret(e.who(), carets);
            if (e.isShiftDown()) {
                orderStartEnd(rc);
            }
            rc = rc.nextColum(text);
            caret(e.who(), rc, carets);
            if (e.isShiftDown()) {
                orderStartEnd(rc);
            } else {
                caret(e.who(), rc, carets);
                caret(e.who(), rc, selectionBegin);
            }
            update();
        } else if (code == KeyEvent.VK_LEFT) {
            RowColum rc = caret(e.who(), carets);
            if (e.isShiftDown()) {
                orderStartEnd(rc);
            } else {
                caret(e.who(), rc, carets);
                caret(e.who(), rc, selectionBegin);
            }
            rc = rc.prevColum(text);
            caret(e.who(), rc, carets);
            if (e.isShiftDown()) {
                orderStartEnd(rc);
            } else {
                caret(e.who(), rc, carets);
                caret(e.who(), rc, selectionBegin);
            }
            update();
        } else if (code == KeyEvent.VK_UP) {
            RowColum rc = caret(e.who(), carets);
            if (e.isShiftDown()) {
                orderStartEnd(rc);
            } else {
                caret(e.who(), rc, carets);
                caret(e.who(), rc, selectionBegin);
            }
            rc = rc.prevRow(text);
            caret(e.who(), rc, carets);
            if (e.isShiftDown()) {
                orderStartEnd(rc);
            } else {
                caret(e.who(), rc, carets);
                caret(e.who(), rc, selectionBegin);
            }
            update();
        } else if (code == KeyEvent.VK_DOWN) {
            RowColum rc = caret(e.who(), carets);
            if (e.isShiftDown()) {
                orderStartEnd(rc);
            } else {
                caret(e.who(), rc, carets);
                caret(e.who(), rc, selectionBegin);
            }
            rc = rc.nextRow(text);
            caret(e.who(), rc, carets);
            if (e.isShiftDown()) {
                orderStartEnd(rc);
            } else {
                caret(e.who(), rc, carets);
                caret(e.who(), rc, selectionBegin);
            }
            update();
        } else if (code == KeyEvent.VK_BACK_SPACE) {
            RowColum rc = caret(e.who(), carets);
            RowColum ss = caret(e.who(), selectionStart);
            RowColum se = caret(e.who(), selectionEnd);
            if (ss == null || se == null || ss.equals(se)) {
                ss = rc.prevColum(text);
                caret(e.who(), ss, selectionStart);
                se = rc;
                caret(e.who(), se, selectionEnd);
                setText(ss.remove(text, se));
                rc = ss;
                caret(e.who(), rc, carets);
                caret(e.who(), null, selectionStart);
                caret(e.who(), null, selectionEnd);
            } else {
                setText(ss.remove(text, se));
                rc = ss;
                caret(e.who(), rc, carets);
                caret(e.who(), null, selectionStart);
                caret(e.who(), null, selectionEnd);
            }
            stringChanged(text);
            update();
        } else if (code == KeyEvent.VK_DELETE) {
            RowColum rc = caret(e.who(), carets);
            RowColum ss = caret(e.who(), selectionStart);
            RowColum se = caret(e.who(), selectionEnd);
            if (ss == null || se == null || ss.equals(se)) {
                ss = rc;
                caret(e.who(), ss, selectionStart);
                se = rc.nextColum(text);
                caret(e.who(), se, selectionEnd);
                setText(ss.remove(text, se));
                rc = ss;
                caret(e.who(), rc, carets);
                caret(e.who(), null, selectionStart);
                caret(e.who(), null, selectionEnd);
            } else {
                setText(ss.remove(text, se));
                rc = ss;
                caret(e.who(), rc, carets);
                caret(e.who(), null, selectionStart);
                caret(e.who(), null, selectionEnd);
            }
            stringChanged(text);
            update();
        } else if (code == KeyEvent.VK_HOME) {
            RowColum rc = caret(e.who(), carets);
            rc = new RowColum(e.who(), rc.getRow(), 0);
            caret(e.who(), rc, carets);
            update();
        } else if (code == KeyEvent.VK_END) {
            RowColum rc = caret(e.who(), carets);
            int row = rc.getRow();
            rc = new RowColum(e.who(), rc.getRow(), text[row].length());
            caret(e.who(), rc, carets);
            update();
        } else if (code == KeyEvent.VK_ENTER) {
            caret(e.who(), null, selectionStart);
            caret(e.who(), null, selectionEnd);


            RowColum rc = caret(e.who(), carets);
            int row = rc.getRow();
            int colum = rc.getColum();
            String line = text[row];
            text[row] = line.substring(0, colum);
            setText(UArray.add(text, row + 1, new String[]{line.substring(colum, line.length())}, row + 1));
            rc = new RowColum(e.who(), row + 1, 0);
            caret(e.who(), rc, carets);
            stringChanged(text);
            update();
        } else {
            RowColum rc = caret(e.who(), carets);
            RowColum ss = caret(e.who(), selectionStart);
            RowColum se = caret(e.who(), selectionEnd);

            String replace = String.valueOf(new char[]{e.getKeyChar()});
            if (ss == null || se == null || ss.equals(se)) {
                setText(rc.replace(replace, text, rc));
                caret(e.who(), null, selectionStart);
                caret(e.who(), null, selectionEnd);
            } else {
                setText(ss.replace(replace, text, se));
                caret(e.who(), null, selectionStart);
                caret(e.who(), null, selectionEnd);
            }
            stringChanged(text);
            update();
        }
    }

    @Override
    public void keyReleased(KeyReleased _e) {
    }

    @Override
    public void keyTyped(KeyTyped _e) {
    }

    // IMouseMotionEvents
    @Override
    public void mouseMoved(MouseMoved _e) {
    }

    @Override
    public void mouseDragged(MouseDragged _e) {
        RowColum rc = caret(_e.who(), carets);
        DragAndDrop.cDefault.mouseDragged(_e);
        rc = mouseAt(text, font, _e, w);
        caret(_e.who(), rc, carets);
        orderStartEnd(rc);
        update();
    }

    synchronized private void orderStartEnd(RowColum end) {
        RowColum sb = caret(end.who, selectionBegin);
        if (sb == null) {
            sb = end;
            caret(end.who, sb, selectionBegin);
            return;
        }
        int compare = sb.compare(end);
        switch (compare) {
            case -1:
                caret(end.who, sb, selectionStart);
                caret(end.who, end, selectionEnd);
                break;
            case 0:
                caret(end.who, null, selectionStart);
                caret(end.who, null, selectionEnd);
                break;
            case 1:
                caret(end.who, end, selectionStart);
                caret(end.who, sb, selectionEnd);
                break;
        }
    }

    // IMouseEvents
    @Override
    public void mouseEntered(MouseEntered _e) {
        grabFocus(_e.who());
    }

    @Override
    public void mouseExited(MouseExited _e) {
    }

    @Override
    public void mousePressed(MousePressed _e) {
        grabHardFocus(_e.who());
        caret(_e.who(), null, selectionEnd);
        RowColum ss = mouseAt(text, font, _e, w);
        caret(_e.who(), ss, selectionStart);
        RowColum rc = caret(_e.who(), carets);
        rc = ss;
        caret(_e.who(), rc, carets);
        caret(_e.who(), rc, selectionBegin);
        update();
    }

    @Override
    public void mouseReleased(MouseReleased _e) {
        caret(_e.who(), null, selectionBegin);
        RowColum rc = mouseAt(text, font, _e, w);
        caret(_e.who(), rc, carets);
        if (_e.isSingleClick()) {
            orderStartEnd(rc);
        } else if (_e.isDoubleClick()) {
            int colum = rc.getColum();
            int row = rc.getRow();
            String line = text[rc.getRow()];
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
                caret(_e.who(), new RowColum(_e.who(), row, start), selectionStart);
                caret(_e.who(), new RowColum(_e.who(), row, end), selectionEnd);

            } else {
                RowColum ss = caret(_e.who(), selectionStart);
                RowColum se = caret(_e.who(), selectionEnd);
                if (ss != null) {
                    ss.startEnd(se);
                }
            }
        } else if (_e.isTripleClick()) {
            int row = rc.getRow();
            caret(_e.who(), new RowColum(_e.who(), row, 0), selectionBegin);
            caret(_e.who(), new RowColum(_e.who(), row, text[row].length()), selectionEnd);
        }
        update();
    }

    private static boolean seperator(char c) {
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

    private RowColum mouseAt(String[] _text, AFont _font, AMouseEvent _e, float _w) {


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
        return new RowColum(_e.who(), row, colum);
    }

    // IFocusEvents
    @Override
    public void focusGained(FocusGained e) {
    }

    @Override
    public void focusLost(FocusLost e) {
    }
}
