package com.example.cesar.service.impl;

import com.example.cesar.dto.Course.AllCoursesByClassroomDto;
import com.example.cesar.dto.Course.CourseCreateDto;
import com.example.cesar.dto.Course.CourseGetSingleDto;
import com.example.cesar.dto.Course.CourseUpdateDto;
import com.example.cesar.entity.Classroom;
import com.example.cesar.entity.Course;
import com.example.cesar.entity.User;
import com.example.cesar.repository.ClassroomRepository;
import com.example.cesar.repository.CourseRepository;
import com.example.cesar.repository.UserRepository;
import com.example.cesar.service.AzureService;
import com.example.cesar.service.CourseService;
import com.example.cesar.utils.exception.ApiException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    private final ModelMapper mapper;
    private final AzureService azureService;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository, ClassroomRepository classroomRepository, ModelMapper mapper, AzureService azureService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.mapper = mapper;
        this.azureService = azureService;
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
    public String updateCourse(CourseUpdateDto courseUpdateDto, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ApiException("Course not found", HttpStatus.NOT_FOUND));

        if(userRepository.findByEmail(courseUpdateDto.getTeacherEmail()).isEmpty()) {
            throw new ApiException("This teacher email does not exist", HttpStatus.NOT_FOUND);
        }

        if(!classroomRepository.existsByName(courseUpdateDto.getClassroomName())) {
            throw new ApiException("This classroom does not exist", HttpStatus.NOT_FOUND);
        }

        if(courseUpdateDto.getStartDate().before(course.getEndDate())) {
            throw new ApiException("Start date must be after end date", HttpStatus.BAD_REQUEST);
        }

        Classroom currentClassroom = classroomRepository.findByName(courseUpdateDto.getClassroomName());

        course.setName(courseUpdateDto.getName());
        course.setStartDate(courseUpdateDto.getStartDate());
        course.setEndDate(courseUpdateDto.getEndDate());
        course.setClassroom(currentClassroom);
        course.setTeacher(userRepository.findByEmail(courseUpdateDto.getTeacherEmail()).get());

        courseRepository.save(course);

        return "Course successfully updated";
    }

    @Override
    public String deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ApiException("Course not found", HttpStatus.NOT_FOUND));

        courseRepository.delete(course);

        return "Course successfully deleted";
    }

    @Override
    public List<AllCoursesByClassroomDto> getCoursesByClassroom(String classroomName, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        if(!classroomRepository.existsByName(classroomName)) {
            throw new ApiException("Classroom not found", HttpStatus.NOT_FOUND);
        }

        Classroom classroom = classroomRepository.findByName(classroomName);

        if(!classroom.getStudents().contains(user)) {
            throw new ApiException("Student does not follow this classroom", HttpStatus.UNAUTHORIZED);
        }

        return courseRepository.findCoursesByClassroomName(classroomName).stream()
            .map(course -> mapper.map(course, AllCoursesByClassroomDto.class))
            .toList();
    }

    @Override
    public Course getCourse(CourseGetSingleDto courseGetSingleDto, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        Optional<Course> course = courseRepository.findCourseByNameAndAndClassroomNameAndStartDate(
            courseGetSingleDto.getCourseName(),
                courseGetSingleDto.getClassroomName(),
                courseGetSingleDto.getStartDate()
        );

        if(!course.get().getClassroom().getStudents().contains(user)) {
            throw new ApiException("Student does not follow this course", HttpStatus.UNAUTHORIZED);
        }

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

    @Override
    public String uploadFile(MultipartFile file, Long courseId, UserDetails userDetails) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ApiException("Course not found", HttpStatus.NOT_FOUND));

        if(!course.getTeacher().getEmail().equals(userDetails.getUsername())) {
            throw new ApiException("You are not the teacher of this course", HttpStatus.UNAUTHORIZED);
        }

        try {
            String url = azureService.uploadFile(file);

            course.getUrlFiles().add(url);
            courseRepository.save(course);

            return "File uploaded successfully";
        } catch (IOException e) {
            throw new ApiException("Error uploading file", HttpStatus.BAD_REQUEST);
        }
    }
}
