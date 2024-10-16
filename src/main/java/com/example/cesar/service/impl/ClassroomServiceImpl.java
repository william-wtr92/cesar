package com.example.cesar.service.impl;

import com.example.cesar.dto.Classroom.ClassroomCreateDto;
import com.example.cesar.dto.Classroom.StudentsInClassroomDto;
import com.example.cesar.dto.User.UserClassroomDto;
import com.example.cesar.entity.Classroom;
import com.example.cesar.entity.User;
import com.example.cesar.repository.ClassroomRepository;
import com.example.cesar.repository.UserRepository;
import com.example.cesar.service.ClassroomService;
import com.example.cesar.utils.constants.RoleConstants;
import com.example.cesar.utils.exception.ApiException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;
    private final ModelMapper mapper;

    public ClassroomServiceImpl(UserRepository userRepository, ClassroomRepository classroomRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
        this.mapper = mapper;
    }

    @Override
    public String createClassroom(ClassroomCreateDto classroomCreateDto) {
        if(classroomRepository.existsByName(classroomCreateDto.getName())) {
            throw new ApiException("Classroom already exist", HttpStatus.BAD_REQUEST);
        }

        Classroom classroom = mapper.map(classroomCreateDto, Classroom.class);

        classroomRepository.save(classroom);

        return "Classroom successfully created";
    }

    @Override
    public String updateUserClassroom(UserClassroomDto userClassroomDto) {
        if(userRepository.findByEmail(userClassroomDto.getEmail()).isEmpty()) {
            throw new ApiException("User not found", HttpStatus.NOT_FOUND);
        }

        if(classroomRepository.findByName(userClassroomDto.getClassroomName()) == null) {
            throw new ApiException("Classroom not found", HttpStatus.NOT_FOUND);
        }

        User user = userRepository.findByEmail(userClassroomDto.getEmail()).get();
        user.setClassroom(classroomRepository.findByName(userClassroomDto.getClassroomName()));

        userRepository.save(user);

        return "User classroom updated";
    }

    @Override
    public List<StudentsInClassroomDto> getStudentsInClassroom(String classroomName, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).get();
        Classroom classroom = classroomRepository.findByName(classroomName);

        if(classroom == null) {
            throw new ApiException("Classroom not found", HttpStatus.NOT_FOUND);
        }

        if(user.getRole().getName().equals(RoleConstants.ROLE_STUDENT) && !user.getClassroom().equals(classroom)) {
            throw new ApiException("You are not allowed to view this classroom", HttpStatus.FORBIDDEN);
        }


        List<User> students = userRepository.findByClassroom(classroom);

        return students.stream()
                .map(student -> mapper.map(student, StudentsInClassroomDto.class))
                .collect(Collectors.toList());
    }
}
