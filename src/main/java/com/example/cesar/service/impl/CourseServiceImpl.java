package com.example.cesar.service.impl;

import com.example.cesar.dto.Course.CourseCreateDto;
import com.example.cesar.dto.Course.CourseGetSingleDto;
import com.example.cesar.entity.Classroom;
import com.example.cesar.entity.Course;
import com.example.cesar.entity.User;
import com.example.cesar.repository.ClassroomRepository;
import com.example.cesar.repository.CourseRepository;
import com.example.cesar.repository.UserRepository;
import com.example.cesar.service.CourseService;
import com.example.cesar.utils.exception.ApiException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    private final ModelMapper mapper;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository, ClassroomRepository classroomRepository, ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.mapper = mapper;
    }

    @Override
    public String createCourse(CourseCreateDto courseCreateDto) {
        if(userRepository.findByEmail(courseCreateDto.getTeacherEmail()).isEmpty()) {
            throw new ApiException("This teacher email does not exist", HttpStatus.NOT_FOUND);
        }

        if(!classroomRepository.existsByName(courseCreateDto.getClassroomName())) {
            throw new ApiException("This classroom does not exist", HttpStatus.NOT_FOUND);
        }

        Classroom currentClassroom = classroomRepository.findByName(courseCreateDto.getClassroomName());

        if(courseRepository.existsByNameAndClassroomIdAndStartDateAndEndDateAndTeacherEmail(
                courseCreateDto.getName(),
                currentClassroom.getId(),
                courseCreateDto.getStartDate(),
                courseCreateDto.getEndDate(),
                courseCreateDto.getTeacherEmail()
            )
        ){
            throw new ApiException("This course already exists", HttpStatus.BAD_REQUEST);
        };

        Optional<User> teacher = userRepository.findByEmail(courseCreateDto.getTeacherEmail());

        Course course = mapper.map(courseCreateDto, Course.class);
        course.setClassroom(currentClassroom);
        course.setTeacher(teacher.get());

        courseRepository.save(course);

        return "Course successfully created";
    }

    @Override
    public String updateCourse() {
        return null;
    }

    @Override
    public String deleteCourse() {
        return null;
    }

    @Override
    public Course getCourse(CourseGetSingleDto courseGetSingleDto) {
        Optional<Course> course = courseRepository.findCourseByNameAndAndClassroomNameAndStartDate(
            courseGetSingleDto.getCourseName(),
                courseGetSingleDto.getClassroomName(),
                courseGetSingleDto.getStartDate()
        );

        if(course.get() == null) {
            throw new ApiException("Course not found", HttpStatus.NOT_FOUND);
        }

        return course.get();
    }

    @Override
    public List<Course> getCourses() {
        List<Course> courses = courseRepository.findAll();

        return courses;
    }
}
