package edu.lawrence.cmsc250.lightbike.client.graphics;

import edu.lawrence.cmsc250.lightbike.client.graphics.panes.Grid;
import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class Controller
{
	
	@FXML
	public Pane gridWindow;
	
	@FXML
	public void initialize()
	{
		Grid grid = new Grid();
		
		Gateway.addEventHandler((evt) -> grid.draw(), Gateway.GameUpdateEvent.class);
		
		gridWindow.getChildren().add(grid);
		gridWindow.requestFocus();
	}
}
