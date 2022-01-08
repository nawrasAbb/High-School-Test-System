package FINAL.project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DisplayExamController {

    @FXML
    private ScrollPane DisplayField;

    @FXML
    private Text ViewField;

    @FXML
    private Button closebutton;
    
    @FXML
    void closePage(ActionEvent event) {
    	Stage stage=(Stage)closebutton.getScene().getWindow();
    	stage.close();
    }

   
	public ScrollPane getDisplayField() {
		return this.DisplayField;
	}

	public void setDisplayField(ScrollPane displayField) {
		DisplayField = displayField;
	}

	public Text getViewField() {
		return this.ViewField;
	}

	public void setViewField(Text viewField) {
		ViewField = viewField;
	}

}

