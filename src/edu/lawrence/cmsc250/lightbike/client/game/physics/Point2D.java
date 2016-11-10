package edu.lawrence.cmsc250.lightbike.client.game.physics;

/**
 * @author thislooksfun
 */
public class Point2D
{
	public final int x;
	public final int y;
	
	public Point2D(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (!(obj instanceof Point2D))
			return false;
		
		Point2D p = (Point2D)obj;
		return p.x == this.x && p.y == this.y;
	}
}