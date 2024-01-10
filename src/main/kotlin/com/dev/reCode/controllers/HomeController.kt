package com.dev.reCode.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {
    @GetMapping("/home")
    fun home(model: Model): String {
        return "index"
    }

    @GetMapping("/templates/contacts")
    fun contacts(model: Model): String {
        return "contacts"
    }

}