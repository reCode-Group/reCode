package com.dev.reCode.controller.rest;


import com.dev.reCode.dto.ConversionsResponse;
import com.dev.reCode.models.Conversion;
import com.dev.reCode.models.MyUserDetails;
import com.dev.reCode.dto.ConverterResponse;
import com.dev.reCode.service.ConversionService;
import com.dev.reCode.service.RequestRateLimiter;
import com.dev.reCode.service.ApiService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


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
