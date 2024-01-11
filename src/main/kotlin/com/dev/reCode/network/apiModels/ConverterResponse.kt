package com.dev.reCode.network.apiModels

import lombok.Data
import lombok.Getter
import lombok.Setter

@Data
@Getter
@Setter
class ConverterResponse {
    var status: String? = null
    var data: String? = null
}