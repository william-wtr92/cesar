package com.example.cesar.service;

import com.example.cesar.dto.Course.CourseCreateDto;
import com.example.cesar.dto.Course.CourseGetSingleDto;
import com.example.cesar.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course getCourse(CourseGetSingleDto courseGetSingleDto);

    List<Course> getCourses();

    String createCourse(CourseCreateDto courseCreateDto);

    String updateCourse();

    String deleteCourse();
}
