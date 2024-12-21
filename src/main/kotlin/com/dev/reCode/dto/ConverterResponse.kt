package com.dev.reCode.dto

import lombok.AllArgsConstructor
import lombok.Data
import lombok.Getter
import lombok.Setter

@AllArgsConstructor
class ConverterResponse (
    //TODO: добавить айди созданного макроса
    val status: String? = null,
    val data: String? = null
)
