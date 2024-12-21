package com.dev.reCode.controller.rest;


import com.dev.reCode.dto.ConversionsResponse;
import com.dev.reCode.models.MyUserDetails;
import com.dev.reCode.dto.ConverterResponse;
import com.dev.reCode.service.ConversionService;
import com.dev.reCode.service.RequestRateLimiter;
import com.dev.reCode.service.ApiService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api")
@AllArgsConstructor
public class ApiController {
    private RequestRateLimiter requestRateLimiter;
    private ApiService apiService;
    private ConversionService conversionService;

    @PostMapping("/convert")
    public ConverterResponse handleSubmit(@RequestParam String inputData) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!requestRateLimiter.isRequestAllowed(userDetails.getEmail())) {
            return new ConverterResponse("408", "Лимит превышен. Будет доступно через 4 часа.");
        }

        //TODO: Проверка валидности входного макроса (лексика и синтаксис)
        ConverterResponse response = apiService.convert(inputData);
        if (response.getStatus().equals("200")) {
            requestRateLimiter.consume(userDetails.getEmail());
            conversionService.saveConversion(userDetails.getUser(), inputData, response.getData());
         }
        return response;
    }
    @GetMapping("/conversions")
    public ConversionsResponse getUserConversions(@RequestParam(value = "page", defaultValue = "0") int page) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return conversionService.findAllByUser(page, userDetails.getUser());
    }

}
