/*
 * VEditFontSlim.java.java
 *
 * Created on 03-12-2010 06:41:54 PM
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

import colt.nicity.view.list.VItem;
import colt.nicity.view.list.VList;
import colt.nicity.core.collection.CArray;
import colt.nicity.core.observer.Change;
import colt.nicity.core.observer.IObservable;
import colt.nicity.core.observer.IObserver;
import colt.nicity.core.value.Value;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VPan;
import colt.nicity.view.core.VPopupButton;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.interfaces.IViewable;

/**
 *
 * @author Administrator
 */
public class VEditFontSlim extends Viewer implements IObserver {
    /**
     *
     * @param _args
     */
    public static void main(String[] _args) {
        ViewColor.onBlack();
        UV.exitFrame(new Viewer(new VEditFontSlim(new Value<AFont>(UV.fonts[UV.cText]))), "");
    }
    Value<AFont> font;

    /**
     *
     * @param _font
     */
    public VEditFontSlim(Value<AFont> _font) {
        font = _font;
        final Value<String> name = new Value<String>() {

            @Override
            public void setValue(String _v) {
            }

            @Override
            public String getValue() {
                return font.getValue().getFontName();
            }
        };
        final Value<Integer> style = new Value<Integer>() {

            @Override
            public void setValue(Integer _v) {
                AFont f = font.getValue();
                font.setValue(new AFont(f.getFontName(), _v, f.getSize()));
                super.setValue(_v);
            }

            @Override
            public Integer getValue() {
                return font.getValue().getStyle();
            }
        };
        final Value<Integer> size = new Value<Integer>() {

            @Override
            public void setValue(Integer _v) {
                AFont f = (font.getValue());
                font.setValue(new AFont(f.getFontName(), f.getStyle(), _v));
                super.setValue(_v);
            }

            @Override
            public Integer getValue() {
                return font.getValue().getSize();
            }
        };

        VChain c = new VChain(UV.cEW);
        c.add(new VPopupButton(new VString(name), new IViewable() {

            public IView getView() {
                return null;
            }
        }));
        c.add(4, 4);
        c.add(new VPopupButton(new VString(style), new IViewable() {

            public IView getView() {
                VChain c = new VChain(UV.cSWNW);
                c.add(new VItem("Plain") {

                    @Override
                    public void picked(IEvent _e) {
                        style.setValue(AFont.cPlain);
                    }
                });
                c.add(new VItem("Bold") {

                    @Override
                    public void picked(IEvent _e) {
                        style.setValue(AFont.cBold);
                    }
                });
                c.add(new VItem("Italic") {

                    @Override
                    public void picked(IEvent _e) {
                        style.setValue(AFont.cItalic);
                    }
                });
                c.add(new VItem("Bold Italic") {

                    @Override
                    public void picked(IEvent _e) {
                        style.setValue(AFont.cBold | AFont.cItalic);
                    }
                });
                return c;
            }
        }));
        c.add(4, 4);
        c.add(new VPopupButton(new VString(size), new IViewable() {

            public IView getView() {
                CArray sizes = new CArray();
                for (int i = 1; i < 300; i++) {
                    sizes.insertLast(new VItem(new VString("" + i), i) {

                        @Override
                        public void picked(IEvent _e) {
                            size.setValue((Integer)getValue());
                        }
                    });
                }
                VList list = new VList(sizes, 1);
                list.setComparator(null);
                VPan p = new VPan(list, 100, 400);
                return p;
            }
        }));
        setContent(c);
        font.bind(this);
    }

    /**
     *
     * @param _change
     */
    public void change(Change _change) {
        paint();
    }

    /**
     *
     * @param _observable
     */
    public void bound(IObservable _observable) {
        paint();
    }

    /**
     *
     * @param _observable
     */
    public void released(IObservable _observable) {
        paint();
    }
}

