package com.dev.reCode.service

import com.dev.reCode.Vba2JsConverter
import com.dev.reCode.converter.TypeConverter
import com.dev.reCode.dto.ConverterResponse
import org.aspectj.apache.bcel.classfile.Unknown
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Timeouts
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
                data = "",
                status = CODE_ERROR
            )
        }
    }

    private companion object {
        const val CODE_SUCCESS = "0"
        const val CODE_ERROR = "1"
        const val TIMEOUT_ERROR = "2"
        const val INTERNAL_ERROR = "3"
        const val UNKNOWN_ERROR = "4"
    }
}