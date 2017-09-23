package com.example.tutorial3.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.tutorial3.model.StudentModel;
import com.example.tutorial3.service.InMemoryStudentService;
import com.example.tutorial3.service.StudentService;

@Controller
public class StudentController {
	private final StudentService studentService;
	
	public StudentController() {
		studentService = new InMemoryStudentService();
	}
	
//	Add a new student using Request Parameter
	@RequestMapping("/student/add")
	public String add(@RequestParam(value = "npm", required = true) String npm, @RequestParam(value = "name", required = true) String name, @RequestParam(value = "gpa", required = true) double gpa) {
		StudentModel student = new StudentModel(npm, name, gpa);
		studentService.addStudent(student);
		return "add";
	}
	
//	View using Request Parameter
	@RequestMapping("/student/view")
	public String view(Model model, @RequestParam(value = "npm", required = true) String npm) {
		StudentModel student = studentService.selectStudent(npm);
		if (student == null) {
			model.addAttribute("npm", npm);
			return "notfound";
		}
		model.addAttribute("student", student);
		return "view";
	}
	
//	View using Path Variable
	@RequestMapping("/student/view/{npm}")
	public String viewPath(@PathVariable String npm, Model model) {
		StudentModel student = studentService.selectStudent(npm);
		if (student == null) {
			model.addAttribute("npm", npm);
			return "notfound";
		}
		model.addAttribute("student", student);
		return "view";
	}
	
//	View list of students
	@RequestMapping("/student/viewall")
	public String viewAll(Model model) {
		List<StudentModel> students = studentService.selectAllStudents();
		model.addAttribute("students", students);
		return "viewall";
	}
	
//	Delete using Request Parameter
	/*@RequestMapping("/student/delete")
	public String delete(@RequestParam(value = "npm", required = true) String npm, Model model) {
		model.addAttribute("npm", npm);
		boolean deleted = studentService.removeStudent(npm);
		if (!deleted) {
			return "notfound";
		}
		return "delete";
	}*/
	
//	Delete using Path Variable
	@RequestMapping(value = {"/student/delete", "/student/delete/{npm}"})
	public String deletePath(@PathVariable String npm, Model model) {
		model.addAttribute("npm", npm);
		boolean deleted = studentService.removeStudent(npm);
		if (!deleted) {
			return "notfound";
		}
		return "delete";
	}
	
//	Handle missing request parameter exceptions
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public @ResponseBody String handleMissingParams(MissingServletRequestParameterException ex) {
		String missingParam = ex.getParameterName();
		if (missingParam.equals("npm")) {
			missingParam = "NPM";
		} else if (missingParam.equals("name")) {
			missingParam = "Nama";
		} else if (missingParam.equals("gpa")) {
			missingParam = "GPA";
		}
		return "<!DOCTYPE html>\r\n" + 
				"<html xmlns:th = \"http://www.thymeleaf.org\">\r\n" + 
				"	<head>\r\n" + 
				"	<title>Error 400</title>\r\n" + 
				"	</head>\r\n" + 
				"	<body>\r\n" + 
				"		<h3>Tolong masukkan parameter " + missingParam + ".</h3>\r\n" +
				"	</body>\r\n" + 
				"</html>";
	}
	
//	Handle missing path variable exceptions
	@ExceptionHandler(MissingPathVariableException.class)
	public @ResponseBody String handleMissingPathVars(MissingPathVariableException ex) {
		String missingPath = ex.getVariableName();
		if (missingPath.equals("npm")) {
			missingPath = "NPM";
		} else if (missingPath.equals("name")) {
			missingPath = "Nama";
		} else if (missingPath.equals("gpa")) {
			missingPath = "GPA";
		}
		return "<!DOCTYPE html>\r\n" + 
				"<html xmlns:th = \"http://www.thymeleaf.org\">\r\n" + 
				"	<head>\r\n" + 
				"	<title>Error 400</title>\r\n" + 
				"	</head>\r\n" + 
				"	<body>\r\n" + 
				"		<h3>Tolong masukkan path " + missingPath + ".</h3>\r\n" +
				"	</body>\r\n" + 
				"</html>";
	}
}
