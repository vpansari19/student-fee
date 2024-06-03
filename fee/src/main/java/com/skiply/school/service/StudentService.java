package com.skiply.school.service;

import ch.qos.logback.core.util.StringUtil;
import com.skiply.school.data.Constants;
import com.skiply.school.entity.Student;
import com.skiply.school.exception.InvalidDataException;
import com.skiply.school.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    StudentRepository studentRepository;

    public Student addStudent(Student student) {
        String grade = student.getGrade();
        if(StringUtils.isEmpty(grade) || Integer.parseInt(grade) < 1 || Integer.parseInt(grade) > 12) {
            log.error(Constants.BETWEEN_1_AND_12);
            throw new InvalidDataException(Constants.BETWEEN_1_AND_12);
        }
        if(StringUtils.isEmpty(student.getMobileNumber()) ||student.getMobileNumber().length() !=10) {
            log.error(Constants.VALID_MOBILE_NUMBER);
            throw new InvalidDataException(Constants.VALID_MOBILE_NUMBER);
        }
        return studentRepository.save(student);
    }

}
