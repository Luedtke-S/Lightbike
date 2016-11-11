package edu.lawrence.cmsc250.lightbike.client.game.physics;

/**
 * Holds an ordinal direction (Up, Down, Left, Right)
 *
 * @author thislooksfun
 */
public enum Direction
{
	UP,
	RIGHT,
	DOWN,
	LEFT;
	
	/**
	 * Get the Direction represented by a certain {@code int}
	 *
	 * @param i The {@code int} to convert from
	 *
	 * @return The Direction represented by the given {@code int}, or {@code null} if the given {@code int} was invalid
	 */
	public static Direction fromInt(int i)
	{
		switch (i) {
			case 0:
				return UP;
			case 1:
				return RIGHT;
			case 2:
				return DOWN;
			case 3:
				return LEFT;
		}
		return null;
	}
	
	/**
	 * Get the Direction one turn to the left of the current Direction
	 * <p>
	 * {@link #UP} -> {@link #LEFT} -> {@link #DOWN} -> {@link #RIGHT} -> {@link #UP}
	 *
	 * @return The Direction one to the left of the current Direction
	 */
	public Direction turnLeft()
	{
		return Direction.fromInt((this.toInt() + 3) % 4);
	}
	
	/**
	 * Get the Direction one turn to the right of the current Direction
	 * <p>
	 * {@link #UP} -> {@link #RIGHT} -> {@link #DOWN} -> {@link #LEFT} -> {@link #UP}
	 *
	 * @return The Direction one to the right of the current Direction
	 */
	public Direction turnRight()
	{
		return Direction.fromInt((this.toInt() + 5) % 4);
	}
	
	/**
	 * Converts this Direction to a corresponding integer representation
	 *
	 * @return An integer representation of this Direction
	 */
	public int toInt()
	{
		return this.ordinal();
	}
}