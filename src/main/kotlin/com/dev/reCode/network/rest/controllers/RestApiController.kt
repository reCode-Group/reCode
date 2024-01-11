package com.dev.reCode.network.rest.controllers

import com.dev.reCode.Vba2JsConverter
import com.dev.reCode.network.apiModels.ConverterResponse
import com.dev.reCode.network.rest.services.RestApiService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api")
class RestApiController {
    val restApiService = RestApiService()
    @PostMapping("/convert")
    fun handleSubmit(@RequestParam inputData: String): ConverterResponse {
        return restApiService.convert(inputData)
    }
}