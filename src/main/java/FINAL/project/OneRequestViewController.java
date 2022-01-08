package FINAL.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class OneRequestViewController {

	@FXML
	private TextField TimeField; 

	@FXML
	private TextArea ExplanationField;

	@FXML
	private TextField CourseOfExam;

	@FXML
	private TextField TeacherWhoStartedExam;

	@FXML
	private Button confirmButon;

	@FXML
	private Button rejactButton;

	private StartExam starExam;

	public Button getConfirmButon() {
		return this.confirmButon;
	}

	public void setConfirmButon(Button confirmButon) {
		this.confirmButon = confirmButon;
	}

	public Button getRejactButton() {
		return this.rejactButton;
	}

	public void setRejactButton(Button rejactButton) {
		this.rejactButton = rejactButton;
	}

	@FXML
	void ConfirmRequestButton(ActionEvent event) throws IOException, InterruptedException {
		// add to timer and go to all students and display a message about the extra
		// time
		getStartExam().AddTime(Double.parseDouble(TimeField.getText()));
		HandleRequests.deleteTheRequest(getCourseOfExam().getText());
		App.setMsgAtCenter("The request has confirmed");
		/*MsgToServer massageMsgToServer = new MsgToServer("", "update", getStartExam(), "");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {

			e.printStackTrace();
		}*/
	}

	@FXML
	private Label label;

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	@FXML
	void RejectRequestButton(ActionEvent event) throws IOException {
		HandleRequests.deleteTheRequest(getCourseOfExam().getText());
		App.setMsgAtCenter("The request has rejected");
	}

	/***** getters *******/
	public TextField getTimeField() {
		return this.TimeField;
	}

	public TextField getTeacherWhoStartedExam() {
		return this.TeacherWhoStartedExam;
	}

	public TextField getCourseOfExam() {
		return this.CourseOfExam;
	}

	public TextArea getExplanationField() {
		return this.ExplanationField;
	}

	public StartExam getStartExam() {
		return starExam;
	}

	/***** setters *******/

	public void setStartExam(StartExam starExam) {
		this.starExam = starExam;
	}

	public void setTeacherWhoStartedExam(TextField teacherWhoStartedExam) {
		this.TeacherWhoStartedExam = teacherWhoStartedExam;
	}

	public void setCourseOfExam(TextField courseOfExam) {
		this.CourseOfExam = courseOfExam;
	}

	public void setExplanationField(TextArea explanationField) {
		this.ExplanationField = explanationField;
	}

	public void setTimeField(TextField timeField) {
		this.TimeField = timeField;
	}
}
