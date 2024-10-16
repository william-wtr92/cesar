package com.example.cesar.service;

import com.example.cesar.dto.Grade.GradeDto;
import com.example.cesar.dto.Grade.UpdateGradeDto;
import com.example.cesar.entity.Grade;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface GradeService {
    String addGrade(GradeDto gradeDto, UserDetails userDetails);
    String updateGrade(Long gradeId, UpdateGradeDto updateGradeDto, UserDetails userDetails);
    String deleteGrade(Long gradeId, UserDetails userDetails);
    List<Grade> getGradesByStudent(String studentEmail, UserDetails userDetails);
}
