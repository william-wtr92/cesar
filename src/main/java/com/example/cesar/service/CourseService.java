package com.example.cesar.service;

import com.example.cesar.dto.Course.CourseCreateDto;
import com.example.cesar.dto.Course.CourseGetSingleDto;
import com.example.cesar.entity.Course;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    Course getCourse(CourseGetSingleDto courseGetSingleDto, UserDetails userDetails);

    List<Course> getCourses();

    String createCourse(CourseCreateDto courseCreateDto);

    String updateCourse();

    String deleteCourse(Long courseId);

    String uploadFile(MultipartFile file, Long courseId, UserDetails userDetails);
}
