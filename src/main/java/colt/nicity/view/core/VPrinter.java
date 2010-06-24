/*
 * VPrinter.java.java
 *
 * Created on 03-12-2010 06:34:28 PM
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
import colt.nicity.view.border.NullBorder;
import colt.nicity.view.border.PopupBorder;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.event.WindowActivated;
import colt.nicity.view.event.WindowClosed;
import colt.nicity.view.event.WindowDeactivated;
import colt.nicity.view.event.WindowDeiconified;
import colt.nicity.view.event.WindowIconified;
import colt.nicity.view.event.WindowOpened;
import colt.nicity.view.image.IImage;
import colt.nicity.view.list.AItem;
import colt.nicity.view.value.VFiles;
import colt.nicity.view.value.VToggle;
import colt.nicity.view.value.VValueButton;
import colt.nicity.core.lang.UFile;
import colt.nicity.core.lang.UString;
import colt.nicity.core.value.Value;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IMouseEvents;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IWindowEvents;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class VPrinter extends AItem implements IWindowEvents, IMouseEvents {

    Object printName;
    IView[] prints;
    private static File lastDir = new File(System.getProperty("user.dir"));
    private static String lastImageFormat = "png";

    /**
     *
     * @param _name
     * @param _print
     * @param _w
     * @param _h
     */
    public VPrinter(Object _name, IView _print, int _w, int _h) {
        init(_name, new IView[]{_print}, _w, _h);
    }

    /**
     *
     * @param _name
     * @param _prints
     * @param _w
     * @param _h
     */
    public VPrinter(Object _name, IView[] _prints, int _w, int _h) {
        init(_name, _prints, _w, _h);
    }

    @Override
    public String toString() {
        return "Print Setup";
    }

    /**
     *
     * @param _name
     * @param _prints
     * @param _w
     * @param _h
     */
    public void init(Object _name, IView[] _prints, final int _w, int _h) {
        printName = _name;
        prints = _prints;

        setContent(menu());
        setBorder(new ViewBorder());
    }

    /**
     *
     * @param _print
     */
    public void setPrint(IView _print) {
        setPrints(new IView[]{_print});
    }

    /**
     *
     * @param _prints
     */
    public void setPrints(IView[] _prints) {
        prints = _prints;
    }

    /**
     *
     * @return
     */
    public long getPrintTime() {
        return System.currentTimeMillis();
    }
    VToggle printToPrinter;
    VToggle printToImage;
    VValueButton scale;
    VValueButton format;

    /**
     *
     * @return
     */
    public IView menu() {

        String[] exportFormats = UV.getToDiskFormats();
        format = new VValueButton("", null, lastImageFormat, exportFormats);

        VChain p2Image = new VChain(UV.cSN);
        p2Image.add(VIcon.icon("image32x32"));
        p2Image.add(format);

        printToPrinter = new VToggle(
                new VChain(UV.cEW, VIcon.icon("checkOn24x24"), VIcon.icon("print32x32")),
                new VChain(UV.cEW, VIcon.icon("checkOff24x24"), VIcon.icon("print32x32")),
                false, NullBorder.cNull);

        printToImage = new VToggle(
                new VChain(UV.cEW, VIcon.icon("checkOn24x24"), p2Image),
                new VChain(UV.cEW, VIcon.icon("checkOff24x24"), VIcon.icon("image32x32")),
                false, NullBorder.cNull);



        scale = new VValueButton("scale ", " %", "100", UString.toStringArray(10, 400, 10));


        VButton printButton = new VButton(
                " Print ") {

            @Override
            public void picked(IEvent _e) {
                getRootView().dispose();
                double _scale = scale.getInt() / 100d;
                if (printToImage.isTrue()) {
                    for (IView print : prints) {
                        IImage image = UV.toImage(
                                print, 0, 0,
                                (int) (print.getW() * _scale),
                                (int) (print.getH() * _scale));
                        Value file = new Value(lastDir);
                        VFiles selectFile = new VFiles(file, "save");
                        selectFile.init(false, true, 500, 600);

                        selectFile.toFront("Save");

                        File got = (File) selectFile.waitForSet();
                        if (got != null && !got.isDirectory()) {
                            UFile.ensureDirectory(got);
                            lastDir = got.getParentFile();

                            String exportFormat = format.toString();
                            String saveTo = got.getAbsolutePath();
                            saveTo = UFile.replaceExtension(saveTo, exportFormat);
                            VS.writeImageToFile(image, exportFormat, new File(saveTo));

                        }
                    }
                } else {
                    UPrintable.print(
                            printName.toString(),
                            prints,
                            _scale);
                }
            }
        };

        VButton cancelButton = new VButton(
                " Cancel ") {

            @Override
            public void picked(IEvent _e) {
                close();
            }
        };




        VChain outputDevices = new VChain(UV.cSWNW);
        outputDevices.add(printToImage);
        outputDevices.add(new RigidBox(10, 10));
        outputDevices.add(printToPrinter);
        outputDevices.layoutInterior();

        VChain setup = new VChain(UV.cEW);
        setup.add(new VString(" Send to: "));
        setup.add(new RigidBox(10, 10));
        setup.add(outputDevices);
        setup.add(new RigidBox(10, 10));
        setup.add(scale);
        setup.setBorder(new PopupBorder(20));
        setup.spans(UV.cXEW);

        VChain menu = new VChain(UV.cEW);
        menu.add(printButton);
        menu.add(new RigidBox(5, 5));
        menu.add(cancelButton);
        menu.spans(UV.cXEW);

        VChain chain = new VChain(UV.cSWNW);
        chain.add(setup);
        chain.add(menu, UV.cSN);

        return chain;
    }
    AWindow window;

    /**
     *
     * @param _centerRelativeTo
     */
    public void toFront(IView _centerRelativeTo) {
        AWindow _window = window;
        if (_window == null || _window.closed()) {
            window = UV.frame(this, this.toString());
        } else {
            _window.toFront();
        }
    }

    /**
     *
     */
    public void close() {
        if (window != null) {
            window.dispose();
        }
    }

    // IWindowEvents
    public void windowOpened(WindowOpened _e) {
    }

    public void windowClosed(WindowClosed _e) {
        window = null;
    }

    public void windowActivated(WindowActivated _e) {
    }

    public void windowDeactivated(WindowDeactivated _e) {
    }

    public void windowIconified(WindowIconified _e) {
    }

    public void windowDeiconified(WindowDeiconified _e) {
    }
}

