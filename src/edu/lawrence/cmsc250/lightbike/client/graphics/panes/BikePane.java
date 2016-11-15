package edu.lawrence.cmsc250.lightbike.client.graphics.panes;

import edu.lawrence.cmsc250.lightbike.client.game.Bike;
import edu.lawrence.cmsc250.lightbike.client.game.Constants;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Direction;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import static edu.lawrence.cmsc250.lightbike.client.game.physics.Direction.*;

/**
 * Created by Samuel on 11/10/2016.
 */

public class BikePane extends Pane
{
	
	private final Bike bike;
	Rectangle playerBike = new Rectangle(10, 15);
	private int trailLength = 0;
	private Line currentTrail;
	
	public BikePane(Bike b)
	{
		this.bike = b;
		
		loadBike();
	}
	
	public void loadBike()
	{
		playerBike.setTranslateX(-playerBike.getWidth() / 2);
		playerBike.setTranslateY(-playerBike.getHeight() / 2);
		switch (bike.color) {
			case BLUE:
				playerBike.setFill(Constants.BLUE);
				break;
			case YELLOW:
				playerBike.setFill(Constants.YELLOW);
				break;
			case GREEN:
				playerBike.setFill(Constants.GREEN);
				break;
			case RED:
				playerBike.setFill(Constants.RED);
		}
		
		getChildren().add(playerBike);
		trailLength = 1;
		Point2D startPos = bike.getPath()[0].toGraphicsPosition();
		Point2D currentPos = bike.getLocation().toGraphicsPosition();
		currentTrail = new Line(startPos.x, startPos.y, currentPos.x, currentPos.y);
//		currentTrail.setStroke(playerBike.getFill());
		currentTrail.setStrokeWidth(3);
		getChildren().add(currentTrail);
		refresh();
	}
	
	public void refresh()
	{
		if (bike.crashed()) {
			this.getChildren().clear();
			return;
		}
		
		Point2D screenLoco = bike.getLocation().toGraphicsPosition();
		
		Direction dir = bike.getDirection();
		double xOffset = (dir == RIGHT ? -playerBike.getWidth() / 2 : (dir == LEFT ? playerBike.getWidth() / 2 : 0));
		double yOffset = (dir == DOWN ? -playerBike.getHeight() / 2 : (dir == UP ? playerBike.getHeight() / 2 : 0));
		playerBike.setX(screenLoco.x + xOffset);
		playerBike.setY(screenLoco.y + yOffset);
		
		playerBike.setRotate(90 * bike.getDirection().toInt());
		Point2D[] path = bike.getPath();
		if (path.length > trailLength) {
			Point2D last = path[path.length - 1].toGraphicsPosition();
			currentTrail.setEndX(last.x);
			currentTrail.setEndY(last.y);
			currentTrail = new Line(last.x, last.y, screenLoco.x + xOffset, screenLoco.y + yOffset);
//			currentTrail.setStroke(playerBike.getFill());
			currentTrail.setStrokeWidth(3);
			getChildren().add(currentTrail);
			trailLength++;
		} else {
			currentTrail.setEndX(screenLoco.x + xOffset);
			currentTrail.setEndY(screenLoco.y + yOffset);
		}
	}
}
