package com.example.cesar.dto.Grade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGradeDto {

    @NotNull(message = "Grade is required")
    @Min(value = 0, message = "Grade must be between 0 and 20")
    @Max(value = 20, message = "Grade must be between 0 and 20")
    private int grade;

    @NotNull(message = "Student is required")
    private String studentEmail;

    @NotEmpty(message = "Review is required")
    private String review;
}
