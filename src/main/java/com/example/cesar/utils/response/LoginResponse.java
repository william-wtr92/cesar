package com.example.cesar.utils.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends ApiResponse {
    private String token;

    public LoginResponse(String message, int status, boolean success, String token) {
        super(message, status, success);
        this.token = token;
    }
}
