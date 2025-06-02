package com.userService.controller;

import com.userService.entity.Users;
import com.userService.model.ApiResponse;
import com.userService.model.JwtResponseDTO;
import com.userService.model.users.CreateUserDTO;
import com.userService.model.users.LoginRequestDTO;
import com.userService.model.users.UserResponseDTO;
import com.userService.service.UsersService;
import com.userService.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UsersService usersService;

    @Autowired
    public AuthController(JwtUtil jwtUtil, UsersService usersService) {
        this.jwtUtil = jwtUtil;
        this.usersService = usersService;
    }
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserDTO dto){
        try{
            Users user = usersService.createUser(dto);
            UserResponseDTO response = mapToResponseDTO(user);
            return ResponseEntity.status(HttpStatus.OK).
                    body(new ApiResponse<>(true, response,null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, null, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        System.out.println("1........ " + dto.password());
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.userName(), dto.password())
        );
        System.out.println("1...... 1 " + auth.getCredentials());
        UserDetails user = (UserDetails) auth.getPrincipal();
        System.out.println("1...... 2 " + user);
        String token = jwtUtil.generateToken(user);
        System.out.println("1...... 3 " + token);
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

    private UserResponseDTO mapToResponseDTO(Users user){
        return new UserResponseDTO(user);
    }
}
