package com.example.cesar.dto.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateDto {
    private String name;

    private Date startDate;

    private Date endDate;

    private String classroomName;

    private String teacherEmail;
}
