package com.skiply.school.controller;

import com.skiply.school.entity.Student;
import com.skiply.school.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {

    StudentService studentService;

    @PostMapping(produces = "application/json")
    @Operation(summary = "Add a new student")
    @ApiResponse(responseCode = "400", description = "Invalid student details",
            content = @Content)
    @ApiResponse(responseCode = "200", description = "Added Student successfully",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Student.class)) })
    public Student addStudent(@Valid @RequestBody Student student) {
        return studentService.addStudent(student);
    }
}
