package FINAL.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import FINAL.project.Exam;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class StartExamControl {

	private static List<OneLineOfExamController> listOfLines = new ArrayList<OneLineOfExamController>();
	private static StartExamForTeacherController theMainPage = new StartExamForTeacherController();
	static String ExamID;

	public static void gettingExams() {
		MsgToServer massageMsgToServer = new MsgToServer("Exam", "Get", "All", "getExams for start exam");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	}

	public static void Examinit(String string) {
		MsgToServer massageMsgToServer1 = new MsgToServer("Exam", "Get", Integer.parseInt(string),
				"getExam to create StartExam");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer1);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}

	}

	public static void initialization(List<Exam> myobject, StartExamForTeacherController controller)
			throws IOException, InterruptedException {
		theMainPage = controller;
		List<Exam> allExams = new ArrayList<Exam>();
		allExams = myobject;
		VBox box = new VBox();
		for (Exam exam : allExams) {
			Pair<Parent, Object> oneLineOfExamPair = App.getFxmlAndController("OneLineOfExam");
			OneLineOfExamController LineController = (OneLineOfExamController) oneLineOfExamPair.getValue();
			LineController.getExamNameAndID().setText(exam.getSubjectOfExam().getSubjectName() + " - "
					+ exam.getCourseOfExam().getCourseName() + " - " + exam.getExam_id());
			box.getChildren().add(oneLineOfExamPair.getKey());
			listOfLines.add(LineController);
		}
		controller.getDisplayAllExams().setContent(box);
	}

	public static void WhichButtonHasPressesd() throws IOException {
		for (OneLineOfExamController page : listOfLines) {
			if (page.getChooseButton().isArmed()) {
				theMainPage.getExamID().setText(page.getExamNameAndID().getText());
				String iDString = theMainPage.getExamID().getText();
				Examinit(iDString.substring(theMainPage.getExamID().getText().length() - 6));

				break;
			}
		}
	}


	public static Pair<String, String> CheckData(String code, String type, String ID) throws InterruptedException {
		String errorFields = "";
		String theErrorMsg = "";
		if (code.isEmpty() == true) {
			theErrorMsg = theErrorMsg + "- Code field is empty.\n";
			errorFields = errorFields + "Code";
		} else if (code.length() != 4) {
			theErrorMsg = theErrorMsg + "- Code must contain 4 digits or letters.\n";
			errorFields = errorFields + "Code";
		} else if (code.matches("[a-zA-Z0-9]+") == false) {
			theErrorMsg = theErrorMsg + "- Invalid code! it must contain only digits or letters.\n";
			errorFields = errorFields + "Code";
		}
		if (theErrorMsg.isEmpty()) {
			MsgToServer massageMsgToServer = new MsgToServer("StartExam", "Get", code, "Check if StartExam is Uniqe");
			try {
				SimpleChatClient.getClient().sendToServer(massageMsgToServer);
			} catch (IOException e) {
				System.out.println("msg not sent ");
				e.printStackTrace();
			}

			//return null;
		}

		if (type == null) {
			theErrorMsg = theErrorMsg + "- Must choose the type of the exam.\n";
			errorFields = errorFields + "Type";
		}
		if (ID.isEmpty()) {
			theErrorMsg = theErrorMsg + "- Must choose the exam.\n";
			errorFields = errorFields + "ID";
		}
		
			return new Pair<>(theErrorMsg, errorFields);
		

	}

}