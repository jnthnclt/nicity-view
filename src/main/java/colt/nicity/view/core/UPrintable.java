/*
 * UPrintable.java.java
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

import colt.nicity.view.adaptor.VS;
import colt.nicity.view.image.IImage;
import colt.nicity.core.lang.UTrace;
import colt.nicity.view.interfaces.IView;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class UPrintable implements Printable, Pageable {

    private IView view;
    private PageFormat pageFormat;
    private double scale;
    private long time;

    /**
     *
     * @param _view
     * @param _pageFormat
     * @param _scale
     */
    public UPrintable(IView _view, PageFormat _pageFormat, double _scale) {
        view = _view;
        pageFormat = _pageFormat;
        scale = _scale;
        time = System.currentTimeMillis();

        IImage image = UV.toImage(view);
        VS.writeImageToFile(image, "jpg", new File(time + ".jpg"));

    }

    /**
     *
     * @param _pageFormat
     */
    public void setPageFormat(PageFormat _pageFormat) {
        pageFormat = _pageFormat;
    }

    // Printable
    public int print(Graphics _graphics, PageFormat _pageFormat, int _pageIndex) throws PrinterException {
        float vw = view.getW();
        float vh = view.getH();

        vw *= scale;
        vh *= scale;


        double pw = pageFormat.getImageableWidth() - pageFormat.getImageableX();
        double ph = pageFormat.getImageableHeight() - pageFormat.getImageableY();


        int colums = (int) Math.ceil(vw / pw);
        int rows = (int) Math.ceil(vh / ph);

        int row = (colums == 0) ? 0 : (_pageIndex / colums);
        int colum = (row == 0) ? _pageIndex : _pageIndex - (row * colums);

        if (row > rows) {
            return NO_SUCH_PAGE;
        }
        if (colum > colums) {
            return NO_SUCH_PAGE;
        }

        int iw = (int) (pw * (1 + scale));
        int ih = (int) (ph * (1 + scale));

        float vx = (float) (colum * iw);
        float vy = (float) (row * ih);

        view.mend();

        IImage image = UV.toImage(view, vx, vy, iw, ih);
        VS.writeImageToFile(image, "jpg", new File(time + "-" + _pageIndex + ".jpg"));


        int dx = (int) (pageFormat.getImageableX());
        int dy = (int) (pageFormat.getImageableY());
        int dxw = (int) (dx + pw);
        int dyh = (int) (dy + ph);


        int sx = (int) (0);
        int sy = (int) (0);
        int sxw = (int) (iw);
        int syh = (int) (ih);

        _graphics.drawImage((BufferedImage) image.data(0), dx, dy, dxw, dyh, sx, sy, sxw, syh, null);//??

        return PAGE_EXISTS;

    }

    // Pageable
    public int getNumberOfPages() {
        if (pageFormat == null) {
            return UNKNOWN_NUMBER_OF_PAGES;
        }
        float vw = view.getW();
        float vh = view.getH();

        vw *= scale;
        vh *= scale;


//System.out.println(pageFormat.getImageableX()+" "+pageFormat.getImageableY()+" "+pageFormat.getImageableWidth()+" "+pageFormat.getImageableHeight());

        double pw = pageFormat.getImageableWidth() - pageFormat.getImageableX();
        double ph = pageFormat.getImageableHeight() - pageFormat.getImageableY();

        int colums = (int) Math.ceil(vw / pw);
        int rows = (int) Math.ceil(vh / ph);

        int numberOfPages = rows * colums;
        return numberOfPages;
    }

    public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
        return pageFormat;
    }

    public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
        return this;
    }

    /**
     *
     * @param _title
     * @param _views
     * @param _scale
     */
    public static void print(String _title, IView[] _views, double _scale) {

        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName(_title);
        PageFormat pf = pj.pageDialog(pj.defaultPage());
        pf = pj.validatePage(pf);

        for (int i = 0; i < _views.length; i++) {
            //!! null value causes PrinterJob to blow our java away task away!
            if (_views[i] == null) {
                UTrace.traceCaller(10);
                continue;
            }
            pj.setPageable(new UPrintable(_views[i], pf, _scale));
            pj.printDialog();
            try {
                pj.print();
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
    }
}
