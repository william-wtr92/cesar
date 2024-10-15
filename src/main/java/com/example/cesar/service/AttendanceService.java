package com.example.cesar.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface AttendanceService {
    String startAttendance(Long courseId, UserDetails userDetails);
}
