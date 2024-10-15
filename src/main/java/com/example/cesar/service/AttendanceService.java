package com.example.cesar.service;

import com.example.cesar.entity.Attendance;
import org.springframework.security.core.userdetails.UserDetails;

public interface AttendanceService {
    String startAttendance(Long courseId, UserDetails userDetails);
    Attendance getAttendanceById(Long attendanceId, UserDetails userDetails);
    String putPresent(Long attendanceId, UserDetails userDetails);
}
