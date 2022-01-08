package FINAL.project;
import FINAL.project.Subject;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "questions")
public class Question implements Serializable {


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int question_id;
	private String content;
	private int CorrectAnswer;
	private String[] answers;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_id")
	private Subject TheSubject;
	private int numAnswer=0;

	public Question() {}


	
	
	public Question(int id, String content, Subject subject) {
		super();
		this.question_id = id;
		this.content = content;
		this.CorrectAnswer = 0;
		this.answers = new String[4];
		this.TheSubject = subject;
	}

	/***** getters ****/
	public int getColumn_id() {
		return this.id;
	}
	
	public int getCorrectAnswer() {
		return this.CorrectAnswer;
	}

	public String getContent() {
		return this.content;
	}

	public int getQuestion_id() {
		return this.question_id;
	}

	public String getAnswers(int i) {
		return this.answers[i];
	}
	public Subject getSubject() {
		return this.TheSubject;
	}

	/***** setters ****/

	public void setCorrectAnswer(int correctAnswer) {
		this.CorrectAnswer = correctAnswer;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void AddAnswer(String answer) {
		answers[numAnswer++] = answer;
	}

	public void setAnswer(int i, String Ans) {
		this.answers[i] = Ans;
	}
	public void setSubject(Subject subject) {
		this.TheSubject=subject;
	}
}
