package com.example.cesar.service.impl;

import com.example.cesar.dto.GradeDto;
import com.example.cesar.entity.Classroom;
import com.example.cesar.entity.Grade;
import com.example.cesar.entity.User;
import com.example.cesar.repository.GradeRepository;
import com.example.cesar.repository.UserRepository;
import com.example.cesar.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, UserRepository userRepository) {
        this.gradeRepository = gradeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Grade addGrade(GradeDto gradeDto, User teacher) {
        Optional<User> studentOpt = userRepository.findById(gradeDto.getStudentId());
        if (studentOpt.isEmpty()) {
            throw new IllegalArgumentException("L'étudiant spécifié n'existe pas");
        }

        Optional<Classroom> classroomOpt = classroomRepository.findById(gradeDto.getClassroomId());
        if (classroomOpt.isEmpty()) {
            throw new IllegalArgumentException("La classe spécifiée n'existe pas");
        }

        Grade grade = new Grade();
        grade.setGrade(gradeDto.getGrade());
        grade.setSubject(gradeDto.getSubject());
        grade.setTeacher(teacher);
        grade.setStudent(studentOpt.get());
        grade.setClassroom(classroomOpt.get());  // Assigner la classe

        return gradeRepository.save(grade);
    }

    @Override
    public Grade updateGrade(Long id, GradeDto gradeDto, User teacher) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La note spécifiée n'existe pas"));

        grade.setGrade(gradeDto.getGrade());
        grade.setSubject(gradeDto.getSubject());

        return gradeRepository.save(grade);
    }

    @Override
    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public List<Grade> getGradesForStudent(User student) {
        return gradeRepository.findByStudent(student);
    }
}
