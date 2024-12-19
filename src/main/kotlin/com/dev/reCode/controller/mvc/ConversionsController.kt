package com.dev.reCode.controller.mvc
import com.dev.reCode.dto.ConversionDto
import com.dev.reCode.repository.ConversionRepository
import com.dev.reCode.repository.UserRepository
import com.dev.reCode.service.UserService
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ConversionsController(
) {
    @GetMapping("/conversions")
    fun conversions(): String {
        return "conversions"
    }
}
