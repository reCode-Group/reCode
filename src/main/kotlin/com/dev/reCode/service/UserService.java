package com.dev.reCode.service;

import com.dev.reCode.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> register(User user);
    User findUserByEmail(String email);
}
