package com.dev.reCode.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    public void autoLogin(HttpServletRequest request, String email, String password) {
        try {
            request.login(email, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
