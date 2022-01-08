package FINAL.project;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class DisplayContentController {

    @FXML
    private TextArea textLabel;

	public TextArea getTextLabel() {
		return textLabel;
	}

	public void setTextLabel(TextArea textLabel) {
		this.textLabel = textLabel;
	}
}

