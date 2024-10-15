package com.example.cesar.controller;

import com.example.cesar.dto.GradeDto;
import com.example.cesar.entity.User;
import com.example.cesar.entity.Grade;
import java.util.stream.Collectors;
import com.example.cesar.service.GradeService;
import com.example.cesar.utils.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades/")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addGrade(@Valid @RequestBody GradeDto gradeDto, @AuthenticationPrincipal User teacher) {
        gradeService.addGrade(gradeDto, teacher);
        ApiResponse apiResponse = new ApiResponse("Grade added successfully", HttpStatus.CREATED.value(), true);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateGrade(@PathVariable Long id, @Valid @RequestBody GradeDto gradeDto, @AuthenticationPrincipal User teacher) {
        gradeService.updateGrade(id, gradeDto, teacher);
        ApiResponse apiResponse = new ApiResponse("Grade updated successfully", HttpStatus.OK.value(), true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        ApiResponse apiResponse = new ApiResponse("Grade deleted successfully", HttpStatus.OK.value(), true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<GradeDto>> getGradesForStudent(@PathVariable Long id, @AuthenticationPrincipal User student) {
        List<Grade> grades = gradeService.getGradesForStudent(student);

        List<GradeDto> gradeDtos = grades.stream().map(grade -> new GradeDto(
            grade.getGrade(),
            grade.getSubject(),
            grade.getStudent().getId(),
            grade.getClassroom().getId()
        )).collect(Collectors.toList());

        return new ResponseEntity<>(gradeDtos, HttpStatus.OK);
    }

}
