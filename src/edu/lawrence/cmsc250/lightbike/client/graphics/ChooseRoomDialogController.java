package edu.lawrence.cmsc250.lightbike.client.graphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
import edu.lawrence.cmsc250.lightbike.client.networking.Room;
import edu.lawrence.cmsc250.lightbike.client.networking.RoomListEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * @author luedtkes
 */
public class ChooseRoomDialogController
{
	@FXML
	private Button joinButton;
	@FXML
	private ListView roomView;
	
	private ArrayList<Room> rooms = new ArrayList<>();
	
	@FXML
	public void initialize()
	{
		Gateway.requestRooms();
		Gateway.addEventHandler(event -> {
			rooms.clear();
			Collections.addAll(rooms, event.rooms);
		}, RoomListEvent.class);
	}
	
	@FXML
	public void pressJoin() throws IOException
	{
		Stage parent = (Stage)joinButton.getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("playerStartMenuDialog.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		parent.setScene(scene);
		parent.setTitle("Ready Up Bitch");
	}
	
	@FXML
	public void pressRefresh()
	{
		
	}
	
	@FXML
	public void pressCreate()
	{
		
	}
}