package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("select i from User i where i.username = ?1 and i.encodedPassword = ?2")
    Optional<User> findByUsernameAndEncodedPassword(String username, String password);

    @Transactional
    @Modifying
    @Query("update User i set i.encodedPassword = ?1 where i.username = ?2")
    int updateEncodedPasswordByUsername(String encodedPassword, String username);
}