package com.example.cesar.service;

import com.example.cesar.dto.SendEmailDto;
import com.example.cesar.dto.UserLoginDto;
import com.example.cesar.dto.UserRegisterDto;

public interface UserService {
    String register(UserRegisterDto userDto);
    String login(UserLoginDto userDto);
    String sendEmailToUsers(SendEmailDto sendEmailDto);
}
