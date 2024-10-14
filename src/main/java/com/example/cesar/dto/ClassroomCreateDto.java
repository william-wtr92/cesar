package com.example.cesar.dto;

import com.example.cesar.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomCreateDto {
    @NotEmpty(message = "Name is required")
    private String name;
}
