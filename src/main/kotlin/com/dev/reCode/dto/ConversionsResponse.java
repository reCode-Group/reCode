package com.dev.reCode.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ConversionsResponse {
    List<ConversionDto> conversions;
}
