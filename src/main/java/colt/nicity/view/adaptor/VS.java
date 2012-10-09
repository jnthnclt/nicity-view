/*
 * VS.java.java
 *
 * Created on 01-13-2010 07:11:46 AM
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
package colt.nicity.view.adaptor;

// Virtual System
import colt.nicity.core.lang.IOut;
import colt.nicity.core.process.IAsyncResponse;
import colt.nicity.view.canvas.GlueAWTGraphicsToCanvas;
import colt.nicity.view.core.AColor;
import colt.nicity.view.image.IImage;
import colt.nicity.view.interfaces.ICanvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Administrator
 */
public class VS {
    // Input
    // Cursor
    // Font
    // Image
    // Canvas
    // Printing
    // File
    // Sound
    // Color

    public static Font systemColor(String _name, int _style, int _size) {
        return new Font(_name, _style, _size);
    }

    /**
     *
     * @param _
     * @param _file
     * @param _image
     */
    static public void systemImage(IOut _, final File _file, IAsyncResponse<IImage> _image) {
        try {
            final BufferedImage image = ImageIO.read(_file);
            _image.response(_, new IImage() {

                @Override
                public ICanvas canvas(long _who) {
                    return new GlueAWTGraphicsToCanvas(0, image.createGraphics());
                }

                @Override
                public Object data(long _who) {
                    return image;
                }

                @Override
                public int getWidth() {
                    return image.getWidth();
                }

                @Override
                public int getHeight() {
                    return image.getHeight();
                }
            });
        } catch (Exception _x) {
            _image.error(_, _x);
        }
    }

    /**
     *
     * @param _color
     * @return
     */
    public static Color systemColor(AColor _color) {
        return new Color(_color.intValue(), true);
    }
    /**
     *
     */
    final public static int c32BitRGB = 0;
    /**
     *
     */
    final public static int c32BitARGB = 1;
    /**
     *
     */
    final public static int c8BitGrey = 2;

    /**
     *
     * @param _w
     * @param _h
     * @param _type
     * @return
     */
    public static IImage systemImage(int _w, int _h, int _type) {
        int type = BufferedImage.TYPE_INT_RGB;
        if (_type == c32BitARGB) {
            type = BufferedImage.TYPE_INT_ARGB;
        }
        if (_type == c8BitGrey) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        }

        final BufferedImage image = new BufferedImage(_w, _h, type);
        return new IImage() {

            @Override
            public ICanvas canvas(long _who) {
                return new GlueAWTGraphicsToCanvas(0, image.createGraphics());
            }

            @Override
            public Object data(long _who) {
                return image;
            }

            @Override
            public int getWidth() {
                return image.getWidth();
            }

            @Override
            public int getHeight() {
                return image.getHeight();
            }
        };
    }

    /**
     *
     * @param _w
     * @param _h
     * @param _type
     * @param _pixels
     * @return
     */
    public static IImage systemImage(int _w, int _h, int _type, int[] _pixels) {
        int type = BufferedImage.TYPE_INT_RGB;
        if (_type == c32BitARGB) {
            type = BufferedImage.TYPE_INT_ARGB;
        }
        final BufferedImage image = new BufferedImage(_w, _h, type);
        image.setRGB(0, 0, _w, _h, _pixels, 0, _w);
        return new IImage() {

            @Override
            public ICanvas canvas(long _who) {
                return new GlueAWTGraphicsToCanvas(0, image.createGraphics());
            }

            @Override
            public Object data(long _who) {
                return image;
            }

            @Override
            public int getWidth() {
                return image.getWidth();
            }

            @Override
            public int getHeight() {
                return image.getHeight();
            }
        };
    }

    /**
     *
     * @param _image
     * @param _type
     * @param _file
     * @return
     */
    public static boolean writeImageToFile(IImage _image, String _type, File _file) {
        return writeImageToFile((BufferedImage) _image.data(0), _type, _file);
    }

    /**
     *
     * @param _image
     * @param _type
     * @param _file
     * @return
     */
    public static boolean writeImageToFile(BufferedImage _image, String _type, File _file) {

        try {
            ImageIO.write(_image, _type, _file);
            return true;
        } catch (IOException x) {
            System.out.println("toDisk error " + x);
            return false;
        }
    }

    public static void setClipboard(String value) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Clipboard cb = tk.getSystemClipboard();
        cb.setContents(new StringSelection(value), new ClipboardOwner() {

            @Override
            public void lostOwnership(Clipboard clipboard, Transferable contents) {
            }
        });
    }

    public static String getClipboardIfString(Object requestor) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Clipboard cb = tk.getSystemClipboard();
        Transferable t = cb.getContents(requestor);
        try {
            return (String) t.getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static List<IFont> allFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        List<IFont> allFonts = new ArrayList<IFont>();
        for (Font font : ge.getAllFonts()) {
            allFonts.add(new AWTFont(font));
        }
        return allFonts;
    }

    public static IFont getSystemFont() {
        return new AWTFont(new Font(IFontConstants.cDefaultFontName, IFontConstants.cPlain, 12));
    }

    public static IFont getFont(String _name, int _style, int _size) {
        return new AWTFont(new Font(_name, _style, _size));
    }

    public static IPath path() {
        return new AWTPath(new GeneralPath());
    }

    public static IClipboard clipboard() {
        return new AWTClipboard();
    }
}

class AWTPath implements IPath {

    private final GeneralPath path;

    public AWTPath(GeneralPath path) {
        this.path = path;
    }

    @Override
    public Object getRawPath() {
        return path;
    }

    @Override
    public void moveTo(int x, int y) {
        path.moveTo(x, y);
    }

    @Override
    public void quadTo(float x1, float y1, float x2, float y2) {
        path.quadTo(x1, y1, x2, y2);
    }

    @Override
    public void lineTo(int x, int y) {
        path.lineTo(x, y);
    }

    @Override
    public void closePath() {
        path.closePath();
    }

    @Override
    public void moveTo(float _sx, float _sy) {
        path.moveTo(_sx, _sy);
    }

    @Override
    public void lineTo(float x, float y) {
        path.lineTo(x, y);
    }

    @Override
    public void curveTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        path.curveTo(x1, y1, x2, y2, x3, y3);
    }
}

class AWTClipboard implements IClipboard, ClipboardOwner {

    Transferable t;

    public AWTClipboard() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Clipboard cb = tk.getSystemClipboard();
        t = cb.getContents(this);
    }

    @Override
    public void put(Object put) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Clipboard cb = tk.getSystemClipboard();
        if (put instanceof String) {
            cb.setContents(new StringSelection((String) put), this);
        }
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }

    @Override
    public <G> G get(Class<? extends G> _class) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Clipboard cb = tk.getSystemClipboard();
        Transferable t = cb.getContents(this);
        if (_class == String.class) {
            String got = "";
            try {
                got = (String) t.getTransferData(DataFlavor.stringFlavor);
                return (G) got;
            } catch (Exception x) {
                return (G) "";
            }

        }
        return null;
    }
}

class AWTFont implements IFont { // Utils View Adaptor

    static private final Component component = new Component() {
    };
    private final Font font;
    private final FontMetrics fm;

    public AWTFont(Font _font) {
        font = _font;
        fm = component.getFontMetrics(_font);
    }

    /**
     *
     * @param _font
     * @param _string
     * @return
     */
    @Override
    public int stringWidth(String _string) {
        return fm.stringWidth(_string);
    }

    @Override
    public int stringHeight(String _string) {
        return fm.getHeight();
    }

    /**
     *
     * @param _font
     * @return
     */
    @Override
    public int ascent() {
        return fm.getAscent();
    }

    /**
     *
     * @param _font
     * @return
     */
    @Override
    public int descent() {
        return fm.getDescent();
    }

    /**
     *
     * @param _font
     * @return
     */
    @Override
    public int height() {
        return fm.getHeight();
    }

    /**
     *
     * @param _font
     * @param _char
     * @return
     */
    @Override
    public int charWidth(char _char) {
        return fm.charWidth(_char);
    }

    @Override
    public Object getNativeFont() {
        return font;
    }

    @Override
    public int getSize() {
        return font.getSize();
    }

    @Override
    public int getStyle() {
        return font.getStyle();
    }

    @Override
    public String getFontName() {
        return font.getFontName();
    }
}
