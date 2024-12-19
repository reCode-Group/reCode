package com.dev.reCode.dto;

import com.dev.reCode.models.Conversion;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ConversionDto {
    private String  id;
    private LocalDateTime date;
    private String originCode; // Исходный код
    private String targetCode; // Целевой код

    public ConversionDto(Conversion conversion) {
        this.id = conversion.getId();
        this.date = conversion.getDate();
        this.originCode = conversion.getOriginCode();
        this.targetCode = conversion.getTargetCode();
    }

}
