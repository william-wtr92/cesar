package com.example.cesar.service;

import com.example.cesar.dto.GradeDto;
import com.example.cesar.entity.Grade;
import com.example.cesar.entity.User;

import java.util.List;

public interface GradeService {
    Grade addGrade(GradeDto gradeDto, User teacher);
    Grade updateGrade(Long id, GradeDto gradeDto, User teacher);
    void deleteGrade(Long id);
    List<Grade> getGradesForStudent(User student);
}
