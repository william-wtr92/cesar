package com.example.cesar.utils.response;

import com.example.cesar.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllCourseResponse extends ApiResponse {
    private List<Course> courses;

    public AllCourseResponse(String message, int status, boolean success, List<Course> courses) {
        super(message, status, success);
        this.courses = courses;
    }
}
