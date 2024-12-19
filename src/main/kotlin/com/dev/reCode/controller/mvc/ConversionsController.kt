package com.dev.reCode.controller.mvc
import com.dev.reCode.dto.ConversionDto
import com.dev.reCode.repository.ConversionRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ConversionsController(
        private val conversionRepository: ConversionRepository // Инъекция репозитория
) {
    @GetMapping("/conversions")
    fun conversions(
            @RequestParam(defaultValue = "0") page: Int, // Номер страницы
            @RequestParam(defaultValue = "10") size: Int, // Размер страницы
            model: Model
    ): String {
        // Получение текущего пользователя
        val user: User = getCurrentUser()

        // Получение страницы данных
        val pageable: PageRequest = PageRequest.of(page, size)
        val conversionsPage = conversionRepository.findPageableAllByUser(pageable, user)
        val conversionsDto = conversionsPage.content.map { ConversionDto(it) }

        // Добавляем данные в модель
        model.addAttribute("conversions", conversionsDto)
        model.addAttribute("currentPage", page)
        model.addAttribute("totalPages", conversionsPage.totalPages)

        return "conversions"
    }
}
