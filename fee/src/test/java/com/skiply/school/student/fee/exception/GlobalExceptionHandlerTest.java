package com.skiply.school.student.fee.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skiply.school.controller.StudentController;
import com.skiply.school.entity.Student;
import com.skiply.school.exception.GlobalExceptionHandling;
import com.skiply.school.exception.InvalidDataException;
import com.skiply.school.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StudentController.class)
@ContextConfiguration(classes = {StudentController.class, GlobalExceptionHandling.class})
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testDuplicateIdException() throws Exception {
        // Arrange
        Student student = new Student();
        student.setStudentName("John Doe");
        student.setGrade("10");
        student.setMobileNumber("1234567890");
        student.setSchoolName("XYZ School");

        Mockito.when(studentService.addStudent(any(Student.class))).thenThrow(new InvalidDataException("Student with ID 1 already exists"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isBadRequest());
    }
}
