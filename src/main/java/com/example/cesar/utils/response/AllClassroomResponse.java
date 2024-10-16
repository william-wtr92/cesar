package com.example.cesar.utils.response;

import com.example.cesar.dto.Classroom.AllClassroomsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllClassroomResponse extends ApiResponse {
    private List<AllClassroomsDto> classrooms;

    public AllClassroomResponse(String message, int status, boolean success, List<AllClassroomsDto> classrooms) {
        super(message, status, success);
        this.classrooms = classrooms;
    }
}
