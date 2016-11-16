package edu.lawrence.cmsc250.lightbike.client;

import java.util.Optional;
import java.util.regex.Pattern;

import edu.lawrence.cmsc250.lightbike.client.game.Constants;
import edu.lawrence.cmsc250.lightbike.client.graphics.ChooseRoomDialogController;
import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class Main extends Application
{
	private static final Pattern IP_MATCH = Pattern.compile("^(?:(?:[0-9]{1,3}\\.){3}[0-9]{1,3})|(?:localhost)$");
	public static Stage root;
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		TextInputDialog tid = new TextInputDialog("143.44.68.178");
		tid.setHeaderText("Enter IP");
		Optional<String> ip = tid.showAndWait();
		
		if (!ip.isPresent() || !IP_MATCH.matcher(ip.get()).matches())
			throw new IllegalStateException("Invalid IP address");
		
		Constants.ip = ip.get();
		
		try {
			root = primaryStage;
			ChooseRoomDialogController.show();
			root.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception
	{
		System.exit(0);
	}
}
