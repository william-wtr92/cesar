package com.example.cesar.repository;

import com.example.cesar.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
}
