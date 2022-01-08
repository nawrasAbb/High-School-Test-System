package FINAL.project;

import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class DisplayResultsController {

	@FXML
	private ScrollPane showExams;
	static int ExamId = 0;
	@FXML
	void showResults(ActionEvent event) throws IOException, InterruptedException {
		
		int count = 0;
		List<subWindowController> mylist = ControlsubWindow.getSwlist3();
		for (subWindowController temp : mylist) {
			if (temp.getChooseMe().isSelected() == true) {
				ExamId = Integer.parseInt(temp.getlabel().substring(temp.getlabel().length() - 6));
				count++;
			}
		}
		if (count != 1)
			App.setCenter("Error ! you have to choose only one Exam");
		else {
			System.out.println(ExamId);
			ControlsubWindow.findExams(ExamId,2);
			Thread.sleep(700);
			Stage showStage = new Stage();
			showStage.setScene(getNewsceneScene());
			showStage.show();
		}
	}

	static void showResults2() throws IOException, InterruptedException {
System.out.println("exam id in shwresults2: "+ExamId);
		
		Pair<Parent, Object> displayPair = App.getFxmlAndController("DisplayContent");
		ControlsubWindow.DisplayResults(ExamId, (DisplayContentController) displayPair.getValue());
		setNewsceneScene(new Scene(displayPair.getKey()));

	}

	private static Scene newsceneScene;
	public static Scene getNewsceneScene() {
		return newsceneScene;
	}

	public static void setNewsceneScene(Scene newsceneScene2) {
		newsceneScene = newsceneScene2;
	}
	public ScrollPane getShowExams() {
		return showExams;
	}

	public void setShowExams(ScrollPane showExams) {
		this.showExams = showExams;
	}

	@FXML
	void backtoData(ActionEvent event) throws IOException {
		App.setRoot("DisplayData");
	}

	public static void setExamID(int id) {
	
		ExamId=id;
	}

}