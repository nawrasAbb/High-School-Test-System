package FINAL.project;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class subWindowController {

    @FXML
    private Label writeMe;

    public CheckBox getChooseMe() {
		return chooseMe;
	}
	public void setChooseMe(CheckBox chooseMe) {
		this.chooseMe = chooseMe;
	}
	@FXML
    private CheckBox chooseMe;
    
    
    public void  setlabel(String label2 ) {
    	this.writeMe.setText(label2);
		
	}
    public String getlabel() {
    	return writeMe.getText(); 
		
	}
}
