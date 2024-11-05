package com.dev.reCode.network.nonRest.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ContactsController {
    @GetMapping("/contacts")
    fun contactsMain(): String {
        return "contacts"
    }
}