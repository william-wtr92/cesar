package com.example.cesar.service.impl;

import com.example.cesar.dto.UserRegisterDto;
import com.example.cesar.entity.User;
import com.example.cesar.repository.UserRepository;
import com.example.cesar.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(UserRegisterDto userDto) {
        if(userRepository.existsByEmail(userDto.getEmail())) {
            return "User already exists";
        }

        User user = mapper.map(userDto, User.class);
        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);

        return "User registered successfully!";
    }
}
