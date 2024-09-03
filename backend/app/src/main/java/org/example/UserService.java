package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, UserDetailsPasswordService, UserDetailsChecker {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void check(UserDetails toCheck) {
        userRepository.findByUsernameAndEncodedPassword(
                        toCheck.getUsername(),
                        passwordEncoder.encode(toCheck.getPassword())
                )
                .orElseThrow(() -> new InvalidUserException("User with specified credentials not found"));
    }

    public void createUser(User user) {
        try {
            loadUserByUsername(user.getUsername());
            throw new InvalidUserException(String.format("User with username '%s' already exists", user.getUsername()));
        } catch (UsernameNotFoundException e) {
            userRepository.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username '%s' not found", username)));
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        check(user);
        userRepository.updateEncodedPasswordByUsername(newPassword, user.getUsername());
        return loadUserByUsername(user.getUsername());
    }
}
