/*
 * VFiles.java.java
 *
 * Created on 03-12-2010 06:39:52 PM
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
package colt.nicity.view.value;

import colt.nicity.view.border.ButtonBorder;
import colt.nicity.view.border.LineBorder;
import colt.nicity.view.list.ListController;
import colt.nicity.view.list.VList;
import colt.nicity.view.list.UListController;
import colt.nicity.core.collection.CArray;
import colt.nicity.core.collection.CSet;
import colt.nicity.core.lang.UFile;
import colt.nicity.core.lang.UText;
import colt.nicity.core.memory.struct.XY_I;
import colt.nicity.core.time.UTime;
import colt.nicity.core.value.IValue;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.EditText;
import colt.nicity.view.core.Place;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UModal;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VFixed;
import colt.nicity.view.core.VFrame;
import colt.nicity.view.core.VPaintable;
import colt.nicity.view.core.VPan;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.VTrapFlex;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.ViewString;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.image.ViewImage;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.ISizeable;
import colt.nicity.view.interfaces.ISupportSizeDependecy;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.rpp.IRPPViewable;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Administrator
 */
public class VFiles extends Viewer implements IValue, ISizeable, ISupportSizeDependecy, IRPPViewable {

    public static IView viewable(String[] args) {
        VFiles files = new VFiles();
        files.init(false, true, 600, 600);
        return files;
    }

    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {

        UV.exitFrame(viewable(_args), "File Browser");
    }
    /**
     *
     */
    final private static ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    final public static CSet fsRootSet = new CSet();
    private static File[] fsRoots;
    private VButton cancel;
    private VButton refresh;
    private VButton ok;
    private String okName = "Select";
    private Value fileName = new Value("");
    private File pov;
    /**
     *
     */
    public IValue value;
    private CArray path = new CArray();
    private CArray roots = new CArray();
    private CArray files = new CArray();
    /**
     *
     */
    public boolean browserOnly = false;
    /**
     *
     */
    public boolean showFiles;

    /**
     *
     */
    public VFiles() {
        this(new File(System.getProperty("user.dir")), "Select");
    }

    /**
     *
     * @param _file
     * @param _okName
     */
    public VFiles(File _file, String _okName) {
        this(new Value(_file), _okName);
    }

    /**
     *
     * @param _file
     * @param _okName
     * @param _showFiles
     */
    public VFiles(File _file, String _okName, boolean _showFiles) {
        this(new Value(_file), _okName, _showFiles);
    }

    /**
     *
     * @param _value
     * @param _okName
     */
    public VFiles(IValue _value, String _okName) {
        this(_value, _okName, true);
    }

    /**
     *
     * @param _value
     * @param _okName
     * @param _showFiles
     */
    public VFiles(IValue _value, String _okName, boolean _showFiles) {
        showFiles = _showFiles;
        if (fsRoots == null) {
            fsRoots = File.listRoots();
        }
        if (roots.getCount() == 0) {
            File[] _roots = fsRoots;//new File[0];
            for (int i = 0; i < _roots.length; i++) {
                fsRootSet.add(_roots[i]);
                roots.insertLast(new VFile(this, _roots[i]));
            }
        }
        value = _value;
        if (value == null) {
            value = new Value(new File(System.getProperty("user.dir")));
        }
        okName = _okName;
    }
    // ISizeable

    /**
     *
     * @param _w
     * @param _h
     */
    public void setSize(int _w, int _h) {
        //!! todo
    }

    /**
     *
     * @param _title
     */
    public void toFront(Object _title) {
        UV.frame(this, _title);
    }

    /**
     *
     * @param _title
     */
    VFrame frame;
    public void popup(IView ref, Object _title) {
        frame = new VFrame(this, _title, true, false);
        UV.popup(ref, UV.cCC, frame, false, false);
    }

    /**
     *
     * @return
     */
    public File waitForSet() {
        UModal wait = new UModal(value);
        value = wait;
        Object got = wait.getModalValue();
        if (frame != null) {
            frame.close();
            frame = null;
        }
        return (File) got;
    }

    /**
     *
     * @return
     */
    @Override
    public Object getValue() {
        return value.getValue();
    }

    /**
     *
     * @param _value
     */
    @Override
    public void setValue(Object _value) {
        File file = null;
        if (_value instanceof File) {
            file = (File) _value;
        } else if (_value instanceof String) {
            file = new File((String) _value);
        }
        if (file == null) {
            return;
        }
        if (fsRootSet.get(file) == null) {
            if (!file.exists()) {
                return;
            }
        }

        value.setValue(file);
        setPOV(0, file);
    }

    /**
     *
     * @param _who
     * @param _file
     */
    public void setPOV(final long _who, final File _file) {
        final VFiles _this = this;
        
        EXECUTOR_SERVICE.execute(new Runnable() {

            @Override
            public void run() {
                if (_file == null) {
                    return;
                }
                
                VChain c = new VChain(UV.cSWNW);
                c.add(new VString("Executable:"+_file.canExecute()));
                c.add(new VString("Readable:"+_file.canRead()));
                c.add(new VString("Writable:"+_file.canWrite()));
                c.add(new VString("Modified:"+UTime.basicTime(_file.lastModified())));
                c.add(new VString("Size:"+_file.length()));
                
                String ext = UFile.getExtension(_file.getName());
                if (ext.equalsIgnoreCase("xml") || ext.equalsIgnoreCase("txt")) {
                    c.add(new EditText(UText.loadTextFile(_file)));
                }
                if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("gif")) {
                    c.add(new VPaintable(new ViewImage(_file).getThumbnail(0.25f)));
                }
                preview.setView(c);
                
                if (!_file.isDirectory()) {
                    return;
                }

                pov = _file;
                if (flcontroller != null) {
                    flcontroller.setFilter("");
                }
                if (pan != null) {
                    pan.setPositionY(0);
                    pan.setPositionY(0);
                }

                path.removeAll();
                for (File _path = _file; _path != null; _path = _path.getParentFile()) {
                    if (!_path.isDirectory()) {
                        continue;
                    }
                    path.insertLast(new VFile(_this, _path));
                    if (fsRootSet.get(_path) != null) {
                        break;
                    }
                }

                CArray fileItems = new CArray(VFile.class);
                if (_file.isDirectory()) {
                    File[] _files = _file.listFiles();
                    String[] names = new String[_files.length];
                    for (int i = 0; i < _files.length; i++) {
                        names[i] = _files[i].getName();
                        if (showFiles) {
                            fileItems.insertLast(new VFile(_this, _files[i]));
                        } else {
                            if (_files[i].isFile()) {
                                continue;
                            }
                            fileItems.insertLast(new VFile(_this, _files[i]));
                        }
                    }
                    files.replaceAll(fileItems.getAll());
                } else {
                    files.replaceAll(new VFile[]{new VFile(_this, _file)});
                }

                paint();

                VFile fileItem = (VFile) files.getAt(0);
                if (fileItem != null) {
                    fileItem.grabFocus(0);// !! propagate who
                }
            }
        });
    }

    /**
     *
     * @param _key
     * @return
     */
    public IValue getValue(Object _key) {
        return value;
    }

    /**
     *
     * @param _value
     * @param _key
     */
    public void setValue(IValue _value, Object _key) {
        value = _value;
    }
    VPan preview;
    VPan pan;
    ListController flcontroller;

    /**
     *
     * @return
     */
    public File[] getSelectedFiles() {
        return (File[]) UListController.getSelectedValues(flcontroller, File.class);
    }

    /**
     *
     * @param _browserOnly
     * @param _scrollable
     * @param _w
     * @param _h
     */
    public void init(boolean _browserOnly, boolean _scrollable, float _w, float _h) {

        fileName = new Value("");

        browserOnly = _browserOnly;
        VList rootList = new VList(new ListController(roots, -1), 1);
        rootList.setComparator(null);
        //rootList.setBorder(new LineBorder(ViewColor.cGray,ViewColor.cGray,5));

        VList pathList = new VList(new ListController(path, -1), 1);
        pathList.setComparator(null);
        //pathList.setBorder(new LineBorder(ViewColor.cGray,ViewColor.cGray,5));

        flcontroller = new ListController(files, -1);
        VList filesList = new VList(flcontroller, 1);


        VString current = new VString(this);
        current.setBorder(new LineBorder(ViewColor.cTheme, ViewColor.cThemeShadow));
        current.spans(UV.cXEW);

        VChain lookin = new VChain(UV.cEW);
        lookin.add(UV.zone("Roots", new VPan(rootList, 64, _h)), UV.cFIG);
        lookin.add(UV.zone("Path", new VPan(pathList, 160, _h)), UV.cFIG);
        pan = new VPan(filesList, _w, _h);
        lookin.add(UV.zone("Files", pan), UV.cFIG);
        preview = new VPan(new RigidBox(1,1),-1,_h); 
        lookin.add(UV.zone("Preview", preview), UV.cFIG);

        VTrapFlex trapLookin = new VTrapFlex(lookin);


        VFixed fixed = new VFixed(72, -1);
        fixed.setPlacer(new Placer(new Viewer(new ViewString(" " + okName)), new Place(UV.cSC, 0, 0)));
        ok = new VButton(fixed) {

            @Override
            public void picked(IEvent _e) {
                value.setValue(new File(pov, fileName.toString()));
                getRootView().dispose();
            }
        };
        fixed = new VFixed(72, -1);
        fixed.setPlacer(new Placer(new Viewer(new ViewString(" Cancel")), new Place(UV.cSC, 0, 0)));
        cancel = new VButton(fixed) {

            @Override
            public void picked(IEvent _e) {
                value.setValue(null);
                getRootView().dispose();
            }
        };
        fixed = new VFixed(72, -1);
        fixed.setPlacer(new Placer(new ViewString("Refresh"), new Place(UV.cCC, 0, 0)));
        refresh = new VButton(fixed) {

            @Override
            public void picked(IEvent _e) {
                setPOV(_e.who(), pov);
            }
        };
        refresh.setBorder(new ButtonBorder(ViewColor.cTheme));


        VChain commit = new VChain(UV.cEW);
        commit.add(ok);
        commit.add(new RigidBox(10, 10));
        commit.add(cancel);

        VChain editChain = new VChain(new Place(UV.cEW, 10, 0));
        editChain.add(new VEditValue("File name", fileName, "", false, 160));
        editChain.add(commit);

        VChain chain = new VChain(UV.cSN);
        /*
        Value vgoto = new Value("") {
        @Override
        public void setValue(Object _value) {
        try {
        File f = new File((String) _value);
        if (f.exists()) {
        setPOV(0, f);
        }
        } catch (Exception x) {
        ;
        }
        }
        };
        VEditValue gotoFile = new VEditValue(vgoto, true, (int) _w);
        
        chain.add(UV.zone("Go to..", gotoFile), UV.cFII);
         */

        chain.add(trapLookin, UV.cFII);
        if (!browserOnly) {
            chain.add(UV.zone("Options", editChain), UV.cFII);
        }



        setContent(chain);

        Object v = value.getValue();
        if (v instanceof File) {
            setPOV(0, (File) v);
        } else {
            setPOV(0, new File(System.getProperty("user.dir")));
        }
    }

    @Override
    public String toString() {
        return (value == null) ? "" : value.toString();
    }

    /**
     *
     * @param _d
     */
    @Override
    public void setWDependency(XY_I _d) {
    }

    /**
     *
     * @param _d
     */
    @Override
    public void setHDependency(XY_I _d) {
    }
}
