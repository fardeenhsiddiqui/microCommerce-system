package com.userService.user;

import com.userService.common.response.ApiResponse;
import com.userService.user.dto.UpdateProfileRequest;
import com.userService.user.dto.UserResponseDTO;
import com.userService.user.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final IUserService userService;

    public UsersController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getCurrentUser() {
        UserResponseDTO response = userService.getCurrentUser();
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        UserResponseDTO response = userService.updateProfile(request);
        return ResponseEntity.ok(new ApiResponse<>(true, response, null));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<String>> deactivateCurrentUser() {
        userService.deactivateCurrentUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "User account deactivated", null));
    }
}
