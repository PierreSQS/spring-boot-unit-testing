package com.luv2code.springmvc.controller;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GradebookController.class)
class GradebookControllerPierrotTest {

    private CollegeStudent student1;
    private CollegeStudent student2;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentAndGradeService studentAndGradeSrvMock;

    @BeforeEach
    void setUp() {
        student1 = new GradebookCollegeStudent("Eric", "Roby",
                "eric_roby@luv2code_school.com");

        student2 = new GradebookCollegeStudent("Chad", "Darby",
                "chad_darby@luv2code_school.com");
    }

    @Test
    void getStudents() throws Exception {
        // Given
        List<CollegeStudent> collegeStudentList = List.of(student1,student2);

        given(studentAndGradeSrvMock.getGradebook()).willReturn(collegeStudentList);

        // When, Then
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("students"))
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("<title>Grade Book App</title>")))
                .andDo(print());

        verify(studentAndGradeSrvMock).getGradebook();
    }

    @Test
    void createStudent() throws Exception {
        // Given
        MultiValueMap<String,String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("firstname",student1.getFirstname());
        multiValueMap.add("lastname",student1.getLastname());
        multiValueMap.add("emailAddress",student1.getEmailAddress());

        // When, Then
        mockMvc.perform(post("/")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .params(multiValueMap))
                .andExpect(status().isOk())
                .andExpect(model().attribute("student",
                        hasProperty("firstname", equalTo("Eric"))))
                .andExpect(view().name("index"))
                .andDo(print());
    }
}