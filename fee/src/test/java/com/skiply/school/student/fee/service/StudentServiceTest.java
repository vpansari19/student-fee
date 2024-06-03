package com.skiply.school.student.fee.service;

import com.skiply.school.entity.Student;
import com.skiply.school.exception.InvalidDataException;
import com.skiply.school.repository.StudentRepository;
import com.skiply.school.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void testAddStudent_ValidStudent() {

        Student student = new Student();
        student.setStudentName("John");
        student.setGrade("10");
        student.setMobileNumber("1234567890");
        student.setSchoolName("XYZ School");

        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.addStudent(student);

        verify(studentRepository, times(1)).save(student);
        assertEquals(student, result);
    }

    @Test
    public void testAddStudent_InvalidGrade() {
        Student student = new Student();
        student.setStudentName("John");
        student.setGrade("15"); // Invalid grade
        student.setMobileNumber("1234567890");
        student.setSchoolName("XYZ School");
        assertThrows(InvalidDataException.class, () -> studentService.addStudent(student));
    }

    @Test
    public void testAddStudent_InvalidMobileNumber() {
        Student student = new Student();
        student.setStudentName("John");
        student.setGrade("10");
        student.setMobileNumber("123");
        student.setSchoolName("XYZ School");
        assertThrows(InvalidDataException.class, () -> studentService.addStudent(student));
    }
}
