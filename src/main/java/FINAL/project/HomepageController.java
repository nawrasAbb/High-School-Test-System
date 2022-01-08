
package FINAL.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import FINAL.project.SimpleChatClient;

public class HomepageController implements Initializable {

	@FXML
	private TextField serverIP;

	@FXML
	private TextField serverPort;
//initialization
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		serverIP.setText("127.0.0.1");
		serverPort.setText("3000");
	}

	@FXML
	public void Connect(ActionEvent actionEvent) throws IOException {
//connecting to server
		String host = serverIP.getText();
		if (serverPort.getText().matches("[0-9]+") == false) {
			App.setCenter("Invalid port number");
		}
		int port = Integer.parseInt(serverPort.getText());

		 System.out.println("port= "+port +"host= "+host);
		SimpleChatClient myclient = new SimpleChatClient(host, port);
		
			SimpleChatClient.setClient(myclient);
			 System.out.println("my client is" +myclient);
			App.connect();
			if (App.getClient1() != null) {
			App.setRoot("LogIn");
		} else {
			App.setCenter("Couldn't connect please try again");
			System.err.println("couldn't connect to server");

	}
}}
