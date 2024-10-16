package com.example.cesar.dto.Classroom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsInClassroomDto {
    private String firstName;
    private String lastName;
    private String email;
    private Date birthday;
}
