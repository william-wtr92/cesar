package com.example.cesar.utils.response;

import com.example.cesar.dto.Course.AllCoursesByClassroomDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllCoursesByClassroomResponse extends ApiResponse {
    private List<AllCoursesByClassroomDto> courses;

    public AllCoursesByClassroomResponse(String message, int status, boolean success, List<AllCoursesByClassroomDto> courses) {
        super(message, status, success);
        this.courses = courses;
    }
}
