package com.dev.reCode.controller.mvc;

import com.dev.reCode.models.User;
import com.dev.reCode.service.AuthenticationService;
import com.dev.reCode.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class RegistrationController {
    private AuthenticationService authenticationService;
    @GetMapping("/registration")
    private String registration() {
        return "registration";
    }
    @PostMapping("/register")
    private String registrationPost(@ModelAttribute User user, HttpServletRequest request) {
        return authenticationService.authenticate(user,request);
    }
}
