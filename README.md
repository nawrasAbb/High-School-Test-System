# High School Test System
This project was the main and final part of the Software Engineering course. We were a team of 3 members.

# Main idea
A computerized information system whose function is to centralize all the activities of exams given at the school and to improve the efficiency and reliability of the examination.

# Tools
1) Programming Language: Java (IDE: Eclipse)
2) GUI Design: JavaFX Scene Builder
3) Database: MySQL
4) OCSF for Create a simple server-client application.
5) Hibernate 

# Content
- Principe operations: 
  * Login
  * Show data
  * Confirm/Reject requests

- Teacher operations:
  * Login
  * Create an exam.
  * Create a question
  * Update exam
  * Update question
  * Start sxam
  * Check and Confirm Grades
  * Request extra time
  * Show the exams

- Student operations:
  * Login
  * Perform exam
  * Display exams 
  * Display grades


# Server Connection
Connecting to the server

![image](https://user-images.githubusercontent.com/97045152/148096443-9733da8a-dc45-4805-9d66-34bf4998817f.png)

# Login
Only the principle, students and teachers that registered in the system can login

![image](https://user-images.githubusercontent.com/97045152/148096554-05a8522b-5e68-4949-88d1-c428443c393e.png)

and those are the home pages for student, teacher and principle respectively:

![image](https://user-images.githubusercontent.com/97045152/148097091-c433940b-6877-4fa2-85b1-3d1d6e9f58f7.png)   ![image](https://user-images.githubusercontent.com/97045152/148097189-305020da-e474-4ac7-94a2-6c70f09a1e5b.png)   
![image](https://user-images.githubusercontent.com/97045152/148097317-5ce226df-6820-4680-8e30-091f73deeb29.png)

# Teacher Home Page
# Create a question
Question id consists of 2 digits of the subject + 3 digits for uniq id, and the teacher must write a description and four answers and what is the correct answer.

![image](https://user-images.githubusercontent.com/97045152/148098698-e128e6cc-bf7d-4493-a227-f87d026bd7b4.png)


# Create an exam
The teacher must insert id of the exam that consist of 6 digits. (subject id + course id + uniq id)

Then it will displays for her the questions that belong to the choosed subject.

The teacher can add or remove questions by selecting it and press on Add Question/Remove Question button. And she must declare the points for each question.

Write her name, notes for the students and notes for herself(not visible to the students).

And when she finish she can show the structure by clicking on the show exam button.

![image](https://user-images.githubusercontent.com/97045152/148099509-f3ae10a1-a51a-4898-b5ab-753cade97eaa.png)


# Update question
Display the original question by entering the question id, make the changes and save it.

![image](https://user-images.githubusercontent.com/97045152/148100506-6ef83616-3d85-494b-880b-235dd1ce58bf.png)


# Update exam
Display the original exam by entering the exam id, make the changes and save it.

![image](https://user-images.githubusercontent.com/97045152/148100719-2893ae51-bbc1-42d1-8e29-2631d5b241b9.png)

# Start exam by the teacher
The teacher defins 4 digits code for the started exam(beacuse the students must insert the same code to get the exam).
Choose which exam to start and the type of it(online or manual).

![image](https://user-images.githubusercontent.com/97045152/148101145-cc448ca3-bc1f-408f-ab91-d21315fa0bb7.png)

# Confirm grades
After the online exam finished, the teacher must insert the code of the exam, starts the auto checking and then confirm the grades and she can add notes to the students.

![image](https://user-images.githubusercontent.com/97045152/148175614-c92cfc31-4ade-4caa-86c6-a9b9c8b902c4.png)

After the teacher press on the auto check button:

![image](https://user-images.githubusercontent.com/97045152/148175810-df223f37-bb77-4074-95ed-4b1bdacc78f5.png)

After the teacher press on the confirm grades button:
She pass over all the student's exam and check the exam and she can change the grade and write explanation.

![image](https://user-images.githubusercontent.com/97045152/148176024-54cd083a-eafd-4780-b1c0-b0c9dc1b41af.png)


# Request extra time
The teacher can request for extra time to the exam by sending a request to the principle, and the principle must accept it in order to update the time.

![image](https://user-images.githubusercontent.com/97045152/148101999-ea710cde-a1c9-486d-990f-6d6e53365165.png)


# Show all the exams
The teacher can see all the exams that she had wrote or started.

![image](https://user-images.githubusercontent.com/97045152/148177428-5c176600-468e-4696-9e02-e862dc342206.png)


# Students Home Page
# Display Grades
The student can show all her grades in all the courses.

![image](https://user-images.githubusercontent.com/97045152/148102382-e66030bd-d633-4fc0-99dd-ae7110ef2309.png)

# Display exams
The student can show all her exams.

![image](https://user-images.githubusercontent.com/97045152/148178767-b017ba19-859b-4cd1-9592-0da019454851.png)


# Perform an exam
Manual Exam:

  The student must download the exam to her
  computer, solve it and upload it back to the system.
  The time starts to run after the student download the exam.
  
  ![image](https://user-images.githubusercontent.com/97045152/148174006-1d68f01c-0626-4e75-a847-9a283f6caf14.png)
  
  Download it:
  ![image](https://user-images.githubusercontent.com/97045152/148174068-cf55faed-2fbc-4446-a57f-828000d3f30e.png)
  
  Upload:
  ![image](https://user-images.githubusercontent.com/97045152/148174304-ab2d9bca-0ab7-4dfe-a078-cbfaaf8fa3ff.png)

  Submit:
  ![image](https://user-images.githubusercontent.com/97045152/148174512-630c7096-2ff7-4adf-8f1c-2684d244f275.png)

  
Online Exam:

The student must enter the 4 digits code of the exam (that the teacher had declared).

Then it will open the below page, and she must enter her ID and start the exam, then the exam is displayed and the time starts to run down.
if the time runs out before the exam has been submitted, then the answers automatically saved.

![image](https://user-images.githubusercontent.com/97045152/148175219-8cb3cc0a-c8ec-481a-be5b-cd79edfc59b5.png)


# Principle Home Page
# Display Requests

![image](https://user-images.githubusercontent.com/97045152/148180188-5e50c16c-675f-47af-a5fc-228dec4f300f.png)

Display the request.
If the principle accept the request then automatically the time of the exam will update.

![image](https://user-images.githubusercontent.com/97045152/148180240-04810ccf-599d-4207-8b99-382a81e9fdcf.png)


# Display Data

![image](https://user-images.githubusercontent.com/97045152/148180548-c35c76cc-a91b-4098-95bf-c81064857d6a.png)

1) Display Results 
  
  ![image](https://user-images.githubusercontent.com/97045152/148180722-00a3c7ed-30dd-484f-8aea-a040301fedc2.png)

2) Display Exams
  
  ![image](https://user-images.githubusercontent.com/97045152/148180819-b8a8e1f2-3480-46f7-8c88-6cb95da09839.png)


3) Display Questions
  
  ![image](https://user-images.githubusercontent.com/97045152/148180909-bb2b2b5e-5913-4f55-afa6-495ae15b9292.png)
 
