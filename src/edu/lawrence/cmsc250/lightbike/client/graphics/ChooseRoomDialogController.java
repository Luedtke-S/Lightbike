package edu.lawrence.cmsc250.lightbike.client.graphics;

import java.io.IOException;
import java.util.Collections;

import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
import edu.lawrence.cmsc250.lightbike.client.networking.Room;
import edu.lawrence.cmsc250.lightbike.client.networking.RoomListEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	private ListView<Room> roomView;
	
	private ObservableList<Room> rooms = FXCollections.observableArrayList();
	
	@FXML
	public void initialize()
	{
		roomView.setCellFactory(new Room.CellFactory());
//		roomView.getSelectionModel().selectedItemProperty().addListener((ov, obj, newItem) -> roomSelected(newItem));
		
		Gateway.requestRooms();
		Gateway.addEventHandler(event -> {
			rooms.clear();
			Collections.addAll(rooms, event.rooms);
		}, RoomListEvent.class);
		
		roomView.setItems(rooms);
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