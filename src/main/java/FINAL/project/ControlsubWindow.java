package FINAL.project;

//check
import FINAL.project.Exam;
import FINAL.project.Question;
import FINAL.project.Student;
import FINAL.project.Subject;

import FINAL.project.AnswerSheet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class ControlsubWindow {

	static int ExamId;
	static DisplayContentController object;
	public static List<subWindowController> swlist1 = new ArrayList<subWindowController>();
	public static List<subWindowController> swlist2 = new ArrayList<subWindowController>();
	public static List<subWindowController> swlist3 = new ArrayList<subWindowController>();

	public static List<subWindowController> getSwlist1() {
		return swlist1;
	}

	public static void setSwlist1(List<subWindowController> swlist1) {
		ControlsubWindow.swlist1 = swlist1;
	}

	public static List<subWindowController> getSwlist2() {
		return swlist2;
	}

	public static void setSwlist2(List<subWindowController> swlist2) {
		ControlsubWindow.swlist2 = swlist2;
	}

	public static List<subWindowController> getSwlist3() {
		return swlist3;
	}

	public static void setSwlist3(List<subWindowController> swlist3) {
		ControlsubWindow.swlist3 = swlist3;
	}

	public static void InitSubject(List<Subject> sub, DisplayQuestionsController displayQuestions)
			throws IOException, InterruptedException {
		swlist1 = new ArrayList<subWindowController>();
		List<Subject> subjects = new ArrayList<Subject>();
		subjects = sub;
		VBox box = new VBox();
		for (Subject subject : subjects) {
			Pair<Parent, Object> pair2 = App.getFxmlAndController("subWindow");
			subWindowController SW = (subWindowController) pair2.getValue();
			SW.setlabel(subject.getSubjectName() + " - " + Integer.toString(subject.getSubject_id()));
			box.getChildren().add(pair2.getKey());

			swlist1.add(SW);
		}
		Platform.runLater(() -> displayQuestions.getShowsubject().setContent(box));
	}

	public static void InitExam(List<Exam> ex, DisplayExamsController displayExams)
			throws IOException, InterruptedException {
		swlist2 = new ArrayList<subWindowController>();

		List<Exam> Exams = new ArrayList<Exam>();
		Exams = ex;
		VBox box = new VBox();
		for (Exam Exam : Exams) {
			Pair<Parent, Object> pair2 = App.getFxmlAndController("subWindow");
			subWindowController SW = (subWindowController) pair2.getValue();
			SW.setlabel(Exam.getSubjectOfExam().getSubjectName() + " - " + Exam.getCourseOfExam().getCourseName() + "- "
					+ Integer.toString(Exam.getExam_id()));
			box.getChildren().add(pair2.getKey());
			swlist2.add(SW);

		}
		Platform.runLater(() -> displayExams.getShowExamsID().setContent(box));
		// displayExams.getShowExamsID().setContent(box);

	}

	public static void InitResults(List<Exam> myobject, DisplayResultsController displayRes)
			throws IOException, InterruptedException {
		swlist3 = new ArrayList<subWindowController>();

		List<Exam> Exams = new ArrayList<Exam>();
		Exams = myobject;
		System.out.println(Exams.get(0).getExam_id());
		VBox box = new VBox();
		for (Exam Exam : Exams) {
			Pair<Parent, Object> pair2 = App.getFxmlAndController("subWindow");
			subWindowController SW = (subWindowController) pair2.getValue();
			SW.setlabel(Exam.getSubjectOfExam().getSubjectName() + " - " + Exam.getCourseOfExam().getCourseName() + "- "
					+ Integer.toString(Exam.getExam_id()));
			box.getChildren().add(pair2.getKey());
			swlist3.add(SW);
		}
		Platform.runLater(() -> displayRes.getShowExams().setContent(box));
	}

	public static void findQuestions() throws InterruptedException {

		MsgToServer massageMsgToServer = new MsgToServer("Question", "Get", "ALL", "GetAll Question for find");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}

	}

	public static void choosenQuestions(List<Question> Questions) throws InterruptedException, IOException {
		int subjectId = DisplayQuestionsController.getSubjectId();
		List<Question> allQuestions = new ArrayList<Question>();
		allQuestions = Questions;
		int x;
		List<Question> myQ = new ArrayList<Question>();
		for (Question Q : allQuestions) {
			x = Q.getQuestion_id() / 1000;

			if (x == subjectId) {

				myQ.add(Q);
			}
		}
		DisplayQuestionsController.setQuestions(myQ);

	}

	public static void findExams(int ExamId, int num) throws InterruptedException {
		String op_string;
		if (num == 1) {
			op_string = "find Exam 1";
		} else {
			op_string = "find Exam 2";
		}
		MsgToServer massageMsgToServer = new MsgToServer("Exam", "Get", ExamId, op_string);
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}

	}

	public static void DisplayExam(Exam exam, DisplayContentController object) throws InterruptedException {

		Exam myExam = new Exam();
		myExam = exam;

		System.out.println("display results" + myExam.getExam_id());
		String initString = "Exam Structure '_'\n " + "Time: " + myExam.getTime() + "\n\n" + "Teacher Name: "
				+ myExam.getTeacher().getPersonName() + "\n\n" + "\n\n" + "Notes For Student: "
				+ myExam.getNotesForStudent() + "\n\n";
		String queString = "";
		for (Question question : myExam.getQuestions()) {
			queString += question.getContent() + "\n" + "1. " + question.getAnswers(0) + ".\n" + "2. "
					+ question.getAnswers(1) + ".\n" + "3. " + question.getAnswers(2) + ".\n" + "4. "
					+ question.getAnswers(3) + "." + "\n\n";
		}
		String finishString = initString + queString;
		object.getTextLabel().setText(finishString);
	}

	public static void DisplayQ(List<Question> myQ, DisplayContentController object) {
		String queString = "";

		int i = 1;
		for (Question question : myQ) {
			queString += "Question number " + i + ":\n";

			i++;
			queString += question.getContent() + "\n" + "1. " + question.getAnswers(0) + ".\n" + "2. "
					+ question.getAnswers(1) + ".\n" + "3. " + question.getAnswers(2) + ".\n" + "4. "
					+ question.getAnswers(3) + ".\n\n";
		}
		object.getTextLabel().setText(queString);
	}

	public static void DisplayResults(int id, DisplayContentController obj) throws InterruptedException {
		ExamId = id;
		object = obj;
		MsgToServer massageMsgToServer = new MsgToServer("AnswerSheet", "Get", "ALL", "DisplayResults Principle");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	}

	public static void DisplayR(List<AnswerSheet> myAnswers) {
		String queString = "";
		List<AnswerSheet> Answersheets = new ArrayList<AnswerSheet>();
		Answersheets = myAnswers;

		for (AnswerSheet answersheet : Answersheets) {

			if ((answersheet.getExam().getExam_id() == ExamId) && (answersheet.getIsConfirmed() == true)) {
				queString += "student Id: " + answersheet.getStudent().getPerson_Id() + "     student name: "
						+ answersheet.getStudent().getPersonName() + "     student grade: " + answersheet.getGrade()
						+ "\n";

			}
		}
		object.getTextLabel().setText(queString);
	}

	public static void DisplayGrades(DisplayContentController object) throws InterruptedException {

		String myString = "";
		List<AnswerSheet> allAnswerSheets = new ArrayList<AnswerSheet>();
		myString = "My Grades:\n\n";
		Student student = (Student) App.getUser();
		if (student != null) {
			allAnswerSheets = student.getAllAnswerSheets();
			int i = 1;
			for (AnswerSheet AS : allAnswerSheets) {

				if (AS.getIsConfirmed() == true) {
					myString += "Exam number " + i + "\n\n";
					i++;
					myString += "Course Name: " + AS.getExam().getCourseOfExam().getCourseName() + "\n"
							+ "Subject Name: " + AS.getExam().getSubjectOfExam().getSubjectName() + "\n" + "Exam ID: "
							+ AS.getExam().getExam_id() + "\n" + "Grade:  " + AS.getGrade() + "\n\n\n";
				
				}

			}

		}
		object.getTextLabel().setText(myString);
		return;
	}

	public static void printExam(Exam myExam, DisplayContentController object) throws InterruptedException {
		int count = 0;
		String queString = "";
		String mystring = "";

		AnswerSheet myAnswerSheet = new AnswerSheet();

		List<AnswerSheet> allAnswerSheets = ((Student) App.getUser()).getAllAnswerSheets();
		for (AnswerSheet aSheet : allAnswerSheets) {
			if (aSheet.getExam().getExam_id() == myExam.getExam_id()) {
				myAnswerSheet = aSheet;
				break;
			}
		}

		mystring = "Here is the Exam: " + "\n" + "The Exam ID is: " + myExam.getExam_id() + "\n";
		mystring += "The exam subject: " + myExam.getSubjectOfExam().getSubjectName() + "\n";
		mystring += "The exam course: " + myExam.getCourseOfExam().getCourseName() + "\n";
		mystring += "your grade is : " + myAnswerSheet.getGrade() + "\n";
		mystring += "The Exam time limit is: " + myExam.getTime() + "\n";
		mystring += "Teachet Name: " + myExam.getTeacher().getPersonName() + "\n";
		mystring += "Notes:" + myAnswerSheet.getNotes() + "\n" + "Notes For Teacher: " + myExam.getNotesForTeacher()
				+ "\n" + "Notes For Student: " + myExam.getNotesForStudent() + "\n\n\n";

		int i = 1;
		for (Question question : myExam.getQuestions()) {
			queString += "Question number " + i + ":\n";

			i++;
			queString += question.getContent() + "\n" + "1. " + question.getAnswers(0) + ".\n" + "2. "
					+ question.getAnswers(1) + ".\n" + "3. " + question.getAnswers(2) + ".\n" + "4. "
					+ question.getAnswers(3) + "." + "\n\n";
			queString += "The Correct Answer is: " + question.getCorrectAnswer() + "\n";
			queString += "The Student Answer is: " + myAnswerSheet.getAnswersList().get(count) + "\n";
			int x = myAnswerSheet.getAnswersList().get(count);
			System.out.println(x);
			if (question.getCorrectAnswer() == x) {
				queString += "The student Answer is Correct!\n";
			} else {
				queString += "The student Answer is Wrong!\n";
			}
			queString += "\n\n";
			count++;
		}
		String finishString = mystring + queString;
		object.getTextLabel().setText(finishString);

	}

}