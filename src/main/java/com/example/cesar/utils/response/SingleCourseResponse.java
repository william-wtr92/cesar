package com.example.cesar.utils.response;

import com.example.cesar.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SingleCourseResponse extends ApiResponse {
    private Course course;

    public SingleCourseResponse(String message, int status, boolean success, Course course) {
        super(message, status, success);
        this.course = course;
    }
}
