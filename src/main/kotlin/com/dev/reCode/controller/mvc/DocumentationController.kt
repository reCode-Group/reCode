package com.dev.reCode.controller.mvc

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DocumentationController {
    @GetMapping("/documentation")
    fun documentation(): String {
        return "documentation"
    }
}

