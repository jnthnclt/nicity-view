/*
 * ProgressViewer.java.java
 *
 * Created on 01-03-2010 01:32:53 PM
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
package colt.nicity.view.monitor;

import colt.nicity.view.border.RecessBorder;
import colt.nicity.view.border.SolidBorder;
import colt.nicity.view.list.AItem;
import colt.nicity.view.list.VList;
import colt.nicity.core.collection.CSet;
import colt.nicity.core.collection.keyed.KeyedValue;
import colt.nicity.core.comparator.AValueComparator;
import colt.nicity.core.comparator.UValueComparator;
import colt.nicity.core.lang.UText;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AWindow;
import colt.nicity.view.core.Place;
import colt.nicity.view.core.Placer;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VIcon;
import colt.nicity.view.core.ViewString;
import colt.nicity.view.core.Viewer;
import java.io.File;

/**
 *
 * @author Administrator
 */
public class ProgressViewer extends Viewer {

    /**
     *
     */
    public static ProgressViewer cStatic = new ProgressViewer(500);
    private float progressW = 220f;
    private CSet accessTimes = new CSet();
    private CSet progressBars = new CSet();
    private AWindow frame;
    private boolean canTitle = true;
    private boolean canIcon = false;
    private int logI = 0;
    private String[] log = new String[10];

    /**
     *
     * @param _w
     */
    public ProgressViewer(float _w) {
        this(_w, 15000);
    }

    /**
     *
     * @param _w
     * @param _releaseInMillis
     */
    public ProgressViewer(float _w, long _releaseInMillis) {
        progressW = _w;
        VList list = new VList(progressBars, 1);
        list.setComparator(UValueComparator.toString(AValueComparator.cAscending));

        Viewer listViewer = new Viewer(list);
        setBorder(new SolidBorder(AColor.lightGray));
        setPlacer(new Placer(listViewer));

        startReleaser(_releaseInMillis);
        UText.saveTextFile(new String[0], new File("log.txt"));
    }
    // Release
    private static Thread releaserThread;

    /**
     *
     * @param _inteval
     */
    public void startReleaser(final long _inteval) {
        releaserThread = new Thread() {

            public void run() {
                while (true) {
                    try {
                        sleep(_inteval);
                    } catch (Exception x) {
                    }
                    long time = System.currentTimeMillis();
                    Object[] keys = accessTimes.getAll(Object.class);
                    for (int k = 0; k < keys.length; k++) {
                        Long _time = (Long) KeyedValue.get(accessTimes,keys[k]);
                        if (_time == null) {
                            continue;
                        }
                        if (_time.longValue() < time - _inteval) {
                            remove((String) keys[k]);
                        }
                    }
                }
            }
        };
        releaserThread.start();
    }

    /**
     *
     * @param _canTitle
     */
    public void canTitle(boolean _canTitle) {
        canTitle = _canTitle;
    }

    /**
     *
     * @param _canIcon
     */
    public void canIcon(boolean _canIcon) {
        canIcon = _canIcon;
    }

    private class ProgressBar extends AItem {

        ProgressView progress = new ProgressView(progressW, 16);
        String title;
        long count = 0;

        ProgressBar(String _title) {
            title = _title;
            if (canTitle) {
                if (canIcon) {
                    Viewer title = new Viewer(VIcon.icon(_title));
                    Viewer progressBar = new Viewer(progress);
                    VChain chain = new VChain(new Place(UV.cEW, 5, 0));
                    chain.add(title);
                    chain.add(progressBar);
                    chain.add(new RigidBox(1, 1));
                    setPlacer(new Placer(chain));
                } else {
                    Viewer title = new Viewer(new ViewString(_title, UV.fonts[UV.cSmall]));
                    Viewer progressBar = new Viewer(progress);
                    title.place(progressBar, new Place(UV.cSWNW, 5, 0));
                    setPlacer(new Placer(title));
                }
            } else {
                Viewer progressBar = new Viewer(progress);
                setPlacer(new Placer(progressBar));
            }
            setBorder(new RecessBorder(AColor.offWhite));
            spans(UV.cXE);
        }

        public void setProgressText(String _string) {
            count++;
            progress.setProgressText(count + ":" + _string);
        }

        public String toString() {
            return title;
        }

        public Object getValue() {
            return progress;
        }

        public void picked() {
            System.out.println("?????" + this);
        }
    }

    /**
     *
     */
    public void showInFrame() {
        if (frame == null) {
            frame = UV.frame(this, this);
        } else {
            frame.toFront();
        }
    }

    /**
     *
     */
    public void hideFrame() {
        if (frame != null) {
            frame.dispose();
        }
        frame = null;
    }

    private void accessedKey(String _key) {
        long time = System.currentTimeMillis();
        KeyedValue.add(accessTimes,_key,new Long(time));
    }

    /**
     *
     */
    public void flushLog() {
        UText.appendTextFile(log, 0, logI, new File("log.txt"));
        logI = 0;
    }

    /**
     *
     * @param _string
     * @param _key
     */
    public void out(String _string, String _key) {
        if (logI + 1 == log.length) {
            UText.appendTextFile(log, 0, logI, new File("log.txt"));
            logI = 0;
        }
        log[logI++] = _key + ":" + _string;

        ProgressBar progressBar = (ProgressBar) KeyedValue.get(progressBars,_key);
        if (progressBar == null) {
            progressBar = new ProgressBar(_key);
            KeyedValue.add(progressBars,_key,progressBar);
        } else {
            accessedKey(_key);
        }
        progressBar.setProgressText(_string);
        //update();
    }

    /**
     *
     * @param _progress
     * @param _key
     */
    public void progress(float _progress, String _key) {
        ProgressBar progressBar = (ProgressBar) KeyedValue.get(progressBars,_key);
        if (progressBar == null) {
            progressBar = new ProgressBar(_key);
            KeyedValue.add(progressBars,_key,progressBar);
        } else {
            accessedKey(_key);
        }
        progressBar.progress.setProgress(_progress);
        if (_progress == 0.0 || _progress == 1.0) {
            update();
        }
    }

    /**
     *
     * @param _key
     */
    public void finished(String _key) {
        ProgressBar progressBar = (ProgressBar) KeyedValue.get(progressBars,_key);
        if (progressBar != null) {
            accessedKey(_key);
            progressBar.progress.finished();
            update();
        }
    }

    /**
     *
     * @param _key
     */
    public void remove(String _key) {
        KeyedValue.remove(progressBars,_key);
        KeyedValue.remove(accessTimes,_key);
    }

    /**
     *
     * @param _t
     */
    public void exception(Throwable _t) {
    }

    /**
     *
     * @return
     */
    public boolean stop() {
        return false;
    }

    /**
     *
     */
    public void update() {
        repair();
        flush();
    }
}
