package edu.lawrence.cmsc250.lightbike.client.game.physics;

/**
 * @author thislooksfun
 */
public class LineSegment
{
	public final Point2D pointA;
	public final Point2D pointB;
	
	public LineSegment(int xA, int yA, int xB, int yB)
	{
		this(new Point2D(xA, yA), new Point2D(xB, yB));
	}
	
	public LineSegment(Point2D pointA, Point2D pointB)
	{
		this.pointA = pointA;
		this.pointB = pointB;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (!(obj instanceof LineSegment))
			return false;
		
		LineSegment ls = (LineSegment)obj;
		return ls.pointA == this.pointA && ls.pointB == this.pointB;
	}
}