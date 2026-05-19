package com.userService.user.repository;

import com.userService.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserName(String username);

    boolean existsByEmail(String emailId);

    boolean existsByUserName(String userName);

}
