/*
home address
office address
billing address
shipping address
* */

package com.userService.address;

import com.userService.common.entity.BaseEntity;
import com.userService.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name ="address")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fullName;
    private String mobileNumber;

    private String addressLine1;
    private String addressLine2;

    private String city;
    private String state;
    private String country;
    private String zipCode;

    private Boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
