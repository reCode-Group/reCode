package com.dev.reCode.service;

import com.dev.reCode.models.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private UserService userService;
    public String authenticate(User user, HttpServletRequest request) {
        String email = user.getEmail();
        String password = user.getPassword();
        if (!userService.register(user).getStatusCode().is2xxSuccessful()) {
            return "registration";
        }
        try{
            request.login(email, password);
            return "redirect:/";
        }catch (Exception e){
            return "registration";
        }
    }
}
