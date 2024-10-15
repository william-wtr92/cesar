package com.example.cesar.repository;

import com.example.cesar.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface CourseRepository extends JpaRepository <Course, Long> {
    boolean existsByNameAndClassroomIdAndStartDateAndEndDateAndTeacherEmail(
            String courseName,
            Long classroomId,
            Date startDate,
            Date endDate,
            String teacherEmail
    );
}
