package com.dev.reCode.network.dto

import lombok.AllArgsConstructor
import lombok.Data
import lombok.Getter
import lombok.Setter

@AllArgsConstructor
class ConverterResponse (
    val status: String? = null,
    val data: String? = null
)