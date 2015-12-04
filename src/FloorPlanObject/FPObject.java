package FloorPlanObject;

import java.awt.Color;
import java.awt.Point;

/**
 * Super Class of floor plan objects
 * @author DongKyu
 *
 */
public abstract class FPObject {

	abstract public Point getStartPosition();
	abstract public void setStartPosition(Point p);
	abstract public Point getEndPosition();
	abstract public void setEndPosition(Point p);
	abstract public ObjectType getType();
	abstract public VertexType getIntersectVertex(Point p);
	abstract public Point getIntersectPosition(VertexType t);
	abstract public Color getColor();
	abstract public void setColor(Color color);
}
