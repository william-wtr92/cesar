package com.example.cesar.controller;

import com.example.cesar.dto.Grade.GradeDto;
import com.example.cesar.dto.Grade.UpdateGradeDto;
import com.example.cesar.entity.Grade;
import com.example.cesar.service.GradeService;
import com.example.cesar.utils.constants.RoleConstants;
import com.example.cesar.utils.response.AllStudentGradesResponse;
import com.example.cesar.utils.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades/")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_TEACHER +"')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addGrade(@Valid @RequestBody GradeDto gradeDto, @AuthenticationPrincipal UserDetails userDetails) {
        String response = gradeService.addGrade(gradeDto, userDetails);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.CREATED.value(), true);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_TEACHER +"')")
    @PutMapping("/{gradeId}/update")
    public ResponseEntity<ApiResponse> updateGrade(@PathVariable Long gradeId, @Valid @RequestBody UpdateGradeDto updateGradeDto, @AuthenticationPrincipal UserDetails userDetails) {
        String response = gradeService.updateGrade(gradeId, updateGradeDto, userDetails);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('"+ RoleConstants.ROLE_TEACHER +"')")
    @DeleteMapping("/{gradeId}/delete")
    public ResponseEntity<ApiResponse> deleteGrade(@PathVariable Long gradeId, @AuthenticationPrincipal UserDetails userDetails) {
        String response = gradeService.deleteGrade(gradeId, userDetails);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('"+ RoleConstants.ROLE_ADMIN +"', '"+ RoleConstants.ROLE_STUDENT +"')")
    @GetMapping("/student")
    public ResponseEntity<ApiResponse> getGradesByStudent(@RequestParam String studentEmail, @AuthenticationPrincipal UserDetails userDetails) {
        List<Grade> grades = gradeService.getGradesByStudent(studentEmail, userDetails);
        AllStudentGradesResponse apiResponse = new AllStudentGradesResponse("Grades retrieved successfully", HttpStatus.OK.value(), true, grades);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
