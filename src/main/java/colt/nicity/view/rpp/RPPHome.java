/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colt.nicity.view.rpp;

import colt.nicity.core.collection.CArray;
import colt.nicity.core.lang.UClass;
import colt.nicity.view.border.BuldgeBorder;
import colt.nicity.view.border.MenuItemBorder;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VPan;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.list.VItem;
import colt.nicity.view.list.VList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonathan
 */
public class RPPHome extends Viewer {

    public static void main(String[] args) {
        ViewColor.onGray();
        UV.exitFrame(new RPPHome("target/nicity-view-1.0-SNAPSHOT.jar"), "");
    }
    String[] jars;
    Viewer menu = new Viewer();
    Viewer content = new Viewer();
    CArray<VItem> apps = new CArray<VItem>();

    public RPPHome(String... jars) {
        this.jars = jars;
        init();
        
        menu.setView(menu());
        menu.spans(UV.cXEW);
        content.setView(new VPan(new VList(apps, 1), 400, 600));
        
        VChain c = new VChain(UV.cSWNW);
        c.add(menu);
        c.add(content);
        setContent(c);
        setBorder(new ViewBorder());
    }

    private void init() {
        apps.removeAll();
        for (String jar : jars) {
            for (Class c : UClass.getClasseNamesInPackage(IRPPViewable.class, null, jar)) {
                apps.insertLast(new VItem(new VString(c.getName()), c) {

                    @Override
                    public void picked(IEvent _e) {
                        try {
                            Class c = (Class) getValue();
                            Class[] argTypes = new Class[]{String[].class};
                            Method m = c.getDeclaredMethod("viewable", argTypes);
                            System.out.println(m);
                            String[] args = new String[0];
                            IView v = (IView) m.invoke(null, (Object) args);
                            content.setView(v);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(RPPHome.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(RPPHome.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchMethodException ex) {
                            Logger.getLogger(RPPHome.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SecurityException ex) {
                            Logger.getLogger(RPPHome.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(RPPHome.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }

    }

    final IView menu() {
        VChain m = new VChain(UV.cEW);
        m.add(UV.border(new VItem("Apps") {

            @Override
            public void picked(IEvent _e) {
                init();
                content.setView(new VPan(new VList(apps, 1), 400, 600));
            }
        }, new MenuItemBorder()));
        m.setBorder(new BuldgeBorder(ViewColor.cThemeHighlight));
        m.spans(UV.cXEW);
        return m;
    }
}
