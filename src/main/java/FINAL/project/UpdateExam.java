package FINAL.project;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
//import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class UpdateExam {
	static int fullID;
	static Exam thisExamToUpdtExam = new Exam();
	private static  List<GradingViewController> PagesInGradesScrollPane = new ArrayList<GradingViewController>();
	private static List<AddQuestionsViewController> PagesInQuestionScrollPane = new ArrayList<AddQuestionsViewController>();
	private static VBox GradesWindow = new VBox();
	private   VBox QuestionsWindow = new VBox();
	private static Subject subject;
	private static UpdateExamController page;
	private static int examid;
	private static int subject_id;
	private static int course_id;
	private static Exam exam; 
	private static Subject subject2;
	private static Course course;
	
	
	

	public static List<GradingViewController> getPagesInGradesScrollPane() {
		return PagesInGradesScrollPane;
	}



	public static void setPagesInGradesScrollPane(List<GradingViewController> pagesInGradesScrollPane) {
		PagesInGradesScrollPane = pagesInGradesScrollPane;
	}



	public static List<AddQuestionsViewController> getPagesInQuestionScrollPane() {
		return PagesInQuestionScrollPane;
	}



	public static void setPagesInQuestionScrollPane(List<AddQuestionsViewController> pagesInQuestionScrollPane) {
		PagesInQuestionScrollPane = pagesInQuestionScrollPane;
	}



	public static Subject getSubject() {
		return subject2;
	}



	public static void setSubject(Subject subject) {
		subject2 = subject;
	}



	public static Course getCourse() {
		return course;
	}



	public static void setCourse(Course course) {
		UpdateExam.course = course;
	}



	public void makeExam(String ID,String Time, String TeacherName, List<Question> choosenQuestions, List<Integer> points2,
			String teachernotes, String studentsnotes) throws InterruptedException {
		MsgToServer massageMsgToServer = new MsgToServer("Course", "Get", ID.substring(2, 4), "Update Exam");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		System.out.println("in sending subject");
		massageMsgToServer = new MsgToServer("Subject", "Get", ID.substring(0, 2), "Update Exam");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		Exam newExam = new Exam(Integer.parseInt(ID), Double.parseDouble(Time), points2, (Teacher) App.getUser(),
				studentsnotes, teachernotes, getSubject(), getCourse(), choosenQuestions);
		massageMsgToServer = new MsgToServer("Exam", "Save", newExam, "");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.err.println("Save has failed");
		}
		((Teacher) App.getUser()).addWrittenExamsList(newExam);
		MsgToServer massageMsgToServer1 = new MsgToServer("", "Update", (Teacher) App.getUser(), "");

		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer1);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	}
	
	
	
	public static Pair<String, List<GradingViewController>> CheckIfLegal(String Time, String TeacherName,
			List<Question> choosenQuestions, List<Integer> points2, String NewCode) throws InterruptedException {
		int ErrorInTime = 0;
		int ErrorInName = 0;
		int ErrorInQuesList = 0;
		int ErrorInPointsList = 0;
		int ErrorInCode = 0;
		String ErrorMsg = "";
		String spaString = TeacherName.replaceAll("\\s", "");
		char[] chars = spaString.toCharArray();
		if (Time.isEmpty() == true)
			ErrorInTime = 1;
		else if (Time.matches("[0-9]+.[0-9]+") == false)
			ErrorInTime = 1;
		else if (Double.parseDouble(Time) <= 0)
			ErrorInTime = 1;
		if (TeacherName.isEmpty() == true)
			ErrorInName = 1;
		else {
			for (char c : chars) {
				if (!Character.isLetter(c) && Character.toString(c) != "") {
					ErrorInName = 1;
				}
			}
		}
		if (PagesInGradesScrollPane.isEmpty() == true)
			ErrorInQuesList = 1;
		if (points2.isEmpty() == true)
			ErrorInPointsList = 1;

		if (NewCode.isEmpty() == true)
			ErrorInCode = 1;
		else if ((NewCode.matches("[0-9]+") == false) || (NewCode.length()!=2))
			ErrorInCode = 1;
		
		else {
			
			
			List<Exam> exams = new ArrayList<Exam>();

			MsgToServer massageMsgToServer = new MsgToServer("Exam", "Get", "All", "Update Exam");
			try {
				SimpleChatClient.getClient().sendToServer(massageMsgToServer);
			} catch (IOException e) {
				System.out.println("msg not sent ");
				e.printStackTrace();
			}
			Thread.sleep(1000);
			exams = App.getExams_List_();
			for (Exam exam : exams) {
				if (Integer.toString(exam.getExam_id()).substring(4).equals(NewCode))
					ErrorInCode = 1;
			}
		}

			if (ErrorInTime == 1)
				ErrorMsg = ErrorMsg + "Time";
			if (ErrorInName == 1)
				ErrorMsg = ErrorMsg + " + Name";
			if (ErrorInPointsList == 1)
				ErrorMsg = ErrorMsg + " + Points";
			if (ErrorInQuesList == 1)
				ErrorMsg = ErrorMsg + " + Questions";
			if (ErrorInCode == 1)
				ErrorMsg = ErrorMsg + " + Code";
		

		return new Pair<>(ErrorMsg, PagesInGradesScrollPane);

	}

	public static Pair<String, List<GradingViewController>> CheckGradingfields() {
		int totalGrade = 0;
		String wrongString = "";
		for (GradingViewController page : PagesInGradesScrollPane) {
			page.getInCorrect().setText("");
			System.out.println(page.getPoints().getText().isEmpty()+"IsEmpty");
			if (page.getPoints().getText().isEmpty()) {
				page.getInCorrect().setText("X");
				wrongString = "Incorrect data";
			} else if (page.getPoints().getText().matches("[0-9]+") == false) {
				page.getInCorrect().setText("X");
				wrongString = "Incorrect data";
			} else if (Integer.parseInt(page.getPoints().getText()) <= 0) {
				page.getInCorrect().setText("X");
				wrongString = "Incorrect data";
			} else if (Integer.parseInt(page.getPoints().getText()) > 100) {
				page.getInCorrect().setText("X");
				wrongString = "Incorrect data";
			} else if ((Integer.parseInt(page.getPoints().getText()) > 0)
					&& Integer.parseInt(page.getPoints().getText()) <= 100) {
				totalGrade += Integer.parseInt(page.getPoints().getText());
			}
		}
		if (wrongString != "Incorrect data" && totalGrade != 100) {
			if (totalGrade > 100)
				wrongString = "Up to 100";
			if (totalGrade < 100)
				wrongString = "Down to 100";
		}
		return new Pair<>(wrongString, PagesInGradesScrollPane);
	}
	
	
	
	
	
	
	

	public static Pair<VBox, String> RemoveQuestionslegality() throws IOException {
		
		int numberOfChoosedQues = 0;
		int i = 0;
		List<Question> choosedQuestions = new ArrayList<Question>();
		for (AddQuestionsViewController page : PagesInQuestionScrollPane) {
			if (page.getChoose().isSelected() == true) {
				numberOfChoosedQues++;
				page.getChoose().setSelected(false);
				if (page.getIsChoosed().getText() == "V") {
					page.getIsChoosed().setText("");
					choosedQuestions.add(page.getQuestion());
				}
			}
		}
		if (numberOfChoosedQues == 0)
			return new Pair<>(null, "There are no Choosed Question");
		List<GradingViewController> toRemove = new ArrayList<GradingViewController>();
		for (Question question : choosedQuestions) {
			for (GradingViewController GradePage : PagesInGradesScrollPane) {
				if (GradePage.getQuestion().getContent().contentEquals((question.getContent()))) {
					toRemove.add(GradePage);
				}
			}
		}
		GradesWindow.getChildren().clear();
		PagesInGradesScrollPane.removeAll(toRemove);
		List<GradingViewController> list=new ArrayList<GradingViewController>();
		for (GradingViewController GradePage2 : PagesInGradesScrollPane) {
			i++;
			Pair<Parent, Object> newPair = App.getFxmlAndController("GradingView");
			GradingViewController controller = (GradingViewController) newPair.getValue();
			controller.getQuestionNumber().setText("Question Number " + Integer.toString(i));
			controller.getViewQuestion().setText((GradePage2.getViewQuestion().getText()));
			controller.setQuestion(GradePage2.getQuestion());
			controller.getPoints().setText(Integer.toString(GradePage2.getGrade()));
			GradesWindow.getChildren().add(newPair.getKey());
			list.add((GradingViewController)newPair.getValue());
		}
		setPagesInGradesScrollPane(list);
		return new Pair<>(GradesWindow, "Successfully Removed");
	}

	
	
	
	
	
	
	
	
	
	
	public static Pair<VBox, String> addQuestions() throws IOException {
		int numberOfChoosedQues = 0;
		List<Question> choosedQuestions = new ArrayList<Question>();
		for (AddQuestionsViewController page : PagesInQuestionScrollPane) {
			if (page.getChoose().isSelected() == true) {
				numberOfChoosedQues++;
				page.getChoose().setSelected(false);
				if (page.getIsChoosed().getText() != "V") {
					page.getIsChoosed().setText("V");
					choosedQuestions.add(page.getQuestion());
				}
			}
		}
		if (numberOfChoosedQues == 0)
			return new Pair<>(null, "There are no Choosed Question");
		int size = PagesInGradesScrollPane.size();
		for (Question question : choosedQuestions) {
			Pair<Parent, Object> newPair = App.getFxmlAndController("GradingView");
			GradingViewController controller = (GradingViewController) newPair.getValue();
			controller.getQuestionNumber().setText("Question Number " + Integer.toString(size + 1));
			controller.getViewQuestion()
					.setText(question.getContent() + ":\n" + "1. " + question.getAnswers(0) + ".\n" + "2. "
							+ question.getAnswers(1) + ".\n" + "3. " + question.getAnswers(2) + ".\n" + "4. "
							+ question.getAnswers(3) + ".");
			controller.setQuestion(question);
			GradesWindow.getChildren().add(newPair.getKey());
			PagesInGradesScrollPane.add((GradingViewController) newPair.getValue());
			size++;
		}
		return new Pair<VBox, String>(GradesWindow, "added seccessfully");
	}

	public static void checkID_S(String subjectID, String courseID, String examID)
			throws InterruptedException, IOException {
				fullID =Integer.parseInt(subjectID + courseID + examID);
				subject_id = Integer.parseInt(subjectID);
				course_id = Integer.parseInt(courseID);
				MsgToServer massageMsgToServer = new MsgToServer("Subject", "Get", subject_id,"Update exam check subject");
				try {
					SimpleChatClient.getClient().sendToServer(massageMsgToServer);
				} catch (IOException e) {
					System.out.println("msg not sent ");
					e.printStackTrace();
				}
			
		
	}

	public static void checkID_C() {
		System.out.println("IN C"+course_id);
		MsgToServer massageMsgToServer = new MsgToServer("Course", "Get", course_id, "Update exam check course");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	}
	
	public static void checkID_E() {
		System.out.println("IN E");
		
		
		MsgToServer massageMsgToServer = new MsgToServer("Exam", "Get",fullID,"updateExam id  if Exam exists");

		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	}

	

	public static void initialize(UpdateExamController page1, int id) throws Exception {
		examid = id;
		page = page1;
		UpdateExamController.setChoosenQuestions((thisExamToUpdtExam.getQuestions()));
		
		MsgToServer massageMsgToServer = new MsgToServer("Exam", "Get", id, "getExam to update");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		Thread.sleep(2000);
		massageMsgToServer = new MsgToServer("Question", "Get", "All", "for update exam");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	}

	public  void initialize2(List<Question> questions1) throws IOException {
		int j = -1;
		int i = 1;
		List<Question> questions = new ArrayList<Question>();
		System.out.println("in init 2 update exam11");
		page.getTeacherName().setText(thisExamToUpdtExam.getTeacher().getPersonName());
		page.getTeacherNotes().setText(thisExamToUpdtExam.getNotesForTeacher());
		page.getStudentNotes().setText(thisExamToUpdtExam.getNotesForStudent());
		page.getTime().setText(Double.toString(thisExamToUpdtExam.getTime()));
		page.getSubjectField().setText(Integer.toString(thisExamToUpdtExam.getSubjectOfExam().getSubject_id()));
		page.getCourseField().setText(Integer.toString(thisExamToUpdtExam.getCourseOfExam().getCourse_id()));
		List<Question> matchQuestions = new ArrayList<Question>();
		questions = questions1;
		for (Question question : questions) {
			String ques_id = Integer.toString(question.getQuestion_id());
			if (ques_id.substring(0, 2).contentEquals(Integer.toString(subject_id).substring(0, 2))) {
				matchQuestions.add(question); /**** matchQuestions have the questions belong to the subject ***/
				
			}
		}
		for (Question question : matchQuestions) {
			System.out.println(question.getContent());
			Pair<Parent, Object> fxmlPair = App.getFxmlAndController("AddQuestionsView");
			AddQuestionsViewController controller = (AddQuestionsViewController) fxmlPair.getValue();
			controller.setIsChoosed("V");
			controller.getQuestionNumber().setText("Question Number " + Integer.toString(i));
			controller.getQuestionContent()
					.setText(question.getContent() + ":\n" + "1. " + question.getAnswers(0) + ".\n" + "2. "
							+ question.getAnswers(1) + ".\n" + "3. " + question.getAnswers(2) + ".\n" + "4. "
							+ question.getAnswers(3) + ".");
			controller.setQuestion(question);
			i++;
			QuestionsWindow.getChildren().add(fxmlPair.getKey());
			page.getDisplayQuesField().setContent(QuestionsWindow);/******/
			PagesInQuestionScrollPane.add((AddQuestionsViewController) fxmlPair.getValue());

		}
		List<Question> choosedQuestions = new ArrayList<Question>();
		choosedQuestions = thisExamToUpdtExam.getQuestions();
		/**** choosedQuestions have the questions that was in the exam ***/
		for (AddQuestionsViewController Quespage : PagesInQuestionScrollPane) {
			if (choosedQuestions.contains(Quespage.getQuestion()))
				Quespage.getIsChoosed().setText("V");
		}
		List<String> poinList = new ArrayList<String>();
		poinList.add("60");
		poinList.add("40");
		for (Question ques : choosedQuestions) {
			j++;
			Pair<Parent, Object> fxmlPair = App.getFxmlAndController("GradingView");
			GradingViewController controller = (GradingViewController) fxmlPair.getValue();
			controller.getQuestionNumber().setText("Question Number " + Integer.toString(j + 1));
			controller.getViewQuestion()
					.setText(ques.getContent() + ":\n" + "1. " + ques.getAnswers(0) + ".\n" + "2. " + ques.getAnswers(1)
							+ ".\n" + "3. " + ques.getAnswers(2) + ".\n" + "4. " + ques.getAnswers(3) + ".");
			controller.getPoints().setText(/* thisExamToUpdtExam.getPoints().get(j) +*/poinList.get(j));
			System.out.println("no ,here");
			controller.setGrade(Integer.parseInt((poinList.get(j))));
			controller.setQuestion(ques);
			GradesWindow.getChildren().add(fxmlPair.getKey());
			Platform.runLater(() ->
			PagesInGradesScrollPane.add((GradingViewController) fxmlPair.getValue()));

		}
		Platform.runLater(() -> page.getGradingField().setContent(GradesWindow));
	}
	
}