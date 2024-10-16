package com.example.cesar.dto.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllCoursesByClassroomDto {

    private Long id;
    private String name;
    private String startDate;
    private String endDate;
    private List<String> urlFiles;

}
