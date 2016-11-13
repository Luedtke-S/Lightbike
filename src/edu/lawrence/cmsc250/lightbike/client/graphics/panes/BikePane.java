package edu.lawrence.cmsc250.lightbike.client.graphics.panes;

import edu.lawrence.cmsc250.lightbike.client.game.Bike;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Samuel on 11/10/2016.
 */

public class BikePane extends Pane
{
	private static final Color RED = new Color(1, 0, 0, 1);
	private static final Color BLUE = new Color(0, 0, 1, 1);
	private static final Color GREEN = new Color(0, 1, 0, 1);
	private static final Color YELLOW = new Color(1, 1, 0, 1);
	private final Bike bike;
	Rectangle playerBike = new Rectangle(10, 15);
	
	public BikePane(Bike b)
	{
		this.bike = b;
		
		loadBike();
	}
	
	public void loadBike()
	{
		switch (bike.color) {
			case BLUE:
				playerBike.setFill(BLUE);
				break;
			case YELLOW:
				playerBike.setFill(YELLOW);
				break;
			case GREEN:
				playerBike.setFill(GREEN);
				break;
			case RED:
				playerBike.setFill(RED);
		}
		refresh();
		getChildren().add(playerBike);
	}
	
	public void refresh()
	{
		Point2D screenLoco = bike.getLocation().toGraphicsPosition();
		playerBike.setX(screenLoco.x);
		playerBike.setY(screenLoco.y);
		playerBike.setRotate(90*bike.getDirection().toInt());
		
	}
}
