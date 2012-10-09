/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colt.nicity.view.adaptor;

/**
 *
 * @author jonathan
 */
public interface IFont {
    
    public Object getNativeFont();

    public int getSize();

    public int getStyle();

    public String getFontName();
    
    public int stringWidth(String string);
    
    public int stringHeight(String string);
    
    public int charWidth(char c);
    
    public int height();
    
    public int ascent();
    
    public int descent();
    
}
