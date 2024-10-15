package com.example.cesar.service.impl;

import com.example.cesar.dto.Course.CourseCreateDto;
import com.example.cesar.entity.Classroom;
import com.example.cesar.entity.Course;
import com.example.cesar.repository.ClassroomRepository;
import com.example.cesar.repository.CourseRepository;
import com.example.cesar.service.CourseService;
import com.example.cesar.utils.exception.ApiException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final ClassroomRepository classroomRepository;
    private final ModelMapper mapper;

    public CourseServiceImpl(CourseRepository courseRepository, ClassroomRepository classroomRepository, ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.classroomRepository = classroomRepository;
        this.mapper = mapper;
    }

    @Override
    public String createCourse(CourseCreateDto courseCreateDto) {
        if(!classroomRepository.existsByName(courseCreateDto.getClassroomName())) {
            throw new ApiException("This classroom does not exist", HttpStatus.NOT_FOUND);
        }

        Classroom currentClassroom = classroomRepository.findByName(courseCreateDto.getClassroomName());

        if(courseRepository.existsByNameAndClassroomIdAndStartDateAndEndDate(
                courseCreateDto.getName(),
                currentClassroom.getId(),
                courseCreateDto.getStartDate(),
                courseCreateDto.getEndDate()
            )
        ){
            throw new ApiException("This course already exists", HttpStatus.BAD_REQUEST);
        };

        Course course = mapper.map(courseCreateDto, Course.class);
        course.setClassroom(currentClassroom);

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
    public String getCourse() {
        return null;
    }

    @Override
    public String getCourses() {
        return null;
    }
}
