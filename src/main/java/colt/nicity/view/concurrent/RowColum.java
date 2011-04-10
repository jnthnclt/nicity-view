package colt.nicity.view.concurrent;

import colt.nicity.core.collection.CArray;
import colt.nicity.view.core.AColor;

class RowColum {

    long who;
    int row = -1;
    int colum = -1;

    RowColum(long _who, int _row, int _colum) {
        who = _who;
        row = _row;
        colum = _colum;
    }

    public AColor color() {
        return AColor.getHashSolid((Long) who);
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
            selection = rowOfText.substring(colum, rowOfText.length()) + "\n\r"; //??
            for (; row < _end.row; row++) {
                selection += _text[row] + "\n\r"; //??
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
        CArray<String> buffer = new CArray<String>(String.class);
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
        CArray<String> buffer = new CArray<String>(String.class);
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
            return new RowColum(who, row, colum - 1);
        } else {
            if (row - 1 > -1) {
                return new RowColum(who, row - 1, text[row - 1].length());
            } else {
                return this;
            }
        }
    }

    public RowColum nextColum(String[] text) {
        String line = text[row];
        if (colum + 1 <= line.length()) {
            return new RowColum(who, row, colum + 1);
        } else {
            if (row + 1 < text.length) {
                return new RowColum(who, row + 1, 0);
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
            return new RowColum(who, row - 1, colum);
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
            return new RowColum(who, row + 1, colum);
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
            return i.colum == colum && i.row == row;
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
