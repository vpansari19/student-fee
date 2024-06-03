package com.skiply.school.repository;

import com.skiply.school.entity.Grades;
import com.skiply.school.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GradesRepository extends JpaRepository<Grades, Integer> {
    @Query("SELECT g.gradeValue FROM Grades g WHERE g.gradeNumber = :gradeNumber")
    Double findFeesByGrade(@Param("gradeNumber") int gradeNumber);
}
