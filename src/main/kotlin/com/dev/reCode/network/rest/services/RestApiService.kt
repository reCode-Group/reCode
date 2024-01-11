package com.dev.reCode.network.rest.services

import com.dev.reCode.Vba2JsConverter
import com.dev.reCode.network.apiModels.ConverterResponse
import org.springframework.stereotype.Service

@Service
class RestApiService {

    fun convert(inputData: String): ConverterResponse {
        println("Полученные данные: $inputData")
        // Верните представление или перенаправление, как вам нужно
        val response = ConverterResponse()
        try {
            val converter = Vba2JsConverter()
            response.data = converter.vbsToJs(inputData)
            response.status = "200"
        } catch (e: Exception) {
            response.status = "500"
        }
        return response
    }

}