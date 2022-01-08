package FINAL.project;
import FINAL.project.StartExam;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Documentation")
public class Documentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private  LocalDateTime ExamDate;
	private double InitialTime;
	private double ActualTime;
	private int ExamineeNumber;
	private int SelfSubmittedNumber;
	private int DidntCompleteNumber;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "startExam_id")
	private StartExam startexam;
	

	
	/**** Constructor **/
	public Documentation( double InitialTime, double ActualTime, int ExamineeNumber,
			int SelfSubmittedNumber, int DidntCompleteNumber, StartExam startExam2) {
		this.ExamDate = LocalDateTime.now();  
		this.InitialTime = InitialTime;
		this.ActualTime = ActualTime;
		this.ExamineeNumber = ExamineeNumber;
		this.SelfSubmittedNumber = SelfSubmittedNumber;
		this.DidntCompleteNumber = DidntCompleteNumber;
		this.startexam = startExam2;

	}	
	

	/*** Getters ***/

	public StartExam getExam() {
		return this.startexam;
	}

	public double getInitialTime() {
		return this.InitialTime;
	}

	public double getActualTime() {
		return this.ActualTime;
	}

	public int getExamineeNumber() {
		return this.ExamineeNumber;
	}

	public int getSelfSubmittedNumber() {
		return this.SelfSubmittedNumber;
	}

	public int getDidntCompleteNumber() {
		return this.DidntCompleteNumber;
	}

	/*** Setter ***/

	public void setExam(StartExam exam) {
		this.startexam = exam;
	}

	public LocalDateTime getExamDate() {
		return ExamDate;
	}


	public void setExamDate(LocalDateTime examDate) {
		ExamDate = examDate;
	}


	public StartExam getStartexam() {
		return startexam;
	}


	public void setStartexam(StartExam startexam) {
		this.startexam = startexam;
	}


	public void setInitialTime(double InitialTime) {
		this.InitialTime = InitialTime;
	}

	public void setActualTime(double ActualTime) {
		this.ActualTime = ActualTime;
	}

	public void setExamineeNumber(int ExamineeNumber) {
		this.ExamineeNumber = ExamineeNumber;
	}

	public void setSelfSubmittedNumber(int SelfSubmittedNumber) {
		this.SelfSubmittedNumber = SelfSubmittedNumber;
	}

	public void setDidntCompleteNumber(int DidntCompleteNumber) {
		this.DidntCompleteNumber = DidntCompleteNumber;
	}


}