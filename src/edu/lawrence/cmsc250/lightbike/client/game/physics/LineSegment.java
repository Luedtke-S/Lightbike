package edu.lawrence.cmsc250.lightbike.client.game.physics;

/**
 * @author thislooksfun
 */
public class LineSegment
{
	/** The first endpoint of the line */
	public final Point2D pointA;
	/** The second endpoint of the line */
	public final Point2D pointB;
	
	/**
	 * Make a new LineSegment
	 *
	 * @param pointA The first endpoint of the line
	 * @param pointB The second endpoint of the line
	 */
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