package com.example.cesar.utils.response;

import com.example.cesar.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse extends ApiResponse {
    private Attendance attendance;

    public AttendanceResponse(String message, int status, boolean success, Attendance attendance) {
        super(message, status, success);
        this.attendance = attendance;
    }
}
