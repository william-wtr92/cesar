package com.example.cesar.controller;

import com.example.cesar.dto.GradeDto;
import com.example.cesar.service.GradeService;
import com.example.cesar.utils.constants.RoleConstants;
import com.example.cesar.utils.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

}
