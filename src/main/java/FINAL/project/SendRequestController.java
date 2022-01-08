package FINAL.project;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Pair;
public class SendRequestController {

	@FXML
	private  TextField ChangeTimeField;

	@FXML
	private   TextArea ExplanationField;

	@FXML
	private  Text CorrectTime;

	@FXML
	private   Text CorrectExplanation;

	@FXML
	private    TextField CodeField;

	@FXML
	private   Text CorrectCode;


	@FXML
	private   Text ErrorField;

	@FXML
	private   Circle circle;
	
	private static StartExam startExam;


	
	
    public static StartExam getExam() {
		return startExam;
	}






	public static void setExam(StartExam exam) {
		startExam = exam;
	}





	@FXML
    void BackPage(ActionEvent event) throws IOException {
    	App.setRoot("teacherHomePage");
    }

	@FXML
	private  Text error;
  
	@FXML
    void Back(ActionEvent event) throws IOException {
			App.setRoot("teacherHomePage");
    }
	
	
	
	

	@FXML
	void SendRequestButton(ActionEvent event) throws IOException, InterruptedException {
		HandleRequests.getStartExam(CodeField.getText());
		Thread.sleep(200);
		// public static void CreateRequest(StartExam startExam) throws InterruptedException, IOException {	
		String time = ChangeTimeField.getText();
		String code = CodeField.getText();
		String explanation = ExplanationField.getText();
		Pair<String, String> dataState = HandleRequests.checkRequestData(time, explanation, code,startExam);
		String theErrorMsg=dataState.getKey();
		String errorFields=dataState.getValue();
		CorrectCode.setText("");
		CorrectExplanation.setText("");
		CorrectTime.setText("");
		if(theErrorMsg.isEmpty()==true) {
			App.setMsgAtCenter("Request successfully sent!");
			App.setRoot("teacherHomePage");
		}
		else {
			ErrorField.setText(theErrorMsg);
			circle.setVisible(true);
			error.setVisible(true);
			ErrorField.setVisible(true);
		}
		if(errorFields.isEmpty()==false) {
			if(errorFields.contains("Code"))
				CorrectCode.setText("X");
			if(errorFields.contains("Time"))
				CorrectTime.setText("X");
			if(errorFields.contains("Explanation"))
				CorrectExplanation.setText("X");
		}

	}
		 

}