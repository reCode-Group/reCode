package com.dev.reCode.service;

import com.dev.reCode.dto.ConversionDto;
import com.dev.reCode.dto.ConversionsResponse;
import com.dev.reCode.models.Conversion;
import com.dev.reCode.models.MyUserDetails;
import com.dev.reCode.models.User;
import com.dev.reCode.repository.ConversionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class ConversionService {
    private final ConversionRepository conversionRepository;

    public void saveConversion(User user, String originCode, String targetCode) {
        Conversion conversion = new Conversion();
        conversion.setId(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()).substring(0, 10));

        conversion.setUser(user);
        conversion.setOriginCode(originCode);
        conversion.setTargetCode(targetCode);
        conversion.setDate(LocalDateTime.now());
        conversionRepository.save(conversion);
    }

    public ConversionsResponse findAllByUser(int page, User user) {
        int size = 10;

        PageRequest pageable = PageRequest.of(page, size);

        System.out.println(user.getEmail());
        List<Conversion> conversions = conversionRepository.findPageableAllByUser(pageable, user).getContent();
        System.out.println(conversions);
        List<ConversionDto> conversionDto = conversions.stream().map(ConversionDto::new).toList();
        System.out.print(conversionDto);
        return new ConversionsResponse(conversionDto);
    }

}
