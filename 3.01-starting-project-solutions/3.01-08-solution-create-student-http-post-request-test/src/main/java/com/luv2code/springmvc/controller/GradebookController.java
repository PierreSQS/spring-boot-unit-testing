package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GradebookController {

    private final StudentAndGradeService studentService;

    public GradebookController(StudentAndGradeService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/")
    public String getStudents(Model model) {
		List<CollegeStudent> collegeStudents = studentService.getGradebook();
		model.addAttribute("students", collegeStudents);
        return "index";
    }

    @PostMapping("/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student) {
        studentService.createStudent(student.getFirstname(), student.getLastname(),
                student.getEmailAddress());
        return "index";
    }



    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id) {
        return "studentInformation";
    }

}
