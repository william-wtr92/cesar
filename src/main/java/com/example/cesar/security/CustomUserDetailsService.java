package com.example.cesar.security;

import com.example.cesar.entity.User;
import com.example.cesar.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // This method is used to load user by username or email from de database
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Search if user exist with mail or username provided
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email)
                );

        // Creation of an instance of GrantedAuthority Class provided by Spring Security
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());

        // Creation of an instance of User Class provided by Spring Security, and we pass user: Email, Password & Role
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(), Collections.singleton(authority));
    }
}
