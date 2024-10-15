package com.example.cesar.utils.response;

import com.example.cesar.entity.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllStudentGradesResponse extends ApiResponse {
    private List<Grade> grades;

    public AllStudentGradesResponse(String message, int status, boolean success, List<Grade> grades) {
        super(message, status, success);
        this.grades = grades;
    }
}
