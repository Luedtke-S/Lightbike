package edu.lawrence.cmsc250.lightbike.client.graphics;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
import edu.lawrence.cmsc250.lightbike.client.networking.JoinRoomSuccessEvent;
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
import javafx.scene.control.TextInputDialog;
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
		roomView.getSelectionModel().selectedItemProperty().addListener((ov, obj, newItem) -> roomSelected(newItem));
		
		Gateway.requestRooms();
		Gateway.setEventHandler(event -> {
			rooms.clear();
			Collections.addAll(rooms, event.rooms);
		}, RoomListEvent.class);
		Gateway.setEventHandler(event -> {
			try {
				Stage parent = (Stage)joinButton.getScene().getWindow();
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("playerStartMenuDialog.fxml"));
				Parent root = null;
				root = loader.load();
				Scene scene = new Scene(root);
				parent.setScene(scene);
				parent.setTitle("Ready Up");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, JoinRoomSuccessEvent.class);
		
		roomView.setItems(rooms);
	}
	
	@FXML
	public void pressJoin()
	{
		Gateway.sendJoinRoom(roomView.getSelectionModel().getSelectedItem().id);
	}
	
	@FXML
	public void pressRefresh()
	{
		Gateway.requestRooms();
	}
	
	@FXML
	public void pressCreate() throws IOException
	{
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Enter Room Name");
		
		Optional<String> name = dialog.showAndWait();
		
		if (name.isPresent()) {
			Gateway.sendCreateRoom(name.get());
			
			Stage parent = (Stage)joinButton.getScene().getWindow();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("playerStartMenuDialog.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			parent.setScene(scene);
			parent.setTitle("Ready Up");
			
			PlayerStartMenuDialogController controller = loader.getController();
			controller.setPlayer1();
		}
	}
	
	private void roomSelected(Room r)
	{
		joinButton.setDisable(r == null);
	}
}