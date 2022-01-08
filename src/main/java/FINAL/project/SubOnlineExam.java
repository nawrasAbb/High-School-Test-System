package FINAL.project;
import java.util.ArrayList;
import java.util.List;

import FINAL.project.Exam;
import FINAL.project.Question;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SubOnlineExam {

	@FXML // fx:id="answer1"
	private Label answer1; // Value injected by FXMLLoader

	@FXML // fx:id="select1"
	private CheckBox select1; // Value injected by FXMLLoader

	@FXML // fx:id="answer2"
	private Label answer2; // Value injected by FXMLLoader

	@FXML // fx:id="anwers3"
	private Label answer3; // Value injected by FXMLLoader

	@FXML // fx:id="answer4"
	private Label answer4; // Value injected by FXMLLoader

	@FXML // fx:id="select2"
	private CheckBox select2; // Value injected by FXMLLoader

	@FXML // fx:id="select3"
	private CheckBox select3; // Value injected by FXMLLoader

	@FXML // fx:id="select4"
	private CheckBox select4; // Value injected by FXMLLoader

	@FXML
	private TextArea content;

	public void  setcontent(String text ) {
    	content.setText(text);}

	public CheckBox get1() {
		return select1;

	}

	public CheckBox get2() {
		return select2;

	}

	public CheckBox get3() {
		return select3;

	}

	public CheckBox get4() {
		return select4;

	}

	public void setAnswer1(String Text) {
		answer1.setText(Text);

	}

	public void setAnswer2(String Text) {
		answer2.setText(Text);

	}

	public void setAnswer3(String Text) {
		answer3.setText(Text);

	}

	public void setAnswer4(String Text) {
		answer4.setText(Text);

	}

	static void InitContent(SubOnlineExam onlineExam, int i) {

		Exam exam = App.getStartExam().getExam();
		List<Question> Ques = new ArrayList<Question>();
		Ques = exam.getQuestions();

		onlineExam.setcontent(
				"Question " + (i + 1) + " : " + Ques.get(i).getContent() + "\n Points : " + exam.getPoints().get(i));
		onlineExam.setAnswer1("1) " + Ques.get(i).getAnswers(0));
		onlineExam.setAnswer2("2) " + Ques.get(i).getAnswers(1));
		onlineExam.setAnswer3("3) " + Ques.get(i).getAnswers(2));
		onlineExam.setAnswer4("4) " + Ques.get(i).getAnswers(3));
	}
	
	public static List<CheckBox> getCheckbox(SubOnlineExam onlineExam)
	{
	List<CheckBox> boxes=new ArrayList<CheckBox>()	;
	boxes.add(onlineExam.get1());
	boxes.add(onlineExam.get2());
	boxes.add(onlineExam.get3());
	boxes.add(onlineExam.get4());
	return boxes;
	}
}
