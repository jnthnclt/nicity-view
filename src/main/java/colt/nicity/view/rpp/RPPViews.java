package colt.nicity.view.rpp;

import colt.nicity.core.collection.CSet;
import colt.nicity.core.collection.keyed.KeyedValue;
import colt.nicity.view.border.WindowBorder;
import colt.nicity.view.core.ViewColor;
import colt.nicity.view.interfaces.IView;

/**
 * 
 * @author jonathan
 */
public class RPPViews {

    /**
     * 
     */
    public RPPViews() {
    }
    private CSet<KeyedValue<String, RPPWindow>> views = new CSet<KeyedValue<String, RPPWindow>>();

    /**
     * 
     * @param _view
     * @param _key
     */
    public void register(IView _view, String _key) {
        _view.setBorder(new WindowBorder(ViewColor.cWindowTheme, 1));
        RPPWindow v = new RPPWindow(_view);
        KeyedValue.add(views, _key, v);
    }

    /**
     * 
     * @param _key
     * @return
     */
    public RPPWindow view(String _key) {
        return KeyedValue.get(views, _key);
    }

    /**
     * 
     * @param _key
     */
    public void release(String _key) {
        KeyedValue.remove(views, _key);
    }
}
