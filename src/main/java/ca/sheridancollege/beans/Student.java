package ca.sheridancollege.beans;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1151601425008580749L;
	
	private int id;
	private String studentName;
	private String studentId;
	private int exercises;
	private int assignment1;
	private int assignment2;
	private int assignment3;
	private int midterm;
	private int finalExam;
	private double weightedAverage;
	private String letterGrade;
}
