package com.userService.emailVerificationToken.repo;

import com.userService.emailVerificationToken.EmailVerificationToken;
import com.userService.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, UUID> {

    Optional<EmailVerificationToken> findByToken(String Token);

    void deleteByUser(User user);
}
