package edu.lawrence.cmsc250.lightbike.client.game;

import java.util.ArrayList;
import java.util.List;

import edu.lawrence.cmsc250.lightbike.client.game.physics.Direction;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Point2D;

/**
 * @author thislooksfun
 */
public class Bike
{
	private final BikeColor color;
	private final List<Point2D> path = new ArrayList<>();
	private Point2D location;
	private Direction direction;
	
	public Bike(Point2D startPos, Direction direction, BikeColor color)
	{
		this.location = startPos;
		this.path.add(startPos);
		this.direction = direction;
		this.color = color;
	}
	
	public enum BikeColor
	{
		RED,
		BLUE,
		GREEN,
		YELLOW;
	}
}