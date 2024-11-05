package com.dev.reCode.network.rest.controllers;


import com.dev.reCode.network.dto.ConverterResponse;
import com.dev.reCode.network.rest.services.RestApiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api")
@AllArgsConstructor
public class RestApiController {
    private RestApiService restApiService;
    @PostMapping("/convert")
    public ConverterResponse handleSubmit(@RequestParam String inputData) {
        return restApiService.convert(inputData);
    }
}
