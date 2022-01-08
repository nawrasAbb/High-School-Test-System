package FINAL.project;
import FINAL.project.Question;  
import FINAL.project.Teacher;
import FINAL.project.Subject;
import FINAL.project.Course;

import java.io.Serializable;
import java.util.List; 
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "exams")
public class Exam implements Serializable {

	private static final long serialVersionUID = -7029466617153317290L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ExamID;

	private int Exam_id;
	@Column(name = "Time_Exam")
	private double Time;
	@Column
	@ElementCollection(targetClass = Integer.class)
	private List<Integer> Points;
	private Boolean ExamIsOut;
	private String NotesForStudent;
	private String NotesForTeacher;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_id")
	private Subject subjectOfExam;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "courseid")
	private Course courseOfExam;
	
	@Column
	@ElementCollection(targetClass = Question.class)
	private List<Question> questionList;



	public Exam() {
	}

	public Exam( int exam_id, double time, List<Integer> points, Teacher teacher, 
			String notesForStudent, String notesForTeacher, Subject subjectOfExam,Course courseOfExam,
			List<Question> questionList) {
		super();
		this.Exam_id = exam_id;
		this.Time = time;
		this.Points = points;
		this.teacher = teacher;
		this.ExamIsOut = false;
		this.NotesForStudent = notesForStudent;
		this.NotesForTeacher = notesForTeacher;
		this.subjectOfExam = subjectOfExam;
		this.courseOfExam = courseOfExam;
		this.questionList = questionList;

	}

	/**** getters *****/
	public Subject getSubjectOfExam() {
		return this.subjectOfExam;
	}



	public Course getCourseOfExam() {
		return this.courseOfExam;
	}

	public List<Question> getQuestions() {
		return this.questionList;
	}
	public int getColumn_id() {
		return this.ExamID;
	}
	

	public int getExam_id() {
		return this.Exam_id;
	}

	public double getTime() {
		return this.Time;
	}

	public List<Integer> getPoints() {
		return this.Points;
	}

	public Boolean getExamIsOut() {
		return this.ExamIsOut;
	}

	public String getNotesForStudent() {
		return this.NotesForStudent;
	}

	public String getNotesForTeacher() {
		return this.NotesForTeacher;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}
	

	/**** setters *****/



	public void setSubjectOfExam(Subject subjectOfExam) {
		this.subjectOfExam = subjectOfExam;
	}

	public void setCourseOfExam(Course courseOfExam) {
		this.courseOfExam = courseOfExam;
	}

	public void setQuestions(List<Question> questions) {
		this.questionList = questions;
	}

	public void setExam_id(int exam_id) {
		this.Exam_id = exam_id;
	}

	public void setTime(double time) {
		Time = time;
	}

	public void setPoints(List<Integer> points) {
		this.Points = points;
	}

	public void setExamIsOut(Boolean examIsOut) {
		this.ExamIsOut = examIsOut;
	}

	public void setNotesForStudent(String notesForStudent) {
		this.NotesForStudent = notesForStudent;
	}

	public void setNotesForTeacher(String notesForTeacher) {
		this.NotesForTeacher = notesForTeacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
		teacher.getWrittenExamsList().add(this);
	}
	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
	public void addQuestion(Question... question2) {
		for (Question ques : question2) {
			questionList.add(ques);
		}
	}

	public void addPoints(Integer... grades) {
		for (Integer grade : grades) {
			Points.add(grade);
		}
	}





}

