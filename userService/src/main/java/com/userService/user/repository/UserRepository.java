package com.userService.user.repository;

import com.userService.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUserName(String username);

    Optional<User> findByEmail(String emailId);

    boolean existsByEmail(String emailId);

    boolean existsByUserName(String userName);

}
