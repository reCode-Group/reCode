package com.dev.reCode.network.rest.services

import com.dev.reCode.Vba2JsConverter
import com.dev.reCode.network.apiModels.ConverterResponse
import org.springframework.stereotype.Service

@Service
class RestApiService {
    fun convert(inputData: String): ConverterResponse {
        println("Полученные данные: $inputData")
        return try {
            ConverterResponse(
                data = Vba2JsConverter().vbsToJs(inputData),
                status = CODE_SUCCESS
            )
        } catch (e: Exception) {
            ConverterResponse(status = CODE_ERROR)
        }
    }

    private companion object {
        const val CODE_ERROR = "500"
        const val CODE_SUCCESS = "200"
    }
}