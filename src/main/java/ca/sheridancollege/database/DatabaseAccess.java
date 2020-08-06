package ca.sheridancollege.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Student;
import ca.sheridancollege.beans.User;

@Repository
public class DatabaseAccess {
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	public void addStudent(Student student) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO student_data (studentName, studentId, exercises, assignment1, assignment2, assignment3, midterm, finalExam, weightedAverage, letterGrade) VALUES " + 
				"(:studentName, :studentId, :exercises, :assignment1, :assignment2, :assignment3, :midterm, :finalExam, :weightedAverage, :letterGrade)";
		parameters.addValue("studentName", student.getStudentName());
		parameters.addValue("studentId", student.getStudentId());
		parameters.addValue("exercises", student.getExercises());
		parameters.addValue("assignment1", student.getAssignment1());
		parameters.addValue("assignment2", student.getAssignment2());
		parameters.addValue("assignment3", student.getAssignment3());
		parameters.addValue("midterm", student.getMidterm());
		parameters.addValue("finalExam", student.getFinalExam());
		parameters.addValue("weightedAverage", calculateWeightedAverage(student));
		parameters.addValue("letterGrade", calculateLetterGrade(calculateWeightedAverage(student)));

		jdbc.update(query, parameters);
		
	}
	
	public ArrayList<Student> getStudents(){
		String q = "SELECT * FROM student_data";
		ArrayList <Student> students = new ArrayList<>();
		List<Map<String, Object>> rows = jdbc.queryForList(q, new HashMap<String,Object>());
		for(Map<String, Object> row : rows) {
			Student c = new Student();
			c.setId((Integer)(row.get("id")));
			c.setStudentName((String)(row.get("studentName")));
			c.setStudentId((String)(row.get("studentId")));
			c.setExercises((Integer)(row.get("exercises")));
			c.setAssignment1((Integer)(row.get("assignment1")));
			c.setAssignment2((Integer)(row.get("assignment2")));
			c.setAssignment3((Integer)(row.get("assignment3")));
			c.setMidterm((Integer)(row.get("midterm")));
			c.setFinalExam((Integer)(row.get("finalExam")));
			c.setWeightedAverage(((java.math.BigDecimal)(row.get("weightedAverage"))).doubleValue());
			c.setLetterGrade((String)(row.get("letterGrade")));
			students.add(c);
		}
		
		return students;
	}
	
	public Student getStudentById(int id) {
		String q = "SELECT * FROM student_data WHERE id=:id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		ArrayList <Student> students = new ArrayList<>();
		List<Map<String, Object>> rows = jdbc.queryForList(q, parameters);
		for(Map<String, Object> row : rows) {
			Student c = new Student();
			c.setId((Integer)(row.get("id")));
			c.setStudentName((String)(row.get("studentName")));
			c.setStudentId((String)(row.get("studentId")));
			c.setExercises((Integer)(row.get("exercises")));
			c.setAssignment1((Integer)(row.get("assignment1")));
			c.setAssignment2((Integer)(row.get("assignment2")));
			c.setAssignment3((Integer)(row.get("assignment3")));
			c.setMidterm((Integer)(row.get("midterm")));
			c.setFinalExam((Integer)(row.get("finalExam")));
			c.setWeightedAverage(((java.math.BigDecimal)(row.get("weightedAverage"))).doubleValue());
			c.setLetterGrade((String)(row.get("letterGrade")));
			students.add(c);
		}
		if(students.size() > 0) {
			return students.get(0);
		}
		return null;
	}
	
	public Student getStudentByName(String name) {
		String q = "SELECT * FROM student_data WHERE studentName=:studentName";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("studentName", name);
		ArrayList <Student> students = new ArrayList<>();
		List<Map<String, Object>> rows = jdbc.queryForList(q, parameters);
		for(Map<String, Object> row : rows) {
			Student c = new Student();
			c.setId((Integer)(row.get("id")));
			c.setStudentName((String)(row.get("studentName")));
			c.setStudentId((String)(row.get("studentId")));
			c.setExercises((Integer)(row.get("exercises")));
			c.setAssignment1((Integer)(row.get("assignment1")));
			c.setAssignment2((Integer)(row.get("assignment2")));
			c.setAssignment3((Integer)(row.get("assignment3")));
			c.setMidterm((Integer)(row.get("midterm")));
			c.setFinalExam((Integer)(row.get("finalExam")));
			c.setWeightedAverage(((java.math.BigDecimal)(row.get("weightedAverage"))).doubleValue());
			c.setLetterGrade((String)(row.get("letterGrade")));
			students.add(c);
		}
		if(students.size() > 0) {
			return students.get(0);
		}
		return null;
	}
	
	public void updateStudent(Student student) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "UPDATE student_data SET studentName=:studentName, studentId=:studentId, exercises=:exercises, assignment1=:assignment1, assignment2=:assignment2, assignment3=:assignment3, midterm=:midterm, finalExam=:finalExam WHERE id=:id";
		parameters.addValue("id", student.getId());
		parameters.addValue("studentName", student.getStudentName());
		parameters.addValue("studentId", student.getStudentId());
		parameters.addValue("exercises", student.getExercises());
		parameters.addValue("assignment1", student.getAssignment1());
		parameters.addValue("assignment2", student.getAssignment2());
		parameters.addValue("assignment3", student.getAssignment3());
		parameters.addValue("midterm", student.getMidterm());
		parameters.addValue("finalExam", student.getFinalExam());
		parameters.addValue("weightedAverage", calculateWeightedAverage(student));
		parameters.addValue("letterGrade", calculateLetterGrade(calculateWeightedAverage(student)));
		jdbc.update(query, parameters);
	}
	
	public void deleteStudent(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM student_data WHERE id=:id";
		parameters.addValue("id", id);
		jdbc.update(query, parameters);
	}
	
	public double calculateWeightedAverage(Student student) {
		double weghtedAverage = student.getExercises() * 0.1 + 
						student.getAssignment1() * 0.06 +
						student.getAssignment2() * 0.12 + 
						student.getAssignment3() * 0.12 +
						student.getMidterm() * 0.3 +
						student.getFinalExam() * 0.3;
		weghtedAverage = weghtedAverage * 100;
		double tmp = Math.round(weghtedAverage);
		return (double)(tmp/100);
	}
	
	public String calculateLetterGrade(double weghtedAverage) {
		String letterGrade = "";
		if(weghtedAverage >= 90 && weghtedAverage <= 100) {
			letterGrade = "A+";
		} else if(weghtedAverage >= 80 && weghtedAverage < 90) {
			letterGrade = "A";
		} else if(weghtedAverage >= 75 && weghtedAverage < 80) {
			letterGrade = "B+";
		} else if(weghtedAverage >= 70 && weghtedAverage < 75) {
			letterGrade = "B";
		} else if(weghtedAverage >= 65 && weghtedAverage < 70) {
			letterGrade = "C+";
		} else if(weghtedAverage >= 60 && weghtedAverage < 65) {
			letterGrade = "C";
		} else if(weghtedAverage >= 50 && weghtedAverage < 60) {
			letterGrade = "D";
		} else if(weghtedAverage < 50) {
			letterGrade = "F";
		} else {
			letterGrade = "";
		}
		return letterGrade;
	}
	
	public double calculateSubjectAverage(ArrayList<Student> students, String subject) {
		double average = 0;
		int total = 0;
		switch(subject) {
			case "exercises":
				for(Student student: students) {
					total += student.getExercises();
				}
				average = total/students.size();
				break;
			case "assignment1":
				for(Student student: students) {
					total += student.getAssignment1();
				}
				average = total/students.size();
				break;
			case "assignment2":
				for(Student student: students) {
					total += student.getAssignment2();
				}
				average = total/students.size();
				break;
			case "assignment3":
				for(Student student: students) {
					total += student.getAssignment3();
				}
				average = total/students.size();
				break;	
			case "midterm":
				for(Student student: students) {
					total += student.getMidterm();
				}
				average = total/students.size();
				break;
			case "finalExam":
				for(Student student: students) {
					total += student.getFinalExam();
				}
				average = total/students.size();
				break;
			case "weightedAverage":
				for(Student student: students) {
					total += student.getWeightedAverage();
				}
				average = total/students.size();
				break;
			default:
				average = 0;
		}
		average = average * 100;
		double tmp = Math.round(average);
		return (double)(tmp/100);

	}
	
	public User findUserAccount(String userName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user WHERE userName=:userName";
		parameters.addValue("userName", userName);
		ArrayList<User> users = (ArrayList<User>)jdbc.query(query,parameters,
				new BeanPropertyRowMapper<User>(User.class));
		if(users.size()>0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	public List<String> getRolesById(long userId){
		ArrayList<String> roles = new ArrayList<String>();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "select user_role.userId, sec_role.roleName "
					+ "FROM user_role, sec_role "
					+ "WHERE user_role.roleId=sec_role.roleId "
					+ "and userId=:userId";
		parameters.addValue("userId", userId);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for(Map<String, Object> row: rows) {
			roles.add((String)row.get("roleName"));
		}
		return roles;
	}
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	public void addUser(String username, String password) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO SEC_User "
						+ "(userName, encryptedPassword, ENABLED)"
						+ " values (:userName, :encryptedPassword, 1);";
		parameters.addValue("userName", username);
		parameters.addValue("encryptedPassword", passwordEncoder.encode(password));
		jdbc.update(query, parameters);
	}
	
	public void addRole(long userId, long roleId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO user_role (userId, roleId) "
						+ "values (:userId,:roleId);";
		parameters.addValue("userId", userId);
		parameters.addValue("roleId", roleId);
		jdbc.update(query, parameters);
	}

}
