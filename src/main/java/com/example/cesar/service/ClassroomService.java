package com.example.cesar.service;

import com.example.cesar.dto.ClassroomCreateDto;
import com.example.cesar.dto.UserClassroomDto;

public interface ClassroomService {
    String createClassroom(ClassroomCreateDto classroomCreateDto);
    String updateUserClassroom(UserClassroomDto userClassroomDto);
}
