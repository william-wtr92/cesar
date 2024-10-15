package com.example.cesar.repository;

import com.example.cesar.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    boolean existsByCourseIdAndEndDateAfter(Long courseId, Date endDate);

    @Query("SELECT a FROM Attendance a WHERE a.id = :attendanceId AND a.user.id = :userId")
    Optional<Attendance> findByIdAndUserIdWithoutJoin(Long attendanceId, Long userId);
}
