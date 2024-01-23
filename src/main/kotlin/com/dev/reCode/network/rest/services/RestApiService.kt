package com.dev.reCode.network.rest.services

import com.dev.reCode.Vba2JsConverter
import com.dev.reCode.converter.TypeConverter
import com.dev.reCode.network.apiModels.ConverterResponse
import org.springframework.stereotype.Service
import kotlin.system.measureTimeMillis

@Service
class RestApiService {
    private val converter = TypeConverter()
    fun convert(inputData: String): ConverterResponse {
        return try {
            // TODO converter.convert(inputData,"vba-js")
            ConverterResponse(
                data = Vba2JsConverter().vbaToJs(inputData),
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