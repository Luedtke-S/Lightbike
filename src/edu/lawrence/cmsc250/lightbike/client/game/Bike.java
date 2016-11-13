package edu.lawrence.cmsc250.lightbike.client.game;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import edu.lawrence.cmsc250.lightbike.client.game.physics.Direction;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Point2D;
import static edu.lawrence.cmsc250.lightbike.client.game.Constants.START_OFFSET;

/**
 * @author thislooksfun
 */
public class Bike
{
	/** The pattern to validate the String passed into {@link #updateFrom(String)} */
	public static final Pattern BIKE_STRING_FORMAT = Pattern.compile("^" + Point2D.POINT_STRING_FORMAT + "(>\\d+>" + Point2D.POINT_STRING_FORMAT + ")?$");
	
	/** Player 1 - Starts bottom-left moving right  --  color: red */
	public static final Bike bike1 = new Bike(new Point2D(-1 * START_OFFSET, -1 * START_OFFSET), Direction.RIGHT, BikeColor.RED);
	/** Player 2 - Starts top-right moving left  --  color: blue */
	public static final Bike bike2 = new Bike(new Point2D(START_OFFSET, START_OFFSET), Direction.LEFT, BikeColor.BLUE);
	/** Player 3 - Starts bottom-right moving up  --  color: green */
	public static final Bike bike3 = new Bike(new Point2D(START_OFFSET, -1 * START_OFFSET), Direction.UP, BikeColor.GREEN);
	/** Player 4 - Starts top-left moving down  --  color: yellow */
	public static final Bike bike4 = new Bike(new Point2D(-1 * START_OFFSET, START_OFFSET), Direction.DOWN, BikeColor.YELLOW);
	
	/** How many players there are */
	public static int bikeCount = 2; //TODO: SET BACK TO -1
	
	/** The color of the bike */
	public final BikeColor color;
	/** The path the bike has followed - each point is a place the bike turned */
	private final List<Point2D> path = new ArrayList<>();
	/** The current location of the bike */
	private Point2D location;
	/** The direction the bike is moving */
	private Direction direction;
	
	/**
	 * Create a new bike object
	 *
	 * @param startPos  The start position
	 * @param direction The direction of travel
	 * @param color     The color
	 */
	public Bike(Point2D startPos, Direction direction, BikeColor color)
	{
		this.location = startPos;
		this.path.add(startPos);
		this.direction = direction;
		this.color = color;
	}
	
	/**
	 * Get a list of all the bikes in the current game
	 *
	 * @return A list of 2,3, or all 4 bikes, depending on {@link #bikeCount}
	 */
	public static Bike[] getBikes()
	{
		if (bikeCount < 2 || bikeCount > 4)
			throw new IllegalStateException("bikeCount must be >= 2 and <= 4");
		
		switch (bikeCount) {
			case 2:
				return new Bike[]{bike1, bike2};
			case 3:
				return new Bike[]{bike1, bike2, bike3};
			case 4:
				return new Bike[]{bike1, bike2, bike3, bike4};
		}
		
		throw new IllegalStateException("Something went very wrong");
	}
	
	/**
	 * Get the current location of the bike
	 *
	 * @return The current location of the bike
	 */
	public Point2D getLocation()
	{
		return location;
	}
	
	/**
	 * Get the current direction of travel of the bike
	 *
	 * @return The current direction this bike is travelling
	 */
	public Direction getDirection()
	{
		return direction;
	}
	
	/**
	 * Get the path the bike has followed
	 *
	 * @return The path the bike has followed
	 */
	public Point2D[] getPath()
	{
		return path.toArray(new Point2D[path.size()]);
	}
	
	/**
	 * Update this bike from the given String
	 *
	 * @param updateFrom The string to update from - MUST EITHER BE OF FORMAT {location} OR {location}>{direction}>{turn}
	 */
	public void updateFrom(String updateFrom)
	{
		// Bike format:
		// {location}>{direction?}>{turn?}
		if (!BIKE_STRING_FORMAT.matcher(updateFrom).matches())
			throw new IllegalArgumentException("Expected format '{location}' or '{location}>{direction}>{turn}' but got '" + updateFrom + "'");
		
		String[] data = updateFrom.split(">");
		this.location = new Point2D(data[0]);
		if (data.length == 3) {
			Direction newDirection = Direction.fromInt(Integer.parseInt(data[1]));
			if (newDirection != this.direction) {
				this.path.add(new Point2D(data[2]));
				this.direction = newDirection;
			}
		}
	}
	
	@Override
	public String toString()
	{
		String pathStr = "";
		for (Point2D p : path)
			pathStr += (pathStr.isEmpty() ? "" : ",") + p;
		return location + ">" + direction + ">" + pathStr;
	}
	
	/** The color of the bike */
	public enum BikeColor
	{
		/** The red color, or player 1 */
		RED,
		/** The blue color, or player 2 */
		BLUE,
		/** The green color, or player 3 */
		GREEN,
		/** The yellow color, or player 4 */
		YELLOW;
	}
}