package com.example.cesar.controller;

import com.example.cesar.dto.UserRegisterDto;
import com.example.cesar.service.UserService;
import com.example.cesar.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {

        String response = userService.register(userRegisterDto);

        if(response.equals("User already exists")) {
            ApiResponse apiResponse = new ApiResponse("User already exists", HttpStatus.CONFLICT.value(), false);
            return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
        }

        ApiResponse apiResponse = new ApiResponse("User registered successfully!", HttpStatus.CREATED.value(), true);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
