package com.example.cesar.controller;

import com.example.cesar.dto.Classroom.ClassroomCreateDto;
import com.example.cesar.dto.Classroom.StudentsInClassroomDto;
import com.example.cesar.dto.User.UserClassroomDto;
import com.example.cesar.service.ClassroomService;
import com.example.cesar.utils.constants.RoleConstants;
import com.example.cesar.utils.response.ApiResponse;
import com.example.cesar.utils.response.StudentsInClassroomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms/")
@Tag(name = "Classrooms API", description = "Classroom management")
@SecurityRequirement(name = "Bearer Authentication")
public class ClassroomController {
    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @Operation(summary = "Create a new classroom")
    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_ADMIN +"')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createClassroom(@Valid @RequestBody ClassroomCreateDto classroomCreateDto) {
        String response = classroomService.createClassroom(classroomCreateDto);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Update a classroom")
    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_ADMIN +"')")
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserClassroom(@Valid @RequestBody UserClassroomDto userClassroomDto) {
        String response = classroomService.updateUserClassroom(userClassroomDto);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get all students in a classroom")
    @GetMapping("/students")
    public ResponseEntity<ApiResponse> getStudentsInClassroom(@RequestParam String classroomName, @AuthenticationPrincipal UserDetails userDetails) {
        List<StudentsInClassroomDto> students = classroomService.getStudentsInClassroom(classroomName, userDetails);
        StudentsInClassroomResponse response = new StudentsInClassroomResponse("Students successfully fetched", HttpStatus.OK.value(), true, students);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
