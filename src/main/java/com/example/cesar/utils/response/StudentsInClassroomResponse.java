package com.example.cesar.utils.response;

import com.example.cesar.dto.Classroom.StudentsInClassroomDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentsInClassroomResponse extends ApiResponse{
    private List<StudentsInClassroomDto> students;

    public StudentsInClassroomResponse(String message, int status, boolean success, List<StudentsInClassroomDto> students) {
        super(message, status, success);
        this.students = students;
    }
}
