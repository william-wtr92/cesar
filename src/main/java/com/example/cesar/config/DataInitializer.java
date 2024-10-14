package com.example.cesar.config;

import com.example.cesar.entity.Role;
import com.example.cesar.entity.User;
import com.example.cesar.repository.RoleRepository;
import com.example.cesar.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role roleAdmin = new Role();
                roleAdmin.setName("admin");

                Role roleUser = new Role();
                roleUser.setName("user");

                Role roleModerator = new Role();
                roleModerator.setName("teacher");

                roleRepository.save(roleAdmin);
                roleRepository.save(roleUser);
                roleRepository.save(roleModerator);
            }

            if (userRepository.count() == 0) {
                Role adminRole = roleRepository.findByName("admin");
                Role teacherRole = roleRepository.findByName("teacher");
                Role studentRole = roleRepository.findByName("student");

                User admin = new User();
                admin.setFirstname("Admin");
                admin.setLastname("User");
                admin.setEmail("admin@example.com");
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                admin.setBirthday(new Date());
                admin.setPhone("123456789");
                admin.setRole(adminRole);
                userRepository.save(admin);

                User teacher = new User();
                teacher.setFirstname("John");
                teacher.setLastname("Doe");
                teacher.setEmail("teacher@example.com");
                teacher.setPasswordHash(passwordEncoder.encode("teacher123"));
                teacher.setBirthday(new Date());
                teacher.setPhone("987654321");
                teacher.setRole(teacherRole);
                userRepository.save(teacher);

                User student = new User();
                student.setFirstname("Jane");
                student.setLastname("Smith");
                student.setEmail("student@example.com");
                student.setPasswordHash(passwordEncoder.encode("student123"));
                student.setBirthday(new Date());
                student.setPhone("456789123");
                student.setRole(studentRole);
                userRepository.save(student);
            }
        };
    }
}
