package com.example.cesar.repository;

import com.example.cesar.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface CourseRepository extends JpaRepository <Course, Long> {
    boolean existsByNameAndClassroomIdAndStartDateAndEndDateAndTeacherEmail(
            String courseName,
            Long classroomId,
            Date startDate,
            Date endDate,
            String teacherEmail
    );

    Optional<Course> findCourseByNameAndAndClassroomNameAndStartDate(
            String courseName,
            String classroomName,
            Date startDate
    );
}
