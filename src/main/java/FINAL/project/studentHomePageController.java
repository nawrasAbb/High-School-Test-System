package FINAL.project;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

import javafx.event.ActionEvent;

public class studentHomePageController {

	
	//Display All Exams for the student
	@FXML
	void DisplayAllExams(ActionEvent event) throws IOException, InterruptedException {
		try {
			Pair<Parent, Object> myPair = App.getFxmlAndController("printMyExams");
			printsMyExamsController show = (printsMyExamsController) myPair.getValue();
			printsMyExamsController.setexams(show);
			App.setRoot2(myPair.getKey());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Display All the grades for this Student
	@FXML
	void DisplayAllGrades(ActionEvent event) throws IOException, InterruptedException {
		Stage showStage = new Stage();
		Pair<Parent, Object> displayPair = App.getFxmlAndController("DisplayContent");
		ControlsubWindow.DisplayGrades((DisplayContentController) displayPair.getValue());
		Scene newsceneScene = new Scene(displayPair.getKey());
		showStage.setScene(newsceneScene);
		showStage.show();
	}

	@FXML
	void PerformExam(ActionEvent event) throws IOException {
		App.setRoot("InsertCodeInterface");
	}
	
	@FXML
	void Performlogout(ActionEvent event) throws IOException {
		LogIn.logout();
		App.setRoot("LogIn");
	}

}
