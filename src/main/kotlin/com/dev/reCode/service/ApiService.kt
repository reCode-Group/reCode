package com.dev.reCode.service

import com.dev.reCode.Vba2JsConverter
import com.dev.reCode.converter.TypeConverter
import com.dev.reCode.dto.ConverterResponse
import org.springframework.stereotype.Service

@Service
class ApiService {
    fun convert(inputData: String): ConverterResponse {
        return try {
            ConverterResponse(
                data = Vba2JsConverter().vbaToJs(inputData),
                status = CODE_SUCCESS
            )
        } catch (e: Exception) {
            ConverterResponse(
                data = e.message,
                status = CODE_ERROR
            )
        }
    }

    private companion object {
        const val CODE_ERROR = "500"
        const val CODE_SUCCESS = "200"
    }
}