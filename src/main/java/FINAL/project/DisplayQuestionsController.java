package FINAL.project;

import java.io.IOException;
import java.util.List;
import FINAL.project.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class DisplayQuestionsController {

	@FXML
	private ScrollPane showsubject;

	static int subjectId = 0;

	@FXML
	void showQuestions(ActionEvent event) throws IOException, InterruptedException {

		int count = 0;
		List<subWindowController> mylist = ControlsubWindow.getSwlist1();
		for (subWindowController temp : mylist) {
			if (temp.getChooseMe().isSelected() == true) {
				subjectId = Integer.parseInt(temp.getlabel().substring(temp.getlabel().length() - 2));
				count++;
			}
		}
		if (count != 1)
			App.setCenter("Error ! you have to choose only one subject");
		else {
			ControlsubWindow.findQuestions();
			Thread.sleep(700);
			Stage showStage = new Stage();
			showStage.setScene(getNewsceneScene());
			showStage.show();
		}
	}

	static void setQuestions(List<Question> myQ) throws IOException, InterruptedException {

		Pair<Parent, Object> displayPair = App.getFxmlAndController("DisplayContent");
		ControlsubWindow.DisplayQ(myQ, (DisplayContentController) displayPair.getValue());
		setNewsceneScene(new Scene(displayPair.getKey()));

		}

		private static Scene newsceneScene;
		public static Scene getNewsceneScene() {
			return newsceneScene;
		}

		public static void setNewsceneScene(Scene newsceneScene2) {
			newsceneScene = newsceneScene2;
		}
	public ScrollPane getShowsubject() {
		return showsubject;
	}

	public void setShowsubject(ScrollPane showsubject) {
		this.showsubject = showsubject;
	}

	@FXML
	void backtoData(ActionEvent event) throws IOException {
		App.setRoot("DisplayData");

	}

	static public int getSubjectId() {
		return subjectId;
	}

	static public void setSubjectId(int newId) {
		subjectId = newId;
	}
}