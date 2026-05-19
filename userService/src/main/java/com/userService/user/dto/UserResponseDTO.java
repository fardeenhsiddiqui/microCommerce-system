package com.userService.user.dto;

import com.userService.address.dto.AddressResponseDTO;
import com.userService.user.User;

import java.util.List;

public class UserResponseDTO {

    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String role;
//    List<AddressResponseDTO> addressResponseDTOList;

    public UserResponseDTO(User user) {
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
//        this.addressResponseDTOList = user.getAddresses();
        this.role = user.getRole().toString();
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

    public String getRole() {
        return role;
    }
}
