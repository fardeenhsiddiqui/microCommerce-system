package com.userService.user;

import com.userService.user.dto.UserResponseDTO;
import com.userService.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
/*
profile
update profile
get user
deactivate user
 */
@RestController
@RequestMapping("/api/user/")
public class UsersController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);
//    private final IUserService userService;
//
//    public UsersController(IUserService usersService) {
//        this.userService = usersService;
//    }

    private UserResponseDTO mapToResponseDTO(User user){
        return new UserResponseDTO(user);
    }
}
