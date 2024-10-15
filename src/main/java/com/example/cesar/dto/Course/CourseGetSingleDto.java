package com.example.cesar.dto.Course;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseGetSingleDto {
    @NotEmpty(message = "Course name is required")
    private String courseName;

    @NotEmpty(message = "Classroom name is required")
    private String classroomName;

    @NotNull(message = "Start date is required")
    private Date startDate;
}
