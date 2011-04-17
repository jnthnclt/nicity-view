/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package colt.nicity.view.rpp;

import colt.nicity.view.border.MenuBorder;
import colt.nicity.view.border.MenuItemBorder;
import colt.nicity.view.border.ViewBorder;
import colt.nicity.view.core.UV;
import colt.nicity.view.core.VChain;
import colt.nicity.view.core.VString;
import colt.nicity.view.core.Viewer;
import colt.nicity.view.interfaces.IEvent;
import colt.nicity.view.interfaces.IView;
import colt.nicity.view.list.VItem;

/**
 *
 * @author jonathan
 */
public class RPPHome extends Viewer {
    RPPViews views;
    Viewer menu = new Viewer();
    Viewer content = new Viewer();
    RPPHome(RPPViews _views) {
        views = _views;
        VChain c = new VChain(UV.cSN);
        c.add(menu);
        c.add(content);
        setContent(c);
        menu.setView(menu());
        content.setView(welcome());
        setBorder(new ViewBorder());
    }
    final IView menu() {
        VChain m = new VChain(UV.cEW);
        m.add(UV.border(new VItem("Launch") {

            @Override
            public void picked(IEvent _e) {
                content.setView(new VTextEditor(views, "Example"));
            }
            
        },new MenuItemBorder()));
        m.add(UV.border(new VItem("Login") {

            @Override
            public void picked(IEvent _e) {
                content.setView(welcome());
            }

        },new MenuItemBorder()));
        m.setBorder(new MenuBorder());
        return UV.pad(m);
    }

    final IView welcome() {
        VChain m = new VChain(UV.cEW);
        m.add(new VString("Welcome to RPP."));
        return UV.pad(m);
    }
}
