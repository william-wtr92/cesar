package com.example.cesar.repository;

import com.example.cesar.entity.Grade;
import com.example.cesar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudent(User student);
}
