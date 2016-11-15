package edu.lawrence.cmsc250.lightbike.client.graphics;

import java.io.IOException;

import edu.lawrence.cmsc250.lightbike.client.game.Constants;
import edu.lawrence.cmsc250.lightbike.client.networking.Gateway;
import edu.lawrence.cmsc250.lightbike.client.networking.RoomUpdateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author luedtkes
 */
public class PlayerStartMenuDialogController
{
	@FXML
	Label playerLabel1;
	@FXML
	Label playerLabel2;
	@FXML
	Label playerLabel3;
	@FXML
	Label playerLabel4;
	@FXML
	Label readyLabel1;
	@FXML
	Label readyLabel2;
	@FXML
	Label readyLabel3;
	@FXML
	Label readyLabel4;
	@FXML
	ToggleButton readyButton;
	
	private boolean isP1 = false;
	
	int playersReady2 = 1;
	int playersReady3 = 1;
	int playersReady4 = 1;
	
	@FXML
	public void initialize()
	{
		playerLabel1.setTextFill(Constants.RED);
		playerLabel2.setTextFill(Constants.BLUE);
		playerLabel3.setTextFill(Constants.GREEN);
		playerLabel4.setTextFill(Constants.YELLOW);
		readyLabel1.setTextFill(Constants.RED);
		Gateway.setEventHandler(event -> gameUpdated(event), RoomUpdateEvent.class);
	}
	
	public void gameUpdated(RoomUpdateEvent e)
	{
		if (e.roomClosed) {
			try {
				Stage parent = (Stage)readyButton.getScene().getWindow();
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("chooseRoomDialog.fxml"));
				Parent root = (Parent)loader.load();
				Scene scene = new Scene(root);
				parent.setScene(scene);
				parent.setTitle("Choose Room");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			
			if (e.room.occupants == 2) {
				if (e.room.player2Ready) {
					readyLabel2.setText("READY");
					readyLabel2.setTextFill(Constants.BLUE);
					readyButton.setDisable(false);
				} else {
					readyLabel2.setText("NOT READY");
					readyLabel2.setTextFill(Color.WHITE);
					if (isP1)
					readyButton.setDisable(true);
				}
			}
		
			
			if (e.room.occupants == 3) {
				if (e.room.player2Ready) {
					readyLabel2.setText("READY");
					readyLabel2.setTextFill(Constants.BLUE);
				} else {
					readyLabel2.setText("NOT READY");
					readyLabel2.setTextFill(Color.WHITE);
				}
				
				if (e.room.player3Ready) {
					readyLabel3.setText("READY");
					readyLabel3.setTextFill(Constants.GREEN);
				} else {
					readyLabel3.setText("NOT READY");
					readyLabel3.setTextFill(Color.WHITE);
				}
				if (isP1) {
					if (e.room.player2Ready && e.room.player3Ready) {
						readyButton.setDisable(false);
					} else {
						readyButton.setDisable(true);
					}
				}
			}
			
			if (e.room.occupants == 4) {
				if (e.room.player2Ready) {
					readyLabel2.setText("READY");
					readyLabel2.setTextFill(Constants.BLUE);
				} else {
					readyLabel2.setText("NOT READY");
					readyLabel2.setTextFill(Color.WHITE);
				}
				
				if (e.room.player3Ready) {
					readyLabel3.setText("READY");
					readyLabel3.setTextFill(Constants.GREEN);
				} else {
					readyLabel3.setTextFill(Color.WHITE);
					readyLabel3.setText("NOT READY");
				}
				
				if (e.room.player4Ready) {
					readyLabel4.setText("READY");
					readyLabel4.setTextFill(Constants.YELLOW);
				} else {
					readyLabel4.setTextFill(Color.WHITE);
					readyLabel4.setText("NOT READY");
				}
				if (isP1) {
					if (e.room.player2Ready && e.room.player3Ready && e.room.player4Ready) {
						readyButton.setDisable(false);
					} else {
						readyButton.setDisable(true);
					}
				}
			}
			
			
		}
	}
	
	public void setPlayer1()
	{
		isP1 = true;
		readyButton.setText("Start");
		readyButton.setDisable(true);
	}
	
	@FXML
	public void pressReady() throws IOException
	{
		
		if (isP1) {
			
		} else {
			Gateway.updateReadyState(readyButton.isSelected());
		}

//		Stage parent = (Stage)readyLabel1.getScene().getWindow();
//		
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("arena.fxml"));
//		Parent root = (Parent)loader.load();
//		Scene scene = new Scene(root);
//		parent.setScene(scene);
//		parent.setTitle("It's on!");
	}
	
	@FXML
	public void pressLeave() throws IOException
	{
		Gateway.sendLeaveRoom();
		Stage parent = (Stage)readyLabel1.getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("chooseRoomDialog.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		parent.setScene(scene);
		parent.setTitle("Choose Room");
	}
}
