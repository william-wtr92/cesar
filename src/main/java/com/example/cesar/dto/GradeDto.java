package com.example.cesar.dto;

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
public class GradeDto {

    @NotNull(message = "La note est obligatoire")
    @Min(value = 0, message = "La note ne peut pas être inférieure à 0")
    @Max(value = 20, message = "La note ne peut pas être supérieure à 20")
    private int grade;

    @NotEmpty(message = "Le sujet est obligatoire")
    private String subject;

    @NotNull(message = "L'ID de l'étudiant est obligatoire")
    private Long studentId;

    @NotNull(message = "L'ID de la classe est obligatoire")
    private Long classroomId;
}
