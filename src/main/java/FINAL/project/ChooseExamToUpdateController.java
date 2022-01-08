package FINAL.project;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class ChooseExamToUpdateController {

    @FXML
    private TextField subjectID;

    @FXML
    private TextField examID;

    @FXML
    private TextField courseID;
    static boolean IDstate = true;
    static String examid;
    static String subjectid;
    static String courseid;
    
    @FXML
    void Back(ActionEvent event) throws IOException {
    	App.setRoot("teacherHomePage");
    }
	@FXML
	void DisplayTheExam(ActionEvent event) throws NumberFormatException, Exception {
		subjectid=subjectID.getText();
		courseid=courseID.getText();
		examid=examID.getText();
		IDstate=true;
		if (subjectID.getText().isEmpty() == true || courseID.getText().isEmpty() == true || examID.getText().isEmpty() == true)
			App.setCenter("Error! there is an empty field.");
		else if (subjectID.getText().matches("[0-9]+") == false || subjectID.getText().length() != 2 || courseID.getText().matches("[0-9]+") == false
				|| courseID.getText().length() != 2 || examID.getText().matches("[0-9]+") == false || examID.getText().length() != 2)
			App.setCenter("Error! All fields must contain two digits.");
		else UpdateExam.checkID_S(subjectID.getText(),courseID.getText(),examID.getText());
		
	}

	 static void DisplayTheExam2() throws NumberFormatException, Exception {
		//if(IDstate==true) {
			System.out.println("IN HERE display 2");
			Pair<Parent,Object> pair=App.getFxmlAndController("UpdateExam");
			UpdateExamController controller=new UpdateExamController();
			controller=(UpdateExamController)pair.getValue();
			UpdateExam.initialize(controller,Integer.parseInt(examid+subjectid+courseid));
			App.setRoot2(pair.getKey());
		//}

	}
	
	
	
	
}