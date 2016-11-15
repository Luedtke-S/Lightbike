package edu.lawrence.cmsc250.lightbike.client;

import edu.lawrence.cmsc250.lightbike.client.graphics.ChooseRoomDialogController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public static Stage root;	
	
	@Override
	public void start(Stage primaryStage)
	{
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
