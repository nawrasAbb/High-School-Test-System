package FINAL.project;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class OneLineOfExamController {

    @FXML
    private Button ChooseButton;

    @FXML
    private Label ExamNameAndID;

    @FXML
    void ShowRequestButton(ActionEvent event) throws IOException {
    	StartExamControl.WhichButtonHasPressesd();
    }

	public Button getChooseButton() {
		return this.ChooseButton;
	}

	public void setChooseButton(Button chooseButton) {
		this.ChooseButton = chooseButton;
	}

	public Label getExamNameAndID() {
		return this.ExamNameAndID;
	}

	public void setExamNameAndID(Label examNameAndID) {
		this.ExamNameAndID = examNameAndID;
	}
    
    

}
