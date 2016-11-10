package edu.lawrence.cmsc250.lightbike.client.game;

import edu.lawrence.cmsc250.lightbike.client.game.physics.Point2D;

/**
 * @author thislooksfun
 */
public class Bike
{
	private final BikeColor color;
	private Point2D location;
	private Point2D[] path;
	
	public Bike(Point2D startPos, BikeColor color)
	{
		this.location = startPos;
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