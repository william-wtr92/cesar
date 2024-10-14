package com.example.cesar.service.impl;

import com.example.cesar.dto.UserLoginDto;
import com.example.cesar.dto.UserRegisterDto;
import com.example.cesar.entity.Classroom;
import com.example.cesar.entity.Role;
import com.example.cesar.entity.User;
import com.example.cesar.repository.ClassroomRepository;
import com.example.cesar.repository.RoleRepository;
import com.example.cesar.repository.UserRepository;
import com.example.cesar.security.JwtTokenProvider;
import com.example.cesar.service.UserService;
import com.example.cesar.utils.exception.ApiException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClassroomRepository classroomRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ClassroomRepository classroomRepository, ModelMapper mapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.classroomRepository = classroomRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String register(UserRegisterDto userDto) {
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new ApiException("User already exists", HttpStatus.BAD_REQUEST);
        }

        Classroom classroom = classroomRepository.findByName(userDto.getClassroomName());

        if(classroom == null) {
            throw new ApiException("Classroom not found", HttpStatus.NOT_FOUND);
        }

        User user = mapper.map(userDto, User.class);
        user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));

        Role defaultRole = roleRepository.findByName("student");
        if(defaultRole == null) {
            throw new ApiException("Role not found", HttpStatus.NOT_FOUND);
        }
        user.setRole(defaultRole);
        user.setClassroom(classroom);

        userRepository.save(user);

        return "User registered successfully!";
    }

    @Override
    public String login(UserLoginDto userDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDto.getEmail(), userDto.getPassword()
        ));

        // Save into the Spring Security Context the auth of the user grab below
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create a token for the user
        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
