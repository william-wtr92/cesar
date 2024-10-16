package com.example.cesar.controller;

import com.example.cesar.dto.SendEmailDto;
import com.example.cesar.dto.User.UserLoginDto;
import com.example.cesar.dto.User.UserRegisterDto;
import com.example.cesar.service.UserService;
import com.example.cesar.utils.constants.RoleConstants;
import com.example.cesar.utils.response.ApiResponse;
import com.example.cesar.utils.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/")
@Tag(name = "Users API", description = "User management")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        String response = userService.register(userRegisterDto);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.CREATED.value(), true);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        String token = userService.login(userLoginDto);
        LoginResponse loginResponse = new LoginResponse("Logged in!", HttpStatus.OK.value(), true, token);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Send email to users")
    @PreAuthorize("hasAnyRole('"+ RoleConstants.ROLE_ADMIN +"', '"+RoleConstants.ROLE_TEACHER+"')")
    @PostMapping("/send-email")
    public ResponseEntity<ApiResponse> sendEmail(@RequestBody SendEmailDto sendEmailDto) {
        String response = userService.sendEmailToUsers(sendEmailDto);
        ApiResponse apiResponse = new ApiResponse(response, HttpStatus.OK.value(), true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Download file")
    @GetMapping("/{courseId}/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String fileName, @AuthenticationPrincipal UserDetails userDetails, @PathVariable Long courseId) {
        byte[] file = userService.downloadFile(fileName, courseId, userDetails);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .body(file);
    }

}
