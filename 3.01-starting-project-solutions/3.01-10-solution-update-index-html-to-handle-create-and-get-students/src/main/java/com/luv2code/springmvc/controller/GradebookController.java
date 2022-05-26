package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradebookController {


    private final StudentAndGradeService studentService;

    public GradebookController(StudentAndGradeService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String getStudents(Model model) {
        Iterable<CollegeStudent> collegeStudents = studentService.getGradebook();
        model.addAttribute("students", collegeStudents);
        return "index";
    }

    @PostMapping("/")
    public String createStudent(@ModelAttribute("student") CollegeStudent student, Model model) {
        studentService.createStudent(student.getFirstname(), student.getLastname(),
                student.getEmailAddress());
        Iterable<CollegeStudent> collegeStudents = studentService.getGradebook();
        model.addAttribute("students", collegeStudents);
        return "index";
    }

    @GetMapping("/studentInformation/{id}")
    public String studentInformation(@PathVariable int id, Model model) {
        return "studentInformation";
    }

}
