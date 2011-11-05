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
import colt.nicity.core.process.IAsyncResponse;
import colt.nicity.core.lang.IOut;
import colt.nicity.view.canvas.GlueAWTGraphicsToCanvas;
import colt.nicity.view.core.AColor;
import colt.nicity.view.image.IImage;
import colt.nicity.view.interfaces.ICanvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
}
