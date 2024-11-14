package com.dev.reCode.controller.mvc

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {
    @GetMapping("/home")
    fun home(model: Model): String {
        return "index"
    }

}