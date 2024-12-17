package com.dev.reCode.controller.mvc;

import com.dev.reCode.models.User;
import com.dev.reCode.service.AuthenticationService;
import com.dev.reCode.service.UserService;
import com.dev.reCode.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@AllArgsConstructor
@Controller
public class RegistrationController {
    private final UserValidator userValidator;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    @GetMapping("/registration")
    private String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    private String registrationPost(@ModelAttribute("user") User user, BindingResult bindingResult , HttpServletRequest request, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        System.out.println(Arrays.toString(bindingResult.getSuppressedFields()));
        String email = user.getEmail();
        String password = user.getPassword();
        if (!userService.save(user).getStatusCode().is2xxSuccessful()) {
            return "registration";
        }
        authenticationService.autoLogin(request,email, password);
        return "redirect:/";
    }
}
