package com.example.tutorial3.service;

import java.util.ArrayList;
import java.util.List;

import com.example.tutorial3.model.StudentModel;

public class InMemoryStudentService implements StudentService {
	private static List<StudentModel> studentList = new ArrayList<StudentModel>();
	
	@Override
	public StudentModel selectStudent(String npm) {
		for (StudentModel result : studentList) {
			if (result.getNpm().contains(npm)) {
				return result;
			}
		}
		return null;
	}

	@Override
	public List<StudentModel> selectAllStudents() {
		return studentList;
	}

	@Override
	public void addStudent(StudentModel student) {
		studentList.add(student);
	}

	@Override
	public boolean removeStudent(String npm) {
		StudentModel target = this.selectStudent(npm);
		if (target == null) {
			return false;
		}
		studentList.remove(target);
		return true;
	}

}
