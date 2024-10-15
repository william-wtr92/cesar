package com.example.cesar.service;

import com.example.cesar.dto.Course.CourseCreateDto;

public interface CourseService {
    String createCourse(CourseCreateDto courseCreateDto);

    String updateCourse();

    String deleteCourse();

    String getCourse();

    String getCourses();
}
