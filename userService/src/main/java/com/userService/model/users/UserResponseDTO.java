package com.userService.model.users;

import com.userService.entity.Users;

public class UserResponseDTO {

    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String email;

    public UserResponseDTO(Users user) {
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
