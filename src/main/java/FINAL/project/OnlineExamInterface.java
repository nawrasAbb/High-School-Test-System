package FINAL.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import javafx.scene.control.ScrollPane;

public class OnlineExamInterface {

	@FXML // fx:id="ID"
	private TextField ID; // Value injected by FXMLLoader

	@FXML // fx:id="StarEXam"
	private Button StarEXam; // Value injected by FXMLLoader

	@FXML // fx:id="submit"
	private Button submit; // Value injected by FXMLLoader

	@FXML // fx:id="WrongInput"
	private Label WrongInput; // Value injected by FXMLLoader

	@FXML // fx:id="ScrollPane"
	private ScrollPane ScrollPane; // Value injected by FXMLLoader

	@FXML
	private Label minutes;

	@FXML
	private Label seconds;

	@FXML
	private Button Back;

	static boolean flag = false;

	@FXML
	private Label hours;
	int count2 = 0;
	int counter, hr, mn, sc;
	Exam exam1 = new Exam();
	static Student student = new Student();
	static TakeExam takeExam = new TakeExam();
	static StartExam mystartExam = new StartExam();
	List<SubOnlineExam> mylist = new ArrayList<SubOnlineExam>();
	double TimeLeft = 0;
	boolean status = true;

	@FXML
	void Back(ActionEvent event) throws IOException {
		App.setRoot("InsertCodeInterface");
	}

	public OnlineExamInterface() throws InterruptedException {
		mystartExam = App.getStartExam();
		TimeLeft = mystartExam.getRemainingTime();

	}

	int countTimes;
	private Timer timer;
	private TimerTask My_task_1 = new TimerTask() {
		@Override
		public void run() {
			countTimes = 0;

			TimeLeft = TimeLeft * 60;

			while (status) {
				System.out.println("status " + status);

				if (mystartExam.getRemainingTime() == 0) {
					TimeLeft = 0;
				}

				if (mystartExam.getActualTime() > mystartExam.getIntialTime()) {

					Platform.runLater(() -> {
						countTimes++;
						if (countTimes == 1) {

							TimeLeft += (mystartExam.getActualTime() - mystartExam.getIntialTime()) * 60;
						}

					});
				}
				System.out.println("TIME :LEFT " + TimeLeft);
				try {
					TimeLeft--;

					MsgToServer massageMsgToServer = new MsgToServer("StartExam", "Get", mystartExam.getCode(),"GetStartTime");
					try {
						SimpleChatClient.getClient().sendToServer(massageMsgToServer);
					} catch (IOException e) {

						e.printStackTrace();
					}
					Thread.sleep(1000);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								SetTime();

							} catch (InterruptedException | IOException e) {
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

			status = false;
			mystartExam.setLock(true);
			submitExam();

		}

		else {
			hr = (int) (TimeLeft / 3600);
			if (Integer.toString(hr).length() == 1) {
				hours.setText("0" + Integer.toString(hr));
			} else
				hours.setText(Integer.toString(hr));

			mn = (int) ((TimeLeft-(hr*3600) )/ 60);
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

	public void OnlineExamInterface2() {
		timer = new Timer();
		timer.schedule(My_task_1, 1000);

	}

	private Timer timerWait;

	@FXML
	void ShowExam(ActionEvent event) throws IOException, InterruptedException {
		int returnedValue = TakeExamControl.ChecklegalId(ID.getText(),
				Integer.toString(mystartExam.getExam().getExam_id()).substring(2, 4));
		VBox Vbox = new VBox();
		if (returnedValue == 0) {
			StarEXam.setDisable(true);
			submit.setDisable(false);
			Back.setDisable(true);
			WrongInput.setText(" ");
			TakeExamControl.TakeExamAction(mystartExam);
			exam1 = App.getStartExam().getExam();
			List<Question> Ques = new ArrayList<Question>();
			Ques = exam1.getQuestions();
			for (int i = 0; i < (Ques.size()); i++) {
				Pair<Parent, Object> pair = App.getFxmlAndController("SubOnlineExam");
				SubOnlineExam onlineExam = (SubOnlineExam) pair.getValue();
				SubOnlineExam.InitContent(onlineExam, i);
				Vbox.getChildren().add(pair.getKey());
				mylist.add(onlineExam);
			}

			ScrollPane.setContent(Vbox);
			OnlineExamInterface2();

		}

		else {
			if (counter > 4) {

				timerWait = new Timer();
				TimerTask My_task_2 = new TimerTask() {
					@Override
					public void run() {
						try {

							ID.setText("");
							StarEXam.setDisable(true);
							Back.setDisable(true);

							App.setCenter("Please try again after 1 minute!");
							Thread.sleep(5000);
							System.out.println("Time is up");
							StarEXam.setDisable(false);

							Back.setDisable(false);
							counter = 0;

						} catch (Exception e) {
							System.out.println("Timer is failed");
						}
					}
				};
				timerWait.schedule(My_task_2, 1);
				WrongInput.setText(" ");
			} else if (returnedValue == -2) {
				App.setCenter("You already took this exam !");
				StarEXam.setDisable(false);
				WrongInput.setText("X");
				counter++;
			} else if (returnedValue == -1) {
				App.setCenter("Invalid ID , please try again!");
				StarEXam.setDisable(false);
				WrongInput.setText("X");
				counter++;
			}
			if (returnedValue == -3) {

				App.setCenter("You are not registered fot this Cousre/Subject");
				StarEXam.setDisable(false);
				WrongInput.setText("X");
				counter++;

			}

		}

	}

	@FXML
	void submit(ActionEvent event) throws InterruptedException {
		List<Integer> answerList = new ArrayList<Integer>();

		int count = 0;
		for (SubOnlineExam temp : mylist) {
			for (int j = 0; j < SubOnlineExam.getCheckbox(temp).size(); j++) {

				if (SubOnlineExam.getCheckbox(temp).get(j).isSelected()) {
					count++;

					answerList.add(j + 1);
				}

			}
			if (count != 1) {
				try {
					count = 0;
					App.setCenter("Error ! you have to choose only one answer per question");
				} catch (IOException e) {
					e.printStackTrace();

				}

				return;
			}
			count = 0;
		}

		TakeExamControl.submit(answerList, mystartExam, takeExam, student);
		Platform.runLater(() -> {
			count2++;
			if (count2 == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Exam Submitted Successfully");
				alert.setHeaderText("You have finished the exam ");
				alert.setContentText("GOOD LUCK ! ");
				alert.showAndWait();
			}

		});

		try {

			App.setRoot("StudentHomePage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		status = false;
		System.out.println(Thread.activeCount());
		timer.cancel();
		My_task_1.cancel();

	}

	void submitExam() throws InterruptedException {
		mystartExam.addDidntCompleteNumber();
		List<Integer> answerList = new ArrayList<Integer>();

		int count = 0, questionIndex = 0;
		int j;
		for (SubOnlineExam temp : mylist) {
			for (j = 0; j < SubOnlineExam.getCheckbox(temp).size(); j++) {

				if (SubOnlineExam.getCheckbox(temp).get(j).isSelected()) {
					count++;
					questionIndex = j;

				}

			}

			if (count != 1) {
				answerList.add(-1);

			} else {
				answerList.add(questionIndex + 1);
			}
			questionIndex++;
			count = 0;
		}

		TakeExamControl.submit(answerList, mystartExam, takeExam, student);
		Platform.runLater(() -> {
			count2++;
			if (count2 == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("TIME'S OVER");
				alert.setHeaderText("Exam is over");
				alert.setContentText("GOOD LUCK ! ");
				alert.showAndWait();

			}
		});

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

	public static Student getStudent() {
		return student;
	}

	public static void setStudent(Student student1) {
		student = student1;
	}

	public static TakeExam getTakeExam() {
		return takeExam;
	}

	public static void setTakeExam(TakeExam takeExam1) {
		takeExam = takeExam1;
	}

	public static StartExam getMystartExam() {
		return mystartExam;
	}

	public static void setMystartExam(StartExam mystartExam1) {
		mystartExam = mystartExam1;
	}

}

