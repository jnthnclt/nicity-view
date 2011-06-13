package colt.nicity.view.rpp;

import colt.nicity.core.collection.CSet;
import colt.nicity.core.collection.keyed.KeyedValue;
import colt.nicity.view.border.ViewBorder;
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
        _view.setBorder(new ViewBorder());
        RPPWindow v = new RPPWindow(_view);
        KeyedValue.add(views, _key, v);
    }

    /**
     * 
     * @param _key
     * @return
     */
    public RPPWindow view(long _who,String _key) {
        
        RPPWindow view = KeyedValue.get(views,_key);
        if (view == null) {
            register(new RPPHome(""),_key);
            view =  KeyedValue.get(views,_key);
        }
        return view;
    }

    /**
     * 
     * @param _key
     */
    public void release(String _key) {
        KeyedValue.remove(views, _key);
    }
}
