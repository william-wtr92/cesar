package com.example.cesar.service;

import com.example.cesar.dto.Classroom.AllClassroomsDto;
import com.example.cesar.dto.Classroom.ClassroomCreateDto;
import com.example.cesar.dto.Classroom.StudentsInClassroomDto;
import com.example.cesar.dto.User.UserClassroomDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ClassroomService {
    String createClassroom(ClassroomCreateDto classroomCreateDto);
    String updateUserClassroom(UserClassroomDto userClassroomDto);
    List<StudentsInClassroomDto> getStudentsInClassroom(String classroomName, UserDetails userDetails);
    List<AllClassroomsDto> getAllClassrooms();
}
