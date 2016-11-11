package edu.lawrence.cmsc250.lightbike.client.game.physics;

/**
 * @author thislooksfun
 */
public class Point2D
{
	public final double x;
	public final double y;
	
	public Point2D(String loadFrom)
	{
		// Point format:
		// {x}:{y}
		String[] data = loadFrom.split(":");
		this.x = Double.parseDouble(data[0]);
		this.y = Double.parseDouble(data[1]);
	}
	
	public Point2D(double x, double y)
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