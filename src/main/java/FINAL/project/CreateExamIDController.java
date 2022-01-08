package FINAL.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import FINAL.project.Question;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class CreateExamIDController {
	private CreateExam createExam = new CreateExam();
	@FXML
	private TextField ExamID;
	static boolean IDstate = true;
	static List<Question> Questions = new ArrayList<Question>();


	@FXML
	void CheckAndDefine(ActionEvent event) throws Exception {
		String exam_id = ExamID.getText();
		 IDstate = true;
		CreateExam.checkExamID_c(exam_id);
	
	}

	static void Define() throws Exception {
		if (IDstate == true) {
			Pair<Parent, Object> pair = App.getFxmlAndController("CreateExam");
			App.setRoot2(pair.getKey());
			CreateExamInterfaceController pageController=(CreateExamInterfaceController)pair.getValue();
        	initialize(pageController);
		}

	}

	public static  void initialize(CreateExamInterfaceController pageController) throws Exception {
		List<Question> questions = new ArrayList<Question>();
		pageController.getTeacherName().setText(App.getUser().getPersonName());
		pageController.getTeacherName().setDisable(true);
		List<AddQuestionsViewController> addQuestionsPages = new ArrayList<AddQuestionsViewController>();
		VBox box = new VBox();
		int i = 1;
		List<Question> matchQuestions = new ArrayList<Question>();
System.out.println("in initiliza");
		questions = getQuestions();
		for (Question question : questions) {
			String ques_id = Integer.toString(question.getQuestion_id());
			if (ques_id.substring(0, 2).contentEquals(CreateExam.getExamID().substring(0, 2)))
				matchQuestions.add(question);
		}
		for (Question question : matchQuestions) {
			Pair<Parent, Object> fxmlPair = App.getFxmlAndController("AddQuestionsView");
			AddQuestionsViewController controller = (AddQuestionsViewController) fxmlPair.getValue();
			controller.getQuestionNumber().setText("Question Number " + Integer.toString(i));
			controller.getQuestionContent()
					.setText(question.getContent() + ":\n" + "1. " + question.getAnswers(0) + ".\n" + "2. "
							+ question.getAnswers(1) + ".\n" + "3. " + question.getAnswers(2) + ".\n" + "4. "
							+ question.getAnswers(3) + ".");
			controller.setQuestion(question);
			i++;
			
			System.out.println("in middle initiliza");
			box.getChildren().add(fxmlPair.getKey());
			Platform.runLater(() ->
			pageController.getDisplayQuesField().setContent(box));
			addQuestionsPages.add((AddQuestionsViewController) fxmlPair.getValue());
		}
		System.out.println("after initiliza");
		pageController.setAddQuestionsPages(addQuestionsPages);

	}

	@FXML
	void Back(ActionEvent event) {
		try {
			App.setRoot("teacherHomePage");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Question> getQuestions() {
		return Questions;
	}

	public static void setQuestions(List<Question> questions) {
		Questions = questions;
	}

	

}

