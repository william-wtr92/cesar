package com.example.cesar.service;

import com.example.cesar.dto.GradeDto;
import com.example.cesar.entity.Grade;
import org.springframework.security.core.userdetails.UserDetails;

public interface GradeService {
    Grade addGrade(GradeDto gradeDto, UserDetails userDetails);
}
