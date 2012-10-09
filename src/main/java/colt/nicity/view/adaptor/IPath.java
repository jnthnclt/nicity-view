/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colt.nicity.view.adaptor;

/**
 *
 * @author jonathan
 */
public interface IPath {
    public Object getRawPath();

    public void moveTo(int x, int y);

    public void quadTo(float x1, float y1, float x2, float y2);

    public void lineTo(int i, int y);

    public void closePath();

    public void moveTo(float _sx, float _sy);

    public void lineTo(float f, float f0);

    public void curveTo(float f, float _fromY, float f0, float _fromY0, float midX, float midY);
}
