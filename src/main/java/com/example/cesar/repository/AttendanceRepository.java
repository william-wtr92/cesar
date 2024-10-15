package com.example.cesar.repository;

import com.example.cesar.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    boolean existsByCourseIdAndEndDateAfter(Long courseId, Date endDate);
}
