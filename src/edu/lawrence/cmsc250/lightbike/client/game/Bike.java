package edu.lawrence.cmsc250.lightbike.client.game;

import java.util.ArrayList;
import java.util.List;

import edu.lawrence.cmsc250.lightbike.client.game.physics.Direction;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Point2D;
import static edu.lawrence.cmsc250.lightbike.client.game.Constants.START_OFFSET;

/**
 * @author thislooksfun
 */
public class Bike
{
	/** Player 1 - Starts bottom-left moving right  --  color: red */
	public static final Bike bike1 = new Bike(new Point2D(-1 * START_OFFSET, -1 * START_OFFSET), Direction.RIGHT, BikeColor.RED);
	/** Player 2 - Starts top-right moving left  --  color: blue */
	public static final Bike bike2 = new Bike(new Point2D(START_OFFSET, START_OFFSET), Direction.LEFT, BikeColor.BLUE);
	/** Player 3 - Starts bottom-right moving up  --  color: green */
	public static final Bike bike3 = new Bike(new Point2D(START_OFFSET, -1 * START_OFFSET), Direction.UP, BikeColor.GREEN);
	/** Player 4 - Starts top-left moving down  --  color: yellow */
	public static final Bike bike4 = new Bike(new Point2D(-1 * START_OFFSET, START_OFFSET), Direction.DOWN, BikeColor.YELLOW);
	
	public static int bikeCount = -1;
	private final BikeColor color;
	private final List<Point2D> path = new ArrayList<>();
	private Point2D location;
	private Direction direction;
	
	private Bike(Point2D startPos, Direction direction, BikeColor color)
	{
		this.location = startPos;
		this.path.add(startPos);
		this.direction = direction;
		this.color = color;
	}
	
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
	
	public void updateFrom(String updateFrom)
	{
		// Bike format:
		// {location}>{turn?}>{direction?}
		String[] data = updateFrom.split(">");
		this.location = new Point2D(data[0]);
		if (data.length == 3) {
			this.path.add(new Point2D(data[1]));
			this.direction = Direction.fromInt(Integer.parseInt(data[2]));
		}
	}
	
	public enum BikeColor
	{
		RED,
		BLUE,
		GREEN,
		YELLOW;
	}
}