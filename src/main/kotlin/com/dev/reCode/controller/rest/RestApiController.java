package com.dev.reCode.controller.rest;


import com.dev.reCode.models.MyUserDetails;
import com.dev.reCode.dto.ConverterResponse;
import com.dev.reCode.service.RequestRateLimiter;
import com.dev.reCode.network.rest.services.RestApiService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api")
@AllArgsConstructor
public class RestApiController {
    @Autowired
    private RequestRateLimiter requestRateLimiter;

    private RestApiService restApiService;
    @PostMapping("/convert")
    public ConverterResponse handleSubmit(@RequestParam String inputData) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!requestRateLimiter.isRequestAllowed(userDetails.getEmail())) {
            return new ConverterResponse("408", "Лимит превышен. Будет доступно через 4 часа.");
        }
        return restApiService.convert(inputData);
    }
}
