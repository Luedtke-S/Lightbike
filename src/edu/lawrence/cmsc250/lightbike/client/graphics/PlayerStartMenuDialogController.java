package edu.lawrence.cmsc250.lightbike.client.graphics;

import java.io.IOException;

import edu.lawrence.cmsc250.lightbike.client.game.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
	public void initialize()
	{
		playerLabel1.setTextFill(Constants.RED);
		playerLabel2.setTextFill(Constants.BLUE);
		playerLabel3.setTextFill(Constants.GREEN);
		playerLabel4.setTextFill(Constants.YELLOW);
	}
	
	@FXML
	public void pressReady() throws IOException
	{
		Stage parent = (Stage)readyLabel1.getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("arena.fxml"));
		Parent root = (Parent)loader.load();
		Scene scene = new Scene(root);
		parent.setScene(scene);
		parent.setTitle("It's on!");
	}
}
