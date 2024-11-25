package com.dev.reCode.service;

import com.dev.reCode.models.User;
import com.dev.reCode.repository.RoleRepository;
import com.dev.reCode.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public ResponseEntity<?> save(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoles(Set.of(roleRepository.findByRoleName("ROLE_USER")));
        return ResponseEntity.ok().body(userRepository.save(user));
    }
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }
}

