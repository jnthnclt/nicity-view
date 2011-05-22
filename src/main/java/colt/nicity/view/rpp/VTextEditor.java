package colt.nicity.view.rpp;

import colt.nicity.core.collection.CSet;
import colt.nicity.core.lang.NullOut;
import colt.nicity.core.lang.UString;
import colt.nicity.view.border.ItemBorder;
import colt.nicity.view.border.LineBorder;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.concurrent.VCText;
import colt.nicity.view.core.AColor;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.RigidBox;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VPan;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.VTrapFlex;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.list.AItem;
import colt.nicity.view.list.VList;
import colt.nicity.view.ngraph.NG;
import colt.nicity.view.ngraph.NGEnvAnim;
import colt.nicity.view.paint.area.TestPainter;
import colt.nicity.view.value.VAlwaysOver;
import colt.nicity.view.value.VFiles;
import java.awt.Font;
import java.io.File;

public class VTextEditor extends Viewer {
    RPPViews views;
    String name;
    CSet editors = new CSet();
    boolean graphing = false;
    VTextEditor(RPPViews _views,String _name) {
        views = _views;
        name = _name;

        String[] t = new String[]{
            "Example: We the People of the United States, in Order to form a more perfect Union,",
            "establish Justice, insure domestic Tranquility, provide for the common defence,",
            "promote the general Welfare, and secure the Blessings of Liberty to ourselves",
            "and our Posterity, do ordain and establish this Constitution for the United States",
            "of America."
        };
        final VCText text = new VCText(t,new AFont(Font.MONOSPACED,Font.PLAIN,14));
        VPan panEditors = new VPan(new VList(editors,1),150,800);
        panEditors.setBorder(new LineBorder());
        final VPan pan = new VPan(text,600,800);
        pan.setBorder(new LineBorder());


        VChain f = new VChain(UV.cEW);
        f.add(new VButton("Reset") {
            @Override
            public void picked(IEvent _e) {
                views.release("Text");
            }
        });
        f.add(new VButton("Graph Text") {
            @Override
            public void picked(IEvent _e) {
                if (graphing) {
                    graphing = !graphing;
                    setView(new VString("Graph Text"));
                    pan.setView(text);
                }
                else {
                    graphing = !graphing;
                    setView(new VString("Edit Text"));
                    NGEnvAnim env = new NGEnvAnim((int)pan.getW(),(int)pan.getH());
                    NG ng = new NG();
                    String t = UString.toString(text.getText()," ");
                    String[] words = UString.toStringArray(t," ");
                    for(int i=1;i<words.length;i++) {
                        ng.order(words[i-1], words[i]);
                    }
                    env.set(NullOut.cNull, ng);
                    pan.setView(env);
                }
                paint();
            }
        });
        f.add(new VButton("TestPainter") {
            @Override
            public void picked(IEvent _e) {
                pan.setView(TestPainter.painter());
                paint();
            }
        });
        f.add(new VButton("Config") {
            @Override
            public void picked(IEvent _e) {
                UV.popup(this,UV.cNS, new VPan(ViewColor.edit(),-1,400), true,true);
            }
        });
        f.add(new VButton("Save") {
            @Override
            public void picked(IEvent _e) {
                VFiles f = new VFiles(new File(System.getProperty("user.dir")), "Save", true);
                f.init(false, true, 400, 400);
                UV.popup(this,UV.cNS,f,true,true);
            }
        });
        f.setBorder(new ViewBorder());
       

        VChain ew = new VChain(UV.cNENW);
        ew.add(new Viewer(pan),UV.cFIG);
        ew.add(new Viewer(panEditors),UV.cFIG);
        setContent(new VChain(UV.cSN,ew,f));
    }
    public void addEditor(long _who,String _ipAddress) {
        Editor e = (Editor)editors.get(_who);
        if (e == null) {
            editors.add(new Editor(_who, _ipAddress));
        }
        else {
            
        }
    }
    class Editor extends AItem {
        long who;
        Editor(long _who,String _name) {
            who = _who;
            setContent(new VString(_name));
            setBorder(new ItemBorder(AColor.getHashSolid((Long)who)));
        }
        @Override
        public Object getValue() { return who; }
    }
}
