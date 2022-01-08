 package FINAL.project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ManualExamController {

	@FXML
	private ListView<File> listview;

	@FXML
	private Label minutes;

	@FXML
	private Label seconds;

	@FXML
	private Label hours;
	@FXML
    private Button submitButton;

	int count2 = 0;
	int counter, hr, mn, sc;
	Exam exam1 = new Exam();
	Student student = new Student();
	StartExam mystartExam = new StartExam();
	List<SubOnlineExam> mylist = new ArrayList<SubOnlineExam>();
	double TimeLeft = 0;
	boolean status = true;
	Manual manualExam;
	@FXML
	private Button download;

	@FXML
	void DownloadButton(ActionEvent event) throws IOException, InterruptedException {
		download.setDisable(true);
		mystartExam = App.getStartExam();
		TimeLeft = mystartExam.getRemainingTime();
		student = (Student) App.getUser();
		App.setStudent_(null);
		manualExam = new Manual(mystartExam, student);
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("WORD FILE (*.docx)", "*.docx");
		fileChooser.getExtensionFilters().setAll(extFilter);
		fileChooser.setInitialFileName("EXAM");
		File file = fileChooser.showSaveDialog(null);
		if(file!=null)
		{
			String pathString = file.getPath();
			FileOutputStream outputStream = new FileOutputStream(pathString);
			XWPFDocument document = new XWPFDocument();
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun paraRun = paragraph.createRun();
			paraRun.setBold(true);
			paraRun.setFontSize(18);

			
			Exam currentExam = mystartExam.getExam();
			TimeLeft = mystartExam.getRemainingTime();
			// *****save start exam change
			String theExam = "";
			double Time = currentExam.getTime();
			String TeacherName = currentExam.getTeacher().getPersonName();
			List<Question> choosenQuestions = currentExam.getQuestions();
			List<Integer> points2 = currentExam.getPoints();
			String TeacherNotes = currentExam.getNotesForTeacher();
			String StudentNotes = currentExam.getNotesForStudent();
			theExam = theExam + "\n" + "Time: " + Time + "\n\n" + "Teacher Name: " + TeacherName + "\n\n"
					+ "Notes For Teacher: " + TeacherNotes + "\n\n" + "Notes For Student: " + StudentNotes + "\n\n";
			int index = 0;
			for (Question question : choosenQuestions) {
				theExam += question.getContent() + "\n" + "1. " + question.getAnswers(0) + ".\n" + "2. "
						+ question.getAnswers(1) + ".\n" + "3. " + question.getAnswers(2) + ".\n" + "4. "
						+ question.getAnswers(3) + "." + "\n" + "Points: " + points2.get(index) + "\n\n";
				index++;
				paraRun.setText(theExam);
			}
			
			document.write(outputStream);
			outputStream.close();
			JOptionPane.showMessageDialog(null, "Exam Downloaded");

		}
		else JOptionPane.showMessageDialog(null, "you must choose a file");

		ManualeExam2();

	}

	private Timer timer;
	private TimerTask My_task_1 = new TimerTask() {
		@Override
		public void run() {

			if (mystartExam.getRemainingTime() == 0) {
				TimeLeft = 0;
			}
			TimeLeft = TimeLeft * 60;
			while (status) {
				try {
					TimeLeft--;
					Thread.sleep(1000);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							try {
								SetTime();
							} catch (IOException e) {

								e.printStackTrace();
							} catch (InterruptedException e) {

								e.printStackTrace();
							}

						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	};

	void SetTime() throws InterruptedException, IOException {
		if (TimeLeft <= 0) {
			mystartExam.setTimeOut();
			status = false;
			mystartExam.setLock(true);
			closeExam();

		} else {
			hr = (int) (TimeLeft / 3600);
			if (Integer.toString(hr).length() == 1) {
				hours.setText("0" + Integer.toString(hr));
			} else
				hours.setText(Integer.toString(hr));

			mn = (int) ((TimeLeft-(hr*3600)) / 60);
			if (mn == 60)
				mn = 0;
			if (Integer.toString(mn).length() == 1)
				minutes.setText("0" + Integer.toString(mn));
			else
				minutes.setText(Integer.toString(mn));

			sc = (int) (TimeLeft % 60);
			if (Integer.toString(sc).length() == 1)
				seconds.setText("0" + Integer.toString(sc));
			else
				seconds.setText(Integer.toString(sc));

		}
	}

	private void closeExam() throws InterruptedException {

		MsgToServer massageMsgToServer1 = new MsgToServer(null, "Save", mystartExam,null);
		MsgToServer massageMsgToServer2 = new MsgToServer(null, "Save", manualExam,null);
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer1);
			SimpleChatClient.getClient().sendToServer(massageMsgToServer2);
		} catch (IOException e) {
			System.out.println("msg not sent ");
			e.printStackTrace();
		}
		Platform.runLater(() -> {
			count2++;
			if (count2 == 1) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("TIME'S OVER");
				alert.setHeaderText("Exam is over");
				alert.setContentText("");
				alert.showAndWait();
			}
		});
		//
		try {
			seconds.setText("00");
			Thread.sleep(1000);
			App.setRoot("StudentHomePage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.activeCount());
		timer.cancel();
		My_task_1.cancel();

	}

	public void ManualeExam2() {
		timer = new Timer();
		timer.schedule(My_task_1, 1000);
	}

	@FXML
	void SubmitButton(ActionEvent event) throws InterruptedException {
	
		status=false;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Exam Submitted -GOODLUCK");
		alert.setHeaderText(null);
		alert.setContentText("you have Submitted the exam succsecfully! ");
		alert.showAndWait();
		closeExam();
	}

	@FXML
	void UploadButton(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			listview.getItems().add(selectedFile.getCanonicalFile());
			submitButton.setDisable(false);
			manualExam.setSubmittedExam(selectedFile);
			((Student)App.getUser()).addManual(manualExam);
		} else {
			App.setErrorAtCenter("Error! file is not valid");

		}
		
		

	}
}

