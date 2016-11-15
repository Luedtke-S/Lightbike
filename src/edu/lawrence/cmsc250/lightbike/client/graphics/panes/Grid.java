package edu.lawrence.cmsc250.lightbike.client.graphics.panes;

import java.util.ArrayList;

import edu.lawrence.cmsc250.lightbike.client.game.Bike;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Direction;
import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import static edu.lawrence.cmsc250.lightbike.client.game.Constants.GRID_SIZE;

/**
 * Created by Samuel on 11/10/2016.
 */

public class Grid extends Pane
{
	
	private static final int screenWidth = 600;
	ArrayList<Node> grid = new ArrayList<>();
	ArrayList<BikePane> bikes = new ArrayList<>();
	
	public Grid()
	{
		loadGrid();
		loadBikes();
		draw();
	}
	
	public static void handleKeyEvent(KeyEvent key)
	{
		if (key.getCode() == KeyCode.LEFT) {
			Gateway.sendControlPressed(Direction.LEFT);
		}
		if (key.getCode() == KeyCode.RIGHT) {
			Gateway.sendControlPressed(Direction.RIGHT);
		}
	}
	
	public void draw()
	{
		for (BikePane b : bikes) {
			b.refresh();
		}
	}
	
	public void loadBikes()
	{
		
		for (Bike b : Bike.getBikes()) {
			bikes.add(new BikePane(b));
		}
		getChildren().addAll(bikes);
	}
	
	public void loadGrid()
	{
		
		int spacing = screenWidth / GRID_SIZE;
		
		for (int i = 0; i < GRID_SIZE; i++) {
			Line lineV = new Line();
			lineV.setStartX(spacing * i);
			lineV.setEndX(spacing * i);
			lineV.setStartY(0);
			lineV.setEndY(screenWidth);
			lineV.setStroke(Color.WHITE);
			lineV.setStrokeWidth(0.1);
			grid.add(lineV);
			
			Line lineH = new Line();
			lineH.setStartY(spacing * i);
			lineH.setEndY(spacing * i);
			lineH.setStartX(0);
			lineH.setEndX(screenWidth);
			lineH.setStroke(Color.WHITE);
			lineH.setStrokeWidth(0.1);
			grid.add(lineH);
		}
		getChildren().addAll(grid);
	}
}