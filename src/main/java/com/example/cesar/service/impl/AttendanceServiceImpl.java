package com.example.cesar.service.impl;

import com.example.cesar.entity.Attendance;
import com.example.cesar.entity.Course;
import com.example.cesar.entity.User;
import com.example.cesar.repository.AttendanceRepository;
import com.example.cesar.repository.CourseRepository;
import com.example.cesar.service.AttendanceService;
import com.example.cesar.utils.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceServiceImpl(CourseRepository courseRepository, AttendanceRepository attendanceRepository) {
        this.courseRepository = courseRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public String startAttendance(Long courseId, UserDetails userDetails) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ApiException("Course not found", HttpStatus.NOT_FOUND));

        if(!course.getTeacher().getEmail().equals(userDetails.getUsername())) {
            throw new ApiException("You are not the teacher of this course", HttpStatus.UNAUTHORIZED);
        }

        if(attendanceRepository.existsByCourseIdAndEndDateAfter(courseId, new Date())) {
            throw new ApiException("Attendance already started", HttpStatus.BAD_REQUEST);
        }

        List<User> students = course.getClassroom().getStudents();

        for(User student : students) {
            Date endDate = new Date(System.currentTimeMillis() + 300000);

            Attendance attendance = new Attendance();
            attendance.setStartDate(new Date());
            attendance.setEndDate(endDate);
            attendance.setPresent(false);
            attendance.setUser(student);
            attendance.setCourse(course);

            attendanceRepository.save(attendance);
        }

        return "Attendance started";
    }
}
