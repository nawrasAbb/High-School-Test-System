package FINAL.project;

import java.io.IOException; 
import FINAL.project.Person;

public class LogIn {

	private static boolean UserNameExist = false;
	private static boolean PasswordExist = false;
	private static String username = "", password = "";
	private static Person thisPerson = new Person();

	public static void CheckUserNameAndPassword(String UserName, String Password)
			throws IOException, InterruptedException {
		System.out.println("2");
		setUserNameExist(false);
		setPasswordExist(false);

		// getting person from Server
		username = UserName;
		password = Password;
		if (UserName.isEmpty() == true || Password.isEmpty() == true)
			App.setCenter("You must insert user name and password to connect");
		else {
			MsgToServer massageMsgToServer = new MsgToServer("Person", "Get", UserName, "LogIn Check");
			try {
				SimpleChatClient.getClient().sendToServer(massageMsgToServer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void HandleCheck() throws IOException {
		if (UserNameExist == false) {
			App.setCenter("Username does not exist! , please try again");}
		else if (PasswordExist == false) {
			App.setCenter("Incorrect password! , please try again");}
		else {
			if (thisPerson.getStatus() == true) {
				App.setCenter("User is already connected");
			} else {
				// if user exit , changing status to true and saving to server
				thisPerson.setStatus(true);
				System.out.println("Status = " + thisPerson.getStatus());
				MsgToServer massageMsgToServer = new MsgToServer("", "Update", thisPerson, "");
				try {
					SimpleChatClient.getClient().sendToServer(massageMsgToServer);
				} catch (IOException e) {
					e.printStackTrace();
				}
				App.setUSER(thisPerson);
				System.out.println("11");
				/// next page the user's costumized homepage
				LogInController.setTriesNumber(LogInController.getTriesNumber()-1);
				if (thisPerson.getAuthorization() == 0) // student
				{
					App.setRoot("studentHomePage");
				} else if (thisPerson.getAuthorization() == 1) // teacher
				{
					App.setRoot("teacherHomePage");
				}

				else if (thisPerson.getAuthorization() == 2) // principle
				{
					App.setRoot("principleHomePage");
				}

			}
		}
	}

	/// logging out , status =0;
	public static void logout() {
		App.getUser().setStatus(false);
		System.out.println("status " + thisPerson.getStatus() + thisPerson.getPersonName());
		MsgToServer massageMsgToServer = new MsgToServer("", "Update", thisPerson, "");
		try {
			SimpleChatClient.getClient().sendToServer(massageMsgToServer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		App.setUSER(null);
		

	}

	// setters & getters
	public static boolean isUserNameExist() {
		return UserNameExist;
	}

	public static void setUserNameExist(boolean userNameExist) {
		UserNameExist = userNameExist;
	}

	public static boolean isPasswordExist() {
		return PasswordExist;
	}

	public static void setPasswordExist(boolean passwordExist) {
		PasswordExist = passwordExist;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		LogIn.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		LogIn.password = password;
	}

	public static Person getThisPerson() {
		return thisPerson;
	}

	public static void setThisPerson(Person thisPerson) {
		LogIn.thisPerson = thisPerson;
	}

}
