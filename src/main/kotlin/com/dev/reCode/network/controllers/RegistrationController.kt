package com.dev.reCode.network.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RegistrationController {
    @GetMapping("/registration")
    fun contactsMain(): String {
        return "registration"
    }
}