package com.example.cesar.service;

import com.example.cesar.dto.Grade.GradeDto;
import com.example.cesar.dto.Grade.UpdateGradeDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface GradeService {
    String addGrade(GradeDto gradeDto, UserDetails userDetails);
    String updateGrade(Long gradeId, UpdateGradeDto updateGradeDto, UserDetails userDetails);
    String deleteGrade(Long gradeId, UserDetails userDetails);
}
