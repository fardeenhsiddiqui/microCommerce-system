package com.userService.service;

import com.userService.entity.Users;
import com.userService.model.users.CreateUserDTO;
import com.userService.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsersService {

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
        user.setPassword(dto.getPassword());

        user.setCreatedBy("ADMIN");
        user.setCreatedDate(LocalDateTime.now());

        return userRepository.save(user);
    }

}
