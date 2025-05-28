package com.userService.service;

import com.userService.entity.Users;
import com.userService.model.users.CreateUserDTO;
import com.userService.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsersService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Users createUser(CreateUserDTO dto){
        Users user = new Users();
        user.setUserName(dto.getUserName());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRole("ADMIN");
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setCreatedBy("ADMIN");
        user.setCreatedDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    public void updateUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
    }

}
