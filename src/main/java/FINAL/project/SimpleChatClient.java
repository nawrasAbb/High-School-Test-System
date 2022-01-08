package FINAL.project;

import java.io.IOException;
import java.util.List;
import ocsf.AbstractClient;

public class SimpleChatClient extends AbstractClient {

	private static SimpleChatClient client = null;

	SimpleChatClient(String host, int port) {
		super(host, port);
	}

	public static void setClient(SimpleChatClient client) {
		SimpleChatClient.client = client;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleMessageFromServer(Object msg) {

		msgToClient newMassageMsgToClient = (msgToClient) msg;
		int returnedvalue;
		String massage = newMassageMsgToClient.getCommand();
		String operation = newMassageMsgToClient.getOperation();
		String className = newMassageMsgToClient.getMyClass();
		Object myobject = newMassageMsgToClient.getMyObject();

		System.out.println("Received Message: " + msg);

		System.out.println("Received command :" + massage);
		System.out.println("Received name:" + myobject);

		if (className.equals("Person")) {
			myobject = ((Person) myobject);
			if (operation.startsWith("LogIn Check")) {
				if (myobject != null) {
					System.out.println("8");
					if (((Person) myobject).getUsername().contentEquals(LogIn.getUsername())) {
						LogIn.setUserNameExist(true);
						if (((Person) myobject).getPassword().contentEquals(LogIn.getPassword())) {
							LogIn.setThisPerson(((Person) myobject));
							LogIn.setPasswordExist(true);

						}
					}
				} 
				try {
					System.out.println("9");
					LogIn.HandleCheck();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		else if (className.equals("SubjectList")) {
			if (operation.contentEquals("init Subjects Principle")) {
				if (myobject != null) {

					try {
						DisplayDataController.ShowQuestions((List<Subject>) myobject);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		else if (className.equals("Subject")) {
			if (operation.contentEquals("CreateExam")) {

				if (myobject != null) {
					CreateExam.setSubject((Subject) myobject);

					System.out.println("callinfg creating");
					// CreateExam.createActualExam();
				}
			} else if (operation.contentEquals("updateQ")) {

				if (myobject != null) {
					UpdateQuestionController.subject = ((Subject) myobject);
				} else {
					UpdateQuestionController.subject = null;
				}
			}

			else if (operation.contentEquals("Check subject for create question")) {
				if (myobject == null) {
					CreateQuestion.theError = CreateQuestion.theError + "There are no subject with this id=" + "\n";
					CreateQuestion.incorrectFields = CreateQuestion.incorrectFields + "ID";
				}
				else {
					CreateQuestion.setSubject((Subject)myobject);
				}
			} else if (operation.contentEquals("create exam check subject")) {

				if (myobject == null) {
					try {
						App.setErrorAtCenter("The subject's code does not exists");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					CreateExamIDController.IDstate = false;
				} else {
					try {
						CreateExam.checkExamID_E();
					} catch (InterruptedException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			else if (operation.contentEquals("Update exam check subject")) {

				if (myobject == null) {
					try {
						App.setErrorAtCenter("The subject's code does not exists");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ChooseExamToUpdateController.IDstate = false;
				} else {
					UpdateExam.checkID_C();
				}
			}
		} else if (className.equals("AnswerSheetList")) {
			if (operation.contentEquals("DisplayResults Principle")) {
				if (myobject != null) {

					ControlsubWindow.DisplayR((List<AnswerSheet>) myobject);
				}

			}

		}

		else if (className.equals("QuestionList")) {

			if (operation.contentEquals("GetAll Question for find")) {

				if (myobject != null) {

					try {
						ControlsubWindow.choosenQuestions((List<Question>) myobject);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (operation.contentEquals("for update exam")) {

				if (myobject != null) {

					try {
						UpdateExam newone = new UpdateExam();
						newone.initialize2((List<Question>) myobject);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else if (operation.contentEquals("GETALL Q for create Exam")) {

				try {
					System.out.println("im in GETALL Q for create Exam");
					CreateExamIDController.Questions = ((List<Question>) myobject);
					System.out.println("got question");
					CreateExamIDController.Define();

				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		else if (className.equals("Question")) {
			if (operation.contentEquals("updateQ")) {

				if (myobject != null) {
					UpdateQuestionController.myQuestion = ((Question) myobject);
				} else {
					UpdateQuestionController.myQuestion = (null);
				}
			} else if (operation.contentEquals("Check unique for create question")) {

				if (myobject != null) {

					CreateQuestion.theError = CreateQuestion.theError + "This ID not uniform.\n";
					CreateQuestion.incorrectFields = CreateQuestion.incorrectFields + "ID";
					// CreateQuestion.Check2();
				}
				
			} 
		}
		
		else if (className.equals("Course")) {
			if (operation.contentEquals("CreateExam")) {
				if (myobject != null) {
					CreateExam.setCourse((Course) myobject);
				}
			}

			else if (operation.contentEquals("create exam check course")) {
				System.out.println("In C client");

				if (myobject == null) {
					try {
						System.out.println("No cousrse");
						App.setErrorAtCenter("The course's code does not exists ");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					CreateExamIDController.IDstate = false;
				} else {
					System.out.println(((Course) myobject).getCourse_id());
					try {
						System.out.println("yes cousrse");
						CreateExam.checkExamID_S();
					} catch (InterruptedException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (operation.contentEquals("Update exam check course")) {

				if (myobject == null) {
					try {
						App.setErrorAtCenter("The Course's code does not exists");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ChooseExamToUpdateController.IDstate = false;
				} else {
					UpdateExam.checkID_E();
				}
			}

		}

		else if (className.equals("ExamList")) {
			System.out.println("in ExamList");
			if (operation.contentEquals("init StartEXam Exams")) {
				if (myobject != null) {
					try {
						StartExam.initialization2((List<Exam>) myobject);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (operation.contentEquals("Update Exam")) {
				if (myobject != null) {
					App.setExams_List_((List<Exam>) myobject);
				}
			}

			if (operation.contentEquals("getExams for start exam")) {
				if (myobject != null) {
					try {
						teacherHomePageController.performStartExam2((List<Exam>) myobject);
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if (operation.contentEquals("init Exams Principle")) {
				if (myobject != null) {

					try {
						DisplayDataController.ShowExams((List<Exam>) myobject);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (operation.contentEquals("init Results Principle")) {
				if (myobject != null) {

					try {
						System.out.println("in clint result principle");
						DisplayDataController.ShowResults((List<Exam>) myobject);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		} else if (className.equals("Exam")) {

			if (operation.contentEquals("getExam to update")) {
				if (myobject != null) {
					UpdateExam.thisExamToUpdtExam = (Exam) myobject;

				} else {

				}
			} else if (operation.contentEquals("updateExam id  if Exam exists")) {

				if (myobject == null) {
					try {
						App.setErrorAtCenter("The Exam's code doesnt exists");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ChooseExamToUpdateController.IDstate = false;
				} else {
					try {
						UpdateExam.thisExamToUpdtExam = (Exam) myobject;
						ChooseExamToUpdateController.DisplayTheExam2();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			/*
			 * if (operation.contentEquals("CheckExam id  if Exam exists")) { if (myobject
			 * == null) { try {
			 * App.setErrorAtCenter("Error! there are no exam with this code."); } catch
			 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 * ChooseExamToUpdateController.IDstate = false; } try {
			 * ChooseExamToUpdateController.DisplayTheExam2(); } catch (Exception e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); } }
			 */

			else if (operation.contentEquals("create exam check unique")) {
				if (myobject != null) {
					try {
						App.setErrorAtCenter("The identifying number is not unique ");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					CreateExamIDController.IDstate = false;
				}
			}

			else if (operation.contentEquals("getExam to create StartExam")) {
				if (myobject != null) {
					StartExamForTeacherController.exam = ((Exam) myobject);
				}
			}

			else if (operation.contentEquals("ShowExam for teacher")) {

				if (myobject != null) {
					try {
						int Flag = 1;
						List<Exam> allexamsforCurrentExams = ((Teacher) App.getUser()).getWrittenExamsList();
						for (Exam exam : allexamsforCurrentExams) {
							if (Integer.toString(exam.getExam_id())
									.contentEquals(Integer.toString(((Exam) myobject).getExam_id()))) {
								showExamController.setThereIsExam(1);
								showExamController.showExamforTeacher((Exam) myobject);
								Flag = 0;
								return;
							}
						}

						List<StartExam> allexamsforCurrentExams2 = ((Teacher) App.getUser()).getStartedExamsList();
						for (StartExam startexam : allexamsforCurrentExams2) {
							if (Integer.toString(startexam.getExam().getExam_id())
									.contentEquals(Integer.toString(((Exam) myobject).getExam_id()))) {

								showExamController.setThereIsExam(1);
								showExamController.showExamforTeacher((Exam) myobject);
								Flag = 0;
								return;
							}
						}

						if (Flag == 1) {
							showExamController.setThereIsExam(-1);
						}
					} catch (InterruptedException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						App.setCenter("Exam Id not found");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			else if (operation.contentEquals("Open exam for student")) {

				if (myobject != null) {
					try {
						System.err.println("stammmmmm");
						List<TakeExam> allexamsforCurrentExams = ((Student) App.getUser()).getTakenExams();
						if (allexamsforCurrentExams.isEmpty())
							System.err.println("empty");
						for (TakeExam takeexam : allexamsforCurrentExams) {
							System.out.println("lala");
							if (Integer.toString(takeexam.getStartExam().getExam().getExam_id())
									.contentEquals(Integer.toString(((Exam) myobject).getExam_id()))) {
								System.out.println("mamama");
								printsMyExamsController.setThereIsExam(true);
								printsMyExamsController.ShowExam((Exam) myobject);
								return;
							}
						}
						App.setCenter("Exam Id not Found");
					} catch (InterruptedException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						App.setCenter("Exam Id not found");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}

			else if (operation.contentEquals("find Exam 1")) {
				if (myobject != null) {
					try {

						DisplayExamsController.setThereIsExam(true);
						DisplayExamsController.ShowExams2((Exam) myobject);
					} catch (InterruptedException | IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						App.setCenter("Exam Id not found");
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}

			else if (operation.contentEquals("find Exam 2")) {
				if (myobject != null) {
					try {
						System.out.println("IN FIND EXAM 2");
						DisplayResultsController.setExamID(((Exam) myobject).getExam_id());
						DisplayResultsController.showResults2();
					} catch (InterruptedException | IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						App.setCenter("Exam Id not found");
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		} else if (className.equals("TaketExamList")) {

			if (operation.contentEquals("Check Code takeExam")) {
				App.setTakeExams_List_((List<TakeExam>) myobject);

			}
		}

		else if (className.equals("StartExam"))

		{
			if (operation.contentEquals("GetStartTime")) {
				if (myobject != null) {
					OnlineExamInterface.mystartExam=(StartExam) myobject;
				}
			}
			else if (operation.contentEquals("getstarat exam for exam check")) {
				if (myobject != null) {

					try {
						CheckExam.startExam = (StartExam) myobject;
						try {
							CheckAndConfirmGrades.autocheck2();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {

					try {
						App.setCenter("Invalid Code!");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} else if (operation.contentEquals("getstarat exam for exam confirm")) {
				if (myobject != null) {

					CheckExam.startExam = (StartExam) myobject;
					// CheckAndConfirmGrades.Confirm2();
				}

			} else if (operation.contentEquals("Check Code takeExam")) {
				if (myobject != null) {

					if (((StartExam) myobject).isLock() == true) {
						returnedvalue = -4;
					}
					App.setStartExam((StartExam) myobject);
					if (((StartExam) myobject).ExamType()) {
						// if Manual

						returnedvalue = 1;
					}
					// else online
					else {

						returnedvalue = 0;

					}
				} else {
					returnedvalue = -1;
				}
				try {
					InsertCodeInterface.ExamNotFound(returnedvalue);
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			else if (operation.startsWith("Check if StartExam is Unique")) {
				if (myobject != null) {
					StartExamForTeacherController.setError("- This code not uniqe.\n");
					// StartExamForTeacherController.ExamNotUnique("This code not uniqe.\n",
					// "Code");

				}
			}

			else if (operation.startsWith("create request")) {
				if (myobject != null) {
					// SendRequestController.CreateRequest((StartExam) myobject);
					SendRequestController.setExam((StartExam) myobject);
				} else
					SendRequestController.setExam(null);
			}

		}

		else if (className.equals("RequestList")) {

			if (operation.contentEquals("init request principle")) {
				if (myobject != null) {
					try {
						System.out.println();
						principleHomePageController.ShowRequests((List<Request>) myobject);
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}

				}

			}

		}

		else if (className.equals("AnswerSheet")) {
			System.err.println("in client answesheet");
			if (myobject == null)
				System.err.println("object null");
			if (operation.contentEquals("get AS for Student change")) {
				if (myobject != null) {
					ConfirmGradesInterface.AS = ((AnswerSheet) myobject);
					System.err.println("in change");
				}
			}
			if (operation.contentEquals("get AS for Student confirm")) {
				if (myobject != null) {
					ConfirmGradesInterface.AS = ((AnswerSheet) myobject);
					System.err.println("in confirm");
				}
			}
		}

		else

		{
			if (msg.getClass().equals(Subject.class)) {
				App.setSubject_((Subject) msg);
			}

			if (msg.getClass().equals(Course.class)) {
				App.setCourse_((Course) msg);
			}

			if (msg.getClass().equals(Student.class)) {
				App.setStudent_((Student) msg);
			}
			if (msg.getClass().equals(Teacher.class)) {
				App.setTeacher_((Teacher) msg);
			}
			if (msg.getClass().equals(Principle.class)) {
				App.setPrinciple_((Principle) msg);
			}

			if (msg.getClass().equals(Request.class)) {
				App.setRequest_((Request) msg);
			}

			if (msg.getClass().equals(Person.class) || msg.getClass().equals(Principle.class)
					|| msg.getClass().equals(Teacher.class) || msg.getClass().equals(Student.class)) {
				App.setPerson_((Person) msg);
			}
			if (msg.getClass().equals(Question.class)) {
				App.setQuestion_((Question) msg);
			}

			if (msg.getClass().equals(Exam.class)) {
				App.setExam_((Exam) msg);
			}

			if (msg.getClass().equals(AnswerSheet.class)) {
				App.setAnswerSheet_((AnswerSheet) msg);
			}

			if (msg.getClass().equals(App.getSubjects_List_().getClass())) {
				App.setSubjects_List_((List<Subject>) msg);
			}

			if (msg.getClass().equals(App.getQuestions_List_().getClass())) {
				App.setQuestions_List_((List<Question>) msg);
			}

			if (msg.getClass().equals(App.getTakeExams_List_().getClass())) {
				App.setTakeExams_List_((List<TakeExam>) msg);
			}

			if (msg.getClass().equals(App.getExams_List_().getClass())) {
				System.out.println("seting list of exam");
				App.setExams_List_((List<Exam>) msg);
			}

			if (msg.getClass().equals(App.getStudents_List().getClass())) {
				App.setStudents_List((List<Student>) msg);
			}
			if (msg.getClass().equals(App.getRequests_List().getClass())) {
				App.setRequests_List((List<Request>) msg);
			}
			if (msg.getClass().equals(App.getAnswerSheets_List_().getClass())) {
				App.setAnswerSheets_List_((List<AnswerSheet>) msg);
			}
		}
	}

	public static SimpleChatClient getClient() {
		if (client == null) {
			System.err.println("No Client");
			return null;
		}
		return client;
	}
}
