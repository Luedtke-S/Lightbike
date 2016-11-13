package edu.lawrence.cmsc250.lightbike.client.game.physics;

import java.util.regex.Pattern;

import edu.lawrence.cmsc250.lightbike.client.game.Constants;
import static edu.lawrence.cmsc250.lightbike.client.game.Constants.GRID_SCALE;

/**
 * @author thislooksfun
 */
public class Point2D
{
	/** The bare string format to match a Point2D object */
	public static final String POINT_STRING_FORMAT = "-?\\d+(\\.\\d+)?:-?\\d+(\\.\\d+)?";
	
	/** The pattern to validate the String passed into {@link #Point2D(String)} */
	public static final Pattern POINT_STRING_FORMAT_PATTERN = Pattern.compile("^" + POINT_STRING_FORMAT + "$");
	
	/** The X position of this point */
	public final double x;
	/** The Y position of this point */
	public final double y;
	
	/**
	 * Make a new Point2D object from a String
	 *
	 * @param loadFrom The String to load from - MUST BE OF FORM {double}:{double}
	 */
	public Point2D(String loadFrom)
	{
		// Point format:
		// {x}:{y}
		if (!POINT_STRING_FORMAT_PATTERN.matcher(loadFrom).matches())
			throw new IllegalArgumentException("Expected form '{double}:{double}' got '" + loadFrom + "'");
		
		String[] data = loadFrom.split(":");
		this.x = Double.parseDouble(data[0]);
		this.y = Double.parseDouble(data[1]);
	}
	
	/**
	 * Make a new Point2D object from X and Y coordinates
	 *
	 * @param x The X position
	 * @param y The Y position
	 */
	public Point2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Converts this point to graphics coordinates
	 *
	 * @return A Point2D object centered around the top left, rather than the center
	 */
	public Point2D toGraphicsPosition()
	{
		double gX = this.x + (Constants.GRID_SIZE / 2.0);
		double gY = this.y + (Constants.GRID_SIZE / 2.0);
		return new Point2D(gX * GRID_SCALE, gY * GRID_SCALE);
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
	
	@Override
	public String toString()
	{
		return x + ":" + y;
	}
}