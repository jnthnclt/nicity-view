/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colt.nicity.view.adaptor;

/**
 *
 * @author jonathan
 */
public interface IClipboard {

    public void put(Object upt);

    public <G> G get(Class<? extends G> _class);
}
