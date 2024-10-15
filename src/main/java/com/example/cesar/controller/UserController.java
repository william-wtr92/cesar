package com.example.cesar.controller;

import com.example.cesar.dto.SendEmailDto;
import com.example.cesar.dto.UserLoginDto;
import com.example.cesar.dto.UserRegisterDto;
import com.example.cesar.service.UserService;
import com.example.cesar.utils.constants.RoleConstants;
import com.example.cesar.utils.response.ApiResponse;
import com.example.cesar.utils.response.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.CREATED.value(), true);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        String token = userService.login(userLoginDto);
        LoginResponse loginResponse = new LoginResponse("Logged in!", HttpStatus.OK.value(), true, token);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('"+ RoleConstants.ROLE_ADMIN +"', '"+RoleConstants.ROLE_TEACHER+"')")
    @PostMapping("/send-email")
    public ResponseEntity<ApiResponse> sendEmail(@RequestBody SendEmailDto sendEmailDto) {
        String response = userService.sendEmailToUsers(sendEmailDto);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
