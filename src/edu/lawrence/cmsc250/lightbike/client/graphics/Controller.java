package edu.lawrence.cmsc250.lightbike.client.graphics;

import edu.lawrence.cmsc250.lightbike.client.game.Bike;
import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
import javafx.fxml.FXML;

public class Controller
{
	@FXML
	public void initialize()
	{
		System.out.println(Bike.bike1);
		Gateway.addEventHandler((event -> System.out.println(Bike.bike1)), Gateway.GameUpdateEvent.class);
	}
}