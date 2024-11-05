package com.dev.reCode.network.nonRest.controllers;

import com.dev.reCode.models.User;
import com.dev.reCode.network.nonRest.services.UserService;
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
    private String registrationPost(@ModelAttribute User user,  @RequestParam("remember-me") boolean rememberMe) {
//        userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
        System.out.println(rememberMe);
        if (userService.register(user).getStatusCode().is2xxSuccessful())
            return "redirect:/";
        else
            return "registration";
    }

}
