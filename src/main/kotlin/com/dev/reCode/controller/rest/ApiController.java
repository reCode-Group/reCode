package com.dev.reCode.controller.rest;


import com.dev.reCode.models.MyUserDetails;
import com.dev.reCode.dto.ConverterResponse;
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
    @PostMapping("/convert")
    public ConverterResponse handleSubmit(@RequestParam String inputData) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (!requestRateLimiter.isRequestAllowed(userDetails.getEmail())) {
            return new ConverterResponse("408", "Лимит превышен. Будет доступно через 4 часа.");
        }
        return apiService.convert(inputData);
    }
}
