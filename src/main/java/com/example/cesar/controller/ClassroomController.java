package com.example.cesar.controller;

import com.example.cesar.dto.ClassroomCreateDto;
import com.example.cesar.dto.UserClassroomDto;
import com.example.cesar.entity.Classroom;
import com.example.cesar.service.ClassroomService;
import com.example.cesar.utils.constants.RoleConstants;
import com.example.cesar.utils.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classrooms/")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_ADMIN +"')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createClassroom(@Valid @RequestBody ClassroomCreateDto classroomCreateDto) {
        String response = classroomService.createClassroom(classroomCreateDto);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_ADMIN +"')")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserClassroom(@Valid @RequestBody UserClassroomDto userClassroomDto) {
        String response = classroomService.updateUserClassroom(userClassroomDto);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
