package com.example.cesar.service.impl;

import com.example.cesar.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public UserServiceImpl() {
    }

    @Override
    public String getUserName() {
        return "Cesar";
    }
}
