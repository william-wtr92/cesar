package com.example.cesar.service;

import com.example.cesar.dto.Course.AllCoursesByClassroomDto;
import com.example.cesar.dto.Course.CourseCreateDto;
import com.example.cesar.dto.Course.CourseGetSingleDto;
import com.example.cesar.dto.Course.CourseUpdateDto;
import com.example.cesar.entity.Course;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    Course getCourse(CourseGetSingleDto courseGetSingleDto, UserDetails userDetails);

    List<Course> getCourses();

    String createCourse(CourseCreateDto courseCreateDto);

    String updateCourse(CourseUpdateDto courseUpdateDto, Long courseId);

    String deleteCourse(Long courseId);

    List<AllCoursesByClassroomDto> getCoursesByClassroom(String classroomName, UserDetails userDetails);

    String uploadFile(MultipartFile file, Long courseId, UserDetails userDetails);
}
