package com.dev.reCode.network.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping


@Controller
class WorkspaceController {
    @GetMapping("/workspace")
    fun workspace(model: Model): String {
        return "workspace"
    }
}