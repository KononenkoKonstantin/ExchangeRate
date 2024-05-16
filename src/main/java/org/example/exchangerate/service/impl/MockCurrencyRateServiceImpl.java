package org.example.exchangerate.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.exchangerate.dto.internal.CurrencyRateDto;
import org.example.exchangerate.dto.internal.CurrencyRateResponseDto;
import org.example.exchangerate.mapper.CurrencyRateMapper;
import org.example.exchangerate.service.CurrencyRateService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("mock")
@RequiredArgsConstructor
public class MockCurrencyRateServiceImpl implements CurrencyRateService {
    private final CurrencyRateMapper currencyRateMapper;


    @Override
    public CurrencyRateResponseDto getCurrencyRatesByDate(LocalDate date) {
        return getMockCurrencyRateResponseDto();
    }

    @Override
    public void deleteAllExchangeRatesByDate(String date) {
    }

    private CurrencyRateResponseDto getMockCurrencyRateResponseDto() {
        CurrencyRateResponseDto responseDto = new CurrencyRateResponseDto();
        responseDto.setDateOfLoading(LocalDateTime.now());

        List<CurrencyRateDto> currencyRateDtos = new ArrayList<>();
        currencyRateDtos.add(createMockCurrencyRateDto("USD", new BigDecimal("1.0")));
        currencyRateDtos.add(createMockCurrencyRateDto("EUR", new BigDecimal("0.85")));
        currencyRateDtos.add(createMockCurrencyRateDto("JPY", new BigDecimal("110.5")));

        responseDto.setCurrencyRateDtos(currencyRateDtos);
        return responseDto;
    }

    private CurrencyRateDto createMockCurrencyRateDto(String code, BigDecimal rate) {
        CurrencyRateDto currencyRateDto = new CurrencyRateDto();
        currencyRateDto.setCode(code);
        currencyRateDto.setRate(rate);
        currencyRateDto.setExchangeDate(LocalDate.now());
        return currencyRateDto;
    }
}
