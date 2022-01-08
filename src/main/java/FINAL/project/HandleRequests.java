
package FINAL.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import FINAL.project.Request;
import FINAL.project.StartExam;

public class HandleRequests {
	private static ConfirmRequestsController confirmingPage = new ConfirmRequestsController();
	private static List<OneLineInDisplayAllRequestsController> allRequestsLins = new ArrayList<OneLineInDisplayAllRequestsController>();
	private static List<FXMLLoader> allFXMLLine = new ArrayList<FXMLLoader>();

	public static void deleteTheRequest(String name) throws IOException {
	
		for (OneLineInDisplayAllRequestsController line : allRequestsLins) {
		
			if (line.getExamNameAndID().getText().contentEquals(name) && line.getRequest().isAvailable()) {
				Request request = line.getRequest();
				request.setAvailable(false);
				MsgToServer massageMsgToServer1 = new MsgToServer("", "Update", request, "");

				try {
					SimpleChatClient.getClient().sendToServer(massageMsgToServer1);
				
				} catch (IOException e) {
					System.out.println("msg not sent ");
					e.printStackTrace();
				}
				
				break;
			}
		}
		getConfirmingPage().getDisplayRequest().setContent(new VBox());
	}

	public static void displayTheRequest() throws IOException {
		Pair<Parent, Object> requestPair = App.getFxmlAndController("OneRequestView");
		OneRequestViewController controller = (OneRequestViewController) requestPair.getValue();

		for (OneLineInDisplayAllRequestsController line : getAllRequestsLins()) {

			VBox box = new VBox();
			if (line.getShowButton().isArmed()) {
				Request theRequest = line.getRequest();

				
				if (theRequest.isAvailable() == true) {
					controller.setStartExam(theRequest.getStartExam());
					controller.getExplanationField().setText(theRequest.getExplanation());
					controller.getTimeField().setText(theRequest.getExtraTime());
					controller.getTeacherWhoStartedExam()
							.setText(theRequest.getStartExam().getTeacher().getPersonName());
					controller.getCourseOfExam()
							.setText(theRequest.getStartExam().getExam().getCourseOfExam().getCourseName() + " - "
									+ theRequest.getStartExam().getExam().getSubjectOfExam().getSubjectName());
					box.getChildren().add(requestPair.getKey());
					getConfirmingPage().getDisplayRequest().setContent(box);
				} else {
					controller.getLabel().setText("The request has been already confirmed or rejected!");
					controller.getRejactButton().setDisable(true);
					controller.getConfirmButon().setDisable(true);
					controller.getExplanationField().setText(theRequest.getExplanation());
					controller.getTimeField().setText(theRequest.getExtraTime());
					controller.getTeacherWhoStartedExam()
							.setText(theRequest.getStartExam().getTeacher().getPersonName());
					controller.getCourseOfExam()
							.setText(theRequest.getStartExam().getExam().getCourseOfExam().getCourseName() + " - "
									+ theRequest.getStartExam().getExam().getSubjectOfExam().getSubjectName());
					box.getChildren().add(requestPair.getKey());
					getConfirmingPage().getDisplayRequest().setContent(box);
				}
			}
		}

	}

	public static void initializeRequests() {
		MsgToServer massageMsgToServer = new MsgToServer("Request", "Get", "ALL", "init request principle");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
	}

	public void initializeRequestsForPrinciple(List<Request> allRequests2, ConfirmRequestsController controller)
			throws IOException, InterruptedException {
		List<OneLineInDisplayAllRequestsController> lines = new ArrayList<OneLineInDisplayAllRequestsController>();
		List<Request> allRequests = new ArrayList<Request>();
		VBox box = new VBox();
		allRequests = allRequests2;
		setConfirmingPage(controller);
		for (Request thisRequest : allRequests) {
			Pair<Parent, Object> lineOfRequestPair = App.getFxmlAndController("OneLineInDisplayAllRequests");
			OneLineInDisplayAllRequestsController Linecontroller = (OneLineInDisplayAllRequestsController) lineOfRequestPair
					.getValue();
			Linecontroller.setRequest(thisRequest);

			Linecontroller.getExamNameAndID()
					.setText(thisRequest.getStartExam().getExam().getCourseOfExam().getCourseName() + " - "
							+ thisRequest.getStartExam().getExam().getSubjectOfExam().getSubjectName());

			
			lines.add(Linecontroller);
			box.getChildren().add(lineOfRequestPair.getKey());
		}
		setAllRequestsLins(lines);
		controller.getDisplayAllRequests().setContent(box);
	}

	
	public static void getStartExam(String code){
		MsgToServer massageMsgToServer = new MsgToServer("StartExam", "Get", code,"create request");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	public static Pair<String, String> checkRequestData(String Time, String explanation, String code,StartExam startExam)
			throws InterruptedException {
		String teacher_name = "";
		boolean errorInCode = true;
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
		} else {

			
			if (startExam != null) {
				errorInCode = false;
				teacher_name =startExam.getTeacher().getUsername();
			}
			if (errorInCode == true) {
				theErrorMsg = theErrorMsg + "- There are no started exam with this code.\n";
				errorFields = errorFields + "Code";
			}
		}

		if (Time.isEmpty() == true) {
			theErrorMsg = theErrorMsg + "- Time field is empty.\n";
			errorFields = errorFields + "Time";
		} else if (Time.matches("[0-9]+") == false) {
			theErrorMsg = theErrorMsg + "- Time field must contaion only digits, and must be a positive number.\n";
			errorFields = errorFields + "Time";
		} else if (Integer.parseInt(Time) == 0) {
			theErrorMsg = theErrorMsg + "- Time field must contaion only digits, and must be a positive number.\n";
			errorFields = errorFields + "Time";
		}

		if (explanation.isEmpty() == true) {
			theErrorMsg = theErrorMsg + "- You must write an explanation";
			errorFields = errorFields + "Explanation";
		}

		if (theErrorMsg.isEmpty() == true) {
			if (!(App.getUser().getPersonName().contentEquals(teacher_name)))
				theErrorMsg = theErrorMsg
						+ "- Only teacher who atarted the exam can request extra time for this exam.\n";
		}
		if (theErrorMsg.isEmpty() == true) {
			Request request = new Request(explanation, Time, code, SendRequestController.getExam());
				MsgToServer massageMsgToServer = new MsgToServer("", "Save", request,"");
				try {
					SimpleChatClient.getClient().sendToServer(massageMsgToServer);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return new Pair<>(theErrorMsg, errorFields);
	}


	/************ getters and setters **************/

	public static List<OneLineInDisplayAllRequestsController> getAllRequestsLins() {
		return allRequestsLins;
	}

	public void setAllRequestsLins(List<OneLineInDisplayAllRequestsController> allRequestsLins1) {
		allRequestsLins = allRequestsLins1;
	}

	public static ConfirmRequestsController getConfirmingPage() {
		return confirmingPage;
	}

	public void setConfirmingPage(ConfirmRequestsController confirmingPage2) {
		confirmingPage = confirmingPage2;
	}

	public static List<FXMLLoader> getAllFXMLLine() {
		return allFXMLLine;
	}

	public static void setAllFXMLLine(List<FXMLLoader> allFXMLLine) {
		HandleRequests.allFXMLLine = allFXMLLine;
	}
}
