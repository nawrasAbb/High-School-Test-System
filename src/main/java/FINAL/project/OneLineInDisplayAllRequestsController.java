package FINAL.project;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
public class OneLineInDisplayAllRequestsController {

    @FXML
    private Button ShowButton;
    
    @FXML
    private Label ExamNameAndID;
    private Request request;
    
    public Request getRequest() {
		return this.request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	@FXML
    void ShowRequestButton(ActionEvent event) throws IOException {
    	HandleRequests.displayTheRequest();
    }

	public Label getExamNameAndID() {
		return this.ExamNameAndID;
	}

	public void setExamNameAndID(Label examNameAndID) {
		this.ExamNameAndID = examNameAndID;
	}

	public Button getShowButton() {
		return this.ShowButton;
	}

	public void setShowButton(Button showButton) {
		this.ShowButton = showButton;
	}
	

    
}
