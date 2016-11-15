package edu.lawrence.cmsc250.lightbike.client.graphics.panes;

import java.util.ArrayList;

import edu.lawrence.cmsc250.lightbike.client.game.Bike;
import edu.lawrence.cmsc250.lightbike.client.game.Constants;
import edu.lawrence.cmsc250.lightbike.client.game.physics.Direction;
import edu.lawrence.cmsc250.lightbike.client.networking.GameFinishedEvent;
import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import static edu.lawrence.cmsc250.lightbike.client.game.Constants.*;

/**
 * Created by Samuel on 11/10/2016.
 */
public class Grid extends Pane
{
	ArrayList<Node> grid = new ArrayList<>();
	ArrayList<BikePane> bikes = new ArrayList<>();
	
	private Text crashedText = new Text("You Crashed");
	private Text finishedText = new Text("Game Over");
	private Text winnerText = new Text("Winner: Player 1");
	
	private boolean crashed = false;
	private boolean finished = false;
	
	public Grid()
	{
		loadGrid();
		loadBikes();
		
		crashedText.setTextOrigin(VPos.CENTER);
		crashedText.setTextAlignment(TextAlignment.CENTER);
		crashedText.setFont(new Font(50));
		crashedText.setFill(Constants.CRASHED_TEXT);
		crashedText.setX(SCREEN_MARGIN + (GRID_SCREEN_SIZE / 2) - crashedText.getBoundsInLocal().getWidth() / 2);
		crashedText.setY(SCREEN_MARGIN + (GRID_SCREEN_SIZE / 2) - crashedText.getBoundsInLocal().getHeight() / 2);
		crashedText.setOpacity(0);
		this.getChildren().add(crashedText);
		
		finishedText.setTextOrigin(VPos.CENTER);
		finishedText.setTextAlignment(TextAlignment.CENTER);
		finishedText.setFont(new Font(50));
		finishedText.setFill(Constants.GAME_OVER_TEXT);
		finishedText.setX(SCREEN_MARGIN + (GRID_SCREEN_SIZE / 2) - finishedText.getBoundsInLocal().getWidth() / 2);
		finishedText.setY(SCREEN_MARGIN + (GRID_SCREEN_SIZE / 2) - finishedText.getBoundsInLocal().getHeight());
		finishedText.setOpacity(0);
		this.getChildren().add(finishedText);

		winnerText.setTextOrigin(VPos.CENTER);
		winnerText.setTextAlignment(TextAlignment.CENTER);
		winnerText.setFont(new Font(30));
		winnerText.setFill(Constants.GAME_OVER_TEXT);
		winnerText.setX(SCREEN_MARGIN + (GRID_SCREEN_SIZE / 2) - winnerText.getBoundsInLocal().getWidth() / 2);
		winnerText.setY(SCREEN_MARGIN + (GRID_SCREEN_SIZE / 2));
		winnerText.setOpacity(0);
		this.getChildren().add(winnerText);
		
		draw();
	}
	
	public static void handleKeyEvent(KeyEvent key)
	{
		switch (key.getCode()) {
			case LEFT:
				Gateway.sendControlPressed(Direction.LEFT);
				break;
			case RIGHT:
				Gateway.sendControlPressed(Direction.RIGHT);
				break;
			case UP:
				Gateway.sendControlPressed(Direction.UP);
				break;
			case DOWN:
				Gateway.sendControlPressed(Direction.DOWN);
				break;
			case ESCAPE:
				Gateway.sendLeaveRoom();
				break;
			case R:
				Gateway.rematch();
		}
	}
	
	public void draw()
	{
		crashedText.setOpacity((!finished && crashed) ? 1 : 0);
		finishedText.setOpacity(finished ? 1 : 0);
		winnerText.setOpacity(finished ? 1 : 0);
		
		for (BikePane b : bikes)
			b.refresh();
	}
	
	public void crashed()
	{
		crashed = true;
	}
	
	public void done(GameFinishedEvent event)
	{
		finished = true;
		String txt = winnerText.getText();
		winnerText.setText(txt.substring(0, txt.length() - 1) + event.winner);
		draw();
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
		double spacing = GRID_SCREEN_SIZE / GRID_SIZE;
		
		for (int i = 0; i <= GRID_SIZE; i++) {
			Line lineV = new Line();
			lineV.setStartX(SCREEN_MARGIN + (spacing * i));
			lineV.setEndX(SCREEN_MARGIN + (spacing * i));
			lineV.setStartY(SCREEN_MARGIN);
			lineV.setEndY(SCREEN_MARGIN + GRID_SCREEN_SIZE);
			lineV.setStroke(Color.WHITE);
			lineV.setStrokeWidth(0.1);
			grid.add(lineV);
			
			Line lineH = new Line();
			lineH.setStartY(SCREEN_MARGIN + (spacing * i));
			lineH.setEndY(SCREEN_MARGIN + (spacing * i));
			lineH.setStartX(SCREEN_MARGIN);
			lineH.setEndX(SCREEN_MARGIN + GRID_SCREEN_SIZE);
			lineH.setStroke(Color.WHITE);
			lineH.setStrokeWidth(0.1);
			grid.add(lineH);
		}
		getChildren().addAll(grid);
	}
}