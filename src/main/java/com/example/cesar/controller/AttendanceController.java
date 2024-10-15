package com.example.cesar.controller;

import com.example.cesar.entity.Attendance;
import com.example.cesar.service.AttendanceService;
import com.example.cesar.utils.constants.RoleConstants;
import com.example.cesar.utils.response.ApiResponse;
import com.example.cesar.utils.response.AttendanceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendances/")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController (AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PreAuthorize("hasAnyRole('"+ RoleConstants.ROLE_ADMIN +"', '"+ RoleConstants.ROLE_TEACHER +"')")
    @GetMapping("/{courseId}/start")
    public ResponseEntity<ApiResponse> startAttendance(@PathVariable Long courseId, @AuthenticationPrincipal UserDetails userDetails) {
        String response = attendanceService.startAttendance(courseId, userDetails);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('"+ RoleConstants.ROLE_STUDENT +"')")
    @GetMapping("/{attendanceId}/present")
    public ResponseEntity<ApiResponse> putPresent(@PathVariable Long attendanceId, @AuthenticationPrincipal UserDetails userDetails) {
        String response = attendanceService.putPresent(attendanceId, userDetails);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);

        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('"+ RoleConstants.ROLE_STUDENT +"')")
    @GetMapping("/{attendanceId}")
    public ResponseEntity<ApiResponse> getAttendanceById(@PathVariable Long attendanceId, @AuthenticationPrincipal UserDetails userDetails) {
        Attendance attendance = attendanceService.getAttendanceById(attendanceId, userDetails);
        AttendanceResponse apiResponse = new AttendanceResponse ("Attendance successfully fetched", HttpStatus.OK.value(), true, attendance);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
