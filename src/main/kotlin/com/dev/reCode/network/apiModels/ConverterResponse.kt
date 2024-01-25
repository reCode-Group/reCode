package com.dev.reCode.network.apiModels

import lombok.Data
import lombok.Getter
import lombok.Setter

@Data
@Getter
@Setter
data class ConverterResponse(
    val status: String? = null,
    val data: String? = null
)