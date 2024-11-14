package com.dev.reCode.controller.mvc;

import com.dev.reCode.models.User;
import com.dev.reCode.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    private String registration() {
        return "registration";
    }

    @PostMapping("/register")
    private String registrationPost(@ModelAttribute User user, @RequestParam("remember-me") boolean rememberMe, HttpServletRequest request) {
//        userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
        System.out.println(rememberMe);
        String email = user.getEmail();
        String password = user.getPassword();
        try {
            if (userService.register(user).getStatusCode().is2xxSuccessful()) {
                request.login(email, password);
                return "redirect:/";
            } else
                return "registration";
        }catch (Exception e) {
            return "registration";
        }

    }

}
