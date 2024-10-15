package com.example.cesar.service;

import com.example.cesar.dto.Course.CourseCreateDto;
import com.example.cesar.entity.Course;

import java.util.List;

public interface CourseService {
    String createCourse(CourseCreateDto courseCreateDto);

    String updateCourse();

    String deleteCourse();

    Course getCourse();

    List<Course> getCourses();
}
