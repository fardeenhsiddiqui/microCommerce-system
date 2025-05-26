package com.userService.controller;

import com.userService.entity.Users;
import com.userService.model.ApiResponse;
import com.userService.model.users.CreateUserDTO;
import com.userService.model.users.UserResponseDTO;
import com.userService.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/")
public class UsersController {

    @Autowired
    UsersService usersService;

    @PostMapping("addUser")
    public ResponseEntity<ApiResponse<UserResponseDTO>> addUser(@Valid @RequestBody CreateUserDTO dto){
        try{
            System.out.println("1........1");
            Users user = usersService.createUser(dto);
            UserResponseDTO response = mapToResponseDTO(user);
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ApiResponse<>(true, response,null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, null, e.getMessage()));
        }

    }

    private UserResponseDTO mapToResponseDTO(Users user){
        return new UserResponseDTO(user);
    }
}
