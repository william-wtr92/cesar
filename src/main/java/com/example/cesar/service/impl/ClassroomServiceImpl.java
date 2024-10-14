package com.example.cesar.service.impl;

import com.example.cesar.dto.UserClassroomDto;
import com.example.cesar.entity.User;
import com.example.cesar.repository.ClassroomRepository;
import com.example.cesar.repository.UserRepository;
import com.example.cesar.service.ClassroomService;
import com.example.cesar.utils.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    public ClassroomServiceImpl(UserRepository userRepository, ClassroomRepository classroomRepository) {
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
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
}
