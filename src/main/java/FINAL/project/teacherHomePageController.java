package FINAL.project;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.util.Pair;

public class teacherHomePageController {


    @FXML
    void ExtraTimrRequest(ActionEvent event) throws IOException {
    	 App.setRoot("SendRequest");
    }

    @FXML
    void ShowAllExams(ActionEvent event) throws IOException, InterruptedException {
    	 try {
    	     Pair<Parent, Object> myPair = App.getFxmlAndController("showExams");
    	     showExamController show= (showExamController)myPair.getValue();
    	     showExamController.setexams(show);
    	     App.setRoot2(myPair.getKey());
    	} catch (IOException e) {
    	e.printStackTrace();
    	}
    }

    @FXML
    void UpdateExam(ActionEvent event) throws IOException {
    	App.setRoot("ChooseExamToUpdate");
    }

    @FXML
    void UpdateQuestion(ActionEvent event) throws IOException {
      App.setRoot("UpdateQuestion");	 
    }

    @FXML
    void creatExam(ActionEvent event) throws IOException {
    	 App.setRoot("createExamID");
    }

    @FXML
    void createQuestion(ActionEvent event) throws IOException {
    	 App.setRoot("createQuestion");
    }

    @FXML
    void performStartExam(ActionEvent event) throws IOException, InterruptedException {
    	StartExamControl.gettingExams();
    }
    	
		 static void performStartExam2(List<Exam> myobject) throws IOException, InterruptedException {
    	
    	Pair<Parent,Object> startExamPair=App.getFxmlAndController("StartExamForTeacher");
    	StartExamControl.initialization(myobject,(StartExamForTeacherController)startExamPair.getValue());
    	App.setRoot2(startExamPair.getKey());
    	
    }

    @FXML
    void performlogout(ActionEvent event) throws IOException {
    	LogIn.logout();
    	 App.setRoot("LogIn");
    }

    @FXML
    void startCheckAndConfirm(ActionEvent event) throws IOException {
    	 App.setRoot("CheckAndConfirmGrades");
    }


}

 
 