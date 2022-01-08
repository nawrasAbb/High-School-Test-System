package FINAL.project;
import java.io.IOException; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

public class ConfirmRequestsController {

	@FXML
	private ScrollPane DisplayRequest;

	@FXML
	private  ScrollPane DisplayAllRequests;

	@FXML
	void ReturnBackToHomePage(ActionEvent event) throws IOException {
		App.setRoot("principleHomePage");		
	}

	/******** getters ********/

	public ScrollPane getDisplayRequest() {
		return this.DisplayRequest;
	}

	public  ScrollPane getDisplayAllRequests() {
		return this.DisplayAllRequests;
	}

	/******* setters ******/

	public void setDisplayRequest(ScrollPane displayRequest) {
		this.DisplayRequest = displayRequest;
	}

	public void setDisplayAllRequests(ScrollPane displayAllRequests) {
		this.DisplayAllRequests = displayAllRequests;
	}



}