package com.example.cesar.service.impl;

import com.example.cesar.dto.SendEmailDto;
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
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClassroomRepository classroomRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    private final JavaMailSender mailSender;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ClassroomRepository classroomRepository, ModelMapper mapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.classroomRepository = classroomRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailSender = mailSender;
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
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
            );

            // Save into the Spring Security Context the auth of the user grab below
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Create a token for the user
            return jwtTokenProvider.generateToken(authentication);
        } catch (AuthenticationException ex) {
            throw new ApiException("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public String sendEmailToUsers(SendEmailDto sendEmailDto) {
        List<String> recipients = sendEmailDto.getRecipients();

        for (String recipient : recipients) {
            if (!userRepository.existsByEmail(recipient)) {
                throw new ApiException("User not found", HttpStatus.NOT_FOUND);
            }

            try {
                sendEmail(sendEmailDto, recipient);
            } catch (MessagingException | IOException e) {
                throw new ApiException("Error sending email", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return "Email sent successfully!";
    }

    private void sendEmail(SendEmailDto sendEmailDto, String recipient) throws MessagingException, IOException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom(sendEmailDto.getSender());
        helper.setTo(recipient);
        helper.setSubject(sendEmailDto.getSubject());
        helper.setText(sendEmailDto.getBody(), true);

        if (sendEmailDto.getAttachments() != null) {
            for (MultipartFile file : sendEmailDto.getAttachments()) {
                helper.addAttachment(file.getOriginalFilename(), file);
            }
        }

        mailSender.send(mimeMessage);
    }

}
