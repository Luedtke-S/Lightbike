package edu.lawrence.cmsc250.lightbike.client.game;

import javafx.scene.paint.Color;

/**
 * @author thislooksfun
 */
public class Constants
{
	/** The size of the game grid */
	public static final int GRID_SIZE = 60;
	/** How far from the edges the bikes should start */
	public static final int START_OFFSET = (GRID_SIZE / 2) - 4;
	
	public static final double GRID_SCREEN_SIZE = 600;
	public static final int SCREEN_MARGIN = 20;
	
	public static final double GRID_SCALE = GRID_SCREEN_SIZE / GRID_SIZE;
	
	public static final Color RED = new Color(1, 0, 0, 1);
	public static final Color BLUE = new Color(0, 0, 1, 1);
	public static final Color GREEN = new Color(0, 1, 0, 1);
	public static final Color YELLOW = new Color(1, 1, 0, 1);
	public static final Color CRASHED_TEXT = new Color(0.9, 0.9, 0.9, 1);
	public static final Color GAME_OVER_TEXT = new Color(0.9, 0.9, 0.9, 1);
	public static final Color YOU_COLOR = new Color(0.9, 0.9, 0.9, 1);
	public static final Color READY = new Color(0.25, 0.5, 0.25, 1);
	public static final Color NOT_READY = new Color(0.5, 0.25, 0.25, 1);
	public static final Color EMPTY_SLOT = new Color(0.4, 0.4, 0.4, 1);
}