package com.skiply.school.student.fee.controller;

import com.skiply.school.controller.StudentController;
import com.skiply.school.entity.Student;
import com.skiply.school.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {
    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void testAddStudent() {
        // Mock student object
        Student student = new Student();
        student.setStudentName("Student");
        student.setGrade("10");
        student.setMobileNumber("1234567890");
        student.setSchoolName("XYZ School");

        when(studentService.addStudent(any(Student.class))).thenReturn(student);
        Student response = studentController.addStudent(student);
        assertEquals(response.getStudentName(), "Student");
    }
}
