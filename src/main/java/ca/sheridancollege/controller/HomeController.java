package ca.sheridancollege.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ca.sheridancollege.beans.Student;
import ca.sheridancollege.database.DatabaseAccess;

@Controller
public class HomeController {
	
	@Autowired
	@Lazy
	private DatabaseAccess da;
	
	@GetMapping("/")
	public String toHome() {
		return "home.html";
	}
	
	@GetMapping("/new")
	public String goCreateStudent(Model model) {
		model.addAttribute("student", new Student());
		return "createStudent.html";
	}
	
	@PostMapping("/new")
	public String doCreateStudent(Model model, @ModelAttribute Student student) {
		da.addStudent(student);
		da.addUser(student.getStudentName(), student.getStudentId());
		long userId = da.findUserAccount(student.getStudentName()).getUserId();
		da.addRole(userId, 1);
		model.addAttribute("student", new Student());
		return "createStudent.html";
	}
	
	@GetMapping("/view")
	public String viewStudent(Authentication authentication, Model model) {
		ArrayList<Student> students = da.getStudents();
		model.addAttribute("students", students);
		model.addAttribute("exercises", da.calculateSubjectAverage(students, "exercises"));
		model.addAttribute("assignment1", da.calculateSubjectAverage(students, "assignment1"));
		model.addAttribute("assignment2", da.calculateSubjectAverage(students, "assignment2"));
		model.addAttribute("assignment3", da.calculateSubjectAverage(students, "assignment3"));
		model.addAttribute("midterm", da.calculateSubjectAverage(students, "midterm"));
		model.addAttribute("finalExam", da.calculateSubjectAverage(students, "finalExam"));
		model.addAttribute("weightedAverage", da.calculateSubjectAverage(students, "weightedAverage"));
		
		ArrayList<String> roles = new ArrayList<>();
		for(GrantedAuthority ga: authentication.getAuthorities()) {
			roles.add(ga.getAuthority());
		}
		if(roles.get(0).equals("ROLE_STUDENT")) {
			String name = authentication.getName();
			model.addAttribute("student", da.getStudentByName(name));
			return "studentView.html";
		}
		return "viewStudent.html";
	}
	
	@GetMapping("/edit/{id}")
	public String editStudent(Model model, @PathVariable int id) {
		Student c = da.getStudentById(id);
		model.addAttribute("student", c);
		return "modifier.html";
	}
	
	@GetMapping("/modify")
	public String modifyStudent(Model model,@ModelAttribute Student student) {
		da.updateStudent(student);
		model.addAttribute("students", da.getStudents());
		return "viewStudent.html";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteStudent(Model model, @PathVariable int id) {
		da.deleteStudent(id);
		model.addAttribute("students", da.getStudents());
		return "viewStudent.html";
	}
	
	@GetMapping("/login")
	public String toLogin() {
		return "login.html";
	}
	
	@GetMapping("/access-denied")
	public String goAccessDenied() {
		return "/error/access-denied.html";
	}
}
