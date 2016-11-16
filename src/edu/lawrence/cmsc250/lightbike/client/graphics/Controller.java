package edu.lawrence.cmsc250.lightbike.client.graphics;

import java.io.IOException;

import edu.lawrence.cmsc250.lightbike.client.Main;
import edu.lawrence.cmsc250.lightbike.client.game.Bike;
import edu.lawrence.cmsc250.lightbike.client.graphics.panes.Grid;
import edu.lawrence.cmsc250.lightbike.client.networking.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import static edu.lawrence.cmsc250.lightbike.client.game.Constants.GRID_SCREEN_SIZE;
import static edu.lawrence.cmsc250.lightbike.client.game.Constants.SCREEN_MARGIN;

public class Controller
{
	@FXML
	public Pane gridWindow;
	
	public static void show(int playerCount)
	{
		Bike.init(playerCount);
		
		try {
			FXMLLoader loader = new FXMLLoader(Controller.class.getResource("arena.fxml"));
			Parent root = loader.load();
			double size = GRID_SCREEN_SIZE + (SCREEN_MARGIN * 2);
			Scene scene = new Scene(root, size, size);
			Main.root.setScene(scene);
			Main.root.setTitle("It's on!");
			Main.root.setResizable(false);
			Controller c = loader.getController();
			c.registerKeys();
			Gateway.finishedSetup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	@SuppressWarnings("unused")
	public void initialize()
	{
		Grid grid = new Grid();
		
		Gateway.setEventHandler(evt -> grid.draw(), GameUpdateEvent.class);
		Gateway.setEventHandler(evt -> grid.crashed(), YouCrashedEvent.class);
		Gateway.setEventHandler(grid::done, GameFinishedEvent.class);
		Gateway.setEventHandler(evt -> {
			GameUpdateEvent.reset();
			Gateway.sendLeaveRoom();
			ChooseRoomDialogController.show();
		}, RoomUpdateEvent.class);
		
		gridWindow.getChildren().add(grid);
		gridWindow.requestFocus();
	}
	
	private void registerKeys()
	{
		Grid g = (Grid)gridWindow.getChildren().get(0);
		gridWindow.getScene().setOnKeyPressed(g::handleKeyEvent);
	}
}
