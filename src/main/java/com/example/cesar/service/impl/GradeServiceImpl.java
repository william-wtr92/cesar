package com.example.cesar.service.impl;

import com.example.cesar.dto.GradeDto;
import com.example.cesar.entity.Classroom;
import com.example.cesar.entity.Course;
import com.example.cesar.entity.Grade;
import com.example.cesar.entity.User;
import com.example.cesar.repository.CourseRepository;
import com.example.cesar.repository.GradeRepository;
import com.example.cesar.repository.UserRepository;
import com.example.cesar.service.GradeService;
import com.example.cesar.utils.constants.RoleConstants;
import com.example.cesar.utils.exception.ApiException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    public GradeServiceImpl(GradeRepository gradeRepository, UserRepository userRepository,  CourseRepository courseRepository, ModelMapper mapper) {
        this.gradeRepository = gradeRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public String addGrade(GradeDto gradeDto, UserDetails userDetails) {
        String email = userDetails.getUsername();
        User teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Teacher not found", HttpStatus.NOT_FOUND));

        if (!teacher.getRole().getName().equals(RoleConstants.ROLE_TEACHER)) {
            throw new ApiException("User is not a teacher", HttpStatus.BAD_REQUEST);
        }

        Optional<User> studentOpt = userRepository.findByEmail(gradeDto.getStudentEmail());

        if (studentOpt.isEmpty()){
            throw new ApiException("Student not found", HttpStatus.NOT_FOUND);
        } else if (!studentOpt.get().getRole().getName().equals(RoleConstants.ROLE_STUDENT)) {
            throw new ApiException("User is not a student", HttpStatus.BAD_REQUEST);
        }

        Optional<Course> courseOpt = courseRepository.findById(gradeDto.getCourseId());

        if (courseOpt.isEmpty()) {
            throw new ApiException("Course not found", HttpStatus.NOT_FOUND);
        }

        if(!courseOpt.get().getTeacher().getEmail().equals(userDetails.getUsername())) {
            throw new ApiException("You are not the teacher of this course", HttpStatus.UNAUTHORIZED);
        }

        Classroom classroom = courseOpt.get().getClassroom();

        if(!classroom.getStudents().contains(studentOpt.get())){
            throw new ApiException("Student not in the course classroom", HttpStatus.BAD_REQUEST);
        }

        Grade gradeIsPresent = gradeRepository.findByStudentAndCourse(studentOpt.get(), courseOpt.get());
        if (gradeIsPresent != null) {
            throw new ApiException("Student already has a grade for this course", HttpStatus.BAD_REQUEST);
        }

        Grade grade = mapper.map(gradeDto, Grade.class);
        grade.setTeacher(teacher);
        grade.setStudent(studentOpt.get());
        grade.setCourse(courseOpt.get());

        gradeRepository.save(grade);

        return "Grade added successfully";
    }
}
