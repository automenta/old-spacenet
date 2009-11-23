/**
 * 
 */
package automenta.spacenet.space.object;

import automenta.spacenet.space.geom2.Rect;

public interface RectBuilder<Y> {
	public Rect newRect(Y y);
}