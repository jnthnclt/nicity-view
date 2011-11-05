package colt.nicity.view.rpp;

import colt.nicity.core.collection.CSet;
import colt.nicity.core.lang.NullOut;
import colt.nicity.core.lang.UString;
import colt.nicity.core.lang.UText;
import colt.nicity.view.border.LineBorder;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.concurrent.VConcurrentEditText;
import colt.nicity.view.core.AFont;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VButton;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VPan;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.ngraph.NG;
import colt.nicity.view.ngraph.NGEnvAnim;
import colt.nicity.view.value.VFiles;
import java.awt.Font;
import java.io.File;

public class VTextEditor extends Viewer implements IRPPViewable {

    public static IView viewable(String[] args) {
        return new VTextEditor();
    }
    String name;
    CSet editors = new CSet();
    boolean graphing = false;

    VTextEditor() {
        this("untitled");
    }

    VTextEditor(String _name) {
        name = _name;

        String[] t = new String[]{
            "Example: We the People of the United States, in Order to form a more perfect Union,",
            "establish Justice, insure domestic Tranquility, provide for the common defence,",
            "promote the general Welfare, and secure the Blessings of Liberty to ourselves",
            "and our Posterity, do ordain and establish this Constitution for the United States",
            "of America."
        };
        final VConcurrentEditText text = new VConcurrentEditText(t, new AFont(Font.MONOSPACED, Font.PLAIN, 14));
        final VPan pan = new VPan(text, 600, 800);
        pan.setBorder(new LineBorder());


        VChain f = new VChain(UV.cEW);
        f.add(new VButton("Open") {

            @Override
            public void picked(IEvent _e) {
                VFiles f = new VFiles(new File(System.getProperty("user.dir")), "Save", true);
                f.init(false, true, 400, 400);
                UV.popup(this, UV.cNS, f, true, true);
                File open = f.waitForSet();
                text.setText(UText.loadTextFile(open));
            }
        });
        f.add(new VButton("Graph Text") {

            @Override
            public void picked(IEvent _e) {
                if (graphing) {
                    graphing = !graphing;
                    setView(new VString("Graph Text"));
                    pan.setView(text);
                } else {
                    graphing = !graphing;
                    setView(new VString("Edit Text"));
                    NGEnvAnim env = new NGEnvAnim((int) pan.getW(), (int) pan.getH());
                    NG ng = new NG();
                    String t = UString.toString(text.getText(), " ");
                    String[] words = UString.toStringArray(t, " ");
                    for (int i = 1; i < words.length; i++) {
                        ng.order(words[i - 1], words[i]);
                    }
                    env.set(NullOut.cNull, ng);
                    pan.setView(env);
                }
                paint();
            }
        });
        f.add(new VButton("Save") {

            @Override
            public void picked(IEvent _e) {
                VFiles f = new VFiles(new File(System.getProperty("user.dir")), "Save", true);
                f.init(false, true, 400, 400);
                UV.popup(this, UV.cNS, f, true, true);
                File saveTo = f.waitForSet();
                UText.saveTextFile(text.getText(), saveTo);
            }
        });
        f.setBorder(new ViewBorder());


        setContent(new VChain(UV.cSN, f,new Viewer(pan)));
    }
}
