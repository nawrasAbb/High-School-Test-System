package FINAL.project;

import java.io.IOException;
import java.util.List;

import FINAL.project.Exam;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class DisplayExamsController {

	@FXML
	private ScrollPane showExamsID;		
	
	 private static Scene newsceneScene;
		private static boolean thereIsExam = false;

		public boolean isThereIsExam() {
			return thereIsExam;
		}

		public static void setThereIsExam(boolean thereIsExam2) {
			thereIsExam = thereIsExam2;
		}
	    
	@FXML
	void showExams(ActionEvent event) throws IOException, InterruptedException {
		{
			int ExamId = 0;
			int count = 0;
			List<subWindowController> mylist = ControlsubWindow.getSwlist2();
			for (subWindowController temp : mylist) {
				if (temp.getChooseMe().isSelected() == true) {
					ExamId = Integer.parseInt(temp.getlabel().substring(temp.getlabel().length() - 6));
					count++;
				}
			}
			if (count != 1)
				App.setCenter("Error ! you have to choose only one Exam");
			else {
				ControlsubWindow.findExams(ExamId,1);
				Thread.sleep(700);
				if (thereIsExam == true) {
					Stage showStage = new Stage();
					showStage.setScene(getNewsceneScene());
					showStage.show();
				}
			}
		}
	}

	public static Scene getNewsceneScene() {
		return newsceneScene;
	}

	public static void setNewsceneScene(Scene newsceneScene2) {
		newsceneScene = newsceneScene2;
	}

	public static void ShowExams2(Exam exam) throws InterruptedException, IOException {

		Pair<Parent, Object> displayPair = App.getFxmlAndController("DisplayContent");
		ControlsubWindow.DisplayExam(exam, (DisplayContentController) displayPair.getValue());
		setNewsceneScene(new Scene(displayPair.getKey()));
	}

	public ScrollPane getShowExamsID() {
		return showExamsID;
	}

	public void setShowExamsID(ScrollPane showExamsID) {
		this.showExamsID = showExamsID;
	}

	@FXML
	void backtoData(ActionEvent event) throws IOException {
		App.setRoot("DisplayData");
	}
}
