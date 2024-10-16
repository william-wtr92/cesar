package com.example.cesar.service.impl;

import com.example.cesar.entity.*;
import com.example.cesar.repository.*;
import com.example.cesar.service.AttendanceService;
import com.example.cesar.utils.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;
    private final DelayRepository delayRepository;
    private final AbsenceRepository absenceRepository;

    public AttendanceServiceImpl(UserRepository userRepository, CourseRepository courseRepository, AttendanceRepository attendanceRepository, DelayRepository delayRepository, AbsenceRepository absenceRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.attendanceRepository = attendanceRepository;
        this.delayRepository = delayRepository;
        this.absenceRepository = absenceRepository;
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

    @Override
    public String putPresent(Long attendanceId, UserDetails userDetails) {
        Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow(() -> new ApiException("Attendance not found", HttpStatus.NOT_FOUND));

        if(!attendance.getUser().getEmail().equals(userDetails.getUsername())) {
            throw new ApiException("You are not the student of this attendance", HttpStatus.UNAUTHORIZED);
        }

        Course course = attendance.getCourse();

        if(attendance.getEndDate().before(new Date())) {
            if(course.getEndDate().before(new Date())) {
                throw new ApiException("Course is finished", HttpStatus.BAD_REQUEST);
            }

            Delay delay = new Delay();
            delay.setArrivalTime(new Date());
            delay.setJustified(false);
            delay.setUser(attendance.getUser());
            delay.setCourse(course);

            delayRepository.save(delay);

           return "Attendance marked as delayed";
        }

        attendance.setPresent(true);
        attendanceRepository.save(attendance);

        return "Attendance marked as present";
    }

    @Override
    public Attendance getAttendanceById(Long attendanceId, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        return attendanceRepository.findByIdAndUserIdWithoutJoin(attendanceId, user.getId())
                .orElseThrow(() -> new ApiException("You are not the student of this attendance", HttpStatus.UNAUTHORIZED));
    }

    // This method is scheduled to run every minute
    @Scheduled(fixedRate = 60000)
    public void markAbsentStudents() {
        List<Attendance> attendances = attendanceRepository.findAll();


        for(Attendance attendance : attendances) {
            Course attendanceCourse = attendance.getCourse();

            if(absenceRepository.existsByUserIdAndCourseId(attendance.getUser().getId(), attendanceCourse.getId())) {
                return;
            }

            if(!attendance.isPresent() && attendanceCourse.getEndDate().before(new Date())) {
                Absence absence = new Absence();
                absence.setJustified(false);
                absence.setUser(attendance.getUser());
                absence.setCourse(attendanceCourse);

                absenceRepository.save(absence);
            }
        }
    }
}
