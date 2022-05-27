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

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GradebookController.class)
class GradebookControllerPierrotTest {

    @BeforeEach
    void setUp() {
        CollegeStudent student1 = new GradebookCollegeStudent("Eric", "Roby",
                "eric_roby@luv2code_school.com");
        student1.setId(1);

        CollegeStudent student2 = new GradebookCollegeStudent("Chad", "Darby",
                "chad_darby@luv2code_school.com");
        student2.setId(2);

        // Given
        given(studentAndGradeSrvMock.getGradebook()).willReturn(List.of(student1, student2));
    }

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentAndGradeService studentAndGradeSrvMock;

    @Test
    void getStudents() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("students",hasSize(2)))
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Darby")))
                .andDo(print());
    }

    @Test
    void createStudent() throws Exception {
        // Given
        MultiValueMap<String,String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("firstname","Tiger");
        multiValueMap.add("lastname","Scott");
        multiValueMap.add("emailAddress","tiger.scott@luv2code.com");

        // When, Then
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(multiValueMap))
                .andExpect(status().isOk())
                .andExpect(model().attribute("student",
                        hasProperty("firstname",equalTo("Tiger"))))
                .andExpect(model().attribute("students",hasSize(2)))
                .andExpect(view().name("index"))
                .andDo(print());
    }
}