package com.dev.reCode.controller.mvc;

import com.dev.reCode.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.naming.AuthenticationException;

@Controller
class LoginController {
    @GetMapping("/login")
    private String login( Model model){
        return "login";
    }
}